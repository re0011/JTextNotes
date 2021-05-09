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

package re01.jtextnotes.view;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import re01.design.color.Color;
import re01.design.color.ColorAttributeTypeEnum;
import re01.design.color.ColorTypeEnum;
import re01.design.font.FontSize;
import re01.design.font.FontStyleEnum;
import re01.design.image.ImageTypeEnum;
import re01.design.view.awt.GridLayout;
import re01.design.view.frame.Alert;
import re01.design.view.frame.Confirm;
import re01.design.view.frame.Loading;
import re01.design.view.frame.Prompt;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JLabel;
import re01.design.view.swing.JMenu;
import re01.design.view.swing.JMenuBar;
import re01.design.view.swing.JMenuItem;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JPopupMenu;
import re01.design.view.swing.JProgressBar;
import re01.design.view.swing.JScrollPane;
import re01.design.view.swing.JSplitPane;
import re01.design.view.swing.JTextField;
import re01.design.view.swing.JTree;
import re01.design.view.swing.jcombobox.JComboBoxTypeEnum;
import re01.design.view.swing.jtree.DefaultMutableTreeNode;
import re01.environment.Parameters;
import re01.exception.Re01JLibException;
import re01.io.system.Files;
import re01.io.serialize.ParametersSerializer;
import re01.object.Tuple;
import re01.tool.helper.system.DesktopHelper;
import re01.tool.helper.system.MethodHelper;
import re01.tool.helper.system.StringHelper;
import re01.tool.reader.NoteReader;
import re01.tool.reader.ParameterReader;
import re01.jtextnotes.bean.Folder;
import re01.jtextnotes.bean.FolderRoot;
import re01.jtextnotes.bean.Note;
import re01.jtextnotes.bean.NoteIconTypeEnum;
import re01.jtextnotes.design.color.Colors;
import re01.jtextnotes.design.image.Images;
import re01.jtextnotes.design.view.swing.JButtonNoteOpened;
import re01.jtextnotes.design.view.swing.JComboBoxNoteOpened;
import re01.jtextnotes.design.view.swing.JTextPane;
import re01.jtextnotes.system.JTextNotes;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.user.ParametersApplicator;
import re01.tool.helper.system.KeyboardHelper;

/**
 *
 * @author renaud
 */
public class Notes extends Global {
	
	private static final String ARG_KEY_ROOT_FOLDER_NOTES_FILE = "view_notes_root_folder_notes_file";
	private static final String ARG_KEY_ASK_FOR_WORK_DIRECTORY_IF_CLOSE = "view_notes_ask_for_work_directory_if_close";
	private static final String ARG_KEY_NODE_PARENT_OF_NEW_NODE = "view_notes_node_parent_of_new_node";
	private static final String ARG_KEY_SET_NOTE_OPENED_IS_SAVED = "view_notes_set_not_opened_is_saved";
	
	private JSplitPane splitPaneTreeAndNoteContent;
	private JPanel panelNoteContent = null;
	
	private HashSet<DefaultMutableTreeNode> foldersNodesRenderedInTree = new HashSet<>();
	private volatile HashSet<Note> notesRenderedInTree = new HashSet<>();
	private volatile Lock notesRenderedInTreeLock = new ReentrantLock();
	
	private Folder folderRoot = null;
	private DefaultMutableTreeNode nodeRoot = null;
	private DefaultMutableTreeNode nodeOpened = null;
	private DefaultMutableTreeNode nodeOpenedNew = null;
	
	private TreePath treePathClicked = null;
	private Note noteOpened = null;
	
	private HashSet<DefaultMutableTreeNode> nodesSelected = new HashSet<>();
	private static HashMap<Folder, Notes> foldersSelected = new HashMap<>();
	private static HashMap<Note, Notes> notesSelected = new HashMap<>();
	private static boolean isCutItemsSelected = false;
	
	private final int TREE_MIN_WIDTH = 192;
	private final int TOOLTIP_DISMISS_TIMEOUT = ToolTipManager.sharedInstance().getDismissDelay();
	
	//-----
	
	private JMenuBar messagesBar = null;
	private JPanel subMessagesBar = null;
	private Boolean isMessagesBarReseted = true;
	private ArrayList<String> messagesBarOkMessages = new ArrayList<>();
	private ArrayList<String> messagesBarInfoMessages = new ArrayList<>();
	private ArrayList<String> messagesBarErrorMessages = new ArrayList<>();
	
	private JMenuBar statusBar = null;
	private JPanel subStatusBar = null;
	private ArrayList<String> statusBarMessages = new ArrayList<>();
	
	private WindowAdapter windowAdapter = null;
	private ComponentListener windowComponentListener = null;
	
	private DefaultTreeModel treeModel = null;
	private JTree tree = null;
	private TreeSelectionListener treeSelectionListener = null;
	private KeyListener treeKeyListener = null;
	private MouseListener treeMouseListener = null;
	
	private JPanel panelSearchInNote = null;
	private JTextField inputSearchInNote = null;
	
	private JPopupMenu contextMenuPopup = null;
	
	private static HashMap<Integer, HashMap<SimpleAttributeSet, Integer[]>> copiedStyle = null;
	
	//-----
	
	private Images images = new Images();
	private Files files = new Files();
	private DesktopHelper desktopHelper = new DesktopHelper();
	private NoteReader noteReader = new NoteReader();
	private ParameterReader parameterReader = new ParameterReader();
	private Colors colors = new Colors();
	
	//====================
	// region construct
	//====================
	
	public Notes( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		construct();
	}

	private void construct() throws Re01JLibException {
		final Notes NOTES = this;
		
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run() {
						Loading viewLoading1 = null;
						try {
							Object[] argsNewLoading = new Object[]{ MethodHelper.createDefaultArgs() };
							viewLoading1 = new Loading( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_CLOSE(), argsNewLoading );
							viewLoading1.resume();
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
						
						if ( NOTES.isNotesSaved(folderRoot) == false ) {
							Class c = NOTES.getClass();

							HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
							HashMap<Class, String[]> callbacksMap = new HashMap<>();
							callbacksMap.put( c, new String[]{"workDirectoryClose"} );
							callbacksObjMap.put( NOTES, callbacksMap );

							Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

							try {
								MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE_ICON(), images.get_GLOBAL_IMAGE_ICON_CAUTION(), argsNew 
								);
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE(), LANG.get_LANG_PROGRAM().get_CONFIRM_CLOSE_WORK_DIRECTORY(), argsNew 
								);

								Confirm confirm = new Confirm( LANG.get_LANG().get_CLOSE(), argsNew );
								confirm.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else {
							THIS.delete();
							JTextNotes.getViewNotes().remove( (Notes)THIS );
						}
						
						if ( viewLoading1 != null )
							viewLoading1.delete();
					}
				});
				thread.setDaemon(false);
				thread.start();
			}
		};
		this.addWindowListener( windowAdapter );
		
		windowComponentListener = new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				final Notes NOTES = ( Notes ) e.getSource();
				re01.jtextnotes.user.Parameters.setWindowWidth( NOTES.getWidth() );
				re01.jtextnotes.user.Parameters.setWindowHeight( NOTES.getHeight() );
				
				if ( splitPaneTreeAndNoteContent != null ) {
					int dividerValue = splitPaneTreeAndNoteContent.getDividerLocation();
					if ( dividerValue <  TREE_MIN_WIDTH )
						splitPaneTreeAndNoteContent.setDividerLocation( TREE_MIN_WIDTH );
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				
			}
		};
		this.addComponentListener( windowComponentListener );
		
		this.setPreferredSize(new Dimension( re01.jtextnotes.system.Parameters.get_window_width(), re01.jtextnotes.system.Parameters.get_window_height() ) );
		
		//====================
		// region menu
		//====================
		
		JMenuBar jmenuBar = new JMenuBar();
		jmenuBar.setMaximumSize( new Dimension( Integer.MAX_VALUE, 24 ) );
		JMenu menuFile = new JMenu( THIS.get_LANG().get_LANG().get_FILE() );
		
		JMenuItem menuItemOpenWorkDirectory = new JMenuItem( 
			LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_OPEN_NEW(), 
			LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_OPEN_NEW_KEYEVENT() 
		);
		menuItemOpenWorkDirectory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextNotes.createViewWorkDirectory( null );
			}
		} );
		menuFile.add( menuItemOpenWorkDirectory );
		
		JMenuItem menuItemCloseWorkDirectory = new JMenuItem( 
			LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_CLOSE(), 
			LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_CLOSE_KEYEVENT() 
		);
		menuItemCloseWorkDirectory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run() {
						Loading viewLoading1 = null;
						try {
							Object[] argsNewLoading = new Object[]{ MethodHelper.createDefaultArgs() };
							viewLoading1 = new Loading( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_CLOSE(), argsNewLoading );
							viewLoading1.resume();
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
						
						if ( NOTES.isNotesSaved(folderRoot) == false ) {
							Class c = NOTES.getClass();

							HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
							HashMap<Class, String[]> callbacksMap = new HashMap<>();
							callbacksMap.put( c, new String[]{"workDirectoryClose"} );
							callbacksObjMap.put( NOTES, callbacksMap );

							Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

							try {
								MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE_ICON(), images.get_GLOBAL_IMAGE_ICON_CAUTION(), argsNew 
								);
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE(), LANG.get_LANG_PROGRAM().get_CONFIRM_CLOSE_WORK_DIRECTORY(), argsNew 
								);
								MethodHelper.addOrReplaceCallbackArg( ARG_KEY_ASK_FOR_WORK_DIRECTORY_IF_CLOSE, true, argsNew );

								Confirm confirm = new Confirm( LANG.get_LANG().get_CLOSE(), argsNew );
								confirm.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else {
							THIS.delete();
							JTextNotes.getViewNotes().remove( (Notes)THIS );
							if ( Objects.equals(JTextNotes.getViewNotes().size(), 0) == true )
								JTextNotes.createViewWorkDirectory( null );
						}
						
						if ( viewLoading1 != null )
							viewLoading1.delete();
					}
				});
				thread.setDaemon(false);
				thread.start();
			}
		} );
		menuFile.add( menuItemCloseWorkDirectory );
		
		JMenuItem menuItemExit = new JMenuItem( 
			LANG.get_LANG().get_EXIT(), 
			LANG.get_LANG().get_EXIT_KEYEVENT() 
		);
		menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run() {
						Loading viewLoading1 = null;
						try {
							Object[] argsNewLoading = new Object[]{ MethodHelper.createDefaultArgs() };
							viewLoading1 = new Loading( re01.jtextnotes.user.Parameters.getLanguageSelected().get_LANG().get_EXIT(), argsNewLoading );
							viewLoading1.resume();
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
						
						if ( JTextNotes.isViewsNotesSaved() == false ) {
							Class c = NOTES.getClass();

							HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
							HashMap<Class, String[]> callbacksMap = new HashMap<>();
							callbacksMap.put( c, new String[]{"programExit"} );
							callbacksObjMap.put( NOTES, callbacksMap );

							Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

							try {
								MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE_ICON(), images.get_GLOBAL_IMAGE_ICON_CAUTION(), argsNew 
								);
								MethodHelper.addOrReplaceCallbackArg( 
									Confirm.get_ARG_KEY_CONFIRM_MESSAGE(), LANG.get_LANG_PROGRAM().get_CONFIRM_EXIT_PROGRAM(), argsNew 
								);

								Confirm confirm = new Confirm( LANG.get_LANG().get_EXIT(), argsNew );
								confirm.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else {
							JTextNotes.programExit( null );
						}
						
						if ( viewLoading1 != null )
							viewLoading1.delete();
					}
				});
				thread.setDaemon(false);
				thread.start();
			}
		} );
		menuFile.add( menuItemExit );
		
		jmenuBar.add( menuFile );
		
		JMenu menuEdit = new JMenu( LANG.get_LANG().get_EDIT() );
		JMenuItem menuItemSearchInNote = new JMenuItem( 
			LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTE(), 
			LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTE_KEYEVENT() 
		);
		menuItemSearchInNote.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( panelSearchInNote != null ) {
					panelSearchInNote.setVisible( true );
					inputSearchInNote.requestFocus();
				}
			}
		} );
		menuEdit.add( menuItemSearchInNote );
		
		JMenuItem menuItemSearchInNotes = new JMenuItem( 
			LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES(), 
			LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES_KEYEVENT() 
		);
		menuItemSearchInNotes.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Class c = NOTES.getClass();

				HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
				HashMap<Class, String[]> callbacksMap = new HashMap<>();
				callbacksMap.put( c, new String[]{"searchInNotes"} );
				callbacksObjMap.put( NOTES, callbacksMap );

				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				try {
					MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
					MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_MESSAGE(), LANG.get_LANG_PROGRAM().get_PROMPT_SEARCH_IN_NOTES(), argsNew );
					MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_LABEL(), LANG.get_LANG().get_KEYWORD(), argsNew );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}

				try {
					Prompt prompt = new Prompt( LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES(), argsNew );
					prompt.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		menuEdit.add( menuItemSearchInNotes );
		
		JMenuItem menuItemSelectLanguage = new JMenuItem( 
			LANG.get_LANG().get_SELECT_LANGUAGE(), 
			LANG.get_LANG().get_SELECT_LANGUAGE_KEYEVENT() 
		);
		menuItemSelectLanguage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
				try {
					MethodHelper.addOrReplaceCallbackArg( SelectLanguage.get_ARG_KEY_IS_OPEN_WORK_DIRECTORY_AFTER_SELECT(), false, argsNew );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				JTextNotes.createViewSelectLanguage( argsNew );
			}
		} );
		menuEdit.add( menuItemSelectLanguage );
		
		JMenuItem menuItemParameters = new JMenuItem( 
			LANG.get_LANG().get_PARAMETERS(), 
			LANG.get_LANG().get_PARAMETERS_KEYEVENT() 
		);
		menuItemParameters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextNotes.createViewParameters( null );
			}
		} );
		menuEdit.add( menuItemParameters );
		
		jmenuBar.add( menuEdit );
		
		JMenu menuHelp = new JMenu( LANG.get_LANG().get_HELP() );
		JMenuItem menuItemHelp = new JMenuItem( 
			LANG.get_LANG().get_HELP(), 
			LANG.get_LANG().get_HELP_KEYEVENT() 
		);
		menuItemHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] args = new Object[]{ MethodHelper.createDefaultArgs() };
				try {
					MethodHelper.addOrReplaceCallbackArg( About.get_ARG_KEY_DEFAULT_NODE_STR(), LANG.get_LANG().get_HELP(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				JTextNotes.createViewAbout( args );
			}
		} );
		menuHelp.add( menuItemHelp );
		
		JMenuItem menuItemAbout = new JMenuItem( 
			LANG.get_LANG().get_ABOUT(), 
			LANG.get_LANG().get_ABOUT_KEYEVENT() 
		);
		menuItemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] args = new Object[]{ MethodHelper.createDefaultArgs() };
				try {
					MethodHelper.addOrReplaceCallbackArg( About.get_ARG_KEY_DEFAULT_NODE_STR(), LANG.get_LANG().get_ABOUT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				JTextNotes.createViewAbout( args );
			}
		} );
		menuHelp.add( menuItemAbout );
		
		jmenuBar.add( menuHelp );
		
		this.setJMenuBar( jmenuBar );
		
		//====================
		// end region menu
		//====================
		
		//====================
		// region status bars
		//====================
		
		messagesBar = new JMenuBar();
		messagesBar.setLayout( new GridLayout( 1, 1 ) );
		
		subMessagesBar = new JPanel();
		subMessagesBar.setLayout( new BoxLayout( subMessagesBar, BoxLayout.LINE_AXIS ) );
		subMessagesBar.addMouseListener( new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();
				
				Iterator<String> messagesBarOkMessagesIt = messagesBarOkMessages.iterator();
				while ( messagesBarOkMessagesIt.hasNext() ) {
					String message = messagesBarOkMessagesIt.next();
					ImageIcon imageIcon = images.get_GLOBAL_IMAGE_ICON_OK();
					messagesMap.put( new ImageIcon(imageIcon.getImage()), message );
				}
				
				Iterator<String> messagesBarInfoMessagesIt = messagesBarInfoMessages.iterator();
				while ( messagesBarInfoMessagesIt.hasNext() ) {
					String message = messagesBarInfoMessagesIt.next();
					ImageIcon imageIcon = images.get_GLOBAL_IMAGE_ICON_INFO();
					messagesMap.put( new ImageIcon(imageIcon.getImage()), message );
				}
				
				Iterator<String> messagesBarErrorMessagesIt = messagesBarErrorMessages.iterator();
				while ( messagesBarErrorMessagesIt.hasNext() ) {
					String message = messagesBarErrorMessagesIt.next();
					ImageIcon imageIcon = images.get_GLOBAL_IMAGE_ICON_ERROR();
					messagesMap.put( new ImageIcon(imageIcon.getImage()), message );
				}
				
				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				try {
					MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}

				try {
					Alert alert = new Alert( LANG.get_LANG().get_MESSAGES(), argsNew );
					alert.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		} );
		subMessagesBar.setBackground( new Color(ColorTypeEnum.YellowNaples, ColorAttributeTypeEnum.Background).getRgbColor() );
		
		statusBar = new JMenuBar();
		statusBar.setLayout( new GridLayout( 1, 1 ) );
		subStatusBar = new JPanel();
		subStatusBar.setLayout( new BoxLayout( subStatusBar, BoxLayout.LINE_AXIS ) );
		
		//====================
		// end region status bars
		//====================
	}
	
	public void createTree() throws Re01JLibException {
		final Notes THIS = this;
		deleteTreeListeners();
		
		JScrollPane treeView = null;
		
		File fileRoot = (File) MethodHelper.getArg(ARG_KEY_ROOT_FOLDER_NOTES_FILE, THIS.getCallbacksArgs());
		if ( this.folderRoot != null && fileRoot != null ) {
			nodeRoot = createNode( this.folderRoot );
			
			setTitle( nodeRoot.getUserObject().toString() + " - " + getTitle() );

			treeModel = new DefaultTreeModel( nodeRoot );

			tree = new JTree( nodeRoot );
			tree.setModel( treeModel );
			
			treeSelectionListener = new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					TreePath treePathClicked = null;
					try {
						treePathClicked = ( TreePath ) e.getNewLeadSelectionPath();
					} catch ( NullPointerException ex ) { }

					if ( treePathClicked != null ) {
						THIS.setTreePathClicked( treePathClicked );
					}
				}
			};
			tree.addTreeSelectionListener( treeSelectionListener );

			treeKeyListener = new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyPressed(KeyEvent e) {
					Integer keyCode = e.getKeyCode();

					if ( Objects.equals(keyCode, KeyEvent.VK_ENTER) == true ) {
						DefaultMutableTreeNode nodeOpenedNew = ( DefaultMutableTreeNode ) THIS.getTreePathClicked().getLastPathComponent();
						if ( nodeOpenedNew != null ) {
							tree.expandPath( THIS.getTreePathClicked() );
							
							Note note = THIS.getNote( nodeOpenedNew );
							if ( note != null ) {
								if ( THIS.getNodeOpened() == null || THIS.getNodeOpened() != null && THIS.getNodeOpened().equals(nodeOpenedNew) == false )
									THIS.setNodeOpenedNew( nodeOpenedNew );
							}
						}
						THIS.openNewNode();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_F) && e.isControlDown() == true ) {
						THIS.promptNewFolder();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_N) && e.isControlDown() == true ) {
						THIS.promptNewNote();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_C) && e.isControlDown() == true ) {
						Thread thread = new Thread(new Runnable(){
							@Override
							public void run() {
								Loading viewLoading1 = null;
								try {
									Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
									viewLoading1 = new Loading( THIS.get_LANG().get_LANG().get_COPY(), argsNewLoading1 );
									viewLoading1.resume();
								} catch ( Re01JLibException ex ) {
									Core.get_LOGGER().write( ex );
								}

								THIS.copyNotes( false );

								if ( viewLoading1 != null )
									viewLoading1.delete();
							}
						});
						thread.setDaemon(false);
						thread.start();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_X) && e.isControlDown() == true ) {
						Thread thread = new Thread(new Runnable(){
							@Override
							public void run() {
								Loading viewLoading1 = null;
								try {
									Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
									viewLoading1 = new Loading( THIS.get_LANG().get_LANG().get_CUT(), argsNewLoading1 );
									viewLoading1.resume();
								} catch ( Re01JLibException ex ) {
									Core.get_LOGGER().write( ex );
								}

								THIS.copyNotes( true );

								if ( viewLoading1 != null )
									viewLoading1.delete();
							}
						});
						thread.setDaemon(false);
						thread.start();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_V) && e.isControlDown() == true ) {
						THIS.pasteNotes();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_S) && e.isControlDown() == true ) {
						THIS.confirmSaveNotes();
					} else if ( Objects.equals(keyCode, KeyEvent.VK_DELETE) == true ) {
						try {
							THIS.confirmDeleteNotes();
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					Integer keyCode = e.getKeyCode();

					JTree tree = (JTree) e.getSource();

					THIS.selectNodes();
					
					if ( Objects.equals(keyCode, KeyEvent.VK_CONTEXT_MENU) == true ) {
						Rectangle rect = tree.getUI().getPathBounds(tree, tree.getSelectionPath() );
						if ( rect != null ) {
							Double x = rect.getX();
							Double y = rect.getY();
							Double height = rect.getHeight();
							if ( x != null && y != null && height != null ) {
								Integer xInt = x.intValue();
								Integer yInt = y.intValue();
								Integer heightInt = height.intValue();
								if ( xInt != null && yInt != null && heightInt != null )
									THIS.openContextPopup( xInt, yInt + heightInt );
							}
						}
					} else if ( Objects.equals(keyCode, KeyEvent.VK_UP) == true 
					|| Objects.equals(keyCode, KeyEvent.VK_DOWN) == true ) {
						THIS.refreshTreeAsync();
					}
				}
			};
			tree.addKeyListener( treeKeyListener );

			treeMouseListener = new MouseListener() {
				@Override
				public void mouseClicked( MouseEvent e ) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if ( Objects.equals(e.getButton(), 1) == true ) {
						THIS.selectNodes();

						TreePath treePath = tree.getPathForLocation( e.getX(), e.getY() );
						if ( treePath != null ) {
							if ( THIS.getTreePathClicked() != null ) {
								DefaultMutableTreeNode nodeOpenedNew = ( DefaultMutableTreeNode ) THIS.getTreePathClicked().getLastPathComponent();
								if ( nodeOpenedNew != null ) {
									Note note = THIS.getNote( nodeOpenedNew );
									if ( note != null ) {
										if ( THIS.getNodeOpened() == null || THIS.getNodeOpened() != null && THIS.getNodeOpened().equals(nodeOpenedNew) == false )
											THIS.setNodeOpenedNew( nodeOpenedNew );
									}
								}
							}
						}

					} else if ( Objects.equals(e.getButton(), 2) == true || Objects.equals(e.getButton(), 3) == true )  {
						TreePath treePathNew = tree.getPathForLocation( e.getX(), e.getY() );
						if ( treePathNew != null ) {
							TreePath[] treePathsSelected = tree.getSelectionPaths();
							if ( treePathsSelected != null && treePathsSelected.length > 1 )
								tree.setSelectionPaths( treePathsSelected );
							else
								tree.setSelectionPath( treePathNew );
						}

						THIS.selectNodes();
						THIS.openContextPopup( e.getX(), e.getY() );
					}

					THIS.openNewNode();
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			};
			tree.addMouseListener( treeMouseListener );

			DefaultTreeCellRenderer treeCellRendererListener = new DefaultTreeCellRenderer() {
				@Override
				public Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
					ImageIcon imageIcon = null;
					
					DefaultMutableTreeNode node = ( DefaultMutableTreeNode ) value;
					
					getTreePath( node );
					Path nodePath = getNodePath( node );
					boolean isNodeFolder = false;
					
					try {
						notesRenderedInTreeLock.lock();
						
						DefaultMutableTreeNode folderNode = null;
						String nodePathStr = nodePath.toString();
						Iterator<DefaultMutableTreeNode> foldersNodesRenderedInTreeIt = foldersNodesRenderedInTree.iterator();
						while ( foldersNodesRenderedInTreeIt.hasNext() ) {
							DefaultMutableTreeNode folderNodeFound = foldersNodesRenderedInTreeIt.next();
							if ( folderNodeFound != null ) {
								Path folderNodeFoundPath = getNodePath( folderNodeFound );
								if ( folderNodeFoundPath != null ) {
									if ( folderNodeFoundPath.toString().equals(nodePathStr) == true ) {
										folderNode = folderNodeFound;
										break;
									}
								}
							}
						}
						
						if ( folderNode != null || folderNode == null && isNodeDirectory(node) == true ) {
							isNodeFolder = true;
							
							if ( folderNode == null ) {
								foldersNodesRenderedInTree.add( node );
							}

							imageIcon = images.get_GLOBAL_IMAGE_ICON_FOLDER();
						}
					} finally {
						notesRenderedInTreeLock.unlock();
					}
					
					if ( isNodeFolder == false ) {
						Note note = null;
						
						try {
							notesRenderedInTreeLock.lock();
							
							Iterator<Note> notesRenderedInTreeIt = notesRenderedInTree.iterator();
							while ( notesRenderedInTreeIt.hasNext() ) {
								Note noteFound = notesRenderedInTreeIt.next();
								if ( noteFound != null ) {
									if ( noteFound.getPathAbsolute().equals(nodePath.toString()) == true ) {
										note = noteFound;
										break;
									}
								}
							}

							if ( note == null ) {
								note = getNote( node );
								notesRenderedInTree.add( note );
							}
						} finally {
							notesRenderedInTreeLock.unlock();
						}
						
						if ( note != null && note.getIcon() != null ) {
							if ( note.getIsSaved() == true )
								imageIcon = note.getIcon();
							else {
								imageIcon = images.mergeImagesIcon( images.get_GLOBAL_IMAGE_ICON_NOT_SAVED(), note.getIcon() );
							}
						} else {
							Images icons = new Images();

							if ( note != null && note.getIsSaved() == false ) {
								imageIcon = icons.mergeImagesIcon( icons.get_GLOBAL_IMAGE_ICON_NOT_SAVED(), icons.get_GLOBAL_IMAGE_ICON_FILE() );
							} else {
								imageIcon = icons.get_GLOBAL_IMAGE_ICON_FILE();
							}
						}
					}

					JLabel label = new JLabel( ( String ) node.getUserObject(), imageIcon, 0 );
					if ( nodeOpened != null && nodeOpened.equals(node) ) {
						java.awt.Font font = Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.SizeNormal, FontStyleEnum.Bold))).getFont();
						if ( font == null ) {
							FontSize fontSize = new FontSize( Parameters.getThemeSelected(), FontStyleEnum.SizeNormal );
							font = new java.awt.Font( label.getFont().getFamily(), java.awt.Font.BOLD, fontSize.getSize() );
						}
						label.setFont( font );

						label.setOpaque( true );
						label.setForeground( new Color( ColorTypeEnum.Blue, ColorAttributeTypeEnum.Foreground ).getRgbColor() );
					} else if ( selected == true ) {
						label.setOpaque( true );
						label.setBackground( new Color( ColorTypeEnum.GrayLight, ColorAttributeTypeEnum.Background ).getRgbColor() );
					} else {
						label.setOpaque( false );
					}
					
					return label;
				}
			};
			tree.setCellRenderer( treeCellRendererListener );
			
			tree.getSelectionModel().setSelectionMode( TreeSelectionModel.CONTIGUOUS_TREE_SELECTION );
			tree.setBackground( new Color( ColorTypeEnum.WhiteGhost, ColorAttributeTypeEnum.Background ).getRgbColor() );
			treeView = new JScrollPane( tree );
		}

		panelNoteContent = new JPanel();
		panelNoteContent.setLayout( new BoxLayout( panelNoteContent, BoxLayout.PAGE_AXIS ) );
		panelNoteContent.setName( "file_content" );

		if ( treeView != null ) {
			splitPaneTreeAndNoteContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, treeView, panelNoteContent );
			this.add( splitPaneTreeAndNoteContent );
		}

		display();
	}
	
	public Folder createFolderRoot( FolderRoot folderRoot, Folder folderParent, File file ) {
		Folder folder = null;
		if ( file != null && file.isDirectory() ) {
			if ( folderRoot == null ) {
				folder = new Folder(
					folderParent, 
					file.getName(), 
					new ArrayList<Folder>(), 
					new ArrayList<Note>()
				);
			} else
				folder = folderRoot;
			
			File[] subFiles = file.listFiles();
			int subFilesLength = subFiles.length;
			for ( int i = 0; i < subFilesLength; i++ ) {
				File subFile = subFiles[i];
				if ( subFile.isDirectory() ) {
					folder.getFolders().add(createFolderRoot(null, folder, subFile) );
				} else if ( subFile.getPath().endsWith(Core.get_NOTE_FILE_EXTENSION()) ) {
					String fileContent = files.getContent( subFile, StandardCharsets.UTF_8 );
					if ( fileContent != null ) {
						String header = noteReader.getHeaderContent( fileContent, true );
						if ( header != null ) {
							ParametersSerializer parametersSerializer = new ParametersSerializer();
							HashMap<String, HashMap<String, String>> parameters = parametersSerializer.read( header, false );

							ParametersApplicator parametersApplicator = new ParametersApplicator();
							NoteIconTypeEnum imageIconType = parametersApplicator.getImageIconType( parameters );
							
							String imageIconValue = parametersApplicator.getImageIconValue( parameters );
							
							ImageIcon imageIcon = parametersApplicator.getImageIcon( parameters );
							
							folder.getNotes().add( new Note( 
								folder, 
								subFile.getName(), 
								imageIconType, 
								imageIconValue, 
								imageIcon, 
								fileContent, 
								null
							) );
						}
					}
				}
			}
		}
		return folder;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			this.removeWindowListener( windowAdapter );
		} catch (Exception e) { }
		try {
			this.removeComponentListener( windowComponentListener );
		} catch (Exception e) { }
		
		deleteTreeListeners();
		
		super.finalize();
	}
	
	private void deleteTreeListeners() {
		try {
			tree.removeTreeSelectionListener( treeSelectionListener );
		} catch (Exception e) { }
		try {
			tree.removeKeyListener( treeKeyListener );
		} catch (Exception e) { }
		try {
			tree.removeMouseListener( treeMouseListener );
		} catch (Exception e) { }
	}
	
	//====================
	// end region construct
	//====================
	
	//====================
	// region G & S
	//====================

	public static final String get_ARG_KEY_ROOT_FOLDER_NOTES_FILE() {
		return ARG_KEY_ROOT_FOLDER_NOTES_FILE;
	}

	public static final String get_ARG_KEY_SET_NOTE_OPENED_IS_SAVED() {
		return ARG_KEY_SET_NOTE_OPENED_IS_SAVED;
	}

	public Folder getFolderRoot() {
		return folderRoot;
	}
	
	public void setFolderRoot( FolderRoot folderRoot ) {
		this.folderRoot = folderRoot;
	}
	
	public DefaultMutableTreeNode getNodeOpened() {
		return nodeOpened;
	}

	public void setNodeOpened(DefaultMutableTreeNode nodeOpened) {
		this.nodeOpened = nodeOpened;
	}

	public DefaultMutableTreeNode getNodeOpenedNew() {
		return nodeOpenedNew;
	}

	public void setNodeOpenedNew(DefaultMutableTreeNode nodeOpenedNew) {
		this.nodeOpenedNew = nodeOpenedNew;
	}

	public TreePath getTreePathClicked() {
		return treePathClicked;
	}

	public void setTreePathClicked(TreePath treePathClicked) {
		this.treePathClicked = treePathClicked;
	}
	
	public Note getNoteOpened() {
		return noteOpened;
	}

	public void setNoteOpened(Note noteOpened) {
		this.noteOpened = noteOpened;
	}

	public static HashMap<Integer, HashMap<SimpleAttributeSet, Integer[]>> getCopiedStyle() {
		return copiedStyle;
	}

	public static void setCopiedStyle(HashMap<Integer, HashMap<SimpleAttributeSet, Integer[]>> copiedStyle) {
		Notes.copiedStyle = copiedStyle;
	}
	
	//====================
	// end region G & S
	//====================
	
	//====================
	// region callbacks
	//====================
	
	public void workDirectoryClose( Object[] args ) {
		try {
			boolean isExit = MethodHelper.getArgBoolean(Confirm.get_ARG_KEY_CONFIRM_RESULT(), args );
			if ( isExit == true ) {
				THIS.delete();
				JTextNotes.getViewNotes().remove( (Notes)THIS );
				
				Boolean askForWorkDirectoryIfClose = MethodHelper.getArgBoolean( ARG_KEY_ASK_FOR_WORK_DIRECTORY_IF_CLOSE, args );
				if ( askForWorkDirectoryIfClose != null && askForWorkDirectoryIfClose == true ) {
					if ( Objects.equals(JTextNotes.getViewNotes().size(), 0) == true )
						JTextNotes.createViewWorkDirectory( null );
				}
			} else
				THIS.resume();
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
	}
	
	public void programExit( Object[] args ) {
		try {
			boolean isExit = MethodHelper.getArgBoolean(Confirm.get_ARG_KEY_CONFIRM_RESULT(), args );
			if ( isExit == true )
				JTextNotes.programExit( null );
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
	}
	
	//====================
	// end region callbacks
	//====================
	
	//====================
	// region refresh & messages
	//====================
	
	public void refreshTree() {
		SwingUtilities.updateComponentTreeUI( tree );
	}
	
	public void refreshTreeAsync() {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				refreshTree();
			}
		} );
	}
	
	private void resetMessagesBar() {
		subMessagesBar.removeAll();
		messagesBar.removeAll();
		messagesBarOkMessages.clear();
		messagesBarInfoMessages.clear();
		messagesBarErrorMessages.clear();
		SwingUtilities.updateComponentTreeUI( messagesBar );
		this.remove( messagesBar );
		isMessagesBarReseted = true;
	}
	
	private void resetMessagesBarAsync() {
		if ( isMessagesBarReseted == false ) {
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					resetMessagesBar();
				}
			} );
		}
	}
	
	private void addMessagesBarOk( String message ) {
		if ( subMessagesBar.getComponentCount() > 0 ) {
			JLabel label = new JLabel( " | " );
			subMessagesBar.add( label );
		}
		JLabel label = new JLabel( message );
		label.setIcon( images.get_GLOBAL_IMAGE_ICON_OK() );
		subMessagesBar.add( label );
		isMessagesBarReseted = false;

		messagesBarOkMessages.add( message );
	}
	
	private void addMessagesBarInfo( String message ) {
		if ( subMessagesBar.getComponentCount() > 0 ) {
			JLabel label = new JLabel( " | " );
			subMessagesBar.add( label );
		}
		JLabel label = new JLabel( message );
		label.setIcon( images.get_GLOBAL_IMAGE_ICON_INFO() );
		subMessagesBar.add( label );
		isMessagesBarReseted = false;

		messagesBarInfoMessages.add( message );
	}
	
	private void addMessagesBarError( String message ) {
		if ( subMessagesBar.getComponentCount() > 0 ) {
			JLabel label = new JLabel( " | " );
			subMessagesBar.add( label );
		}
		JLabel label = new JLabel( message );
		label.setIcon( images.get_GLOBAL_IMAGE_ICON_ERROR() );
		subMessagesBar.add( label );
		isMessagesBarReseted = false;

		messagesBarErrorMessages.add( message );
	}
	
	private void refreshMessagesBar() {
		removeStatusBars();
		messagesBar.removeAll();
		messagesBar.add( subMessagesBar );
		this.add( messagesBar, "South" );
		SwingUtilities.updateComponentTreeUI( subMessagesBar );
	}
	
	private void refreshMessagesBarAsync() {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				refreshMessagesBar();
			}
		} );
	}
	
	private void resetStatusBar() {
		subStatusBar.removeAll();
		statusBar.removeAll();
		statusBarMessages.clear();
		SwingUtilities.updateComponentTreeUI( statusBar );
		this.remove( statusBar );
	}
	
	private void resetStatusBarAsync() {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				resetStatusBar();
			}
		} );
	}
	
	private void addStatusBar( String message ) {
		if ( subStatusBar.getComponentCount() > 0 ) {
			JLabel label = new JLabel( " | " );
			subStatusBar.add( label );
		}
		JLabel label = new JLabel( message );
		subStatusBar.add( label );
		
		statusBarMessages.add( message );
	}
	
	private void refreshStatusBar() {
		removeStatusBars();
		statusBar.removeAll();
		statusBar.add( subStatusBar );
		this.add( statusBar, "South" );
		SwingUtilities.updateComponentTreeUI( subStatusBar );
	}
	
	private void refreshStatusBarAsync() {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				refreshStatusBar();
			}
		} );
	}
	
	private void removeStatusBars() {
		this.remove( messagesBar );
		this.remove( statusBar );
	}
	
	//====================
	// end region refresh & messages
	//====================
	
	//====================
	// region create
	//====================
	
	private DefaultMutableTreeNode createNode( Folder folder ) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( folder.getName() );
		
		LinkedList<DefaultMutableTreeNode> directoriesNodes = new LinkedList<>();
		LinkedList<String> directoriesNodesNames = new LinkedList<>();
		
		LinkedList<DefaultMutableTreeNode> filesNodes = new LinkedList<>();
		LinkedList<String> filesNodesNames = new LinkedList<>();
		
		ArrayList<Folder> folderFolders = folder.getFolders();
		Iterator<Folder> folderFoldersIt = folderFolders.iterator();
		while ( folderFoldersIt.hasNext() ) {
			Folder folderFound = folderFoldersIt.next();
			try {
				directoriesNodes.add( createNode(folderFound) );
			} catch (Exception e) {
				Core.get_LOGGER().write( e );
			}
			directoriesNodesNames.add( folderFound.getName() );
		}
		
		ArrayList<Note> folderNotes = folder.getNotes();
		Iterator<Note> folderNotesIt = folderNotes.iterator();
		while ( folderNotesIt.hasNext() ) {
			Note noteFound = folderNotesIt.next();
			if ( noteFound.getName().endsWith(Core.get_NOTE_FILE_EXTENSION()) ) {
				filesNodes.add( new DefaultMutableTreeNode( noteFound.getName() ) );
				filesNodesNames.add( noteFound.getName() );
			}
		}
		
		Collections.sort( directoriesNodesNames );
		Iterator<String> directoriesNodesNamesIt = directoriesNodesNames.iterator();
		while ( directoriesNodesNamesIt.hasNext() ) {
			String nodeNameFound = directoriesNodesNamesIt.next();
			DefaultMutableTreeNode nodeFound = getNodeByName( directoriesNodes, nodeNameFound );
			if ( nodeFound != null ) {
				try {
					node.add( nodeFound );
				} catch (Exception e) {
					Core.get_LOGGER().write( e );
				}
			}
		}
		
		Collections.sort( filesNodesNames );
		
		Iterator<String> filesNodesNamesIt = filesNodesNames.iterator();
		while ( filesNodesNamesIt.hasNext() ) {
			String nodeNameFound = filesNodesNamesIt.next();
			DefaultMutableTreeNode nodeFound = getNodeByName( filesNodes, nodeNameFound );
			if ( nodeFound != null ) {
				try {
					node.add( nodeFound );
				} catch (Exception e) {
					Core.get_LOGGER().write( e );
				}
			}
		}
		return node;
	}
	
	private Folder createFolderFromFolder( Folder folderParent, Folder folder, String folderNameNew, JProgressBar progressBar ) {
		Folder folderNew = null;
		
		String pathFolderStr = Paths.get( folderParent.getPathAbsolute(true), folderNameNew ).toString();
		if ( files.createDirectory(pathFolderStr) == true ) {
			folderNew = new Folder(
				folderParent, 
				folderNameNew, 
				null, 
				null
			);
			
			DefaultMutableTreeNode folderParentNode = getNode( folderParent );
			DefaultMutableTreeNode folderNewNode = null;
			if ( folderParentNode != null ) {
				folderNewNode = new DefaultMutableTreeNode(folderNew.getName());
				folderParentNode.add( folderNewNode );
			}

			ArrayList<Note> folderNewNotesNew = new ArrayList<>();

			Iterator<Note> folderNotesIt = folder.getNotes().iterator();
			while ( folderNotesIt.hasNext() ) {
				Note folderNote = folderNotesIt.next();

				String pathNoteStr = Paths.get( folderNew.getPathAbsolute(true), folderNote.getName() ).toString();
				if ( files.writeContent(pathNoteStr, folderNote.getContent(), StandardCharsets.UTF_8 ) == true ) {
					Note noteNew = new Note( 
						folderNew, 
						folderNote.getName(), 
						folderNote.getIconType(), 
						folderNote.getIconValue(), 
						( folderNote.getIconType() != null && folderNote.getIconValue() != null ) ? images.createImageIcon( folderNote.getIconType(), folderNote.getIconValue() ) : null, 
						folderNote.getContent(), 
						null
					);
					folderNewNotesNew.add( noteNew );
					
					if ( folderNewNode != null )
						folderNewNode.add( new DefaultMutableTreeNode(noteNew.getName()) );
					
					if ( progressBar != null )
						progressBar.setValue( progressBar.getValue() + 1 );
				}
			}
			folderNew.setNotes( folderNewNotesNew );

			ArrayList<Folder> folderNewFoldersNew = new ArrayList<>();

			Iterator<Folder> folderFoldersIt = folder.getFolders().iterator();
			while ( folderFoldersIt.hasNext() ) {
				Folder folderFolder = folderFoldersIt.next();
				folderNewFoldersNew.add( createFolderFromFolder(folderNew, folderFolder, folderFolder.getName(), progressBar) );
			}
			folderNew.setFolders( folderNewFoldersNew );
		}
		return folderNew;
	}
	
	public void promptNewFolder() {
		resetMessagesBar();
		final Notes THIS = this;
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
				DefaultMutableTreeNode nodeDirectoryFound = null;

				while ( nodesSelectedIt.hasNext() ) {
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					if ( isNodeDirectory(nodeSelected) == true ) {
						nodeDirectoryFound = nodeSelected;
						break;
					}
				}

				if ( nodeDirectoryFound != null ) {
					Class c = THIS.getClass();

					HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
					HashMap<Class, String[]> callbacksMap = new HashMap<>();
					callbacksMap.put( c, new String[]{"newFolder"} );
					callbacksObjMap.put( THIS, callbacksMap );

					Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

					try {
						MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
						MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_MESSAGE(), LANG.get_LANG_PROGRAM().get_PROMPT_NEW_FOLDER(), argsNew );
						MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_LABEL(), LANG.get_LANG().get_NAME(), argsNew );
						MethodHelper.addOrReplaceCallbackArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, nodeDirectoryFound, argsNew );
						
						Prompt prompt = new Prompt( LANG.get_LANG_PROGRAM().get_NEW_FOLDER(), argsNew );
						prompt.resume();
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}

				} else {
					addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_A_FOLDER_TO_ACTION() );
					refreshMessagesBarAsync();
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void newFolder( Object[] args ) {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				String name = null;
				try {
					name = MethodHelper.getArgString( Prompt.get_ARG_KEY_PROMPT_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( name != null ) {// if null, action was canceled by user
					if ( name.trim().isEmpty() == false ) {
						if ( name.length() <= Core.get_INPUT_VALUE_MAX_LENGTH() ) {
							boolean isCharsAllowed = LANG.get_LANG_PROGRAM().isStringCharsAllowed( name );
							name = LANG.get_LANG_PROGRAM().parseToAllowedString( name );

							if ( name.trim().isEmpty() == false ) {
								DefaultMutableTreeNode nodeParent = null;
								try {
									nodeParent = (DefaultMutableTreeNode) MethodHelper.getArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, args );
								} catch (Re01JLibException ex) {
									Core.get_LOGGER().write( ex );
								}
								if ( nodeParent != null ) {
									DefaultMutableTreeNode existingNode = getNodeChildrenByIname( nodeParent, name );

									if ( existingNode == null ) {
										Folder folderParent = getFolder( nodeParent );
										if ( folderParent != null ) {
											Folder existingFolder = getFolderByName( folderParent, name );
											if ( existingFolder == null ) {
												if ( files.isDirectoryExist(Paths.get( folderParent.getPathAbsolute(true), name ).toString()) == false ) {
													if ( isCharsAllowed == false ) {
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_EVERY_CHARS_NOT_ALLOWED() );
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_FOLDER_ALREADY_EXIST() );
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_EVERY_CHARS_NOT_ALLOWED() );
													}

													createNewFolder( folderParent, name, nodeParent );
													
													if ( nodeParent.equals(nodeRoot) == true ) {
														TreePath treePathRoot = getTreePath( nodeRoot );
														tree.expandPath( treePathRoot );
													}
												} else
													addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_FOLDER_ALREADY_EXIST() );
											} else
												addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_FOLDER_ALREADY_EXIST() );
										}
									} else
										addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_FOLDER_ALREADY_EXIST() );
								}
							} else
								addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
						} else
							addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_TO_HIGH());
					} else
						addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
				}
				
				refreshMessagesBarAsync();
				refreshTreeAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	private void createNewFolder( Folder folderParent, String name, DefaultMutableTreeNode nodeParent ) {
		String pathStr = Paths.get( folderParent.getPathAbsolute(true), name ).toString();
		if ( files.createDirectory(pathStr) == true ) {
			folderParent.getFolders().add( new Folder(
				folderParent, 
				name, 
				new ArrayList<Folder>(), 
				new ArrayList<Note>()
			) );
			nodeParent.add( new DefaultMutableTreeNode(name) );
		} else
			addMessagesBarError( LANG.get_LANG().get_FOLDER_NOT_CREATED() );
	}
	
	public void promptNewNote() {
		resetMessagesBar();
		final Notes THIS = this;
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();

				DefaultMutableTreeNode nodeDirectoryFound = null;

				while ( nodesSelectedIt.hasNext() ) {
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					if ( isNodeDirectory(nodeSelected) == true ) {
						nodeDirectoryFound = nodeSelected;
						break;
					}
				}

				if ( nodeDirectoryFound != null ) {
					Class c = THIS.getClass();

					HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
					HashMap<Class, String[]> callbacksMap = new HashMap<>();
					callbacksMap.put( c, new String[]{"newNote"} );
					callbacksObjMap.put( THIS, callbacksMap );

					Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

					try {
						MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
						MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_MESSAGE(), LANG.get_LANG_PROGRAM().get_PROMPT_NEW_NOTE(), argsNew );
						MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_LABEL(), LANG.get_LANG().get_NAME(), argsNew );
						MethodHelper.addOrReplaceCallbackArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, nodeDirectoryFound, argsNew );
						
						Prompt prompt = new Prompt( LANG.get_LANG_PROGRAM().get_NEW_NOTE(), argsNew );
						prompt.resume();
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				} else {
					addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_A_FOLDER_TO_ACTION() );
					refreshMessagesBarAsync();
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void newNote( Object[] args ) throws Re01JLibException {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				String name = null;
				try {
					name = MethodHelper.getArgString( Prompt.get_ARG_KEY_PROMPT_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( name != null ) {// if null, action was canceled by user
					if ( name.trim().isEmpty() == false ) {
						if ( name.length() <= Core.get_INPUT_VALUE_MAX_LENGTH() ) {
							if ( name.endsWith(Core.get_NOTE_FILE_EXTENSION()) == false )
								name += Core.get_NOTE_FILE_EXTENSION();

							boolean isCharsAllowed = LANG.get_LANG_PROGRAM().isStringCharsAllowed( name );
							name = LANG.get_LANG_PROGRAM().parseToAllowedString( name );

							if ( name.trim().isEmpty() == false ) {
								DefaultMutableTreeNode nodeParent = null;
								try {
									nodeParent = (DefaultMutableTreeNode) MethodHelper.getArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, args );
								} catch (Re01JLibException ex) {
									Core.get_LOGGER().write( ex );
								}
								if ( nodeParent != null ) {
									DefaultMutableTreeNode existingNode = getNodeChildrenByIname( nodeParent, name );

									if ( existingNode == null ) {
										Folder folderParent = getFolder( nodeParent );
										if ( folderParent != null ) {
											Note existingNote = getNoteByName( folderParent, name );
											if ( existingNote == null ) {
												if ( files.isFileExists(Paths.get( folderParent.getPathAbsolute(true), name ).toString()) == false ) {
													if ( isCharsAllowed == false )
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_EVERY_CHARS_NOT_ALLOWED() );

													createNewNote( folderParent, name, nodeParent );
													
													if ( nodeParent.equals(nodeRoot) == true ) {
														TreePath treePathRoot = getTreePath( nodeRoot );
														tree.expandPath( treePathRoot );
													}
												} else
													addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_NOTE_ALREADY_EXIST() );
											} else
												addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_NOTE_ALREADY_EXIST() );
										}
									} else
										addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_NOTE_ALREADY_EXIST() );
								}
							} else
								addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
						} else
							addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_TO_HIGH());
					} else
						addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
				}
				
				refreshMessagesBarAsync();
				refreshTreeAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	private void createNewNote( Folder folder, String name, DefaultMutableTreeNode nodeParent ) {
		String content = noteReader.createHeaderContent( null );
		content += noteReader.createBodyContent( null );
		
		String pathStr = Paths.get( folder.getPathAbsolute(true), name ).toString();
		if ( files.writeContent(pathStr, content, StandardCharsets.UTF_8) == true ) {
			folder.getNotes().add( new Note( 
				folder, 
				name, 
				null, 
				null, 
				null, 
				content, 
				null
			) );
			nodeParent.add( new DefaultMutableTreeNode(name) );
		} else
			addMessagesBarError( LANG.get_LANG().get_FILE_NOT_CREATED() );
	}
	
	public void promptRenameItem() throws Re01JLibException {
		resetMessagesBar();
		final Notes THIS = this;
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Loading viewLoading1 = null;
				try {
					Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
					viewLoading1 = new Loading( LANG.get_LANG().get_RENAME(), argsNewLoading1 );
					viewLoading1.resume();
				} catch ( Re01JLibException ex ) {
					Core.get_LOGGER().write( ex );
				}
				
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
				Integer nodesSelectedLength = nodesSelected.size();
				if ( Objects.equals(nodesSelectedLength, 1) == true ) {
					DefaultMutableTreeNode nodeFound = null;

					while ( nodesSelectedIt.hasNext() ) {
						DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
						nodeFound = nodeSelected;
					}

					if ( nodeFound != null ) {
						boolean isNodeDirectory = isNodeDirectory( nodeFound );

						Class c = THIS.getClass();

						HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
						HashMap<Class, String[]> callbacksMap = new HashMap<>();
						
						callbacksMap.put( c, new String[]{"renameItem"} );
						callbacksObjMap.put( THIS, callbacksMap );

						Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

						try {
							MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
							MethodHelper.addOrReplaceCallbackArg( 
								Prompt.get_ARG_KEY_PROMPT_MESSAGE(), 
								( isNodeDirectory == true ) ? LANG.get_LANG_PROGRAM().get_PROMPT_RENAME_FOLDER() : LANG.get_LANG_PROGRAM().get_PROMPT_RENAME_NOTE(), 
								argsNew 
							);
							MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_LABEL(), LANG.get_LANG().get_NAME(), argsNew );

							StringHelper stringHelper = new StringHelper();
							String nodeName = nodeFound.getUserObject().toString();
							int nodeNameLength = nodeName.length();
							if ( nodeName.endsWith(Core.get_NOTE_FILE_EXTENSION()) )
								nodeName = stringHelper.removeCharsAt( nodeName, nodeNameLength - Core.get_NOTE_FILE_EXTENSION().length(), nodeNameLength );

							MethodHelper.addOrReplaceCallbackArg( Prompt.get_ARG_KEY_PROMPT_INPUT(), nodeName, argsNew );
							MethodHelper.addOrReplaceCallbackArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, nodeFound, argsNew );
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}

						try {
							Prompt prompt = new Prompt( 
								( isNodeDirectory == true ) ? LANG.get_LANG_PROGRAM().get_RENAME_FOLDER() : LANG.get_LANG_PROGRAM().get_RENAME_NOTE(),
								argsNew
							);
							prompt.resume();
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}

					} else {
						addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_A_FOLDER_TO_ACTION() );
					}
				} else
					addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION() );
				
				if ( viewLoading1 != null )
					viewLoading1.delete();
				
				refreshMessagesBarAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void renameItem( Object[] args ) {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Loading viewLoading1 = null;
				try {
					Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
					viewLoading1 = new Loading( LANG.get_LANG().get_RENAME(), argsNewLoading1 );
					viewLoading1.resume();
				} catch ( Re01JLibException e ) {
					Core.get_LOGGER().write( e );
				}
				
				String newName = null;
				try {
					newName = MethodHelper.getArgString( Prompt.get_ARG_KEY_PROMPT_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( newName != null ) {// if null, action was canceled by user
					if ( newName.trim().isEmpty() == false ) {
						if ( LANG.get_LANG_PROGRAM().isStringCharsAllowed(newName) == false ) {
							addMessagesBarError( LANG.get_LANG_PROGRAM().get_EVERY_CHARS_NOT_ALLOWED() );
						}
						newName = LANG.get_LANG_PROGRAM().parseToAllowedString( newName );

						if ( newName.trim().isEmpty() == false ) {
							if ( newName.length() <= Core.get_INPUT_VALUE_MAX_LENGTH() ) {

								DefaultMutableTreeNode node = null;
								try {
									node = (DefaultMutableTreeNode) MethodHelper.getArg( ARG_KEY_NODE_PARENT_OF_NEW_NODE, args );
								} catch (Re01JLibException ex) {
									Core.get_LOGGER().write( ex );
								}
								
								if ( node != null ) {
									boolean isNodeDirectory = isNodeDirectory( node );
									if ( isNodeDirectory == false && newName.endsWith(Core.get_NOTE_FILE_EXTENSION()) == false )
										newName += Core.get_NOTE_FILE_EXTENSION();
									
									DefaultMutableTreeNode nodeParent = ( DefaultMutableTreeNode ) node.getParent();
									if ( nodeParent != null ) {
										
										Folder folderParent = getFolder( nodeParent );
										if ( folderParent != null ) {
											String nodeName = node.getUserObject().toString();

											Folder existingFolder = null;
											Note existingNote = null;
											if ( isNodeDirectory == true )
												existingFolder = getFolderByName( folderParent, nodeName );
											else
												existingNote = getNoteByName( folderParent, nodeName );

											if ( existingFolder != null || existingNote != null ) {
												String pathStrNew = Paths.get( folderParent.getPathAbsolute(true), newName ).toString();

												if ( isNodeDirectory == true && files.isDirectoryExist(pathStrNew) == false 
												|| isNodeDirectory == false && files.isFileExists(pathStrNew) == false ) {

													String pathStr = Paths.get( folderParent.getPathAbsolute(true), nodeName ).toString();

													if ( isNodeDirectory == true && files.isDirectoryExist(pathStr) == true 
													|| isNodeDirectory == false && files.isFileExists(pathStr) == true ) {
														renameItem( folderParent, newName, node, nodeParent );
													} else
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_FOLDER_DO_NOT_EXIST() );
												} else {
													if ( isNodeDirectory == true )
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_FOLDER_ALREADY_EXIST() );
													else
														addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEW_NOTE_ALREADY_EXIST() );
												}
											} else
												addMessagesBarError( LANG.get_LANG_PROGRAM().get_FOLDER_DO_NOT_EXIST() );
										}
									} else
										addMessagesBarError( LANG.get_LANG_PROGRAM().get_CAN_NOT_RENAME_ITEM() );
								} else
									addMessagesBarError( LANG.get_LANG_PROGRAM().get_CAN_NOT_RENAME_ITEM() );
							} else
								addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_TO_HIGH());
						} else
							addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
					} else
						addMessagesBarError( LANG.get_LANG().get_TEXT_NAME_IS_EMPTY() );
				}
				
				if ( viewLoading1 != null )
					viewLoading1.delete();
				
				refreshTreeAsync();
				refreshMessagesBarAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	private void renameItem( Folder folderParent, String newName, DefaultMutableTreeNode node, DefaultMutableTreeNode nodeParent ) {
		boolean isNodeDirectory = isNodeDirectory( node );
		Path path = Paths.get( folderParent.getPathAbsolute(true), node.getUserObject().toString() );
		String pathStr = path.toString();
		if ( isNodeDirectory == true && files.isDirectoryExist(pathStr) == true 
		|| isNodeDirectory == false && files.isFileExists(pathStr) == true ) {
			String newPathStr = Paths.get( folderParent.getPathAbsolute(true), newName ).toString();
			if ( files.rename( pathStr, newPathStr ) == true ) {
				if ( isNodeDirectory == true ) {
					Folder folder = getFolder( folderParent, path );
					if ( folder != null ) {
						Folder newFolder = new Folder(
							folderParent, 
							newName, 
							folder.getFolders(), 
							folder.getNotes()
						);
						changeFolderItems( folder, newFolder );
						if ( folderParent.getFolders().add(newFolder) ) {
							if ( folderParent.getFolders().remove(folder) ) {
								nodeParent.remove( node );
								nodeParent.add( createNode(newFolder) );
							}
						}
					}
				} else {
					Note note = getNote( folderParent, path );
					if ( note != null ) {
						if ( folderParent.getNotes().add(new Note( 
							folderParent, 
							newName, 
							note.getIconType(), 
							note.getIconValue(), 
							note.getIcon(), 
							note.getContent(), 
							note.getPane()
						)) ) {
							if ( folderParent.getNotes().remove(note) ) {
								nodeParent.remove( node );
								nodeParent.add( new DefaultMutableTreeNode(newName) );
							}
						}
					}
				}
			} else
				addMessagesBarError( LANG.get_LANG().get_ITEM_NOT_RENAMED() );
		}
	}
	
	private void changeFolderItems( Folder folder, Folder folderParentNew ) {
		Iterator<Folder> foldersIt = folder.getFolders().iterator();
		while ( foldersIt.hasNext() ) {
			Folder folderFound = foldersIt.next();
			folderFound.setFolderParent( folderParentNew );
		}
		Iterator<Note> notesIt = folder.getNotes().iterator();
		while ( notesIt.hasNext() ) {
			Note noteFound = notesIt.next();
			noteFound.setFolder( folderParentNew );
		}
	}
	
	private void createContent() throws Re01JLibException {
		if ( noteOpened != null ) {
			StringHelper stringHelper = new StringHelper();
			
			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					ToolTipManager.sharedInstance().setDismissDelay(300000);
					ToolTipManager.sharedInstance().setInitialDelay(20);
					
					super.mouseEntered( e );
				}
				@Override
				public void mouseExited(MouseEvent e) {
					ToolTipManager.sharedInstance().setDismissDelay(TOOLTIP_DISMISS_TIMEOUT);
					
					super.mouseExited( e );
				}
			};
			
			final Notes NOTES = this;
			Class c = NOTES.getClass();
			
			HashMap<Object, HashMap<Class, String[]>> callbacksObjMapNoteOpenedIsSaved = new HashMap<Object, HashMap<Class, String[]>>();
			HashMap<Class, String[]> callbacksMapNoteOpenedIsSaved = new HashMap<>();
			callbacksMapNoteOpenedIsSaved.put( c, new String[]{"setNoteOpenedIsSaved"} );
			callbacksObjMapNoteOpenedIsSaved.put( NOTES, callbacksMapNoteOpenedIsSaved );
			
			Object[] argsNewNoteOpenedIsSaved = new Object[]{ MethodHelper.createDefaultArgs() };
			MethodHelper.addOrReplaceCallbackArg( ARG_KEY_SET_NOTE_OPENED_IS_SAVED, false, argsNewNoteOpenedIsSaved );
			MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMapNoteOpenedIsSaved, argsNewNoteOpenedIsSaved );

			HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
			HashMap<Class, String[]> callbacksMap = new HashMap<>();
			callbacksMap.put( c, new String[]{"createCopiedStyle"} );
			callbacksObjMap.put( NOTES, callbacksMap );
			
			Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
			MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
			MethodHelper.addOrReplaceCallbackArg( JTextPane.get_ARG_KEY_CALLBACKS_SET_NOTE_OPENED_IS_SAVED(), argsNewNoteOpenedIsSaved, argsNew );

			JTextPane pane;
			if ( noteOpened.getPane() == null ) {
				pane = (JTextPane) noteReader.parseToPane( 
					noteOpened.getContent(), 
					new JTextPane( 
						"", 
						Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(), ColorTypeEnum.Black, ColorTypeEnum.White), 
						true, 
						true, 
						argsNew
					)
				);
				noteOpened.setPane( pane );
				
				pane.addKeyListener( new KeyListener() {
					KeyboardHelper keyboardHelper = new KeyboardHelper();
					
					@Override
					public void keyTyped(KeyEvent e) {
						if ( e.isControlDown() == false )
							setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					}

					@Override
					public void keyPressed(KeyEvent e) {
						Integer keyCode = e.getKeyCode();
						
						if ( Objects.equals(keyCode, KeyEvent.VK_C) && e.isControlDown() == true ) {
							try {
								MethodHelper.addOrReplaceCallbackArg( JTextPane.get_ARG_KEY_COPY_STYLE_VALUE(), null, pane.getCallbacksArgs() );
								MethodHelper.executeCallbacks( pane.getCallbacksArgs() );
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else if ( Objects.equals(keyCode, KeyEvent.VK_B) && e.isControlDown() == true ) {
							try {
								pane.addStyle( FontStyleEnum.Bold, null );
								setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else if ( Objects.equals(keyCode, KeyEvent.VK_U) && e.isControlDown() == true ) {
							try {
								pane.addStyle( FontStyleEnum.Underline, null );
								setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else if ( Objects.equals(keyCode, KeyEvent.VK_I) && e.isControlDown() == true ) {
							try {
								pane.addStyle( FontStyleEnum.Italic, null );
								setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
						} else if ( Objects.equals(keyCode, KeyEvent.VK_F) && e.isControlDown() == true ) {
							panelSearchInNote.setVisible( true );
							inputSearchInNote.requestFocus();
						} else if ( e.isControlDown() == true && Objects.equals(keyCode, KeyEvent.VK_Z) 
						|| e.isControlDown() == true && Objects.equals(keyCode, KeyEvent.VK_Y) )
							setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					}

					@Override
					public void keyReleased(KeyEvent e) {
						pane.setSelectionStartIndex( pane.getSelectionStart() );
						if ( keyboardHelper.isKeyCodeNonCharKey(e.getKeyCode()) == false )
							resetMessagesBarAsync();
					}
				} );

				pane.addMouseListener( new MouseInputListener() {
					@Override
					public void mouseClicked(MouseEvent e) {

					}

					@Override
					public void mousePressed(MouseEvent e) {

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						pane.setSelectionStartIndex( pane.getSelectionStart() );
					}

					@Override
					public void mouseEntered(MouseEvent e) {

					}

					@Override
					public void mouseExited(MouseEvent e) {

					}

					@Override
					public void mouseDragged(MouseEvent e) {

					}

					@Override
					public void mouseMoved(MouseEvent e) {

					}
				} );
			} else 
				pane = noteOpened.getPane();
			
			JMenuBar jmenuBar = new JMenuBar();
			jmenuBar.setMaximumSize(new Dimension( Integer.MAX_VALUE, 24 ));
			
			String textNoteIcon =	LANG.get_LANG_PROGRAM().get_NOTE_ICON();
			String textNoteIconShort =	LANG.get_LANG_PROGRAM().get_NOTE_ICON_SHORT();
			String textBold = stringHelper.getFirstCharAsString( LANG.get_LANG().get_BOLD() );
			String textItalic = stringHelper.getFirstCharAsString( LANG.get_LANG().get_ITALIC() );
			String textUnderline = stringHelper.getFirstCharAsString( LANG.get_LANG().get_UNDERLINE() );
			String textSizeNormal = stringHelper.getFirstCharAsString( LANG.get_LANG().get_TITLE_0() ) + "0";
			String textTitle1 = stringHelper.getFirstCharAsString( LANG.get_LANG().get_TITLE_1() ) + "1";
			String textTitle2 = stringHelper.getFirstCharAsString( LANG.get_LANG().get_TITLE_2() ) + "2";
			String textTitle3 = stringHelper.getFirstCharAsString( LANG.get_LANG().get_TITLE_3() ) + "3";
			String textColorBackground = LANG.get_LANG().get_FONT_COLOR_BACKGROUND();
			String textColorBackgroundShort = LANG.get_LANG().get_FONT_COLOR_BACKGROUND_SHORT();
			String textColorForeground = LANG.get_LANG().get_FONT_COLOR_FOREGROUND();
			String textColorForegroundShort = LANG.get_LANG().get_FONT_COLOR_FOREGROUND_SHORT();
			String textDeleteStyle = LANG.get_LANG().get_DELETE_STYLE();
			
			ArrayList<Color> colorsTreeItem = new ArrayList<Color>();
			ArrayList<Color> colorsBackground = new ArrayList<Color>();
			ArrayList<Color> colorsForeground = colors.get_COLORS_ALLOWED() ;
			colorsTreeItem.addAll( colorsForeground );
			colorsBackground.add( null );
			colorsBackground.addAll( colorsForeground );
			
			ArrayList<ImageIcon> imagesIconsTreeItem = new ArrayList<ImageIcon>();
			imagesIconsTreeItem.add( images.get_GLOBAL_IMAGE_ICON_FILE() );
			imagesIconsTreeItem.add( images.get_GLOBAL_IMAGE_ICON_OK() );
			imagesIconsTreeItem.add( images.get_GLOBAL_IMAGE_ICON_ERROR() );
			imagesIconsTreeItem.add( images.get_GLOBAL_IMAGE_ICON_CAUTION() );
			
			JLabel labelColorsTreeItem = new JLabel( textNoteIconShort );
			labelColorsTreeItem.setToolTipText( textNoteIcon );
			labelColorsTreeItem.addMouseListener( mouseAdapter );
			
			JComboBoxNoteOpened comboboxIconTreeSelectedItem = new JComboBoxNoteOpened( JComboBoxTypeEnum.ColorsOrImagesIcons, Parameters.getIconsSize() * 2 + Parameters.get_RECOMMENDED_COMPONENT_MARGIN_WIDTH() );
			comboboxIconTreeSelectedItem.addItemsImagesIcons( imagesIconsTreeItem );
			comboboxIconTreeSelectedItem.addItemsColors( colorsTreeItem );
			comboboxIconTreeSelectedItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBoxNoteOpened comboBox = (JComboBoxNoteOpened) e.getSource();
					
					Integer colorSelectedInt = null;
					try {
						colorSelectedInt = (Integer) comboBox.getSelectedItem();
					} catch ( Exception ex ) { }
					
					if ( colorSelectedInt != null ) {
						Color colorSelected = colors.getColorByRgbInt( colorSelectedInt );
						if ( colorSelected != null ) {
							String iconValue = colorSelected.getColorType().toString();
							setNoteHeaderParameter( parameterReader.get_PARAMETER_HEADER_ICON_TYPE(), NoteIconTypeEnum.ColorImg.toString().toLowerCase() );
							setNoteHeaderParameter( parameterReader.get_PARAMETER_HEADER_ICON_VALUE(), iconValue );
							noteOpened.setIconType( NoteIconTypeEnum.ColorImg );
							noteOpened.setIconValue( iconValue );
							noteOpened.setIcon( images.createImageIcon( false, colorSelected.getRgbColor() ) );
						}
					} else {
						ImageIcon imageIconSelected = null;
						try {
							imageIconSelected = (ImageIcon) comboBox.getSelectedItem();
						} catch ( Exception ex ) { }
						
						if ( imageIconSelected != null ) {
							String iconValue = imageIconSelected.getDescription();
							ImageTypeEnum iconType = null;
							ImageTypeEnum[] iconTypes = ImageTypeEnum.values();
							for ( int i = 0; i < iconTypes.length; i++ ) {
								ImageTypeEnum iconTypeFound = iconTypes[i];
								if ( iconTypeFound.toString().toLowerCase().equals(iconValue.toLowerCase()) == true ) {
									iconType = iconTypeFound;
									break;
								}
							}
							if ( iconType != null ) {
								setNoteHeaderParameter( parameterReader.get_PARAMETER_HEADER_ICON_TYPE(), NoteIconTypeEnum.G2dImg.toString() );
								setNoteHeaderParameter( parameterReader.get_PARAMETER_HEADER_ICON_VALUE(), iconType.toString() );
								noteOpened.setIconType( NoteIconTypeEnum.G2dImg );
								noteOpened.setIconValue( iconType.toString() );
								
								noteOpened.setIcon( images.getImage(iconType) );
							}
						}
					}
					setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					refreshTree();
				}
			} );
			
			JButtonNoteOpened buttonBold = new JButtonNoteOpened( textBold );
			buttonBold.setPreferredSize( new Dimension(Parameters.getIconsSize(), Parameters.getIconsSize()) );
			buttonBold.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Bold, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonItalic = new JButtonNoteOpened( textItalic );
			buttonItalic.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Italic, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonUnderline = new JButtonNoteOpened( textUnderline );
			buttonUnderline.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Underline, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonSizeNormal = new JButtonNoteOpened( textSizeNormal );
			buttonSizeNormal.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.SizeNormal, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonTitle1 = new JButtonNoteOpened( textTitle1 );
			buttonTitle1.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Title1, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonTitle2 = new JButtonNoteOpened( textTitle2 );
			buttonTitle2.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Title2, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JButtonNoteOpened buttonTitle3 = new JButtonNoteOpened( textTitle3 );
			buttonTitle3.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.addStyle( FontStyleEnum.Title3, null );
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			JLabel labelColorForeground = new JLabel( textColorForegroundShort );
			labelColorForeground.setToolTipText( textColorForeground );
			labelColorForeground.addMouseListener( mouseAdapter );
			
			JComboBoxNoteOpened comboboxColorsListForeground = new JComboBoxNoteOpened( JComboBoxTypeEnum.Colors, Parameters.getIconsSize() * 2 + Parameters.get_RECOMMENDED_COMPONENT_MARGIN_WIDTH() );
			comboboxColorsListForeground.addItemsColors( colorsForeground );
			comboboxColorsListForeground.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBoxNoteOpened comboBox = (JComboBoxNoteOpened) e.getSource();
					Integer colorSelectedInt = (Integer) comboBox.getSelectedItem();
					
					Color colorSelected = ( colorSelectedInt != null ) ? colors.getColorByRgbInt( colorSelectedInt ) : new Color( ColorTypeEnum.Transparent, ColorAttributeTypeEnum.Foreground ) ;
					if ( colorSelected != null ) {
						colorSelected.setColorAttributeType( ColorAttributeTypeEnum.Foreground );
						try {
							pane.addStyle( null, colorSelected );
							setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
					}
				}
			} );
			
			JLabel labelColorBackground = new JLabel( textColorBackgroundShort );
			labelColorBackground.setToolTipText( textColorBackground );
			labelColorBackground.addMouseListener( mouseAdapter );
			
			JComboBoxNoteOpened comboboxColorsListBackground = new JComboBoxNoteOpened( JComboBoxTypeEnum.Colors, Parameters.getIconsSize() * 2 + Parameters.get_RECOMMENDED_COMPONENT_MARGIN_WIDTH() );
			comboboxColorsListBackground.addItemsColors( colorsBackground );
			comboboxColorsListBackground.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBoxNoteOpened comboBox = (JComboBoxNoteOpened) e.getSource();
					Integer colorSelectedInt = (Integer) comboBox.getSelectedItem();
					
					Color colorSelected = ( colorSelectedInt != null ) ? colors.getColorByRgbInt( colorSelectedInt ) : new Color( ColorTypeEnum.Transparent, ColorAttributeTypeEnum.Background ) ;
					if ( colorSelected != null ) {
						colorSelected.setColorAttributeType( ColorAttributeTypeEnum.Background );
						try {
							pane.addStyle( null, colorSelected );
							setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
						} catch (Re01JLibException ex) {
							Core.get_LOGGER().write( ex );
						}
					}
				}
			} );
			
			JButtonNoteOpened buttonDeleteStyle = new JButtonNoteOpened( "X" );
			buttonDeleteStyle.setToolTipText( textDeleteStyle );
			buttonDeleteStyle.addMouseListener( mouseAdapter );
			buttonDeleteStyle.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						pane.deleteStyle();
						setNoteOpenedIsSaved( argsNewNoteOpenedIsSaved );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			
			jmenuBar.add( labelColorsTreeItem );
			jmenuBar.add( comboboxIconTreeSelectedItem );
			jmenuBar.add( buttonBold );
			jmenuBar.add( buttonItalic );
			jmenuBar.add( buttonUnderline );
			jmenuBar.add( buttonSizeNormal );
			jmenuBar.add( buttonTitle1 );
			jmenuBar.add( buttonTitle2 );
			jmenuBar.add( buttonTitle3 );
			jmenuBar.add( labelColorForeground );
			jmenuBar.add( comboboxColorsListForeground );
			jmenuBar.add( labelColorBackground );
			jmenuBar.add( comboboxColorsListBackground );
			jmenuBar.add( buttonDeleteStyle );
			
			panelNoteContent.add(jmenuBar);

			JScrollPane fileContentView = new JScrollPane( pane );
			
			panelNoteContent.add(fileContentView);
			
			//====================
			// region search in note
			//====================
			
			ImageIcon imageIconSearchInNote = images.get_GLOBAL_IMAGE_ICON_SEARCH();
			
			panelSearchInNote = new JPanel();
			panelSearchInNote.setLayout( new BoxLayout( panelSearchInNote, BoxLayout.X_AXIS ) );
			
			JLabel labelSearchInNoteIcon = new JLabel( imageIconSearchInNote );
			panelSearchInNote.add( labelSearchInNoteIcon );
			
			inputSearchInNote = new JTextField();
			inputSearchInNote.addKeyListener( new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyPressed(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					if ( Objects.equals(e.getKeyCode(), KeyEvent.VK_ESCAPE) == true )
						panelSearchInNote.setVisible( false );
				}
			} );
			
			panelSearchInNote.add( inputSearchInNote );
			
			JButton buttonValidSearchInNote = new JButton( LANG.get_LANG().get_SEARCH() );
			buttonValidSearchInNote.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pane.search( inputSearchInNote.getText() );
					resetStatusBar();
					addStatusBar(
						LANG.get_LANG().get_RESULT() + 
						" " + 
						pane.getSearchFoundQtyPosition() + 
						"/" + 
						pane.getSearchFoundQty() + 
						" " + 
						LANG.get_LANG().get_OF().toLowerCase() + 
						" \"" + 
						inputSearchInNote.getText() + 
						"\"" 
					);
					refreshStatusBarAsync();
				}
			} );
			panelSearchInNote.add( buttonValidSearchInNote );
			
			panelSearchInNote.setMaximumSize(new Dimension(desktopHelper.get_DESKTOP_WIDTH(), 32));
			panelSearchInNote.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
			panelSearchInNote.setAlignmentX( CENTER_ALIGNMENT );
			
			panelNoteContent.add( panelSearchInNote );
			panelSearchInNote.setVisible( false );
			
			//====================
			// end region search in note
			//====================
		}
	}
	
	public void createCopiedStyle( Object[] args ) {
		try {
			Notes.copiedStyle = ( HashMap<Integer, HashMap<SimpleAttributeSet, Integer[]>> ) MethodHelper.getArg( re01.design.view.swing.JTextPane.get_ARG_KEY_COPY_STYLE_VALUE(), args );
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
	}
	
	//====================
	// end region create
	//====================
	
	//====================
	// region get
	//====================
	
	private TreePath getTreePath( DefaultMutableTreeNode node ) {
		TreePath treePath = null;
		TreePath treePathRoot = tree.getPathForRow(0);
		
		if ( treePathRoot != null ) {
			Path nodePath = getNodePath( node );
			String nodePathStr = nodePath.toString();

			int treePathsVisibleLength = tree.getRowCount();
			for ( int i = 0; i < treePathsVisibleLength; i++ ) {
				TreePath treePathFound = tree.getPathForRow( i );
				DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) treePathFound.getLastPathComponent();
				Path nodeFoundPath = getNodePath( nodeFound );
				if ( nodeFoundPath.toString().equals(nodePathStr) == true ) {
					treePath = treePathFound;
					break;
				}
			}
		}
		
		return treePath;
	}
	
	private DefaultMutableTreeNode getNodeByName( LinkedList<DefaultMutableTreeNode> nodes, String name ) {
		DefaultMutableTreeNode result = null;
		Iterator<DefaultMutableTreeNode> nodesIt = nodes.iterator();
		while ( nodesIt.hasNext() ) {
			DefaultMutableTreeNode nodeFound = nodesIt.next();
			if ( ( (String) nodeFound.getUserObject() ).equals( name ) ) {
				result = nodeFound;
				break;
			}
		}
		return result;
	}
	
	private DefaultMutableTreeNode getNodeChildrenByIname( DefaultMutableTreeNode node, String name ) {
		DefaultMutableTreeNode result = null;
		Enumeration nodesIt = node.children();
		while ( nodesIt.hasMoreElements()) {
			DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) nodesIt.nextElement();
			if ( ((String)nodeFound.getUserObject()).toLowerCase().equals(name.toLowerCase()) == true ) {
				result = nodeFound;
				break;
			}
		}
		return result;
	}
	
	private DefaultMutableTreeNode getNode( DefaultMutableTreeNode node, Folder folder ) {
		DefaultMutableTreeNode result = null;
		Enumeration nodeIt = node.children();
		while ( nodeIt.hasMoreElements() && result == null ) {
			Object objFound = nodeIt.nextElement();
			DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) objFound;
			Path nodePath = getNodePath( nodeFound );
			if ( nodePath != null ) {
				String nodePathStr = nodePath.toString();
				
				if ( nodePathStr.equals(folder.getPathAbsolute(true)) == true ) {
					result = nodeFound;
					break;
				}
				if ( result == null ) {
					Folder folderFound = getFolder( nodeFound );
					if ( folderFound != null )
						result = getNode( nodeFound, folder );
				}
			}
		}
		return result;
	}
	
	private DefaultMutableTreeNode getNode( DefaultMutableTreeNode node, Note note ) {
		DefaultMutableTreeNode result = null;
		Enumeration rootNodeIt = node.children();
		while ( rootNodeIt.hasMoreElements() && result == null ) {
			Object objFound = rootNodeIt.nextElement();
			DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) objFound;
			Path nodePath = getNodePath( nodeFound );
			if ( nodePath != null ) {
				String nodePathStr = nodePath.toString();

				if ( nodePathStr.equals(note.getPathAbsolute()) == true ) {
					result = nodeFound;
					break;
				}
				if ( result == null ) {
					Folder folderFound = getFolder( nodeFound );
					if ( folderFound != null )
						result = getNode( nodeFound, note );
				}
			}
		}
		return result;
	}
	
	private DefaultMutableTreeNode getNode( Folder folder ) {
		return getNode( nodeRoot, folder.getPathAbsolute(true) );
	}
	
	private DefaultMutableTreeNode getNode( Note note ) {
		return getNode( nodeRoot, note.getPathAbsolute() );
	}
	
	public DefaultMutableTreeNode getNode( DefaultMutableTreeNode node, String pathStr ) {
		DefaultMutableTreeNode result = null;
		
		Path nodePath = getNodePath( node );
		if ( nodePath.toString().equals(pathStr) )
			result = node;
		
		Enumeration<TreeNode> nodeChildrenIt = node.children();
		while ( nodeChildrenIt.hasMoreElements() && result == null ) {
			TreeNode treeNodeFound = nodeChildrenIt.nextElement();
			DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) treeNodeFound;
			
			Enumeration<TreeNode> nodeFoundChildrenIt = nodeFound.children();
			while ( nodeFoundChildrenIt.hasMoreElements() && result == null ) {
				TreeNode subTreeNodeFound = nodeFoundChildrenIt.nextElement();
				DefaultMutableTreeNode subNodeFound = ( DefaultMutableTreeNode ) subTreeNodeFound;
				result = getNode( subNodeFound, pathStr );
			}
			
			if ( result == null ) {
				Path nodeFoundPath = getNodePath( nodeFound );

				if ( nodeFoundPath.toString().equals(pathStr) )
					result = nodeFound;
			}
		}
		return result;
	}
	
	public Path getNodePath( DefaultMutableTreeNode node ) {
		Path nodePath = null;
		try {
			Object[] pathsObj = node.getUserObjectPath();
			int pathsObjLength = pathsObj.length;
			String[] pathsStr = new String[pathsObjLength];
			for ( int i = 0; i < pathsObjLength; i++ ) {
				String path = ( String ) pathsObj[i];
				pathsStr[i] = path;
			}
			nodePath = Paths.get( folderRoot.getPathAbsolute(false), pathsStr );
		} catch (Exception e) {
			Core.get_LOGGER().write( e );
		}
		return nodePath;
	}
	
	public Folder getFolder( DefaultMutableTreeNode node ) {
		Folder folderFound = null;
		
		Path nodePath = getNodePath( node );
		if ( nodePath != null ) {
			folderFound = getFolder( folderRoot, nodePath );
		}
		
		return folderFound;
	}
	
	private Folder getFolder( Folder folder, Path absolutePath ) {
		Folder result = null;
		
		if ( absolutePath.toString().equals(folder.getPathAbsolute(true)) == true )
			result = folder;
		
		if ( result == null ) {
			Iterator<Folder> folderIt = folder.getFolders().iterator();
			while ( folderIt.hasNext() ) {
				Folder folderFound = folderIt.next();

				if ( absolutePath.toString().equals(folderFound.getPathAbsolute(true)) == true ) {
					result = folderFound;
					break;
				}
				if ( result == null ) {
					result = getFolder( folderFound, absolutePath );
					if ( result != null )
						break;
				}
			}
		}
		
		return result;
	}
	
	private Folder getFolderByName( Folder folder, String name ) {
		Folder result = null;
		Iterator<Folder> folderFoldersIt = folder.getFolders().iterator();
		while ( folderFoldersIt.hasNext() ) {
			Folder folderFound = folderFoldersIt.next();
			if ( folderFound.getName().equals(name) == true ) {
				result = folderFound;
				break;
			}
		}
		return result;
	}
	
	public Note getNote( DefaultMutableTreeNode node ) {
		Note noteFound = null;
		
		Path nodePath = getNodePath( node );
		if ( nodePath != null ) {
			noteFound = getNote( folderRoot, nodePath );
		}
		
		return noteFound;
	}
	
	private Note getNote( Folder folder, Path absolutePath ) {
		Note noteFound = null;
		
		Iterator<Note> folderNotesIt = folder.getNotes().iterator();
		while ( folderNotesIt.hasNext() ) {
			Note note = folderNotesIt.next();
			if ( absolutePath.toString().equals(note.getPathAbsolute()) == true ) {
				noteFound = note;
				break;
			}
		}
		if ( noteFound == null ) {
			Iterator<Folder> folderIt = folder.getFolders().iterator();
			while ( folderIt.hasNext() ) {
				Folder folderFound = folderIt.next();
				noteFound = getNote( folderFound, absolutePath );
				if ( noteFound != null )
					break;
			}
		}
		
		return noteFound;
	}
	
	private Note getNoteByName( Folder folder, String name ) {
		Note result = null;
		Iterator<Note> folderNotesIt = folder.getNotes().iterator();
		while ( folderNotesIt.hasNext() ) {
			Note noteFound = folderNotesIt.next();
			if ( noteFound.getName().equals(name) == true ) {
				result = noteFound;
				break;
			}
		}
		return result;
	}
	
	//====================
	// end region get
	//====================
	
	//====================
	// region set
	//====================
	
	private void setNoteContent( Note note ) {
		HashMap<String, String> headerParams = new HashMap<>();
		if ( note.getIconType() != null && note.getIconValue() != null ) {
			headerParams.put( parameterReader.get_PARAMETER_HEADER_ICON_TYPE(), note.getIconType().toString() );
			headerParams.put( parameterReader.get_PARAMETER_HEADER_ICON_VALUE(), note.getIconValue() );
		}
		String content = noteReader.createHeaderContent( headerParams ); 
		String paneStrParsed = null;
		try {
			paneStrParsed = noteReader.parseToString( note.getPane() );
		} catch (Re01JLibException ex) {
			Core.get_LOGGER().write( ex );
		}
		content += noteReader.createBodyContent( paneStrParsed );
		note.setContent( content );
	}
	
	public void setNoteOpenedIsSaved( Object[] args ) {
		Boolean isSaved = null;
		try {
			isSaved = MethodHelper.getArgBoolean( Notes.get_ARG_KEY_SET_NOTE_OPENED_IS_SAVED(), args );
		} catch (Exception ex) {
			Core.get_LOGGER().write( ex );
		}
		
		if ( isSaved != null ) {
			if ( noteOpened.getIsSaved() != isSaved ) {
				noteOpened.setIsSaved( isSaved );
				refreshTree();
			}
		}
	}
	
	private void setNoteHeaderParameter( String key, String value ) {
		String header = noteReader.getHeaderContent( noteOpened.getContent(), true );
		if ( header != null ) {
			ParametersSerializer parametersSerializer = new ParametersSerializer();
			HashMap<String, HashMap<String, String>> parameters = parametersSerializer.read( header, false );
			
			ParameterReader parameterReader = new ParameterReader( parameters );
			parameterReader.addValue( parameterReader.get_PARAMETER_HEADER(), key, value );
			
			HashMap<String, String> paramsSection = parameterReader.getSection( parameterReader.get_PARAMETER_HEADER() );
			
			if ( paramsSection != null ) {
				String content = noteReader.createHeaderContent( paramsSection );
				String body = noteReader.getBodyContent( noteOpened.getContent(), true );
				if ( body != null )
					content += body;
				noteOpened.setContent( content );
			}
		}
	}
	
	public void selectNodes() {
		nodesSelected.clear();
		TreePath[] treePaths = null;
		try {
			treePaths = this.tree.getSelectionPaths();
		} catch (NullPointerException e) { }
		
		if ( treePaths != null ) {
			int treePathsLength = treePaths.length;
			for ( int i = 0; i < treePathsLength; i++ ) {
				TreePath treePath = treePaths[i];
				DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) treePath.getLastPathComponent();
				if ( nodeFound != null ) {
					nodesSelected.add( nodeFound );
				}
			}
		}
	}
	
	//====================
	// end region set
	//====================
	
	//====================
	// region is
	//====================
	
	private boolean isNodeVisible( DefaultMutableTreeNode node ) {
		boolean isVisible = false;
		
		TreePath treePath = getTreePath( node );
		if ( treePath != null )
			isVisible = tree.isVisible( treePath );
		
		return isVisible;
	}
	
	public Boolean isNodeDirectory( DefaultMutableTreeNode node ) {
		Boolean result = false;
		try {
			Folder folderFound = getFolder( node );
			if ( folderFound != null )
				result = true;
		} catch (Exception e) {
			Core.get_LOGGER().write( e );
		}
		return result;
	}
	
	public boolean isAnyItemExistsInBothFolders( Folder folder1, Folder folder2 ) {
		boolean isExists = false;
		
		Folder folderFound = getFolder( folder2, Paths.get(folder1.getPathAbsolute(true)) );
		if ( folderFound != null ) {
			isExists = true;
		} else {
			Iterator<Note> folderNotesIt = folder1.getNotes().iterator();
			while ( folderNotesIt.hasNext() ) {
				Note noteFound1 = folderNotesIt.next();
				Note noteFound2 = getNote( folder2, Paths.get(noteFound1.getPathAbsolute()) );
				if ( noteFound2 != null ) {
					isExists = true;
					break;
				}
			}
			if ( isExists == false ) {
				Iterator<Folder> folderFoldersIt = folder1.getFolders().iterator();
				while ( folderFoldersIt.hasNext() && isExists == false ) {
					Folder folderFound1 = folderFoldersIt.next();
					isExists = isAnyItemExistsInBothFolders( folderFound1, folder2 );
				}
			}
		}
		return isExists;
	}
	
	public boolean isNotesSaved() {
		return isNotesSaved( folderRoot );
	}
	
	private boolean isNotesSaved( Folder folder ) {
		boolean isSaved = true;
		
		Iterator<Folder> foldersIt = folder.getFolders().iterator();
		while ( foldersIt.hasNext() ) {
			Folder folderFound = foldersIt.next();
			isSaved = isNotesSaved( folderFound );
			if ( isSaved == false ) {
				break;
			}
		}
		
		if ( isSaved == true ) {
			Iterator<Note> folderNotesIt = folder.getNotes().iterator();
			while ( folderNotesIt.hasNext() ) {
				Note noteFound = folderNotesIt.next();
				isSaved = noteFound.getIsSaved();
				if ( isSaved == false ) {
					break;
				}
			}
		}
		
		return isSaved;
	}
	
	//====================
	// end region is
	//====================
	
	//====================
	// region open
	//====================
	
	public void openNoteContent( DefaultMutableTreeNode node ) throws Re01JLibException {
		deleteContent();
		
		noteOpened = getNote( node );
		createContent();
		
		refreshTree();
		resetMessagesBar();
	}
	
	public void openNewNode() {
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Loading viewLoading1 = null;
				
				try {
					if ( notesRenderedInTreeLock.tryLock() == false ) {
						try {
							Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
							viewLoading1 = new Loading( LANG.get_LANG().get_OPEN(), argsNewLoading1 );
							viewLoading1.resume();
						} catch ( Re01JLibException ex ) {
							Core.get_LOGGER().write( ex );
						}
						
						notesRenderedInTreeLock.lock();
					}
					
					HashSet<DefaultMutableTreeNode> foldersNodesToRemove = new HashSet<>();
					
					Iterator<DefaultMutableTreeNode> foldersNodesRenderedInTreeIt = foldersNodesRenderedInTree.iterator();
					while ( foldersNodesRenderedInTreeIt.hasNext() ) {
						DefaultMutableTreeNode folderNodeFound = foldersNodesRenderedInTreeIt.next();
						if ( folderNodeFound != null ) {
							if ( isNodeVisible(folderNodeFound) == false )
								foldersNodesToRemove.add( folderNodeFound );
						}
					}
					
					Iterator<DefaultMutableTreeNode> foldersNodesToRemoveIt = foldersNodesToRemove.iterator();
					while ( foldersNodesToRemoveIt.hasNext() ) {
						DefaultMutableTreeNode folderNodeToRemove = foldersNodesToRemoveIt.next();
						foldersNodesRenderedInTree.remove( folderNodeToRemove );
					}
					
					HashSet<Note> notesToRemove = new HashSet<>();
					
					Iterator<Note> notesRenderedInTreeIt = notesRenderedInTree.iterator();
					while ( notesRenderedInTreeIt.hasNext() ) {
						Note noteFound = notesRenderedInTreeIt.next();
						if ( noteFound != null ) {
							DefaultMutableTreeNode nodeFound = getNode( noteFound );
							if ( nodeFound != null ) {
								isNodeVisible( nodeFound );

								if ( isNodeVisible(nodeFound) == false ) {
									notesToRemove.add( noteFound );
								}
							}
						}
					}
					
					Iterator<Note> notesToRemoveIt = notesToRemove.iterator();
					while ( notesToRemoveIt.hasNext() ) {
						Note noteToRemove = notesToRemoveIt.next();
						notesRenderedInTree.remove( noteToRemove );
					}
				} catch ( Exception e ) {
					Core.get_LOGGER().write( e );
				} finally {
					notesRenderedInTreeLock.unlock();
					
					if ( viewLoading1 != null )
						viewLoading1.delete();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		
		if ( nodeOpenedNew != null && nodeOpenedNew.equals(nodeOpened) == false ) {
			nodeOpened = nodeOpenedNew;
			try {
				openNoteContent( nodeOpened );
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
			nodeOpenedNew = null;
		}
	}
	
	public void openContextPopup( int x, int y ) {
		if ( contextMenuPopup == null ) {
			contextMenuPopup = new JPopupMenu();

			JMenuItem contextMenuPopupMenuItemNewFolder = new JMenuItem( LANG.get_LANG_PROGRAM().get_NEW_FOLDER(), 0 );
			contextMenuPopupMenuItemNewFolder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					promptNewFolder();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemNewFolder );

			JMenuItem contextMenuPopupMenuItemNewNote = new JMenuItem( LANG.get_LANG_PROGRAM().get_NEW_NOTE(), 0 );
			contextMenuPopupMenuItemNewNote.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					promptNewNote();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemNewNote );

			JMenuItem contextMenuPopupMenuItemRename = new JMenuItem( LANG.get_LANG().get_RENAME(), 0 );
			contextMenuPopupMenuItemRename.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						promptRenameItem();
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemRename );
			
			JMenuItem contextMenuPopupMenuItemCopy = new JMenuItem( LANG.get_LANG().get_COPY(), 0 );
			contextMenuPopupMenuItemCopy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Thread thread = new Thread(new Runnable(){
						@Override
						public void run() {
							Loading viewLoading1 = null;
							try {
								Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
								viewLoading1 = new Loading( LANG.get_LANG().get_COPY(), argsNewLoading1 );
								viewLoading1.resume();
							} catch ( Re01JLibException ex ) {
								Core.get_LOGGER().write( ex );
							}

							copyNotes( false );

							if ( viewLoading1 != null )
								viewLoading1.delete();
						}
					});
					thread.setDaemon(false);
					thread.start();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemCopy );
			
			JMenuItem contextMenuPopupMenuItemCut = new JMenuItem( LANG.get_LANG().get_CUT(), 0 );
			contextMenuPopupMenuItemCut.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Thread thread = new Thread(new Runnable(){
						@Override
						public void run() {
							Loading viewLoading1 = null;
							try {
								Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
								viewLoading1 = new Loading( LANG.get_LANG().get_CUT(), argsNewLoading1 );
								viewLoading1.resume();
							} catch ( Re01JLibException ex ) {
								Core.get_LOGGER().write( ex );
							}

							copyNotes( true );

							if ( viewLoading1 != null )
								viewLoading1.delete();
						}
					});
					thread.setDaemon(false);
					thread.start();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemCut );
			
			JMenuItem contextMenuPopupMenuItemPaste = new JMenuItem( LANG.get_LANG().get_PASTE(), 0 );
			contextMenuPopupMenuItemPaste.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pasteNotes();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemPaste );

			JMenuItem contextMenuPopupMenuItemReorder = new JMenuItem( LANG.get_LANG().get_ORDER(), 0 );
			contextMenuPopupMenuItemReorder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reorderNodes();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemReorder );

			JMenuItem contextMenuPopupMenuItemSave = new JMenuItem( LANG.get_LANG().get_SAVE(), 0 );
			contextMenuPopupMenuItemSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					confirmSaveNotes();
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemSave );

			JMenuItem contextMenuPopupMenuItemDelete = new JMenuItem( LANG.get_LANG().get_DELETE(), 0 );
			contextMenuPopupMenuItemDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						confirmDeleteNotes();
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			} );
			contextMenuPopup.add( contextMenuPopupMenuItemDelete );
		}

		contextMenuPopup.show( tree, x, y );
	}
	
	//====================
	// end region open
	//====================
	
	//====================
	// region save
	//====================
	
	public void confirmSaveNotes() {
		resetMessagesBar();
		final Notes THIS = this;
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Loading viewLoading1 = null;
				try {
					Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
					viewLoading1 = new Loading( LANG.get_LANG().get_SAVE(), argsNewLoading1 );
					viewLoading1.resume();
				} catch ( Re01JLibException ex ) {
					Core.get_LOGGER().write( ex );
				}
				
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
				Integer nodesSelectedLength = nodesSelected.size();
				Folder folderFound = null;

				while ( nodesSelectedIt.hasNext() ) {
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					Folder folder = getFolder( nodeSelected );
					if ( folder != null ) {
						folderFound = folder;
						break;
					}
				}

				Class c = THIS.getClass();

				HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
				HashMap<Class, String[]> callbacksMap = new HashMap<>();
				callbacksMap.put( c, new String[]{"saveNotes"} );
				callbacksObjMap.put( THIS, callbacksMap );

				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				try {
					MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
					MethodHelper.addOrReplaceCallbackArg( 
						Confirm.get_ARG_KEY_CONFIRM_MESSAGE(), 
						( folderFound != null ) ? LANG.get_LANG_PROGRAM().get_CONFIRM_SAVE_FOLDER_NOTES() : ( nodesSelectedLength > 1 ) ? LANG.get_LANG_PROGRAM().get_CONFIRM_SAVE_NOTES() : LANG.get_LANG_PROGRAM().get_CONFIRM_SAVE_NOTE(), 
						argsNew 
					);
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}

				try {
					Confirm confirm = new Confirm( 
						( nodesSelectedLength > 1 ) ? LANG.get_LANG_PROGRAM().get_SAVE_NOTES() : LANG.get_LANG_PROGRAM().get_SAVE_NOTE(),
						argsNew
					);
					confirm.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				
				if ( viewLoading1 != null )
					viewLoading1.delete();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void saveNotes( Object[] args ) {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Boolean isSaveNotes = null;
				try {
					isSaveNotes = MethodHelper.getArgBoolean( Confirm.get_ARG_KEY_CONFIRM_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( isSaveNotes != null && isSaveNotes == true ) {
					int notesTrySavedLength = 0;
					int actionResultOk = 0;
					int actionResultError = 0;
					
					Loading viewLoading1 = null;
					try {
						Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 1, argsNewLoading1 );
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), 2, argsNewLoading1 );
						viewLoading1 = new Loading( LANG.get_LANG_PROGRAM().get_SAVE_NOTES(), argsNewLoading1 );
						viewLoading1.resume();
					} catch ( Re01JLibException e ) {
						Core.get_LOGGER().write( e );
					}
					
					int notesSelectedLength = 0;
					Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
					while ( nodesSelectedIt.hasNext() ) {
						DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
						Folder folderSelected = null;
						folderSelected = getFolder( nodeSelected );
						if ( folderSelected != null ) {
							notesSelectedLength += countFolderNotes( folderSelected );
						} else {
							Note noteSelected = getNote( nodeSelected );
							if ( noteSelected != null )
								notesSelectedLength++;
						}
					}
					
					if ( viewLoading1 != null )
						viewLoading1.delete();
					
					Loading viewLoading2 = null;
					try {
						Object[] argsNewLoading2 = new Object[]{ MethodHelper.createDefaultArgs() };
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 2, argsNewLoading2 );
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), 2, argsNewLoading2 );
						viewLoading2 = new Loading( LANG.get_LANG_PROGRAM().get_SAVE_NOTES(), notesSelectedLength, argsNewLoading2 );
						viewLoading2.resume();
					} catch ( Re01JLibException e ) {
						Core.get_LOGGER().write( e );
					}
					
					JProgressBar progressBar2 = null;
					if ( viewLoading2 != null )
						progressBar2 = viewLoading2.getProgressBar();
					
					nodesSelectedIt = nodesSelected.iterator();
					
					while ( nodesSelectedIt.hasNext() ) {
						DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();

						Folder folderSelected = null;
						folderSelected = getFolder( nodeSelected );
						if ( folderSelected != null ) {
							int[] folderSelectedResult = saveFolderNotes( folderSelected, progressBar2 );
							notesTrySavedLength += folderSelectedResult[0];
							actionResultOk += folderSelectedResult[1];
							actionResultError += folderSelectedResult[2];
						} else {
							Note noteSelected = getNote( nodeSelected );
							if ( noteSelected.getIsSaved() == false )
								setNoteContent( noteSelected );

							notesTrySavedLength++;
							if ( saveNote( noteSelected ) == true ) {
								actionResultOk++;
							} else
								actionResultError++;

							if ( progressBar2 != null )
								progressBar2.setValue( progressBar2.getValue() + 1 );
						}
					}

					String actionResultStrOk = "";
					String actionResultStrError = "";
					
					if ( actionResultOk > 0 ) {
						actionResultStrOk 
							+= actionResultOk 
							+ "/" 
							+ notesTrySavedLength 
							+ " " 
							+ ( ( actionResultOk > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_SAVED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_SAVED().toLowerCase() );

						addMessagesBarOk( actionResultStrOk );
					}
					if ( actionResultError > 0 ) {
						actionResultStrError 
							+= actionResultError 
							+ "/" 
							+ notesTrySavedLength 
							+ " " 
							+ ( ( actionResultError > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_NOT_SAVED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_NOT_SAVED().toLowerCase() );

						addMessagesBarError( actionResultStrError );
					}

					if ( viewLoading2 != null )
						viewLoading2.delete();

					refreshTreeAsync();
					refreshMessagesBarAsync();
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	private int[] saveFolderNotes( Folder folder, JProgressBar progressBar ) {
		int[] result = new int[3];
		int notesSelectedLength = 0;
		int actionResultOk = 0;
		int actionResultError = 0;
		
		Iterator<Folder> foldersIt = folder.getFolders().iterator();
		while ( foldersIt.hasNext() ) {
			Folder folderFound = foldersIt.next();
			int[] folderFoundResult = saveFolderNotes( folderFound, progressBar );
			notesSelectedLength += folderFoundResult[0];
			actionResultOk += folderFoundResult[1];
			actionResultError += folderFoundResult[2];
		}
		
		notesSelectedLength += folder.getNotes().size();
		Iterator<Note> folderNotesIt = folder.getNotes().iterator();
		while ( folderNotesIt.hasNext() ) {
			Note noteFound = folderNotesIt.next();
			if ( noteFound.getIsSaved() == false ) {
				setNoteContent( noteFound );

				if ( saveNote( noteFound ) == true )
					actionResultOk++;
				else
					actionResultError++;
			} else
				actionResultOk++;
			
			if ( progressBar != null )
				progressBar.setValue( progressBar.getValue() + 1 );
		}
		result[0] = notesSelectedLength;
		result[1] = actionResultOk;
		result[2] = actionResultError;
		return result;
	}
	
	private boolean saveNote( Note note ) {
		boolean isSaved = false;
		try {
			if ( files.writeContent(note.getPathAbsolute(), note.getContent(), StandardCharsets.UTF_8) == true ) {
				isSaved = true;
				note.setIsSaved( true );
			}
		} catch ( Exception e ) {
			Core.get_LOGGER().write( e );
		}
		return isSaved;
	}
	
	//====================
	// end region save
	//====================
	
	//====================
	// region copy
	//====================
	
	public void copyNotes( boolean isCut ) {
		isCutItemsSelected = isCut;
		foldersSelected.clear();
		notesSelected.clear();
		
		Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
		while ( nodesSelectedIt.hasNext() ) {
			DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
			
			Folder folderSelected = getFolder( nodeSelected );
			if ( folderSelected != null )
				foldersSelected.put( folderSelected, this );
			else {
				Note noteSelected = getNote( nodeSelected );
				if ( noteSelected != null )
					notesSelected.put( noteSelected, this );
			}
		}
	}
	
	public void pasteNotes() {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				int foldersCopiedQty = 0;
				int foldersRenamedQty = 0;
				int notesCopiedQty = 0;
				int notesRenamedQty = 0;

				if ( Objects.equals(nodesSelected.size(), 1) == true ) {
					int loadingStepsLength = ( isCutItemsSelected == true ) ? 3 : 2 ;
					Loading viewLoading1 = null;
					try {
						Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 1, argsNewLoading1 );
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading1 );
						viewLoading1 = new Loading( LANG.get_LANG().get_PASTE(), argsNewLoading1 );
						viewLoading1.resume();
					} catch ( Re01JLibException e ) {
						Core.get_LOGGER().write( e );
					}
					
					int foldersSelectedReorderedNotesLength = 0;
					HashMap<Folder, Notes> foldersSelectedReordered = new HashMap<>();

					Set<Entry<Folder, Notes>> foldersSelectedSet = foldersSelected.entrySet();
					Iterator<Entry<Folder, Notes>> foldersSelectedSetIt = foldersSelectedSet.iterator();
					while ( foldersSelectedSetIt.hasNext() ) {
						Entry<Folder, Notes> foldersSelectedEntry = foldersSelectedSetIt.next();
						Folder folderFound = foldersSelectedEntry.getKey();
						Notes notesFound = foldersSelectedEntry.getValue();
						if ( folderFound.getFolderParent() == null || foldersSelected.containsValue(folderFound.getFolderParent()) == false ) {
							foldersSelectedReorderedNotesLength += countFolderNotes( folderFound );
							foldersSelectedReordered.put( folderFound, notesFound );
						}
					}

					foldersSelectedReorderedNotesLength += notesSelected.size();
					
					if ( viewLoading1 != null )
						viewLoading1.delete();
				
					Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					Folder folderSelected = getFolder( nodeSelected );
					
					if ( folderSelected != null ) {
						
						Loading viewLoading2 = null;
						try {
							Object[] argsNewLoading2 = new Object[]{ MethodHelper.createDefaultArgs() };
							MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 2, argsNewLoading2 );
							MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading2 );
							viewLoading2 = new Loading( LANG.get_LANG().get_PASTE(), foldersSelectedReorderedNotesLength, argsNewLoading2 );
							viewLoading2.resume();
						} catch ( Re01JLibException e ) {
							Core.get_LOGGER().write( e );
						}

						JProgressBar progressBar2 = null;
						if ( viewLoading2 != null )
							progressBar2 = viewLoading2.getProgressBar();

						ArrayList<Folder> folderSelectedFolders = folderSelected.getFolders();

						Set<Entry<Folder, Notes>> foldersSelectedReorderedSet = foldersSelectedReordered.entrySet();
						Iterator<Entry<Folder, Notes>> foldersSelectedReorderedSetIt = foldersSelectedReorderedSet.iterator();
						while ( foldersSelectedReorderedSetIt.hasNext() ) {
							Entry<Folder, Notes> foldersSelectedReorderedEntry = foldersSelectedReorderedSetIt.next();
							Folder folderFound = foldersSelectedReorderedEntry.getKey();

							String folderNameNew = folderFound.getName();
							boolean folderNameNewExists;
							int d = 1;
							do {
								folderNameNewExists = false;
								Iterator<Folder> folderSelectedIt = folderSelected.getFolders().iterator();
								while ( folderSelectedIt.hasNext() ) {
									Folder folderSelectedFolderFound = folderSelectedIt.next();
									if ( folderSelectedFolderFound.getName().equals(folderNameNew) == true ) {
										folderNameNewExists = true;
										folderNameNew = folderFound.getName() + "(" + d + ")";
										break;
									}
								}
								d++;
							} while ( folderNameNewExists == true && d < Integer.MAX_VALUE );
							if ( folderNameNewExists == false ) {
								foldersCopiedQty++;
								if ( folderFound.getName().equals(folderNameNew) == false )
									foldersRenamedQty++;

								Folder folderNew = createFolderFromFolder( folderSelected, folderFound, folderNameNew, progressBar2 );
								if ( folderNew != null )
									folderSelectedFolders.add( folderNew );
							}
						}

						ArrayList<Note> folderSelectedNotes = folderSelected.getNotes();

						Set<Entry<Note, Notes>> notesSelectedSet = notesSelected.entrySet();
						Iterator<Entry<Note, Notes>> notesSelectedSetIt = notesSelectedSet.iterator();
						while ( notesSelectedSetIt.hasNext() ) {
							Entry<Note, Notes> notesSelectedEntry = notesSelectedSetIt.next();

							Note noteSelected = notesSelectedEntry.getKey();

							String nameNew = noteSelected.getName();
							boolean nameNewExists;
							int d = 1;
							do {
								nameNewExists = false;
								Iterator<Note> folderSelectedNotesIt = folderSelected.getNotes().iterator();
								while ( folderSelectedNotesIt.hasNext() ) {
									Note folderSelectedNoteFound = folderSelectedNotesIt.next();
									if ( folderSelectedNoteFound.getName().equals(nameNew) == true ) {
										nameNewExists = true;
										nameNew = noteSelected.getName().replace(Core.get_NOTE_FILE_EXTENSION(), "") + "(" + d + ")" + Core.get_NOTE_FILE_EXTENSION();
										break;
									}
								}
								d++;
							} while ( nameNewExists == true && d < Integer.MAX_VALUE );
							if ( nameNewExists == false ) {
								notesCopiedQty++;
								if ( noteSelected.getName().equals(nameNew) == false )
									notesRenamedQty++;

								String pathStr = Paths.get( folderSelected.getPathAbsolute(true), nameNew ).toString();
								if ( files.writeContent(pathStr, noteSelected.getContent(), StandardCharsets.UTF_8) == true ) {
									folderSelectedNotes.add( new Note( 
										folderSelected, 
										nameNew, 
										noteSelected.getIconType(), 
										noteSelected.getIconValue(), 
										( noteSelected.getIconType() != null && noteSelected.getIconValue() != null ) ? images.createImageIcon(noteSelected.getIconType(), noteSelected.getIconValue()) : null, 
										noteSelected.getContent(), 
										null
									) );

									DefaultMutableTreeNode folderNode = getNode( folderSelected );
									if ( folderNode != null )
										folderNode.add( new DefaultMutableTreeNode(nameNew) );
									
									if ( progressBar2 != null )
										progressBar2.setValue( progressBar2.getValue() + 1 );
								}
							}
						}
						
						if ( viewLoading2 != null )
							viewLoading2.delete();

						if ( isCutItemsSelected == true ) {
							Loading viewLoading3 = null;
							try {
								Object[] argsNewLoading3 = new Object[]{ MethodHelper.createDefaultArgs() };
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 3, argsNewLoading3 );
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading3 );
								viewLoading3 = new Loading( LANG.get_LANG().get_PASTE(), foldersSelectedReordered.size() + notesSelected.size(), argsNewLoading3 );
								viewLoading3.resume();
							} catch ( Re01JLibException e ) {
								Core.get_LOGGER().write( e );
							}

							JProgressBar progressBar3 = null;
							if ( viewLoading3 != null )
								progressBar3 = viewLoading3.getProgressBar();
							
							foldersSelectedReorderedSet = foldersSelectedReordered.entrySet();
							foldersSelectedReorderedSetIt = foldersSelectedReorderedSet.iterator();
							while ( foldersSelectedReorderedSetIt.hasNext() ) {
								Entry<Folder, Notes> foldersSelectedReorderedEntry = foldersSelectedReorderedSetIt.next();
								Folder folderFound = foldersSelectedReorderedEntry.getKey();
								Notes notesFound = foldersSelectedReorderedEntry.getValue();
								files.delete( folderFound.getPathAbsolute(true) );
								if ( files.isDirectoryExist(folderFound.getPathAbsolute(true)) == false )
									JTextNotes.viewsNotesDeleteFolder( notesFound, folderFound );
								
								if ( progressBar3 != null )
									progressBar3.setValue( progressBar3.getValue() + 1 );
							}
							notesSelectedSet = notesSelected.entrySet();
							notesSelectedSetIt = notesSelectedSet.iterator();
							while ( notesSelectedSetIt.hasNext() ) {
								Entry<Note, Notes> notesSelectedEntry = notesSelectedSetIt.next();
								Note noteSelected = notesSelectedEntry.getKey();
								Notes notesFound = notesSelectedEntry.getValue();

								files.delete( noteSelected.getPathAbsolute() );
								if ( files.isFileExists(noteSelected.getPathAbsolute()) == false )
									JTextNotes.viewsNotesDeleteNote( notesFound, noteSelected );
								
								if ( progressBar3 != null )
									progressBar3.setValue( progressBar3.getValue() + 1 );
							}
							foldersSelected.clear();
							notesSelected.clear();
							
							if ( viewLoading3 != null )
								viewLoading3.delete();
						}
					} else
						addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_A_FOLDER_TO_ACTION() );
				} else
					addMessagesBarError( LANG.get_LANG_PROGRAM().get_NEED_SELECT_A_FOLDER_TO_ACTION() );
				
				if ( foldersCopiedQty > 0 ) {
					String message = foldersCopiedQty + " ";
					if ( isCutItemsSelected == false )
						message += ( foldersCopiedQty > 1 ) ? LANG.get_LANG().get_FOLDERS_COPIED().toLowerCase() : LANG.get_LANG().get_FOLDER_COPIED().toLowerCase() ;
					else
						message += ( foldersCopiedQty > 1 ) ? LANG.get_LANG().get_FOLDERS_MOVED().toLowerCase() : LANG.get_LANG().get_FOLDER_MOVED().toLowerCase() ;
					addMessagesBarOk( message );
				}
				if ( notesCopiedQty > 0 ) {
					String message = notesCopiedQty + " ";
					if ( isCutItemsSelected == false )
						message += ( notesCopiedQty > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_COPIED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_COPIED().toLowerCase() ;
					else
						message += ( notesCopiedQty > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_MOVED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_MOVED().toLowerCase() ;
					addMessagesBarOk( message );
				}

				if ( foldersRenamedQty > 0 ) {
					String message = foldersRenamedQty + " ";
					message += ( foldersRenamedQty > 1 ) ? LANG.get_LANG().get_FOLDERS_RENAMED().toLowerCase() : LANG.get_LANG().get_FOLDER_RENAMED().toLowerCase() ;
					addMessagesBarInfo( message );
				}
				if ( notesRenamedQty > 0 ) {
					String message = notesRenamedQty + " ";
					message += ( notesRenamedQty > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_RENAMED().toLowerCase(): LANG.get_LANG_PROGRAM().get_NOTE_RENAMED().toLowerCase();
					addMessagesBarInfo( message );
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextNotes.refreshViewsNotesTree();
					}
				} );
				
				refreshMessagesBarAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	//====================
	// end region copy
	//====================
	
	//====================
	// region reorder
	//====================
	
	public void reorderNodes() {
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
				while ( nodesSelectedIt.hasNext() ) {
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					reorderNode( nodeSelected );
				}
				refreshTreeAsync();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void reorderNode( DefaultMutableTreeNode node ) {
		LinkedList<DefaultMutableTreeNode> directoriesNodes = new LinkedList<>();
		LinkedList<String> directoriesNodesNames = new LinkedList<>();
		
		LinkedList<DefaultMutableTreeNode> filesNodes = new LinkedList<>();
		LinkedList<String> filesNodesNames = new LinkedList<>();
		
		if ( node != null ) {
			HashSet<DefaultMutableTreeNode> nodesToRemove = new HashSet<>();
			Enumeration nodeIt = node.children();
			while ( nodeIt.hasMoreElements() ) {
				DefaultMutableTreeNode nodeFound = ( DefaultMutableTreeNode ) nodeIt.nextElement();
				Folder folderFound = getFolder( nodeFound );
				if ( folderFound != null ) {
					directoriesNodes.add( nodeFound );
					directoriesNodesNames.add( folderFound.getName() );
					
					reorderNode( nodeFound );
				} else {
					Note noteFound = getNote( nodeFound );
					if ( noteFound != null ) {
						filesNodes.add( nodeFound );
						filesNodesNames.add( noteFound.getName() );
					}
				}
				nodesToRemove.add( nodeFound );
			}
			
			Iterator<DefaultMutableTreeNode> nodesToRemoveIt = nodesToRemove.iterator();
			while ( nodesToRemoveIt.hasNext() ) {
				DefaultMutableTreeNode nodeToRemove = nodesToRemoveIt.next();
				node.remove( nodeToRemove );
			}

			Collections.sort( directoriesNodesNames );
			Iterator<String> directoriesNodesNamesIt = directoriesNodesNames.iterator();
			while ( directoriesNodesNamesIt.hasNext() ) {
				String nodeNameFound = directoriesNodesNamesIt.next();
				DefaultMutableTreeNode nodeFound = getNodeByName( directoriesNodes, nodeNameFound );
				if ( nodeFound != null ) {
					try {
						node.add( nodeFound );
					} catch (Exception e) {
						Core.get_LOGGER().write( e );
					}
				}
			}

			// Add files after folders
			Collections.sort( filesNodesNames );
			Iterator<String> filesNodesNamesIt = filesNodesNames.iterator();
			while ( filesNodesNamesIt.hasNext() ) {
				String nodeNameFound = filesNodesNamesIt.next();
				DefaultMutableTreeNode nodeFound = getNodeByName( filesNodes, nodeNameFound );
				if ( nodeFound != null ) {
					try {
						node.add( nodeFound );
					} catch (Exception e) {
						Core.get_LOGGER().write( e );
					}
				}
			}
		}
	}
	
	//====================
	// end region reorder
	//====================
	
	//====================
	// region delete
	//====================
	
	public void deleteNode( Folder folder ) {
		DefaultMutableTreeNode node = getNode( nodeRoot, folder );
		if ( node != null ) {
			DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();
			if ( nodeParent != null )
				nodeParent.remove( node );
		}
	}
	
	public void deleteNode( Note note ) {
		DefaultMutableTreeNode node = getNode( nodeRoot, note );
		if ( node != null ) {
			DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();
			if ( nodeParent != null )
				nodeParent.remove( node );
		}
	}
	
	public void deleteFolder( Folder folder ) {
		folder.getFolderParent().getFolders().remove( folder );
		foldersNodesRenderedInTree.remove( folder );
	}
	
	public void deleteNote( Note note ) {
		note.getFolder().getNotes().remove( note );
		if ( note.equals(noteOpened) )
			deleteContent();
		notesRenderedInTree.remove( note );
	}
	
	public void confirmDeleteNotes() throws Re01JLibException {
		resetMessagesBar();
		final Notes THIS = this;
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Loading viewLoading1 = null;
				try {
					Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
					viewLoading1 = new Loading( LANG.get_LANG().get_DELETE(), argsNewLoading1 );
					viewLoading1.resume();
				} catch ( Re01JLibException ex ) {
					Core.get_LOGGER().write( ex );
				}
				
				Iterator<DefaultMutableTreeNode> nodesSelectedIt = nodesSelected.iterator();
				Integer nodesSelectedLength = nodesSelected.size();

				Folder folderFound = null;
				while ( nodesSelectedIt.hasNext() ) {
					DefaultMutableTreeNode nodeSelected = nodesSelectedIt.next();
					Folder folder = getFolder( nodeSelected );
					if ( folder != null ) {
						folderFound = folder;
						break;
					}
				}

				Class c = THIS.getClass();

				HashMap<Object, HashMap<Class, String[]>> callbacksObjMap = new HashMap<Object, HashMap<Class, String[]>>();
				HashMap<Class, String[]> callbacksMap = new HashMap<>();
				callbacksMap.put( c, new String[]{"deleteNotes"} );
				callbacksObjMap.put( THIS, callbacksMap );

				Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };

				try {
					MethodHelper.addOrReplaceCallbackArg( MethodHelper.get_ARG_KEY_CALLBACKS_TO_EXECUTE(), callbacksObjMap, argsNew );
					MethodHelper.addOrReplaceCallbackArg( 
						Confirm.get_ARG_KEY_CONFIRM_MESSAGE(), 
						( folderFound != null ) ? LANG.get_LANG_PROGRAM().get_CONFIRM_DELETE_FOLDER_NOTES() : ( nodesSelectedLength > 1 ) ? LANG.get_LANG_PROGRAM().get_CONFIRM_DELETE_NOTES() : LANG.get_LANG_PROGRAM().get_CONFIRM_DELETE_NOTE(), 
						argsNew 
					);
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}

				try {
					Confirm confirm = new Confirm( 
						( nodesSelectedLength > 1 ) ? LANG.get_LANG_PROGRAM().get_DELETE_NOTES() : LANG.get_LANG_PROGRAM().get_DELETE_NOTE(),
						argsNew
					);
					confirm.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				
				if ( viewLoading1 != null )
					viewLoading1.delete();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public void deleteNotes( Object[] args ) {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
		
				Boolean isDeleteNotes = null;
				try {
					isDeleteNotes = MethodHelper.getArgBoolean( Confirm.get_ARG_KEY_CONFIRM_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( isDeleteNotes != null && isDeleteNotes == true ) {
					Loading viewLoading1 = null;
					try {
						Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 1, argsNewLoading1 );
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), 2, argsNewLoading1 );
						viewLoading1 = new Loading( LANG.get_LANG_PROGRAM().get_DELETE_NOTES(), argsNewLoading1 );
						viewLoading1.resume();
					} catch ( Re01JLibException e ) {
						Core.get_LOGGER().write( e );
					}
					
					copyNotes( false );

					Integer notesSelectedLength = 0;
					Integer actionResultOk = 0;
					int actionResultError = 0;
					int foldersSelectedReorderedNotesLength = 0;
					HashMap<Folder, Notes> foldersSelectedReordered = new HashMap<>();

					Set<Entry<Folder, Notes>> foldersSelectedSet = foldersSelected.entrySet();
					Iterator<Entry<Folder, Notes>> foldersSelectedSetIt = foldersSelectedSet.iterator();
					while ( foldersSelectedSetIt.hasNext() ) {
						Entry<Folder, Notes> foldersSelectedEntry = foldersSelectedSetIt.next();
						Folder folderFound = foldersSelectedEntry.getKey();
						Notes notesFound = foldersSelectedEntry.getValue();
						if ( folderFound.getFolderParent() == null || foldersSelected.containsValue(folderFound.getFolderParent()) == false ) {
							foldersSelectedReorderedNotesLength += countFolderNotes( folderFound );
							foldersSelectedReordered.put( folderFound, notesFound );
						}
					}
					foldersSelectedReorderedNotesLength += notesSelected.size();
					
					if ( viewLoading1 != null )
						viewLoading1.delete();

					Loading viewLoading2 = null;
					try {
						Object[] argsNewLoading2 = new Object[]{ MethodHelper.createDefaultArgs() };
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 2, argsNewLoading2 );
						MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), 2, argsNewLoading2 );
						viewLoading2 = new Loading( LANG.get_LANG_PROGRAM().get_DELETE_NOTES(), foldersSelectedReorderedNotesLength, argsNewLoading2 );
						viewLoading2.resume();
					} catch ( Re01JLibException e ) {
						Core.get_LOGGER().write( e );
					}

					JProgressBar progressBar2 = null;
					if ( viewLoading2 != null )
						progressBar2 = viewLoading2.getProgressBar();
					
					HashSet<Note> notesDeleted = new HashSet<>();

					Set<Entry<Folder, Notes>> foldersSelectedReorderedSet = foldersSelectedReordered.entrySet();
					Iterator<Entry<Folder, Notes>> foldersSelectedReorderedSetIt = foldersSelectedReorderedSet.iterator();
					while ( foldersSelectedReorderedSetIt.hasNext() ) {
						Entry<Folder, Notes> foldersSelectedReorderedEntry = foldersSelectedReorderedSetIt.next();
						Folder folderFound = foldersSelectedReorderedEntry.getKey();

						FolderRoot folderRoot = null;
						try {
							folderRoot = (FolderRoot) folderFound;
						} catch ( Exception e ) {
							Core.get_LOGGER().write( e );
						}
						if ( folderRoot == null ) {
							String txtExtension = Core.get_NOTE_FILE_EXTENSION().replace(".", "");
							HashSet<Tuple> folderDeleteResult = files.delete( folderFound.getPathAbsolute(true) );
							Iterator<Tuple> folderDeleteResultIt = folderDeleteResult.iterator();
							while ( folderDeleteResultIt.hasNext() ) {
								Tuple resultFound = folderDeleteResultIt.next();
								String extensionFound = (String) resultFound.getOne();
								String pathFound = (String) resultFound.getTwo();
								Boolean isDeletedFound = (Boolean) resultFound.getThree();

								if ( extensionFound.equals(txtExtension) == true ) {
									notesSelectedLength++;
									if ( isDeletedFound == true )
										actionResultOk++;
									else
										actionResultError++;
									
									if ( progressBar2 != null )
										progressBar2.setValue( progressBar2.getValue() + 1 );
								}
							}
							if ( Objects.equals(notesSelectedLength, actionResultOk) == true ) {
								notesDeleted.addAll( folderFound.getNotes() );
								deleteFolder( folderFound );
								deleteNode( folderFound );
							}
						} else if ( folderRoot != null && Objects.equals(foldersSelectedReordered.size(), 1) == true )
							addMessagesBarError( LANG.get_LANG_PROGRAM().get_CAN_NOT_DELETE_ITEM() );
					}

					Set<Entry<Note, Notes>> notesSelectedSet = notesSelected.entrySet();
					Iterator<Entry<Note, Notes>> notesSelectedSetIt = notesSelectedSet.iterator();
					while ( notesSelectedSetIt.hasNext() ) {
						Entry<Note, Notes> notesSelectedEntry = notesSelectedSetIt.next();
						Note noteSelected = notesSelectedEntry.getKey();

						if ( noteSelected != null && notesDeleted.contains(noteSelected) == false ) {
							notesSelectedLength++;

							if ( files.deleteFile(noteSelected.getPathAbsolute()) ) {
								deleteNote( noteSelected );
								deleteNode( noteSelected );
								actionResultOk++;
							} else
								actionResultError++;
						}
						
						if ( progressBar2 != null )
							progressBar2.setValue( progressBar2.getValue() + 1 );
					}

					String actionResultStrOk = "";
					String actionResultStrError = "";
					if ( actionResultOk > 0 ) {
						actionResultStrOk 
							+= actionResultOk 
							+ "/" 
							+ notesSelectedLength 
							+ " " 
							+ ( ( actionResultOk > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_DELETED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_DELETED().toLowerCase() );

						addMessagesBarOk( actionResultStrOk );
					}
					if ( actionResultError > 0 ) {
						actionResultStrError 
							+= actionResultError 
							+ "/" 
							+ notesSelectedLength 
							+ " " 
							+ ( ( actionResultError > 1 ) ? LANG.get_LANG_PROGRAM().get_NOTES_NOT_DELETED().toLowerCase() : LANG.get_LANG_PROGRAM().get_NOTE_NOT_DELETED().toLowerCase() );

						addMessagesBarError( actionResultStrError );
					}

					foldersSelected.clear();
					notesSelected.clear();
					
					if ( viewLoading2 != null )
						viewLoading2.delete();

					refreshTreeAsync();
					refreshMessagesBarAsync();
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	private void deleteContent() {
		panelNoteContent.removeAll();
		SwingUtilities.updateComponentTreeUI( panelNoteContent );
	}
	
	//====================
	// end region delete
	//====================
	
	//====================
	// region search
	//====================
	
	public void searchInNotes( Object[] args ) throws Re01JLibException {
		resetMessagesBar();
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				String keyword = null;
				try {
					keyword = MethodHelper.getArgString( Prompt.get_ARG_KEY_PROMPT_RESULT(), args );
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
				if ( keyword != null ) {// if null, action was canceled by user
					if ( keyword.trim().isEmpty() == false ) {
						if ( keyword.length() <= Core.get_INPUT_VALUE_MAX_LENGTH() ) {
							int loadingStepsLength = 3;
							Loading viewLoading1 = null;
							try {
								Object[] argsNewLoading1 = new Object[]{ MethodHelper.createDefaultArgs() };
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 1, argsNewLoading1 );
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading1 );
								viewLoading1 = new Loading( LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES(), argsNewLoading1 );
								viewLoading1.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}

							int rootFolderNotesLength = countFolderNotes( folderRoot );
							
							if ( viewLoading1 != null )
								viewLoading1.delete();

							Loading viewLoading2 = null;
							try {
								Object[] argsNewLoading2 = new Object[]{ MethodHelper.createDefaultArgs() };
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 2, argsNewLoading2 );
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading2 );
								viewLoading2 = new Loading( LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES(), rootFolderNotesLength, argsNewLoading2 );
								viewLoading2.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}
							
							JProgressBar progressBar2 = null;
							if ( viewLoading2 != null )
								progressBar2 = viewLoading2.getProgressBar();

							Tuple folderResult = searchInFolder( folderRoot, keyword, progressBar2 );

							if ( viewLoading2 != null )
								viewLoading2.delete();

							LinkedHashMap<ImageIcon, String> messagesMap = new LinkedHashMap<ImageIcon, String>();

							ImageIcon imageIconInfo = images.get_GLOBAL_IMAGE_ICON_INFO();
							ImageIcon imageIconFolder = images.get_GLOBAL_IMAGE_ICON_FOLDER();
							ImageIcon imageIconFile = images.get_GLOBAL_IMAGE_ICON_FILE();

							messagesMap.put( 
								images.get_GLOBAL_IMAGE_ICON_SEARCH(), 
								LANG.get_LANG().get_RESULT() + 
								" " + 
								LANG.get_LANG().get_OF().toLowerCase() + 
								" \"" + 
								keyword + 
								"\"" 
							);

							ArrayList<Folder> folderResultInFoldersNames = (ArrayList<Folder>) folderResult.getOne();
							int folderResultInFoldersNamesLength = folderResultInFoldersNames.size();
							ArrayList<Note> folderResultInNotesNames = (ArrayList<Note>) folderResult.getTwo();
							int folderResultInNotesNamesLength = folderResultInNotesNames.size();
							ArrayList<Note> folderResultInNotesContents = (ArrayList<Note>) folderResult.getThree();
							int folderResultInNotesContentsLength = folderResultInNotesContents.size();

							Loading viewLoading3 = null;
							try {
								Object[] argsNewLoading3 = new Object[]{ MethodHelper.createDefaultArgs() };
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEP_ID(), 3, argsNewLoading3 );
								MethodHelper.addOrReplaceCallbackArg( Loading.get_ARG_KEY_STEPS_LENGTH(), loadingStepsLength, argsNewLoading3 );
								viewLoading3 = new Loading( 
									LANG.get_LANG_PROGRAM().get_SEARCH_IN_NOTES(), 
									folderResultInFoldersNamesLength + folderResultInNotesNamesLength + folderResultInNotesContentsLength, 
									argsNewLoading3 
								);
								viewLoading3.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}

							int progressBarValue3 = 0;
							JProgressBar progressBar3 = null;
							if ( viewLoading3 != null )
								progressBar3 = viewLoading3.getProgressBar();

							messagesMap.put( new ImageIcon(imageIconInfo.getImage()), LANG.get_LANG_PROGRAM().get_RESULTS_IN_FOLDERS_NAMES() + " (" + folderResultInFoldersNamesLength + ")" );

							if ( folderResultInFoldersNamesLength > 0 ) {
								Iterator<Folder> folderResultInFoldersNamesIt = folderResultInFoldersNames.iterator();
								while ( folderResultInFoldersNamesIt.hasNext() ) {
									Folder folderResultFolderFound = folderResultInFoldersNamesIt.next();
									
									messagesMap.put( new ImageIcon(imageIconFolder.getImage()), folderResultFolderFound.getPathAbsolute(true).replace(folderRoot.getPathAbsolute(false), "").replace("/", " / ").replace("\\", " \\ ") );

									progressBarValue3++;
									if ( progressBar3 != null )
										progressBar3.setValue( progressBarValue3 );
								}
							} else {
								messagesMap.put( new ImageIcon(imageIconFile.getImage()), LANG.get_LANG().get_RESULT() + " : " + "0" );

								progressBarValue3++;
								if ( progressBar3 != null )
									progressBar3.setValue( progressBarValue3 );
							}

							messagesMap.put( new ImageIcon(imageIconInfo.getImage()), LANG.get_LANG_PROGRAM().get_RESULTS_IN_NOTES_NAMES() + " (" + folderResultInNotesNamesLength + ")" );

							if ( folderResultInNotesNamesLength > 0 ) {
								Iterator<Note> folderResultInNotesNamesIt = folderResultInNotesNames.iterator();
								while ( folderResultInNotesNamesIt.hasNext() ) {
									Note noteFound = folderResultInNotesNamesIt.next();
									
									messagesMap.put( new ImageIcon(imageIconFile.getImage()), noteFound.getPathAbsolute().replace(folderRoot.getPathAbsolute(false), "").replace("/", " / ").replace("\\", " \\ ") );

									progressBarValue3++;
									if ( progressBar3 != null )
										progressBar3.setValue( progressBarValue3 );
								}
							} else {
								messagesMap.put( new ImageIcon(imageIconFile.getImage()), LANG.get_LANG().get_RESULT() + " : " + "0" );

								progressBarValue3++;
								if ( progressBar3 != null )
									progressBar3.setValue( progressBarValue3 );
							}

							messagesMap.put( new ImageIcon(imageIconInfo.getImage()), LANG.get_LANG_PROGRAM().get_RESULTS_IN_NOTES_CONTENTS() + " (" + folderResultInNotesContentsLength + ")" );

							if ( folderResultInNotesContentsLength > 0 ) {
								Iterator<Note> folderResultInNotesContentsIt = folderResultInNotesContents.iterator();
								while ( folderResultInNotesContentsIt.hasNext() ) {
									Note noteFound = folderResultInNotesContentsIt.next();
									
									messagesMap.put( new ImageIcon(imageIconFile.getImage()), noteFound.getPathAbsolute().replace(folderRoot.getPathAbsolute(false), "").replace("/", " / ").replace("\\", " \\ ") );

									progressBarValue3++;
									if ( progressBar3 != null )
										progressBar3.setValue( progressBarValue3 );
								}
							} else {
								messagesMap.put( new ImageIcon(imageIconFile.getImage()), LANG.get_LANG().get_RESULT() + " : " + "0" );

								progressBarValue3++;
								if ( progressBar3 != null )
									progressBar3.setValue( progressBarValue3 );
							}

							if ( viewLoading3 != null )
								viewLoading3.delete();

							try {
								Object[] argsNew = new Object[]{ MethodHelper.createDefaultArgs() };
								MethodHelper.addOrReplaceCallbackArg( Alert.get_ARG_KEY_ALERT_MESSAGE(), messagesMap, argsNew );
								Alert alert = new Alert( LANG.get_LANG().get_MESSAGES(), argsNew );
								alert.resume();
							} catch (Re01JLibException ex) {
								Core.get_LOGGER().write( ex );
							}

						} else
							addMessagesBarError( LANG.get_LANG().get_TEXT_KEYWORD_IS_TO_HIGH());
					} else
						addMessagesBarError( LANG.get_LANG().get_TEXT_KEYWORD_IS_EMPTY() );
				}
				
				refreshMessagesBarAsync();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	private Tuple searchInFolder( Folder folder, String keyword, JProgressBar progressBar ) {
		Tuple result = new Tuple();
		
		ArrayList<Folder> resultInFoldersNames = new ArrayList<>();
		ArrayList<Note> resultInNotesNames = new ArrayList<>();
		ArrayList<Note> resultInNotesContents = new ArrayList<>();
		
		Iterator<Note> folderNotesIt = folder.getNotes().iterator();
		while ( folderNotesIt.hasNext() ) {
			Note noteFound = folderNotesIt.next();
			if ( noteFound.getName().contains(keyword) )
				resultInNotesNames.add( noteFound );
			if ( noteFound.getContent().contains(keyword) )
				resultInNotesContents.add( noteFound );
			
			if ( progressBar != null )
				progressBar.setValue( progressBar.getValue() + 1 );
		}
		
		Iterator<Folder> folderFoldersIt = folder.getFolders().iterator();
		while ( folderFoldersIt.hasNext() ) {
			Folder folderFound = folderFoldersIt.next();
			if ( folderFound.getName().contains(keyword) )
				resultInFoldersNames.add( folderFound );
			
			Tuple folderResult = searchInFolder( folderFound, keyword, progressBar );
			
			Iterator<Folder> folderResultInFoldersNamesIt = ((ArrayList<Folder>)folderResult.getOne()).iterator();
			while ( folderResultInFoldersNamesIt.hasNext() ) {
				Folder folderResultFolderFound = folderResultInFoldersNamesIt.next();
				if ( resultInFoldersNames.contains(folderResultFolderFound) == false )
					resultInFoldersNames.add( folderResultFolderFound );
			}
			
			Iterator<Note> folderResultInNotesNamesIt = ((ArrayList<Note>)folderResult.getTwo()).iterator();
			while ( folderResultInNotesNamesIt.hasNext() ) {
				Note noteFound = folderResultInNotesNamesIt.next();
				if ( resultInNotesNames.contains(noteFound) == false )
					resultInNotesNames.add( noteFound );
			}
			
			Iterator<Note> folderResultInNotesContentsIt = ((ArrayList<Note>)folderResult.getThree()).iterator();
			while ( folderResultInNotesContentsIt.hasNext() ) {
				Note noteFound = folderResultInNotesContentsIt.next();
				if ( resultInNotesContents.contains(noteFound) == false )
					resultInNotesContents.add( noteFound );
			}
		}
		
		result.setOne( resultInFoldersNames );
		result.setTwo( resultInNotesNames );
		result.setThree( resultInNotesContents );
		
		return result;
	}
	
	//====================
	// end region search
	//====================
	
	private int countFolderNotes( Folder folder ) {
		int result = folder.getNotes().size();
		
		Iterator<Folder> folderFoldersIt = folder.getFolders().iterator();
		while ( folderFoldersIt.hasNext() ) {
			Folder folderFound = folderFoldersIt.next();
			result += countFolderNotes( folderFound );
		}
		
		return result;
	}
	
}
