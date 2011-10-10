package cc.kune.core.server.manager;

import cc.kune.domain.User;

public interface KuneWaveManager {

  String sendFeedback(User user, String title, String body);

  String writeTo(User user, String groupName, boolean onlyToAdmins);

  String writeTo(User user, String groupName, boolean onlyToAdmins, String title, String content);

}
