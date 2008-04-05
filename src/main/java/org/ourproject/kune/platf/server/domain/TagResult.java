
package org.ourproject.kune.platf.server.domain;


public class TagResult {
    private String name;
    private Long count;

    public TagResult() {
        this(null, null);
    }

    public TagResult(final String name) {
        this(name, null);
    }

    public TagResult(final String name, final Long count) {
        this.name = name;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(final Long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
