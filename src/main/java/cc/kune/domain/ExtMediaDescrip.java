package cc.kune.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import cc.kune.domain.utils.HasId;

import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "ext_media_descriptors")
public class ExtMediaDescrip implements HasId {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String siteurl;
    @Column(nullable = false)
    private String detectRegex;
    @Column(nullable = false)
    private String idRegex;
    @Column(nullable = false)
    @Length(max = 1000)
    private String embedTemplate;
    private int width;
    private int height;

    public ExtMediaDescrip() {
        this(null, null, null, null, null, 0, 0);
    }

    public ExtMediaDescrip(final String name, final String siteurl, final String detectRegex, final String idRegex,
            final String embedTemplate, final int defWidth, final int defHeight) {
        this.name = name;
        this.siteurl = siteurl;
        this.detectRegex = detectRegex;
        this.idRegex = idRegex;
        this.embedTemplate = embedTemplate;
        width = defWidth;
        height = defHeight;
    }

    @Finder(query = "from ExtMediaDescrip")
    public List<ExtMediaDescrip> getAll() {
        return null;
    }

    public String getDetectRegex() {
        return detectRegex;
    }

    public String getEmbedTemplate() {
        return embedTemplate;
    }

    public int getHeight() {
        return height;
    }

    public Long getId() {
        return id;
    }

    public String getIdRegex() {
        return idRegex;
    }

    public String getName() {
        return name;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public int getWidth() {
        return width;
    }

    public void setDetectRegex(final String detectRegex) {
        this.detectRegex = detectRegex;
    }

    public void setEmbedTemplate(final String embedTemplate) {
        this.embedTemplate = embedTemplate;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIdRegex(final String idRegex) {
        this.idRegex = idRegex;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSiteurl(final String siteurl) {
        this.siteurl = siteurl;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
