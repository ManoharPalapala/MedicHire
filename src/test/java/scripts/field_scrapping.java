package scripts;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelHandler;
import utilities.PropHandler;
import utilities.Utils;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class field_scrapping {
    ExcelHandler excel = new ExcelHandler();
    PropHandler ph = new PropHandler();
    Utils util = new Utils();


    public static void main(String[] args){
        field_scrapping m = new field_scrapping();
        m.extractData("url");

    }


    public List<String> data(){
        List<String> url = new ArrayList<String>();
        for(Object[] x:excel.readData_Excel(ph.readDataFromPropFile("inputDataSheetName"))){
            for(Object y:x){
                url.add(y.toString());
            }
        }
        return url;
    }




    public void extractData(String attribute){
        List<String> data_to_print = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver;
        int count=1;

        for(String url:data()){
            driver = new ChromeDriver(options);
            if(attribute.equals("employmentType")){
                try{
                    driver.get(url);
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement employmentType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='css-19idom']/child::div[@data-automation-id='time']/dl/child::dd")));
                    System.out.println(count+": "+employmentType.getText());
                    data_to_print.add(employmentType.getText());

                }catch (Exception e){
                    try{
                        if(driver.findElement(By.xpath("//span[@data-automation-id='errorMessage']")).isDisplayed()){
                            System.out.println(count+": "+"Expired");
                            data_to_print.add("Expired");
                        }
                    }catch (Exception error){
                        System.out.println(count+": "+"Full time");
                        data_to_print.add("Full time");
                    }
                    finally {
                        driver.quit();
                    }
                    count++;
                }
            } else if (attribute.equals("url")) {
                try{
                    driver.get(url);
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    System.out.println(count+": "+driver.getCurrentUrl());
                    data_to_print.add(driver.getCurrentUrl());
                }catch (Exception error){
                    System.out.println(count+": "+"Took more time to load");
                    data_to_print.add("Took more time to load");
                }
            }

        }
        try {
            excel.writeData_NewExcel(data_to_print);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
