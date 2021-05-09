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

/**
 *
 * @author renaud
 */
public class Folder {
	
	private Folder folderParent;
	protected String name;
	
	protected ArrayList<Folder> folders = new ArrayList<Folder>();
	protected ArrayList<Note> notes = new ArrayList<Note>();

	public Folder( Folder folderParent, String name, ArrayList<Folder> folders, ArrayList<Note> notes ) {
		this.folderParent = folderParent;
		this.name = name;
		this.folders = folders;
		this.notes = notes;
	}

	public Folder getFolderParent() {
		return folderParent;
	}

	public void setFolderParent(Folder folderParent) {
		this.folderParent = folderParent;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getPathAbsolute( boolean withName ) {
		if ( withName == true )
			return Paths.get( folderParent.getPathAbsolute(true), name ).toString();
		else
			return folderParent.getPathAbsolute(true);
	}
	
	public ArrayList<Folder> getFolders() {
		return folders;
	}

	public void setFolders( ArrayList<Folder> folders ) {
		this.folders = folders;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes( ArrayList<Note> notes ) {
		this.notes = notes;
	}
	
}
