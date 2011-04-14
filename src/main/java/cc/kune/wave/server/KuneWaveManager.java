package cc.kune.wave.server;

import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.wave.api.Wavelet;

public interface KuneWaveManager {

    WaveRef createWave(String message, String... participants);

    Wavelet fetchWavelet(WaveRef waveRef);

}
