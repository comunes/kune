package cc.kune.msgs.client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.msgs.client.UserMessagesPresenter.UserMessagesView;

public class UserMessagesPresenterTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final String MESSAGE_1 = "message 1";
  private static final String MESSAGE_2 = "message 2";
  private static final String TITLE_1 = "title 1";
  private static final String TITLE_2 = "title 2";
  private CloseCallback closeCallback;
  private UserMessage msg;
  private UserMessagesPresenter presenter;
  private UserMessagesView view;

  @Test
  public void basicMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
  }

  @Before
  public void before() {
    presenter = new UserMessagesPresenter();
    view = Mockito.mock(UserMessagesView.class);
    closeCallback = Mockito.mock(CloseCallback.class);
    presenter.init(view);
    msg = Mockito.mock(UserMessage.class);
    // Mockito.when(msg.getText()).thenReturn(MESSAGE_1);
    Mockito.when(
        view.add((NotifyLevel) Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyBoolean(), (CloseCallback) Mockito.anyObject())).thenReturn(
        msg);
  }

  @Test
  public void twoBasicAvatarMsg() {
    final NotifyLevel avatar = NotifyLevel.avatar.url("image1.png");
    presenter.add(avatar, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(true);
    presenter.add(avatar, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(msg, Mockito.times(1)).appendMsg(MESSAGE_2);
  }

  @Test
  public void twoBasicDiffAvatarMsg() {
    final NotifyLevel avatar1 = NotifyLevel.avatar.url("image1.png");
    presenter.add(avatar1, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    final NotifyLevel avatar2 = NotifyLevel.avatar.url("image2.png");
    presenter.add(avatar2, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar1, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar2, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  @Test
  public void twoBasicDiffCloseableMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, true,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  @Test
  public void twoBasicDiffIdMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_2, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_2, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  @Test
  public void twoBasicDiffLevelMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    presenter.add(NotifyLevel.error, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.error, TITLE_1, MESSAGE_2, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_2);
  }

  @Test
  public void twoBasicDiffTitleMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_2, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_2, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  @Test
  public void twoBasicMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(true);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.times(1)).appendMsg(MESSAGE_2);
  }

  @Test
  public void twoBasicMsgButAfterFirstClosed() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(false);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_2);
  }

}
