package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;

public class EntityOptionsPublicSpaceConfPanel extends Panel implements EntityOptionsPublicSpaceConfView {

    public EntityOptionsPublicSpaceConfPanel(final EntityOptionsPublicSpaceConfPresenter presenter,
            final WorkspaceSkeleton ws, I18nTranslationService i18n, WsThemePresenter wsPresenter) {
        super.setTitle(i18n.t("Styling"));
        super.setIconCls("k-colors-icon");
        super.setAutoScroll(false);
        super.setBorder(false);
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setFrame(true);
        super.setPaddings(10);

        HorizontalPanel wsHP = new HorizontalPanel();
        Label wsThemeInfo = new Label(i18n.t("Change this workspace theme:"));
        ToolbarButton toolbarWsChange = (ToolbarButton) wsPresenter.getView();
        toolbarWsChange.addStyleName("kune-Margin-Medium-l");
        wsHP.add(wsThemeInfo);
        wsHP.add(toolbarWsChange);
        add(wsHP);
        Label wsInfo = new Label(i18n.t("Configure the public space theme:"));
        wsInfo.addStyleName("kune-Margin-Medium-tb");
        Label wsDev = new Label(Site.IN_DEVELOPMENT);
        wsDev.addStyleName("kune-Margin-Medium-tb");
        add(wsInfo);
        add(wsDev);
    }
}
