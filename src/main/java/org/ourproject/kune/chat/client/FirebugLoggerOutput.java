package org.ourproject.kune.chat.client;

import to.tipit.gwtlib.FireLog;

import com.calclab.gwtjsjac.client.log.LoggerOuput;

public class FirebugLoggerOutput implements LoggerOuput {

    public void log(final String text) {
	FireLog.debug(text);
    }

}
