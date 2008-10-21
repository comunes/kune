package org.ourproject.kune.workspace.client.sitebar.siteoptions;

import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class SiteOptionsPanel implements SiteOptionsView {

    public SiteOptionsPanel(final SiteOptionsPresenter presenter, final WorkspaceSkeleton ws,
            final I18nUITranslationService i18n, final Provider<I18nTranslator> translatorProvider) {
        final PushButton optionsButton = new PushButton("");
        // optionsButton.setText(i18n.t("Options"));
        optionsButton.setHTML(i18n.t("Options")
                + "<img style=\"vertical-align: middle;\" src=\"images/arrowdown.png\" />");
        optionsButton.setStyleName("k-sitebar-labellink");
        ws.getSiteBar().addSeparator();
        ws.getSiteBar().add(optionsButton);
        ws.getSiteBar().addSpacer();
        ws.getSiteBar().addSpacer();
        final Menu optionsMenu = new Menu();
        optionsButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                optionsMenu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 10);
            }
        });
        final Item linkHelpInTrans = new Item(i18n.t("Help with the translation"), new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                super.onClick(item, e);
                translatorProvider.get().doShowTranslator();
            }
        }, "images/language.gif");

        final Item linkKuneBugs = new Item(i18n.t("Report kune bugs"), new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                super.onClick(item, e);
                Window.open("http://code.google.com/p/kune/issues/list", "_blank", null);
            }
        }, "images/kuneicon16.gif");
        optionsMenu.addItem(linkHelpInTrans);
        optionsMenu.addItem(linkKuneBugs);

    }
}
