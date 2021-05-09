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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import re01.design.view.awt.GridLayout;
import re01.design.view.component.Title3;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JPanel;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.program.Core;

/**
 *
 * @author renaud
 */
public class WorkDirectoryLast extends Global {
	
	private static final String ARG_KEY_WORK_DIRECTORY_LAST_PATH = "view_work_directory_last_path";
	
	private WindowAdapter windowAdapter = null;
	
	public WorkDirectoryLast( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		construct();
	}
	
	private void construct() throws Re01JLibException {
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				
				super.windowClosing( windowEvent );
			}
		};
		this.addWindowListener( windowAdapter );
		
		this.setPreferredSize( new Dimension( re01.environment.Parameters.get_RECOMMENDED_POPUP_WINDOW_WIDTH(), re01.environment.Parameters.get_RECOMMENDED_POPUP_WINDOW_HEIGHT() ) );
		
		JPanel panelContainer = new JPanel();
		panelContainer.setLayout( new GridLayout( 2, 1 ) );
		
		Title3 titleWorkDirectoryLastOpened = new Title3( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_LAST_OPENED() );
		panelContainer.add( titleWorkDirectoryLastOpened );
		
		JButton buttonWorkDirectoryLastOpened = new JButton(
			( String ) MethodHelper.getArg( ARG_KEY_WORK_DIRECTORY_LAST_PATH, callbacksArgs )
		);
		buttonWorkDirectoryLastOpened.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				THIS.delete();
				try {
					MethodHelper.executeCallbacks( new String[]{ "createViewNotes" }, THIS.getCallbacksArgs() );
				} catch ( Exception ex ) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		panelContainer.add( buttonWorkDirectoryLastOpened );
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout( new GridLayout( 2, 1 ) );
		
		Title3 titleWorkDirectoryOpenNew = new Title3( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_OPEN_NEW() );
		panelSouth.add( titleWorkDirectoryOpenNew );
		
		JButton buttonWorkDirectoryOpenNew = new JButton(
			LANG.get_LANG().get_OPEN() 
		);
		buttonWorkDirectoryOpenNew.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				THIS.delete();
				try {
					MethodHelper.executeCallbacks( new String[]{ "createViewWorkDirectory" }, THIS.getCallbacksArgs() );
				} catch ( Exception ex ) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		panelSouth.add( buttonWorkDirectoryOpenNew );
		
		this.getContentPane().add( panelContainer, "North" );
		this.getContentPane().add( panelSouth, "South" );
		
		display();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			this.removeWindowListener( windowAdapter );
		} catch (Exception e) { }
		
		super.finalize();
	}

	public static final String get_ARG_KEY_WORK_DIRECTORY_LAST_PATH() {
		return ARG_KEY_WORK_DIRECTORY_LAST_PATH;
	}
	
}
