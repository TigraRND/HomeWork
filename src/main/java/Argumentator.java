import java.util.Properties;

public class Argumentator {
    private Properties properties = null;
    private String login = null;
    private String password = null;
    private String browser = null;

    public Argumentator(){
        properties = System.getProperties();
        login = properties.getProperty("login");
        password = properties.getProperty("password");
        browser = properties.getProperty("browser");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getBrowser() {
        if (browser != null)
            browser = browser.toUpperCase();
        return browser;
    }
}