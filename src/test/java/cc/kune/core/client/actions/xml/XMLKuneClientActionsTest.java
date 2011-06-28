package cc.kune.core.client.actions.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.content.XMLActionReader;

public class XMLKuneClientActionsTest {

  private XMLKuneClientActions actions;
  private Map<String, XMLWaveExtension> extensions;
  private List<XMLGuiActionDescriptor> guiActionDescriptors;

  @Before
  public void before() throws IOException {
    actions = new XMLActionReader().getActions();
    extensions = actions.getExtensions();
    guiActionDescriptors = actions.getGuiActionDescriptors();
  }

  @Test
  public void testExtensions() {
    assertTrue(extensions.size() > 0);
    for (final XMLWaveExtension extension : extensions.values()) {
      assertTrue(extension.getExtName().length() > 0);
      assertTrue(extension.getGadgetUrl().length() > 0);
      assertTrue(extension.getIconUrl().length() > 0 || extension.getIconCss().length() > 0);
    }
  }

  @Test
  public void testGuiActions() {
    assertTrue(guiActionDescriptors.size() > 0);
    for (final XMLGuiActionDescriptor descrip : guiActionDescriptors) {
      assertTrue(descrip.getDescName().length() > 0);
      assertTrue(descrip.getType().length() > 0);
      final String extensionName = descrip.getExtensionName();
      assertTrue(extensionName.length() > 0);
      assertNotNull(extensions.get(extensionName));
      assertTrue(descrip.getPath().length() >= 0);
      assertTrue(descrip.isEnabled() || !descrip.isEnabled());
      final List<XMLTypeId> typeIds = descrip.getTypeIds();
      assertTrue(typeIds.size() > 0);
      for (final XMLTypeId typeId : typeIds) {
        assertTrue(typeId.getOrigTypeId().length() > 0);
        assertTrue(typeId.getDestTypeId().length() > 0);
      }
      final XMLRol rol = descrip.getRol();
      assertNotNull(rol);
      assertTrue(rol.isAuthNeed() || !rol.isAuthNeed());
      assertTrue(rol.getRolRequired() != null);
    }
  }
}
