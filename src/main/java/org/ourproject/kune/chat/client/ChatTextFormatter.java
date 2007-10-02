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

package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.rooms.ui.RoomImages;

import com.google.gwt.user.client.ui.HTML;

public class ChatTextFormatter {

    private static final String JOYFUL = "KuneProtIniJOYFULKuneProtEnd";
    private static final String ANGRY = "KuneProtIniANGRYKuneProtEnd";
    private static final String BLUSHING = "KuneProtIniBLUSHINGKuneProtEnd";
    private static final String CRYING = "KuneProtIniCRYINGKuneProtEnd";
    private static final String POUTY = "KuneProtIniPOUTYKuneProtEnd";
    private static final String SURPRISED = "KuneProtIniSURPRISEDKuneProtEnd";
    private static final String GRIN = "KuneProtIniGRINKuneProtEnd";
    private static final String ANGEL = "KuneProtIniANGELKuneProtEnd";
    private static final String KISSING = "KuneProtIniKISSINGKuneProtEnd";
    private static final String SMILE = "KuneProtIniSMILEKuneProtEnd";
    private static final String TONGUE = "KuneProtIniTONGUEKuneProtEnd";
    private static final String UNCERTAIN = "KuneProtIniUNCERTAINKuneProtEnd";
    private static final String COOL = "KuneProtIniCOOLKuneProtEnd";
    private static final String WINK = "KuneProtIniWINKKuneProtEnd";
    private static final String HAPPY = "KuneProtIniHAPPYKuneProtEnd";
    private static final String ALIEN = "KuneProtIniALIENKuneProtEnd";
    private static final String ANDY = "KuneProtIniANDYKuneProtEnd";
    private static final String DEVIL = "KuneProtIniDEVILKuneProtEnd";
    private static final String LOL = "KuneProtIniLOLKuneProtEnd";
    private static final String NINJA = "KuneProtIniNINJAKuneProtEnd";
    private static final String SAD = "KuneProtIniSADKuneProtEnd";
    private static final String SICK = "KuneProtIniSICKKuneProtEnd";
    private static final String SIDEWAYS = "KuneProtIniSIDEWAYSKuneProtEnd";
    private static final String SLEEPING = "KuneProtIniSLEEPINGKuneProtEnd";
    private static final String UNSURE = "KuneProtIniUNSUREKuneProtEnd";
    private static final String WONDERING = "KuneProtIniWONDERINGKuneProtEnd";
    private static final String LOVE = "KuneProtIniLOVEKuneProtEnd";
    private static final String PINCHED = "KuneProtIniPINCHEDKuneProtEnd";
    private static final String POLICEMAN = "KuneProtIniPOLICEMANKuneProtEnd";
    private static final String W00T = "KuneProtIniWOOTKuneProtEnd";
    private static final String WHISTLING = "KuneProtIniWHISLINGKuneProtEnd";
    private static final String WIZARD = "KuneProtIniWIZARDKuneProtEnd";
    private static final String BANDIT = "KuneProtIniBANDITKuneProtEnd";
    private static final String HEART = "KuneProtIniHEARTKuneProtectRepEnd";

    public ChatTextFormatter() {
    }

    public static HTML format(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll("&gt;", "&gt;");
	message = message.replaceAll("\n", "<br>\n");

	formatEmoticons(message);

	return new HTML(message);
    }

    private static void formatEmoticonsNew(String message) {
	RoomImages img = RoomImages.App.getInstance();

	replace(message, new String[] { "=:\\)", "\\(alien\\)" }, ALIEN);
	replace(message, new String[] { "o_O", "o_0", "O_O", "o_o", "O_o", "0_o", "o0", "0o", "oO", "Oo", "0_0" }, ANDY);
	replace(message, new String[] { "O:\\)", "o:\\)", "o:-\\)", "O:-\\)", "0:\\)", "0:-\\)" }, ANGEL);
	replace(message, new String[] { "&gt;:o", "&gt;:-o", "&gt;:O", "&gt;:-O", "X\\(", "X-\\(", "x\\(", "x-\\(",
		":@", "&lt;_&lt;" }, ANGRY);
	replace(message, new String[] { "\\(bandit\\)", ":\\(&gt;" }, BANDIT);
	replace(message, new String[] { ":&quot;&gt;", ":\\*&gt;", ":-$", ":$" }, BLUSHING);
	replace(message, new String[] { "B\\)", "B-\\)", "8\\)" }, COOL);
	replace(message, new String[] { ":\'\\(", "=\'\\(" }, CRYING);
	replace(message, new String[] { "&gt;:\\)" }, DEVIL);
	replace(message, new String[] { ":-d", ":d", ":-D", ":D", ":d", "=D", "=-D" }, GRIN);
	replace(message, new String[] { "=\\)", "=-\\)" }, HAPPY);
	replace(message, new String[] { "\\(L\\)", "\\(h\\)", "\\(H\\)" }, HEART);
	replace(message, new String[] { "^_^", "^-^", "^^", ":\\)\\)", ":-\\)\\)" }, JOYFUL);
	replace(message, new String[] { ":-\\*", ":\\*" }, KISSING);
	replace(message, new String[] { "\\(LOL\\)", "lol" }, LOL);
	replace(message, new String[] { ":-X", ":-xX", ":x", "\\(wubya\\)", "\\(wubyou\\)", "\\(wub\\)" }, LOVE);
	replace(message, new String[] { "\\(:\\)", "\\(ph33r\\)", "\\(ph34r\\)" }, NINJA);
	replace(message, new String[] { "&gt;_&lt;" }, PINCHED);
	replace(message, new String[] { "\\(police\\)", "\\(cop\\)", "{\\):\\)" }, POLICEMAN);
	replace(message, new String[] { ":|", "=|", ":-|" }, POUTY);
	replace(message, new String[] { ":\\(", "=\\(", "=-\\(", ":-\\(" }, SAD);
	replace(message, new String[] { ":&amp;", ":-&amp;" }, SICK);
	replace(message, new String[] { "=]" }, SIDEWAYS);
	replace(message, new String[] { "\\(-.-\\)", "|\\)", "|-\\)", "I-\\)", "I-|" }, SLEEPING);
	replace(message, new String[] { ":-\\)", ":\\)" }, SMILE);
	replace(message, new String[] { ":-O", ":O", ":-o", ":o", ":-0", "=-O", "=-o", "=o", "=O" }, SURPRISED);
	replace(message, new String[] { ":P", "=P", "=p", ":-P", ":p", ":-p", ":b" }, TONGUE);
	replace(message, new String[] { ":-\\", ":-/", ":/", ":\\" }, UNCERTAIN);
	replace(message, new String[] { ":s", ":-S", ":-s", ":S" }, UNSURE);
	replace(message, new String[] { "\\(woot\\)", "\\(w00t\\)", "\\(wOOt\\)" }, W00T);
	replace(message, new String[] { ":-&quot;" }, WHISTLING);
	replace(message, new String[] { ";\\)", ";-\\)", ";&gt;" }, WINK);
	replace(message, new String[] { "\\(wizard\\)" }, WIZARD);
	replace(message, new String[] { ":\\?" }, WONDERING);

	message = message.replaceAll(SMILE, img.smile().getHTML());
	message = message.replaceAll(CRYING, img.crying().getHTML());
	message = message.replaceAll(SURPRISED, img.surprised().getHTML());
	message = message.replaceAll(ANGEL, img.angel().getHTML());
	message = message.replaceAll(HAPPY, img.happy().getHTML());
	message = message.replaceAll(GRIN, img.grin().getHTML());
	message = message.replaceAll(JOYFUL, img.joyful().getHTML());
	message = message.replaceAll(UNCERTAIN, img.uncertain().getHTML());
	message = message.replaceAll(ANGRY, img.angry().getHTML());
	message = message.replaceAll(TONGUE, img.tongue().getHTML());
	message = message.replaceAll(LOVE, img.love().getHTML());
	message = message.replaceAll(SLEEPING, img.sleeping().getHTML());
	message = message.replaceAll(COOL, img.cool().getHTML());
	message = message.replaceAll(KISSING, img.kissing().getHTML());
	message = message.replaceAll(SAD, img.sad().getHTML());
	message = message.replaceAll(ALIEN, img.alien().getHTML());
	message = message.replaceAll(ANDY, img.andy().getHTML());
	message = message.replaceAll(BANDIT, img.bandit().getHTML());
	message = message.replaceAll(BLUSHING, img.blushing().getHTML());
	message = message.replaceAll(DEVIL, img.devil().getHTML());
	message = message.replaceAll(HEART, img.heart().getHTML());
	message = message.replaceAll(LOL, img.lol().getHTML());
	message = message.replaceAll(NINJA, img.ninja().getHTML());
	message = message.replaceAll(PINCHED, img.pinched().getHTML());
	message = message.replaceAll(POLICEMAN, img.policeman().getHTML());
	message = message.replaceAll(POUTY, img.pouty().getHTML());
	message = message.replaceAll(SICK, img.sick().getHTML());
	message = message.replaceAll(SIDEWAYS, img.sideways().getHTML());
	message = message.replaceAll(UNSURE, img.unsure().getHTML());
	message = message.replaceAll(W00T, img.w00t().getHTML());
	message = message.replaceAll(WINK, img.wink().getHTML());
	message = message.replaceAll(WONDERING, img.wondering().getHTML());
	message = message.replaceAll(WHISTLING, img.whistling().getHTML());
	message = message.replaceAll(WIZARD, img.wizard().getHTML());
    }

    private static void replace(String message, final String[] from, final String to) {
	for (int j = 0; j < from.length; j++) {
	    message = message.replaceAll(from[j], to);
	}
    }

    private static void formatEmoticons(String message) {
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
    }
}
