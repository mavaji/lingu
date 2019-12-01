package core.util;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 12/8/10
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomMySQL5Dialect extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=myisam DEFAULT CHARSET=utf8";
    }
}