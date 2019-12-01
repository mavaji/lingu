package core.util;

import java.util.ResourceBundle;

/**
 * @author Vahid Mavaji
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
