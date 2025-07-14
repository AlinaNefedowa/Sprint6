import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pageobject.ConfirmationPopup;
import pageobject.MainPage;
import pageobject.OrderPage;
import data.OrderData;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTestsFirefox extends BaseTest { // Наследуемся от BaseTest

    public OrderTestsFirefox() {
        this.browserName = "firefox"; // Указываем, что этот класс тестов должен использовать Firefox
    }

    static Stream<OrderData> orderDataProvider() {
        return Stream.of(
                new OrderData("top", "Иван", "Петров", "Москва, Тверская 1", "Чистые пруды", "89001234567",
                        "01.08.2025", "двое суток", "black", "Позвонить заранее"),

                new OrderData("bottom", "Мария", "Сидорова", "Санкт-Петербург, Невский пр., 100", "Парк культуры", "89007654321",
                        "02.08.2025", "сутки", "grey", "коммент")
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    @DisplayName("Проверка успешного оформления заказа самоката в Firefox")
    public void testOrderFlow(OrderData data) {
        // Шаг 1: Нажимаем кнопку "Заказать"
        MainPage mainPage = new MainPage(driver); // driver доступен из BaseTest
        if (data.entryPoint.equals("top")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Шаг 2: Заполняем первую часть формы заказа
        OrderPage orderPage = new OrderPage(driver); // driver доступен из BaseTest
        orderPage.fillFirstForm(data.name, data.surname, data.address, data.metro, data.phone);

        // Шаг 3: Заполняем вторую часть формы заказа и нажимаем "Заказать"
        orderPage.fillSecondForm(data.date, data.rentalPeriod, data.color, data.comment);

        // Шаг 4: Взаимодействуем с поп-апом подтверждения
        ConfirmationPopup orderConfirmationPopup = new ConfirmationPopup(driver); // driver доступен из BaseTest
        assertTrue(orderConfirmationPopup.isPopupVisible(), "Окно подтверждения заказа не появилось.");
        orderConfirmationPopup.confirmOrder(); // Нажимаем "Да"

        // Шаг 5: Проверяем поп-ап успешного оформления заказа "Заказ оформлен"
        ConfirmationPopup orderSuccessPopup = new ConfirmationPopup(driver);
        assertTrue(orderSuccessPopup.isPopupVisible(), "Окно об успешном создании заказа не появилось.");
        assertTrue(orderSuccessPopup.getPopupHeaderText().contains("Заказ оформлен"),
                "Текст об успешном оформлении заказа отсутствует или некорректен.");

        // Шаг 6: Нажимаем "Посмотреть статус" для закрытия поп-апа
        orderSuccessPopup.clickViewStatus();
    }

}