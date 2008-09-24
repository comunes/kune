package org.ourproject.kune.platf.server.manager.impl;

public class FileUtils {

    public static String getFileNameWithoutExtension(final String fileName, final String extension) {
	final int extlength = extension.length();
	if (extlength > 0) {
	    return fileName.substring(0, fileName.length() - extlength - 1);
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
}
