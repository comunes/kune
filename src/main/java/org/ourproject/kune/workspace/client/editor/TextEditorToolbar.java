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

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBox.PromptCallback;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class TextEditorToolbar extends Composite {

    /**
     * We use an inner EventListener class to avoid exposing event methods on
     * the RichTextToolbar itself.
     */
    private class EventListener implements ClickListener, ChangeListener, KeyboardListener {

        public static final String TEXT_EDITOR_TOOLBAR_INS_IMG = "k-textedtol-img";
        // private ImageChooser ic;
        private final TextEditorInsertElement insertElement;

        public EventListener(TextEditorInsertElement insertElement) {
            this.insertElement = insertElement;
            insertElement.onInsert(new Listener2<String, String>() {
                public void onEvent(String name, String url) {
                    extended.createLink(url);
                }
            });
        }

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
                insertElement.show();
            } else if (sender == backColor) {
                colorPalette.show(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 20, new Listener<String>() {
                    public void onEvent(final String color) {
                        basic.setBackColor(color);
                        colorPalette.hide();
                    }
                });
            } else if (sender == editHtml) {
                presenter.onEditHTML();
            } else if (sender == fontColor) {
                colorPalette.show(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 20, new Listener<String>() {
                    public void onEvent(final String color) {
                        basic.setForeColor(color);
                        colorPalette.hide();
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
                // This will catch any cases where the user moves the cursor
                // using the keyboard, or uses one of the browser's built-in
                // keyboard shortcuts.
                updateStatus();
                fireEdit();
            }
        }

        private void showImagePanel() {
            MessageBox.prompt("Insert image", "Enter an image URL:", new PromptCallback() {
                public void execute(final String btnID, final String text) {
                    if (btnID.equals("ok") && text != null) {
                        extended.insertImage(text);
                    }
                }
            });

        }
    }

    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL,
            RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE };

    private final TextEditorImages images = (TextEditorImages) GWT.create(TextEditorImages.class);
    private final EventListener listener;
    private final RichTextArea richText;
    private final RichTextArea.BasicFormatter basic;
    private final RichTextArea.ExtendedFormatter extended;
    private final VerticalPanel outer = new VerticalPanel();
    private final HorizontalPanel topPanel = new HorizontalPanel();
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private ToggleButton strikethrough;
    private ToggleButton editHtml;
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
    private final ColorWebSafePalette colorPalette;
    private final TextEditorPresenter presenter;
    private final I18nTranslationService i18n;

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText
     *            the rich text area to be controlled
     */
    public TextEditorToolbar(final RichTextArea richText, final TextEditorPresenter presenter,
            final ColorWebSafePalette colorPalette, final I18nTranslationService i18n, boolean permitEditHtml,
            TextEditorInsertElement insertElement) {
        this.richText = richText;
        this.colorPalette = colorPalette;
        this.i18n = i18n;
        this.basic = richText.getBasicFormatter();
        this.extended = richText.getExtendedFormatter();
        this.presenter = presenter;
        listener = new EventListener(insertElement);

        initWidget(outer);

        outer.add(topPanel);
        outer.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        setStyleName("gwt-RichTextToolbar");

        if (basic != null) {
            topPanel.add(bold = createToggleButton(images.bold(), i18n.t("Toggle Bold")));
            topPanel.add(italic = createToggleButton(images.italic(), i18n.t("Toggle Italic")));
            topPanel.add(underline = createToggleButton(images.underline(), i18n.t("Toggle Underline")));
        }

        if (extended != null) {
            topPanel.add(strikethrough = createToggleButton(images.strikeout(), i18n.t("Toggle Strikethrough")));
        }

        if (basic != null) {
            subscript = createToggleButton(images.subscript(), i18n.t("Toggle Subscript"));
            superscript = createToggleButton(images.superscript(), i18n.t("Toggle Superscript"));
            // topPanel.add(subscript);
            // topPanel.add(superscript);
            topPanel.add(justifyLeft = createPushButton(images.alignleft(), i18n.t("Left Justify")));
            topPanel.add(justifyCenter = createPushButton(images.centerpara(), i18n.t("Center")));
            topPanel.add(justifyRight = createPushButton(images.alignright(), i18n.t("Right Justify")));
        }

        if (extended != null) {
            topPanel.add(indent = createPushButton(images.incrementindent(), i18n.t("Indent Right")));
            topPanel.add(outdent = createPushButton(images.decrementindent(), i18n.t("Indent Left")));
            hr = createPushButton(images.hfixedline(), i18n.t("Insert Horizontal Rule"));
            // topPanel.add(hr);
            topPanel.add(ol = createPushButton(images.defaultnumbering(), i18n.t("Insert Ordered List")));
            topPanel.add(ul = createPushButton(images.defaultbullet(), i18n.t("Insert Unordered List")));
            topPanel.add(insertImage = createPushButton(images.images(), i18n.t("Insert Image")));
            topPanel.add(createLink = createPushButton(images.link(), i18n.t("Create Link")));
            topPanel.add(removeLink = createPushButton(images.linkBreak(), i18n.t("Remove Link")));
            removeFormat = createPushButton(images.removeFormat(), i18n.t("Remove Formatting"));
            // topPanel.add(removeFormat);
        }

        if (basic != null) {
            topPanel.add(backColor = createPushButton(images.backcolor(), i18n.t("Background Color")));
            topPanel.add(fontColor = createPushButton(images.fontcolor(), i18n.t("Font Color")));
            topPanel.add(fonts = createFontsMenu());
            topPanel.add(fontSizes = createFontSizesMenu());

            // We only use these listeners for updating status, so don't
            // hook them up unless at least basic editing is supported.
            richText.addKeyboardListener(listener);
            richText.addClickListener(listener);
        }

        if (basic != null && permitEditHtml) {
            topPanel.add(editHtml = createToggleButton(images.edithtml(), i18n.t("Edit HTML")));
        }

        // super.setVisible(false);
    }

    public void editHTML(final boolean edit) {
        final boolean enable = !edit;
        if (basic != null) {
            bold.setVisible(enable);
            italic.setVisible(enable);
            underline.setVisible(enable);
            subscript.setVisible(enable);
            superscript.setVisible(enable);
            justifyLeft.setVisible(enable);
            justifyCenter.setVisible(enable);
            justifyRight.setVisible(enable);
            backColor.setVisible(enable);
            fontColor.setVisible(enable);
        }
        if (extended != null) {
            strikethrough.setVisible(enable);
            indent.setVisible(enable);
            outdent.setVisible(enable);
            insertImage.setVisible(enable);
            createLink.setVisible(enable);
            removeLink.setVisible(enable);
            ol.setVisible(enable);
            ul.setVisible(enable);
            hr.setVisible(enable);
            removeFormat.setVisible(enable);
            fonts.setVisible(enable);
            fontSizes.setVisible(enable);
        }
    }

    private MenuBar createFontSizesMenu() {
        final MenuBar menu = new MenuBar();
        final MenuBar submenu = new MenuBar(true);
        final String fontSizes[] = { i18n.t("Extra small"), i18n.t("Very small"), i18n.t("Small"), i18n.t("Medium"),
                i18n.t("Large"), i18n.t("Very large"), i18n.t("Extra large") };

        KuneUiUtils.setQuickTip(menu, i18n.t("Font Size"));
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

    private MenuBar createFontsMenu() {
        final MenuBar menu = new MenuBar();
        final MenuBar submenu = new MenuBar(true);
        final String fontName[] = { "Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet", "Verdana" };

        KuneUiUtils.setQuickTip(menu, i18n.t("Font Type"));
        menu.addItem(images.charfontname().getHTML(), true, submenu);
        for (final String f : fontName) {
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

    private PushButton createPushButton(final AbstractImagePrototype img, final String tip) {
        final PushButton pb = new PushButton(img.createImage());
        pb.addClickListener(listener);
        KuneUiUtils.setQuickTip(pb, tip);
        return pb;
    }

    private ToggleButton createToggleButton(final AbstractImagePrototype img, final String tip) {
        final ToggleButton tb = new ToggleButton(img.createImage());
        tb.addClickListener(listener);
        KuneUiUtils.setQuickTip(tb, tip);
        return tb;
    }

    private void fireEdit() {
        presenter.onEdit();
    }

    private void fireEditHTML() {
        presenter.onEditHTML();
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
