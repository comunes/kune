package cc.kune.core.client.groups.newgroup;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter.NewGroupProxy;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.event.shared.EventBus;

public class NewGroupPresenterTest {

  private NewGroupPresenter grPresenter;
  private NewGroupPanel mockGrPanel;

  @Before
  public void before() {
    mockGrPanel = Mockito.mock(NewGroupPanel.class);
    grPresenter = new NewGroupPresenter(Mockito.mock(EventBus.class), mockGrPanel,
        Mockito.mock(NewGroupProxy.class), Mockito.mock(I18nTranslationService.class),
        Mockito.mock(Session.class), Mockito.mock(StateManager.class), null, null,
        Mockito.mock(GroupOptions.class));
  }

  @Test
  public void testGenerateShortNameAccents() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("áéíóúàèìòùâêîôûäëïöü");
    assertEquals(grPresenter.generateShortName(), "aeiouaeiouaeiouaeiou");
  }

  @Test
  public void testGenerateShortNameCapitalAccents() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜñÑçÇ");
    assertEquals(grPresenter.generateShortName(), "aeiouaeiouaeiouaeiounncc");
  }

  @Test
  public void testGenerateShortNameSentence() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("Los Desposeidos: una utopía ambigua!!");
    assertEquals(grPresenter.generateShortName(), "losdesposeidosunautopiaambigua");
  }

  @Test
  public void testGenerateShortNameSymbols() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("a!·$%&/()=?¿¡'|@#'12");
    assertEquals(grPresenter.generateShortName(), "a12");
  }

  @Test
  public void testGenerateShortNameVeryLongSentence() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn(
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
    assertEquals(grPresenter.generateShortName(), "loremipsumdolorsitametconsect");
  }
}
