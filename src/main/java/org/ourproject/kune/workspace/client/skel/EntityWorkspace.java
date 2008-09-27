package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.platf.client.ui.RoundedPanel;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

public class EntityWorkspace extends DefaultBorderLayout {
    private static final String ENTITY_TITLE = "k-entity-title";
    private static final String ENTITY_SUBTITLE = "k-entity-subtitle";
    private static final String ENTITY_BOTTOM = "k-entity-bottom";
    private static final String ENTITY_CONTENT = "k-entity-content";
    private static final String ENTITY_CONTEXT = "k-entity-context";
    private final Panel content;
    private final Panel context;
    private final SimpleToolbar title;
    private final SimpleToolbar subTitle;
    private final SimpleToolbar bottom;
    private final Toolbar contentTopBar;
    private final Toolbar contentBottomBar;
    private final Toolbar contextTopBar;
    private final Toolbar contextBottomBar;
    private final RoundedPanel roundedTitle;
    private final RoundedPanel roundedBottom;
    private final DefaultBorderLayout contextWrap;

    public EntityWorkspace() {
	final Panel titles = new Panel();
	titles.setBorder(false);
	titles.setLayout(new AnchorLayout());
	final Panel bottomPanel = new Panel();
	bottomPanel.setBorder(false);
	bottomPanel.setLayout(new AnchorLayout());

	title = new SimpleToolbar();
	title.setHeight("" + (DEF_TOOLBAR_HEIGHT - 2));
	title.setStylePrimaryName(ENTITY_TITLE);
	title.ensureDebugId(ENTITY_TITLE);
	subTitle = new SimpleToolbar();
	subTitle.setStylePrimaryName(ENTITY_SUBTITLE);
	subTitle.ensureDebugId(ENTITY_SUBTITLE);
	bottom = new SimpleToolbar();
	bottom.setHeight("" + (DEF_TOOLBAR_HEIGHT - 2));
	bottom.setStylePrimaryName(ENTITY_BOTTOM);
	bottom.ensureDebugId(ENTITY_BOTTOM);

	roundedTitle = new RoundedPanel(title, RoundedPanel.TOPLEFT, 2);
	roundedBottom = new RoundedPanel(bottom, RoundedPanel.BOTTOMLEFT, 2);

	titles.add(roundedTitle, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));
	titles.add(subTitle, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));
	bottomPanel.add(roundedBottom, new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));

	final DefaultBorderLayout contentWrap = new DefaultBorderLayout();
	contextWrap = new DefaultBorderLayout();
	contentWrap.setBorder(true);
	contextWrap.setBorder(true);
	content = new Panel();
	context = new Panel();
	content.setCls(ENTITY_CONTENT);
	context.setCls(ENTITY_CONTEXT);
	content.setBorder(false);
	context.setBorder(false);
	context.setCollapsible(true);
	content.setPaddings(7);
	content.setAutoScroll(true);
	context.setAutoScroll(true);

	contentTopBar = new Toolbar();
	contentBottomBar = new Toolbar();
	contextTopBar = new Toolbar();
	contextBottomBar = new Toolbar();
	contentTopBar.addStyleName("k-toolbar-bottom-line");
	contentBottomBar.addStyleName("k-toolbar-top-line");
	contextTopBar.addStyleName("k-toolbar-bottom-line");
	contextBottomBar.addStyleName("k-toolbar-top-line");
	contentWrap.add(contentTopBar.getPanel(), Position.NORTH, false, DEF_TOOLBAR_HEIGHT);
	contextWrap.add(contextTopBar.getPanel(), Position.NORTH, false, DEF_TOOLBAR_HEIGHT);
	contentWrap.add(content, Position.CENTER);
	contextWrap.add(context, Position.CENTER);
	contentWrap.add(contentBottomBar.getPanel(), Position.SOUTH, false, DEF_TOOLBAR_HEIGHT);
	contextWrap.add(contextBottomBar.getPanel(), Position.SOUTH, false, DEF_TOOLBAR_HEIGHT);

	add(titles, DefaultBorderLayout.Position.NORTH, DEF_TOOLBAR_HEIGHT * 2);
	add(contentWrap.getPanel(), DefaultBorderLayout.Position.CENTER);
	add(contextWrap.getPanel(), DefaultBorderLayout.Position.EAST, true, 175);
	add(bottomPanel, DefaultBorderLayout.Position.SOUTH, DEF_TOOLBAR_HEIGHT + 2);
    }

    public SimpleToolbar getBottomTitle() {
	return bottom;
    }

    public Toolbar getContentBottomBar() {
	return contentBottomBar;
    }

    public Toolbar getContentTopBar() {
	return contentTopBar;
    }

    public Toolbar getContextBottomBar() {
	return contextBottomBar;
    }

    public Toolbar getContextTopBar() {
	return contextTopBar;
    }

    public SimpleToolbar getSubTitle() {
	return subTitle;
    }

    public SimpleToolbar getTitle() {
	return title;
    }

    public void setContent(final Widget widget) {
	setPanel(content, widget);
    }

    public void setContext(final Widget widget) {
	setPanel(context, widget);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	final String themeS = newTheme.toString();
	if (oldTheme != null) {
	    final String previousThemeS = oldTheme.toString();
	    title.removeStyleDependentName(previousThemeS);
	    subTitle.removeStyleDependentName(previousThemeS);
	    bottom.removeStyleDependentName(previousThemeS);
	    super.removeStyle("k-entityworkspace-" + previousThemeS);
	    context.removeStyleName("k-entity-context-" + previousThemeS);
	}
	super.addStyle("k-entityworkspace-" + newTheme);
	roundedTitle.setCornerStyleName("k-entity-title-rd-" + newTheme);
	roundedBottom.setCornerStyleName("k-entity-bottom-rd-" + newTheme);
	title.addStyleDependentName(themeS);
	subTitle.addStyleDependentName(themeS);
	bottom.addStyleDependentName(themeS);
	context.addStyleName("k-entity-context-" + newTheme);
    }
}
