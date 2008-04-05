
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TagResultDTO implements IsSerializable {
    private String name;
    private Long count;

    public TagResultDTO() {
        this(null, null);
    }

    public TagResultDTO(final String name, final Long count) {
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
