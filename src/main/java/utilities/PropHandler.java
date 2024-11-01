package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropHandler {

    public static void main(String[] args){
        PropHandler ph = new PropHandler();
        System.out.println(ph.readDataFromPropFile("robots.txt"));
    }

    public String readDataFromPropFile(String propName) {

        File propFile = new File(
                System.getProperty("user.dir")+ "\\src\\main\\java\\resources\\config.properties");
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propFile);
            prop.load(fis);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return prop.getProperty(propName);

    }
}
