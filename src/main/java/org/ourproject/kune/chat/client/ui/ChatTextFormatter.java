/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.ui;

import java.util.HashMap;

import org.ourproject.kune.chat.client.rooms.ui.RoomImages;

import com.google.gwt.user.client.ui.HTML;

public class ChatTextFormatter {

    private static final String JOYFUL = "KuneProtectRepBeginJOYFULKuneProtectRepEnd";
    private static final String ANGRY = "KuneProtectRepBeginANGRYKuneProtectRepEnd";
    private static final String BLUSHING = "KuneProtectRepBeginBLUSHINGKuneProtectRepEnd";
    private static final String CRYING = "KuneProtectRepBeginCRYINGKuneProtectRepEnd";
    private static final String POUTY = "KuneProtectRepBeginPOUTYKuneProtectRepEnd";
    private static final String SURPRISED = "KuneProtectRepBeginSURPRISEDKuneProtectRepEnd";
    private static final String GRIN = "KuneProtectRepBeginGRINKuneProtectRepEnd";
    private static final String ANGEL = "KuneProtectRepBeginANGELKuneProtectRepEnd";
    private static final String KISSING = "KuneProtectRepBeginKISSINGKuneProtectRepEnd";
    private static final String SMILE = "KuneProtectRepBeginSMILEKuneProtectRepEnd";
    private static final String TONGUE = "KuneProtectRepBeginTONGUEKuneProtectRepEnd";
    private static final String UNCERTAIN = "KuneProtectRepBeginUNCERTAINKuneProtectRepEnd";
    private static final String COOL = "KuneProtectRepBeginCOOLKuneProtectRepEnd";
    private static final String WINK = "KuneProtectRepBeginWINKKuneProtectRepEnd";
    private static final String HAPPY = "KuneProtectRepBeginHAPPYKuneProtectRepEnd";
    private static final String ALIEN = "KuneProtectRepBeginALIENKuneProtectRepEnd";
    private static final String ANDY = "KuneProtectRepBeginANDYKuneProtectRepEnd";
    private static final String DEVIL = "KuneProtectRepBeginDEVILKuneProtectRepEnd";
    private static final String LOL = "KuneProtectRepBeginLOLKuneProtectRepEnd";
    private static final String NINJA = "KuneProtectRepBeginNINJAKuneProtectRepEnd";
    private static final String SAD = "KuneProtectRepBeginSADKuneProtectRepEnd";
    private static final String SICK = "KuneProtectRepBeginSICKKuneProtectRepEnd";
    private static final String SIDEWAYS = "KuneProtectRepBeginSIDEWAYSKuneProtectRepEnd";
    private static final String SLEEPING = "KuneProtectRepBeginSLEEPINGKuneProtectRepEnd";
    private static final String UNSURE = "KuneProtectRepBeginUNSUREKuneProtectRepEnd";
    private static final String WONDERING = "KuneProtectRepBeginWONDERINGKuneProtectRepEnd";
    private static final String LOVE = "KuneProtectRepBeginLOVEKuneProtectRepEnd";
    private static final String PINCHED = "KuneProtectRepBeginPINCHEDKuneProtectRepEnd";
    private static final String POLICEMAN = "KuneProtectRepBeginPOLICEMANKuneProtectRepEnd";
    private static final String W00T = "KuneProtectRepBeginWOOTKuneProtectRepEnd";
    private static final String WHISTLING = "KuneProtectRepBeginWHISLINGKuneProtectRepEnd";
    private static final String WIZARD = "KuneProtectRepBeginWIZARDKuneProtectRepEnd";
    private static final String BANDIT = "KuneProtectRepBeginBANDITKuneProtectRepEnd";
    private static final String HEART = "KuneProtectRepBeginHEARTKuneProtectRepEnd";

    private final HashMap replacements;
    private final String[] replacementList;

    public ChatTextFormatter() {
	replacements = new HashMap();
	replacements.put(JOYFUL, new String[] { "^_^", "^-^", "^^", ":))", ":-))" });
	replacements.put(ANGRY, new String[] { "&gt;:o", "&gt;:-o", "&gt;:O", "&gt;:-O", "X(", "X-(", "x(", "x-(",
		":@", "&lt;_&lt;" });
	replacements.put(BLUSHING, new String[] { ":\"&gt;", ":*&gt;", ":-$", ":$" });
	replacements.put(CRYING, new String[] { ":'(", "='(" });
	replacements.put(POUTY, new String[] { ":|", "=|", ":-|" });
	replacements.put(SURPRISED, new String[] { ":-O", ":O", ":-o", ":o", ":-0", "=-O", "=-o", "=o", "=O" });
	replacements.put(GRIN, new String[] { ":-d", ":d", ":-D", ":D", ":d", "=D", "=-D" });
	replacements.put(ANGEL, new String[] { "O:)", "o:)", "o:-)", "O:-)", "0:)", "0:-)" });
	replacements.put(KISSING, new String[] { ":-*", ":*" });
	replacements.put(SMILE, new String[] { ":-)", ":)" });
	replacements.put(TONGUE, new String[] { ":P", "=P", "=p", ":-P", ":p", ":-p", ":b" });
	replacements.put(UNCERTAIN, new String[] { ":-\\", ":-/", ":/", ":\\" });
	replacements.put(COOL, new String[] { "B)", "B-)", "8)" });
	replacements.put(WINK, new String[] { ";)", ";-)", ";&gt;" });
	replacements.put(HAPPY, new String[] { "=)", "=-)" });
	replacements.put(ALIEN, new String[] { "=:)", "(alien)" });
	replacements
		.put(ANDY, new String[] { "o_O", "o_0", "O_O", "o_o", "O_o", "0_o", "o0", "0o", "oO", "Oo", "0_0" });
	replacements.put(DEVIL, new String[] { "&gt;:)" });
	replacements.put(LOL, new String[] { "(LOL)", "lol" });
	replacements.put(NINJA, new String[] { "(:)", "(ph33r)", "(ph34r)" });
	replacements.put(PINCHED, new String[] { "&gt;_&lt;" });
	replacements.put(POLICEMAN, new String[] { "(police)", "(cop)", "{):)" });
	replacements.put(SAD, new String[] { ":(", "=(", "=-(", ":-(" });
	replacements.put(SICK, new String[] { ":&amp;", ":-&amp;" });
	replacements.put(SIDEWAYS, new String[] { "=]" });
	replacements.put(SLEEPING, new String[] { "(-.-)", "|)", "|-)", "I-)", "I-|" });
	replacements.put(UNSURE, new String[] { ":s", ":-S", ":-s", ":S" });
	replacements.put(W00T, new String[] { "(woot)", "(w00t)", "(wOOt)" });
	replacements.put(WHISTLING, new String[] { ":-\"" });
	replacements.put(WIZARD, new String[] { "(wizard)" });
	replacements.put(WONDERING, new String[] { ":?" });
	replacements.put(LOVE, new String[] { ":-X", ":-xX", ":x", "(wubya)", "(wubyou)", "(wub)" });
	replacements.put(BANDIT, new String[] { "(bandit)", ":(&gt;" });
	replacements.put(HEART, new String[] { "(L)", "(h)", "(H)" });

	replacementList = new String[] { JOYFUL, ANGRY, BLUSHING, CRYING, POUTY, SURPRISED, GRIN, ANGEL, KISSING,
		SMILE, TONGUE, UNCERTAIN, COOL, WINK, HAPPY, ALIEN, ANDY, DEVIL, LOL, NINJA, SAD, SICK, SIDEWAYS,
		SLEEPING, UNSURE, WONDERING, LOVE, PINCHED, POLICEMAN, W00T, WHISTLING, WIZARD, BANDIT, HEART };
    }

    public static HTML format(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");

	message = formatEmoticons(message);

	return new HTML(message);
    }

    //
    // private String formatEmoticonsNew(final String initMessage) {
    // String message = initMessage;
    //
    //
    // for (int i = 0; i < replacementEnums.length; i++) {
    // String[] replacement = (String[]) replacements.get(replacementEnums[i]);
    // // TODO
    // }
    //
    // return null;
    // }

    private static String formatEmoticons(final String initMessage) {
	String message = initMessage;
	message = message.replaceAll(":\\)", SMILE);
	message = message.replaceAll(":-\\)", SMILE);

	message = message.replaceAll("X-\\(", ANGRY);
	message = message.replaceAll("X\\(", ANGRY);

	message = message.replaceAll(":-D", GRIN);
	message = message.replaceAll(":D", GRIN);

	message = message.replaceAll(":\\(", SAD);
	message = message.replaceAll(":-\\(", SAD);

	message = message.replaceAll(":P", TONGUE);

	message = message.replaceAll(":\'\\(", CRYING);
	message = message.replaceAll(":\'\\(", CRYING);

	message = message.replaceAll(":-O", SURPRISED);
	message = message.replaceAll(":O", SURPRISED);

	message = message.replaceAll(":-\\*", KISSING);
	message = message.replaceAll(":\\*", KISSING);

	message = message.replaceAll(":-/", UNCERTAIN);
	message = message.replaceAll(":-/", UNCERTAIN);

	message = message.replaceAll(";\\)", WINK);
	message = message.replaceAll(";-\\)", WINK);

	message = message.replaceAll(":\\?", WONDERING);
	message = message.replaceAll(":\\?", WONDERING);

	message = message.replaceAll(":-X", LOVE);
	message = message.replaceAll(":-xX", LOVE);

	message = message.replaceAll(SMILE, RoomImages.App.getInstance().smile().getHTML());
	message = message.replaceAll(ANGRY, RoomImages.App.getInstance().angry().getHTML());
	message = message.replaceAll(GRIN, RoomImages.App.getInstance().grin().getHTML());
	message = message.replaceAll(SAD, RoomImages.App.getInstance().sad().getHTML());
	message = message.replaceAll(TONGUE, RoomImages.App.getInstance().tongue().getHTML());
	message = message.replaceAll(CRYING, RoomImages.App.getInstance().crying().getHTML());
	message = message.replaceAll(SURPRISED, RoomImages.App.getInstance().surprised().getHTML());
	message = message.replaceAll(KISSING, RoomImages.App.getInstance().kissing().getHTML());
	message = message.replaceAll(UNCERTAIN, RoomImages.App.getInstance().uncertain().getHTML());
	message = message.replaceAll(WINK, RoomImages.App.getInstance().wink().getHTML());
	message = message.replaceAll(WONDERING, RoomImages.App.getInstance().wondering().getHTML());
	message = message.replaceAll(LOVE, RoomImages.App.getInstance().love().getHTML());

	return message;
    }
}
