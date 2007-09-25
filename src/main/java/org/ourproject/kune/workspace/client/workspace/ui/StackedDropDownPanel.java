package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StackedDropDownPanel extends DropDownPanel {

    public final static String ICON_HORIZ_ALIGN_RIGHT = "right";
    public final static String ICON_HORIZ_ALIGN_LEFT = "left";
    private final AbstractPresenter presenter;
    private final StackPanel stack;
    private final ArrayList stackList;
    private final VerticalPanel bottomLinksVP;
    private final ArrayList bottomLinksIndex;
    private String headerText;
    private String headerTitle;
    private int headerCount;
    private boolean headerCountVisible;

    public StackedDropDownPanel(final AbstractPresenter presenter, final String borderColor, final String headerText,
	    final String headerTitle, final boolean headerCountVisible) {
	this.presenter = presenter;
	this.headerText = headerText;
	this.headerTitle = headerTitle;
	this.headerCountVisible = headerCountVisible;
	this.headerCount = 0;
	VerticalPanel generalVP = new VerticalPanel();
	stack = new StackPanel();
	stackList = new ArrayList();
	bottomLinksVP = new VerticalPanel();
	bottomLinksIndex = new ArrayList();

	// Layout
	generalVP.add(stack);
	generalVP.add(bottomLinksVP);
	setContent(generalVP);

	// Set properties
	super.setColor(borderColor);
	setContentVisible(true); // DropDown
	setHeaderText(headerText);
	setHeaderTitle(headerTitle);
	addStyleName("kune-SocialNet");
	addStyleName("kune-Margin-Medium-t");
	stack.setStyleName("kune-SocialNet");
    }

    /* Header */

    public void setHeaderText(final String headerText) {
	this.headerText = headerText;
    }

    public void setHeaderTitle(final String headerTitle) {
	this.headerTitle = headerTitle;
	super.setTitle(headerTitle);
    }

    public void setHeaderCountVisible(final boolean headerCountVisible) {
	this.headerCountVisible = headerCountVisible;
    }

    public void updateHeaderText() {
	if (headerCountVisible) {
	    super.setHeaderText(headerText + " (" + headerCount + ")");
	} else {
	    super.setHeaderText(headerText);
	}

    }

    public String getHeaderText() {
	return headerText;
    }

    public String getHeaderTitle() {
	return headerTitle;
    }

    public int getHeaderCount() {
	return headerCount;
    }

    public boolean isHeaderCountVisible() {
	return headerCountVisible;
    }

    /* Stack items */

    public void addStackItem(final String name, final String title, final boolean countVisible) {
	addStackItem(name, title, null, null, countVisible);
    }

    public void addStackItem(final String name, final String title, final AbstractImagePrototype icon,
	    final String iconAlign, final boolean countVisible) {
	ScrollPanel siSP = new ScrollPanel();
	VerticalPanel siVP = new VerticalPanel();
	siSP.add(siVP);
	StackItem stackItem = new StackItem(name, title, icon, iconAlign, countVisible);
	stack.add(siSP, stackItem.getHtml(), true);
	stackList.add(stackItem);
    }

    public void removeStackItem(final String name) {
	int idx = indexInArray(name);
	stack.remove(idx);
	stackList.remove(idx);
    }

    private int indexInArray(final String name) {
	final Iterator iter = stackList.iterator();
	int i = 0;
	while (iter.hasNext()) {
	    final StackItem stackItem = (StackItem) iter.next();
	    if (stackItem.getName() == name) {
		return i;
	    } else {
		i++;
	    }
	}
	throw new IndexOutOfBoundsException();
    }

    /* Stack subItems */

    public void addStackSubItem(final String parentItemName, final AbstractImagePrototype icon, final String name,
	    final String title, final MemberAction[] memberActions) {
	StackSubItem stackSubItem = new StackSubItem(icon, name, title, memberActions);
	int indexOfStackItem = indexInArray(parentItemName);
	ScrollPanel sp = (ScrollPanel) stack.getWidget(indexOfStackItem);
	VerticalPanel vp = (VerticalPanel) sp.getWidget();
	vp.add(stackSubItem);
	StackItem stackItem = (StackItem) stackList.get(indexOfStackItem);
	stackItem.addSubItem(name);
	stack.setStackText(indexOfStackItem, stackItem.getHtml(), true);
	headerCount++;
	updateHeaderText();
    }

    public void removeStackSubItem(final String parentItemName, final String name) {
	int indexOfStackItem = indexInArray(parentItemName);
	int indexOfStackSubItem = ((StackItem) stackList.get(indexOfStackItem)).indexOfSubItem(name);

	ScrollPanel sp = (ScrollPanel) stack.getWidget(indexOfStackItem);
	((VerticalPanel) sp.getWidget()).remove(indexOfStackSubItem);
	headerCount--;
	updateHeaderText();
    }

    public void addBottomLink(final AbstractImagePrototype icon, final String text, final String targetHistoryToken,
	    final String action) {
	IconHyperlink link = new IconHyperlink(icon, text, targetHistoryToken);
	bottomLinksVP.add(link);
	link.addStyleName("kune-SocialNetJoinLink");
	link.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doAction(action, null);

	    }
	});
	bottomLinksVP.setCellHorizontalAlignment(link, HorizontalPanel.ALIGN_CENTER);
	bottomLinksIndex.add(text);
    }

    public void removeBottomLink(final String text) {
	bottomLinksVP.remove(indexOfLink(text));
    }

    public void setDropDownContentVisible(final boolean visible) {
	setContentVisible(visible);
    }

    // public void addCategory(final String name, final List groupList, final
    // AccessRightsDTO rights,
    // final MemberAction[] adminActions, final MemberAction[] editorActions,
    // final MemberAction[] viewerActions) {
    //
    // ScrollPanel categorySP = new ScrollPanel();
    // VerticalPanel categoryVP = new VerticalPanel();
    // categorySP.add(categoryVP);
    // stack.add(categorySP, name);
    // stackIndex.add(name);
    // stackSubItems.put(name, new ArrayList());
    //
    // final Iterator iter = groupList.iterator();
    // while (iter.hasNext()) {
    // final GroupDTO group = (GroupDTO) iter.next();
    // AbstractImagePrototype icon = setGroupIcon(group);
    // StackSubItem groupMenu = new StackSubItem(icon, group.getShortName());
    // if (rights.isAdministrable() && adminActions != null) {
    // for (int i = 0; i < adminActions.length; i++) {
    // groupMenu.addAction(adminActions[i], group);
    // }
    // }
    // if (rights.isEditable() && editorActions != null) {
    // for (int i = 0; i < editorActions.length; i++) {
    // groupMenu.addAction(editorActions[i], group);
    // }
    // }
    // if (rights.isVisible() && viewerActions != null) {
    // for (int i = 0; i < viewerActions.length; i++) {
    // groupMenu.addAction(viewerActions[i], group);
    // }
    // }
    // categoryVP.add(groupMenu);
    // ((ArrayList) stackSubItems.get(name)).add(group.getShortName());
    // }
    // }

    private int indexOfLink(final String text) {
	return bottomLinksIndex.indexOf(text);
    }

    /*
     * Unattached stack item. We update the stack using this object and
     * generating the Html with getHtml
     */

    class StackItem {
	private String text;
	private String title;
	private AbstractImagePrototype icon;
	private String iconAlign;
	private boolean countVisible;
	private final ArrayList subItems;

	public StackItem(final String text, final String title, final AbstractImagePrototype icon,
		final String iconAlign, final boolean countVisible) {
	    this.text = text;
	    this.title = title;
	    this.icon = icon;
	    this.iconAlign = iconAlign;
	    this.countVisible = countVisible;
	    subItems = new ArrayList();
	}

	public int indexOfSubItem(final String name) {
	    return subItems.indexOf(name);
	}

	public void addSubItem(final String name) {
	    subItems.add(name);
	}

	public String getName() {
	    return text;
	}

	public void setText(final String text) {
	    this.text = text;
	}

	public void setTitle(final String title) {
	    this.title = title;
	}

	public void setIcon(final AbstractImagePrototype icon) {
	    this.icon = icon;
	}

	public void setIconAlign(final String iconAlign) {
	    this.iconAlign = iconAlign;
	}

	public boolean isCountVisible() {
	    return countVisible;
	}

	public void setCountVisible(final boolean visible) {
	    this.countVisible = visible;
	}

	public String getHtml() {
	    Element div = DOM.createDiv();
	    Element labelElem = DOM.createSpan();
	    Element iconElement = null;
	    DOM.setElementAttribute(div, "title", title);
	    boolean insertIcon = icon != null && iconAlign != null;
	    if (insertIcon) {
		iconElement = icon.createImage().getElement();
		if (iconAlign == StackedDropDownPanel.ICON_HORIZ_ALIGN_LEFT) {
		    DOM.appendChild(div, iconElement);
		}
	    }
	    DOM.setInnerText(labelElem, text + (countVisible ? " (" + subItems.size() + ")" : ""));
	    DOM.appendChild(div, labelElem);
	    if (insertIcon) {
		if (iconAlign == StackedDropDownPanel.ICON_HORIZ_ALIGN_RIGHT) {
		    DOM.appendChild(div, iconElement);
		}
	    }
	    return div.toString();
	}
    }

    class StackSubItem extends MenuBar {
	private final MenuBar actions;
	private AbstractImagePrototype icon;
	private String name;

	public StackSubItem(final AbstractImagePrototype icon, final String name, final String title,
		final MemberAction[] memberActions) {
	    super(false);
	    this.icon = icon;
	    this.name = name;
	    String label = icon.getHTML() + name;
	    setTitle(title);
	    actions = new MenuBar(true);
	    addItem(label, true, actions);
	    setAutoOpen(false);
	    actions.setAutoOpen(true);
	    setStyleName("kune-GroupMemberLabel");
	    actions.setStyleName("kune-GroupMemberCommands");
	    for (int i = 0; i < memberActions.length; i++) {
		addAction(memberActions[i], name);
	    }
	}

	public void setName(final String name) {
	    this.name = name;
	    setMenu();
	}

	public void setImage(final AbstractImagePrototype icon) {
	    this.icon = icon;
	    setMenu();
	}

	public void addAction(final MemberAction memberAction, final String param) {
	    String itemHtml = "";
	    AbstractImagePrototype icon = memberAction.getIcon();
	    if (icon != null) {
		itemHtml = icon.getHTML();
	    }
	    itemHtml += memberAction.getText();
	    actions.addItem(itemHtml, true, createCommand(memberAction.getAction(), param));
	}

	private void setMenu() {
	    String label = icon.getHTML() + name;
	    ((MenuItem) getItems().get(0)).setText(label);
	}

	private Command createCommand(final String action, final String param) {
	    return new Command() {
		public void execute() {
		    presenter.doAction(action, param);
		}
	    };
	}
    }

}
