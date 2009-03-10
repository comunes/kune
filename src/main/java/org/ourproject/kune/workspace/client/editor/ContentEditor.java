package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.SimpleToolbar;
import org.ourproject.kune.platf.client.ui.rte.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class ContentEditor extends RTESavingEditorPresenter {

    private final WorkspaceSkeleton ws;
    private final VerticalPanel vp;
    private final RTEditor basicEditor;
    private final RTEditorPanel editorPanel;
    private final SimpleToolbar topbar;

    public ContentEditor(RTEditor editor, boolean autoSave, I18nTranslationService i18n, StateManager stateManager,
            SiteSignOutLink siteSignOutLink, DeferredCommandWrapper deferredCommandWrapper,
            RTEImgResources imgResources, WorkspaceSkeleton ws) {
        super(editor, autoSave, i18n, stateManager, siteSignOutLink, deferredCommandWrapper, imgResources);
        this.ws = ws;

        vp = new VerticalPanel();
        basicEditor = super.getBasicEditor();
        vp.add(((ActionToolbarPanel<Object>) basicEditor.getSndBar().getView()).getToolbar());
        editorPanel = (RTEditorPanel) basicEditor.getEditorArea();
        vp.add(editorPanel.getRTE());
        basicEditor.setExtended(true);
        vp.setWidth("100%");
        adjHeight(ws.getEntityWorkspace().getContentHeight());
        ws.getEntityWorkspace().addContentListener(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                adjHeight(adjHeight);
            }
        });
        topbar = ((ActionToolbarPanel<Object>) basicEditor.getTopBar().getView()).getToolbar();
    }

    @Override
    public void edit(String html, Listener<String> onSave, Listener0 onEditCancelled) {
        Toolbar contentTopBar = ws.getEntityWorkspace().getContentTopBar();
        contentTopBar.removeAll();
        contentTopBar.add(topbar);
        ws.getEntityWorkspace().setContent(vp);
        super.edit(html, onSave, onEditCancelled);
    }

    @Override
    protected void onCancelConfirmed() {
        ws.getEntityWorkspace().clearContent();
        super.onCancelConfirmed();
    }

    private void adjHeight(final int height) {
        int newHeight = height - WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT - 27;
        editorPanel.adjustSize(newHeight);
        vp.setCellHeight(editorPanel.getRTE(), "" + newHeight);
    }
}
