package org.ourproject.kune.platf.server.manager.file;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;

public class FileDownloadManagerTest {

    private static final String SOMETITLE = "Sometitle";

    @Inject
    FileDownloadManager fileDownloadManager;

    @Inject
    KuneProperties kuneProperties;

    private HttpServletResponse resp;
    private Content content;

    private String uploadLocation;

    private FileUtils fileUtils;

    private StateToken stateToken;

    private String filename;

    @Before
    public void create() throws IOException {
        new IntegrationTestHelper(this);
        content = Mockito.mock(Content.class);
        resp = Mockito.mock(HttpServletResponse.class);
        ServletOutputStream oStream = Mockito.mock(ServletOutputStream.class);

        Mockito.stub(resp.getOutputStream()).toReturn(oStream);
        uploadLocation = kuneProperties.get(KuneProperties.UPLOAD_LOCATION);

        fileUtils = Mockito.mock(FileUtils.class);
        Mockito.stub(fileUtils.exist(Mockito.anyString())).toReturn(true);
        stateToken = new StateToken("test.test.1.1");
        filename = "somefile";

        Mockito.stub(content.getTitle()).toReturn(SOMETITLE);
    }

    @Test
    public void fileWithNoExtensionDownloadTest() throws Exception {
        throw new Exception("TODO");
    }

    @Test
    public void testJpgThumbDownload() throws IOException {
        String extension = ".jpg";
        contentStub(new BasicMimeType("image", "jpg"), filename, extension);
        String subExt = ImageSize.thumb.toString();
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + extension, absFile);
        Mockito.verify(resp).setContentType("image/jpg");
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPdfDownload1() throws IOException {
        String extension = ".pdf";
        contentStub(new BasicMimeType("application", "pdf"), filename, extension);
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "true", ImageSize.ico.toString(), resp,
                fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
        Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPdfDownload2() throws IOException {
        String pngExtension = ".png";
        contentStub(new BasicMimeType("application", "pdf"), filename, ".pdf");
        String subExt = ImageSize.ico.toString();
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension, absFile);
        Mockito.verify(resp).setContentType("image/png");
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPdfDownload3() throws IOException {
        String pngExtension = ".png";
        contentStub(new BasicMimeType("application", "pdf"), filename, ".pdf");
        String subExt = ImageSize.sized.toString();
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension, absFile);
        Mockito.verify(resp).setContentType("image/png");
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPdfDownload4() throws IOException {
        String pngExtension = ".png";
        contentStub(new BasicMimeType("application", "pdf"), filename, ".pdf");
        String subExt = ImageSize.thumb.toString();
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension, absFile);
        Mockito.verify(resp).setContentType("image/png");
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPdfDownloadNullMime() throws IOException {
        String extension = ".pdf";
        contentStub(null, filename, extension);
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "true", ImageSize.ico.toString(), resp,
                fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
        Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPngDownload1() throws IOException {
        String extension = ".png";
        contentStub(new BasicMimeType("image", "png"), filename, extension);
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "true", null, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
        Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    @Test
    public void testPngDownload2() throws IOException {
        String extension = ".png";
        contentStub(new BasicMimeType("image", "png"), filename, extension);
        String subExt = ImageSize.thumb.toString();
        String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp, fileUtils);
        assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + extension, absFile);
        Mockito.verify(resp).setContentType("image/png");
        Mockito.verify(resp).setHeader(
                FileDownloadManager.RESP_HEADER_CONTEND_DISP,
                FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
                        + FileDownloadManager.RESP_HEADER_END);
    }

    private void contentStub(BasicMimeType mimeType, String filename, String extension) {
        Mockito.stub(content.getMimeType()).toReturn(mimeType);
        Mockito.stub(content.getFilename()).toReturn(filename + extension);
    }
}
