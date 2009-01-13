package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public abstract class FoldableContentPresenter extends AbstractContentPresenter implements FoldableContent {

    private final String toolName;
    private final ActionRegistry<StateToken> actionRegistry;
    protected final Session session;
    private final ActionContentToolbar toolbar;
    private final Provider<FileDownloadUtils> downloadProvider;
    private final I18nTranslationService i18n;

    public FoldableContentPresenter(final String toolName, StateManager stateManager, Session session,
            final ActionContentToolbar toolbar, ActionRegistry<StateToken> actionRegistry,
            Provider<FileDownloadUtils> downloadProvider, I18nTranslationService i18n) {
        this.toolName = toolName;
        this.session = session;
        this.toolbar = toolbar;
        this.actionRegistry = actionRegistry;
        this.downloadProvider = downloadProvider;
        this.i18n = i18n;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
    }

    public String getToolName() {
        return toolName;
    }

    public void refreshState() {
        setState(session.getContentState());
    }

    protected void setContent(StateContentDTO state, String uploadedfileType) {
        String typeId = state.getTypeId();
        String contentBody = state.getContent();
        StateToken token = state.getStateToken();
        BasicMimeTypeDTO mimeType = state.getMimeType();
        if (typeId.equals(uploadedfileType)) {
            if (mimeType != null) {
                FileDownloadUtils fileDownloadUtils = downloadProvider.get();
                if (mimeType.isImage()) {
                    view.showImage(fileDownloadUtils.getImageUrl(token), fileDownloadUtils.getImageResizedUrl(token,
                            ImageSize.sized), false);
                } else if (mimeType.isPdf()) {
                    view.showImage(fileDownloadUtils.getImageUrl(token), fileDownloadUtils.getImageResizedUrl(token,
                            ImageSize.sized), true);
                } else if (mimeType.isText()) {
                    view.setContent(contentBody, true);
                } else {
                    view.setNoPreview();
                }
            } else {
                view.setNoPreview();
            }
        } else {
            if ((contentBody == null || contentBody.length() == 0)) {
                if (state.getContentRights().isEditable()) {
                    view.setInfoMessage(i18n.t("There is no text in this page. Feel free to edit this page"));
                } else {
                    view.setInfoMessage(i18n.t("There is no text in this page"));
                }
            } else {
                view.setRawContent(contentBody);
            }
        }
        view.attach();
    }

    protected void setState(StateAbstractDTO state) {
        toolbar.detach();
        if (state instanceof StateContainerDTO) {
            StateContainerDTO stateCntCtx = (StateContainerDTO) state;
            if (stateCntCtx.getToolName().equals(toolName)) {
                // This tool
                if (stateCntCtx instanceof StateContentDTO) {
                    setState((StateContentDTO) stateCntCtx);
                } else if (stateCntCtx instanceof StateContainerDTO) {
                    setState(stateCntCtx);
                }
            }
        }
    }

    protected void setState(StateContainerDTO state) {
        ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContainerRights());
        setToolbar(collection);
        attach();
    }

    protected void setState(StateContentDTO state) {
        ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContentRights());
        setToolbar(collection);
    }

    private ActionItemCollection<StateToken> getActionCollection(StateContainerDTO state, AccessRightsDTO rights) {
        return actionRegistry.getCurrentActions(state.getStateToken(), state.getTypeId(), session.isLogged(), rights,
                true);
    }

    private void setToolbar(ActionItemCollection<StateToken> collection) {
        toolbar.disableMenusAndClearButtons();
        toolbar.setActions(collection);
        toolbar.attach();
    }

}
