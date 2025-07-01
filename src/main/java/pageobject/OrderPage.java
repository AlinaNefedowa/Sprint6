package pageobject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
    private By orderButton = By.xpath("//button[text()='Заказать']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
    }

    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        this.driver.findElement(this.nameInput).sendKeys(new CharSequence[]{name});
        this.driver.findElement(this.surnameInput).sendKeys(new CharSequence[]{surname});
        this.driver.findElement(this.addressInput).sendKeys(new CharSequence[]{address});
        this.driver.findElement(this.metroInput).click();
        this.driver.findElement(By.xpath("//div[text()='" + metro + "']")).click();
        this.driver.findElement(this.phoneInput).sendKeys(new CharSequence[]{phone});
        this.driver.findElement(this.nextButton).click();
    }

    public void fillSecondForm(String date, String rentalPeriod, String color, String comment) {
        this.driver.findElement(this.dateInput).sendKeys(new CharSequence[]{date});
        this.driver.findElement(this.rentalPeriodDropdown).click();
        this.driver.findElement(By.xpath("//div[text()='" + rentalPeriod + "']")).click();
        if ("black".equalsIgnoreCase(color)) {
            this.driver.findElement(this.colorCheckboxBlack).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            this.driver.findElement(this.colorCheckboxGrey).click();
        }

        if (comment != null && !comment.isEmpty()) {
            this.driver.findElement(this.commentInput).sendKeys(new CharSequence[]{comment});
        }

        this.driver.findElement(this.orderButton).click();
    }
}
