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

package re01.jtextnotes.design.view.swing;

import java.awt.Insets;
import javax.swing.Icon;
import re01.design.view.swing.JButton;

/**
 *
 * @author renaud
 */
public class JButtonInputInt extends JButton {
	
	public JButtonInputInt() {
		super();
		construct();
	}
	
	public JButtonInputInt( String text ) {
		super( text );
		construct();
	}
	
	public JButtonInputInt( Icon icon ) {
		super( icon );
		construct();
	}
	
	public JButtonInputInt( String text, Icon icon ) {
		super( text, icon );
		construct();
	}
	
	private void construct() {
		this.setMargin(new Insets(
			re01.environment.Parameters.createSpacingSize( new Float(2) / new Float(1920) ), 
			re01.environment.Parameters.createSpacingSize( new Float(4) / new Float(1920) ), 
			re01.environment.Parameters.createSpacingSize( new Float(2) / new Float(1920) ), 
			re01.environment.Parameters.createSpacingSize( new Float(4) / new Float(1920) )
		));
	}
	
}
