package org.ourproject.kune.platf.server.manager.impl;

import java.io.File;

import org.ourproject.kune.platf.client.dto.StateToken;

public class FileUtils {

    public static final String SLASH = File.separator;

    /**
     * For filename extension info see:
     * http://en.wikipedia.org/wiki/File_name_extension
     * 
     * @param filename
     * @return
     */
    public static String getFileNameExtension(final String filename, final boolean withDot) {
        // also we can use FilenameUtils
        if (filename == null) {
            return "";
        }
        final int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == 0) {
            return "";
        } else {
            if (withDot) {
                final String ext = filename.substring(dotIndex);
                return ext.length() == 1 ? "" : ext;
            } else {
                return filename.substring(dotIndex + 1);
            }
        }
    }

    public static String getFileNameWithoutExtension(final String fileName, final String extension) {
        final int extlength = extension.length();
        if (extlength > 0) {
            boolean withDot = extension.charAt(0) == '.';
            return fileName.substring(0, fileName.length() - extlength - (withDot ? 0 : 1));
        } else {
            return fileName;
        }
    }

    public static String getNextSequentialFileName(final String fileName) {
        final int lastSpace = fileName.lastIndexOf(" ");
        if (lastSpace != -1) {
            final String suffix = fileName.substring(lastSpace + 1);
            try {
                final Integer i = new Integer(suffix);
                return fileName.substring(0, lastSpace + 1) + (i + 1);
            } catch (final NumberFormatException e) {
            }
        }
        return fileName + " 1";
    }

    public static String getNextSequentialFileName(final String fileName, boolean preserveExtension) {
        if (!preserveExtension) {
            return getNextSequentialFileName(fileName);
        }
        final int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == 0) {
            return getNextSequentialFileName(fileName);
        } else {
            final String ext = fileName.substring(dotIndex);
            return getNextSequentialFileName(fileName.substring(0, dotIndex)) + ext;
        }
    }

    public static String toDir(final StateToken stateToken) {
        return SLASH + stateToken.getGroup() + SLASH + stateToken.getTool() + SLASH + stateToken.getFolder() + SLASH;
    }
}
