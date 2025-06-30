package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FaqBlock {
    private WebDriver driver;

    // Локаторы вопросов
    private By question1 = By.id("accordion__heading-0");
    private By question2 = By.id("accordion__heading-1");
    private By question3 = By.id("accordion__heading-2");
    private By question4 = By.id("accordion__heading-3");
    private By question5 = By.id("accordion__heading-4");
    private By question6 = By.id("accordion__heading-5");
    private By question7 = By.id("accordion__heading-6");
    private By question8 = By.id("accordion__heading-7");


    // Локаторы ответов
    private By answer1 = By.id("accordion__panel-0");
    private By answer2 = By.id("accordion__panel-1");
    private By answer3 = By.id("accordion__panel-2");
    private By answer4 = By.id("accordion__panel-3");
    private By answer5 = By.id("accordion__panel-4");
    private By answer6 = By.id("accordion__panel-5");
    private By answer7 = By.id("accordion__panel-6");
    private By answer8 = By.id("accordion__panel-7");


    public FaqBlock(WebDriver driver) {
        this.driver = driver;
    }

    // Клик по вопросу по номеру
    public void clickQuestion(int questionNumber) {
        // 1. Создаём локатор для вопроса с нужным номером
        By questionLocator = By.id("accordion__heading-" + questionNumber);

        // 2. Находим сам элемент на странице по локатору
        WebElement element = driver.findElement(questionLocator);

        // 3. Скроллим браузер так, чтобы элемент был виден на экране
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        // 4. Создаём явное ожидание — ждём, пока элемент станет кликабельным (видимым и доступным для клика)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(questionLocator));

        // 5. Кликаем по элементу
        element.click();
    }

    // Получить текст ответа по номеру
    public String getAnswerText(int questionNumber) {
        By answerLocator = By.id("accordion__panel-" + questionNumber);
        WebElement answerElement = driver.findElement(answerLocator);
        return answerElement.getText();
    }

    // Проверить, что ответ виден (открыт)
    public boolean isAnswerVisible(int questionNumber) {
        By answerLocator = By.id("accordion__panel-" + questionNumber);
        WebElement answerElement = driver.findElement(answerLocator);
        return answerElement.isDisplayed();
    }
}
