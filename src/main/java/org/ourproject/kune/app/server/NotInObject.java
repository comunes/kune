
package org.ourproject.kune.app.server;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.google.inject.matcher.AbstractMatcher;

public class NotInObject extends AbstractMatcher<Method> {
    private final List<String> excluded;
    Object o;

    public NotInObject() {
        // FIXME exclude password
        excluded = Arrays.asList(new String[] { "finalize", "toString", "hashCode", "getClass", "wait", "equals" });
    }

    public boolean matches(final Method t) {
        String name = t.getName();

        boolean isGetter = name.startsWith("set");
        boolean isExcluded = excluded.contains(name);
        return !isGetter || !isExcluded;
    }

}
