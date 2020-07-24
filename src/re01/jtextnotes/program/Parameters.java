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

package re01.jtextnotes.program;

import re01.design.font.FontStyleEnum;
import re01.design.theme.ThemeTypeEnum;
import re01.language.LanguageTypeEnum;
import re01.language.Languages;

/**
 *
 * @author renaud
 */
public class Parameters {
	
	//===================
	// Language
	
	private static final Boolean IS_LANGUAGE_SELECTED = false;
	
	//===================
	// Program
	
	private static final ThemeTypeEnum THEME_TYPE = ThemeTypeEnum.MetalSlate;
	private static final LanguageTypeEnum SYSTEM_LANGUAGE_TYPE = new Languages().getSystemLanguageType();
	private static final LanguageTypeEnum LANGUAGE_TYPE = ( SYSTEM_LANGUAGE_TYPE != null ) ? SYSTEM_LANGUAGE_TYPE : LanguageTypeEnum.English ;
	private static final FontStyleEnum FONT_STYLE = FontStyleEnum.Default;
	
	private static final Boolean IS_WORK_DIRECTORY_LAST_AUTO_SELECT = true;
	private static final Boolean IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN = false;
	
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	// Getters & Setters
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================
	//=============================

	//===================
	// Language

	public static Boolean get_IS_LANGUAGE_SELECTED() {
		return IS_LANGUAGE_SELECTED;
	}
	
	//=====================
	// Program

	public static ThemeTypeEnum get_THEME_TYPE() {
		return THEME_TYPE;
	}
	
	public static LanguageTypeEnum get_LANGUAGE_TYPE() {
		return LANGUAGE_TYPE;
	}
	
	public static FontStyleEnum get_FONT_STYLE() {
		return FONT_STYLE;
	}

	public static Boolean get_IS_WORK_DIRECTORY_LAST_AUTO_SELECT() {
		return IS_WORK_DIRECTORY_LAST_AUTO_SELECT;
	}

	public static Boolean get_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN() {
		return IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN;
	}

}
