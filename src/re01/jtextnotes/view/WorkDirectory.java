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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.Box;
import re01.design.font.FontStyleEnum;
import re01.design.view.awt.BorderLayout;
import re01.design.view.awt.GridLayout;
import re01.design.view.component.Paragraph;
import re01.design.view.component.Title1;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JFileChooser;
import re01.design.view.swing.JLabel;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JScrollPane;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.design.image.Images;
import re01.jtextnotes.program.Core;

/**
 *
 * @author renaud
 */
public class WorkDirectory extends Global {
	
	private static final String ARG_KEY_WORK_DIRECTORY_PATH = "view_work_directory_work_directory_path";
	private static final String ARG_KEY_IS_VIEW_VALIDATED = "view_work_directory_is_view_validated";
	
	private WindowAdapter windowAdapter = null;
	
	public WorkDirectory(String viewTitle, Object[] callbacksArgs) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				
				super.windowClosing( windowEvent );
			}
		};
		this.addWindowListener( windowAdapter );
		
		this.getContentPane().setLayout( new BorderLayout() );
		this.setPreferredSize( new Dimension( re01.environment.Parameters.get_RECOMMENDED_POPUP_WINDOW_WIDTH(), re01.environment.Parameters.get_RECOMMENDED_POPUP_WINDOW_HEIGHT() ) );
		isResizable = true;
		
		//====================
		// region north
		//====================
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout( new BoxLayout( panelNorth, BoxLayout.PAGE_AXIS ) );
		
		Title1 titleWelcome = new Title1( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY() );
		panelNorth.add( titleWelcome );
		
		this.add( panelNorth, "North" );
		
		//====================
		// end region north
		//====================
		
		//====================
		// region center
		//====================
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout( new BorderLayout() );
		
		JPanel subPanelCenter = new JPanel();
		subPanelCenter.setLayout( new BoxLayout( subPanelCenter, BoxLayout.PAGE_AXIS ) );
		
		Paragraph pInfo = new Paragraph( "" );
		pInfo.addText(LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION(), re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null) );
		pInfo.addText( LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_2(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.OsFolder)), null, null) );
		pInfo.addText(LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_3(), re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null) );
		pInfo.addText( LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_4(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold)), null, null) );
		pInfo.addText(LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_5(), re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null) );
		pInfo.addText( LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_6(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.OsFolder)), null, null) );
		pInfo.addText(LANG.get_LANG_PROGRAM().get_INFO_WORK_DIRECTORY_SELECTION_7(), re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null) );
		subPanelCenter.add( pInfo );
		
		String workDirectoryPath = MethodHelper.getArgString( ARG_KEY_WORK_DIRECTORY_PATH, THIS.getCallbacksArgs() );
		
		JPanel pathPanel = new JPanel();
		pathPanel.setLayout( new BoxLayout( pathPanel, BoxLayout.LINE_AXIS ) );
		
		JButton buttonPathSelect = new JButton( LANG.get_LANG().get_CHOOSE() );
		buttonPathSelect.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				JFileChooser chooser = null;
				if ( workDirectoryPath != null )
					chooser = new JFileChooser( workDirectoryPath );
				else if ( re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() != null ) {
					java.io.File directoryLastPath = new java.io.File( re01.jtextnotes.user.Parameters.getWorkDirectoryLastPath() );
					if ( directoryLastPath != null && directoryLastPath.getParent() != null )
						chooser = new JFileChooser( directoryLastPath.getParent() );
				}
				if ( chooser == null )
					chooser = new JFileChooser();
				
				chooser.setDialogTitle( "" );
				chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
				chooser.setAcceptAllFileFilterUsed( false );
				Integer showOpenDialog = chooser.showOpenDialog( THIS );
				
				if ( Objects.equals(showOpenDialog, JFileChooser.APPROVE_OPTION) == true ) {
					THIS.delete();
					try {
						MethodHelper.addOrReplaceCallbackArg( JFileChooser.get_ARG_KEY_PATH_SELECTED(), chooser.getSelectedFile().getPath(), THIS.getCallbacksArgs() );
						MethodHelper.executeCallbacks( new String[]{ "createViewWorkDirectory" }, THIS.getCallbacksArgs() );
					} catch ( Exception ex ) {
						Core.get_LOGGER().write( ex );
					}
				}
			}
		} );
		pathPanel.add( buttonPathSelect );
		
		Images icons = new Images();
		JLabel pathImgLabel = new JLabel( icons.get_GLOBAL_IMAGE_ICON_FOLDER() );
		pathPanel.add( pathImgLabel );
		
		Paragraph pWorkPath = new Paragraph( ( workDirectoryPath != null ) ? workDirectoryPath.replace("/", " / ").replace("\\", " \\ ") : LANG.get_LANG().get_TEXT_INFO_FILE_BUTTON_SELECT() );
		pathPanel.add( pWorkPath );
		
		subPanelCenter.add( pathPanel );
		
		panelCenter.add( subPanelCenter, "North" );
		
		scrollCenter = new JScrollPane( panelCenter );
		
		this.add( scrollCenter, "Center" );
		
		//====================
		// end region center
		//====================
		
		//====================
		// region south
		//====================
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout( new GridLayout( 1, 1 ) );
		
		JPanel subPanelSouth = new JPanel();
		subPanelSouth.setLayout( new BoxLayout( subPanelSouth, BoxLayout.LINE_AXIS ) );
		subPanelSouth.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
		
		subPanelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		JButton buttonOk = new JButton( LANG.get_LANG().get_START() );
		buttonOk.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				THIS.delete();
				try {
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_IS_VIEW_VALIDATED, true, THIS.getCallbacksArgs() );
					MethodHelper.executeCallbacks( new String[]{ "createViewWorkDirectoryOrNotes" }, THIS.getCallbacksArgs() );
				} catch (Exception ex) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		subPanelSouth.add( buttonOk );
		
		subPanelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		panelSouth.add( subPanelSouth );
		
		this.add( panelSouth, "South" );
		
		//====================
		// end region south
		//====================
		
		display();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			this.removeWindowListener( windowAdapter );
		} catch (Exception e) { }
		
		super.finalize();
	}
	
	public static final String get_ARG_KEY_WORK_DIRECTORY_PATH() {
		return ARG_KEY_WORK_DIRECTORY_PATH;
	}

	public static final String get_ARG_KEY_IS_VIEW_VALIDATED() {
		return ARG_KEY_IS_VIEW_VALIDATED;
	}
	
}
