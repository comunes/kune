package cc.kune.chat.client.snd;

import com.calclab.hablar.signals.sound.client.SoundManager;
import com.calclab.hablar.signals.sound.client.SoundSignalsConfig;
import com.google.gwt.event.shared.EventBus;

public class KuneSoundManager {
    private final SoundSignalsConfig soundConfig;
    SoundManager soundManager;

    public KuneSoundManager(final EventBus eventBus, final SoundSignalsConfig soundConfig) {
        this.soundConfig = soundConfig;
        eventBus.addHandler(SndClickEvent.getType(), new SndClickEvent.SndClickHandler() {
            @Override
            public void onClick(final SndClickEvent event) {
                createSoundManagerIfNeeded();
                soundManager.play();
            }
        });
    }

    private void createSoundManagerIfNeeded() {
        if (soundManager == null) {
            soundManager = new SoundManager(soundConfig);
        }
    }

}
