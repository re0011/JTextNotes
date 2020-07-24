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

package re01.jtextnotes.bean;

import java.nio.file.Paths;
import javax.swing.ImageIcon;
import re01.jtextnotes.design.view.swing.JTextPane;

/**
 *
 * @author renaud
 */
public class Note {
	
	private Folder folder;
	private String name;
	
	private NoteIconTypeEnum iconType;
	private String iconValue;
	private ImageIcon icon;
	private String content;
	private JTextPane pane;
	
	private Boolean isSaved = true;

	public Note( 
		Folder folder, 
		String name, 
		NoteIconTypeEnum iconType, 
		String iconValue, 
		ImageIcon icon, 
		String content, 
		JTextPane pane
	) {
		this.folder = folder;
		this.name = name;
		this.iconType = iconType;
		this.iconValue = iconValue;
		this.icon = icon;
		this.content = content;
		this.pane = pane;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder( Folder folder ) {
		this.folder = folder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathAbsolute() {
		return Paths.get( folder.getPathAbsolute(true), name ).toString();
	}

	public NoteIconTypeEnum getIconType() {
		return iconType;
	}

	public void setIconType( NoteIconTypeEnum iconType ) {
		this.iconType = iconType;
	}

	public String getIconValue() {
		return iconValue;
	}

	public void setIconValue(String iconValue) {
		this.iconValue = iconValue;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon( ImageIcon icon ) {
		this.icon = icon;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

	public JTextPane getPane() {
		return pane;
	}

	public void setPane(JTextPane pane) {
		this.pane = pane;
	}

	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}
	
}
