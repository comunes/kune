/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
 */
package cc.kune.core.server.manager.file;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.services.ImageSize;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Content;

import com.google.inject.Inject;

public class FileDownloadManagerTest {

  private static final String SOMETITLE = "Sometitle";

  private Content content;

  @Inject
  FileDownloadManager fileDownloadManager;

  private String filename;
  private FileUtils fileUtils;

  @Inject
  KuneProperties kuneProperties;

  private HttpServletResponse resp;

  private StateToken stateToken;

  private String uploadLocation;

  private void contentwhen(final BasicMimeType mimeType, final String filename, final String extension) {
    Mockito.when(content.getMimeType()).thenReturn(mimeType);
    Mockito.when(content.getFilename()).thenReturn(filename + extension);
  }

  @Before
  public void create() throws IOException {
    new IntegrationTestHelper(true, this);
    content = Mockito.mock(Content.class);
    resp = Mockito.mock(HttpServletResponse.class);
    final ServletOutputStream oStream = Mockito.mock(ServletOutputStream.class);

    Mockito.when(resp.getOutputStream()).thenReturn(oStream);
    uploadLocation = kuneProperties.get(KuneProperties.UPLOAD_LOCATION);

    fileUtils = Mockito.mock(FileUtils.class);
    Mockito.when(fileUtils.exist(Mockito.anyString())).thenReturn(true);
    stateToken = new StateToken("test.test.1.1");
    filename = "somefile";

    Mockito.when(content.getTitle()).thenReturn(SOMETITLE);
  }

  @Ignore
  public void fileWithNoExtensionDownloadTest() throws Exception {
    throw new Exception("TODO");
  }

  @Test
  public void testJpgThumbDownload() throws IOException {
    final String extension = ".jpg";
    contentwhen(new BasicMimeType("image", "jpg"), filename, extension);
    final String subExt = ImageSize.thumb.toString();
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + extension,
        absFile);
    Mockito.verify(resp).setContentType("image/jpg");
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPdfDownload1() throws IOException {
    final String extension = ".pdf";
    contentwhen(new BasicMimeType("application", "pdf"), filename, extension);
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "true",
        ImageSize.ico.toString(), resp, fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
    Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPdfDownload2() throws IOException {
    final String pngExtension = ".png";
    contentwhen(new BasicMimeType("application", "pdf"), filename, ".pdf");
    final String subExt = ImageSize.ico.toString();
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension,
        absFile);
    Mockito.verify(resp).setContentType("image/png");
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPdfDownload3() throws IOException {
    final String pngExtension = ".png";
    contentwhen(new BasicMimeType("application", "pdf"), filename, ".pdf");
    final String subExt = ImageSize.sized.toString();
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension,
        absFile);
    Mockito.verify(resp).setContentType("image/png");
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPdfDownload4() throws IOException {
    final String pngExtension = ".png";
    contentwhen(new BasicMimeType("application", "pdf"), filename, ".pdf");
    final String subExt = ImageSize.thumb.toString();
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + pngExtension,
        absFile);
    Mockito.verify(resp).setContentType("image/png");
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + pngExtension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPdfDownloadNullMime() throws IOException {
    final String extension = ".pdf";
    contentwhen(null, filename, extension);
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "true",
        ImageSize.ico.toString(), resp, fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
    Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPngDownload1() throws IOException {
    final String extension = ".png";
    contentwhen(new BasicMimeType("image", "png"), filename, extension);
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "true", null, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + extension, absFile);
    Mockito.verify(resp).setContentType(FileDownloadManager.APPLICATION_X_DOWNLOAD);
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
            + FileDownloadManager.RESP_HEADER_END);
  }

  @Test
  public void testPngDownload2() throws IOException {
    final String extension = ".png";
    contentwhen(new BasicMimeType("image", "png"), filename, extension);
    final String subExt = ImageSize.thumb.toString();
    final String absFile = fileDownloadManager.buildResponse(content, stateToken, "false", subExt, resp,
        fileUtils);
    assertEquals(uploadLocation + FileUtils.toDir(stateToken) + filename + "." + subExt + extension,
        absFile);
    Mockito.verify(resp).setContentType("image/png");
    Mockito.verify(resp).setHeader(
        FileDownloadManager.RESP_HEADER_CONTEND_DISP,
        FileDownloadManager.RESP_HEADER_ATTACHMENT_FILENAME + SOMETITLE + extension
            + FileDownloadManager.RESP_HEADER_END);
  }
}
