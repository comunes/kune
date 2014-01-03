/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class FileDownloadManagerTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FileDownloadManagerTest {

  /** The Constant SOMETITLE. */
  private static final String SOMETITLE = "Sometitle";

  /** The content. */
  private Content content;

  /** The file download manager. */
  @Inject
  FileDownloadManager fileDownloadManager;

  /** The filename. */
  private String filename;

  /** The file utils. */
  private FileUtils fileUtils;

  /** The kune properties. */
  @Inject
  KuneProperties kuneProperties;

  /** The resp. */
  private HttpServletResponse resp;

  /** The state token. */
  private StateToken stateToken;

  /** The upload location. */
  private String uploadLocation;

  /**
   * Contentwhen.
   * 
   * @param mimeType
   *          the mime type
   * @param filename
   *          the filename
   * @param extension
   *          the extension
   */
  private void contentwhen(final BasicMimeType mimeType, final String filename, final String extension) {
    Mockito.when(content.getMimeType()).thenReturn(mimeType);
    Mockito.when(content.getFilename()).thenReturn(filename + extension);
  }

  /**
   * Creates the.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * File with no extension download test.
   * 
   * @throws Exception
   *           the exception
   */
  @Ignore
  public void fileWithNoExtensionDownloadTest() throws Exception {
    throw new Exception("TODO");
  }

  /**
   * Test jpg thumb download.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test pdf download1.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test pdf download2.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test pdf download3.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test pdf download4.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test pdf download null mime.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test png download1.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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

  /**
   * Test png download2.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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
