package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;

public abstract class AbstractOptionsPanel {
    private BasicDialog dialog;
    private MessageToolbar messageErrorBar;
    private TabPanel tabPanel;
    private String title;
    private final int width;
    private final int height;
    private final int minWidth;
    private final int minHeight;
    private final Images images;
    private final String errorLabelId;
    private String iconCls;
    private final String id;

    public AbstractOptionsPanel(String title, int width, int height, int minWidth, int minHeight, Images images,
            String id, String errorLabelId) {
        this.id = id;
        this.title = title;
        this.width = width;
        this.height = height;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.images = images;
        this.errorLabelId = errorLabelId;
    }

    public void addTab(Panel newTab) {
        createDialogIfNecessary();
        tabPanel.add(newTab);
    }

    public void doLayoutIfNeeded() {
        if (dialog.isRendered()) {
            dialog.doLayout(false);
        }
    }

    public void hide() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void hideMessages() {
        if (dialog != null) {
            messageErrorBar.hideErrorMessage();
        }
    }

    public boolean isVisible() {
        createDialogIfNecessary();
        return dialog.isVisible();
    }

    public void setErrorMessage(final String message, final SiteErrorType type) {
        messageErrorBar.setErrorMessage(message, type);
    }

    public void setFirstTabActive() {
        tabPanel.setActiveTab(1);
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
        if (dialog != null) {
            dialog.setIconCls(iconCls);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        if (dialog != null) {
            dialog.setTitle(title);
        }
    }

    public void show() {
        createDialogIfNecessary();
        dialog.show();
    }

    private void createDialog() {
        dialog = new BasicDialog(title, false, true, width, height, minWidth, minHeight);
        dialog.setId(id);
        messageErrorBar = new MessageToolbar(images, errorLabelId);
        dialog.setBottomToolbar(messageErrorBar.getToolbar());
        tabPanel = new TabPanel();
        tabPanel.setBorder(false);
        dialog.add(tabPanel);
        if (iconCls != null) {
            dialog.setIconCls(iconCls);
        }
    }

    private void createDialogIfNecessary() {
        if (dialog == null) {
            createDialog();
        }
    }
}
