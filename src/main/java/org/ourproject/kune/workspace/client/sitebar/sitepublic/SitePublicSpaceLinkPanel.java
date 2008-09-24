package org.ourproject.kune.workspace.client.sitebar.sitepublic;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.SiteBar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SitePublicSpaceLinkPanel implements SitePublicSpaceLinkView {

    private static final String SITE_GOTO_PUBLIC = "k-site-goto-public";
    private static final String SITE_CONTENT_NO_PUBLIC = "k-site-content-no-public";

    private final HorizontalPanel publicHP;
    private final IconLabel contentNoPublic;
    private final IconLabel gotoPublic;
    private String publicUrl;

    public SitePublicSpaceLinkPanel(final SitePublicSpaceLinkPresenter presenter, final WorkspaceSkeleton ws,
	    final I18nUITranslationService i18n, final Images img) {
	publicHP = new HorizontalPanel();
	gotoPublic = new IconLabel(img.anybody(), i18n.t("Go to Public Space"), false);
	gotoPublic.ensureDebugId(SITE_GOTO_PUBLIC);
	contentNoPublic = new IconLabel(img.anybody(), i18n.t("This content is not public"));
	contentNoPublic.ensureDebugId(SITE_CONTENT_NO_PUBLIC);
	publicHP.add(gotoPublic);
	publicHP.add(contentNoPublic);
	final SiteBar siteBar = ws.getSiteBar();
	siteBar.add(publicHP);
	siteBar.addFill();

	gotoPublic.addStyleName("kune-Margin-Medium-r");
	setContentPublic(true);
	gotoPublic.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		gotoPublic();
	    }
	});
	gotoPublic.setTitle(i18n.t("Leave the workspace and go to the public space of this group")
		+ Site.IN_DEVELOPMENT);
	gotoPublic.addStyleName("k-sitebar-labellink");
	contentNoPublic.addStyleName("k-sitebar-labellink");
    }

    public void setContentGotoPublicUrl(final String publicUrl) {
	this.publicUrl = publicUrl;
    }

    public void setContentPublic(boolean visible) {
	gotoPublic.setVisible(visible);
	contentNoPublic.setVisible(!visible);
    }

    public void setVisible(final boolean visible) {
	publicHP.setVisible(visible);
    }

    private void gotoPublic() {
	Window.open(publicUrl, "_blank", "");
    }
}
