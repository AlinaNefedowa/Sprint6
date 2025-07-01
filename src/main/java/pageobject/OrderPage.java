package pageobject;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By nameInput = By.cssSelector("input[placeholder='* Имя']");
    private By surnameInput = By.cssSelector("input[placeholder='* Фамилия']");
    private By addressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private By metroInput = By.cssSelector("input[placeholder='* Станция метро']");
    private By phoneInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");
    private By dateInput = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-placeholder");
    private By colorCheckboxBlack = By.id("black");
    private By colorCheckboxGrey = By.id("grey");
    private By commentInput = By.cssSelector("input[placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);

        // Ввод текста и ожидание элемента в выпадающем списке
        driver.findElement(metroInput).sendKeys(metro);

       // Ждём, пока появится нужная станция метро
        By metroOption = By.xpath("//button//div[text()='" + metro + "']");
        wait.until(ExpectedConditions.elementToBeClickable(metroOption));

        // Скроллим к элементу и кликаем
        WebElement metroElement = driver.findElement(metroOption);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroElement);
        metroElement.click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillSecondForm(String date, String rentalPeriod, String color, String comment) {
        driver.findElement(dateInput).sendKeys(date + Keys.ENTER); // ✅ здесь ENTER

        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath("//div[text()='" + rentalPeriod + "']")).click();

        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(colorCheckboxBlack).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(colorCheckboxGrey).click();
        }

        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentInput).sendKeys(comment);
        }

        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        WebElement orderBtn = driver.findElement(orderButton);
        orderBtn.click();
    }
}
