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
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Box;
import re01.design.color.ColorTypeEnum;
import re01.design.font.FontStyleEnum;
import re01.design.view.awt.BorderLayout;
import re01.design.view.awt.GridLayout;
import re01.design.view.component.Paragraph;
import re01.design.view.component.Title1;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JCheckBox;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JScrollPane;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.system.JTextNotes;

/**
 *
 * @author renaud
 */
public class Welcome extends Global {

	private static final String ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN = "view_welcome_check_do_not_display_again";
	
	private WindowAdapter windowAdapter = null;
	
	public Welcome( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				JTextNotes.programExit( null );
				
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
		
		Title1 titleWelcome = new Title1( LANG.get_LANG().get_WELCOME() );
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
		
		Paragraph pWelcome = new Paragraph( "" );
		pWelcome.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold)), null, null) );
		pWelcome.addText( LANG.get_LANG_PROGRAM().get_PROGRAM_NAME(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold, FontStyleEnum.Title2)), ColorTypeEnum.Blue, null) );
		pWelcome.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_2(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold)), null, null) );
		
		subPanelCenter.add( pWelcome );
		
		Paragraph pWelcome2 = new Paragraph( "" );
		pWelcome2.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_3(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Default)), null, null) );
		pWelcome2.addText( LANG.get_LANG().get_LICENSE_GNU(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Default)), null, null) );
		pWelcome2.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_4(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Default)), null, null) );
		pWelcome2.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_5(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Italic)), null, null) );
		pWelcome2.addText( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_6(), re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Default)), null, null) );
		subPanelCenter.add( pWelcome2 );
		
		Paragraph pWelcome3 = new Paragraph( LANG.get_LANG_PROGRAM().get_TEXT_WELCOME_7() );
		subPanelCenter.add( pWelcome3 );
		
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
		panelSouth.setLayout( new GridLayout( 2, 1 ) );
		
		JCheckBox checkDoNotDisplayAgain = new JCheckBox( LANG.get_LANG().get_DO_NOT_DISPLAY_WINDOW_AGAIN() );
		
		Object checkObj = MethodHelper.getArg( ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN, THIS.getCallbacksArgs() );
		try {
			Boolean checkBool = ( Boolean ) checkObj;
			checkDoNotDisplayAgain.setSelected( checkBool );
		} catch ( Exception e ) { }
		
		panelSouth.add( checkDoNotDisplayAgain );
		
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
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN, checkDoNotDisplayAgain.isSelected(), THIS.getCallbacksArgs() );
					MethodHelper.executeCallbacks( THIS.getCallbacksArgs() );
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

	public static final String get_ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN() {
		return ARG_KEY_CHECK_DO_NOT_DISPLAY_AGAIN;
	}
	
}
