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

package re01.jtextnotes.program;

import java.nio.file.Paths;
import re01.io.system.Files;
import re01.tool.helper.debug.Logger;

/**
 *
 * @author renaud
 */
public class Core {
	
	private static final String AUTHOR_NAME = "Renaud LE CLERRE";
	private static final String WEBSITE = "http://www.re01.website/";
	
	private static final String PROGRAM_NAME = "JTextNotes";
	private static final String PROGRAM_VERSION = "20210509";
	private static final String PROGRAM_VERSION_STATE = "Beta";
	private static final String PROGRAM_LICENSE_NAME = "GNU GENERAL PUBLIC LICENSE";
	private static final String PROGRAM_COPYRIGHT = "Copyright (C) 2020-2021";
	private static final String PROGRAM_ICON_PATH = "images/program_icon.png";
	
	private static final String NOTE_FILE_EXTENSION = ".txt";
	private static final String PARAMS_DIR_NAME = ".JTextNotes";
	private static final String PARAMS_DIR_PATH = Paths.get(System.getProperty("user.home"), PARAMS_DIR_NAME).toString();
	private static final String PARAMS_FILE_EXTENSION = ".txtn";
	private static final String PARAMS_FILE_NAME = "." + PROGRAM_NAME.toLowerCase() + "_params" + PARAMS_FILE_EXTENSION;
	private static final String PARAMS_FILE_PATH = Paths.get(PARAMS_DIR_PATH, PARAMS_FILE_NAME).toString();
	private static final String USER_PARAMS_FILE_NAME = PROGRAM_NAME.toLowerCase() + "_user_params" + PARAMS_FILE_EXTENSION;
	
	private static final int INPUT_VALUE_MAX_LENGTH = 96;
	
	private static Logger LOGGER = createLogger();
	
	public static String get_AUTHOR_NAME() {
		return AUTHOR_NAME;
	}

	public static String get_WEBSITE() {
		return WEBSITE;
	}

	public static String get_PROGRAM_VERSION() {
		return PROGRAM_VERSION;
	}

	public static String get_PROGRAM_VERSION_STATE() {
		return PROGRAM_VERSION_STATE;
	}

	public static String get_PROGRAM_LICENSE_NAME() {
		return PROGRAM_LICENSE_NAME;
	}

	public static String get_PROGRAM_COPYRIGHT() {
		return PROGRAM_COPYRIGHT;
	}

	public static String get_PROGRAM_ICON_PATH() {
		return PROGRAM_ICON_PATH;
	}

	public static String get_NOTE_FILE_EXTENSION() {
		return NOTE_FILE_EXTENSION;
	}

	public static String get_PARAMS_DIR_NAME() {
		return PARAMS_DIR_NAME;
	}

	public static String get_PARAMS_DIR_PATH() {
		return PARAMS_DIR_PATH;
	}

	public static String get_PARAMS_FILE_EXTENSION() {
		return PARAMS_FILE_EXTENSION;
	}

	public static String get_PARAMS_FILE_NAME() {
		return PARAMS_FILE_NAME;
	}

	public static String get_PARAMS_FILE_PATH() {
		return PARAMS_FILE_PATH;
	}

	public static String get_USER_PARAMS_FILE_NAME() {
		return USER_PARAMS_FILE_NAME;
	}

	public static int get_INPUT_VALUE_MAX_LENGTH() {
		return INPUT_VALUE_MAX_LENGTH;
	}

	public static Logger get_LOGGER() {
		return LOGGER;
	}
	
	private static Logger createLogger() {
		Files files = new Files();
		if ( files.isDirectoryExist(PARAMS_DIR_PATH) == false )
			files.createDirectory(PARAMS_DIR_PATH);
		
		Logger logger = new Logger( PARAMS_DIR_PATH, 2000000 );// 2 MB
		return logger;
	}
	
}
