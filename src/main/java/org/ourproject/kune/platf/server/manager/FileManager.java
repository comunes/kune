/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package org.ourproject.kune.platf.server.manager;

import java.io.File;
import java.io.IOException;

public interface FileManager {

    /**
     * Create a new file adding a numeric sequence if the file already exists
     * 
     * @param dir
     *            the directory (use File.separator for dir delimiters)
     * @param fileName
     *            (the new file name)
     * @return if 'file.txt' exists it creates 'file 1.txt' and if 'file 1.txt'
     *         exists it creates 'file 2.txt' and so on
     * @throws IOException
     */
    File createFileWithSequentialName(String dir, String fileName) throws IOException;

    /**
     * @param dir
     *            the directory (use File.separator for dir delimiters)
     * @return true if and only if the directory was created, along with all
     *         necessary parent directories; false otherwise
     */
    boolean mkdir(String dir);

    /**
     * @param dir
     *            the directory (use File.separator for dir delimiters)
     */
    boolean rmdir(String dir) throws IOException;

}
