package cc.kune.core.server.notifier;

import com.google.inject.Provider;

/**
 * The Class PendingNotificationProvider is used to generate notifications in a
 * deferred way of waves updates (for instance)
 */
public abstract class PendingNotificationProvider implements Provider<PendingNotification> {

}
