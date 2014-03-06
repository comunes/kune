UPDATE containers c, access_lists a, group_list g SET g.mode="NORMAL" WHERE c.typeId="lists.list" AND c.accessLists_id=a.id AND a.editors_id=g.id;

