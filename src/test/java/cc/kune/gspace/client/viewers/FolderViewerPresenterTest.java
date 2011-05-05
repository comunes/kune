package cc.kune.gspace.client.viewers;

import static cc.kune.gspace.client.viewers.FolderViewerPresenter.CSSBTN;
import static cc.kune.gspace.client.viewers.FolderViewerPresenter.CSSBTNC;
import static cc.kune.gspace.client.viewers.FolderViewerPresenter.CSSBTNL;
import static cc.kune.gspace.client.viewers.FolderViewerPresenter.CSSBTNR;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.registry.ContentCapabilitiesRegistry;

public class FolderViewerPresenterTest {

  private FolderViewerPresenter presenter;

  @Before
  public void before() {
    presenter = new FolderViewerPresenter(null, null, null, null, null, null, null, null, null,
        Mockito.mock(ContentCapabilitiesRegistry.class));
  }

  @Test
  public void with1() {
    assertEquals(CSSBTN, presenter.calculateStyle(0, 1));
  }

  @Test
  public void with2() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 2));
    assertEquals(CSSBTNR, presenter.calculateStyle(1, 2));
  }

  @Test
  public void with3() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 3));
    assertEquals(CSSBTNC, presenter.calculateStyle(1, 3));
    assertEquals(CSSBTNR, presenter.calculateStyle(2, 3));
  }

  @Test
  public void with4() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 4));
    assertEquals(CSSBTNC, presenter.calculateStyle(1, 4));
    assertEquals(CSSBTNC, presenter.calculateStyle(2, 4));
    assertEquals(CSSBTNR, presenter.calculateStyle(3, 4));
  }
}
