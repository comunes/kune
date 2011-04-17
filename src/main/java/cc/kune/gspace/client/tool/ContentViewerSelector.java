package cc.kune.gspace.client.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentViewerSelector {

    private final HashMap<String, ContentViewer> defViewsRegister;
    private final Session session;
    private final StateManager stateManager;
    private final HashMap<String, List<ContentViewer>> viewsRegister;

    @Inject
    public ContentViewerSelector(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
        viewsRegister = new HashMap<String, List<ContentViewer>>();
        defViewsRegister = new HashMap<String, ContentViewer>();
    }

    public void init() {
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                stateManager.onStateChanged(true, new StateChangedHandler() {
                    @Override
                    public void onStateChanged(final StateChangedEvent event) {
                        final StateAbstractDTO state = event.getState();
                        if (state instanceof StateContentDTO || state instanceof StateContainerDTO) {
                            setContent((HasContent) state);
                        } else {
                            // NoContent
                        }
                    }
                });
            }
        });
    }

    public void register(final String typeId, final ContentViewer view) {
        Log.info("Registered " + typeId + " with class " + ContentViewer.class);
        register(typeId, view, false);
    }

    public void register(@Nonnull final String typeId, @Nonnull final ContentViewer view, final boolean isDefault) {
        List<ContentViewer> list = viewsRegister.get(typeId);
        if (list == null) {
            list = new ArrayList<ContentViewer>();
        }
        if (!list.contains(view)) {
            list.add(view);
        }
        if (isDefault) {
            defViewsRegister.put(typeId, view);
        }
    }

    private void setContent(final ContentViewer view, final HasContent state) {
        view.setContent(state);
        view.attach();
    }

    public void setContent(@Nonnull final HasContent state) {
        final String typeId = state.getTypeId();
        assert typeId != null;
        final ContentViewer defView = defViewsRegister.get(typeId);
        if (defView == null) {
            final List<ContentViewer> viewsList = viewsRegister.get(typeId);
            if (viewsList != null && !viewsList.isEmpty()) {
                setContent(viewsList.get(0), state);
            } else {
                throw new UIException("Unsupported typeId: " + typeId);
            }
        } else {
            setContent(defView, state);
        }
    }
}
