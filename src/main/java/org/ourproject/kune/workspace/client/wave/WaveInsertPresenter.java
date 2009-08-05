package org.ourproject.kune.workspace.client.wave;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.ioc.Provider;

public class WaveInsertPresenter implements WaveInsert {

    private WaveInsertView view;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<ContentServiceAsync> contentService;
    private final I18nTranslationService i18n;
    private StateToken parentToken;

    public WaveInsertPresenter(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.contentService = contentService;
    }

    public View getView() {
        return view;
    }

    public void init(final WaveInsertView view) {
        this.view = view;
    }

    public void onAdd() {
        final String waveId = view.getWaveId();
        if (waveId.length() > 0) {
            addWave(waveId);
        } else {
            NotifyUser.error(i18n.t("The wave id is incorrect"));
            resetAndHide();
        }
    }

    public void onCancel() {
        resetAndHide();
    }

    public void show(final StateToken parentToken) {
        this.parentToken = parentToken;
        view.show();
    }

    /**
     * 
     * Add a wave to the current location
     * 
     * @param waveId
     *            something like "wavesandbox.com!w+NdlzA9PU%B"
     */
    private void addWave(final String waveId) {
        assert parentToken != null;
        session.getContentState().getContainer().getStateToken();
        contentService.get().addWave(session.getUserHash(), parentToken, DocumentClientTool.TYPE_WAVE, waveId,
                new AsyncCallbackSimple<StateContentDTO>() {
                    public void onSuccess(final StateContentDTO state) {
                        stateManager.setRetrievedState(state);
                    }
                });
        resetAndHide();
    }

    private void resetAndHide() {
        view.hide();
        view.reset();
    }
}
