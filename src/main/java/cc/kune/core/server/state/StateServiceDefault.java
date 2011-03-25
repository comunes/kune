/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.state;


import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.License;
import cc.kune.domain.Revision;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StateServiceDefault implements StateService {

    private final AccessRightsService rightsService;
    private final SocialNetworkManager socialNetworkManager;
    private final GroupManager groupManager;
    private final TagUserContentManager tagManager;
    private final ContentManager contentManager;
    private final I18nTranslationService i18n;

    @Inject
    public StateServiceDefault(GroupManager groupManager, SocialNetworkManager socialNetworkManager,
            ContentManager contentManager, TagUserContentManager tagManager, AccessRightsService rightsService,
            I18nTranslationService i18n) {
        this.groupManager = groupManager;
        this.socialNetworkManager = socialNetworkManager;
        this.contentManager = contentManager;
        this.tagManager = tagManager;
        this.rightsService = rightsService;
        this.i18n = i18n;
    }

    public StateContainer create(User userLogged, Container container) {
        final StateContainer state = new StateContainer();
        state.setTitle(container.getName());
        state.setTypeId(container.getTypeId());
        state.setLanguage(container.getLanguage());
        state.setStateToken(container.getStateToken());
        state.setRootContainer(calculateRootContainer(container));
        state.setLicense(container.getOwner().getDefaultLicense());
        state.setAccessLists(container.getAccessLists());
        Group group = container.getOwner();
        setCommon(state, userLogged, group, container);
        return state;
    }

    public StateContent create(User userLogged, Content content) {
        StateContent state = new StateContent();
        state.setTypeId(content.getTypeId());
        state.setMimeType(content.getMimeType());
        state.setDocumentId(content.getId().toString());
        state.setLanguage(content.getLanguage());
        state.setPublishedOn(content.getPublishedOn());
        state.setAuthors(content.getAuthors());
        state.setTags(tagManager.getTagsAsString(userLogged, content));
        state.setStatus(content.getStatus());
        state.setStateToken(content.getStateToken());
        Revision revision = content.getLastRevision();
        state.setTitle(revision.getTitle());
        state.setVersion(content.getVersion());
        char[] text = revision.getBody();
        state.setContent(text == null ? null : new String(text));
        Container container = content.getContainer();
        state.setRootContainer(calculateRootContainer(container));
        License license = content.getLicense();
        Group group = container.getOwner();
        state.setLicense(license == null ? group.getDefaultLicense() : license);
        state.setContentRights(rightsService.get(userLogged, content.getAccessLists()));
        state.setAccessLists(content.getAccessLists());
        setCommon(state, userLogged, group, container);
        if (userLogged != User.UNKNOWN_USER) {
            state.setCurrentUserRate(contentManager.getRateContent(userLogged, content));
        }
        // FIXME: user RateResult
        Double rateAvg = contentManager.getRateAvg(content);
        state.setRate(rateAvg != null ? rateAvg : 0D);
        Long rateByUsers = contentManager.getRateByUsers(content);
        state.setRateByUsers(rateByUsers != null ? rateByUsers.intValue() : 0);
        return state;
    }

    public StateNoContent createNoHome(User userLogged, String groupShortName) {
        Group group = groupManager.findByShortName(groupShortName);
        assert (group.isPersonal());
        StateNoContent state = new StateNoContent();
        state.setGroup(group);
        state.setEnabledTools(groupManager.findEnabledTools(group.getId()));
        setSocialNetwork(state, userLogged, group);
        state.setStateToken(group.getStateToken());
        state.setTitle("<h2>" + i18n.t("This user does not have a homepage") + "</h2>");
        return state;
    }

    private Container calculateRootContainer(Container container) {
        return container.isRoot() ? container : container.getAbsolutePath().get(0);
    }

    private void setCommon(StateContainer state, User userLogged, Group group, Container container) {
        state.setToolName(container.getToolName());
        state.setGroup(group);
        state.setContainer(container);
        state.setContainerRights(rightsService.get(userLogged, container.getAccessLists()));
        state.setEnabledTools(groupManager.findEnabledTools(group.getId()));
        state.setTagCloudResult(tagManager.getTagCloudResultByGroup(group));
        setSocialNetwork(state, userLogged, group);
    }

    private void setSocialNetwork(StateAbstract state, User userLogged, Group group) {
        state.setSocialNetworkData(socialNetworkManager.getSocialNetworkData(userLogged, group));
    }
}
