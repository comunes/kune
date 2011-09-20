package cc.kune.core.server.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.manager.KuneWaveManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.utils.UserBuddiesData;
import cc.kune.wave.server.KuneWaveService;
import cc.kune.wave.server.KuneWaveUtils;
import cc.kune.wave.server.ParticipantUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KuneWaveManagerDefault implements KuneWaveManager {

  private final GroupFinder groupFinder;
  private final KuneProperties kuneProperties;
  private final ParticipantUtils participantUtils;
  private final UserManager userManager;
  private final KuneWaveService waveService;

  @Inject
  public KuneWaveManagerDefault(final KuneWaveService waveService,
      final ParticipantUtils participantUtils, final KuneProperties kuneProperties,
      final GroupFinder groupFinder, final UserManager userManager) {
    this.waveService = waveService;
    this.participantUtils = participantUtils;
    this.kuneProperties = kuneProperties;
    this.groupFinder = groupFinder;
    this.userManager = userManager;
  }

  @Override
  public String sendFeedback(final User user, final String title, final String body) {
    final List<String> participants = kuneProperties.getList(KuneProperties.FEEDBACK_TO);
    participants.add(0, user.getShortName());
    final List<ParticipantId> partIds = new ArrayList<ParticipantId>();
    for (final String part : participants) {
      partIds.add(participantUtils.of(part));
    }
    return KuneWaveUtils.getUrl(waveService.createWave(title, body,
        partIds.toArray(new ParticipantId[0])));
  }

  @Override
  public String writeTo(final User user, final String groupName, final boolean onlyToAdmins) {
    final Group group = groupFinder.findByShortName(groupName);
    final Set<Group> toList = new HashSet<Group>();
    toList.add(user.getUserGroup());
    if (group.isPersonal()) {
      final UserBuddiesData userBuddies = userManager.getUserBuddies(groupName);
      if (userBuddies.contains(user.getShortName())) {
        toList.add(group);
      } else {
        throw new AccessViolationException("You cannot write to non buddies");
      }
    } else {
      if (!AccessRightsUtils.correctMember(user, group, AccessRol.Editor)) {
        throw new AccessViolationException("You cannot write because you are not a member");
      }
      if (onlyToAdmins) {
        toList.addAll(group.getSocialNetwork().getAccessLists().getAdmins().getList());
      } else {
        toList.addAll(group.getSocialNetwork().getAccessLists().getAdmins().getList());
        toList.addAll(group.getSocialNetwork().getAccessLists().getEditors().getList());
      }
    }
    return KuneWaveUtils.getUrl(waveService.createWave("", participantUtils.listFrom(toList)));
  }
}
