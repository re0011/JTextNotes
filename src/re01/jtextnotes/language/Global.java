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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

/**
 *
 * @author renaud
 */
public abstract class Global {
	
	//==================
	// Common
	
	protected String NOTES_FOLDER;
	protected String WORK_DIRECTORY;
	protected String KEYBOARD_SHORTCUTS_IN_TREE;
	protected String KEYBOARD_SHORTCUTS_IN_NOTE;
	
	//==================
	// Files tree
	
	protected String CAN_NOT_DELETE_ITEM;
	protected String CAN_NOT_RENAME_ITEM;
	protected String FOLDER_ICON;
	protected String FOLDER_ICON_SHORT;
	protected String NOTE_ICON;
	protected String NOTE_ICON_SHORT;
	protected String NEW_FOLDER;
	protected String NEW_FOLDER_ALREADY_EXIST;
	protected String RENAME_FOLDER;
	protected String FOLDER_DO_NOT_EXIST;
	protected String NEW_NOTE;
	protected String NEW_NOTE_ALREADY_EXIST;
	protected String RENAME_NOTE;
	protected String NOTE_DO_NOT_EXIST;
	
	protected String NOTE;
	protected String NOTE_OPENED;
	protected String NOTES;
	protected String NOTE_COPIED;
	protected String NOTE_MOVED;
	protected String NOTE_RENAMED;
	protected String NOTE_SAVED;
	protected String NOTE_NOT_SAVED;
	protected String NOTES_COPIED;
	protected String NOTES_MOVED;
	protected String NOTES_RENAMED;
	protected String NOTES_SAVED;
	protected String NOTES_NOT_SAVED;
	protected String NOTE_DELETED;
	protected String NOTE_NOT_DELETED;
	protected String NOTES_DELETED;
	protected String NOTES_NOT_DELETED;
	
	protected String DELETE_NOTE;
	protected String DELETE_NOTES;
	protected String SAVE_NOTE;
	protected String SAVE_NOTES;
	
	//=======================
	// Global text
	
	protected String TEXT_WELCOME;
	protected String TEXT_WELCOME_2;
	protected String TEXT_WELCOME_3;
	protected String TEXT_WELCOME_4;
	protected String TEXT_WELCOME_5;
	protected String TEXT_WELCOME_6;
	protected String TEXT_WELCOME_7;
	
	protected String CHARS_BASE_SIZE_PARAM_INFO;
	protected String INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO;
	
	protected String ICONS_SIZE_PARAM_INFO;
	protected String INPUT_POPUP_ICONS_SIZE_PARAM_INFO;
	protected String ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO;
	protected String ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO;
	
	protected String FILES_PARAMS_AVAILABILITY;
	
	protected String INFO_WORK_DIRECTORY_SELECTED;
	protected String INFO_WORK_DIRECTORY_SELECTION;
	protected String INFO_WORK_DIRECTORY_SELECTION_2;
	protected String INFO_WORK_DIRECTORY_SELECTION_3;
	protected String INFO_WORK_DIRECTORY_SELECTION_4;
	protected String INFO_WORK_DIRECTORY_SELECTION_5;
	protected String INFO_WORK_DIRECTORY_SELECTION_6;
	protected String INFO_WORK_DIRECTORY_SELECTION_7;
	
	protected String ABOUT_LIBRARY_RE01JLIB;
	
	protected String HELP_WHAT_IS_JTEXTNOTES;
	protected String HELP_OPEN_MULTIPLES_WORK_DIRECTORIES;
	protected String HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2;
	protected String HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3;
	protected String HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4;
	protected String HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5;
	protected String HELP_NOTE_OPENED_IN_TREE;
	protected String HELP_NOTE_OPENED_IN_TREE_2;
	protected String HELP_NOTE_OPENED_IN_TREE_3;
	protected String HELP_NOTES_ARE_SIMPLE_FILES;
	protected String HELP_SAVE_NOTES_TO_EXTERNAL_HDD;
	protected String HELP_CLOSE_WINDOW_WITH_CROSS;
	protected String HELP_CLOSE_WORK_DIRECTORY;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_ENTER;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S;
	protected String HELP_KEYBOARD_SHORTCUT_TREE_DEL;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z;
	protected String HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y;
	
	protected String LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE;
	
	protected String ROOT_FOLDER_NOTES_FOUND;
	protected String ROOT_FOLDER_NOTES_NOT_FOUND;
	protected String ROOT_FOLDER_NOTES_SELECTED;
	
	protected String NEED_SELECT_A_FOLDER_TO_ACTION;
	protected String NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION;
	
	protected String CONFIRM_SAVE_FOLDER_NOTES;
	protected String CONFIRM_SAVE_NOTE;
	protected String CONFIRM_SAVE_NOTES;
	protected String CONFIRM_DELETE_FOLDER_NOTES;
	protected String CONFIRM_DELETE_NOTE;
	protected String CONFIRM_DELETE_NOTES;
	protected String CONFIRM_CLOSE_WORK_DIRECTORY;
	protected String CONFIRM_EXIT_PROGRAM;
	
	protected String PROMPT_NEW_FOLDER;
	protected String PROMPT_RENAME_FOLDER;
	protected String PROMPT_NEW_NOTE;
	protected String PROMPT_RENAME_NOTE;
	protected String PROMPT_SEARCH_IN_NOTES;
	
	protected String RESULTS_IN_FOLDERS_NAMES;
	protected String RESULTS_IN_NOTES_NAMES;
	protected String RESULTS_IN_NOTES_CONTENTS;
	
	protected String SEARCH_IN_NOTE;
	protected String SEARCH_IN_NOTES;
	
	protected String EVERY_CHARS_NOT_ALLOWED;
	
	protected String WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN;
	
	protected String WORK_DIRECTORY_REQUIRED;
	protected String WORK_DIRECTORY_LAST_OPENED;
	protected String WORK_DIRECTORY_IS_LAST_AUTO_OPEN;
	protected String WORK_DIRECTORY_OPEN_NEW;
	protected String WORK_DIRECTORY_CLOSE;
	protected String WORK_DIRECTORY_PART_OPENED;
	
	//=====================
	// Languages
	
	protected String SELECT_LANGUAGE_ABOUT;
	
	//===================
	// Program
	
	protected String PROGRAM_NAME;
	protected HashSet<Character> ELEMENTS_NAMES_CHARS_ALLOWED = new HashSet<>();
	
	//=====================
	// KeyEvents
	
	protected int SEARCH_IN_NOTE_KEYEVENT;
	protected int SEARCH_IN_NOTES_KEYEVENT;
	
	protected int WORK_DIRECTORY_OPEN_NEW_KEYEVENT;
	protected int WORK_DIRECTORY_CLOSE_KEYEVENT;
	
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	// Constructors
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================
	//========================

	public Global() {
		ELEMENTS_NAMES_CHARS_ALLOWED.clear();
		
		HashSet<Character> allowedChars = new HashSet<>();
//		allowedChars.addAll(
//			Arrays.asList(
//			'a',
//			'b',
//			'c',
//			'd',
//			'e',
//			'f',
//			'g',
//			'h',
//			'i',
//			'j',
//			'k',
//			'l',
//			'm',
//			'n',
//			'o',
//			'p',
//			'q',
//			'r',
//			's',
//			't',
//			'u',
//			'v',
//			'w',
//			'x',
//			'y',
//			'z',
//			//-----
//			'é',
//			'è',
//			'ç',
//			'à',
//			'ù',
//			//-----
//			'0',
//			'1',
//			'2',
//			'3',
//			'4',
//			'5',
//			'6',
//			'7',
//			'8',
//			'9',
//			'²',
//			//-----
//			'!',
//			'?',
//			'*',
//			'%',
//			'$',
//			'£',
//			'€',
//			'&',
//			'§',
//			'µ',
//			//====================
//			// region universal
//			//====================
//			' ',
//			'\'',
//			'"',
//			'#',
//			'~',
//			'`',
//			'(',
//			')',
//			'{',
//			'}',
//			'[',
//			']',
//			'<',
//			'>',
//			'=',
//			'+',
//			'|',
//			':',
//			';',
//			',',
//			'_',
//			'-',
//			'°',
//			'.'
//			//====================
//			// end region universal
//			//====================
//			)
//		);

		allowedChars.addAll(
			Arrays.asList(
			'a',
			'b',
			'c',
			'd',
			'e',
			'f',
			'g',
			'h',
			'i',
			'j',
			'k',
			'l',
			'm',
			'n',
			'o',
			'p',
			'q',
			'r',
			's',
			't',
			'u',
			'v',
			'w',
			'x',
			'y',
			'z',
			//-----
			'é',
			'è',
			'ç',
			'à',
			'ù',
			//-----
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'²',
			//====================
			// region universal
			//====================
			' ',
			'_',
			'-',
			'.'
			//====================
			// end region universal
			//====================
			)
		);
		
		Iterator<Character> allowedCharsIt = allowedChars.iterator();
		while ( allowedCharsIt.hasNext() ) {
			Character charFound = allowedCharsIt.next();
			if ( ELEMENTS_NAMES_CHARS_ALLOWED.contains(charFound) == false ) {
				ELEMENTS_NAMES_CHARS_ALLOWED.add( charFound );
			}
		}
	}
	
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
	
	//==================
	// Common

	public String get_NOTES_FOLDER() {
		return NOTES_FOLDER;
	}

	public String get_WORK_DIRECTORY() {
		return WORK_DIRECTORY;
	}

	public String get_KEYBOARD_SHORTCUTS_IN_TREE() {
		return KEYBOARD_SHORTCUTS_IN_TREE;
	}

	public String get_KEYBOARD_SHORTCUTS_IN_NOTE() {
		return KEYBOARD_SHORTCUTS_IN_NOTE;
	}
	
	//==================
	// Files tree

	public String get_CAN_NOT_DELETE_ITEM() {
		return CAN_NOT_DELETE_ITEM;
	}
	
	public String get_CAN_NOT_RENAME_ITEM() {
		return CAN_NOT_RENAME_ITEM;
	}
	
	public String get_FOLDER_ICON() {
		return FOLDER_ICON;
	}

	public String get_FOLDER_ICON_SHORT() {
		return FOLDER_ICON_SHORT;
	}

	public String get_NOTE_ICON() {
		return NOTE_ICON;
	}

	public String get_NOTE_ICON_SHORT() {
		return NOTE_ICON_SHORT;
	}

	public String get_NEW_FOLDER() {
		return NEW_FOLDER;
	}

	public String get_NEW_FOLDER_ALREADY_EXIST() {
		return NEW_FOLDER_ALREADY_EXIST;
	}

	public String get_RENAME_FOLDER() {
		return RENAME_FOLDER;
	}

	public String get_FOLDER_DO_NOT_EXIST() {
		return FOLDER_DO_NOT_EXIST;
	}

	public String get_NEW_NOTE() {
		return NEW_NOTE;
	}

	public String get_NEW_NOTE_ALREADY_EXIST() {
		return NEW_NOTE_ALREADY_EXIST;
	}
	
	public String get_RENAME_NOTE() {
		return RENAME_NOTE;
	}
	
	public String get_NOTE_DO_NOT_EXIST() {
		return NOTE_DO_NOT_EXIST;
	}

	public String get_NOTE() {
		return NOTE;
	}

	public String get_NOTE_OPENED() {
		return NOTE_OPENED;
	}

	public String get_NOTES() {
		return NOTES;
	}

	public String get_NOTE_COPIED() {
		return NOTE_COPIED;
	}

	public String get_NOTE_MOVED() {
		return NOTE_MOVED;
	}

	public String get_NOTE_RENAMED() {
		return NOTE_RENAMED;
	}

	public String get_NOTE_SAVED() {
		return NOTE_SAVED;
	}

	public String get_NOTE_NOT_SAVED() {
		return NOTE_NOT_SAVED;
	}
	
	public String get_NOTES_COPIED() {
		return NOTES_COPIED;
	}

	public String get_NOTES_MOVED() {
		return NOTES_MOVED;
	}

	public String get_NOTES_RENAMED() {
		return NOTES_RENAMED;
	}

	public String get_NOTES_SAVED() {
		return NOTES_SAVED;
	}

	public String get_NOTES_NOT_SAVED() {
		return NOTES_NOT_SAVED;
	}

	public String get_NOTE_DELETED() {
		return NOTE_DELETED;
	}

	public String get_NOTE_NOT_DELETED() {
		return NOTE_NOT_DELETED;
	}

	public String get_NOTES_DELETED() {
		return NOTES_DELETED;
	}

	public String get_NOTES_NOT_DELETED() {
		return NOTES_NOT_DELETED;
	}

	public String get_DELETE_NOTE() {
		return DELETE_NOTE;
	}
	
	public String get_DELETE_NOTES() {
		return DELETE_NOTES;
	}
	
	public String get_SAVE_NOTE() {
		return SAVE_NOTE;
	}
	
	public String get_SAVE_NOTES() {
		return SAVE_NOTES;
	}
	
	//=======================
	// Global text
	
	public String get_TEXT_WELCOME() {
		return TEXT_WELCOME;
	}

	public String get_TEXT_WELCOME_2() {
		return TEXT_WELCOME_2;
	}

	public String get_TEXT_WELCOME_3() {
		return TEXT_WELCOME_3;
	}

	public String get_TEXT_WELCOME_4() {
		return TEXT_WELCOME_4;
	}

	public String get_TEXT_WELCOME_5() {
		return TEXT_WELCOME_5;
	}

	public String get_TEXT_WELCOME_6() {
		return TEXT_WELCOME_6;
	}

	public String get_TEXT_WELCOME_7() {
		return TEXT_WELCOME_7;
	}

	public String get_CHARS_BASE_SIZE_PARAM_INFO() {
		return CHARS_BASE_SIZE_PARAM_INFO;
	}

	public String get_INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO() {
		return INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO;
	}

	public String get_ICONS_SIZE_PARAM_INFO() {
		return ICONS_SIZE_PARAM_INFO;
	}

	public String get_INPUT_POPUP_ICONS_SIZE_PARAM_INFO() {
		return INPUT_POPUP_ICONS_SIZE_PARAM_INFO;
	}

	public String get_ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO() {
		return ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO;
	}

	public String get_ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO() {
		return ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO;
	}
	
	public String get_FILES_PARAMS_AVAILABILITY() {
		return FILES_PARAMS_AVAILABILITY;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTED() {
		return INFO_WORK_DIRECTORY_SELECTED;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION() {
		return INFO_WORK_DIRECTORY_SELECTION;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_2() {
		return INFO_WORK_DIRECTORY_SELECTION_2;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_3() {
		return INFO_WORK_DIRECTORY_SELECTION_3;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_4() {
		return INFO_WORK_DIRECTORY_SELECTION_4;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_5() {
		return INFO_WORK_DIRECTORY_SELECTION_5;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_6() {
		return INFO_WORK_DIRECTORY_SELECTION_6;
	}

	public String get_INFO_WORK_DIRECTORY_SELECTION_7() {
		return INFO_WORK_DIRECTORY_SELECTION_7;
	}

	public String get_ABOUT_LIBRARY_RE01JLIB() {
		return ABOUT_LIBRARY_RE01JLIB;
	}

	public String get_HELP_WHAT_IS_JTEXTNOTES() {
		return HELP_WHAT_IS_JTEXTNOTES;
	}

	public String get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES() {
		return HELP_OPEN_MULTIPLES_WORK_DIRECTORIES;
	}

	public String get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2() {
		return HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2;
	}

	public String get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3() {
		return HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3;
	}

	public String get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4() {
		return HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4;
	}

	public String get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5() {
		return HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5;
	}

	public String get_HELP_NOTE_OPENED_IN_TREE() {
		return HELP_NOTE_OPENED_IN_TREE;
	}

	public String get_HELP_NOTE_OPENED_IN_TREE_2() {
		return HELP_NOTE_OPENED_IN_TREE_2;
	}

	public String get_HELP_NOTE_OPENED_IN_TREE_3() {
		return HELP_NOTE_OPENED_IN_TREE_3;
	}

	public String get_HELP_NOTES_ARE_SIMPLE_FILES() {
		return HELP_NOTES_ARE_SIMPLE_FILES;
	}

	public String get_HELP_SAVE_NOTES_TO_EXTERNAL_HDD() {
		return HELP_SAVE_NOTES_TO_EXTERNAL_HDD;
	}

	public String get_HELP_CLOSE_WINDOW_WITH_CROSS() {
		return HELP_CLOSE_WINDOW_WITH_CROSS;
	}

	public String get_HELP_CLOSE_WORK_DIRECTORY() {
		return HELP_CLOSE_WORK_DIRECTORY;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_ENTER() {
		return HELP_KEYBOARD_SHORTCUT_TREE_ENTER;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP() {
		return HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S() {
		return HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_TREE_DEL() {
		return HELP_KEYBOARD_SHORTCUT_TREE_DEL;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z;
	}

	public String get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y() {
		return HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y;
	}

	public String get_LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE() {
		return LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE;
	}

	public String get_ROOT_FOLDER_NOTES_FOUND() {
		return ROOT_FOLDER_NOTES_FOUND;
	}
	
	public String get_ROOT_FOLDER_NOTES_NOT_FOUND() {
		return ROOT_FOLDER_NOTES_NOT_FOUND;
	}

	public String get_ROOT_FOLDER_NOTES_SELECTED() {
		return ROOT_FOLDER_NOTES_SELECTED;
	}

	public String get_NEED_SELECT_A_FOLDER_TO_ACTION() {
		return NEED_SELECT_A_FOLDER_TO_ACTION;
	}

	public String get_NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION() {
		return NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION;
	}

	public String get_CONFIRM_SAVE_FOLDER_NOTES() {
		return CONFIRM_SAVE_FOLDER_NOTES;
	}

	public String get_CONFIRM_SAVE_NOTE() {
		return CONFIRM_SAVE_NOTE;
	}

	public String get_CONFIRM_SAVE_NOTES() {
		return CONFIRM_SAVE_NOTES;
	}

	public String get_CONFIRM_DELETE_FOLDER_NOTES() {
		return CONFIRM_DELETE_FOLDER_NOTES;
	}

	public String get_CONFIRM_DELETE_NOTE() {
		return CONFIRM_DELETE_NOTE;
	}

	public String get_CONFIRM_DELETE_NOTES() {
		return CONFIRM_DELETE_NOTES;
	}

	public String get_CONFIRM_CLOSE_WORK_DIRECTORY() {
		return CONFIRM_CLOSE_WORK_DIRECTORY;
	}

	public String get_CONFIRM_EXIT_PROGRAM() {
		return CONFIRM_EXIT_PROGRAM;
	}

	public String get_PROMPT_NEW_FOLDER() {
		return PROMPT_NEW_FOLDER;
	}
	
	public String get_PROMPT_RENAME_FOLDER() {
		return PROMPT_RENAME_FOLDER;
	}
	
	public String get_PROMPT_NEW_NOTE() {
		return PROMPT_NEW_NOTE;
	}

	public String get_PROMPT_RENAME_NOTE() {
		return PROMPT_RENAME_NOTE;
	}

	public String get_PROMPT_SEARCH_IN_NOTES() {
		return PROMPT_SEARCH_IN_NOTES;
	}

	public String get_RESULTS_IN_FOLDERS_NAMES() {
		return RESULTS_IN_FOLDERS_NAMES;
	}

	public String get_RESULTS_IN_NOTES_NAMES() {
		return RESULTS_IN_NOTES_NAMES;
	}

	public String get_RESULTS_IN_NOTES_CONTENTS() {
		return RESULTS_IN_NOTES_CONTENTS;
	}

	public String get_SEARCH_IN_NOTE() {
		return SEARCH_IN_NOTE;
	}
	
	public String get_SEARCH_IN_NOTES() {
		return SEARCH_IN_NOTES;
	}

	public String get_EVERY_CHARS_NOT_ALLOWED() {
		return EVERY_CHARS_NOT_ALLOWED;
	}

	public String get_WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN() {
		return WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN;
	}

	public String get_WORK_DIRECTORY_REQUIRED() {
		return WORK_DIRECTORY_REQUIRED;
	}
	
	public String get_WORK_DIRECTORY_LAST_OPENED() {
		return WORK_DIRECTORY_LAST_OPENED;
	}

	public String get_WORK_DIRECTORY_IS_LAST_AUTO_OPEN() {
		return WORK_DIRECTORY_IS_LAST_AUTO_OPEN;
	}

	public String get_WORK_DIRECTORY_OPEN_NEW() {
		return WORK_DIRECTORY_OPEN_NEW;
	}

	public String get_WORK_DIRECTORY_CLOSE() {
		return WORK_DIRECTORY_CLOSE;
	}

	public String get_WORK_DIRECTORY_PART_OPENED() {
		return WORK_DIRECTORY_PART_OPENED;
	}
	
	//=====================
	// Languages

	public String get_SELECT_LANGUAGE_ABOUT() {
		return SELECT_LANGUAGE_ABOUT;
	}
	
	//===================
	// Program

	public String get_PROGRAM_NAME() {
		return PROGRAM_NAME;
	}
	
	public HashSet<Character> get_ELEMENTS_NAMES_CHARS_ALLOWED() {
		return ELEMENTS_NAMES_CHARS_ALLOWED;
	}
	
	//=====================
	// KeyEvents

	public int get_SEARCH_IN_NOTE_KEYEVENT() {
		return SEARCH_IN_NOTE_KEYEVENT;
	}
	
	public int get_SEARCH_IN_NOTES_KEYEVENT() {
		return SEARCH_IN_NOTES_KEYEVENT;
	}

	public int get_WORK_DIRECTORY_OPEN_NEW_KEYEVENT() {
		return WORK_DIRECTORY_OPEN_NEW_KEYEVENT;
	}

	public int get_WORK_DIRECTORY_CLOSE_KEYEVENT() {
		return WORK_DIRECTORY_CLOSE_KEYEVENT;
	}
	
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
	// Methods
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
	
	public boolean isStringCharsAllowed( String str ) {
		boolean isAllowed = true;
		
		str = str.toLowerCase();
		String strUpper = str.toUpperCase();
		
		int strLength = str.length();
		for ( int i = 0; i < strLength; i++ ) {
			Character charFound = str.charAt(i);
			Character charFoundUpper = strUpper.charAt(i);
			if ( ELEMENTS_NAMES_CHARS_ALLOWED.contains(charFound) == false && ELEMENTS_NAMES_CHARS_ALLOWED.contains(charFoundUpper) == false ) {
				isAllowed = false;
				break;
			}
		}
		
		return isAllowed;
	}
	
	public String parseToAllowedString( String str ) {
		String strNew = "";
		String strLower = str.toLowerCase();
		String strUpper = str.toUpperCase();
		
		int strLength = str.length();
		for ( int i = 0; i < strLength; i++ ) {
			Character charFound = str.charAt(i);
			Character charFoundLower = strLower.charAt(i);
			Character charFoundUpper = strUpper.charAt(i);
			if ( ELEMENTS_NAMES_CHARS_ALLOWED.contains(charFoundLower) == true || ELEMENTS_NAMES_CHARS_ALLOWED.contains(charFoundUpper) == true ) {
				strNew += charFound;
			}
		}
		return strNew;
	}
	
}
