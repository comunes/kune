package org.ourproject.kune.sitebar.client;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.group.NewGroupForm;
import org.ourproject.kune.platf.client.group.NewGroupListener;
import org.ourproject.kune.platf.client.group.NewGroupFormPanel;
import org.ourproject.kune.platf.client.group.NewGroupFormPresenter;
import org.ourproject.kune.platf.client.license.LicenseChangeListener;
import org.ourproject.kune.platf.client.license.LicenseChooseForm;
import org.ourproject.kune.platf.client.license.LicenseChooseFormPanel;
import org.ourproject.kune.platf.client.license.LicenseChooseFormPresenter;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.login.LoginForm;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.login.LoginFormPanel;
import org.ourproject.kune.sitebar.client.login.LoginFormPresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

public class SiteBarFactory {
    private static SiteMessage siteMessage;

    public static SiteBar createSiteBar(final SiteBarListener listener) {
	SiteBarPresenter siteBarPresenter = new SiteBarPresenter(listener);
	SiteBarPanel siteBarView = new SiteBarPanel(siteBarPresenter);
	siteBarPresenter.init(siteBarView);
	Site.sitebar = siteBarPresenter;
	return siteBarPresenter;
    }

    public static SiteMessage getSiteMessage() {
	if (siteMessage == null) {
	    SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
	    SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter);
	    siteMessagePresenter.init(siteMessageView);
	    siteMessage = siteMessagePresenter;
	    Site.siteUserMessage = siteMessagePresenter;
	}
	return siteMessage;
    }

    public static LoginForm createLogin(final LoginListener listener) {
	LoginFormPresenter presenter = new LoginFormPresenter(listener);
	LoginFormPanel view = new LoginFormPanel(presenter);
	presenter.init(view);
	return presenter;
    }

    public static NewGroupForm createNewGroup(final NewGroupListener listener) {
	NewGroupFormPresenter presenter = new NewGroupFormPresenter(listener);
	NewGroupFormPanel view = new NewGroupFormPanel(presenter);
	presenter.init(view);
	return presenter;
    }

    public static LicenseChooseForm createLicenseChoose(final LicenseChangeListener listener) {

	List licensesList = new ArrayList();
	licensesList.add(new LicenseDTO("by", "Creative Commons Attribution", "",
		"http://creativecommons.org/licenses/by/3.0/", true, false, false, "", ""));
	licensesList.add(new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", ""));
	licensesList.add(new LicenseDTO("by-nd", "Creative Commons Attribution-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", ""));
	licensesList.add(new LicenseDTO("by-nc", "Creative Commons Attribution-NonCommercial", "",
		"http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", ""));
	licensesList.add(new LicenseDTO("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		"http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", ""));
	licensesList.add(new LicenseDTO("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", ""));
	licensesList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	licensesList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));

	LicenseChooseFormPresenter presenter = new LicenseChooseFormPresenter(listener);
	LicenseChooseFormPanel view = new LicenseChooseFormPanel(presenter, licensesList);
	presenter.init(view, licensesList);
	return presenter;
    }
}
