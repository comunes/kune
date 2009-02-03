package org.ourproject.kune.platf.client.ui.rte;

import java.util.Date;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class RTEditorPanel implements RTEditorView {

    private final I18nUITranslationService i18n;

    public RTEditorPanel(final RTEditorPresenter presenter, I18nUITranslationService i18n) {
        this.i18n = i18n;
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
        // TODO Auto-generated method stub
        return false;
    }

    public void insertHR() {
        // TODO Auto-generated method stub

    }

    public void redo() {
        // TODO Auto-generated method stub
    }

    public void selectAll() {
        // TODO Auto-generated method stub
    }

    public void undo() {
        // TODO Auto-generated method stub
    }
}
