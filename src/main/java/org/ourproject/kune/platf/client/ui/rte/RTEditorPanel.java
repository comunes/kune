package org.ourproject.kune.platf.client.ui.rte;

import java.util.Date;

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.services.I18nUITranslationService;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.BasicFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.ExtendedFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.FontSize;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.Justification;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.dom.client.Element;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class RTEditorPanel implements RTEditorView {

    private final I18nUITranslationService i18n;
    private final RichTextArea rta;
    private final BasicFormatter basic;
    private final ExtendedFormatter extended;

    public RTEditorPanel(final RTEditorPresenter presenter, I18nUITranslationService i18n) {
        this.i18n = i18n;
        rta = new RichTextArea();
        basic = rta.getBasicFormatter();
        extended = rta.getExtendedFormatter();
        StyleInjector.injectStylesheet(RTEImgResources.INSTANCE.css().getText());
    }

    public void addComment(String userName) {
        String time = i18n.formatDateWithLocale(new Date());
        Element span = DOM.createSpan();
        span.setInnerText(i18n.t("type here") + " -" + userName + time);
        DOM.setElementAttribute((com.google.gwt.user.client.Element) span, "backgroundColor", "rgb(255,255,215");
        // FIXME: addCustomStyle
        insertHtml(span.getString());
    }

    public boolean canBeExtended() {
        return extended != null;
    }

    public void copy() {
        extended.copy();
    }

    public void createLink(String url) {
        extended.createLink(url);
    }

    public void cut() {
        extended.cut();
    }

    public void delete() {
        extended.delete();
    }

    public Widget getRTE() {
        return rta;
    }

    public void insertHorizontalRule() {
        extended.insertHorizontalRule();
    }

    public void insertHR() {
        extended.insertHorizontalRule();
    }

    public void insertHtml(String html) {
        extended.insertHtml(html);
    }

    public void insertImage(String url) {
        extended.insertImage(url);
    }

    public void insertOrderedList() {
        extended.insertOrderedList();
    }

    public void insertUnorderedList() {
        extended.insertUnorderedList();
    }

    public boolean isBold() {
        return basic.isBold();
    }

    public boolean isItalic() {
        return basic.isItalic();
    }

    public boolean isStrikethrough() {
        return extended.isStrikethrough();
    }

    public boolean isSubscript() {
        return basic.isSubscript();
    }

    public boolean isSuperscript() {
        return basic.isSuperscript();
    }

    public boolean isUnderlined() {
        return basic.isUnderlined();
    }

    public void justifyCenter() {
        basic.setJustification(Justification.CENTER);
    }

    public void justifyLeft() {
        basic.setJustification(Justification.LEFT);
    }

    public void justifyRight() {
        basic.setJustification(Justification.RIGHT);
    }

    public void leftIndent() {
        extended.leftIndent();
    }

    public void paste() {
        extended.paste();
    }

    public void redo() {
        extended.redo();
    }

    public void removeFormat() {
        extended.removeFormat();
    }

    public void rightIndent() {
        extended.rightIndent();
    }

    public void selectAll() {
        basic.selectAll();
    }

    public void setActions(ActionCollection<Object> actions) {
        // TODO Auto-generated method stub
    }

    public void setFontName(String name) {
        basic.setFontName(name);
    }

    public void setFontSize(int size) {
        switch (size) {
        case 1:
            basic.setFontSize(FontSize.XX_SMALL);
            break;
        case 2:
            basic.setFontSize(FontSize.X_SMALL);
            break;
        case 3:
            basic.setFontSize(FontSize.SMALL);
            break;
        case 4:
            basic.setFontSize(FontSize.MEDIUM);
            break;
        case 5:
            basic.setFontSize(FontSize.LARGE);
            break;
        case 6:
            basic.setFontSize(FontSize.X_LARGE);
            break;
        case 7:
            basic.setFontSize(FontSize.XX_LARGE);
            break;
        }
    }

    public void setForeColor(String color) {
        basic.setForeColor(color);
    }

    public void toggleBold() {
        basic.toggleBold();
    }

    public void toggleItalic() {
        basic.toggleItalic();
    }

    public void toggleStrikethrough() {
        extended.toggleStrikethrough();
    }

    public void toggleSubscript() {
        basic.toggleSubscript();
    }

    public void toggleSuperscript() {
        basic.toggleSuperscript();
    }

    public void toggleUnderline() {
        basic.toggleUnderline();
    }

    public void undo() {
        extended.undo();
    }

    public void unlink() {
        extended.removeLink();
    }
}
