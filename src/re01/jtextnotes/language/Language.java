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

package re01.jtextnotes.language;

import re01.language.LanguageTypeEnum;

/**
 *
 * @author renaud
 */
public class Language extends re01.language.Language {
	
	private final Global LANG_PROGRAM;
	
	public Language( LanguageTypeEnum langType ) {
		super( langType );
		
		switch ( this.LANG_TYPE ) {
			case English:
				LANG_PROGRAM = new English();
				break;
			case French:
				LANG_PROGRAM = new French();
				break;
			default:
				LANG_PROGRAM = new English();
				break;
		}
	}

	public Global get_LANG_PROGRAM() {
		return LANG_PROGRAM;
	}
	
}
