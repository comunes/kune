/**
 * Copyright 2012 Apache Wave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cc.kune.wave.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.persistence.file.FileAccountStore;

import cc.kune.core.server.manager.file.FileDownloadManagerUtils;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.inject.Inject;
import com.google.inject.name.Named;

// TODO: Auto-generated Javadoc
/**
 * The servlet for fetching available gadgets from a json file on the server.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SuppressWarnings("serial")
@Singleton
public class CustomGadgetProviderServlet extends HttpServlet {

  /** The Constant LOG. */
  private static final Logger LOG = Logger.getLogger(FileAccountStore.class.getName());

  /** The json cache. */
  private final ConcurrentMap<String, String> jsonCache;

  /**
   * Instantiates a new custom gadget provider servlet.
   * 
   * @param resourceBases
   *          the resource bases
   */
  @Inject
  public CustomGadgetProviderServlet(@Named(CoreSettings.RESOURCE_BASES) final List<String> resourceBases) {
    // FIXME: For new versions of guava
    // http://code.google.com/p/guava-libraries/wiki/MapMakerMigration
    jsonCache = new MapMaker().expireAfterWrite(5, TimeUnit.MINUTES).makeComputingMap(
        new Function<String, String>() {
          @Override
          public String apply(final String key) {
            String jsonString = "";
            try {
              jsonString = FileDownloadManagerUtils.getInpuStreamAsString(FileDownloadManagerUtils.getInputStreamInResourceBases(
                  resourceBases, "/others/jsongadgets.json"));
            } catch (final IOException e) {
              LOG.log(Level.WARNING, "Error while loading gadgets json", e);
            }
            return jsonString;
          }
        });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
      throws IOException {
    final String jsonString = jsonCache.get("");
    if (jsonString.equals("")) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error loading json data from file");
    } else {
      final PrintWriter out = response.getWriter();
      out.print(jsonCache.get(""));
      out.flush();
    }
  }
}
