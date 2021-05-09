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

package re01.jtextnotes.user;

import java.util.ArrayList;
import re01.design.font.FontStyleEnum;
import re01.jtextnotes.language.Language;

/**
 *
 * @author renaud
 */
public class Parameters {
	
	//===================
	// Program
	
	private static ArrayList<FontStyleEnum> fontsStyles = null;
	private static Language languageSelected = null;
	private static Boolean isLanguageSelected = null;
	private static Boolean isViewWelcomeDoNotDisplayAgain = null;
	private static Boolean isWorkDirectoryLastAutoSelect = null;
	private static String workDirectoryLastPath = null;
	
	//==================
	// Window
	
	private static Integer windowWidth = null;
	private static Integer windowHeight = null;
	
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
	// Program
	
	public static ArrayList<FontStyleEnum> getFontsStyles() {
		return fontsStyles;
	}

	public static void setFontsStyles(ArrayList<FontStyleEnum> fontsStyles) {
		Parameters.fontsStyles = fontsStyles;
	}

	public static Language getLanguageSelected() {
		return languageSelected;
	}

	public static void setLanguageSelected(Language languageSelected) {
		Parameters.languageSelected = languageSelected;
	}

	public static Boolean getIsLanguageSelected() {
		return isLanguageSelected;
	}

	public static void setIsLanguageSelected( Boolean isLanguageSelected ) {
		Parameters.isLanguageSelected = isLanguageSelected;
	}

	public static Boolean getIsViewWelcomeDoNotDisplayAgain() {
		return isViewWelcomeDoNotDisplayAgain;
	}

	public static void setIsViewWelcomeDoNotDisplayAgain( Boolean isViewWelcomeDoNotDisplayAgain ) {
		Parameters.isViewWelcomeDoNotDisplayAgain = isViewWelcomeDoNotDisplayAgain;
	}

	public static Boolean getIsWorkDirectoryLastAutoSelect() {
		return isWorkDirectoryLastAutoSelect;
	}

	public static void setIsWorkDirectoryLastAutoSelect( Boolean isWorkDirectoryLastAutoSelect ) {
		Parameters.isWorkDirectoryLastAutoSelect = isWorkDirectoryLastAutoSelect;
	}

	public static String getWorkDirectoryLastPath() {
		return workDirectoryLastPath;
	}

	public static void setWorkDirectoryLastPath( String workDirectoryLastPath ) {
		Parameters.workDirectoryLastPath = workDirectoryLastPath;
	}
	
	//==================
	// Window

	public static Integer getWindowWidth() {
		return windowWidth;
	}

	public static void setWindowWidth( Integer windowWidth ) {
		Parameters.windowWidth = windowWidth;
	}

	public static Integer getWindowHeight() {
		return windowHeight;
	}

	public static void setWindowHeight( Integer windowHeight ) {
		Parameters.windowHeight = windowHeight;
	}
	
}
