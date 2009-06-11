package org.ourproject.kune.workspace.client.editor;

import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_ICON;
import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_TEXT;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.ComplexToolbar;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorNew;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanelNew;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorView;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class ContentEditor extends RTESavingEditorPresenter {

    public class RenameAction extends AbstractExtendedAction {
        public RenameAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            entityTitle.edit();
        }
    }

    private final WorkspaceSkeleton ws;
    private final VerticalPanel vp;
    private final RTEditorNew basicEditor;
    private final RTEditorPanelNew editorPanel;
    private final ComplexToolbar topbar;
    private final SiteSignOutLink siteSignOutLink;
    private final I18nTranslationService i18n;
    private final EntityTitle entityTitle;
    private final ComplexToolbar sndbar;

    public ContentEditor(final RTEditorNew editor, final boolean autoSave, final I18nTranslationService i18n,
            final StateManager stateManager, final SiteSignOutLink siteSignOutLink,
            final DeferredCommandWrapper deferredCommandWrapper, final RTEImgResources imgResources,
            final WorkspaceSkeleton ws, final TimerWrapper timer, final RTESavingEditorView view,
            final EntityTitle entityTitle) {
        super(editor, autoSave, i18n, stateManager, deferredCommandWrapper, imgResources, timer);
        this.i18n = i18n;
        this.siteSignOutLink = siteSignOutLink;
        this.entityTitle = entityTitle;
        super.init(view);
        this.ws = ws;
        Window.addWindowClosingHandler(new ClosingHandler() {
            public void onWindowClosing(final ClosingEvent event) {
                if (isSavePending()) {
                    event.setMessage(i18n.t("You have changes without save. Are you sure?"));
                }
                // onDoSaveAndClose();
            }
        });
        vp = new VerticalPanel();
        basicEditor = super.getBasicEditor();
        addContentActions();
        editorPanel = (RTEditorPanelNew) basicEditor.getEditorArea();
        topbar = ((ComplexToolbar) basicEditor.getTopBar());
        sndbar = ((ComplexToolbar) basicEditor.getSndBar());
        vp.add(sndbar);
        vp.add(editorPanel);
        basicEditor.setExtended(true);
        vp.setWidth("100%");
        ws.getEntityWorkspace().addContentListener(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                adjHeight(adjHeight);
            }
        });

    }

    @Override
    public void edit(final String html, final Listener<String> onSave, final Listener0 onEditCancelled) {
        final Toolbar contentTopBar = ws.getEntityWorkspace().getContentTopBar();
        contentTopBar.removeAll();
        contentTopBar.add(topbar);
        ws.getEntityWorkspace().setContent(vp);
        super.edit(html, onSave, onEditCancelled);
        adjHeight(ws.getEntityWorkspace().getContentHeight());
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    public void setFileMenuTitle(final String fileMenuTitleNew) {
        basicEditor.getFileMenu().setText(fileMenuTitleNew);
    }

    @Override
    protected void onCancelConfirmed() {
        ws.getEntityWorkspace().clearContent();
        super.onCancelConfirmed();
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    private void addContentActions() {
        final RenameAction renameAction = new RenameAction(i18n.t("Rename"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor renameItem = new MenuItemDescriptor(basicEditor.getFileMenu(), renameAction);
        renameItem.setLocation(RTEditorNew.TOPBAR);
        final KeyStroke key = KeyStroke.getKeyStroke(Keyboard.KEY_F2, 0);
        renameAction.putValue(Action.ACCELERATOR_KEY, key);
        basicEditor.setActionShortcut(key, renameAction);
        basicEditor.addAction(renameItem);
    }

    private void adjHeight(final int height) {
        final int barHeight = sndbar.getOffsetHeight();
        final int newHeight = height - 20 - barHeight;
        // Log.debug("Sndbar height: " + barHeight + " new height: " +
        // newHeight);
        editorPanel.adjustSize(newHeight);
        vp.setCellHeight(editorPanel, "" + newHeight);
    }
}
