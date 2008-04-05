
package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContextTopBar extends VerticalPanel {

    public final Label currentFolder;
    public final PushButton btnGoParent;
    public final Image btnGoParentDisabled;
    public final HorizontalPanel firstRow;
    private final ContextItemsImages img;
    private final MenuBar pathSubmenu;

    public ContextTopBar(final ContextItemsPresenter presenter) {
        img = ContextItemsImages.App.getInstance();
        firstRow = new HorizontalPanel();
        final HorizontalPanel secondRow = new HorizontalPanel();
        final HorizontalPanel iconBarHP = new HorizontalPanel();
        final HorizontalPanel currentFolderHP = new HorizontalPanel();
        btnGoParent = new PushButton(img.folderGoUp().createImage(), img.folderGoUpLight().createImage());
        btnGoParent.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                presenter.onGoUp();
            }
        });

        KuneUiUtils.setQuickTip(btnGoParent, Kune.I18N.t("Go to parent folder"));
        btnGoParentDisabled = img.folderGoUpLight().createImage();
        final MenuBar pathMenu = new MenuBar();
        pathSubmenu = new MenuBar(true);
        // Layout
        add(firstRow);
        add(secondRow);
        firstRow.add(iconBarHP);
        secondRow.add(currentFolderHP);
        // iconBarHP.add(btnGoParent);
        final RoundedBorderDecorator buttonRounded = new RoundedBorderDecorator(pathMenu, RoundedBorderDecorator.ALL,
                RoundedBorderDecorator.SIMPLE);
        iconBarHP.add(buttonRounded);
        pathMenu.addItem(img.folderpathmenu().getHTML(), true, pathSubmenu);
        KuneUiUtils.setQuickTip(pathMenu, Kune.I18N.t("Navigation tree"));
        currentFolderHP.add(btnGoParent);
        currentFolderHP.add(btnGoParentDisabled);
        currentFolder = new Label("Current Container");
        currentFolderHP.add(currentFolder);

        // Set properties
        addStyleName("kune-NavigationBar");
        firstRow.addStyleName("topBar");
        firstRow.addStyleName("topBar-margin");
        secondRow.addStyleName("topBar");
        firstRow.setWidth("100%");
        secondRow.setWidth("100%");
        setCellWidth(firstRow, "100%");
        setCellWidth(secondRow, "100%");
        firstRow.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        iconBarHP.addStyleName("kune-topBar-iconBar");
        iconBarHP.setCellVerticalAlignment(btnGoParent, VerticalPanel.ALIGN_MIDDLE);
        iconBarHP.setCellVerticalAlignment(buttonRounded, VerticalPanel.ALIGN_MIDDLE);
        pathMenu.setStyleName("pathMenu");
        buttonRounded.setColor("AAA");
        btnGoParent.addStyleName("kune-pointer");
    }

    public void setAbsolutePath(final ContainerSimpleDTO[] absolutePath) {
        pathSubmenu.clearItems();
        String indent = "";
        for (int i = 0; i < absolutePath.length; i++) {
            final ContainerSimpleDTO folder = absolutePath[i];
            String folderName = folder.getName();
            if (i == 0) {
                // We translate root folders
                folderName = Kune.I18N.t(folderName);
            }
            pathSubmenu.addItem(indent + img.folder().getHTML() + "&nbsp;" + folderName, true, new Command() {
                public void execute() {
                    DefaultDispatcher.getInstance().fire(PlatformEvents.GOTO_CONTAINER, folder.getId());
                }
            });
            indent = indent + "&nbsp&nbsp;";
        }
    }

}
