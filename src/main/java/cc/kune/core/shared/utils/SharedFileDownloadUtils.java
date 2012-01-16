package cc.kune.core.shared.utils;

import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

public class SharedFileDownloadUtils {

  private final String prefix;

  public SharedFileDownloadUtils() {
    this.prefix = "";
  }

  public SharedFileDownloadUtils(final String prefix) {
    this.prefix = prefix;
  }

  public String getGroupLogo(final GroupDTO group) {
    return prefix
        + (group.hasLogo() ? getLogoImageUrl(group.getShortName())
            : group.isPersonal() ? FileConstants.PERSON_NO_AVATAR_IMAGE
                : FileConstants.GROUP_NO_AVATAR_IMAGE);
  }

  public String getLogoAvatarHtml(final String groupName, final boolean groupHasLogo,
      final boolean isPersonal, final int size, final int hvspace) {
    final String imgUrl = groupHasLogo ? getLogoImageUrl(groupName) : isPersonal ? prefix + "/"
        + FileConstants.PERSON_NO_AVATAR_IMAGE : prefix + "/" + FileConstants.GROUP_NO_AVATAR_IMAGE;
    return "<img hspace='" + hvspace + "' vspace='" + hvspace + "' align='left' style='width: " + size
        + "px; height: " + size + "px;' src='" + imgUrl + "'>";
  }

  public String getLogoImageUrl(final String groupName) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, groupName)).toString();
  }

  public String getPrefix() {
    return prefix;
  }

  public String getUrl(final String hash) {
    return getPrefix() + "#" + hash;
  }

  public String getUserAvatar(final String username) {
    return prefix
        + new Url(FileConstants.AVATARDOWNLOADSERVLET, new UrlParam(FileConstants.USERNAME, username)).toString();
  }

  public String getUserAvatar(final UserSimpleDTO user) {
    return prefix
        + (user.hasLogo() ? getLogoImageUrl(user.getShortName()) : FileConstants.PERSON_NO_AVATAR_IMAGE);
  }

}