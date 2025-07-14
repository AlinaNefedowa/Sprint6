import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import pageobject.FaqBlock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class FaqBlockTests extends BaseTest {

    private FaqBlock faqBlock;

    @BeforeEach
    public void setUp() {
        super.setup();
        faqBlock = new FaqBlock(driver);
    }

    @AfterEach
    public void tearDown() {
        super.teardown();
    }

    // Метод, предоставляющий данные для параметризованного теста
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
        faqBlock.clickQuestion(questionNumber); // Кликаем по вопросу через Page Object

        // Проверяем видимость ответа через Page Object
        assertTrue(faqBlock.isAnswerVisible(questionNumber), "Ответ должен быть видимым для вопроса " + questionNumber);

        // Получаем фактический текст ответа через Page Object
        String actualAnswer = faqBlock.getAnswerText(questionNumber).trim();

        // Сравниваем ожидаемый и фактический текст ответа
        assertEquals(expectedAnswer, actualAnswer, "Текст ответа не совпадает для вопроса " + questionNumber);
    }
}