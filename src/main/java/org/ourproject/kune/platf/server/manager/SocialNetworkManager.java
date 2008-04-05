
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.server.ParticipationData;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface SocialNetworkManager extends Manager<SocialNetwork, Long> {

    void addAdmin(User user, Group group);

    void addGroupToAdmins(User userLogged, Group group, Group inGroup) throws SerializableException;

    void addGroupToCollabs(User userLogged, Group group, Group inGroup) throws SerializableException;

    void addGroupToViewers(User userLogged, Group group, Group inGroup) throws SerializableException;

    String requestToJoin(User user, Group inGroup) throws SerializableException;

    void acceptJoinGroup(User userLogged, Group group, Group inGroup) throws SerializableException;

    void denyJoinGroup(User userLogged, Group group, Group inGroup) throws SerializableException;

    void setCollabAsAdmin(User userLogged, Group group, Group inGroup) throws SerializableException;

    void setAdminAsCollab(User userLogged, Group group, Group inGroup) throws SerializableException;

    void deleteMember(User userLogged, Group group, Group inGroup) throws SerializableException,
            AccessViolationException;

    void unJoinGroup(Group groupToUnJoin, Group inGroup) throws SerializableException;

    SocialNetwork find(User user, Group group) throws SerializableException;

    ParticipationData findParticipation(User user, Group group) throws SerializableException;

}
