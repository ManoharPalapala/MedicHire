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
        m.getEmployment();

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




    public void getEmployment(){
        List<String> jobType = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver;
        int count=1;

        for(String url:data()){
            driver = new ChromeDriver(options);
            try{
                driver.get(url);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement employmentType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='css-19idom']/child::div[@data-automation-id='time']/dl/child::dd")));
                System.out.println(count+": "+employmentType.getText());
                jobType.add(employmentType.getText());

            }catch (Exception e){
                try{
                    if(driver.findElement(By.xpath("//span[@data-automation-id='errorMessage']")).isDisplayed()){
                        jobType.add("Expired");
                        System.out.println(count+": "+"Expired");
                    }
                }catch (Exception error){
                    jobType.add("Full time");
                    System.out.println(count+": "+"Full time");
                }

            }finally {
                driver.quit();
            }
            count++;
        }
        try {
            excel.writeData_NewExcel(jobType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
