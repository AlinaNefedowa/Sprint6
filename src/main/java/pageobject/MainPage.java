package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private WebDriver driver;
    private By topOrderButton = By.cssSelector(".Button_Button__ra12g");
    private By bottomOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTopOrderButton() {
        this.driver.findElement(this.topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        WebElement button = this.driver.findElement(this.bottomOrderButton);
        ((JavascriptExecutor)this.driver).executeScript("arguments[0].scrollIntoView(true);", new Object[]{button});
        button.click();
    }
}