package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AbstractComponents {

    protected WebDriverWait wait;
    public WebElement elementStore(WebDriver driver, By elem) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return wait.until(ExpectedConditions.presenceOfElementLocated(elem));
        } catch (Exception e) {
            throw new org.openqa.selenium.NoSuchElementException("Element not found " + elem, e);
        }
    }

    public List<WebElement> elementsStore(WebDriver driver,By elems){
        try{
            wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elems));
        } catch (Exception e) {
            throw new org.openqa.selenium.NoSuchElementException("Elements not found " + elems, e);
        }
    }

    public void clickElement(WebDriver driver,WebElement elem) {
        try {
            elem.click();
        } catch (Exception e) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(elem));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", elem);
        }

    }

    public WebElement waitUsingElement(WebDriver driver,By elem) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elem));
    }

    public boolean waitUsingAttribute(WebDriver driver,WebElement elem, String attr, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        return wait.until(ExpectedConditions.attributeToBe(elem, attr, value));
    }
}
