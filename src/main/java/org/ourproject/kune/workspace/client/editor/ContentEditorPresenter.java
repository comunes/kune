package org.ourproject.kune.workspace.client.editor;

import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_ICON;
import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_TEXT;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.ComplexToolbar;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class ContentEditorPresenter extends RTESavingEditorPresenter implements ContentEditor {

    public class RenameAction extends AbstractExtendedAction {
        public RenameAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            entityTitle.edit();
        }
    }

    private final WorkspaceSkeleton wspace;
    private VerticalPanel vpanel;
    private RTEditorPanel editorPanel;
    private final SiteSignOutLink siteSignOutLink;
    private final I18nTranslationService i18n;
    private final EntityTitle entityTitle;
    private ComplexToolbar topbar;
    private ComplexToolbar sndbar;

    public ContentEditorPresenter(final I18nTranslationService i18n, final Session session,
            final RTEImgResources imgResources, final Provider<InsertLinkDialog> insLinkDialog,
            final Provider<ColorWebSafePalette> palette, final Provider<EditHtmlDialog> editHtmlDialog,
            final Provider<InsertImageDialog> insertImageDialog, final Provider<InsertMediaDialog> insertMediaDialog,
            final Provider<InsertTableDialog> insertTableDialog, final Provider<InsertSpecialCharDialog> insCharDialog,
            final DeferredCommandWrapper deferred, final boolean autoSave, final StateManager stateManager,
            final SiteSignOutLink siteSignOutLink, final WorkspaceSkeleton wspace, final TimerWrapper timer,
            final EntityTitle entityTitle) {
        super(i18n, session, imgResources, insLinkDialog, palette, editHtmlDialog, insertImageDialog,
                insertMediaDialog, insertTableDialog, insCharDialog, deferred, autoSave, stateManager, timer);
        super.setExtended(true);
        this.i18n = i18n;
        this.siteSignOutLink = siteSignOutLink;
        this.entityTitle = entityTitle;
        this.wspace = wspace;
        Window.addWindowClosingHandler(new ClosingHandler() {
            public void onWindowClosing(final ClosingEvent event) {
                if (isSavePending()) {
                    event.setMessage(i18n.t("You have changes without save. Are you sure?"));
                }
                // onDoSaveAndClose();
            }
        });
        wspace.getEntityWorkspace().addContentListener(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                adjHeight(adjHeight);
            }
        });

    }

    @Override
    public void edit(final String html, final Listener<String> onSave, final Listener0 onEditCancelled) {
        super.edit(html, onSave, onEditCancelled);
        final Toolbar contentTopBar = wspace.getEntityWorkspace().getContentTopBar();
        contentTopBar.removeAll();
        contentTopBar.add(topbar);
        wspace.getEntityWorkspace().setContent(vpanel);
        adjHeight(wspace.getEntityWorkspace().getContentHeight());
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    public void init(final ContentEditorView view) {
        super.init(view);
        addContentActions();
        vpanel = new VerticalPanel();
        editorPanel = (RTEditorPanel) super.getEditorArea();
        topbar = ((ComplexToolbar) super.getTopBar());
        sndbar = ((ComplexToolbar) super.getSndBar());
        vpanel.add(sndbar);
        vpanel.add(editorPanel);
        vpanel.setWidth("100%");
    }

    public void setFileMenuTitle(final String fileMenuTitleNew) {
        super.getFileMenu().setText(fileMenuTitleNew);
    }

    @Override
    protected void onCancelConfirmed() {
        wspace.getEntityWorkspace().clearContent();
        super.onCancelConfirmed();
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    private void addContentActions() {
        final RenameAction renameAction = new RenameAction(i18n.t("Rename"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor renameItem = new MenuItemDescriptor(super.getFileMenu(), renameAction);
        renameItem.setLocation(RTEditor.TOPBAR);
        final KeyStroke key = KeyStroke.getKeyStroke(Keyboard.KEY_F2, 0);
        renameAction.putValue(Action.ACCELERATOR_KEY, key);
        super.setActionShortcut(key, renameAction);
        addAction(renameItem);
    }

    private void adjHeight(final int height) {
        final int barHeight = sndbar.getOffsetHeight();
        final int newHeight = height - 20 - barHeight;
        // Log.debug("Sndbar height: " + barHeight + " new height: " +
        // newHeight);
        editorPanel.adjustSize(newHeight);
        vpanel.setCellHeight(editorPanel, String.valueOf(newHeight));
    }

}
