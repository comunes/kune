package org.ourproject.massmob.client;

public class WaveUtils {
    public String getWaveId() {
        final String nat = getWaveIdNative();
        return nat != null ? nat : "test";
    }

    public native String getWaveIdNative()/*-{
        //        Wave ID Gadget v1.0
        //        © Copyright 2009 Accilent® Corp.
        //        Permission granted to copy and reuse this code under the Apache License 2.0.
        //        Doc page: http://code.Google.com/p/add-ons/wiki/wave_id_gadget
        //        Feedback: 1-877-554-3210

        var amp = location.href.indexOf("&waveId=");
        var waveId = location.href.substring(amp + 8);
        amp = waveId.indexOf("&");
        var hash = waveId.indexOf("#");
        if (amp >= 0 || hash >= 0)
        waveId = amp < hash || hash < 0
        ? waveId.substring(0, amp)
        : waveId.substring(0, hash);
        return waveId;
    }-*/;
}
