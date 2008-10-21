package org.ourproject.kune.platf.server.manager.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;

public abstract class FileDownloadManagerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void returnFile(final String filename, final OutputStream out) throws FileNotFoundException, IOException {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filename));
            final byte[] buf = new byte[4 * 1024]; // 4K buffer
            int bytesRead;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
