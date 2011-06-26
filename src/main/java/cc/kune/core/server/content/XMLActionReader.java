package cc.kune.core.server.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.actions.xml.XMLKuneClientActions;

import com.calclab.emite.xtesting.ServicesTester;
import com.google.inject.Singleton;

@Singleton
public class XMLActionReader {

  private XMLKuneClientActions actions;

  public XMLActionReader() throws IOException {
    final File xmlFile = new File("src/main/webapp/" + XMLActionsParser.ACTIONS_XML_LOCATION);

    // Inspired in:
    // http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
    final FileInputStream stream = new FileInputStream(xmlFile);
    try {
      final FileChannel fc = stream.getChannel();
      final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      final String xml = Charset.forName("UTF-8").decode(bb).toString();
      actions = new XMLKuneClientActions(new ServicesTester(), xml);
    } finally {
      stream.close();
    }
  }

  public XMLKuneClientActions getActions() {
    return actions;
  }
}
