/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.CustomPushButton;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.form.FileUploadFormSample;
import org.ourproject.kune.platf.client.ui.imgchooser.ImageChooser;
import org.ourproject.kune.platf.client.ui.imgchooser.ImageChooserCallback;
import org.ourproject.kune.platf.client.ui.imgchooser.ImageData;
import org.ourproject.kune.platf.client.ui.palette.ColorSelectListener;
import org.ourproject.kune.platf.client.ui.palette.WebSafePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.WebSafePalettePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBox.PromptCallback;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class TextEditorToolbar extends Composite implements TextEditorToolbarView {

    /**
     * We use an inner EventListener class to avoid exposing event methods on
     * the RichTextToolbar itself.
     */
    private class EventListener implements ClickListener, ChangeListener, KeyboardListener {

        private ImageChooser ic;

        public void onChange(final Widget sender) {
            fireEdit();
        }

        public void onClick(final Widget sender) {
            if (sender == bold) {
                basic.toggleBold();
            } else if (sender == italic) {
                basic.toggleItalic();
            } else if (sender == underline) {
                basic.toggleUnderline();
            } else if (sender == subscript) {
                basic.toggleSubscript();
            } else if (sender == superscript) {
                basic.toggleSuperscript();
            } else if (sender == strikethrough) {
                extended.toggleStrikethrough();
            } else if (sender == indent) {
                extended.rightIndent();
            } else if (sender == outdent) {
                extended.leftIndent();
            } else if (sender == justifyLeft) {
                basic.setJustification(RichTextArea.Justification.LEFT);
            } else if (sender == justifyCenter) {
                basic.setJustification(RichTextArea.Justification.CENTER);
            } else if (sender == justifyRight) {
                basic.setJustification(RichTextArea.Justification.RIGHT);
            } else if (sender == insertImage) {
                showImagePanel();
            } else if (sender == createLink) {
                MessageBox.prompt("Insert a link", "Enter a link URL:", new PromptCallback() {
                    public void execute(final String btnID, final String text) {
                        if (btnID.equals("ok") && text != null) {
                            extended.createLink(text);
                        }
                    }
                });
                showLinkPanel();
            } else if (sender == backColor) {
                showPalette(sender, sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 20);
                palettePresenter.addColorSelectListener(new ColorSelectListener() {
                    public void onColorSelected(final String color) {
                        basic.setBackColor(color);
                        popupPalette.hide();
                        palettePresenter.reset();
                    }
                });
            } else if (sender == fontColor) {
                showPalette(sender, sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 20);
                palettePresenter.addColorSelectListener(new ColorSelectListener() {
                    public void onColorSelected(final String color) {
                        basic.setForeColor(color);
                        popupPalette.hide();
                        palettePresenter.reset();
                    }
                });
            } else if (sender == removeLink) {
                extended.removeLink();
            } else if (sender == hr) {
                extended.insertHorizontalRule();
            } else if (sender == ol) {
                extended.insertOrderedList();
            } else if (sender == ul) {
                extended.insertUnorderedList();
            } else if (sender == removeFormat) {
                extended.removeFormat();
            } else if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the
                // toolbar status.
                // This will catch any cases where the user moves the cursur
                // using the
                // keyboard, or uses one of the browser's built-in keyboard
                // shortcuts.
                updateStatus();
            }
            if (sender != richText) {
                // some button pressed is equiv to edit
                fireEdit();
            }
        }

        private void showLinkPanel() {
        }

        private void showImagePanel() {
            // i18n:
            FileUploadFormSample fileUploadFormSample = new FileUploadFormSample();
            BasicDialog dialog = new BasicDialog("Insert image", false, true, 400, 400);
            dialog.add(fileUploadFormSample);
            dialog.show();

            if (ic == null) {
                MemoryProxy dataProxy = new MemoryProxy(getData());
                RecordDef recordDef = new RecordDef(new FieldDef[] { new StringFieldDef("name"),
                        new IntegerFieldDef("size"), new DateFieldDef("lastmod", "timestamp"),
                        new StringFieldDef("url") });
                ArrayReader reader = new ArrayReader(recordDef);
                final Store store = new Store(dataProxy, reader, true);
                store.load();

                ic = new ImageChooser("Image Chooser", 515, 400, store);
            }

            ic.show(new ImageChooserCallback() {
                public void onImageSelection(final ImageData data) {
                    // Element el = DomHelper.append("images",
                    // Format.format("<img src='{0}'
                    // style='margin:20px;visibility:hidden;'/>",
                    // data.getUrl()));
                    // ExtElement extEl = new ExtElement(el);
                    // extEl.show(true).frame();
                }
            });
            // MessageBox.prompt("Insert image", "Enter an image URL:", new
            // PromptCallback() {
            // public void execute(final String btnID, final String text) {
            // if (btnID.equals("ok") && text != null) {
            // extended.insertImage(text);
            // }
            // }
            // });

        }

        private Object[][] getData() {
            return new Object[][] {
            // new Object[]{"Pirates of the Caribbean", new Integer(2120), new
            // Long(1180231870000l), "images/view/carribean.jpg"},
            // new Object[]{"Resident Evil", new Integer(2120), new
            // Long(1180231870000l), "images/view/resident_evil.jpg"},
            // new Object[]{"Blood Diamond", new Integer(2120), new
            // Long(1180231870000l), "images/view/blood_diamond.jpg"},
            // new Object[]{"No Reservations", new Integer(2120), new
            // Long(1180231870000l), "images/view/no_reservations.jpg"},
            // new Object[]{"Casino Royale", new Integer(2120), new
            // Long(1180231870000l), "images/view/casino_royale.jpg"},
            // new Object[]{"Good Shepherd", new Integer(2120), new
            // Long(1180231870000l), "images/view/good_shepherd.jpg"},
            // new Object[]{"Ghost Rider", new Integer(2120), new
            // Long(1180231870000l), "images/view/ghost_rider.jpg"},
            // new Object[]{"Batman Begins", new Integer(2120), new
            // Long(1180231870000l), "images/view/batman_begins.jpg"},
            // new Object[]{"Last Samurai", new Integer(2120), new
            // Long(1180231870000l), "images/view/last_samurai.jpg"},
            // new Object[]{"Italian Job", new Integer(2120), new
            // Long(1180231870000l), "images/view/italian_job.jpg"},
            // new Object[]{"Mission Impossible III", new Integer(2120), new
            // Long(1180231870000l), "images/view/mi3.jpg"},
            // new Object[]{"Mr & Mrs Smith", new Integer(2120), new
            // Long(1180231870000l), "images/view/smith.jpg"},
            // new Object[]{"Inside Man", new Integer(2120), new
            // Long(1180231870000l), "images/view/inside_man.jpg"},
            // new Object[]{"The Island", new Integer(2120), new
            // Long(1180231870000l), "images/view/island.jpg"}
            };
        }

        public void onKeyDown(final Widget sender, final char keyCode, final int modifiers) {
            if (sender == removeLink && keyCode == KeyboardListener.KEY_ENTER) {
                fireEditHTML();
            }
        }

        public void onKeyPress(final Widget sender, final char keyCode, final int modifiers) {
        }

        public void onKeyUp(final Widget sender, final char keyCode, final int modifiers) {
            if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the
                // toolbar status.
                // This will catch any cases where the user moves the cursur
                // using the
                // keyboard, or uses one of the browser's built-in keyboard
                // shortcuts.
                updateStatus();
                fireEdit();
            }
        }
    }

    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL,
            RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE };

    private final TextEditorImages images = (TextEditorImages) GWT.create(TextEditorImages.class);
    private final EventListener listener = new EventListener();

    private final RichTextArea richText;
    private final RichTextArea.BasicFormatter basic;
    private final RichTextArea.ExtendedFormatter extended;

    private final VerticalPanel outer = new VerticalPanel();
    private final HorizontalPanel topPanel = new HorizontalPanel();
    private final HorizontalPanel subtopPanel = new HorizontalPanel();
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private ToggleButton strikethrough;
    private PushButton indent;
    private PushButton outdent;
    private PushButton justifyLeft;
    private PushButton justifyCenter;
    private PushButton justifyRight;
    private PushButton hr;
    private PushButton ol;
    private PushButton ul;
    private PushButton insertImage;
    private PushButton createLink;
    private PushButton removeLink;
    private PushButton removeFormat;
    private PushButton backColor;
    private PushButton fontColor;
    private MenuBar fonts;
    private MenuBar fontSizes;
    private final CustomPushButton save;
    private final CustomPushButton close;
    private WebSafePalettePanel palettePanel;
    private final TextEditorPresenter panelListener;

    private PopupPanel popupPalette;

    private WebSafePalettePresenter palettePresenter;

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText
     *                the rich text area to be controlled
     */
    public TextEditorToolbar(final RichTextArea richText, final TextEditorPresenter panelListener) {
        this.richText = richText;
        this.basic = richText.getBasicFormatter();
        this.extended = richText.getExtendedFormatter();

        this.panelListener = panelListener;

        initWidget(outer);

        outer.add(topPanel);
        outer.add(subtopPanel);
        outer.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        setStyleName("gwt-RichTextToolbar");

        if (basic != null) {
            topPanel.add(bold = createToggleButton(images.bold(), Kune.I18N.t("Toggle Bold")));
            topPanel.add(italic = createToggleButton(images.italic(), Kune.I18N.t("Toggle Italic")));
            topPanel.add(underline = createToggleButton(images.underline(), Kune.I18N.t("Toggle Underline")));
        }

        if (extended != null) {
            topPanel
                    .add(strikethrough = createToggleButton(images.strikeThrough(), Kune.I18N.t("Toggle Strikethrough")));
        }

        if (basic != null) {
            topPanel.add(subscript = createToggleButton(images.subscript(), Kune.I18N.t("Toggle Subscript")));
            topPanel.add(superscript = createToggleButton(images.superscript(), Kune.I18N.t("Toggle Superscript")));
            topPanel.add(justifyLeft = createPushButton(images.justifyLeft(), Kune.I18N.t("Left Justify")));
            topPanel.add(justifyCenter = createPushButton(images.justifyCenter(), Kune.I18N.t("Center")));
            topPanel.add(justifyRight = createPushButton(images.justifyRight(), Kune.I18N.t("Right Justify")));
        }

        if (extended != null) {
            topPanel.add(indent = createPushButton(images.indent(), Kune.I18N.t("Indent Right")));
            topPanel.add(outdent = createPushButton(images.outdent(), Kune.I18N.t("Indent Left")));
            topPanel.add(hr = createPushButton(images.hr(), Kune.I18N.t("Insert Horizontal Rule")));
            topPanel.add(ol = createPushButton(images.ol(), Kune.I18N.t("Insert Ordered List")));
            topPanel.add(ul = createPushButton(images.ul(), Kune.I18N.t("Insert Unordered List")));
            topPanel.add(insertImage = createPushButton(images.insertImage(), Kune.I18N.t("Insert Image")));
            topPanel.add(createLink = createPushButton(images.createLink(), Kune.I18N.t("Create Link")));
            topPanel.add(removeLink = createPushButton(images.removeLink(), Kune.I18N.t("Remove Link")));
            topPanel.add(removeFormat = createPushButton(images.removeFormat(), Kune.I18N.t("Remove Formatting")));
        }

        if (basic != null) {
            topPanel.add(backColor = createPushButton(images.backcolor(), Kune.I18N.t("Background Color")));
            topPanel.add(fontColor = createPushButton(images.fontcolor(), Kune.I18N.t("Font Color")));
            topPanel.add(fonts = createFontsMenu());
            topPanel.add(fontSizes = createFontSizesMenu());

            // We only use these listeners for updating status, so don't
            // hook them up
            // unless at least basic editing is supported.
            richText.addKeyboardListener(listener);
            richText.addClickListener(listener);
        }

        save = new CustomPushButton(Kune.I18N.tWithNT("Save", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                if (save.isEnabled()) {
                    fireSave();
                }
            }
        });

        close = new CustomPushButton(Kune.I18N.tWithNT("Close", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                if (close.isEnabled()) {
                    fireCancel();
                }
            }
        });

        close.addStyleName("kune-Button-Large-lrSpace");

        // if (basic != null) {
        // topPanel.add(editHtml = createToggleButton(images.editHtml(),
        // Kune.I18N.t("Edit HTML")));
        // }

        subtopPanel.add(save);
        subtopPanel.add(close);
    }

    private void fireEdit() {
        panelListener.onEdit();
    }

    private void fireSave() {
        panelListener.onSave();
    }

    private void fireCancel() {
        panelListener.onCancel();
    }

    private void fireEditHTML() {
        panelListener.onEditHTML();
    }

    public void editHTML(final boolean edit) {
        boolean enable = !edit;
        if (basic != null) {
            bold.setEnabled(enable);
            italic.setEnabled(enable);
            underline.setEnabled(enable);
            subscript.setEnabled(enable);
            superscript.setEnabled(enable);
            justifyLeft.setEnabled(enable);
            justifyCenter.setEnabled(enable);
            justifyRight.setEnabled(enable);
            backColor.setEnabled(enable);
            fontColor.setEnabled(enable);
        }
        if (extended != null) {
            strikethrough.setEnabled(enable);
            indent.setEnabled(enable);
            outdent.setEnabled(enable);
            insertImage.setEnabled(enable);
            createLink.setEnabled(enable);
            removeLink.setEnabled(enable);
            ol.setEnabled(enable);
            ul.setEnabled(enable);
            hr.setEnabled(enable);
            removeFormat.setEnabled(enable);
            fonts.setVisible(enable);
            fontSizes.setVisible(enable);
        }
    }

    private MenuBar createFontsMenu() {
        MenuBar menu = new MenuBar();
        MenuBar submenu = new MenuBar(true);
        String fontName[] = { "Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet", "Verdana" };

        KuneUiUtils.setQuickTip(menu, Kune.I18N.t("Font Type"));
        menu.addItem(images.charfontname().getHTML(), true, submenu);
        for (int i = 0; i < fontName.length; i++) {
            final String f = fontName[i];
            submenu.addItem("<span style=\"font-family: " + f + "\">" + f + "</span>", true, new Command() {
                public void execute() {
                    basic.setFontName(f);
                    fireEdit();
                }
            });
        }
        menu.setStyleName("RichTextToolbar-menu");
        submenu.setStyleName("RichTextToolbar-submenu");
        return menu;
    }

    private MenuBar createFontSizesMenu() {
        MenuBar menu = new MenuBar();
        MenuBar submenu = new MenuBar(true);
        String fontSizes[] = { Kune.I18N.t("Extra small"), Kune.I18N.t("Very small"), Kune.I18N.t("small"),
                Kune.I18N.t("Medium"), Kune.I18N.t("Large"), Kune.I18N.t("Very large"), Kune.I18N.t("Extra large") };

        KuneUiUtils.setQuickTip(menu, Kune.I18N.t("Font Size"));
        menu.addItem(images.fontheight().getHTML(), true, submenu);
        for (int i = 0; i < fontSizes.length; i++) {
            final String f = fontSizes[i];
            final int fontSize = i;
            submenu.addItem("<font size=\"" + (i + 1) + "\">" + f + "</font>", true, new Command() {
                public void execute() {
                    basic.setFontSize(fontSizesConstants[fontSize]);
                    fireEdit();
                }
            });
        }
        menu.setStyleName("RichTextToolbar-menu");
        submenu.setStyleName("RichTextToolbar-submenu");
        return menu;
    }

    private PushButton createPushButton(final AbstractImagePrototype img, final String tip) {
        PushButton pb = new PushButton(img.createImage());
        pb.addClickListener(listener);
        KuneUiUtils.setQuickTip(pb, tip);
        return pb;
    }

    private ToggleButton createToggleButton(final AbstractImagePrototype img, final String tip) {
        ToggleButton tb = new ToggleButton(img.createImage());
        tb.addClickListener(listener);
        KuneUiUtils.setQuickTip(tb, tip);
        return tb;
    }

    public void showPalette(final Widget sender, final int left, final int top) {
        if (palettePanel == null) {
            palettePresenter = new WebSafePalettePresenter();
            palettePanel = new WebSafePalettePanel(palettePresenter);
        }
        popupPalette = new PopupPanel(true, true);
        popupPalette.setVisible(false);
        popupPalette.show();
        popupPalette.setPopupPosition(left, top);
        popupPalette.setWidget(palettePanel);
        popupPalette.setVisible(true);
    }

    public void setEnabledSaveButton(final boolean enabled) {
        save.setEnabled(enabled);
    }

    public void setEnabledCloseButton(final boolean enabled) {
        close.setEnabled(enabled);
    }

    public void setTextSaveButton(final String text) {
        save.setText(text);
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
        if (basic != null) {
            bold.setDown(basic.isBold());
            italic.setDown(basic.isItalic());
            underline.setDown(basic.isUnderlined());
            subscript.setDown(basic.isSubscript());
            superscript.setDown(basic.isSuperscript());
        }

        if (extended != null) {
            strikethrough.setDown(extended.isStrikethrough());
        }
    }

}
