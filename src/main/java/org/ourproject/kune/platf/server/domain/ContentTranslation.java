
package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "content_translations")
public class ContentTranslation implements HasId {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private I18nLanguage language;

    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(final Long contentId) {
        this.contentId = contentId;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

}
