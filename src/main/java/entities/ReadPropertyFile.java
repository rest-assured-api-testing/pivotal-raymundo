package entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    Properties prop = new Properties();
    FileInputStream ip;
    {
        try {
            ip = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getToken() {
        return prop.getProperty("token");
    }

    public String getBaseUri() {
        return prop.getProperty("baseUri");
    }

    public String getAccountId() {
        return prop.getProperty("accountId");
    }

    public String getPersonId() {
        return prop.getProperty("personId");
    }
}
