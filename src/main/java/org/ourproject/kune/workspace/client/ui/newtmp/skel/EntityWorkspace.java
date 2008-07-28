package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.platf.client.ui.RoundedPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

public class EntityWorkspace extends DefaultBorderLayout {
    private final Panel content;
    private final Panel container;
    private final TitleBar title;
    private final TitleBar subTitle;
    private final TitleBar bottom;
    private final Toolbar contentTopBar;
    private final Toolbar contentBottomBar;
    private final Toolbar containerTopBar;
    private final Toolbar containerBottomBar;
    private final RoundedPanel roundedTitle;
    private final RoundedPanel roundedBottom;
    private final DefaultBorderLayout containerWrap;

    public EntityWorkspace() {
	final Panel titles = new Panel();
	titles.setBorder(false);
	titles.setLayout(new AnchorLayout());
	final Panel bottomPanel = new Panel();
	bottomPanel.setBorder(false);
	bottomPanel.setLayout(new AnchorLayout());

	title = new TitleBar();
	title.setHeight("" + (DEF_TOOLBAR_HEIGHT - 2));
	title.setStylePrimaryName("k-entity-title");
	subTitle = new TitleBar();
	subTitle.setStylePrimaryName("k-entity-subtitle");
	bottom = new TitleBar();
	bottom.setHeight("" + (DEF_TOOLBAR_HEIGHT - 2));
	bottom.setStylePrimaryName("k-entity-bottom");

	roundedTitle = new RoundedPanel(title, RoundedPanel.TOPLEFT, 2);
	roundedBottom = new RoundedPanel(bottom, RoundedPanel.BOTTOMLEFT, 2);

	titles.add(roundedTitle, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));
	titles.add(subTitle, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));
	bottomPanel.add(roundedBottom, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));

	final DefaultBorderLayout contentWrap = new DefaultBorderLayout();
	containerWrap = new DefaultBorderLayout();
	contentWrap.setBorder(true);
	containerWrap.setBorder(true);
	content = new Panel();
	container = new Panel();
	content.setBorder(false);
	container.setBorder(false);
	container.setCollapsible(true);
	content.setPaddings(5);
	container.setPaddings(5);
	content.setAutoScroll(true);
	container.setAutoScroll(true);

	contentTopBar = new Toolbar();
	contentBottomBar = new Toolbar();
	containerTopBar = new Toolbar();
	containerBottomBar = new Toolbar();
	contentTopBar.addClass("k-toolbar-bottom-line");
	contentBottomBar.addClass("k-toolbar-top-line");
	containerTopBar.addClass("k-toolbar-bottom-line");
	containerBottomBar.addClass("k-toolbar-top-line");
	contentWrap.add(contentTopBar.getPanel(), Position.NORTH, false, DEF_TOOLBAR_HEIGHT);
	containerWrap.add(containerTopBar.getPanel(), Position.NORTH, false, DEF_TOOLBAR_HEIGHT);
	contentWrap.add(content, Position.CENTER);
	containerWrap.add(container, Position.CENTER);
	contentWrap.add(contentBottomBar.getPanel(), Position.SOUTH, false, DEF_TOOLBAR_HEIGHT);
	containerWrap.add(containerBottomBar.getPanel(), Position.SOUTH, false, DEF_TOOLBAR_HEIGHT);

	add(titles, DefaultBorderLayout.Position.NORTH, DEF_TOOLBAR_HEIGHT * 2);
	add(contentWrap.getPanel(), DefaultBorderLayout.Position.CENTER);
	add(containerWrap.getPanel(), DefaultBorderLayout.Position.EAST, true, 175);
	add(bottomPanel, DefaultBorderLayout.Position.SOUTH, DEF_TOOLBAR_HEIGHT + 2);
    }

    public TitleBar getBottomTitle() {
	return bottom;
    }

    public Toolbar getContainerBottomBar() {
	return containerBottomBar;
    }

    public Toolbar getContainerTopBar() {
	return containerTopBar;
    }

    public Toolbar getContentBottomBar() {
	return contentBottomBar;
    }

    public Toolbar getContentTopBar() {
	return contentTopBar;
    }

    public TitleBar getSubTitle() {
	return subTitle;
    }

    public TitleBar getTitle() {
	return title;
    }

    public void setContainer(final Widget widget) {
	setPanel(container, widget);
    }

    public void setContent(final Widget widget) {
	setPanel(content, widget);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	final String themeS = newTheme.toString();
	if (oldTheme != null) {
	    final String previousThemeS = oldTheme.toString();
	    title.removeStyleDependentName(previousThemeS);
	    subTitle.removeStyleDependentName(previousThemeS);
	    bottom.removeStyleDependentName(previousThemeS);
	    super.removeStyle("k-entityworkspace-" + previousThemeS);
	    container.removeStyleName("k-entity-container-" + previousThemeS);
	}
	super.addStyle("k-entityworkspace-" + newTheme);
	roundedTitle.setCornerStyleName("k-entity-title-rd-" + newTheme);
	roundedBottom.setCornerStyleName("k-entity-bottom-rd-" + newTheme);
	title.addStyleDependentName(themeS);
	subTitle.addStyleDependentName(themeS);
	bottom.addStyleDependentName(themeS);
	container.addStyleName("k-entity-container-" + newTheme);
    }
}
