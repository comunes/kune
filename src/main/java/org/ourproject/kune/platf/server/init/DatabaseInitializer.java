package org.ourproject.kune.platf.server.init;

import java.util.TimeZone;

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.License;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class DatabaseInitializer {
    private final ContentManager contentManager;
    private final I18nCountryManager countryManager;
    private final GroupManager groupManager;
    private final I18nLanguageManager languageManager;
    private final LicenseManager licenseManager;
    private final DatabaseProperties properties;
    private final I18nTranslationManager translationManager;
    private final UserManager userManager;

    @Inject
    public DatabaseInitializer(final DatabaseProperties properties, final UserManager userManager,
            final GroupManager groupManager, final LicenseManager licenseManager,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
            final I18nTranslationManager translationManager, final ContentManager contentManager) {
        this.properties = properties;
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.licenseManager = licenseManager;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
        this.translationManager = translationManager;
        this.contentManager = contentManager;
    }

    private void createDefUsersGroup() throws Exception, UserMustBeLoggedException {
        final String adminName = properties.getAdminUserName();
        final String adminShortName = properties.getAdminShortName();
        final String adminEmail = properties.getAdminEmail();
        final String adminPassword = properties.getAdminPassword();
        // FIXME:
        final User user = new User(adminShortName, adminName, adminEmail, adminPassword,
                languageManager.findByCode("en"), countryManager.findByCode("GB"), TimeZone.getDefault());
        groupManager.createUserGroup(user, false);
        final User dummyUser = new User("dummy", "dummy user", "example@example.com", adminPassword,
                languageManager.findByCode("en"), countryManager.findByCode("GB"), TimeZone.getDefault());
        groupManager.createUserGroup(dummyUser, false);

        final String siteName = properties.getDefaultSiteName();
        final String siteShortName = properties.getDefaultSiteShortName();
        final String defaultLicenseId = properties.getDefaultLicense();
        final License defaultLicense = licenseManager.findByShortName(defaultLicenseId);

        final Group siteGroup = new Group(siteShortName, siteName, defaultLicense, GroupType.PROJECT);
        groupManager.createGroup(siteGroup, user);

        userManager.reIndex();
        groupManager.reIndex();

        final Content defaultContent = siteGroup.getDefaultContent();
        contentManager.save(
                user,
                defaultContent,
                "<img src=\"http://kune.ourproject.org/IMG/cache-200x37/siteon0-200x37.png\" "
                        + "align=\"right\">This a initial demo of "
                        + "<a href=\"http://kune.ourproject.org\">kune</a>."
                        + "<br><div style=\"text-align: right;\"><br></div>"
                        + "Kune is currently under initial development.<br>"
                        + "<br>To test it, <a href=\"#signin\">sign in registering an user</a>, "
                        + "but take into account that:<br><ul><li>kune is not optimized yet, "
                        + "then the initial load and other operations maybe are slow.</li>"
                        + "<li>Don\'t use passwords that you are using in other sites "
                        + "(kune isn\'t secure yet storing passwords).</li>"
                        + "<li>The site is divided in two main areas: the public "
                        + "space and the workspace:</li><ul>"
                        + "<li>The workspace is the most important part of kune, "
                        + "because is where users collaborate and communicates which each others. "
                        + "This part is the most dynamic and we are using modern "
                        + "(and sometimes experimental) web technologies to improve the end user "
                        + "experience. I heavily under development.<br></li>"
                        + "<li>The public space is currently lest developed. "
                        + "Our main goal is to offer customs and configurable styles "
                        + "for groups/users contents.</li></ul></ul><font size=\"4\"><br>"
                        + "We need your feedback<br><br></font>Please help us to improve this software "
                        + "reporting <a href=\"http://ourproject.org/tracker/?atid=139&group_id=407&func=browse\">bugs and/or "
                        + "suggestions</a>. Also you <a href=\"#translate\">help us to translate it</a> "
                        + "into other languages.<br><br>Thank you,<br>"
                        + "<br>--<br><span style=\"font-style: italic;\">" + "The kune development team</span><br><br>");
        contentManager.renameContent(user, defaultContent.getId(), "Welcome to kune demo");
        contentManager.setStatus(defaultContent.getId(), ContentStatus.publishedOnline);
    }

    private void createLicenses() {
        // FIXME: Add CC RDF info (seems CC is working on new forms to add
        // license metadata)
        License license = new License("by-v3.0", "Creative Commons Attribution", "None",
                "http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "images/lic/by80x15.png");
        licenseManager.persist(license);
        license = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "None",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "images/lic/bysa80x15.png");
        licenseManager.persist(license);
        license = new License("by-nd-v3.0", "Creative Commons Attribution-NoDerivs", "None",
                "http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", "images/lic/bynd80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc-v3.0", "Creative Commons Attribution-NonCommercial", "None",
                "http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", "images/lic/bync80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc-sa-v3.0", "Creative Commons Attribution-NonCommercial-ShareAlike", "None",
                "http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "",
                "images/lic/byncsa80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc-nd-v3.0", "Creative Commons Attribution-NonCommercial-NoDerivs", "None",
                "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "",
                "images/lic/byncnd80x15.png");
        licenseManager.persist(license);
        license = new License(
                "gfdl-v1-3",
                "GNU Free Documentation License",
                "The GNU Free Documentation License (GNU FDL or simply GFDL) is a copyleft license for free documentation, giving readers the rights to copy, redistribute and modify a work and requires all copies and derivatives to be available under the same license.",
                "http://www.gnu.org/copyleft/fdl.html", false, true, false, "", "images/lic/gnu-fdl.gif");
        licenseManager.persist(license);
        license = new License(
                "fal",
                "Free Art License",
                "The Free Art license is a French copyleft license for works of art. It authorises the user to freely copy, spread, and transform creative works while respecting the author's rights.",
                "http://artlibre.org/licence/lal/en/", false, true, false, "", "images/lic/fal-license.gif");
        licenseManager.persist(license);
        license = new License(
                "gpl-v3",
                "GNU General Public License",
                "This license grant the recipients of a computer program the rights of the free software definition and uses copyleft to ensure the freedoms are preserved, even when the work is changed or added to.",
                "http://www.gnu.org/licenses/gpl.html", false, true, false, "", "images/lic/gnu-gpl.gif");
        licenseManager.persist(license);
    }

    public void createOthers() {
        final I18nLanguage english = new I18nLanguage(Long.valueOf(1819), "en", "ltr", "English", "", "", "en", "eng",
                "eng", false, "", "", "", "c == 1 ? 1 : 2", null, "L", "MMM d\\, yyyy");
        final I18nLanguage spanish = new I18nLanguage(Long.valueOf(5889), "es", "ltr", "Spanish", "", "", "es", "spa",
                "spa", true, "Español", "", "", "c == 1 ? 1 : 2", null, "L", "dd/MM/yyyy");
        languageManager.persist(english);
        languageManager.persist(spanish);
        final I18nCountry gb = new I18nCountry(Long.valueOf(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom",
                "western", ",");
        countryManager.persist(gb);
        final I18nTranslation test = new I18nTranslation("test", english, "test");
        translationManager.persist(test);
    }

    private void createProperties() {
        // final PropertyGroup groupProps = new PropertyGroup(Group.PROPS_ID);
        // final PropertyGroup userProps = new PropertyGroup(User.PROPS_ID);
        // propGroupManager.persist(userProps);
        // propGroupManager.persist(groupProps);
        // final PropertySubgroup userXmppProps = new
        // PropertySubgroup("user-xmpp");
        // propSubgroupManager.persist(userXmppProps);

        // final Property colorProp = new Property("xmpp_color",
        // "Choose your color", Property.Type.STRING, true, "blue",
        // userProps, userXmppProps);
        // final ArrayList<String> subValues = new ArrayList<String>();
        // subValues.add(SubscriptionMode.autoAcceptAll.toString());
        // subValues.add(SubscriptionMode.autoRejectAll.toString());
        // subValues.add(SubscriptionMode.manual.toString());
        // final Property subProp = new Property("xmpp_subcriptionmode",
        // "New buddies options", Property.Type.ENUM, true,
        // SubscriptionMode.manual.toString(), subValues, userProps,
        // userXmppProps);
        // final Property unanavProp = new
        // Property("xmpp_unanavailableitemsvisible",
        // "Show unavailable buddies",
        // Property.Type.BOOL, true, Boolean.toString(true), userProps,
        // userXmppProps);
        // propertyManager.persist(colorProp);
        // propertyManager.persist(subProp);
        // propertyManager.persist(unanavProp);
    }

    public void initConditional() throws Exception {
        try {
            groupManager.getSiteDefaultGroup();
        } catch (final NoResultException e) {
            initDatabase();
        }
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void initDatabase() throws Exception {
        createOthers();
        createLicenses();
        createProperties();
        createDefUsersGroup();
    }
}
