package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;

public class ContentUtils {

    public static Long parseId(final String id) throws ContentNotFoundException {
	try {
	    return new Long(id);
	} catch (final NumberFormatException e) {
	    throw new ContentNotFoundException();
	}
    }
}
