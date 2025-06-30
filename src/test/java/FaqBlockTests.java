import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.FaqBlock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class FaqBlockTests {

    private WebDriver driver;
    private FaqBlock faqBlock;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/"); // замени на реальный URL
        faqBlock = new FaqBlock(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    static Stream<Arguments> faqDataProvider() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }

    @ParameterizedTest
    @MethodSource("faqDataProvider")
    public void testAnswerText(int questionNumber, String expectedAnswer) {
        faqBlock.clickQuestion(questionNumber);
        // Явное ожидание появления видимого ответа:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("accordion__panel-" + questionNumber)
        ));
        assertTrue(faqBlock.isAnswerVisible(questionNumber), "Ответ должен быть видимым для вопроса " + questionNumber);
        String actualAnswer = faqBlock.getAnswerText(questionNumber).trim();

        // Можно делать более гибкое сравнение, если нужно — например, contains, игнорируя пробелы и переносы
        assertEquals(expectedAnswer, actualAnswer, "Текст ответа не совпадает для вопроса " + questionNumber);
    }
}
