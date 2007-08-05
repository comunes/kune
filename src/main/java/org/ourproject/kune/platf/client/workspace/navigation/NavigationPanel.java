package org.ourproject.kune.platf.client.workspace.navigation;

import java.util.HashMap;

import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavigationPanel extends VerticalPanel implements NavigationView {
    private final HashMap fileIcons;
    private final VerticalPanel itemsVP;

    public NavigationPanel() {
        NavImages Img = NavImages.App.getInstance();
        fileIcons = new HashMap();
        // FIXME
        fileIcons.put("folder", Img.folder());
        fileIcons.put("file", Img.pageWhite());

        // Initialize
        HorizontalPanel iconBarHP = new HorizontalPanel();
        DockPanel expandPanel1 = new DockPanel();
        HorizontalPanel currentFolderHP = new HorizontalPanel();
        DockPanel expandPanel2 = new DockPanel();
        PushButton upIcon = new PushButton(Img.goUp().createImage(), Img.goUpLight().createImage());
        MenuBar pathMenu = new MenuBar();
        MenuBar pathSubmenu = new MenuBar(true);
        itemsVP = new VerticalPanel();

        // Layout
        expandPanel1.add(iconBarHP, DockPanel.WEST);
        expandPanel2.add(currentFolderHP, DockPanel.WEST);
        add(expandPanel1);
        add(expandPanel2);
        add(itemsVP);
        iconBarHP.add(upIcon);
        iconBarHP.add(pathMenu);
        pathMenu.addItem(Img.folderpath().getHTML(), true, pathSubmenu);
        pathSubmenu.addItem(Img.folder().getHTML() + "&nbsp;Folder", true, new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump!");
            }
        });
        pathSubmenu.addItem(Img.folder().getHTML() + "&nbsp;Folder 2", true, new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump too!");
            }
        });
        currentFolderHP.add(Img.bulletArrowRight().createImage());
        Label currentFolder = new Label("Current Folder");
        currentFolderHP.add(currentFolder);

        // Set properties
        addStyleName("kune-NavigationBar");
        iconBarHP.addStyleName("topBar");
        currentFolderHP.addStyleName("topBar");
        expandPanel1.addStyleName("topBar");
        expandPanel2.addStyleName("topBar");
        setCellWidth(expandPanel1, "100%");
        setCellWidth(expandPanel2, "100%");
        expandPanel1.setWidth("100%");
        expandPanel2.setWidth("100%");
        itemsVP.addStyleName("itemsVP");
        pathMenu.setStyleName("pathMenu");
        upIcon.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                // TODO
                Site.info("Test");
            }
        });
        currentFolder.addClickListener(new ClickListener() {

            public void onClick(final Widget sender) {
                // TODO
                Site.error("Test 2");
            }
        });
    }

    public void add(final String name, final String type, final String event) {
        HorizontalPanel itemHP = new HorizontalPanel();
        itemsVP.add(itemHP);
        itemHP.add(((AbstractImagePrototype) fileIcons.get(type)).createImage());
        itemHP.add(new Hyperlink(name, event));
    }

    public void selectItem(final int index) {
        // TODO Auto-generated method stub
    }

    public void clear() {
        itemsVP.clear();
    }

}
