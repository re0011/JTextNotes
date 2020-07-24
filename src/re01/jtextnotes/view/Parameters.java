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

package re01.jtextnotes.view;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import re01.design.font.FontSize;
import re01.design.font.FontStyleEnum;
import re01.design.view.awt.BorderLayout;
import re01.design.view.awt.GridLayout;
import re01.design.view.component.Paragraph;
import re01.design.view.component.Title1;
import re01.design.view.component.Title3;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JButton;
import re01.design.view.swing.JCheckBox;
import re01.design.view.swing.JLabel;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JScrollPane;
import re01.design.view.swing.JSlider;
import re01.design.view.swing.JTextField;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.design.view.swing.JButtonInputInt;
import re01.jtextnotes.program.Core;
import re01.jtextnotes.system.JTextNotes;

/**
 *
 * @author renaud
 */
public class Parameters extends Global {
	
	private static final String ARG_KEY_PARAMETER_CHARS_BASE_SIZE = "view_parameters_chars_base_size";
	private static final String ARG_KEY_PARAMETER_ICONS_SIZE = "view_parameters_icons_size";
	private static final String ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN = "view_parameters_is_view_welcome_do_not_display_again";
	private static final String ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT = "view_parameters_is_work_directory_last_auto_select";
	private static final String ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH = "view_parameters_document_history_length";
	
	private WindowAdapter windowAdapter = null;
	
	private final int TOOLTIP_DISMISS_TIMEOUT = ToolTipManager.sharedInstance().getDismissDelay();
	
	private Title1 title = null;
	
	private Title3 labelCharsBaseSize = null;
	private JSlider sliderCharsBaseSize = null;
	private JButtonInputInt buttonInputCharsBaseSizeLess = null;
	private JButtonInputInt buttonInputCharsBaseSizeMore = null;
	private JTextField inputCharsBaseSize = null;
	private Integer charsBaseSizeValue = null;
	private JLabel labelCharsBaseSizeInfo = null;
	private Paragraph pCharsBaseSizeInfo = null;
	
	private Title3 labelIconsSize = null;
	private JSlider sliderIconsSize = null;
	private JButtonInputInt buttonInputIconsSizeLess = null;
	private JButtonInputInt buttonInputIconsSizeMore = null;
	private JTextField inputIconsSize = null;
	private Integer iconsSizeValue = null;
	private JLabel labelIconsSizeInfo = null;
	private Paragraph pIconsSizeInfo = null;
	private JLabel labelIconFileExample = null;
	private JLabel labelIconOkExample = null;
	
	private Title3 labelProgramStart = null;
	private JCheckBox checkIsViewWelcomeDoNotDisplayAgain = null;
	private JCheckBox checkIsWorkDirectoryLastAutoSelect = null;
	
	private Title3 labelDocumentHistoryLength = null;
	private JButtonInputInt buttonInputDocumentHistoryLengthLess = null;
	private JButtonInputInt buttonInputDocumentHistoryLengthMore = null;
	private JTextField inputDocumentHistoryLength = null;
	private Integer documentHistoryLengthValue = null;
	private JLabel labelDocumentHistoryLengthInfo = null;
	private Paragraph pDocumentHistoryLengthInfo = null;
	
	private JButton buttonCancel = null;
	private JButton buttonOk = null;
	
	private re01.jtextnotes.design.image.Images icons = new re01.jtextnotes.design.image.Images();
	
	public Parameters(String viewTitle, Object[] callbacksArgs) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				JTextNotes.setViewParameters( null );
				
				super.windowClosing( windowEvent );
			}
		};
		this.addWindowListener( windowAdapter );
		
		this.getContentPane().setLayout( new BorderLayout() );
		this.setPreferredSize( new Dimension( re01.environment.Parameters.get_RECOMMENDED_PARAMETERS_WINDOW_WIDTH(), re01.environment.Parameters.get_RECOMMENDED_PARAMETERS_WINDOW_HEIGHT() ) );
		isResizable = true;
		
		//====================
		// region north
		//====================
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout( new BoxLayout( panelNorth, BoxLayout.PAGE_AXIS ) );
		
		title = new Title1( LANG.get_LANG().get_PARAMETERS() );
		panelNorth.add( title );
		
		this.add( panelNorth, "North" );
		
		//====================
		// end region north
		//====================
		
		//====================
		// region center
		//====================
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout( new BorderLayout() );
		
		JPanel subPanelCenter = new JPanel();
		subPanelCenter.setLayout( new BoxLayout( subPanelCenter, BoxLayout.PAGE_AXIS ) );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		labelCharsBaseSize = new Title3( LANG.get_LANG().get_CHARS_BASE_SIZE() );
		subPanelCenter.add( labelCharsBaseSize );
		
		charsBaseSizeValue = MethodHelper.getArgInteger( ARG_KEY_PARAMETER_CHARS_BASE_SIZE, callbacksArgs );
		Integer sliderCharsBaseSizeIntDefaultIndex = ( charsBaseSizeValue != null ) ? charsBaseSizeValue : 0;
		
		ArrayList<Integer> charsBaseSizeRange = re01.environment.Parameters.getCharsBaseSizeRange();
		sliderCharsBaseSize = new JSlider( javax.swing.JSlider.HORIZONTAL, 1, charsBaseSizeRange.size(), sliderCharsBaseSizeIntDefaultIndex, charsBaseSizeRange );
		sliderCharsBaseSize.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				
				if ( inputCharsBaseSize != null ) {
					Integer slideIntVal = Integer.parseInt( ((Hashtable<String, JLabel>)slider.getLabelTable()).get(slider.getValue()).getFullText() );
					if ( slideIntVal != null )
						charsBaseSizeValue = slideIntVal;

					checkCharsBaseSizeValue();
					inputCharsBaseSize.setText( charsBaseSizeValue.toString() );
					setFonts();
				}
			}
		} );
		subPanelCenter.add( sliderCharsBaseSize );
		
		JPanel panelInputCharsBaseSize = new JPanel();
		panelInputCharsBaseSize.setLayout( new BoxLayout( panelInputCharsBaseSize, BoxLayout.LINE_AXIS ) );
		
		buttonInputCharsBaseSizeLess = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_NEGATIVE() );
		buttonInputCharsBaseSizeLess.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				charsBaseSizeValue -= re01.environment.Parameters.getCharsBaseSizeIncrementValue();
				checkCharsBaseSizeValue();
				inputCharsBaseSize.setText( charsBaseSizeValue.toString() );
				setFonts();
			}
		} );
		panelInputCharsBaseSize.add( buttonInputCharsBaseSizeLess );
		
		MouseAdapter inputCharsBaseSizeMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay(300000);
				ToolTipManager.sharedInstance().setInitialDelay(20);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay( TOOLTIP_DISMISS_TIMEOUT );
			}
		};
		inputCharsBaseSize = new JTextField();
		inputCharsBaseSize.addMouseListener( inputCharsBaseSizeMouseAdapter );
		
		if ( charsBaseSizeValue != null )
			inputCharsBaseSize.setText( charsBaseSizeValue.toString() );
		inputCharsBaseSize.setEditable( false );
		inputCharsBaseSize.setToolTipText( LANG.get_LANG_PROGRAM().get_INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO() );
		
		panelInputCharsBaseSize.add( inputCharsBaseSize );
		
		buttonInputCharsBaseSizeMore = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_PLUS() );
		buttonInputCharsBaseSizeMore.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				charsBaseSizeValue += re01.environment.Parameters.getCharsBaseSizeIncrementValue();
				if ( charsBaseSizeValue > re01.environment.Parameters.get_MAX_CHARS_BASE_SIZE() )
					charsBaseSizeValue = re01.environment.Parameters.get_MAX_CHARS_BASE_SIZE();
				
				inputCharsBaseSize.setText( charsBaseSizeValue.toString() );
				setFonts();
			}
		} );
		panelInputCharsBaseSize.add( buttonInputCharsBaseSizeMore );
		
		subPanelCenter.add( panelInputCharsBaseSize );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		JPanel panelInputCharsBaseSizeInfo = new JPanel();
		panelInputCharsBaseSizeInfo.setLayout( new BoxLayout( panelInputCharsBaseSizeInfo, BoxLayout.LINE_AXIS ) );
		
		labelCharsBaseSizeInfo = new JLabel( icons.get_GLOBAL_IMAGE_ICON_INFO() );
		panelInputCharsBaseSizeInfo.add( labelCharsBaseSizeInfo );
		
		pCharsBaseSizeInfo = new Paragraph( LANG.get_LANG_PROGRAM().get_CHARS_BASE_SIZE_PARAM_INFO() );
		panelInputCharsBaseSizeInfo.add( pCharsBaseSizeInfo );
		
		subPanelCenter.add( panelInputCharsBaseSizeInfo );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		labelIconsSize = new Title3( LANG.get_LANG().get_ICONS_SIZE() );
		subPanelCenter.add( labelIconsSize );
		
		iconsSizeValue = MethodHelper.getArgInteger( ARG_KEY_PARAMETER_ICONS_SIZE, callbacksArgs );
		Integer sliderIconsSizeIntDefaultIndex = ( iconsSizeValue != null ) ? iconsSizeValue : 1;
		
		ArrayList<Integer> iconsSizeRange = re01.environment.Parameters.getIconsSizeRange();
		sliderIconsSize = new JSlider( javax.swing.JSlider.HORIZONTAL, 1, iconsSizeRange.size(), sliderIconsSizeIntDefaultIndex, iconsSizeRange );
		sliderIconsSize.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if ( inputIconsSize != null ) {
					Integer slideIntVal = Integer.parseInt( ((Hashtable<String, JLabel>)slider.getLabelTable()).get(slider.getValue()).getFullText() );
					if ( slideIntVal != null )
						iconsSizeValue = slideIntVal;

					inputIconsSize.setText( iconsSizeValue.toString() );
					setIcons();
				}
			}
		} );
		subPanelCenter.add( sliderIconsSize );
		
		JPanel panelInputIconsSize = new JPanel();
		panelInputIconsSize.setLayout( new BoxLayout( panelInputIconsSize, BoxLayout.LINE_AXIS ) );
		
		buttonInputIconsSizeLess = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_NEGATIVE() );
		buttonInputIconsSizeLess.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iconsSizeValue -= re01.environment.Parameters.getIconsSizeIncrementValue();
				if ( iconsSizeValue < re01.environment.Parameters.get_MIN_ICONS_SIZE() )
					iconsSizeValue = re01.environment.Parameters.get_MIN_ICONS_SIZE();
				
				inputIconsSize.setText( iconsSizeValue.toString() );
				setIcons();
			}
		} );
		panelInputIconsSize.add( buttonInputIconsSizeLess );
		
		MouseAdapter inputIconsSizeMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay(300000);
				ToolTipManager.sharedInstance().setInitialDelay(20);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay( TOOLTIP_DISMISS_TIMEOUT );
			}
		};
		inputIconsSize = new JTextField();
		inputIconsSize.addMouseListener( inputIconsSizeMouseAdapter );
		
		if ( iconsSizeValue != null )
			inputIconsSize.setText( iconsSizeValue.toString() );
		inputIconsSize.setEditable( false );
		inputIconsSize.setToolTipText( LANG.get_LANG_PROGRAM().get_INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO() );
		
		panelInputIconsSize.add( inputIconsSize );
		
		buttonInputIconsSizeMore = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_PLUS() );
		buttonInputIconsSizeMore.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iconsSizeValue += re01.environment.Parameters.getIconsSizeIncrementValue();
				if ( iconsSizeValue > re01.environment.Parameters.get_MAX_ICONS_SIZE() )
					iconsSizeValue = re01.environment.Parameters.get_MAX_ICONS_SIZE();
				
				inputIconsSize.setText( iconsSizeValue.toString() );
				setIcons();
			}
		} );
		panelInputIconsSize.add( buttonInputIconsSizeMore );
		
		subPanelCenter.add( panelInputIconsSize );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		JPanel panelInputIconsSizeInfo = new JPanel();
		panelInputIconsSizeInfo.setLayout( new BoxLayout( panelInputIconsSizeInfo, BoxLayout.LINE_AXIS ) );
		
		labelIconsSizeInfo = new JLabel( icons.get_GLOBAL_IMAGE_ICON_INFO() );
		panelInputIconsSizeInfo.add( labelIconsSizeInfo );
		
		pIconsSizeInfo = new Paragraph( LANG.get_LANG_PROGRAM().get_ICONS_SIZE_PARAM_INFO() );
		panelInputIconsSizeInfo.add( pIconsSizeInfo );
		
		subPanelCenter.add( panelInputIconsSizeInfo );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		JPanel panelIconsSizeExample = new JPanel();
		panelIconsSizeExample.setLayout( new BoxLayout( panelIconsSizeExample, BoxLayout.LINE_AXIS ) );
		
		labelIconFileExample = new JLabel( icons.get_GLOBAL_IMAGE_ICON_FILE() );
		MouseAdapter labelIconFileMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay(300000);
				ToolTipManager.sharedInstance().setInitialDelay(20);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay( TOOLTIP_DISMISS_TIMEOUT );
			}
		};
		labelIconFileExample.addMouseListener( labelIconFileMouseAdapter );
		labelIconFileExample.setToolTipText( LANG.get_LANG_PROGRAM().get_ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO() );
		panelIconsSizeExample.add( labelIconFileExample );
		
		panelIconsSizeExample.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		labelIconOkExample = new JLabel( icons.get_GLOBAL_IMAGE_ICON_OK() );
		MouseAdapter labelIconOkMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay(300000);
				ToolTipManager.sharedInstance().setInitialDelay(20);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay( TOOLTIP_DISMISS_TIMEOUT );
			}
		};
		labelIconOkExample.addMouseListener( labelIconOkMouseAdapter );
		labelIconOkExample.setToolTipText( LANG.get_LANG_PROGRAM().get_ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO() );
		panelIconsSizeExample.add( labelIconOkExample );
		
		subPanelCenter.add( panelIconsSizeExample );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		labelProgramStart = new Title3( LANG.get_LANG().get_PROGRAM_START() );
		subPanelCenter.add( labelProgramStart );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		checkIsViewWelcomeDoNotDisplayAgain = new JCheckBox( LANG.get_LANG_PROGRAM().get_WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN() );
		
		Boolean checkBoolIsViewWelcomeDoNotDisplayAgain = MethodHelper.getArgBoolean(ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN, THIS.getCallbacksArgs() );
		if ( checkBoolIsViewWelcomeDoNotDisplayAgain != null ) {
			checkIsViewWelcomeDoNotDisplayAgain.setSelected( checkBoolIsViewWelcomeDoNotDisplayAgain );
		}
		
		JPanel panelCheckIsViewWelcomeDoNotDisplayAgain = new JPanel();
		panelCheckIsViewWelcomeDoNotDisplayAgain.setLayout( new BoxLayout( panelCheckIsViewWelcomeDoNotDisplayAgain, BoxLayout.LINE_AXIS ) );
		
		panelCheckIsViewWelcomeDoNotDisplayAgain.add( checkIsViewWelcomeDoNotDisplayAgain );
		panelCheckIsViewWelcomeDoNotDisplayAgain.add( new Paragraph("") );
		
		subPanelCenter.add( panelCheckIsViewWelcomeDoNotDisplayAgain );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		checkIsWorkDirectoryLastAutoSelect = new JCheckBox( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_IS_LAST_AUTO_OPEN() );
		
		Boolean checkBoolIsWorkDirectoryLastAutoSelect = MethodHelper.getArgBoolean(ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT, THIS.getCallbacksArgs() );
		if ( checkBoolIsWorkDirectoryLastAutoSelect != null ) {
			checkIsWorkDirectoryLastAutoSelect.setSelected( checkBoolIsWorkDirectoryLastAutoSelect );
		}
		
		JPanel panelCheckIsWorkDirectoryLastAutoSelect = new JPanel();
		panelCheckIsWorkDirectoryLastAutoSelect.setLayout( new BoxLayout( panelCheckIsWorkDirectoryLastAutoSelect, BoxLayout.LINE_AXIS ) );
		
		panelCheckIsWorkDirectoryLastAutoSelect.add( checkIsWorkDirectoryLastAutoSelect );
		panelCheckIsWorkDirectoryLastAutoSelect.add( new Paragraph("") );
		
		subPanelCenter.add( panelCheckIsWorkDirectoryLastAutoSelect );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		labelDocumentHistoryLength = new Title3( LANG.get_LANG().get_DOCUMENT_HISTORY_LENGTH() );
		subPanelCenter.add( labelDocumentHistoryLength );
		
		documentHistoryLengthValue = MethodHelper.getArgInteger( ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH, callbacksArgs );
		
		JPanel panelDocumentHistoryLength = new JPanel();
		panelDocumentHistoryLength.setLayout( new BoxLayout( panelDocumentHistoryLength, BoxLayout.LINE_AXIS ) );
		
		buttonInputDocumentHistoryLengthLess = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_NEGATIVE() );
		buttonInputDocumentHistoryLengthLess.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				documentHistoryLengthValue -= 1;
				if ( documentHistoryLengthValue < re01.environment.Parameters.get_MIN_DOCUMENT_HISTORY_LENGTH() )
					documentHistoryLengthValue = re01.environment.Parameters.get_MIN_DOCUMENT_HISTORY_LENGTH();
				
				inputDocumentHistoryLength.setText( documentHistoryLengthValue.toString() );
			}
		} );
		panelDocumentHistoryLength.add( buttonInputDocumentHistoryLengthLess );
		
		inputDocumentHistoryLength = new JTextField();
		inputDocumentHistoryLength.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay(300000);
				ToolTipManager.sharedInstance().setInitialDelay(20);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ToolTipManager.sharedInstance().setDismissDelay( TOOLTIP_DISMISS_TIMEOUT );
			}
		} );
		
		if ( documentHistoryLengthValue != null )
			inputDocumentHistoryLength.setText( documentHistoryLengthValue.toString() );
		
		panelDocumentHistoryLength.add( inputDocumentHistoryLength );
		
		buttonInputDocumentHistoryLengthMore = new JButtonInputInt( LANG.get_LANG().get_MATHS_SYMBOL_PLUS() );
		buttonInputDocumentHistoryLengthMore.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				documentHistoryLengthValue += 1;
				if ( documentHistoryLengthValue > re01.environment.Parameters.get_MAX_DOCUMENT_HISTORY_LENGTH() )
					documentHistoryLengthValue = re01.environment.Parameters.get_MAX_DOCUMENT_HISTORY_LENGTH();
				inputDocumentHistoryLength.setText( documentHistoryLengthValue.toString() );
			}
		} );
		panelDocumentHistoryLength.add( buttonInputDocumentHistoryLengthMore );
		
		subPanelCenter.add( panelDocumentHistoryLength );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		JPanel panelInputDocumentHistoryLengthInfo = new JPanel();
		panelInputDocumentHistoryLengthInfo.setLayout( new BoxLayout( panelInputDocumentHistoryLengthInfo, BoxLayout.LINE_AXIS ) );
		
		labelDocumentHistoryLengthInfo = new JLabel( icons.get_GLOBAL_IMAGE_ICON_INFO() );
		panelInputDocumentHistoryLengthInfo.add( labelDocumentHistoryLengthInfo );
		
		pDocumentHistoryLengthInfo = new Paragraph( LANG.get_LANG().get_TEXT_DOCUMENT_HISTORY_LENGTH_INFO());
		panelInputDocumentHistoryLengthInfo.add( pDocumentHistoryLengthInfo );
		
		subPanelCenter.add( panelInputDocumentHistoryLengthInfo );
		
		subPanelCenter.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, COMPONENT_SPACING_SIZE)) );
		
		panelCenter.add( subPanelCenter, "North" );
		
		scrollCenter = new JScrollPane( panelCenter );
		
		this.add( scrollCenter, "Center" );
		
		//====================
		// end region center
		//====================
		
		//====================
		// region south
		//====================
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout( new GridLayout( 1, 1 ) );
		
		JPanel subPanelSouth = new JPanel();
		subPanelSouth.setLayout( new BoxLayout( subPanelSouth, BoxLayout.LINE_AXIS ) );
		subPanelSouth.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
		
		subPanelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		buttonOk = new JButton( LANG.get_LANG().get_OK() );
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				checkCharsBaseSizeValue();
				
				THIS.delete();
				JTextNotes.setViewParameters( null );
				try {
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_PARAMETER_CHARS_BASE_SIZE, charsBaseSizeValue, THIS.getCallbacksArgs() );
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_PARAMETER_ICONS_SIZE, iconsSizeValue, THIS.getCallbacksArgs() );
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN, checkIsViewWelcomeDoNotDisplayAgain.isSelected(), THIS.getCallbacksArgs() );
					MethodHelper.addOrReplaceCallbackArg( ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT, checkIsWorkDirectoryLastAutoSelect.isSelected(), THIS.getCallbacksArgs() );
					
					Integer inputDocumentHistoryLengthInt = null;
					try {
						inputDocumentHistoryLengthInt = Integer.parseInt( inputDocumentHistoryLength.getText() );
					} catch (Exception ex) { }
					if ( inputDocumentHistoryLengthInt != null ) {
						if ( inputDocumentHistoryLengthInt < re01.environment.Parameters.get_MIN_DOCUMENT_HISTORY_LENGTH() )
							inputDocumentHistoryLengthInt = re01.environment.Parameters.get_MIN_DOCUMENT_HISTORY_LENGTH();
						else if ( inputDocumentHistoryLengthInt > re01.environment.Parameters.get_MAX_DOCUMENT_HISTORY_LENGTH() )
							inputDocumentHistoryLengthInt = re01.environment.Parameters.get_MAX_DOCUMENT_HISTORY_LENGTH();
						
						MethodHelper.addOrReplaceCallbackArg( ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH, inputDocumentHistoryLengthInt, THIS.getCallbacksArgs() );
					}
					MethodHelper.executeCallbacks( THIS.getCallbacksArgs() );
				} catch (Exception ex) {
					Core.get_LOGGER().write( ex );
				}
			}
		} );
		subPanelSouth.add( buttonOk );
		
		subPanelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		buttonCancel = new JButton( LANG.get_LANG().get_CANCEL() );
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				THIS.delete();
				JTextNotes.setViewParameters( null );
			}
		} );
		subPanelSouth.add( buttonCancel );
		
		subPanelSouth.add( Box.createRigidArea(new Dimension(COMPONENT_SPACING_SIZE, 0)) );
		
		panelSouth.add( subPanelSouth );
		
		this.add( panelSouth, "South" );
		
		//====================
		// end region south
		//====================
		
		display();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			this.removeWindowListener( windowAdapter );
		} catch (Exception e) { }
		
		super.finalize();
	}

	public static final String get_ARG_KEY_PARAMETER_CHARS_BASE_SIZE() {
		return ARG_KEY_PARAMETER_CHARS_BASE_SIZE;
	}

	public static final String get_ARG_KEY_PARAMETER_ICONS_SIZE() {
		return ARG_KEY_PARAMETER_ICONS_SIZE;
	}

	public static final String get_ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN() {
		return ARG_KEY_PARAMETER_IS_VIEW_WELCOME_DO_NOT_DISPLAY_AGAIN;
	}

	public static final String get_ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT() {
		return ARG_KEY_PARAMETER_IS_WORK_DIRECTORY_LAST_AUTO_SELECT;
	}

	public static final String get_ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH() {
		return ARG_KEY_PARAMETER_DOCUMENT_HISTORY_LENGTH;
	}
	
	private void checkCharsBaseSizeValue() {
		if ( re01.environment.Parameters.get_MIN_CHARS_BASE_SIZE() > re01.environment.Parameters.get_STRICT_MIN_CHARS_BASE_SIZE() ) {
			if ( charsBaseSizeValue < re01.environment.Parameters.get_MIN_CHARS_BASE_SIZE() )
				charsBaseSizeValue = re01.environment.Parameters.get_MIN_CHARS_BASE_SIZE();
		} else {
			if ( charsBaseSizeValue < re01.environment.Parameters.get_STRICT_MIN_CHARS_BASE_SIZE() )
				charsBaseSizeValue = re01.environment.Parameters.get_STRICT_MIN_CHARS_BASE_SIZE();
		}
	}
	
	private void setFonts() {
		re01.design.font.Fonts fonts = new re01.design.font.Fonts();
		String fontFamiltyName = ( fonts.getFontFamilyGlobal() != null ) ? fonts.getFontFamilyGlobal() : getFont().getFamily();
		
		FontSize fontSizeNormal = new FontSize( re01.environment.Parameters.getThemeSelected(), FontStyleEnum.SizeNormal, charsBaseSizeValue );
		java.awt.Font fontNormal = new java.awt.Font( fontFamiltyName, java.awt.Font.PLAIN, fontSizeNormal.getSize() );
		FontSize fontSizeTitle1 = new FontSize( re01.environment.Parameters.getThemeSelected(), FontStyleEnum.Title1, charsBaseSizeValue );
		java.awt.Font fontTitle1 = new java.awt.Font( fontFamiltyName, java.awt.Font.BOLD, fontSizeTitle1.getSize() );
		FontSize fontSizeTitle3 = new FontSize( re01.environment.Parameters.getThemeSelected(), FontStyleEnum.Title3, charsBaseSizeValue );
		java.awt.Font fontTitle3 = new java.awt.Font( fontFamiltyName, java.awt.Font.BOLD, fontSizeTitle3.getSize() );

		title.setFont( fontTitle1 );
		
		labelCharsBaseSize.setFont( fontTitle3 );
		sliderCharsBaseSize.setFont( fontNormal );
		Dictionary sliderCharsBaseSizeLabels = sliderCharsBaseSize.getLabelTable();
		Enumeration sliderCharsBaseSizeLabelsEnum = sliderCharsBaseSizeLabels.elements();
		while ( sliderCharsBaseSizeLabelsEnum.hasMoreElements() ) {
			Object obj = sliderCharsBaseSizeLabelsEnum.nextElement();
			try {
				JComponent component = (JComponent) obj;
				component.setFont( fontNormal );
			} catch (Exception e) {
				Core.get_LOGGER().write( e );
			}
		}
		buttonInputCharsBaseSizeLess.setFont( fontNormal );
		buttonInputCharsBaseSizeMore.setFont( fontNormal );
		inputCharsBaseSize.setFont( fontNormal );
		pCharsBaseSizeInfo.setFont( fontNormal );
		
		labelIconsSize.setFont( fontTitle3 );
		sliderIconsSize.setFont( fontNormal );
		Dictionary sliderIconsSizeLabels = sliderIconsSize.getLabelTable();
		Enumeration sliderIconsSizeLabelsEnum = sliderIconsSizeLabels.elements();
		while ( sliderIconsSizeLabelsEnum.hasMoreElements() ) {
			Object obj = sliderIconsSizeLabelsEnum.nextElement();
			try {
				JComponent component = (JComponent) obj;
				component.setFont( fontNormal );
			} catch (Exception e) {
				Core.get_LOGGER().write( e );
			}
		}
		buttonInputIconsSizeLess.setFont( fontNormal );
		buttonInputIconsSizeMore.setFont( fontNormal );
		inputIconsSize.setFont( fontNormal );
		pIconsSizeInfo.setFont( fontNormal );
		
		labelProgramStart.setFont( fontTitle3 );
		checkIsViewWelcomeDoNotDisplayAgain.setFont( fontNormal );
		checkIsWorkDirectoryLastAutoSelect.setFont( fontNormal );
		
		labelDocumentHistoryLength.setFont( fontTitle3 );
		buttonInputDocumentHistoryLengthLess.setFont( fontNormal );
		buttonInputDocumentHistoryLengthMore.setFont( fontNormal );
		inputDocumentHistoryLength.setFont( fontNormal );
		pDocumentHistoryLengthInfo.setFont( fontNormal );
		
		buttonCancel.setFont( fontNormal );
		buttonOk.setFont( fontNormal );
	}
	
	private void setIcons() {
		ImageIcon imageIconInfo = icons.createGlobalImageIconInfo(iconsSizeValue);
		labelCharsBaseSizeInfo.setIcon( imageIconInfo );
		labelIconsSizeInfo.setIcon( imageIconInfo );
		labelIconFileExample.setIcon( icons.createGlobalImageIconFile(iconsSizeValue) );
		labelIconOkExample.setIcon( icons.createGlobalImageIconOk(iconsSizeValue) );
		labelDocumentHistoryLengthInfo.setIcon( imageIconInfo );
	}
	
}
