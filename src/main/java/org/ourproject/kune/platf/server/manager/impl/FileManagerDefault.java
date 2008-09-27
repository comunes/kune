package org.ourproject.kune.platf.server.manager.impl;

import java.io.File;
import java.io.IOException;

import org.ourproject.kune.platf.server.manager.FileManager;

import com.google.inject.Singleton;

@Singleton
public class FileManagerDefault implements FileManager {

    public File createFileWithSequentialName(final String dir, final String fileName) throws IOException {
	String fileNameProposal = new String(fileName);
	File file = new File(dir, fileNameProposal);
	while (file.exists()) {
	    fileNameProposal = FileUtils.getNextSequentialFileName(fileNameProposal, true);
	    file = new File(dir, fileNameProposal);
	}
	file.createNewFile();
	return file;
    }

    public boolean mkdir(final String dir) {
	return (new File(dir)).mkdirs();
    }

    public void rmdir(final String dir) {
	final File file = new File(dir);
	if (!file.isDirectory()) {
	    throw new RuntimeException("rmdir: " + dir + ": Not a directory");
	}
	if (file.listFiles().length != 0) {
	    throw new RuntimeException("rmdir: " + dir + ": Directory not empty");
	}
	file.delete();
    }
}
