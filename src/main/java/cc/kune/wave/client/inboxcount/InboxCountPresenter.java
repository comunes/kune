package cc.kune.wave.client.inboxcount;

import java.util.HashMap;
import java.util.Map;

import org.waveprotocol.box.webclient.search.Digest;
import org.waveprotocol.box.webclient.search.Search;
import org.waveprotocol.box.webclient.search.Search.Listener;

import cc.kune.chat.client.snd.SndClickEvent;
import cc.kune.core.client.events.NewUserRegisteredEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInOrSignOutEvent;
import cc.kune.core.client.state.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

public class InboxCountPresenter {

  public interface InboxCountView {

    void blink();

    void setTotal(int total);

    void showCount(boolean show);

  }

  private int currentTotal;
  private final Map<Integer, Integer> diggests;
  private final EventBus eventBus;
  // protected SimpleSearch search;
  private final Listener searchListener;
  private final Session session;
  // private final Session session;
  private final Timer updateTimer;
  private final InboxCountView view;

  @Inject
  public InboxCountPresenter(final InboxCountView view, final Session session, final EventBus eventBus) {
    this.view = view;
    this.session = session;
    this.eventBus = eventBus;
    // this.session = session;
    diggests = new HashMap<Integer, Integer>();
    currentTotal = Search.UNKNOWN_SIZE;

    updateTimer = new Timer() {
      @Override
      public void run() {
        update();
      }
    };

    searchListener = new Listener() {
      private void log(final int index, final Digest diggest, final String msg) {
        // Log.info(msg + index + " - " + diggest.getTitle() + ": unread: " +
        // diggest.getUnreadCount());
      }

      @Override
      public void onDigestAdded(final int index, final Digest diggest) {
        log(index, diggest, "Diggest added: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onDigestReady(final int index, final Digest diggest) {
        log(index, diggest, "Diggest ready: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onDigestRemoved(final int index, final Digest diggest) {
        log(index, diggest, "Diggest removed: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onStateChanged() {
        shouldUpdate();
        // diggests.clear();
      }

      @Override
      public void onTotalChanged(final int total) {
        shouldUpdate();
      }

      private void shouldUpdate() {
        updateTimer.schedule(3000);
      }

      private void updateDigest(final int index, final Digest diggest) {
        diggests.put(Integer.valueOf(index), Integer.valueOf(diggest.getUnreadCount()));
      }
    };

    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        view.showCount(event.isLogged());
      }
    });

    eventBus.addHandler(NewUserRegisteredEvent.getType(),
        new NewUserRegisteredEvent.NewUserRegisteredHandler() {
          @Override
          public void onNewUserRegistered(final NewUserRegisteredEvent event) {
            sendNoticeToUser();
          }
        });

  }

  public Listener getSearchListener() {
    return searchListener;
  }

  private void sendNoticeToUser() {
    view.blink();
    SndClickEvent.fire(eventBus);
  }

  private void setTotal(final int total) {
    view.setTotal(total);
    final boolean show = session.isLogged() && total != Search.UNKNOWN_SIZE && total > 0;
    view.showCount(show);
    if (show && total > currentTotal) {
      sendNoticeToUser();
    }
    currentTotal = total;
  }

  private void update() {
    int total = 0;
    for (final Integer unread : diggests.values()) {
      total += unread;
    }
    setTotal(total);
  }
}
