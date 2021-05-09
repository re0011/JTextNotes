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

package re01.jtextnotes.bean;

import java.nio.file.Paths;
import java.util.ArrayList;
import re01.tool.helper.system.StringHelper;

/**
 *
 * @author renaud
 */
public class FolderRoot extends Folder {
	
	private String pathAbsolute = null;
	
	public FolderRoot(
		String name, 
		String pathAbsolute, 
		ArrayList<Folder> folders, 
		ArrayList<Note> notes
	) {
		super(null, name, folders, notes);
		
		StringHelper stringHelper = new StringHelper();
		int pathAbsoluteLength = pathAbsolute.length();
		this.pathAbsolute = stringHelper.removeCharsAt(pathAbsolute, pathAbsoluteLength - name.length(), pathAbsoluteLength);
	}
	
	@Override
	public String getPathAbsolute( boolean withName ) {
		if ( withName == true )
			return Paths.get( pathAbsolute, name ).toString();
		else
			return pathAbsolute;
	}

	public void setPathAbsolute(String pathAbsolute) {
		this.pathAbsolute = pathAbsolute;
	}
	
}
