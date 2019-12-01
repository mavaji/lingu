package ganjoor.model;

import core.model.BaseEntity;

import javax.persistence.*;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "GJ_VERSE")
@NamedQueries({@NamedQuery(name = Verse.FIND_BY_POEM_ID, query = "from Verse v where v.poem.id=:poemId"),
        @NamedQuery(name = Verse.FIND_BY_TEXT, query = "from Verse v where v.text like '%:text%'"),
        @NamedQuery(name = Verse.FIND_BY_POET_ID_AND_TEXT, query = "from Verse v where v.text like '%:text%' and v.poem.category.poet.id=:poetId"),
        @NamedQuery(name = Verse.FIND_BY_CENTURY_AND_TEXT, query = "from Verse v where v.text like '%:text%' and v.poem.category.poet.century=:century"),
        @NamedQuery(name = Verse.COUNT_BY_CENTURY, query = "select count(*) from Verse v where v.poem.category.poet.century=:century")})
public class Verse extends BaseEntity {
    public static final String FIND_BY_POEM_ID = "FIND_BY_POEM_ID";

    public static final String FIND_BY_TEXT = "FIND_BY_TEXT";

    public static final String FIND_BY_POET_ID_AND_TEXT = "FIND_BY_POET_ID_AND_TEXT";

    public static final String FIND_BY_CENTURY_AND_TEXT = "FIND_BY_CENTURY_AND_TEXT";

    public static final String COUNT_BY_CENTURY = "COUNT_BY_CENTURY";

    private Poem poem;

    private Long vorder;

    private Long position;

    private String text;

    @ManyToOne
    @JoinColumn(name = "poem_id")
    public Poem getPoem() {
        return poem;
    }

    public void setPoem(Poem poem) {
        this.poem = poem;
    }

    @Column(name = "vorder")
    public Long getVorder() {
        return vorder;
    }

    public void setVorder(Long vorder) {
        this.vorder = vorder;
    }

    @Column(name = "position")
    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
