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

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.wave.api.JsonRpcResponse;
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
        final Set<String> participants = new HashSet<String>();
        participants.add(user.toString());
        final Wavelet newWavelet = opQueue.createWavelet(domain, participants);
        // newWavelet.getRootBlip().append(Markup.of(message));
        opQueue.appendMarkupToDocument(newWavelet.getRootBlip(), Markup.of(message).getMarkup());

        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);

        for (final OperationRequest req : opQueue.getPendingOperations()) {
            OperationUtil.executeOperation(req, operationRegistry, context, user);
            final String reqId = req.getId();
            final JsonRpcResponse response = context.getResponse(reqId);
            if (response != null && response.isError()) {
                onFailure(context.getResponse(reqId).getErrorMessage());
            }
        }
        OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
            @Override
            public void onFailure(final String arg0) {
                KuneWaveManagerDefault.this.onFailure("Wave creation failed, onFailure: " + arg0);
            }

            @Override
            public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
                LOG.info("Wave creation success: " + arg1);
            }
        });
    }

    private void onFailure(final String message) {
        final String errorMsg = TextUtils.notEmpty(message) ? message : "Wave operation failed";
        LOG.error(errorMsg);
        throw new DefaultException(errorMsg);
    }
}
