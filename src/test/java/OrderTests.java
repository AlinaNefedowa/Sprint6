import data.OrderData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pageobject.ConfirmationPopup;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTests extends BaseTest { // Наследуемся от BaseTest

    // Метод, который предоставляет тестовые данные для параметризованного теста
    static Stream<OrderData> orderDataProvider() {
        return Stream.of(
                // Первый набор данных: точка входа "top" (верхняя кнопка "Заказать")
                new OrderData("top", "Иван", "Петров", "Москва, Тверская 1", "Чистые пруды", "89001234567",
                        "01.08.2025", "двое суток", "black", "Позвонить заранее"),

                // Второй набор данных: точка входа "bottom" (нижняя кнопка "Заказать")
                new OrderData("bottom", "Мария", "Сидорова", "Санкт-Петербург, Невский пр., 100", "Парк культуры", "89007654321",
                        "02.08.2025", "сутки", "grey", "Комментарий")
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    @DisplayName("Проверка успешного оформления заказа самоката")
    public void testOrderFlow(OrderData data) {
        MainPage mainPage = new MainPage(driver);
        if (data.entryPoint.equals("top")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstForm(data.name, data.surname, data.address, data.metro, data.phone);

        orderPage.fillSecondForm(data.date, data.rentalPeriod, data.color, data.comment);

        ConfirmationPopup orderConfirmationPopup = new ConfirmationPopup(driver);
        assertTrue(orderConfirmationPopup.isPopupVisible(), "Окно подтверждения заказа не появилось.");
        orderConfirmationPopup.confirmOrder();

        ConfirmationPopup orderSuccessPopup = new ConfirmationPopup(driver);
        assertTrue(orderSuccessPopup.isPopupVisible(), "Окно об успешном создании заказа не появилось.");
        assertTrue(orderSuccessPopup.getPopupHeaderText().contains("Заказ оформлен"),
                "Текст об успешном оформлении заказа отсутствует или некорректен.");

        orderSuccessPopup.clickViewStatus();
    }

}