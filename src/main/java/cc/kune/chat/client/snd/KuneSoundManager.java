/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
