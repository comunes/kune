/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package cc.kune.core.shared.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CommentDTO implements IsSerializable {

    private Long id;
    // private ContentDTO content;
    private Long publishedOn;
    private UserSimpleDTO author;
    private CommentDTO parent;
    private List<CommentDTO> childs;
    private int positiveVotersCount;
    private int negativeVotersCount;
    private int abuseInformersCount;
    private String text;

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
        final CommentDTO other = (CommentDTO) obj;
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        if (publishedOn == null) {
            if (other.publishedOn != null) {
                return false;
            }
        } else if (!publishedOn.equals(other.publishedOn)) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }

    public int getAbuseInformersCount() {
        return abuseInformersCount;
    }

    public UserSimpleDTO getAuthor() {
        return author;
    }

    public List<CommentDTO> getChilds() {
        return childs;
    }

    public Long getId() {
        return id;
    }

    public int getNegativeVotersCount() {
        return negativeVotersCount;
    }

    public CommentDTO getParent() {
        return parent;
    }

    public int getPositiveVotersCount() {
        return positiveVotersCount;
    }

    public Long getPublishedOn() {
        return publishedOn;
    }

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (author == null ? 0 : author.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (parent == null ? 0 : parent.hashCode());
        result = prime * result + (publishedOn == null ? 0 : publishedOn.hashCode());
        result = prime * result + (text == null ? 0 : text.hashCode());
        return result;
    }

    public void setAbuseInformersCount(final int abuseInformersCount) {
        this.abuseInformersCount = abuseInformersCount;
    }

    public void setAuthor(final UserSimpleDTO author) {
        this.author = author;
    }

    public void setChilds(final List<CommentDTO> childs) {
        this.childs = childs;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNegativeVotersCount(final int negativeVotersCount) {
        this.negativeVotersCount = negativeVotersCount;
    }

    public void setParent(final CommentDTO parent) {
        this.parent = parent;
    }

    public void setPositiveVotersCount(final int positiveVotersCount) {
        this.positiveVotersCount = positiveVotersCount;
    }

    public void setPublishedOn(final Long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
