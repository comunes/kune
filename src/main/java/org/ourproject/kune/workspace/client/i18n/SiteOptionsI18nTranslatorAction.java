package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.SiteOptions;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.Provider;

public class SiteOptionsI18nTranslatorAction extends AbstractAction {
    private final Provider<I18nTranslator> translator;

    public SiteOptionsI18nTranslatorAction(final SiteOptions siteOptions, final I18nTranslationService i18n,
            final IconResources img, final Provider<I18nTranslator> translator) {
        super();
        this.translator = translator;
        putValue(Action.NAME, i18n.t("Help with the translation"));
        putValue(Action.SMALL_ICON, img.language());
        final MenuItemDescriptor item = new MenuItemDescriptor(this);
        item.setPosition(1);
        siteOptions.addAction(item);
    }

    public void actionPerformed(final ActionEvent event) {
        translator.get().doShowTranslator();
    }

}
