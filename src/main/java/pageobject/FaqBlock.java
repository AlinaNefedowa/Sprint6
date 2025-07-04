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
    private WebDriverWait wait;

    // Конструктор
    public FaqBlock(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Инициализируем WebDriverWait здесь
    }

    // Метод для получения локатора вопроса по его номеру
    private By getQuestionLocator(int questionNumber) {
        return By.id("accordion__heading-" + questionNumber);
    }

    // Метод для получения локатора ответа по его номеру
    private By getAnswerLocator(int questionNumber) {
        return By.id("accordion__panel-" + questionNumber);
    }

    // Клик по вопросу по номеру
    public void clickQuestion(int questionNumber) {
        By questionLocator = getQuestionLocator(questionNumber);

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));

        // Скроллим браузер так, чтобы элемент был виден на экране
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        element.click();
    }

    // Получить текст ответа по номеру
    public String getAnswerText(int questionNumber) {
        By answerLocator = getAnswerLocator(questionNumber);
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answerElement.getText();
    }

    // Проверить, что ответ виден
    public boolean isAnswerVisible(int questionNumber) {
        By answerLocator = getAnswerLocator(questionNumber);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator)).isDisplayed();
    }
}