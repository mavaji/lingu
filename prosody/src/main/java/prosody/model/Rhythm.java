package prosody.model;

import core.model.BaseEntity;

import javax.persistence.*;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "PR_RHYTHM")
@NamedQueries({@NamedQuery(name = Rhythm.GET_ALL_RHYTHMS, query = "from Rhythm")})
public class Rhythm extends BaseEntity {
    public static final String GET_ALL_RHYTHMS = "get_all_rhythms";

    private Integer code;

    private String symbol;

    private String rhythm;

    private String name;

    @Column(name = "code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "rhythm")
    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
