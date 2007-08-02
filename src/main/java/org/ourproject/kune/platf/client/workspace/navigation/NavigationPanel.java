package org.ourproject.kune.platf.client.workspace.navigation;

import java.util.HashMap;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationPanel extends VerticalPanel implements NavigationView {
    private HashMap fileIcons;
    private VerticalPanel itemsVP;

    public NavigationPanel() {
        NavImages Img = NavImages.App.getInstance();

        // Initialize
        HorizontalPanel iconBarHP = new HorizontalPanel();
        HorizontalPanel currentFolderHP = new HorizontalPanel();
        PushButton upIcon = new PushButton(Img.goUp().createImage(), Img.goUpLight().createImage());
        MenuBar pathMenu = new MenuBar();
        MenuBar pathSubmenu = new MenuBar(true);
        itemsVP = new VerticalPanel();

        // Layout
        add(iconBarHP);
        add(currentFolderHP);
        add(itemsVP);
        iconBarHP.add(upIcon);
        iconBarHP.add(pathMenu);
        pathMenu.addItem(Img.folderpath().getHTML(), true, pathSubmenu);
        pathSubmenu.addItem("Folder", new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump!");
            }
        });
        pathSubmenu.addItem("Folder 2", new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump too!");
            }
        });
        currentFolderHP.add(Img.bulletArrowRight().createImage());
        currentFolderHP.add(new Label("Current Folder"));

        addStyleName("kune-NavigationBar");
        iconBarHP.addStyleName("IconBarHP");
        currentFolderHP.addStyleName("CurrentFolderHP");
        itemsVP.addStyleName("itemsVP");

        fileIcons = new HashMap();
        // FIXME
        fileIcons.put("folder", Img.folder());
        fileIcons.put("file", Img.pageWhite());
    }

    public void add(String name, String type, String event) {
        HorizontalPanel itemHP = new HorizontalPanel();
        itemsVP.add(itemHP);
        itemHP.add(((AbstractImagePrototype) fileIcons.get(type)).createImage());
        itemHP.add(new Hyperlink(name, event));
    }

    public void selectItem(int index) {
        // TODO Auto-generated method stub
    }

    public void clear() {
        itemsVP.clear();
    }

}
