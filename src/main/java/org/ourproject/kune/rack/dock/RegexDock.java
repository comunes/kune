package org.ourproject.kune.rack.dock;

import java.util.regex.Pattern;

public class RegexDock extends AbstractDock {
    private final Pattern pattern;

    public RegexDock(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public boolean matches(final String url) {
        return pattern.matcher(url).matches();
    }

    @Override
    public String toString() {
        return pattern.toString() + " - " + getFilter().getClass().getSimpleName();
    }

}
