package org.ourproject.kune.platf.client.ui.rte;

import java.util.Date;

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.BasicFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.ExtendedFormatter;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

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
    }

    public void addComment(String userName) {
        String time = i18n.formatDateWithLocale(new Date());
        Element span = DOM.createSpan();
        span.setInnerText(i18n.t("type here") + " -" + userName + time);
        DOM.setElementAttribute((com.google.gwt.user.client.Element) span, "backgroundColor", "rgb(255,255,215");
        // addCustomStyle
        // insertHtml
    }

    public boolean canBeExtended() {
        return extended != null;
    }

    public void copy() {
        extended.undo();
    }

    public void createlink(String url) {
        // TODO Auto-generated method stub

    }

    public void cut() {
        extended.undo();
    }

    public void delete() {
        // TODO Auto-generated method stub

    }

    public void fontname(String name) {
        // TODO Auto-generated method stub

    }

    public void fontsize(String size) {
        // TODO Auto-generated method stub

    }

    public void forecolor(String color) {
        // TODO Auto-generated method stub

    }

    public void indent() {
        // TODO Auto-generated method stub

    }

    public void inserthorizontalrule() {
        // TODO Auto-generated method stub

    }

    public void insertHR() {
        extended.insertHorizontalRule();
    }

    public void inserthtml(String html) {
        // TODO Auto-generated method stub

    }

    public void insertimage(String url) {
        // TODO Auto-generated method stub

    }

    public void insertorderedlist() {
        // TODO Auto-generated method stub

    }

    public void insertparagraph() {
        // TODO Auto-generated method stub

    }

    public void insertunorderedlist() {
        // TODO Auto-generated method stub

    }

    public void italic() {
        // TODO Auto-generated method stub

    }

    public void justifycenter() {
        // TODO Auto-generated method stub

    }

    public void justifyfull() {
        // TODO Auto-generated method stub

    }

    public void justifyleft() {
        // TODO Auto-generated method stub

    }

    public void justifyright() {
        // TODO Auto-generated method stub

    }

    public void outdent() {
        // TODO Auto-generated method stub

    }

    public void paste() {
        extended.undo();
    }

    public void redo() {
        extended.redo();
    }

    public void removeformat() {
        // TODO Auto-generated method stub
    }

    public void selectall() {
        // TODO Auto-generated method stub
    }

    public void selectAll() {
        // TODO Auto-generated method stub
    }

    public void setActions(ActionCollection<Object> actions) {
        // TODO Auto-generated method stub

    }

    public void strikethrough() {
        // TODO Auto-generated method stub

    }

    public void subscript() {
        // TODO Auto-generated method stub

    }

    public void superscript() {
        // TODO Auto-generated method stub

    }

    public void underline() {
        basic.toggleUnderline();
    }

    public void undo() {
        extended.undo();
    }

    public void unlink() {
        extended.removeLink();
    }
}
