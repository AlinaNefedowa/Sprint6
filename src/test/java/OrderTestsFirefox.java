import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.ConfirmationPopup;
import pageobject.MainPage;
import pageobject.OrderPage;
import java.time.Duration;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTestsFirefox {

    private WebDriver driver;

    @BeforeEach
    public void setup() {

        driver = new FirefoxDriver(); // Инициализируем FirefoxDriver напрямую
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
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
    @DisplayName("Проверка успешного оформления заказа самоката в Firefox") // Изменено название для Firefox
    public void testOrderFlow(OrderData data) {
        // Шаг 1: Нажимаем кнопку "Заказать"
        MainPage mainPage = new MainPage(driver);
        if (data.entryPoint.equals("top")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Шаг 2: Заполняем первую часть формы заказа
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstForm(data.name, data.surname, data.address, data.metro, data.phone);

        // Шаг 3: Заполняем вторую часть формы заказа и нажимаем "Заказать"
        orderPage.fillSecondForm(data.date, data.rentalPeriod, data.color, data.comment);

        // Шаг 4: Взаимодействуем с поп-апом подтверждения
        ConfirmationPopup orderConfirmationPopup = new ConfirmationPopup(driver);
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

    // Вложенный статический класс для хранения данных заказа
    static class OrderData {
        String entryPoint;
        String name;
        String surname;
        String address;
        String metro;
        String phone;
        String date;
        String rentalPeriod;
        String color;
        String comment;

        public OrderData(String entryPoint, String name, String surname, String address, String metro, String phone,
                         String date, String rentalPeriod, String color, String comment) {
            this.entryPoint = entryPoint;
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.metro = metro;
            this.phone = phone;
            this.date = date;
            this.rentalPeriod = rentalPeriod;
            this.color = color;
            this.comment = comment;
        }
    }
}
