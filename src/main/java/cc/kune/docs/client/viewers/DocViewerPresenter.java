package cc.kune.docs.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.docs.client.DocsClientTool;
import cc.kune.docs.client.actions.DocsClientActions;
import cc.kune.gspace.client.actions.perspective.ViewActionsGroup;
import cc.kune.gspace.client.tool.ContentViewer;
import cc.kune.gspace.client.tool.ContentViewerSelector;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class DocViewerPresenter extends Presenter<DocViewerPresenter.DocViewerView, DocViewerPresenter.DocViewerProxy>
        implements ContentViewer {

    @ProxyCodeSplit
    public interface DocViewerProxy extends Proxy<DocViewerPresenter> {
    }

    public interface DocViewerView extends View {

        void attach();

        void clear();

        void detach();

        void setActions(GuiActionDescCollection actions);

        void setContent(StateContentDTO state);
    }

    private final ActionRegistryByType actionsRegistry;
    private final Session session;

    @Inject
    public DocViewerPresenter(final EventBus eventBus, final DocViewerView view, final DocViewerProxy proxy,
            final Session session, final ContentViewerSelector viewerSelector,
            final ActionRegistryByType actionsRegistry, final DocsClientActions actions) {
        super(eventBus, view, proxy);
        this.session = session;
        this.actionsRegistry = actionsRegistry;
        viewerSelector.register(this, true, DocsClientTool.TYPE_WAVE);
    }

    @Override
    public void attach() {
        getView().attach();
    }

    @Override
    public void detach() {
        getView().detach();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @Override
    public void setContent(@Nonnull final HasContent state) {
        getView().clear();
        final StateContentDTO stateContent = (StateContentDTO) state;
        final AccessRights rights = stateContent.getContentRights();
        final GuiActionDescCollection actions = actionsRegistry.getCurrentActions(stateContent.getGroup(),
                stateContent.getTypeId(), session.isLogged(), rights, ViewActionsGroup.class);
        getView().setActions(actions);
        getView().setContent(stateContent);
    }
}