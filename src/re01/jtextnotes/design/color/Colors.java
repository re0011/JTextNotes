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

package re01.jtextnotes.design.color;

import java.util.ArrayList;
import java.util.Arrays;
import re01.design.color.Color;
import re01.design.color.ColorTypeEnum;

/**
 *
 * @author renaud
 */
public class Colors extends re01.design.color.Colors {
	
	private final ArrayList<Color> COLORS_ALLOWED = new ArrayList<Color>( Arrays.asList( 
		new Color( ColorTypeEnum.Black, null ), 
		new Color( ColorTypeEnum.White, null ), 
		new Color( ColorTypeEnum.Red, null ), 
		new Color( ColorTypeEnum.Green, null ), 
		new Color( ColorTypeEnum.Blue, null ), 
		new Color( ColorTypeEnum.Yellow, null ), 
		new Color( ColorTypeEnum.Orange, null ), 
		new Color( ColorTypeEnum.Teal, null ), 
		new Color( ColorTypeEnum.Purple, null ), 
		new Color( ColorTypeEnum.Pink, null ), 
		new Color( ColorTypeEnum.BlueSky, null ), 
		new Color( ColorTypeEnum.MaroonWeb, null ), 
		new Color( ColorTypeEnum.Bronze, null ), 
		new Color( ColorTypeEnum.GreenYellow, null ) 
	) );
	
	public Colors() {
		super();
	}

	public ArrayList<Color> get_COLORS_ALLOWED() {
		return COLORS_ALLOWED;
	}
	
}
