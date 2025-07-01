package pageobject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPopup {
    private WebDriver driver;
    private WebDriverWait wait;
    private By popupLocator = By.className("Order_ModalHeader__3FDaJ");
    private By closeButton = By.xpath("//button[text()='Посмотреть статус']");

    public ConfirmationPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
    }

    public boolean isPopupVisible() {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.popupLocator));
        return this.driver.findElement(this.popupLocator).isDisplayed();
    }

    public String getPopupText() {
        return this.driver.findElement(this.popupLocator).getText();
    }

    public void closePopup() {
        this.driver.findElement(this.closeButton).click();
    }
}
