package org.ourproject.kune.platf.server.utils;

import org.apache.commons.lang.StringUtils;
import org.ourproject.kune.platf.client.errors.NameNotPermittedException;

public class FilenameUtils {

    /**
     * Check filename is not empty, or '.', or '..'
     * 
     * @param filename
     */
    public static void checkBasicFilename(String filename) {
        if (filename.length() == 0 || filename.equals(".") || filename.equals("..")) {
            throw new NameNotPermittedException();
        }
    }

    public static String chomp(String filename) {
        return StringUtils.chomp(filename);
    }
}
