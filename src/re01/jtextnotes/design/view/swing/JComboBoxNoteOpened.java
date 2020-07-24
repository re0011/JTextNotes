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

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JComboBox;
import re01.design.view.swing.JScrollPane;
import re01.design.view.swing.jcombobox.JComboBoxTypeEnum;
import re01.design.view.swing.jscrollbar.ScrollBarVerticalComboBoxSlimUI;

/**
 *
 * @author renaud
 */
public class JComboBoxNoteOpened extends JComboBox {
	
	public JComboBoxNoteOpened( JComboBoxTypeEnum comboboxType, String[] items, Integer maxWidth, Integer rowCount ) {
		super( comboboxType, items, maxWidth, rowCount );
	}
	
	public JComboBoxNoteOpened( JComboBoxTypeEnum comboboxType, Integer maxWidth, Integer rowCount ) {
		super( comboboxType, maxWidth, rowCount );
	}
	
	public JComboBoxNoteOpened( JComboBoxTypeEnum comboboxType, Integer maxWidth ) {
		super( comboboxType, maxWidth );
	}
	
	@Override
	public void updateUI() {
		super.updateUI();
		UIManager.put( "ComboBox.squareButton", false );
		setUI( new BasicComboBoxUI(){
			@Override
			protected JButton createArrowButton() {
				JButton button = new JButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setVisible(false);
				return button;
			}

			@Override
			protected ComboPopup createPopup() {
				return new BasicComboPopup(comboBox) {
					@Override
					protected JScrollPane createScroller() {
						JScrollPane scrollPane = new JScrollPane( list );
						scrollPane.getVerticalScrollBar().setUI( new ScrollBarVerticalComboBoxSlimUI() );
						return scrollPane;
					}
				};
			}
		} );
	}
	
}
