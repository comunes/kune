/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.manager.file;

import java.io.File;
import java.io.IOException;

import org.ourproject.kune.platf.server.manager.FileManager;

import com.google.inject.Singleton;

@Singleton
public class FileManagerDefault implements FileManager {

    public File createFileWithSequentialName(final String dir, final String fileName) throws IOException {
        String fileNameProposal = String.valueOf(fileName);
        File file = new File(dir, fileNameProposal);
        while (file.exists()) {
            fileNameProposal = FileUtils.getNextSequentialFileName(fileNameProposal, true);
            // @PMD:REVIEWED:AvoidInstantiatingObjectsInLoops: by vjrj on
            // 21/05/09 13:15
            file = new File(dir, fileNameProposal);
        }
        if (file.createNewFile()) {
            return file;
        } else {
            throw new IOException("Cannot create sequential file name");
        }
    }

    public boolean mkdir(final String dir) {
        return new File(dir).mkdirs();
    }

    public boolean rmdir(final String dir) throws IOException {
        final File file = new File(dir);
        if (!file.isDirectory()) {
            throw new IOException("rmdir: " + dir + ": Not a directory");
        }
        if (file.listFiles().length != 0) {
            throw new IOException("rmdir: " + dir + ": Directory not empty");
        }
        return file.delete();
    }
}
