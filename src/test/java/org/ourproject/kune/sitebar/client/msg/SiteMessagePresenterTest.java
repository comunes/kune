package org.ourproject.kune.sitebar.client.msg;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class SiteMessagePresenterTest {

    private SiteMessagePresenter presenter;
    private SiteMessageView view;

    @Before
    public void createObjects() {
	view = EasyMock.createStrictMock(SiteMessageView.class);
	presenter = new SiteMessagePresenter();
    }

    @Test
    public void testViewInitialization() {
	view.hide();
	EasyMock.replay(view);
	presenter.init(view);
	EasyMock.verify(view);
    }

    @Test
    public void testMessage() {
	view.hide();
	view.setMessage("test 1", 3, 0);
	view.show();
	EasyMock.replay(view);
	presenter.init(view);
	presenter.setValue("test 1", 0);
	EasyMock.verify(view);
    }

    @Test
    public void testTwoMessagesSameType() {
	view.hide();
	view.setMessage("test 1", 3, 0);
	view.show();
	view.setMessage("test 1<br>test 2");
	view.show();
	EasyMock.replay(view);
	presenter.init(view);
	presenter.setValue("test 1", 0);
	presenter.setValue("test 2", 0);
	EasyMock.verify(view);
    }

    @Test
    public void testTwoMessagesDifTypes() {
	view.hide();
	view.setMessage("test 1", 3, 1);
	view.show();
	view.setMessage("test 1<br>test 2", 1, 0);
	view.show();
	EasyMock.replay(view);
	presenter.init(view);
	presenter.setValue("test 1", 1);
	presenter.setValue("test 2", 0);
	EasyMock.verify(view);
    }

}
