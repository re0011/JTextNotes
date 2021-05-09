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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.swing.ImageIcon;
import re01.design.font.FontStyleEnum;
import re01.design.theme.Theme;
import re01.design.theme.ThemeTypeEnum;
import re01.io.system.Files;
import re01.language.Language;
import re01.language.LanguageTypeEnum;
import re01.tool.reader.ParameterReader;
import re01.jtextnotes.bean.NoteIconTypeEnum;
import re01.jtextnotes.program.Core;

/**
 *
 * @author renaud
 */
public class ParametersApplicator {
	
	private final String DATAS_LANGUAGE = "Language";
	private final String DATAS_LANGUAGE_TYPE = "language_type";
	private final String DATAS_LANGUAGE_IS_SELECTED = "is_language_selected";
	private final String DATAS_PROGRAM = "Program";
	private final String DATAS_PROGRAM_WORK_DIRECTORY_LAST_PATH = "work_directory_last_path";
	private final String DATAS_PROGRAM_IS_WORK_DIRECTORY_LAST_AUTO_SELECT = "is_work_directory_last_auto_select";
	private final String DATAS_PROGRAM_THEME_TYPE = "theme_type";
	private final String DATAS_PROGRAM_FONTS_STYLES = "fonts_styles";
	private final String DATAS_PROGRAM_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN = "is_view_welcome_do_not_display_again";
	private final String DATAS_PROGRAM_CHARS_BASE_SIZE = "chars_base_size";
	private final String DATAS_PROGRAM_ICONS_SIZE = "icons_size";
	private final String DATAS_PROGRAM_DOCUMENT_HISTORY_LENGTH = "document_history_length";
	private final String DATAS_VIEW = "View";
	private final String DATAS_VIEW_WINDOW_WIDTH = "window_width";
	private final String DATAS_VIEW_WINDOW_HEIGHT = "window_height";
	
	private final String DATAS_LANGUAGE_TO_LOWER_CASE = DATAS_LANGUAGE.toLowerCase();
	private final String DATAS_PROGRAM_TO_LOWER_CASE = DATAS_PROGRAM.toLowerCase();
	private final String DATAS_VIEW_TO_LOWER_CASE = DATAS_VIEW.toLowerCase();
	
	private HashSet<String> paramsSections = new HashSet<String>();
	private HashMap<String, String> paramsApplied = new HashMap<>();
	
	public ParametersApplicator() {
		paramsSections.add( DATAS_LANGUAGE );
		paramsSections.add( DATAS_PROGRAM );
		paramsSections.add( DATAS_VIEW );
	}
	
	public void apply_default() {
		re01.environment.Parameters.setLanguageSelected(new Language(re01.jtextnotes.program.Parameters.get_LANGUAGE_TYPE()) );
		re01.environment.Parameters.setThemeSelected(new Theme(re01.jtextnotes.program.Parameters.get_THEME_TYPE()) );
		
		Parameters.setWindowWidth( re01.environment.Parameters.get_DEFAULT_WINDOW_WIDTH() );
		Parameters.setWindowHeight( re01.environment.Parameters.get_DEFAULT_WINDOW_HEIGHT() );
		Parameters.setIsWorkDirectoryLastAutoSelect(re01.jtextnotes.program.Parameters.get_IS_WORK_DIRECTORY_LAST_AUTO_SELECT() );
		Parameters.setLanguageSelected(new re01.jtextnotes.language.Language(re01.jtextnotes.program.Parameters.get_LANGUAGE_TYPE()) );
		Parameters.setIsLanguageSelected(re01.jtextnotes.program.Parameters.get_IS_LANGUAGE_SELECTED() );
		Parameters.setIsViewWelcomeDoNotDisplayAgain(re01.jtextnotes.program.Parameters.get_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN() );
	}
	
	public void apply( HashMap<String, HashMap<String, String>> params ) {
		try {
			Iterator<Map.Entry<String, HashMap<String, String>>> paramsIteratorEntry = params.entrySet().iterator();
			while ( paramsIteratorEntry.hasNext() ) {
				Map.Entry<String, HashMap<String, String>> paramsEntry = paramsIteratorEntry.next();
				
				if ( paramsEntry.getKey().toLowerCase().equals( DATAS_LANGUAGE_TO_LOWER_CASE )
				|| paramsEntry.getKey().toLowerCase().equals( DATAS_PROGRAM_TO_LOWER_CASE )
				|| paramsEntry.getKey().toLowerCase().equals( DATAS_VIEW_TO_LOWER_CASE ) ) {
					Iterator<Map.Entry<String, String>> pairOptionValueIt = paramsEntry.getValue().entrySet().iterator();
					while ( pairOptionValueIt.hasNext() ) {
						Map.Entry<String, String> entry = pairOptionValueIt.next();
						apply( entry.getKey(), entry.getValue() );
					}
				}
			}
		} catch ( Exception e ) {
			Core.get_LOGGER().write( e );
		}
	}
	
	public void apply( String option, String value ) {
		if ( value.equals("null") == true )
			value = null;
		
		switch( option ){
			case( "null" ):
				value = null;
				break;
			default :
				try {
					switch( option ){
						case( DATAS_LANGUAGE_TYPE ):
							LanguageTypeEnum[] languagesTypesEnumValues = LanguageTypeEnum.values();
							int languagesTypesEnumValuesLength = languagesTypesEnumValues.length;
							for ( int i = 0; i < languagesTypesEnumValuesLength; i++ ) {
								LanguageTypeEnum languagesTypesEnumFound = languagesTypesEnumValues[i];
								if ( languagesTypesEnumFound.toString().equals(value) ) {
									re01.environment.Parameters.setLanguageSelected( new Language(languagesTypesEnumFound) );
									Parameters.setLanguageSelected( new re01.jtextnotes.language.Language(languagesTypesEnumFound) );
									break;
								}
							}
							break;
						case( DATAS_LANGUAGE_IS_SELECTED ):
							Parameters.setIsLanguageSelected( Boolean.parseBoolean(value) );
							break;
						case( DATAS_PROGRAM_WORK_DIRECTORY_LAST_PATH ):
							Files files = new Files();
							if ( files.isDirectoryExist(value) )
								Parameters.setWorkDirectoryLastPath( value );
							break;
						case( DATAS_PROGRAM_IS_WORK_DIRECTORY_LAST_AUTO_SELECT ):
							Parameters.setIsWorkDirectoryLastAutoSelect( Boolean.parseBoolean(value) );
							break;
						case( DATAS_PROGRAM_THEME_TYPE ):
							ThemeTypeEnum[] themesTypesEnumValues = ThemeTypeEnum.values();
							int themesTypesEnumValuesLength = themesTypesEnumValues.length;
							for ( int i = 0; i < themesTypesEnumValuesLength; i++ ) {
								ThemeTypeEnum themesTypesEnumFound = themesTypesEnumValues[i];
								if ( themesTypesEnumFound.toString().equals(value) ) {
									re01.environment.Parameters.setThemeSelected( new Theme(themesTypesEnumFound) );
									break;
								}
							}
							break;
						case( DATAS_PROGRAM_FONTS_STYLES ):
							ArrayList<FontStyleEnum> fontsStylesFound = new ArrayList<>();
							String[] valuesSplit = value.split(",");
							int valuesSplitLength = valuesSplit.length;
							for (int i = 0; i < valuesSplitLength; i++) {
								String valueSplit = valuesSplit[i].trim();
								if ( !valueSplit.equals("") ) {
									
									FontStyleEnum fontStyleFound = null;
									FontStyleEnum[] fontsStylesEnumValues = FontStyleEnum.values();
									int fontsStylesEnumValuesLength = fontsStylesEnumValues.length;
									for ( int i2 = 0; i2 < fontsStylesEnumValuesLength; i2++ ) {
										FontStyleEnum fontStyleFound2 = fontsStylesEnumValues[i2];
										if ( fontStyleFound2.toString().equals(valueSplit) ) {
											fontStyleFound = fontStyleFound2;
											break;
										}
									}
									if ( fontStyleFound != null && !fontsStylesFound.contains(fontStyleFound) )
										fontsStylesFound.add( fontStyleFound );
								}
							}
							Parameters.setFontsStyles( fontsStylesFound );
							break;
						case( DATAS_PROGRAM_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN ):
							Parameters.setIsViewWelcomeDoNotDisplayAgain( Boolean.parseBoolean(value) );
							break;
						case( DATAS_PROGRAM_CHARS_BASE_SIZE ):
							Integer charsBaseSize = Integer.parseInt(value);
							if ( charsBaseSize != null ) {
								if ( re01.environment.Parameters.isCharsBaseSizeValid(charsBaseSize) == true )
									re01.environment.Parameters.setCharsBaseSize( charsBaseSize );
							}
							break;
						case( DATAS_PROGRAM_ICONS_SIZE ):
							Integer iconsSize = Integer.parseInt(value);
							if ( iconsSize != null ) {
								if ( re01.environment.Parameters.isIconsSizeValid(iconsSize) == true )
									re01.environment.Parameters.setIconsSize( iconsSize );
							}
							break;
						case( DATAS_PROGRAM_DOCUMENT_HISTORY_LENGTH ):
							Integer documentHistoryLength = Integer.parseInt(value);
							if ( documentHistoryLength != null ) {
								if ( re01.environment.Parameters.isDocumentHistoryLengthValid(documentHistoryLength) == true )
									re01.environment.Parameters.setDocumentHistoryLength( documentHistoryLength );
							}
							break;
						case( DATAS_VIEW_WINDOW_WIDTH ):
							Integer windowWidth = Integer.parseInt(value);
							if ( windowWidth != null ) {
								if ( re01.environment.Parameters.isWindowWidthValid(windowWidth) == true )
									Parameters.setWindowWidth( windowWidth );
							}
							break;
						case( DATAS_VIEW_WINDOW_HEIGHT ):
							Integer windowHeight = Integer.parseInt(value);
							if ( windowHeight != null ) {
								if ( re01.environment.Parameters.isWindowHeightValid(windowHeight) == true )
									Parameters.setWindowHeight( windowHeight );
							}
							break;
					}

				} catch ( Exception e ) {
					Core.get_LOGGER().write( e );
					switch( option ){
						case( DATAS_LANGUAGE_TYPE ):
							LanguageTypeEnum languageTypeDefault = re01.jtextnotes.program.Parameters.get_LANGUAGE_TYPE();
							re01.environment.Parameters.setLanguageSelected( new Language(languageTypeDefault) );
							Parameters.setLanguageSelected(new re01.jtextnotes.language.Language(re01.jtextnotes.program.Parameters.get_LANGUAGE_TYPE()) );
							value = languageTypeDefault.toString();
							break;
						case( DATAS_LANGUAGE_IS_SELECTED ):
							Boolean isLanguageSelected = re01.jtextnotes.program.Parameters.get_IS_LANGUAGE_SELECTED();
							Parameters.setIsLanguageSelected( isLanguageSelected );
							value = isLanguageSelected.toString();
							break;
						case( DATAS_PROGRAM_WORK_DIRECTORY_LAST_PATH ):
							value = new String( "null" );
							break;
						case( DATAS_PROGRAM_IS_WORK_DIRECTORY_LAST_AUTO_SELECT ):
							Boolean isWorkDirectoryLastAutoSelect = re01.jtextnotes.program.Parameters.get_IS_WORK_DIRECTORY_LAST_AUTO_SELECT();
							Parameters.setIsWorkDirectoryLastAutoSelect( isWorkDirectoryLastAutoSelect );
							value = isWorkDirectoryLastAutoSelect.toString();
							break;
						case( DATAS_PROGRAM_THEME_TYPE ):
							ThemeTypeEnum themeTypeDefault = re01.jtextnotes.program.Parameters.get_THEME_TYPE();
							re01.environment.Parameters.setThemeSelected( new Theme(themeTypeDefault) );
							value = themeTypeDefault.toString();
							break;
						case( DATAS_PROGRAM_FONTS_STYLES ):
							Parameters.setFontsStyles(new ArrayList<FontStyleEnum>(Arrays.asList(re01.jtextnotes.program.Parameters.get_FONT_STYLE())) );
							break;
						case( DATAS_PROGRAM_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN ):
							Boolean isViewWelcomeDoNotDisplayAgain = re01.jtextnotes.program.Parameters.get_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN();
							Parameters.setIsViewWelcomeDoNotDisplayAgain( isViewWelcomeDoNotDisplayAgain );
							value = isViewWelcomeDoNotDisplayAgain.toString();
							break;
						case( DATAS_PROGRAM_CHARS_BASE_SIZE ):
							value = String.valueOf( re01.environment.Parameters.get_DEFAULT_CHARS_BASE_SIZE() );
							break;
						case( DATAS_PROGRAM_ICONS_SIZE ):
							value = String.valueOf( re01.environment.Parameters.get_DEFAULT_ICONS_SIZE() );
							break;
						case( DATAS_PROGRAM_DOCUMENT_HISTORY_LENGTH ):
							value = String.valueOf( re01.environment.Parameters.get_DEFAULT_DOCUMENT_HISTORY_LENGTH() );
							break;
						case( DATAS_VIEW_WINDOW_WIDTH ):
							Integer windowWidth = re01.environment.Parameters.get_DEFAULT_WINDOW_WIDTH();
							Parameters.setWindowWidth( windowWidth );
							value = windowWidth.toString();
							break;
						case( DATAS_VIEW_WINDOW_HEIGHT ):
							Integer windowHeight = re01.environment.Parameters.get_DEFAULT_WINDOW_HEIGHT();
							Parameters.setWindowHeight( windowHeight );
							value = windowHeight.toString();
							break;
					}
				}
				break;
		}
		paramsApplied.put( option, value );
	}
	
	public HashMap<String, HashMap<String, String>> get() {
		HashMap<String, HashMap<String, String>> params = new HashMap<String, HashMap<String, String>>();
		try {
			Iterator<String> paramsSectionsIt = paramsSections.iterator();
			while ( paramsSectionsIt.hasNext() ) {
				HashMap<String, String> paramsSection = new HashMap<String, String>();
				String section = paramsSectionsIt.next();
				if ( section.toLowerCase().equals( DATAS_LANGUAGE_TO_LOWER_CASE.toLowerCase() ) ) {
					paramsSection.put( DATAS_LANGUAGE_TYPE, re01.environment.Parameters.getLanguageSelected().get_LANG_TYPE().toString() );
					if ( Parameters.getIsLanguageSelected() != null )
						paramsSection.put( DATAS_LANGUAGE_IS_SELECTED, Parameters.getIsLanguageSelected().toString() );
					else
						paramsSection.put( DATAS_LANGUAGE_IS_SELECTED, "null" );
				} else if ( section.toLowerCase().equals( DATAS_PROGRAM_TO_LOWER_CASE.toLowerCase() ) ) {
					if ( Parameters.getWorkDirectoryLastPath() != null )
						paramsSection.put( DATAS_PROGRAM_WORK_DIRECTORY_LAST_PATH, Parameters.getWorkDirectoryLastPath() );
					else
						paramsSection.put( DATAS_PROGRAM_WORK_DIRECTORY_LAST_PATH, "null" );
					
					if ( Parameters.getIsWorkDirectoryLastAutoSelect() != null )
						paramsSection.put( DATAS_PROGRAM_IS_WORK_DIRECTORY_LAST_AUTO_SELECT, Parameters.getIsWorkDirectoryLastAutoSelect().toString() );
					else
						paramsSection.put( DATAS_PROGRAM_IS_WORK_DIRECTORY_LAST_AUTO_SELECT, "null" );
					
					paramsSection.put( DATAS_PROGRAM_THEME_TYPE, re01.environment.Parameters.getThemeSelected().getThemeType().toString() );
					
					String fontsStyles = "";
					ArrayList<FontStyleEnum> userFontsStyles = Parameters.getFontsStyles();
					if ( userFontsStyles != null ) {
						Iterator<FontStyleEnum> userFontsStylesIt = userFontsStyles.iterator();
						while ( userFontsStylesIt.hasNext() ) {
							FontStyleEnum fontStyleFound = userFontsStylesIt.next();
							if ( !fontsStyles.equals("") )
								fontsStyles += ",";
							fontsStyles += fontStyleFound.toString();
						}
					}
					paramsSection.put( DATAS_PROGRAM_FONTS_STYLES, fontsStyles );
					if ( Parameters.getIsViewWelcomeDoNotDisplayAgain() != null )
						paramsSection.put( DATAS_PROGRAM_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN, Parameters.getIsViewWelcomeDoNotDisplayAgain().toString() );
					if ( re01.environment.Parameters.getCharsBaseSize() != null )
						paramsSection.put( DATAS_PROGRAM_CHARS_BASE_SIZE, re01.environment.Parameters.getCharsBaseSize().toString() );
					
					if ( re01.environment.Parameters.getIconsSize() != null )
						paramsSection.put( DATAS_PROGRAM_ICONS_SIZE, re01.environment.Parameters.getIconsSize().toString() );
					
					if ( re01.environment.Parameters.getDocumentHistoryLength() != null )
						paramsSection.put( DATAS_PROGRAM_DOCUMENT_HISTORY_LENGTH, re01.environment.Parameters.getDocumentHistoryLength().toString() );
					
				} else if ( section.toLowerCase().equals( DATAS_VIEW_TO_LOWER_CASE ) ) {
					paramsSection.put( DATAS_VIEW_WINDOW_WIDTH, Parameters.getWindowWidth().toString() );
					paramsSection.put( DATAS_VIEW_WINDOW_HEIGHT, Parameters.getWindowHeight().toString() );
				}
				params.put( section, paramsSection );
			}
		} catch ( Exception e ) {
			Core.get_LOGGER().write( e );
		}
		return params;
	}
	
	public NoteIconTypeEnum getImageIconType( HashMap<String, HashMap<String, String>> parameters ) {
		NoteIconTypeEnum iconTypeFound = null;
		ParameterReader parameterReader = new ParameterReader( parameters );
		String iconTypeStr = parameterReader.getValue( parameterReader.get_PARAMETER_HEADER(), "icon_type" );
		if ( iconTypeStr != null ) {
			String iconTypeStrLower = iconTypeStr.toLowerCase();
			NoteIconTypeEnum[] iconsTypes = NoteIconTypeEnum.values();
			int iconsTypesLength = iconsTypes.length;
			for ( Integer i = 0; i < iconsTypesLength; i++ ) {
				NoteIconTypeEnum iconsType = iconsTypes[i];
				if ( iconsType.toString().toLowerCase().equals(iconTypeStrLower) ) {
					iconTypeFound = iconsType;
					break;
				}
			}
		}
		return iconTypeFound;
	}
	
	public String getImageIconValue( HashMap<String, HashMap<String, String>> parameters ) {
		String imageIconValue = null;
		
		ParameterReader parameterReader = new ParameterReader( parameters );
		String imageIconValueFound = parameterReader.getValue( parameterReader.get_PARAMETER_HEADER(), "icon_value" );
		if ( imageIconValueFound != null )
			imageIconValue = imageIconValueFound;
		
		return imageIconValue;
	}
	
	public ImageIcon getImageIcon( HashMap<String, HashMap<String, String>> parameters ) {
		ImageIcon imageIcon = null;
		
		NoteIconTypeEnum iconTypeFound = null;
		ParameterReader parameterReader = new ParameterReader( parameters );
		String iconTypeStr = parameterReader.getValue( parameterReader.get_PARAMETER_HEADER(), "icon_type" );
		if ( iconTypeStr != null ) {
			String iconTypeStrLower = iconTypeStr.toLowerCase();
			NoteIconTypeEnum[] iconsTypes = NoteIconTypeEnum.values();
			int iconsTypesLength = iconsTypes.length;
			for ( Integer i = 0; i < iconsTypesLength; i++ ) {
				NoteIconTypeEnum iconsType = iconsTypes[i];
				if ( iconsType.toString().toLowerCase().equals(iconTypeStrLower) ) {
					iconTypeFound = iconsType;
					break;
				}
			}
		}
		if ( iconTypeFound != null ) {
			re01.jtextnotes.design.image.Images icons = new re01.jtextnotes.design.image.Images();
			String iconImgValueStr = parameterReader.getValue( parameterReader.get_PARAMETER_HEADER(), "icon_value" );
			imageIcon = icons.createImageIcon( iconTypeFound, iconImgValueStr );
		}
		
		return imageIcon;
	}
}
