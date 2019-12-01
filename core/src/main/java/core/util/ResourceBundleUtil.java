package core.util;

import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 12/8/10
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceBundleUtil {
    public static String getResource(String key) {
        try {
            return ResourceBundle.getBundle("Messages").getString(key);
        } catch (Exception e) {
            return null;
        }
    }
}
