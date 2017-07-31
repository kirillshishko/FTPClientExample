package property;

import java.util.ResourceBundle;

public class FTPClientProperty {
    private static final String CONFIG_PATH = "ftpclient";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_PATH);

    public static String getProperty(String propertyName) {
        return bundle.getString(propertyName);
    }
}
