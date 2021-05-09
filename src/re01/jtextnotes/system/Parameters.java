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

package re01.jtextnotes.system;

import java.util.ArrayList;
import java.util.Arrays;
import re01.design.font.FontStyleEnum;
import re01.design.theme.ThemeTypeEnum;
import re01.language.LanguageTypeEnum;

/**
 *
 * @author renaud
 */
public class Parameters {
	
	//====================
	// Program
	
	public static ThemeTypeEnum getThemeType() {
		return ( 
			re01.environment.Parameters.getThemeSelected() != null 
			&& re01.environment.Parameters.getThemeSelected().getThemeType() != null 
		) ? re01.environment.Parameters.getThemeSelected().getThemeType() : re01.jtextnotes.program.Parameters.get_THEME_TYPE() ;
	}
	
	public static LanguageTypeEnum getLanguageType() {
		return ( 
			re01.environment.Parameters.getLanguageSelected() != null 
			&& re01.environment.Parameters.getLanguageSelected().get_LANG_TYPE() != null 
		) ? re01.environment.Parameters.getLanguageSelected().get_LANG_TYPE() : re01.jtextnotes.program.Parameters.get_LANGUAGE_TYPE();
	}
	
	public static ArrayList<FontStyleEnum> getFontsStyles() {
		return ( re01.jtextnotes.user.Parameters.getFontsStyles() != null ) ? re01.jtextnotes.user.Parameters.getFontsStyles() : new ArrayList<FontStyleEnum>( Arrays.asList(re01.jtextnotes.program.Parameters.get_FONT_STYLE()) );
	}

	//====================
	// Window
	
	public static Integer get_window_width() {
		return ( re01.jtextnotes.user.Parameters.getWindowWidth() != null ) ? re01.jtextnotes.user.Parameters.getWindowWidth() : re01.environment.Parameters.get_DEFAULT_WINDOW_WIDTH();
	}

	public static Integer get_window_height() {
		return ( re01.jtextnotes.user.Parameters.getWindowHeight() != null ) ? re01.jtextnotes.user.Parameters.getWindowHeight() : re01.environment.Parameters.get_DEFAULT_WINDOW_HEIGHT();
	}
	
}
