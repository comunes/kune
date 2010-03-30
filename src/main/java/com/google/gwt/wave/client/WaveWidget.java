package com.google.gwt.wave.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.wave.client.event.HasWaveLoadHandlers;
import com.google.gwt.wave.client.event.WaveLoadEvent;
import com.google.gwt.wave.client.event.WaveLoadHandler;

public class WaveWidget extends Widget implements HasWaveLoadHandlers {

    private static final String UICONFIG_ERROR = "setUIConfig must be called before the widget is attached.";

    private final WaveEmbed waveEmbed;

    public WaveWidget(final String waveServer) {
        setElement(Document.get().createDivElement());
        this.waveEmbed = WaveEmbed.newInstance(waveServer);
    }

    // Package protected until this is functional on the developer sandbox
    public void addParticipant() {
        waveEmbed.addParticipant();
    }

    // Package protected until this is functional on the developer sandbox
    public void addReply() {
        waveEmbed.addReply();
    }

    public HandlerRegistration addWaveLoadHandler(final WaveLoadHandler handler) {
        return addHandler(handler, WaveLoadEvent.getType());
    }

    /**
     * Loads the given wave into the WaveWidget. This method may be called at
     * any time.
     */
    public void loadWave(final String waveId) {
        waveEmbed.loadWave(this, waveId);
    }

    @Override
    public void onLoad() {
        waveEmbed.init(getElement());
    }

    public void setEditMode(final boolean value) {
        waveEmbed.setEditMode(value);
    }

    public void setToolbarVisible(final boolean value) {
        waveEmbed.setToolbarVisible(value);
    }

    /**
     * Set the UI configuration for the wave. This must be done before the panel
     * is attached to the page.
     * 
     * @param bgColor
     *            An HTML color for the background, like "#ffffff"
     * @param color
     *            A color for the text, like "#000000"
     * @param font
     *            A font style, like "sans"
     * @param fontSize
     *            The font size, which must be expressed in points, as in "12pt"
     */
    public void setUIConfig(final String bgColor, final String color, final String font, final String fontSize) {
        assert (!isAttached()) : UICONFIG_ERROR;
        waveEmbed.setUIConfig(bgColor, color, font, fontSize);
    }

}
