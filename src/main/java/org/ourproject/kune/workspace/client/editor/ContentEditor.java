package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanel;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class ContentEditor extends RTESavingEditorPresenter {

    private final WorkspaceSkeleton ws;
    private final VerticalPanel vp;
    private final RTEditor basicEditor;
    private final RTEditorPanel editorPanel;
    private final AbstractToolbar topbar;
    private final SiteSignOutLink siteSignOutLink;
    private final I18nTranslationService i18n;
    private final EntityTitle entityTitle;
    private final String fileMenuTitle;
    private final AbstractToolbar sndbar;

    public ContentEditor(RTEditor editor, boolean autoSave, final I18nTranslationService i18n,
            StateManager stateManager, SiteSignOutLink siteSignOutLink, DeferredCommandWrapper deferredCommandWrapper,
            RTEImgResources imgResources, WorkspaceSkeleton ws, TimerWrapper timer, RTESavingEditorView view,
            EntityTitle entityTitle) {
        super(editor, autoSave, i18n, stateManager, deferredCommandWrapper, imgResources, timer);
        this.i18n = i18n;
        this.siteSignOutLink = siteSignOutLink;
        this.entityTitle = entityTitle;
        super.init(view);
        this.ws = ws;
        fileMenuTitle = i18n.t(RTESavingEditorPresenter.FILE_DEF_MENU_OPTION);
        Window.addWindowCloseListener(new WindowCloseListener() {
            public void onWindowClosed() {
            }

            public String onWindowClosing() {
                if (isSavePending()) {
                    return i18n.t("You have changes without save. Are you sure?");
                }
                // onDoSaveAndClose();
                return null;
            }

        });
        vp = new VerticalPanel();
        basicEditor = super.getBasicEditor();
        addContentActions();
        vp.add((Widget) ((ActionToolbarPanel<Object>) basicEditor.getSndBar().getView()).getToolbar());
        editorPanel = (RTEditorPanel) basicEditor.getEditorArea();
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
        topbar = ((ActionToolbarPanel<Object>) basicEditor.getTopBar().getView()).getToolbar();
        sndbar = ((ActionToolbarPanel<Object>) basicEditor.getSndBar().getView()).getToolbar();
    }

    @Override
    public void edit(String html, Listener<String> onSave, Listener0 onEditCancelled) {
        Toolbar contentTopBar = ws.getEntityWorkspace().getContentTopBar();
        contentTopBar.removeAll();
        contentTopBar.add((Widget) topbar);
        ws.getEntityWorkspace().setContent(vp);
        super.edit(html, onSave, onEditCancelled);
        adjHeight(ws.getEntityWorkspace().getContentHeight());
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    public void setFileMenuTitle(String fileMenuTitleNew) {
        basicEditor.getTopBar().setParentMenuTitle(RTEditor.TOPBAR, fileMenuTitle, null, fileMenuTitleNew);
    }

    @Override
    protected void onCancelConfirmed() {
        ws.getEntityWorkspace().clearContent();
        super.onCancelConfirmed();
        siteSignOutLink.addBeforeSignOut(getBeforeSavingListener());
    }

    private void addContentActions() {
        ActionToolbarMenuDescriptor<Object> rename = new ActionToolbarMenuDescriptor<Object>(AccessRolDTO.Editor,
                RTEditor.TOPBAR, new Listener0() {
                    public void onEvent() {
                        entityTitle.edit();
                        // basicEditor.setFocus(false);
                    }
                });
        rename.setParentMenuTitle(fileMenuTitle);
        rename.setShortcut(new ShortcutDescriptor(false, Keyboard.KEY_F2, i18n.tWithNT("F2", "The F2 Function key")));
        rename.setTextDescription(i18n.t("Rename"));
        basicEditor.addAction(rename);
    }

    private void adjHeight(final int height) {
        int barHeight = sndbar.getOffsetHeight();
        int newHeight = height - 20 - barHeight;
        // Log.debug("Sndbar height: " + barHeight + " new height: " +
        // newHeight);
        editorPanel.adjustSize(newHeight);
        vp.setCellHeight(editorPanel, "" + newHeight);
    }
}
