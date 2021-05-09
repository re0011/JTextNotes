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

import re01.exception.Re01JLibException;
import re01.jtextnotes.language.Language;

/**
 *
 * @author renaud
 */
public class Global extends re01.design.view.frame.Global {
	
	protected final Global THIS;
	protected final Language LANG;
	
	public Global( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		THIS = this;
		LANG = re01.jtextnotes.user.Parameters.getLanguageSelected();
		
		if ( viewTitle != null )
			setTitle( viewTitle + " - " + LANG.get_LANG_PROGRAM().get_PROGRAM_NAME() );
	}

	protected Object[] getCallbacksArgs() {
		return callbacksArgs;
	}
	
	public void setCallbacksArgs(Object[] callbacksArgs) {
		this.callbacksArgs = callbacksArgs;
	}

	protected Boolean getIsResizable() {
		return isResizable;
	}

	public Language get_LANG() {
		return LANG;
	}
	
}
