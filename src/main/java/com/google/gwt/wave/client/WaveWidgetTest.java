package com.google.gwt.wave.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.wave.client.event.WaveLoadEvent;
import com.google.gwt.wave.client.event.WaveLoadHandler;

public class WaveWidgetTest implements EntryPoint {

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void onUncaughtException(final Throwable e) {
                Window.alert("Uncaught exception: " + e.getMessage());
            }
        });
        final WaveWidget ww = new WaveWidget("http://wave.google.com/a/wavesandbox.com/");
        ww.setUIConfig("#333", "#ccc", "sans", "12pt");

        RootPanel.get().add(ww);
        ww.addWaveLoadHandler(new WaveLoadHandler() {
            public void onWaveLoad(final WaveLoadEvent event) {
                Window.alert("Wave '" + event.getWaveId() + "' loaded!");
            }
        });
        ww.loadWave("???");
    }

}
