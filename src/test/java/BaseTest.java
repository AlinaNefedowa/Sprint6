import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import config.Config; // Импорт класса Config

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    protected String browserName = System.getProperty("browser", "chrome"); //

    @BeforeEach
    public void setup() {
        if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.DEFAULT_TIMEOUT_SECONDS)); // Используем константу для таймаута
        driver.manage().window().maximize();
        driver.get(Config.BASE_URL);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
