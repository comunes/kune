package cc.kune.wave.server;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.robots.OperationContextImpl;
import org.waveprotocol.box.server.robots.OperationServiceRegistry;
import org.waveprotocol.box.server.robots.util.ConversationUtil;
import org.waveprotocol.box.server.robots.util.OperationUtil;
import org.waveprotocol.box.server.waveserver.WaveletProvider;
import org.waveprotocol.box.server.waveserver.WaveletProvider.SubmitRequestListener;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.ParticipantId;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.wave.api.Markup;
import com.google.wave.api.OperationQueue;
import com.google.wave.api.OperationRequest;
import com.google.wave.api.ProtocolVersion;
import com.google.wave.api.Wavelet;
import com.google.wave.api.data.converter.EventDataConverterManager;

public class KuneWaveManagerDefault implements KuneWaveManager {
    public static final Log LOG = LogFactory.getLog(KuneWaveManagerDefault.class);

    private final ConversationUtil conversationUtil;
    private final EventDataConverterManager converterManager;
    private final String domain;
    private final OperationServiceRegistry operationRegistry;
    private final ParticipantUtils participantUtils;

    private final WaveletProvider waveletProvider;

    @Inject
    public KuneWaveManagerDefault(final EventDataConverterManager converterManager,
            @Named("DataApiRegistry") final OperationServiceRegistry operationRegistry,
            final WaveletProvider waveletProvider, final ConversationUtil conversationUtil,
            final ParticipantUtils participantUtils, @Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
        this.converterManager = converterManager;
        this.waveletProvider = waveletProvider;
        this.conversationUtil = conversationUtil;
        this.operationRegistry = operationRegistry;
        this.participantUtils = participantUtils;
        this.domain = domain;
    }

    @Override
    public void createWave(final String username, final String message) {
        final ParticipantId user = participantUtils.of(username);
        final OperationQueue opQueue = new OperationQueue();
        final Set<String> users = new HashSet<String>();
        users.add(user.toString());
        final Wavelet newWavelet = opQueue.createWavelet(domain, users, message);
        final Markup markup = Markup.of(message);
        opQueue.createChildOfBlip(newWavelet.getRootBlip());
        opQueue.appendBlipToWavelet(newWavelet, message);
        opQueue.appendBlipToWavelet(newWavelet, message);
        assert newWavelet.getRootBlip() != null;
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        for (final OperationRequest req : opQueue.getPendingOperations()) {
            OperationUtil.executeOperation(req, operationRegistry, context, user);
            final String reqId = req.getId();
            if (context.getResponse(reqId).isError()) {
                LOG.error(context.getResponse(reqId).getErrorMessage());
            }
            OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
                @Override
                public void onFailure(final String arg0) {
                    LOG.error("Wave creation failed: " + arg0);
                }

                @Override
                public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
                }
            });
        }
    }
}
