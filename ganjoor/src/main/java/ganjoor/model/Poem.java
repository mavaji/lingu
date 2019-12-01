package ganjoor.model;


import core.model.BaseEntity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 4/18/11
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "GJ_POEM")
@NamedQueries({@NamedQuery(name =Poem.FIND_BY_POET_ID, query = "from Poem p where p.category.poet.id=:poetId")})
public class Poem extends BaseEntity {
    public static final String FIND_BY_POET_ID = "FIND_BY_POET_ID";

    private Category category;

    private String title;

    private String url;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
