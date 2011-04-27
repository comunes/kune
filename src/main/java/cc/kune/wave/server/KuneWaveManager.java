package cc.kune.wave.server;

import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.wave.api.Wavelet;

public interface KuneWaveManager {

    WaveRef createWave(String message, ParticipantId participants);

    WaveRef createWave(String title, String message, ParticipantId... participantsArray);

    Wavelet fetchWavelet(WaveRef waveRef, String author);

}
