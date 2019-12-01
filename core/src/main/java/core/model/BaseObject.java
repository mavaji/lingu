package core.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 12/9/10
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseObject implements Serializable, Cloneable {
    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false, this.getClass());
        } catch (Exception e) {
            return super.toString();
        }
    }
}
