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

import java.awt.event.KeyEvent;

/**
 *
 * @author renaud
 */
public class English extends Global {
	
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
	
	public English() {
		super();
		
		//==================
		// Common
		
		NOTES_FOLDER = "Folder notes";
		WORK_DIRECTORY = "Work directory";
		KEYBOARD_SHORTCUTS_IN_TREE = "Keyboard shortcuts in notes tree structure";
		KEYBOARD_SHORTCUTS_IN_NOTE = "Keyboard shortcuts in opened note";
		
		//==================
		// Files tree
	
		CAN_NOT_DELETE_ITEM = "Can not delete item";
		CAN_NOT_RENAME_ITEM = "Can not rename item";
		FOLDER_ICON = "Folder icon";
		FOLDER_ICON_SHORT = "FI";
		NOTE_ICON = "Note icon";
		NOTE_ICON_SHORT = "NI";
		NEW_FOLDER = "New folder";
		NEW_FOLDER_ALREADY_EXIST = "A folder with this name already exists in the selected folder";
		RENAME_FOLDER = "Rename folder";
		FOLDER_DO_NOT_EXIST = "Folder do not exists";
		NEW_NOTE = "New note";
		NEW_NOTE_ALREADY_EXIST = "A note with this name already exists in the selected folder";
		RENAME_NOTE = "Rename note";
		NOTE_DO_NOT_EXIST = "Note do not exists";
		
		NOTE = "Note";
		NOTE_OPENED = "Opened note";
		NOTES = "Notes";
		NOTE_COPIED = "Copied note";
		NOTE_MOVED = "Moved note";
		NOTE_RENAMED = "Renamed note";
		NOTE_SAVED = "Saved note";
		NOTE_NOT_SAVED = "Not saved note";
		NOTES_COPIED = "Copied notes";
		NOTES_MOVED = "Moved notes";
		NOTES_RENAMED = "Renamed notes";
		NOTES_SAVED = "Saved notes";
		NOTES_NOT_SAVED = "Not saved notes";
		NOTE_DELETED = "Deleted note";
		NOTE_NOT_DELETED = "Not deleted note";
		NOTES_DELETED = "Deleted notes";
		NOTES_NOT_DELETED = "Not deleted notes";
		
		DELETE_NOTE = "Delete note";
		DELETE_NOTES = "Delete notes";
		SAVE_NOTE = "Save note";
		SAVE_NOTES = "Save notes";
		
		//=======================
		// Global text
		
		TEXT_WELCOME = "Welcome to ";
		TEXT_WELCOME_2 = " !";
		TEXT_WELCOME_3 = "This program is free software (open source), it is published under ";
		TEXT_WELCOME_4 = " license. More details are available in the ";
		TEXT_WELCOME_5 = "About";
		TEXT_WELCOME_6 = " menu.";
		TEXT_WELCOME_7 = "Have fun !";
		
		CHARS_BASE_SIZE_PARAM_INFO = "When you change this value, you see the result directly in this window.";
		INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO = "Use the cursor or the + and - buttons to change the value.";
		
		ICONS_SIZE_PARAM_INFO = "When you change this value, you see the result directly in this window. You can see 2 examples icons below : one represent a file and the other the success of an action.";
		INPUT_POPUP_ICONS_SIZE_PARAM_INFO = "Use the cursor or the + and - buttons to change the value.";
		ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO = "Represent a file";
		ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO = "Represent the success of an action";
		
		FILES_PARAMS_AVAILABILITY = "Existence of parameters files";
		
		INFO_WORK_DIRECTORY_SELECTED = "You choose directory";
		INFO_WORK_DIRECTORY_SELECTION = "Choose a ";
		INFO_WORK_DIRECTORY_SELECTION_2 = WORK_DIRECTORY.toLowerCase();
		INFO_WORK_DIRECTORY_SELECTION_3 = " on your computer. It can be an empty directory to create new ";
		INFO_WORK_DIRECTORY_SELECTION_4 = NOTES_FOLDER.toLowerCase();
		INFO_WORK_DIRECTORY_SELECTION_5 = " or a directory that already contains notes.";
		
		ABOUT_LIBRARY_RE01JLIB = "(published under GNU GPL license)";
		
		HELP_WHAT_IS_JTEXTNOTES = "JTextNotes is a program used to take notes. It is similar to a notepad with options to stylize text.";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES = "To open several ";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2 = "work directories";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3 = " simultaneously, proceed as follows : from the menu, go to \"File\" -> \"Open new work directory\". ";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4 = " In this way, the program will work optimally.";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5 = " It is not recommended to open the program at several times so as to open several work directories simultaneously.";
		HELP_NOTE_OPENED_IN_TREE = "The name of the opened note is represented with ";
		HELP_NOTE_OPENED_IN_TREE_2 = "bold and blue text";
		HELP_NOTE_OPENED_IN_TREE_3 = " in the notes tree structure.";
		HELP_NOTES_ARE_SIMPLE_FILES = "In JTextNotes, notes are nothing else than text files that you can list with a file manager and edit with any text editor. Be careful to do not modify the [header][/header] and [body][/body] tags of a note file, otherwise the note will not be visible in JTextNotes.";
		HELP_SAVE_NOTES_TO_EXTERNAL_HDD = "To save the notes to an external hard disk drive or another computer, you just need to copy them with your favorite file manager because in JTextNotes, notes are nothing else than files.";
		HELP_CLOSE_WINDOW_WITH_CROSS = "Close a window with cross is the same than click to \"Cancel\", \"No\" or \"Close\" button of the window. Regarding to the window of work directory, click to the cross is the same than close work directory from menu : \"File\" -> \"Close work directory\".";
		HELP_CLOSE_WORK_DIRECTORY = "When you close the last opened work directory, JTextNotes also closes. To close all of the opened work directories at the same time, click to the menu : \"File\" -> \"Exit\".";
		HELP_KEYBOARD_SHORTCUT_TREE_ENTER = "ENTER = open the selected note.";
		HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP = "Hold down SHIFT and press UP ARROW (or DOWN ARROW) = select several folders/notes.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F = "CTRL + F = create a folder in the selected folder.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N = "CTRL + N = create a note in the selected folder.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C = "CTRL + C = copy the selected folders/notes.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X = "CTRL + X = cut the selected folders/notes.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V = "CTRL + V = paste the folders/notes that are in the clipboard to the selected folder.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S = "CTRL + S = save the selected notes.";
		HELP_KEYBOARD_SHORTCUT_TREE_DEL = "DEL = delete the selected folders/notes.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F = "CTRL + F = search a term in the opened note.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B = "CTRL + B = set the selected text to bold.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I = "CTRL + I = set the selected text to italic.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U = "CTRL + U = set the selected text to underline.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C = "CTRL + C = copy the selected text.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S = "CTRL + S = copy the selected text with style.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X = "CTRL + X = cut the selected text.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V = "CTRL + V = paste the text that is in the clipboard.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z = "CTRL + Z = back to anterior note state.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y = "CTRL + Y (or CTRL + SHIFT + Z) = forward to posterior note state.";
		
		LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE = "(to see details, go on left side, then go to \"License\" -> \"GNU\")";
		
		ROOT_FOLDER_NOTES_FOUND = "Notes that already exists were found";
		ROOT_FOLDER_NOTES_NOT_FOUND = "No one existing note has been found";
		ROOT_FOLDER_NOTES_SELECTED = "Selected " + WORK_DIRECTORY.toLowerCase().toLowerCase();
		
		NEED_SELECT_A_FOLDER_TO_ACTION = "You need to select a folder to do it.";
		NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION = "You need to select only one item to do it.";
		
		CONFIRM_SAVE_FOLDER_NOTES = "The notes of the selected folders will be saved as well as those of their sub folders. Save the selected notes ?";
		CONFIRM_SAVE_NOTE = "Save the selected note ?";
		CONFIRM_SAVE_NOTES = "Save the selected notes ?";
		CONFIRM_DELETE_FOLDER_NOTES = "The notes of the selected folders will be deleted as well as those of their sub folders. Delete the selected notes ?";
		CONFIRM_DELETE_NOTE = "Delete the selected note ?";
		CONFIRM_DELETE_NOTES = "Delete the selected notes ?";
		CONFIRM_CLOSE_WORK_DIRECTORY = "Close this work directory ? Some notes are not saved. Close anyway ?";
		CONFIRM_EXIT_PROGRAM = "Exit program now ? All of the opened work directories gonna be closed. At least one opened work directory contains unsaved notes. Exit anyway ?";
		
		PROMPT_NEW_FOLDER = "Write name for the new folder";
		PROMPT_RENAME_FOLDER = "Write new name for the folder";
		PROMPT_NEW_NOTE = "Write name for the new note";
		PROMPT_RENAME_NOTE = "Write new name for the note";
		PROMPT_SEARCH_IN_NOTES = "Write keyword for search";
		
		RESULTS_IN_FOLDERS_NAMES = "Results in folders names";
		RESULTS_IN_NOTES_NAMES = "Results in notes names";
		RESULTS_IN_NOTES_CONTENTS = "Results in notes contents";
		
		SEARCH_IN_NOTE = "Search in the note";
		SEARCH_IN_NOTES = "Search in all the notes";
		
		EVERY_CHARS_NOT_ALLOWED = "Some characters was not allowed";
		
		WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN = "Do not display welcome window when program starts";
		
		WORK_DIRECTORY_REQUIRED = "You need to choose a " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_LAST_OPENED = "Last " + WORK_DIRECTORY.toLowerCase() + " opened";
		WORK_DIRECTORY_IS_LAST_AUTO_OPEN = "Open the last known work directory when program starts";
		WORK_DIRECTORY_OPEN_NEW = "Open new " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_CLOSE = "Close " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_PART_OPENED = "An opened work directory contains some parts of the work directory that you try to open";
		
		TEXT_CAN_NOT_APPLY_PARAMETERS_CHANGES_UNTIL_SAVE_NOTES = "Parameters can not change until every notes are not saved.";
		
		//=====================
		// Languages
		
		SELECT_LANGUAGE_ABOUT = "You can change it later in parameters";
		
		//===================
		// Program
		
		PROGRAM_NAME = "JTextNotes";
		
		//=====================
		// KeyEvents

		SEARCH_IN_NOTE_KEYEVENT = KeyEvent.VK_T;
		SEARCH_IN_NOTES_KEYEVENT = KeyEvent.VK_A;
		
		WORK_DIRECTORY_OPEN_NEW_KEYEVENT = KeyEvent.VK_O;
		WORK_DIRECTORY_CLOSE_KEYEVENT = KeyEvent.VK_C;
		
	}
	
}
