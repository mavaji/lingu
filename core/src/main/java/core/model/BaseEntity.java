package core.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Vahid Mavaji
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable, Comparable {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof BaseEntity)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        BaseEntity that = (BaseEntity) other;

        return this.id != null && that.id != null && this.id.equals(that.id);
    }

    public int hashCode() {
        if (id != null) {
            return (id.hashCode());
        }
        return super.hashCode();
    }

    public int compareTo(Object o) {
        if (o == null || !(o instanceof BaseEntity)) {
            return -1;
        }
        BaseEntity that = (BaseEntity) o;

        if (this.id == null || that.id == null) {
            return -1;
        }

        if (this.id > that.id) {
            return 1;
        } else if (this.id < that.id) {
            return -1;
        } else {
            return 0;
        }
    }
}
