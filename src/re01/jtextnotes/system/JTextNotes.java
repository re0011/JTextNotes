/*
 * Copyright (C) 2020-2021 LE CLERRE Renaud
 *
 * This file is part of JTextNotes.
 *
 * JTextNotes is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTextNotes is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTextNotes. If not, see <http://www.gnu.org/licenses/>.
 */

package re01.jtextnotes.system;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import javax.swing.ImageIcon;
import re01.design.view.frame.Alert;
import re01.design.view.frame.Loading;
import re01.design.view.swing.JFileChooser;
import re01.exception.Re01JLibException;
import re01.io.system.Files;
import re01.language.LanguageTypeEnum;
import re01.tool.helper.system.MethodHelper;
import re01.program.user.KeyEnterReleasedEvent;
import re01.program.user.Listener;
import re01.jtextnotes.bean.Folder;
import re01.jtextnotes.bean.FolderRoot;
import re01.jtextnotes.bean.Note;
import re01.jtextnotes.design.image.Images;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.user.ParametersApplicator;
import re01.jtextnotes.view.WorkDirectoryLast;
import re01.jtextnotes.view.WorkDirectory;
import re01.jtextnotes.view.Notes;
import re01.jtextnotes.view.About;
import re01.jtextnotes.view.SelectLanguage;
import re01.jtextnotes.view.Welcome;

/**
 *
 * @author renaud
 */
public class JTextNotes {

	private static HashSet<Notes> viewNotes = new HashSet<Notes>();
	private static re01.jtextnotes.view.SelectLanguage viewSelectLanguage = null;
	private static re01.jtextnotes.view.Parameters viewParameters = null;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main( String[] args ) {
		MethodHelper.setMainMethods(JTextNotes.class.getDeclaredMethods() );
		re01.environment.Parameters.setProgramIconPath( Core.get_PROGRAM_ICON_PATH() );
		
		Toolkit.getDefaultToolkit().addAWTEventListener( new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				try {
					KeyEvent keyEvent = (KeyEvent) event;
					String keyEventParamsStr = keyEvent.paramString();
					
					String[] keyEventParamsStrSplit = keyEventParamsStr.split(",");
					String actionStr = keyEventParamsStrSplit[0];
					String keyCodeStr = keyEventParamsStrSplit[1];
					
					String[] keyCodeStrSplit = keyCodeStr.split("=");
					String keyCodeValueStr = keyCodeStrSplit[1];
					
					Integer keyCodeValue = Integer.parseInt( keyCodeValueStr );
					if ( keyCodeValue != null ) {
						if ( actionStr.equals("KEY_PRESSED") == true ) {
							if ( Objects.equals(keyCodeValue, KeyEvent.VK_ENTER) == true ) {
								Listener.setIsEnterPressed( true );
							}
						} else if ( actionStr.equals("KEY_RELEASED") == true ) {
							if ( Objects.equals(keyCodeValue, KeyEvent.VK_ENTER) == true ) {
								Listener.setIsEnterPressed( false );
								Listener.fireKeyEnterReleasedEvent( new KeyEnterReleasedEvent(this) );
							}
						}
					}
				} catch (Exception ex) { }
			}
		}, AWTEvent.KEY_EVENT_MASK);
		
		Files files = new Files();
		
		if ( files.isDirectoryExist(Core.get_PARAMS_DIR_PATH()) == false )
			files.createDirectory(Core.get_PARAMS_DIR_PATH());
		
		HashMap<String, HashMap<String, String>> params = files.loadParams( Core.get_PARAMS_FILE_PATH() );
		ParametersApplicator parametersApplicator = new ParametersApplicator();
		parametersApplicator.apply_default();
		parametersApplicator.apply( params );
		
		createViewWelcomeOrSelectLanguage( null );

		Runtime.getRuntime().addShutdownHook( new Thread() {
			@Override
			public void run() {
				saveParameters( null );
			}
		} );
	}

	public static HashSet<Notes> getViewNotes() {
		return viewNotes;
	}

	public static void setViewNotes(HashSet<Notes> viewNotes) {
		JTextNotes.viewNotes = viewNotes;
	}

	public static SelectLanguage getViewSelectLanguage() {
		return viewSelectLanguage;
	}

	public static void setViewSelectLanguage(SelectLanguage viewSelectLanguage) {
		JTextNotes.viewSelectLanguage = viewSelectLanguage;
	}

	public static re01.jtextnotes.view.Parameters getViewParameters() {
		return viewParameters;
	}

	public static void setViewParameters(re01.jtextnotes.view.Parameters viewParameters) {
		JTextNotes.viewParameters = viewParameters;
	}
	
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	// Program
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	
	public static void programExit( Object[] args ) {
		System.exit( 0 );
	}
	
	public static void saveParameters( Object[] args ) {
		Files files = new Files();
		ParametersApplicator parametersApplicator = new ParametersApplicator();
		files.saveParams( Core.get_PARAMS_FILE_PATH(), parametersApplicator.get() );
	}
	
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	// Views
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	//====================
	
	public static void createViewWelcomeOrSelectLanguage( Object[] args ) {
		if ( re01.jtextnotes.user.Parameters.getIsLanguageSelected() == false ) {
			createViewSelectLanguage( null );
		} else if ( re01.jtextnotes.user.Parameters.getIsViewWelcomeDoNotDisplayAgain() == false ) {
			createViewWelcome( null );
		} else if ( re01.jtextnotes.user.Parameters.getIsWorkDirectoryLastAutoSelect() != null && re01.jtextnotes.user.Parameters.getIsWorkDirectoryLastAutoSelect() == true ) {
			
			Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
			
			try {
				MethodHelper.addOrReplaceCallbackArg(WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath(), argsNew );
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
			
			createViewWorkDirectoryOrNotes( argsNew );
		} else
			createViewWorkDirectoryOrNotes( null );
	}
	
	public static void createViewSelectLanguage( Object[] args ) {
		Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
		try {
			Boolean isOpenWorkDirectory = MethodHelper.getArgBoolean( SelectLanguage.get_ARG_KEY_IS_OPEN_WORK_DIRECTORY_AFTER_SELECT(), args );
			if ( isOpenWorkDirectory == null || isOpenWorkDirectory != null && isOpenWorkDirectory == true )
				MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "createViewWelcomeOrSelectLanguage" }, argsNew );
			else if ( isOpenWorkDirectory != null && isOpenWorkDirectory == false ) {
				MethodHelper.addOrReplaceCallbackArg( SelectLanguage.get_ARG_KEY_CURRENT_SELECTED_LANGUAGE_TYPE(), Parameters.getLanguageType(), argsNew );
				MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "validViewSelectLanguage" }, argsNew );
			}
				
			if ( viewSelectLanguage == null ) {
				viewSelectLanguage = new SelectLanguage( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_SELECT_LANGUAGE(), argsNew );
				viewSelectLanguage.resume();
			} else {
				viewSelectLanguage.resume();
				viewSelectLanguage.requestFocus();
			}
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public static void validViewSelectLanguage( Object[] args ) {
		LanguageTypeEnum langType = null;
		try {
			langType = (LanguageTypeEnum) MethodHelper.getArg(re01.jtextnotes.view.SelectLanguage.get_ARG_KEY_CURRENT_SELECTED_LANGUAGE_TYPE(), args );
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
		
		if ( langType != null ) {
			if ( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_TYPE() != langType ) {
				try {
					Images icons = new Images();
					LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
					messagesMap.put(icons.get_GLOBAL_IMAGE_ICON_INFO(), re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_TEXT_YOU_NEED_TO_RESTART_PROGRAM_TO_APPLY_CHANGES() );

					Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

					MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );

					Alert alert = new Alert( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_MESSAGES(), argsNew );
					alert.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
			}
		}
	}
	
	public static void createViewWelcome( Object[] args ) {
		Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
		try {
			MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "createViewWorkDirectoryOrNotes" }, argsNew );
			Welcome view = new Welcome( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_WELCOME(), argsNew );
			view.resume();
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public static void createViewWorkDirectoryOrNotes( Object[] args ) {
		String workDirectoryPath = null;
		try {
			workDirectoryPath = MethodHelper.getArgString( WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), args );
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		} catch ( Exception e ) {
			Core.get_LOGGER().write( e );
		}
		
		Boolean isWorkDirectoryPathSelectedFromView = null;
		try {
			isWorkDirectoryPathSelectedFromView = MethodHelper.getArgBoolean(WorkDirectory.get_ARG_KEY_IS_VIEW_VALIDATED(), args );
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
		if ( isWorkDirectoryPathSelectedFromView != null && isWorkDirectoryPathSelectedFromView == true && workDirectoryPath == null ) {
			createViewWorkDirectory( new Object[]{MethodHelper.createDefaultArgs()} );
			
			try {
				Images icons = new Images();
				LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
				
				messagesMap.put(icons.get_GLOBAL_IMAGE_ICON_ERROR(), re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY_REQUIRED() );

				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );

				Alert alert = new Alert( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_MESSAGES(), argsNew );
				alert.resume();
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
		} else {
			Boolean isViewWelcomeDoNotDisplayAgain = null;
			try {
				isViewWelcomeDoNotDisplayAgain = MethodHelper.getArgBoolean( Welcome.get_ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN(), args );
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
			if ( isViewWelcomeDoNotDisplayAgain != null )
				re01.jtextnotes.user.Parameters.setIsViewWelcomeDoNotDisplayAgain( isViewWelcomeDoNotDisplayAgain );
			
			if ( workDirectoryPath != null ) {
				createViewNotes( args );
			} else if ( re01.jtextnotes.user.Parameters.getIsWorkDirectoryLastAutoSelect() != null && re01.jtextnotes.user.Parameters.getIsWorkDirectoryLastAutoSelect() == true && re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() != null ) {
				try {
					MethodHelper.addOrReplaceCallbackArg(WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}

				createViewNotes( args );
			} else if ( re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() != null ) {
				try {
					MethodHelper.addOrReplaceCallbackArg(WorkDirectoryLast.get_ARG_KEY_WORK_DIRECTORY_LAST_PATH(), re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				createViewWorkDirectoryLast( args );
			} else {
				createViewWorkDirectory( args );
			}
		}
	}
	
	public static void createViewWorkDirectoryLast( Object[] args ) {
		Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
		try {
			MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "createViewNotes" }, argsNew );
			MethodHelper.addOrReplaceCallbackArg(WorkDirectoryLast.get_ARG_KEY_WORK_DIRECTORY_LAST_PATH(), re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath(), argsNew );
			WorkDirectoryLast view = new WorkDirectoryLast( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY(), argsNew );
			view.resume();
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public static void createViewWorkDirectory( Object[] args ) {
		Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
		
		try {
			String workDirectoryPath = MethodHelper.getArgString( JFileChooser.get_ARG_KEY_PATH_SELECTED(), args );
			if ( workDirectoryPath != null ) {
				MethodHelper.addOrReplaceCallbackArg( WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), workDirectoryPath, argsNew );
			} else {
				MethodHelper.addOrReplaceCallbackArg( WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), null, argsNew );
			}
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
		
		try {
			MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "createViewWorkDirectoryOrNotes" }, argsNew );
			WorkDirectory view = new WorkDirectory( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY(), argsNew );
			view.resume();
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public static void createViewNotes( Object[] args ) {
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					String workDirectoryPath = MethodHelper.getArgString( WorkDirectory.get_ARG_KEY_WORK_DIRECTORY_PATH(), args );

					if ( workDirectoryPath == null && re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() == null ) {
						createViewWorkDirectoryOrNotes( args );
					} else {
						if ( workDirectoryPath == null )
							workDirectoryPath = re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath();

						Files files = new Files();
						if ( files.chmodCheck(workDirectoryPath) == true ) {
							File fileRoot = new File( workDirectoryPath );
							FolderRoot folderRoot = new FolderRoot(
								fileRoot.getName(), 
								fileRoot.getPath(), 
								new ArrayList<Folder>(), 
								new ArrayList<Note>()
							);

							MethodHelper.addOrReplaceCallbackArg( Notes.get_ARG_KEY_ROOT_FOLDER_NOTES_FILE(), fileRoot, args );
							Notes view = new Notes( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY(), args );
							folderRoot = (FolderRoot) view.createFolderRoot( folderRoot, null, fileRoot );
							view.setFolderRoot( folderRoot );
							view.createTree();

							Object[] argsNewLoading = new Object[]{ MethodHelper.createDefaultArgs() };
							Loading viewLoading1 = new Loading( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY_OPEN_NEW(), argsNewLoading );
							viewLoading1.resume();

							boolean isWorkDirectoryPartOpened = false;
							Iterator<Notes> viewNotesIt = viewNotes.iterator();
							while ( viewNotesIt.hasNext() ) {
								Notes viewNotesFound = viewNotesIt.next();

								if ( view.isAnyItemExistsInBothFolders(folderRoot, viewNotesFound.getFolderRoot()) == true ) {
									isWorkDirectoryPartOpened = true;
									break;
								}
							}

							viewLoading1.delete();

							if ( isWorkDirectoryPartOpened == false ) {
								re01.jtextnotes.user.Parameters.setWorkDirectoryLastPath( workDirectoryPath );
								viewNotes.add( view );
								view.resume();

							} else {
								view.delete();

								Images icons = new Images();
								LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
								messagesMap.put( icons.get_GLOBAL_IMAGE_ICON_ERROR(), re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG_PROGRAM().get_WORK_DIRECTORY_PART_OPENED() );

								Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

								MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );

								Alert alert = new Alert( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_MESSAGES(), argsNew );
								alert.resume();
							}
						} else {
							if ( re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() != null && workDirectoryPath.equals(re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath()) == true ) {
								re01.jtextnotes.user.Parameters.setWorkDirectoryLastPath( null );
								createViewWorkDirectory( new Object[]{MethodHelper.createDefaultArgs()} );
							}
							
							Images icons = new Images();
							LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
							
							messagesMap.put( icons.get_GLOBAL_IMAGE_ICON_ERROR(), re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_CHMOD_DIRECTORY_INSUFFICIENT() );

							Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

							MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );

							Alert alert = new Alert( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_MESSAGES(), argsNew );
							alert.resume();
						}
					}
				} catch ( Re01JLibException e ) {
					Core.get_LOGGER().write( e );
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public static void createViewParameters( Object[] args ) {
		
		if ( viewParameters == null ) {
			Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

			try {
				MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), new String[]{ "validViewParameters" }, argsNew );
				MethodHelper.addOrReplaceCallbackArg(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_CHARS_BASE_SIZE(), re01.environment.Parameters.getCharsBaseSize(), argsNew );
				MethodHelper.addOrReplaceCallbackArg(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_ICONS_SIZE(), re01.environment.Parameters.getIconsSize(), argsNew );
				MethodHelper.addOrReplaceCallbackArg(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN(), re01.jtextnotes.user.Parameters.getIsViewWelcomeDoNotDisplayAgain(), argsNew );
				MethodHelper.addOrReplaceCallbackArg(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT(), re01.jtextnotes.user.Parameters.getIsWorkDirectoryLastAutoSelect(), argsNew );
				MethodHelper.addOrReplaceCallbackArg(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH(), re01.environment.Parameters.getDocumentHistoryLength(), argsNew );
				viewParameters = new re01.jtextnotes.view.Parameters( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_PARAMETERS(), argsNew );
				viewParameters.resume();
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
		} else {
			viewParameters.resume();
			viewParameters.requestFocus();
		}
	}
	
	public static void validViewParameters( Object[] args ) {
		boolean isNeedProgramRestartToApplyChanges = false;
		
		Integer charsBaseSize = null;
		Integer iconsSize = null;
		Boolean isViewWelcomeDoNotDisplayAgain = null;
		Boolean isWorkDirectoryLastAutoSelect = null;
		Integer documentHistoryLength = null;
		try {
			charsBaseSize = MethodHelper.getArgInteger(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_CHARS_BASE_SIZE(), args );
			iconsSize = MethodHelper.getArgInteger(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_ICONS_SIZE(), args );
			isViewWelcomeDoNotDisplayAgain = MethodHelper.getArgBoolean(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN(), args );
			isWorkDirectoryLastAutoSelect = MethodHelper.getArgBoolean(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT(), args );
			documentHistoryLength = MethodHelper.getArgInteger(re01.jtextnotes.view.Parameters.get_ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH(), args );
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
		if ( charsBaseSize != null ) {
			if ( Objects.equals(re01.environment.Parameters.getCharsBaseSize(), charsBaseSize) == false )
				isNeedProgramRestartToApplyChanges = true;
			
			re01.environment.Parameters.setCharsBaseSize( charsBaseSize );
		}
		if ( iconsSize != null ) {
			if ( Objects.equals(re01.environment.Parameters.getIconsSize(), iconsSize) == false )
				isNeedProgramRestartToApplyChanges = true;
			
			re01.environment.Parameters.setIconsSize( iconsSize );
		}
		if ( isViewWelcomeDoNotDisplayAgain != null ) {
			re01.jtextnotes.user.Parameters.setIsViewWelcomeDoNotDisplayAgain( isViewWelcomeDoNotDisplayAgain );
		}
		if ( isWorkDirectoryLastAutoSelect != null ) {
			re01.jtextnotes.user.Parameters.setIsWorkDirectoryLastAutoSelect( isWorkDirectoryLastAutoSelect );
		}
		if ( documentHistoryLength != null ) {
			re01.environment.Parameters.setDocumentHistoryLength( documentHistoryLength );
		}
		
		if ( isNeedProgramRestartToApplyChanges == true ) {
			try {
				Images icons = new Images();
				LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
				messagesMap.put( icons.get_GLOBAL_IMAGE_ICON_INFO(), re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_TEXT_YOU_NEED_TO_RESTART_PROGRAM_TO_APPLY_CHANGES() );

				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );

				Alert alert = new Alert( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_MESSAGES(), argsNew );
				alert.resume();
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
		}
	}
	
	public static void createViewAbout( Object[] args ) {
		try {
			About view = new About( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_ABOUT(), args );
			view.resume();
		} catch ( Re01JLibException e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public static void refreshViewsNotesTree() {
		Iterator<Notes> notesIt = JTextNotes.viewNotes.iterator();
		while ( notesIt.hasNext() ) {
			Notes notesFound = notesIt.next();
			notesFound.refreshTree();
		}
	}
	
	public static boolean isViewsNotesSaved() {
		boolean isSaved = true;
		Iterator<Notes> notesIt = JTextNotes.viewNotes.iterator();
		while ( notesIt.hasNext() ) {
			Notes notesFound = notesIt.next();
			isSaved = notesFound.isNotesSaved();
			if ( isSaved == false )
				break;
		}
		return isSaved;
	}
	
	public static void viewsNotesDeleteFolder( Notes viewNotes, Folder folder ) {
		Iterator<Notes> notesIt = JTextNotes.viewNotes.iterator();
		while ( notesIt.hasNext() ) {
			Notes viewNotesFound = notesIt.next();
			if ( viewNotesFound.equals(viewNotes) == true ) {
				viewNotesFound.deleteFolder( folder );
				viewNotesFound.deleteNode( folder );
				break;
			}
			viewNotesFound.refreshTree();
		}
	}
	
	public static void viewsNotesDeleteNote( Notes viewNotes, Note note ) {
		Iterator<Notes> notesIt = JTextNotes.viewNotes.iterator();
		while ( notesIt.hasNext() ) {
			Notes viewNotesFound = notesIt.next();
			if ( viewNotesFound.equals(viewNotes) == true ) {
				viewNotesFound.deleteNote( note );
				viewNotesFound.deleteNode( note );
				break;
			}
			viewNotesFound.refreshTree();
		}
	}
	
}
