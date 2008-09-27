package org.ourproject.kune.platf.server.manager;

import java.io.File;
import java.io.IOException;

public interface FileManager {

    /**
     * Create a new file adding a numeric sequence if the file already exists
     * 
     * @param dir
     *                the directory (use File.separator for dir delimiters)
     * @param fileName
     *                (the new file name)
     * @return if 'file.txt' exists it creates 'file 1.txt' and if 'file 1.txt'
     *         exists it creates 'file 2.txt' and so on
     * @throws IOException
     */
    File createFileWithSequentialName(String dir, String fileName) throws IOException;

    /**
     * @param dir
     *                the directory (use File.separator for dir delimiters)
     * @return true if and only if the directory was created, along with all
     *         necessary parent directories; false otherwise
     */
    boolean mkdir(String dir);

    /**
     * @param dir
     *                the directory (use File.separator for dir delimiters)
     */
    void rmdir(String dir);

}
