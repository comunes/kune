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
 */
package cc.kune.core.server.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.actions.xml.XMLKuneClientActions;

import com.calclab.emite.xtesting.ServicesTester;
import com.google.inject.Singleton;

@Singleton
public class XMLActionReader implements ServletContextListener {
  public static final Log LOG = LogFactory.getLog(XMLActionReader.class);
  private XMLKuneClientActions actions;

  public XMLActionReader() throws IOException {
  }

  @Override
  public void contextDestroyed(final ServletContextEvent sce) {
  }

  @Override
  public void contextInitialized(final ServletContextEvent sce) {
    final File xmlFile = new File(sce.getServletContext().getRealPath(
        XMLActionsParser.ACTIONS_XML_LOCATION_PATH_ABS));
    // Inspired in:
    //
    // stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
    FileInputStream stream;
    try {
      stream = new FileInputStream(xmlFile);
      final FileChannel fc = stream.getChannel();
      final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      final String xml = Charset.forName("UTF-8").decode(bb).toString();
      actions = new XMLKuneClientActions(new ServicesTester(), xml);
      stream.close();
    } catch (final IOException e) {
      LOG.error("Error reading extension actions", e);
    }

    // Other option:

    // final InputStream iStream =
    // this.getClass().getClassLoader().getResourceAsStream(
    // XMLActionsParser.ACTIONS_XML_LOCATION_FILE);
    // final StringWriter writer = new StringWriter();
    // IOUtils.copy(iStream, writer, "UTF-8");
    // final String xml = writer.toString();
    // actions = new XMLKuneClientActions(new ServicesTester(), xml);

  }

  public XMLKuneClientActions getActions() {
    return actions;
  }
}
