package org.ourproject.kune.platf.server.manager.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.manager.FileManager;

import com.google.inject.Inject;

public class FileManagerTest {

    @Inject
    FileManager fileManager;
    private String tempDir;

    @Before
    public void inject() throws IOException {
        TestHelper.inject(this);
        tempDir = File.createTempFile("temp", "txt").getParent();
    }

    @Test
    public void test3FileCreationWithSeq() throws IOException {
        final File tempFile = File.createTempFile("some file name", ".txt");
        final File newFile1 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
        final File newFile2 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
        final File newFile3 = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
        assertEquals(tempDir + File.separator + seq(seq(seq(tempFile.getName()))), newFile3.getAbsolutePath());
        newFile1.delete();
        newFile2.delete();
        newFile3.delete();
    }

    @Test
    public void testDirCreation() throws IOException {
        final String newTestDir = tempDir + File.separator + "test1" + File.separator + "test2" + File.separator;
        assertTrue(fileManager.mkdir(newTestDir));
        final File dir = new File(newTestDir);
        assertTrue(dir.exists());
        fileManager.rmdir(newTestDir);
        assertFalse(dir.exists());
    }

    @Test
    public void testFileCreationWithSeq() throws IOException {
        final File tempFile = File.createTempFile("some file name", ".txt");
        final File newFile = fileManager.createFileWithSequentialName(tempDir, tempFile.getName());
        assertEquals(tempDir + File.separator + seq(tempFile.getName()), newFile.getAbsolutePath());
        newFile.delete();
    }

    private String seq(final String file) {
        return FileUtils.getNextSequentialFileName(file, true);
    }
}
