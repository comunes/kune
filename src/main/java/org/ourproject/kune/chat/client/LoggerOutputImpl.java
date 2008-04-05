package org.ourproject.kune.chat.client;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.gwtjsjac.client.log.LoggerOuput;

public class LoggerOutputImpl implements LoggerOuput {

    public void log(final String text) {
        Log.debug(text);
    }

}
