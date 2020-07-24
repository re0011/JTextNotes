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

package re01.jtextnotes.design.image;

import javax.swing.ImageIcon;
import re01.design.color.Color;
import re01.design.image.ImageTypeEnum;
import re01.jtextnotes.bean.NoteIconTypeEnum;

/**
 *
 * @author renaud
 */
public class Images extends re01.design.image.Images {
	
	public ImageIcon createImageIcon( NoteIconTypeEnum noteIconType, String imgValueStr ) {
		ImageIcon imageIcon = null;
		re01.jtextnotes.design.color.Colors colors = new re01.jtextnotes.design.color.Colors();
		
		switch ( noteIconType ) {
			case ColorImg:
				Color color = colors.getColor(imgValueStr, colors.get_COLORS_ALLOWED());
				if ( color != null ) {
					java.awt.Color colorRgb = color.getRgbColor();
					if ( colorRgb != null )
						imageIcon = createImageIcon( false, colorRgb );
				}
				break;
			case G2dImg:
				ImageTypeEnum iomageType = null;
				ImageTypeEnum[] imagesTypes = ImageTypeEnum.values();
				for ( int i = 0; i < imagesTypes.length; i++ ) {
					ImageTypeEnum imageTypeFound = imagesTypes[i];
					if ( imageTypeFound.toString().toLowerCase().equals(imgValueStr.toLowerCase()) == true ) {
						iomageType = imageTypeFound;
						break;
					}
				}
				if ( iomageType != null ) {
					imageIcon = getImage( iomageType );
				}
				break;
		}
		
		return imageIcon;
	}
	
}
