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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import re01.design.color.Color;
import re01.design.color.ColorAttributeTypeEnum;
import re01.design.color.ColorTypeEnum;
import re01.design.font.FontStyleEnum;
import re01.design.view.awt.BorderLayout;
import re01.design.view.component.Paragraph;
import re01.design.view.component.Title1;
import re01.design.view.component.Title3;
import re01.design.view.swing.BoxLayout;
import re01.design.view.swing.JLabel;
import re01.design.view.swing.JPanel;
import re01.design.view.swing.JScrollPane;
import re01.design.view.swing.JSplitPane;
import re01.design.view.swing.JTree;
import re01.design.view.swing.jtree.DefaultMutableTreeNode;
import re01.exception.Re01JLibException;
import re01.tool.helper.system.MethodHelper;
import re01.jtextnotes.design.image.Images;
import re01.jtextnotes.program.Core;

/**
 *
 * @author renaud
 */
public class About extends Global {
	
	private static final String ARG_KEY_DEFAULT_NODE_STR = "view_about_default_node_str";
	
	private WindowAdapter windowAdapter = null;
	
	private JSplitPane splitPaneTreeAndFileContent;
	private JPanel panelFileContent = null;
	
	private DefaultMutableTreeNode rootFolderNotesTree = null;
	private DefaultMutableTreeNode nodeSelected = null;
	
	private JTree tree = null;
	private TreeSelectionListener treeSelectionListener = null;
	
	private String licenseGnuText = null;
	
	public About( String viewTitle, Object[] callbacksArgs ) throws Re01JLibException {
		super( viewTitle, callbacksArgs );
		construct();
	}
	
	private void construct() throws Re01JLibException {
		windowAdapter = new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing( java.awt.event.WindowEvent windowEvent ) {
				THIS.delete();
				
				super.windowClosing( windowEvent );
			}
		};
		this.addWindowListener( windowAdapter );
		
		this.setPreferredSize( new Dimension( re01.environment.Parameters.get_RECOMMENDED_PARAMETERS_WINDOW_WIDTH(), re01.environment.Parameters.get_RECOMMENDED_PARAMETERS_WINDOW_HEIGHT() ) );
		
		DefaultMutableTreeNode nodeHelp = new DefaultMutableTreeNode( LANG.get_LANG().get_HELP() );
		DefaultMutableTreeNode nodeAbout = new DefaultMutableTreeNode( LANG.get_LANG().get_ABOUT() );
		DefaultMutableTreeNode nodeLicense = new DefaultMutableTreeNode( LANG.get_LANG().get_LICENSE() );
		DefaultMutableTreeNode nodeLicenseGnu = new DefaultMutableTreeNode( LANG.get_LANG().get_LICENSE_GNU_SHORT() );
		
		nodeLicense.add( nodeLicenseGnu );
		
		rootFolderNotesTree = new DefaultMutableTreeNode( LANG.get_LANG_PROGRAM().get_PROGRAM_NAME() );
		rootFolderNotesTree.add( nodeHelp );
		rootFolderNotesTree.add( nodeAbout );
		rootFolderNotesTree.add( nodeLicense );
		
		DefaultTreeModel treeModel = new DefaultTreeModel(rootFolderNotesTree);

		tree = new JTree( rootFolderNotesTree );
		tree.setModel( treeModel );
		
		treeSelectionListener = new TreeSelectionListener() {
			@Override
			public void valueChanged( TreeSelectionEvent e ) {
				DefaultMutableTreeNode nodeSelectedNew = ( DefaultMutableTreeNode ) e.getNewLeadSelectionPath().getLastPathComponent();
				if ( nodeSelected == null || nodeSelected != null && nodeSelected.equals(nodeSelectedNew) == false ) {
					nodeSelected = nodeSelectedNew;

					try {
						openContent( nodeSelected );
					} catch (Re01JLibException ex) {
						Core.get_LOGGER().write( ex );
					}
				}
			}

		};
		tree.addTreeSelectionListener( treeSelectionListener );
		
		tree.setCellRenderer( new DefaultTreeCellRenderer () {

			@Override
			public Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				Images icons = new Images();
				DefaultMutableTreeNode node = ( DefaultMutableTreeNode ) value;
				JLabel label = new JLabel( ( String ) node.getUserObject(), icons.get_GLOBAL_IMAGE_ICON_FILE(), 0 );
				if ( selected == true ) {
					label.setOpaque( true );
					label.setBackground( new Color(ColorTypeEnum.GrayLight, ColorAttributeTypeEnum.Background).getRgbColor() );
				} else {
					label.setOpaque( false );
				}
				return label;
			}
		} );
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		tree.setBackground( new Color( ColorTypeEnum.WhiteGhost, ColorAttributeTypeEnum.Background ).getRgbColor() );
		JScrollPane treeView = new JScrollPane( tree );
		
		panelFileContent = new JPanel();
		panelFileContent.setLayout( new BoxLayout( panelFileContent, BoxLayout.PAGE_AXIS ) );
		panelFileContent.setName( "file_content" );

		Paragraph titleWelcomeTest = new Paragraph( LANG.get_LANG().get_WELCOME() );

		panelFileContent.add( titleWelcomeTest );

		if ( treeView != null ) {
			splitPaneTreeAndFileContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, treeView, panelFileContent );
			this.add( splitPaneTreeAndFileContent );
		}
		
		display();
		
		//====================
		// region open desired node
		//====================
		
		String defaultNodeStr = MethodHelper.getArgString( ARG_KEY_DEFAULT_NODE_STR, THIS.getCallbacksArgs());
		nodeSelected = new DefaultMutableTreeNode(defaultNodeStr);
		openContent( nodeSelected );
		
		TreePath treePath = tree.getNextMatch( defaultNodeStr, 0, Position.Bias.Forward );
		tree.setSelectionPath( treePath );
		
		//====================
		// end region open desired node
		//====================
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			this.removeWindowListener( windowAdapter );
		} catch (Exception e) { }
		try {
			tree.removeTreeSelectionListener( treeSelectionListener );
		} catch (Exception e) { }
		
		super.finalize();
	}
	
	public JPanel getPanelFileContent() {
		return panelFileContent;
	}

	public void setPanelFileContent(JPanel panelFileContent) {
		this.panelFileContent = panelFileContent;
	}
	
	public DefaultMutableTreeNode getNodeSelected() {
		return nodeSelected;
	}

	public void setNodeSelected(DefaultMutableTreeNode nodeSelected) {
		this.nodeSelected = nodeSelected;
	}

	public static final String get_ARG_KEY_DEFAULT_NODE_STR() {
		return ARG_KEY_DEFAULT_NODE_STR;
	}
	
	public void openContent( DefaultMutableTreeNode node ) throws Re01JLibException {
		deleteContent();
		createContent( node );
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				scrollCenter.getViewport().setViewPosition(new Point(0, 0));
				scrollCenter.revalidate();
			}
		} );
	}
	
	private void deleteContent() {
		panelFileContent.removeAll();
		SwingUtilities.updateComponentTreeUI( panelFileContent );
	}
	
	private void createContent( DefaultMutableTreeNode node ) throws Re01JLibException {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout( new BorderLayout() );

		JPanel subPanelCenter = new JPanel();
		subPanelCenter.setLayout( new BoxLayout( subPanelCenter, BoxLayout.PAGE_AXIS ) );
		
		if ( ((String)nodeSelected.getUserObject()).equals(LANG.get_LANG().get_HELP()) ) {
			Title1 title = new Title1( LANG.get_LANG().get_HELP() );
			subPanelCenter.add( title );
			
			Title3 titleJTextNotes = new Title3( LANG.get_LANG_PROGRAM().get_PROGRAM_NAME() );
			subPanelCenter.add( titleJTextNotes );
			
			Paragraph pJTextNotes = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_WHAT_IS_JTEXTNOTES() );
			subPanelCenter.add( pJTextNotes );
			
			Title3 titleWorkDirectory = new Title3( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY() );
			subPanelCenter.add( titleWorkDirectory );
			
			Paragraph pHelpOpenMultiplesWorkDirectories = new Paragraph( "" );
			pHelpOpenMultiplesWorkDirectories.addText(LANG.get_LANG_PROGRAM().get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			pHelpOpenMultiplesWorkDirectories.addText( 
				LANG.get_LANG_PROGRAM().get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2(), 
				re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold)), null, null)
			);
			pHelpOpenMultiplesWorkDirectories.addText(LANG.get_LANG_PROGRAM().get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			pHelpOpenMultiplesWorkDirectories.addText(LANG.get_LANG_PROGRAM().get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			pHelpOpenMultiplesWorkDirectories.addText(LANG.get_LANG_PROGRAM().get_HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			subPanelCenter.add( pHelpOpenMultiplesWorkDirectories );
			
			Title3 titleCloseWorkDirectory = new Title3( LANG.get_LANG_PROGRAM().get_WORK_DIRECTORY_CLOSE() );
			subPanelCenter.add( titleCloseWorkDirectory );
			
			Paragraph pCloseWorkDirectory = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_CLOSE_WORK_DIRECTORY() );
			subPanelCenter.add( pCloseWorkDirectory );
			
			Title3 titleNoteOpenedInTree = new Title3( LANG.get_LANG_PROGRAM().get_NOTE_OPENED() );
			subPanelCenter.add( titleNoteOpenedInTree );
			
			Paragraph pNoteOpened = new Paragraph( "" );
			pNoteOpened.addText(LANG.get_LANG_PROGRAM().get_HELP_NOTE_OPENED_IN_TREE(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			pNoteOpened.addText( 
				LANG.get_LANG_PROGRAM().get_HELP_NOTE_OPENED_IN_TREE_2(), 
				re01.environment.Parameters.getThemeSelected().createFont(new ArrayList<FontStyleEnum>(Arrays.asList(FontStyleEnum.Bold)), ColorTypeEnum.Blue, ColorTypeEnum.GrayLight)
			);
			pNoteOpened.addText(LANG.get_LANG_PROGRAM().get_HELP_NOTE_OPENED_IN_TREE_3(), 
				re01.environment.Parameters.getThemeSelected().createFont(re01.jtextnotes.system.Parameters.getFontsStyles(), null, null)
			);
			subPanelCenter.add( pNoteOpened );
			
			Title3 titleNotesAreSimpleFiles = new Title3( LANG.get_LANG_PROGRAM().get_NOTES() );
			subPanelCenter.add( titleNotesAreSimpleFiles );
			
			Paragraph pNotesAreSimpleFiles = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_NOTES_ARE_SIMPLE_FILES() );
			subPanelCenter.add( pNotesAreSimpleFiles );
			
			Title3 titleSaveNotesToExternalHdd = new Title3( LANG.get_LANG_PROGRAM().get_SAVE_NOTES() );
			subPanelCenter.add( titleSaveNotesToExternalHdd );
			
			Paragraph pSaveNotesToExternalHdd = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_SAVE_NOTES_TO_EXTERNAL_HDD() );
			subPanelCenter.add( pSaveNotesToExternalHdd );
			
			Title3 titleCloseWindow = new Title3( LANG.get_LANG().get_CLOSE_WINDOW() );
			subPanelCenter.add( titleCloseWindow );
			
			Paragraph pCloseWindow = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_CLOSE_WINDOW_WITH_CROSS() );
			subPanelCenter.add( pCloseWindow );
			
			Title3 titleKeyboardShortcutsInTree = new Title3( LANG.get_LANG_PROGRAM().get_KEYBOARD_SHORTCUTS_IN_TREE() );
			subPanelCenter.add( titleKeyboardShortcutsInTree );
			
			Paragraph pKeyboardShortcutsInTreeEnter = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_ENTER() );
			subPanelCenter.add( pKeyboardShortcutsInTreeEnter );
			
			Paragraph pKeyboardShortcutsInTreeShiftUp = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP() );
			subPanelCenter.add( pKeyboardShortcutsInTreeShiftUp );
			
			Paragraph pKeyboardShortcutsInTreeCtrlF = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlF );
			
			Paragraph pKeyboardShortcutsInTreeCtrlN = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlN );
			
			Paragraph pKeyboardShortcutsInTreeCtrlC = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlC );
			
			Paragraph pKeyboardShortcutsInTreeCtrlX = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlX );
			
			Paragraph pKeyboardShortcutsInTreeCtrlV = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlV );
			
			Paragraph pKeyboardShortcutsInTreeCtrlS = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S() );
			subPanelCenter.add( pKeyboardShortcutsInTreeCtrlS );
			
			Paragraph pKeyboardShortcutsInTreeDel = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_TREE_DEL() );
			subPanelCenter.add( pKeyboardShortcutsInTreeDel );
			
			Title3 titleKeyboardShortcutsInNote = new Title3( LANG.get_LANG_PROGRAM().get_KEYBOARD_SHORTCUTS_IN_NOTE() );
			subPanelCenter.add( titleKeyboardShortcutsInNote );
			
			Paragraph pKeyboardShortcutsInNoteCtrlF = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlF );
			
			Paragraph pKeyboardShortcutsInNoteCtrlB = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlB );
			
			Paragraph pKeyboardShortcutsInNoteCtrlI = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlI );
			
			Paragraph pKeyboardShortcutsInNoteCtrlU = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlU );
			
			Paragraph pKeyboardShortcutsInNoteCtrlC = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlC );
			
			Paragraph pKeyboardShortcutsInNoteCtrlS = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlS );
			
			Paragraph pKeyboardShortcutsInNoteCtrlX = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlX );
			
			Paragraph pKeyboardShortcutsInNoteCtrlV = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlV );
			
			Paragraph pKeyboardShortcutsInNoteCtrlZ = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlZ );
			
			Paragraph pKeyboardShortcutsInNoteCtrlY = new Paragraph( LANG.get_LANG_PROGRAM().get_HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y() );
			subPanelCenter.add( pKeyboardShortcutsInNoteCtrlY );
			
		} else if ( ((String)nodeSelected.getUserObject()).equals(LANG.get_LANG().get_ABOUT()) ) {
			
			Title1 title = new Title1( LANG.get_LANG().get_ABOUT() );
			subPanelCenter.add( title );
			
			Title3 titleProgram = new Title3( LANG.get_LANG().get_PROGRAM() );
			subPanelCenter.add( titleProgram );
			
			Paragraph textAreaProgramName = new Paragraph( LANG.get_LANG().get_NAME() + " : " + LANG.get_LANG_PROGRAM().get_PROGRAM_NAME() );
			subPanelCenter.add( textAreaProgramName );
			
			Paragraph textAreaProgramCopyright = new Paragraph( Core.get_PROGRAM_COPYRIGHT() );
			subPanelCenter.add( textAreaProgramCopyright );
			
			Paragraph textAreaProgramVersion = new Paragraph( LANG.get_LANG().get_VERSION() + " : " + Core.get_PROGRAM_VERSION() );
			subPanelCenter.add( textAreaProgramVersion );
			
			Paragraph textAreaProgramLicence = new Paragraph( 
				LANG.get_LANG().get_LICENSE() + " : " + LANG.get_LANG().get_LICENSE_GNU() + " " + 
				LANG.get_LANG_PROGRAM().get_LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE() 
			);
			subPanelCenter.add( textAreaProgramLicence );
			
			Paragraph textAreaLibraries = new Paragraph( LANG.get_LANG().get_LIBRARY() + " : " + LANG.get_LANG().get_RE01JLIB() + " " + LANG.get_LANG_PROGRAM().get_ABOUT_LIBRARY_RE01JLIB() );
			subPanelCenter.add( textAreaLibraries );
			
			Title3 titleAuthor = new Title3( LANG.get_LANG().get_AUTHOR() );
			subPanelCenter.add( titleAuthor );
			
			String contentAuthor = Core.get_AUTHOR_NAME();
			Paragraph textAreaAuthor = new Paragraph( contentAuthor );
			subPanelCenter.add( textAreaAuthor );
			
			Title3 titleWebsite = new Title3( LANG.get_LANG().get_WEB_SITE() );
			subPanelCenter.add( titleWebsite );
			
			String contentWebsite = Core.get_WEBSITE();
			Paragraph textAreaWebsite = new Paragraph( contentWebsite );
			subPanelCenter.add( textAreaWebsite );
			
		} else if ( ((String)nodeSelected.getUserObject()).equals(LANG.get_LANG().get_LICENSE_GNU_SHORT()) ) {
			
			Title1 title = new Title1( LANG.get_LANG().get_LICENSE_GNU() );
			subPanelCenter.add( title );
			
			if ( licenseGnuText == null )
				licenseGnuText = createLicense();
			if ( licenseGnuText != null ) {
				Paragraph pLicenseGnu = new Paragraph( licenseGnuText );
				subPanelCenter.add( pLicenseGnu );
			}
		}
		
		panelCenter.add( subPanelCenter, "North" );

		scrollCenter = new JScrollPane( panelCenter );
		
		panelFileContent.add( scrollCenter );
	}
	
	private String createLicense() {
		String license = new String();
		
		InputStream input_stream = null;
		BufferedReader buffer = null;
		Scanner scan = null;
		try {
			input_stream = this.getClass().getResourceAsStream("/COPYING");
			buffer = new BufferedReader(new InputStreamReader(input_stream));
			scan = new Scanner(buffer);
			license = scan.useDelimiter("\\A").next();
		} catch ( Exception ex ) {
			Core.get_LOGGER().write( ex );
		} finally {
			try {
				input_stream.close();
			} catch (Exception ex) { }
			try {
				buffer.close();
			} catch (Exception ex) { }
			if (scan != null)
				scan.close();
		}
		
		return license;
	}
	
}
