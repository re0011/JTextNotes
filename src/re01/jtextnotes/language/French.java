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

package re01.jtextnotes.language;

import java.awt.event.KeyEvent;

/**
 *
 * @author renaud
 */
public class French extends Global {
	
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

	public French() {
		super();
		
		re01.language.French lang = new re01.language.French();
		
		//==================
		// Common
		
		NOTES_FOLDER = "Dossier de notes";
		WORK_DIRECTORY = lang.get_DIRECTORY() + " de " + lang.get_WORK().toLowerCase();
		KEYBOARD_SHORTCUTS_IN_TREE = "Raccourcis clavier dans l'arborescence des notes";
		KEYBOARD_SHORTCUTS_IN_NOTE = "Raccourcis clavier dans une note en cours d'édition";
		
		//==================
		// Files tree
	
		CAN_NOT_DELETE_ITEM = "Impossible de supprimer cet élément.";
		CAN_NOT_RENAME_ITEM = "Impossible de renommer cet élément.";
		FOLDER_ICON = "Icône du dossier";
		FOLDER_ICON_SHORT = "ID";
		NOTE_ICON = "Icône de la note";
		NOTE_ICON_SHORT = "IN";
		NEW_FOLDER = "Nouveau dossier";
		NEW_FOLDER_ALREADY_EXIST = "Un dossier portant ce nom existe déjà dans le dossier sélectionné.";
		RENAME_FOLDER = "Renommer dossier";
		FOLDER_DO_NOT_EXIST = "Le dossier n'existe pas.";
		NEW_NOTE = "Nouvelle note";
		NEW_NOTE_ALREADY_EXIST = "Une note portant ce nom existe déjà dans le dossier sélectionné.";
		RENAME_NOTE = "Renommer note";
		NOTE_DO_NOT_EXIST = "La note n'existe pas.";
		
		NOTE = "Note";
		NOTE_OPENED = "Note en cours d'édition";
		NOTES = "Notes";
		NOTE_COPIED = "Note copiée";
		NOTE_MOVED = "Note déplacée";
		NOTE_RENAMED = "Note renommée";
		NOTE_SAVED = "Note sauvegardée";
		NOTE_NOT_SAVED = "Note non sauvegardée";
		NOTES_COPIED = "Notes copiées";
		NOTES_MOVED = "Notes déplacées";
		NOTES_RENAMED = "Notes renommées";
		NOTES_SAVED = "Notes sauvegardées";
		NOTES_NOT_SAVED = "Notes non sauvegardées";
		NOTE_DELETED = "Note supprimée";
		NOTE_NOT_DELETED = "Note non supprimée";
		NOTES_DELETED = "Notes supprimées";
		NOTES_NOT_DELETED = "Notes non supprimées";
		
		DELETE_NOTE = "Supprimer note";
		DELETE_NOTES = "Supprimer notes";
		SAVE_NOTE = "Sauvegarder note";
		SAVE_NOTES = "Sauvegarder notes";
		
		//=======================
		// Global text
		
		TEXT_WELCOME = "Bienvenue dans ";
		TEXT_WELCOME_2 = " !";
		TEXT_WELCOME_3 = "Ce programme est gratuit, son code source est libre (open source), il est publié sous licence ";
		TEXT_WELCOME_4 = ". Vous trouverez plus de détails dans le menu ";
		TEXT_WELCOME_5 = "À propos";
		TEXT_WELCOME_6 = ".";
		TEXT_WELCOME_7 = "Amusez-vous bien !";
		
		CHARS_BASE_SIZE_PARAM_INFO = "Quand vous modifiez cette valeur, vous voyez le résultat directement dans cette fenêtre.";
		INPUT_POPUP_CHARS_BASE_SIZE_PARAM_INFO = "Utilisez le curseur ou bien les boutons + et - pour modifier la valeur.";
		
		ICONS_SIZE_PARAM_INFO = "Quand vous modifiez cette valeur, vous voyez le résultat directement dans cette fenêtre. Vous pouvez voir ci-dessous 2 icônes d'exemple : l'un représente un fichier et l'autre indique le succès d'une action.";
		INPUT_POPUP_ICONS_SIZE_PARAM_INFO = "Utilisez le curseur ou bien les boutons + et - pour modifier la valeur.";
		ICON_FILE_POPUP_ICONS_SIZE_PARAM_INFO = "Représente un fichier";
		ICON_OK_POPUP_ICONS_SIZE_PARAM_INFO = "Représente le succès d'une action";
		
		FILES_PARAMS_AVAILABILITY = "Existence des fichiers de paramètres";
		
		INFO_WORK_DIRECTORY_SELECTED = "Vous avez choisi un répertoire.";
		INFO_WORK_DIRECTORY_SELECTION = "Choisissez un ";
		INFO_WORK_DIRECTORY_SELECTION_2 = WORK_DIRECTORY.toLowerCase();
		INFO_WORK_DIRECTORY_SELECTION_3 = " sur votre ordinateur. Cela peut être un répertoire vide pour créer un nouveau ";
		INFO_WORK_DIRECTORY_SELECTION_4 = NOTES_FOLDER.toLowerCase();
		INFO_WORK_DIRECTORY_SELECTION_5 = " ou bien un répertoire contenant déjà des notes.";
		
		ABOUT_LIBRARY_RE01JLIB = "(publiée sous la licence GNU GPL)";
		
		HELP_WHAT_IS_JTEXTNOTES = "JTextNotes est un programme de prise de notes, semblable à un bloc-note avec des options pour styliser le texte.";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES = "Pour ouvrir plusieurs ";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_2 = "répertoires de travail";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_3 = " simultanément, procédez comme suit : depuis le menu, allez dans \"Fichier\" -> \"Ouvrir un répertoire de travail\".";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_4 = "De cette façon le programme fonctionnera de manière optimale.";
		HELP_OPEN_MULTIPLES_WORK_DIRECTORIES_5 = " Il est déconseillé d'ouvrir plusieurs fois le programme afin d'ouvrir plusieurs répertoires de travail simultanément.";
		HELP_NOTE_OPENED_IN_TREE = "Le nom de la note en cours d'édition est représenté par ";
		HELP_NOTE_OPENED_IN_TREE_2 = "un texte en gras de couleur bleu";
		HELP_NOTE_OPENED_IN_TREE_3 = " dans l'arborescence des notes.";
		HELP_NOTES_ARE_SIMPLE_FILES = "Dans JTextNotes, les notes ne sont rien d'autre que des fichiers texte que vous pouvez lister avec un gestionnaire de fichier et éditer avec n'importe quel éditeur de texte. Attention à ne pas modifier les balises [header][/header] et [body][/body] d'un fichier de note, sinon la note ne sera plus visible dans JTextNotes.";
		HELP_SAVE_NOTES_TO_EXTERNAL_HDD = "Pour sauvegarder vos notes sur un disque dur externe ou sur un autre ordinateur, il suffit de les copier avec votre gestionnaire de fichier favori car dans JTextNotes, les notes ne sont rien d'autre que des fichiers.";
		HELP_CLOSE_WINDOW_WITH_CROSS = "Fermer une fenêtre avec la croix équivaut à cliquer sur le bouton \"Annuler\", \"Non\" ou \"Fermer\" de la fenêtre. En ce qui concerne la fenêtre d'un répertoire de travail, cliquer sur la croix équivaut à fermer le répertoire de travail depuis le menu : \"Fichier\" -> \"Fermer le répertoire de travail\".";
		HELP_CLOSE_WORK_DIRECTORY = "Quand vous fermez le dernier répertoire de travail ouvert, JTextNotes se ferme également. Pour fermer tous les répertoires de travail ouverts en même temps, il faut cliquer dans le menu : \"Fichier\" -> \"Quitter\".";
		HELP_KEYBOARD_SHORTCUT_TREE_ENTER = "ENTRÉE = ouvrir la note sélectionnée.";
		HELP_KEYBOARD_SHORTCUT_TREE_SHIFT_UP = "Maintenir SHIFT appuyé + flèche du haut (ou flèche du bas) = sélectionner plusieurs dossiers/notes.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_F = "CTRL + F = créer un dossier dans le dossier sélectionné.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_N = "CTRL + N = créer une note dans le dossier sélectionné.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_C = "CTRL + C = copier les dossiers/notes sélectionnés.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_X = "CTRL + X = couper les dossiers/notes sélectionnés.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_V = "CTRL + V = coller les dossiers/notes présentes dans le presse papier dans le dossier sélectionné.";
		HELP_KEYBOARD_SHORTCUT_TREE_CTRL_S = "CTRL + S = sauvegarder les notes sélectionnées.";
		HELP_KEYBOARD_SHORTCUT_TREE_DEL = "SUPPR = supprimer les dossiers/notes sélectionnés.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_F = "CTRL + F = rechercher un terme dans la note en cours d'édition.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_B = "CTRL + B = mettre en gras le texte sélectionné.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_I = "CTRL + I = mettre en italique le texte sélectionné.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_U = "CTRL + U = souligner le texte sélectionné.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_C = "CTRL + C = copier le texte sélectionné.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_S = "CTRL + S = copier le texte sélectionné avec le style.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_X = "CTRL + X = couper le texte sélectionné.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_V = "CTRL + V = coller le texte présent dans le presse papier.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Z = "CTRL + Z = revenir à un état antérieur de la note.";
		HELP_KEYBOARD_SHORTCUT_NOTE_CTRL_Y = "CTRL + Y (ou CTRL + SHIFT + Z) = avancer à un état postérieur de la note.";
		
		LICENSE_ABOUT_SEE_DETAILS_ON_LEFT_SIDE = "(pour voir le détail, depuis le panneau de gauche, allez dans \"Licence\" -> \"GNU\")";
		
		ROOT_FOLDER_NOTES_FOUND = "Des notes déjà existantes ont été trouvées.";
		ROOT_FOLDER_NOTES_NOT_FOUND = "Aucune note déjà existante n'a été trouvée.";
		ROOT_FOLDER_NOTES_SELECTED = WORK_DIRECTORY.toLowerCase() + " sélectionné";
		
		NEED_SELECT_A_FOLDER_TO_ACTION = "Veuillez sélectionner un dossier pour réaliser cette action.";
		NEED_SELECT_ONLY_ONE_ITEM_TO_ACTION = "Veuillez ne sélectionner qu'un seul élément pour réaliser cette action.";
		
		CONFIRM_SAVE_FOLDER_NOTES = "Toutes les notes des dossiers sélectionnés vont être sauvegardées ainsi que celles de leurs sous-dossiers. Sauvegarder les notes sélectionnées ?";
		CONFIRM_SAVE_NOTE = "Sauvegarder la note sélectionnée ?";
		CONFIRM_SAVE_NOTES = "Sauvegarder les notes sélectionnées ?";
		CONFIRM_DELETE_FOLDER_NOTES = "Toutes les notes des dossiers sélectionnés vont être supprimées ainsi que celles de leurs sous-dossiers. Supprimer les notes sélectionnées ?";
		CONFIRM_DELETE_NOTE = "Supprimer la note sélectionnée ?";
		CONFIRM_DELETE_NOTES = "Supprimer les notes sélectionnées ?";
		CONFIRM_CLOSE_WORK_DIRECTORY = "Fermer ce répertoire de travail ? Certaines notes ne sont pas sauvegardées. Fermer malgré tout ?";
		CONFIRM_EXIT_PROGRAM = "Quitter le programme maintenant ? Tous les répertoires de travail ouverts vont être fermés. Au moins un répertoire de travail ouvert contient des notes qui ne sont pas sauvegardées. Quitter malgré tout ? ";
		
		PROMPT_NEW_FOLDER = "Entrez un nom pour le nouveau dossier.";
		PROMPT_RENAME_FOLDER = "Entrez un nouveau nom pour le dossier.";
		PROMPT_NEW_NOTE = "Entrez un nom pour la nouvelle note.";
		PROMPT_RENAME_NOTE = "Entrez un nouveau nom pour la note.";
		PROMPT_SEARCH_IN_NOTES = "Entrez un mot-clé pour la recherche.";
		
		RESULTS_IN_FOLDERS_NAMES = "Résultats dans les noms de dossiers";
		RESULTS_IN_NOTES_NAMES = "Résultats dans les noms des notes";
		RESULTS_IN_NOTES_CONTENTS = "Résultats dans les contenus des notes";
		
		SEARCH_IN_NOTE = "Rechercher dans la note";
		SEARCH_IN_NOTES = "Rechercher dans toutes les notes";
		
		EVERY_CHARS_NOT_ALLOWED = "Certains caractères entrés n'étaient pas autorisés.";
		
		WELCOME_VIEW_DO_NOT_DISPLAY_AGAIN = "Ne plus afficher la fenêtre de bienvenue quand le programme démarre";
		
		WORK_DIRECTORY_REQUIRED = "Vous devez renseigner un " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_LAST_OPENED = "Dernier " + WORK_DIRECTORY.toLowerCase() + " ouvert";
		WORK_DIRECTORY_IS_LAST_AUTO_OPEN = "Ouvrir le dernier répertoire de travail connu quand le programme démarre";
		WORK_DIRECTORY_OPEN_NEW = "Ouvrir un " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_CLOSE = "Fermer le " + WORK_DIRECTORY.toLowerCase();
		WORK_DIRECTORY_PART_OPENED = "Un répertoire de travail déjà ouvert contient tout ou partie du répertoire de travail que vous essayez d'ouvrir.";
		
		//=====================
		// Languages
		
		SELECT_LANGUAGE_ABOUT = "Vous pourrez la changer ultérieurement dans les paramètres.";
		
		//===================
		// Program
		
		PROGRAM_NAME = "JTextNotes";
		
		//=====================
		// KeyEvents

		SEARCH_IN_NOTE_KEYEVENT = KeyEvent.VK_L;
		SEARCH_IN_NOTES_KEYEVENT = KeyEvent.VK_T;
		
		WORK_DIRECTORY_OPEN_NEW_KEYEVENT = KeyEvent.VK_O;
		WORK_DIRECTORY_CLOSE_KEYEVENT = KeyEvent.VK_F;
		
	}
}
