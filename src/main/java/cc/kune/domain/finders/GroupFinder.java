package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.Group;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface GroupFinder {

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid)", returnAs = ArrayList.class)
    public List<Group> findAdminInGroups(@Named("groupid") final Long groupId);

    @Finder(query = "FROM Group g WHERE g.shortName = :shortName")
    public Group findByShortName(@Named("shortName") final String shortName);

    @Finder(query = "FROM Group g WHERE g.id IN (SELECT g.id FROM g.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid)", returnAs = ArrayList.class)
    public List<Group> findCollabInGroups(@Named("groupid") final Long groupId);

    @Finder(query = "SELECT t.root.toolName FROM ToolConfiguration t WHERE t.enabled=true AND t.root.owner.id = :groupid", returnAs = ArrayList.class)
    public List<String> findEnabledTools(@Named("groupid") final Long groupId);

    @Finder(query = "FROM Group", returnAs = ArrayList.class)
    public List<Group> getAll();
}
