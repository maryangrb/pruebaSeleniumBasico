package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
    private Properties prop = new Properties();

    public GetProperties(){
        InputStream in = getClass().getResourceAsStream("/config.properties");
        try {
            prop.load(in);
        } catch (FileNotFoundException e){
            System.out.println("The property file was not found");
        } catch (IOException e){
            System.out.println("Can not read property file");
        }
    }

    public String getString(String propertyName){
        return prop.getProperty(propertyName);
    }
}
