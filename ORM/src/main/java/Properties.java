import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class defines everything that's required for JPA to connect to your MySQL service
 * For e.g. Username, password, URL, etc. is all captured here as properties
 */
public class Properties {

    protected static void setup() {

        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    protected static Map<String, String> getPropertiesForTableAutoCreation() {

        return getProperties("create");
    }

    protected static Map<String, String> getPropertiesForTableValidation() {

        return getProperties("validate");
    }

    /*
     * The properties Map contains all required parameters to build an EntityManager
     * See EntityManager being created in E.g.Store.java
     * Read this information from database.properties and populate it
     */
    private static Map<String, String> getProperties(String hibernate_option) {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", hibernate_option);

        // the driver - this needs to be part of the project's classpath
        // see: https://studres.cs.st-andrews.ac.uk/CS1003/Examples/W06-1-JDBC/READ-ME.txt
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");

        //Replace 'graham' with your own username, and xxxxxxxx with your MySQL password
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://graham.host.cs.st-andrews.ac.uk/graham_db");
        properties.put("javax.persistence.jdbc.user", "graham");
        properties.put("javax.persistence.jdbc.password", "xxxxxxxx");

        return properties;
    }
}

