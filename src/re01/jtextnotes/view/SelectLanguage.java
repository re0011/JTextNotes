/*
 * Copyright (C) 2020 LE CLERRE Renaud
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
import javax.swing.Box;
import re01.design.view.awt.BorderLayout;
import re01.design.view.component.Paragraph;
import re01.design.view.component.Title1;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JComboBox;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JScrollPane;
import re01.design.view.swing.jcombobox.JComboBoxTypeEnum;
import re01.exception.Re01JLibException;
import re01.language.Language;
import re01.language.LanguageTypeEnum;
import re01.environment.Parameters;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.system.JTextNotes;

/**
 *
 * @author renaud
 */
public class SelectLanguage extends Global {
	
	private static final String ARG_KEY_CURRENT_SELECTED_LANGUAGE_TYPE = "view_select_language_current_selected_language_type";
	private static final String ARG_KEY_IS_OPEN_WORK_DIRECTORY_AFTER_SELECT = "view_select_language_open_work_directory_after_select";
	
	private WindowAdapter windowAdapter = null;
	
	public SelectLanguage( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				JTextNotes.setViewSelectLanguage( null );
				
				super.windowClosing( windowEvent );
			}
		};
		this.addWindowListener( windowAdapter );
		
		this.getContentPane().setLayout( new BorderLayout() );
		this.setPreferredSize( new Dimension( re01.environment.Parameters.get_RECOMMENDED_WINDOW_WIDTH(), re01.environment.Parameters.get_RECOMMENDED_WINDOW_HEIGHT() ) );
		isResizable = true;
		
		//====================
		// region north
		//====================
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout( new BoxLayout( panelNorth, BoxLayout.PAGE_AXIS ) );
		
		Title1 titleLabel = new Title1( LANG.get_LANG().get_SELECT_LANGUAGE() );
		panelNorth.add( titleLabel );
		
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
		
		LanguageTypeEnum[] languagesTypes = LanguageTypeEnum.values();
		int languagesTypesLength = languagesTypes.length;
		String[] langs = new String[languagesTypesLength];
		int langsDefaultIndex = 0;
		for ( int i = 0; i < languagesTypesLength; i++ ) {
			LanguageTypeEnum languageType = languagesTypes[i];
			switch ( languageType ) {
				case English :
					langs[i] = LANG.get_LANG().get_ENGLISH();
					break;
				case French :
					langs[i] = LANG.get_LANG().get_FRENCH();
					break;
			}
			if ( LANG.get_LANG_TYPE() == languageType )
				langsDefaultIndex = i;
		}
		
		JComboBox langsCombobox = new JComboBox( JComboBoxTypeEnum.Default, langs, null, 3 );
		langsCombobox.setSelectedIndex( langsDefaultIndex );
		langsCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				
				String langSelected = (String) source.getSelectedItem();
				if ( langSelected.equals(LANG.get_LANG().get_ENGLISH()) == true ) {
					Parameters.setLanguageSelected( new Language(LanguageTypeEnum.English) );
					re01.jtextnotes.user.Parameters.setLanguageSelected( new re01.jtextnotes.language.Language(LanguageTypeEnum.English) );
				} else if ( langSelected.equals(LANG.get_LANG().get_FRENCH()) == true ) {
					Parameters.setLanguageSelected( new Language(LanguageTypeEnum.French) );
					re01.jtextnotes.user.Parameters.setLanguageSelected( new re01.jtextnotes.language.Language(LanguageTypeEnum.French) );
				}
				
				THIS.delete();
				try {
					SelectLanguage view = new SelectLanguage(viewTitle, THIS.getCallbacksArgs());
					JTextNotes.setViewSelectLanguage( view );
					view.resume();
				} catch (Re01JLibException ex) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		subPanelCenter.add( langsCombobox );
		
		Paragraph pAboutSelectLanguage = new Paragraph( LANG.get_LANG_PROGRAM().get_SELECT_LANGUAGE_ABOUT() );
		subPanelCenter.add( pAboutSelectLanguage );
		
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
		panelSouth.setLayout( new BoxLayout( panelSouth, BoxLayout.LINE_AXIS ) );
		panelSouth.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
		
		panelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		JButton buttonOk = new JButton( LANG.get_LANG().get_OK() );
		buttonOk.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				re01.jtextnotes.user.Parameters.setIsLanguageSelected( true );
				THIS.delete();
				JTextNotes.setViewSelectLanguage( null );
				try {
					MethodHelper.executeCallbacks( THIS.getCallbacksArgs() );
				} catch ( Exception ex ) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		panelSouth.add( buttonOk );
		
		panelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
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

	public static final String get_ARG_KEY_CURRENT_SELECTED_LANGUAGE_TYPE() {
		return ARG_KEY_CURRENT_SELECTED_LANGUAGE_TYPE;
	}

	public static final String get_ARG_KEY_IS_OPEN_WORK_DIRECTORY_AFTER_SELECT() {
		return ARG_KEY_IS_OPEN_WORK_DIRECTORY_AFTER_SELECT;
	}
	
}
