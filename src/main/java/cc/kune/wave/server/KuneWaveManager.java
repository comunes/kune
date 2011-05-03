package cc.kune.wave.server;

import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.wave.api.Wavelet;

public interface KuneWaveManager {

    void addParticipant(WaveRef waveName, String author, String userWhoAdd, String newParticipant);

    WaveRef createWave(String message, ParticipantId participants);

    WaveRef createWave(String title, String message, ParticipantId... participantsArray);

    Wavelet fetchWavelet(WaveRef waveRef, String author);

    boolean isParticipant(Wavelet wavelet, String user);

    void setTitle(WaveRef waveName, String title, String author);

}
