package ganjoor.model;

import core.model.BaseEntity;

import javax.persistence.*;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "GJ_CAT")
public class Category extends BaseEntity {
    private String text;

    private String url;

    private Category parent;

    private Poet poet;

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @OneToOne
    @JoinColumn(name = "poet_id")
    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }
}
