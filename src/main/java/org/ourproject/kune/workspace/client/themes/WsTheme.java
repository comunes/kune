package org.ourproject.kune.workspace.client.themes;

public class WsTheme {
    private final String name;

    public WsTheme(final String name) {
	this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final WsTheme other = (WsTheme) obj;
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	return true;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (name == null ? 0 : name.hashCode());
	return result;
    }

    public String toString() {
	return name;
    }

}
