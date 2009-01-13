package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

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
    private final String dialogId;
    private Listener0 onHideListener;
    private final boolean modal;

    public AbstractOptionsPanel(String dialogId, String title, int width, int height, int minWidth, int minHeight,
            boolean modal, Images images, String errorLabelId) {
        this.dialogId = dialogId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.modal = modal;
        this.images = images;
        this.errorLabelId = errorLabelId;
    }

    public void addHideListener(final Listener0 onHideListener) {
        this.onHideListener = onHideListener;
        if (dialog != null) {
            addHideListener();
        }
    }

    public void addOptionTab(View view) {
        if (view instanceof Panel) {
            addTab((Panel) view);
        } else if (view instanceof DefaultForm) {
            addTab(((DefaultForm) view).getFormPanel());
        } else {
            Log.error("Programatic error: Unexpected element added to GroupOptions");
        }
        doLayoutIfNeeded();
    }

    public void createAndShow() {
        show();
        setFirstTabActive();
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
        tabPanel.setActiveTab(0);
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

    private void addHideListener() {
        dialog.addListener(new WindowListenerAdapter() {
            @Override
            public void onClose(Panel panel) {
                onHideListener.onEvent();
            }

            @Override
            public void onHide(Component component) {
                onHideListener.onEvent();
            }
        });
    }

    private void addTab(Panel newTab) {
        createDialogIfNecessary();
        tabPanel.add(newTab);
    }

    private void createDialog() {
        dialog = new BasicDialog(dialogId, title, modal, true, width, height, minWidth, minHeight);
        messageErrorBar = new MessageToolbar(images, errorLabelId);
        dialog.setBottomToolbar(messageErrorBar.getToolbar());
        tabPanel = new TabPanel();
        tabPanel.setBorder(false);
        dialog.add(tabPanel);
        if (iconCls != null) {
            dialog.setIconCls(iconCls);
        }
        if (onHideListener != null) {
            addHideListener();
        }
    }

    private void createDialogIfNecessary() {
        if (dialog == null) {
            createDialog();
        }
    }
}
