package org.ourproject.kune.chat.client.rooms.ui;

import org.ourproject.kune.platf.client.ui.KuneStringUtils;

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

    public static HTML format(final String messageOrig) {
        String message = messageOrig;
        message = KuneStringUtils.escapeHtmlLight(message);
        message = message.replaceAll("\n", "<br>\n");
        message = formatEmoticons(message);

        return new HTML(message);
    }

    private static String formatEmoticons(String message) {
        RoomImages img = RoomImages.App.getInstance();

        message = replace(message, new String[] { "&gt;:\\)" }, DEVIL);
        message = replace(message, new String[] { "O:\\)", "o:\\)", "o:-\\)", "O:-\\)", "0:\\)", "0:-\\)" }, ANGEL);
        message = replace(message, new String[] { "\\^_\\^", "\\^-\\^", "\\^\\^", ":\\)\\)", ":-\\)\\)" }, JOYFUL);
        message = replace(message, new String[] { "\\(police\\)", "\\(cop\\)", "\\{\\):\\)" }, POLICEMAN);
        message = replace(message, new String[] { "=:\\)", "\\(alien\\)" }, ALIEN);
        message = replace(message, new String[] { "o_O", "o_0", "O_O", "o_o", "O_o", "0_o", "o0", "0o", "oO", "Oo",
                "0_0" }, ANDY);
        message = replace(message, new String[] { "&gt;:o", "&gt;:-o", "&gt;:O", "&gt;:-O", "X\\(", "X-\\(", "x\\(",
                "x-\\(", ":@", "&lt;_&lt;" }, ANGRY);
        message = replace(message, new String[] { "\\(bandit\\)", ":\\(&gt;" }, BANDIT);
        message = replace(message, new String[] { ":&quot;&gt;", ":\\*&gt;", ":-\\$", ":\\$" }, BLUSHING);
        message = replace(message, new String[] { "B\\)", "B-\\)", "8\\)" }, COOL);
        message = replace(message, new String[] { ":\'\\(", "=\'\\(" }, CRYING);
        message = replace(message, new String[] { ":-d", ":d", ":-D", ":D", ":d", "=D", "=-D" }, GRIN);
        message = replace(message, new String[] { "=\\)", "=-\\)" }, HAPPY);
        message = replace(message, new String[] { "\\(L\\)", "\\(h\\)", "\\(H\\)" }, HEART);
        message = replace(message, new String[] { ":-\\*", ":\\*" }, KISSING);
        message = replace(message, new String[] { "\\(LOL\\)", "lol" }, LOL);
        message = replace(message, new String[] { ":-X", ":-xX", ":x", "\\(wubya\\)", "\\(wubyou\\)", "\\(wub\\)" },
                LOVE);
        message = replace(message, new String[] { "\\(:\\)", "\\(ph33r\\)", "\\(ph34r\\)" }, NINJA);
        message = replace(message, new String[] { "&gt;_&lt;" }, PINCHED);
        message = replace(message, new String[] { ":\\|", "=\\|", ":-\\|" }, POUTY);
        message = replace(message, new String[] { ":\\(", "=\\(", "=-\\(", ":-\\(" }, SAD);
        message = replace(message, new String[] { ":&amp;", ":-&amp;" }, SICK);
        message = replace(message, new String[] { "=]" }, SIDEWAYS);
        message = replace(message, new String[] { "\\(-.-\\)", "\\|\\)", "\\|-\\)", "I-\\)", "I-\\|" }, SLEEPING);
        message = replace(message, new String[] { ":-O", ":O", ":-o", ":o", ":-0", "=-O", "=-o", "=o", "=O" },
                SURPRISED);
        message = replace(message, new String[] { ":P", "=P", "=p", ":-P", ":p", ":-p", ":b" }, TONGUE);
        message = replace(message, new String[] { ":-\\\\", ":-/", ":/", ":\\\\" }, UNCERTAIN);
        message = replace(message, new String[] { ":s", ":-S", ":-s", ":S" }, UNSURE);
        message = replace(message, new String[] { "\\(woot\\)", "\\(w00t\\)", "\\(wOOt\\)" }, W00T);
        message = replace(message, new String[] { ":-&quot;" }, WHISTLING);
        message = replace(message, new String[] { ";\\)", ";-\\)", ";&gt;" }, WINK);
        message = replace(message, new String[] { "\\(wizard\\)" }, WIZARD);
        message = replace(message, new String[] { ":\\?" }, WONDERING);
        message = replace(message, new String[] { ":-\\)", ":\\)" }, SMILE);

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

        return message;
    }

    private static String replace(String message, final String[] from, final String to) {
        for (int j = 0; j < from.length; j++) {
            message = message.replaceAll(from[j], to);
        }
        return message;
    }

}
