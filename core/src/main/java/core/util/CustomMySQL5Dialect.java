package core.util;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * @author Vahid Mavaji
 */
public class CustomMySQL5Dialect extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=myisam DEFAULT CHARSET=utf8";
    }
}