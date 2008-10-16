package org.ourproject.kune.platf.server.tool;

public class ToolSimple {

    private final String name;
    private final String rootName;

    public ToolSimple(String name, String rootName) {
        this.name = name;
        this.rootName = rootName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ToolSimple other = (ToolSimple) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (rootName == null) {
            if (other.rootName != null) {
                return false;
            }
        } else if (!rootName.equals(other.rootName)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getRootName() {
        return rootName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rootName == null) ? 0 : rootName.hashCode());
        return result;
    }

}
