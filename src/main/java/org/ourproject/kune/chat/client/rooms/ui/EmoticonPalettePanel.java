package org.ourproject.kune.chat.client.rooms.ui;

import org.ourproject.kune.chat.client.rooms.EmoticonPaletteListener;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EmoticonPalettePanel extends Composite {
    private static final int PALETTE_ROWS = 5;
    private static final int PALETTE_COLUMNS = 7;
    private final Grid grid;
    private int currentWidget;

    public EmoticonPalettePanel(final EmoticonPaletteListener listener) {
        grid = new Grid(PALETTE_ROWS, PALETTE_COLUMNS);
        SimplePanel sp = new SimplePanel();
        sp.add(grid);
        sp.setStyleName("kune-Margin-Medium-trbl");
        currentWidget = 0;
        initWidget(sp);
        grid.setCellSpacing(1);
        grid.addStyleName("kune-MultiRoomPanel-EmoticonPalette");
        RoomImages img = RoomImages.App.getInstance();
        addNextIcon(createEmoticon(img.smile(), ":-)", listener));
        addNextIcon(createEmoticon(img.crying(), ":'(", listener));
        addNextIcon(createEmoticon(img.surprised(), ":-O", listener));
        addNextIcon(createEmoticon(img.angel(), "0:)", listener));
        addNextIcon(createEmoticon(img.happy(), "=)", listener));
        addNextIcon(createEmoticon(img.grin(), ":D", listener));
        addNextIcon(createEmoticon(img.joyful(), ":-))", listener));
        addNextIcon(createEmoticon(img.uncertain(), ":-/", listener));
        addNextIcon(createEmoticon(img.angry(), "x-(", listener));
        addNextIcon(createEmoticon(img.tongue(), ":P", listener));
        addNextIcon(createEmoticon(img.love(), ":-X", listener));
        addNextIcon(createEmoticon(img.sleeping(), "|-)", listener));
        addNextIcon(createEmoticon(img.cool(), "8)", listener));
        addNextIcon(createEmoticon(img.kissing(), ":*", listener));
        addNextIcon(createEmoticon(img.sad(), ":(", listener));
        addNextIcon(createEmoticon(img.alien(), "=:)", listener));
        addNextIcon(createEmoticon(img.andy(), "0o", listener));
        addNextIcon(createEmoticon(img.bandit(), "(bandit)", listener));
        addNextIcon(createEmoticon(img.blushing(), ":-$", listener));
        addNextIcon(createEmoticon(img.devil(), ">:)", listener));
        addNextIcon(createEmoticon(img.whistling(), ":-\"", listener));
        addNextIcon(createEmoticon(img.heart(), "(H)", listener));
        addNextIcon(createEmoticon(img.lol(), "(LOL)", listener));
        addNextIcon(createEmoticon(img.ninja(), "(:)", listener));
        addNextIcon(createEmoticon(img.pinched(), ">_<", listener));
        addNextIcon(createEmoticon(img.policeman(), "(police)", listener));
        addNextIcon(createEmoticon(img.pouty(), ":|", listener));
        addNextIcon(createEmoticon(img.wizard(), "(wizard)", listener));
        addNextIcon(createEmoticon(img.sick(), ":-&", listener));
        addNextIcon(createEmoticon(img.sideways(), "=]", listener));
        addNextIcon(createEmoticon(img.unsure(), ":-S", listener));
        addNextIcon(createEmoticon(img.w00t(), "(woot)", listener));
        addNextIcon(createEmoticon(img.wink(), ";-)", listener));
        addNextIcon(createEmoticon(img.wondering(), ":?", listener));
        addNextIcon(createEmoticon(
                img.bulletStar(),
                "ONLY FOR DEVELOPMENT AND TESTS: >:) O:) o:) o:-) O:-) 0:) 0:-) ^_^ ^-^ ^^ :)) :-)) (police) (cop) {):) =:) (alien) o_O o_0 O_O o_o O_o 0_o o0 0o oO Oo 0_0 >:o >:-o >:O >:-O X( X-( x( x-( :@ <_< (bandit) :(> :\"> :*> :-$ :$ B) B-) 8) :'( ='( :-d :d :-D :D :d =D =-D =) =-) (L) (h) (H) :-* :* (LOL) lol :-X :-xX :x (wubya) (wubyou) (wub) (:) (ph33r) (ph34r) >_< :| =| :-| :( =( =-( :-( :& :-& =] (-.-) |) |-) I-) I-| :-O :O :-o :o :-0 =-O =-o =o =O :P =P =p :-P :p :-p :b :-\\ :-/ :/ :\\ :s :-S :-s :S (woot) (w00t) (wOOt) :-\" ;) ;-) ;> (wizard) :? :-) :)",
                listener));
    }

    private void addNextIcon(final Image img) {
        int row = currentWidget / PALETTE_COLUMNS;
        int col = currentWidget % PALETTE_COLUMNS;
        grid.setWidget(row, col, img);
        currentWidget++;
    }

    private Image createEmoticon(final AbstractImagePrototype imageProto, final String emoticonText,
            final EmoticonPaletteListener listener) {
        Image img = new Image();
        imageProto.applyTo(img);
        img.addClickListener(new ClickListener() {

            public void onClick(final Widget arg0) {
                listener.onEmoticonSelected(emoticonText);
            }
        });
        img.setTitle(emoticonText);
        return img;
    }
}
