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

package re01.jtextnotes.design.view.swing;

import re01.design.font.Font;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.view.Notes;

/**
 *
 * @author renaud
 */
public class JTextPane extends re01.design.view.swing.JTextPane {
	
	private static final String ARG_KEY_CALLBACKS_SET_NOTE_OPENED_IS_SAVED = "design_view_swing_set_not_opened_is_saved";
	
	public JTextPane( String text, boolean enableCopyActionWithStyle, boolean enablePasteAction, Object[] callbacksArgs ) throws Re01JLibException {
		super( text, enableCopyActionWithStyle, enablePasteAction, callbacksArgs );
	}
	
	public JTextPane( String text, Font font, boolean enableCopyActionWithStyle, boolean enablePasteAction, Object[] callbacksArgs ) throws Re01JLibException {
		super( text, font, enableCopyActionWithStyle, enablePasteAction, callbacksArgs );
	}

	public static final String get_ARG_KEY_CALLBACKS_SET_NOTE_OPENED_IS_SAVED() {
		return ARG_KEY_CALLBACKS_SET_NOTE_OPENED_IS_SAVED;
	}
	
	@Override
	public void paste() {
		super.paste();
		if ( Notes.getCopiedStyle() != null ) {
			try {
				setCharacterAttributes( Notes.getCopiedStyle(), false, selectionStartIndex, null, null );
			} catch (Re01JLibException ex) {
				Core.get_LOGGER().write( ex );
			}
		}
		
		try {
			Object[] args = (Object[]) MethodHelper.getArg( ARG_KEY_CALLBACKS_SET_NOTE_OPENED_IS_SAVED, callbacksArgs );
			MethodHelper.executeCallbacks( args );
		} catch (Exception ex) {
			Core.get_LOGGER().write( ex );
		}
	}
	
}
