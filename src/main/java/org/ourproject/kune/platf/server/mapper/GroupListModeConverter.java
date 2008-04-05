package org.ourproject.kune.platf.server.mapper;

import java.text.MessageFormat;
import java.util.HashMap;

import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.converters.CustomConverter;

import org.ourproject.kune.platf.server.domain.GroupListMode;

public class GroupListModeConverter implements CustomConverter {
    private HashMap<String, GroupListMode> stringToEnum;
    private HashMap<GroupListMode, String> enumToString;

    public GroupListModeConverter() {
	this.stringToEnum = new HashMap<String, GroupListMode>();
	this.enumToString = new HashMap<GroupListMode, String>();
	add(GroupListMode.NORMAL);
	add(GroupListMode.EVERYONE);
	add(GroupListMode.NOBODY);
    }

    private void add(GroupListMode mode) {
	enumToString.put(mode, mode.toString());
	stringToEnum.put(mode.toString(), mode);
    }

    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {
	if (source == null) {
	    return null;
	} else if (sourceClass.equals(String.class) && destinationClass.equals(GroupListMode.class)) {
	    return stringToEnum.get(source);
	} else if (sourceClass.equals(GroupListMode.class) && destinationClass.equals(String.class)) {
	    return enumToString.get(source);
	} else {
	    String msg = MessageFormat.format("couldn't map {0} ({1}) to {2} ({3})", source, sourceClass, destination,
		    destinationClass);
	    throw new MappingException(msg);
	}

    }

}
