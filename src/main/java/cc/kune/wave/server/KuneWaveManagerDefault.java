package cc.kune.wave.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.robots.OperationContextImpl;
import org.waveprotocol.box.server.robots.OperationServiceRegistry;
import org.waveprotocol.box.server.robots.util.ConversationUtil;
import org.waveprotocol.box.server.robots.util.OperationUtil;
import org.waveprotocol.box.server.waveserver.WaveletProvider;
import org.waveprotocol.box.server.waveserver.WaveletProvider.SubmitRequestListener;
import org.waveprotocol.wave.model.id.InvalidIdException;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.wave.api.ApiIdSerializer;
import com.google.wave.api.Blip;
import com.google.wave.api.BlipData;
import com.google.wave.api.BlipThread;
import com.google.wave.api.JsonRpcConstant.ParamsProperty;
import com.google.wave.api.JsonRpcResponse;
import com.google.wave.api.OperationQueue;
import com.google.wave.api.OperationRequest;
import com.google.wave.api.ProtocolVersion;
import com.google.wave.api.Wavelet;
import com.google.wave.api.data.converter.EventDataConverterManager;
import com.google.wave.api.impl.WaveletData;

public class KuneWaveManagerDefault implements KuneWaveManager {
    public static final Log LOG = LogFactory.getLog(KuneWaveManagerDefault.class);

    private static final String NO_TITLE = "";

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
    public void addParticipant(final WaveRef waveName, final String author, final String userWhoAdds,
            final String participant) {
        final Wavelet wavelet = fetchWavelet(waveName, author);
        final String whoAdd = wavelet.getParticipants().contains(participantUtils.of(userWhoAdds)) ? userWhoAdds
                : author;
        final OperationQueue opQueue = new OperationQueue();
        opQueue.addParticipantToWavelet(wavelet, participantUtils.of(participant).toString());
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        final OperationRequest request = opQueue.getPendingOperations().get(0);
        OperationUtil.executeOperation(request, operationRegistry, context, participantUtils.of(whoAdd));
        final String reqId = request.getId();
        final JsonRpcResponse response = context.getResponse(reqId);
        if (response != null && response.isError()) {
            onFailure(context.getResponse(reqId).getErrorMessage());
        }
        OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
            @Override
            public void onFailure(final String arg0) {
                KuneWaveManagerDefault.this.onFailure("Wave add participant failed, onFailure: " + arg0);
            }

            @Override
            public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
                LOG.info("Wave addParticipant success: " + arg1);
            }
        });
    }

    @Override
    public WaveRef createWave(final String message, final ParticipantId participant) {
        return createWave(NO_TITLE, message, participant);
    }

    @Override
    public WaveRef createWave(@Nonnull final String title, final String message,
            @Nonnull final ParticipantId... participantsArray) {
        String newWaveId = null;
        String newWaveletId = null;
        final Set<String> participants = new HashSet<String>();
        for (final ParticipantId participant : participantsArray) {
            participants.add(participant.toString());
        }
        final ParticipantId user = participantsArray[0];
        final OperationQueue opQueue = new OperationQueue();
        final Wavelet newWavelet = opQueue.createWavelet(domain, participants);
        opQueue.setTitleOfWavelet(newWavelet, title);
        final Blip rootBlip = newWavelet.getRootBlip();
        rootBlip.append(new com.google.wave.api.Markup(message).getText());
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        for (final OperationRequest req : opQueue.getPendingOperations()) {
            OperationUtil.executeOperation(req, operationRegistry, context, user);
            final String reqId = req.getId();
            final JsonRpcResponse response = context.getResponse(reqId);
            if (response != null) {
                if (response.isError()) {
                    onFailure(context.getResponse(reqId).getErrorMessage());
                } else {
                    final Object responseWaveId = response.getData().get(ParamsProperty.WAVE_ID);
                    final Object responseWaveletId = response.getData().get(ParamsProperty.WAVELET_ID);
                    if (responseWaveId != null && responseWaveletId != null) {
                        // This is serialized use
                        // ApiIdSerializer.instance().deserialiseWaveId (see
                        // WaveService)
                        newWaveId = (String) responseWaveId;
                        newWaveletId = (String) responseWaveletId;
                    }
                }
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
        LOG.info("WaveId: " + newWaveId + " waveletId: " + newWaveletId);
        WaveRef wavename;
        try {
            wavename = WaveRef.of(ApiIdSerializer.instance().deserialiseWaveId(newWaveId),
                    ApiIdSerializer.instance().deserialiseWaveletId(newWaveletId));
        } catch (final InvalidIdException e) {
            throw new DefaultException("Error getting wave id");
        }
        return wavename;
    }

    public void doOperations(final WaveRef waveName, final String author, final OperationQueue opQueue,
            final SubmitRequestListener listener) {
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        for (final OperationRequest req : opQueue.getPendingOperations()) {
            OperationUtil.executeOperation(req, operationRegistry, context, participantUtils.of(author));
        }
        OperationUtil.submitDeltas(context, waveletProvider, listener);
    }

    @Override
    public Wavelet fetchWavelet(final WaveRef waveName, final String author) {
        Wavelet wavelet = null;
        final OperationQueue opQueue = new OperationQueue();
        opQueue.fetchWavelet(waveName.getWaveId(), waveName.getWaveletId());
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        final OperationRequest request = opQueue.getPendingOperations().get(0);
        OperationUtil.executeOperation(request, operationRegistry, context, participantUtils.of(author));
        final String reqId = request.getId();
        final JsonRpcResponse response = context.getResponse(reqId);
        if (response != null && response.isError()) {
            onFailure(context.getResponse(reqId).getErrorMessage());
        } else {
            // Duplicate code from WaveService
            final WaveletData waveletData = (WaveletData) response.getData().get(ParamsProperty.WAVELET_DATA);
            final Map<String, Blip> blips = new HashMap<String, Blip>();
            final Map<String, BlipThread> threads = new HashMap<String, BlipThread>();
            wavelet = Wavelet.deserialize(opQueue, blips, threads, waveletData);

            // Deserialize threads.
            @SuppressWarnings("unchecked")
            final Map<String, BlipThread> tempThreads = (Map<String, BlipThread>) response.getData().get(
                    ParamsProperty.THREADS);
            for (final Map.Entry<String, BlipThread> entry : tempThreads.entrySet()) {
                final BlipThread thread = entry.getValue();
                threads.put(entry.getKey(), new BlipThread(thread.getId(), thread.getLocation(), thread.getBlipIds(),
                        blips));
            }

            // Deserialize blips.
            @SuppressWarnings("unchecked")
            final Map<String, BlipData> blipDatas = (Map<String, BlipData>) response.getData().get(ParamsProperty.BLIPS);
            for (final Map.Entry<String, BlipData> entry : blipDatas.entrySet()) {
                blips.put(entry.getKey(), Blip.deserialize(opQueue, wavelet, entry.getValue()));
            }
        }
        return wavelet;
    }

    @Override
    public boolean isParticipant(final Wavelet wavelet, final String user) {
        return wavelet.getParticipants().contains(participantUtils.of(user));
    }

    private void onFailure(final String message) {
        final String errorMsg = TextUtils.notEmpty(message) ? message : "Wave operation failed";
        LOG.error(errorMsg);
        throw new DefaultException(errorMsg);
    }

    @Override
    public void setTitle(final WaveRef waveName, final String title, final String author) {
        final Wavelet wavelet = fetchWavelet(waveName, author);
        final OperationQueue opQueue = new OperationQueue();
        opQueue.setTitleOfWavelet(wavelet, title);
        final OperationContextImpl context = new OperationContextImpl(waveletProvider,
                converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
        final OperationRequest request = opQueue.getPendingOperations().get(0);
        OperationUtil.executeOperation(request, operationRegistry, context, participantUtils.of(author));
        final String reqId = request.getId();
        final JsonRpcResponse response = context.getResponse(reqId);
        if (response != null && response.isError()) {
            onFailure(context.getResponse(reqId).getErrorMessage());
        }
        OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
            @Override
            public void onFailure(final String arg0) {
                KuneWaveManagerDefault.this.onFailure("Wave set title failed, onFailure: " + arg0);
            }

            @Override
            public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
                LOG.info("Wave set title success: " + arg1);
            }
        });
    }

}
