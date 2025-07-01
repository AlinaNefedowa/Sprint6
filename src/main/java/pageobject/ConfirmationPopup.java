package pageobject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPopup {
    private WebDriver driver;
    private WebDriverWait wait;
    // Локатор для заголовка поп-апа подтверждения заказа
    private By popupHeaderLocator = By.className("Order_ModalHeader__3FDaJ");
    // Локатор для кнопки "Да" (подтвердить заказ)
    private By confirmOrderButton = By.xpath("//button[text()='Да']"); // Убедись, что текст кнопки "Да"
    // Локатор для кнопки "Посмотреть статус" (после успешного создания заказа)
    private By viewStatusButton = By.xpath("//button[text()='Посмотреть статус']");


    public ConfirmationPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
    }

    // Метод для ожидания появления поп-апа подтверждения
    public boolean isPopupVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupHeaderLocator));
        return driver.findElement(popupHeaderLocator).isDisplayed();
    }

    // Метод для получения текста заголовка поп-апа
    public String getPopupHeaderText() {
        return driver.findElement(popupHeaderLocator).getText();
    }

    // Метод для клика по кнопке "Да" (подтвердить заказ)
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        driver.findElement(confirmOrderButton).click();
    }

    // Метод для клика по кнопке "Посмотреть статус" (после успешного создания заказа)
    public void clickViewStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(viewStatusButton));
        driver.findElement(viewStatusButton).click();
    }
}