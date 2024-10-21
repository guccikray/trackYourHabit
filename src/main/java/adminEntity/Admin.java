package adminEntity;

import propertiesLoader.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class Admin {

    private final String name;
    private final String email;
    private final String password;
    private final static Properties config ;
    //I decided to make Admin Singleton, to prevent user make many instances
    private static final Admin instance;

    private Admin(String name, String email, String password) throws IOException {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    static {
        try {
            config = PropertiesLoader.loadProperties();
            //Admin data stored in application.properties
            String adminName = config.getProperty("admin.name");
            String adminEmail = config.getProperty("admin.email");
            String adminPassword = config.getProperty("admin.password");

            instance = new Admin(adminName, adminEmail, adminPassword);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Admin getInstance() {
        return instance;
    }

    public boolean checkIfAdmin(String name, String email, String password) {
        if (!this.name.equals(name) || !this.email.equals(email) || !this.password.equals(password)) {
            throw new IllegalArgumentException("Invalid admin credentials provided");
        }
        return true;
    }
}
