package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.ConfirmationPopup; // Make sure this import is correct and the class is updated
import pageobject.MainPage;
import pageobject.OrderPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals; // Added for more specific assertions

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTests {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) { // Added a null check for robustness
            driver.quit();
        }
    }

    static Stream<OrderData> orderDataProvider() {
        return Stream.of(
                new OrderData("top", "Иван", "Петров", "Москва, Тверская 1", "Чистые пруды", "89001234567",
                        "01.08.2025", "двое суток", "black", "Позвонить заранее"),

                new OrderData("bottom", "Мария", "Сидорова", "Санкт-Петербург, Невский пр., 100", "Парк культуры", "89007654321",
                        "02.08.2025", "сутки", "grey", "Комментарий")
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    @DisplayName("Проверка успешного оформления заказа самоката") // Add a display name for clarity
    public void testOrderFlow(OrderData data) {
        // Step 1: Click the appropriate order button based on the entry point
        MainPage mainPage = new MainPage(driver);
        if (data.entryPoint.equals("top")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Step 2: Fill the first part of the order form
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstForm(data.name, data.surname, data.address, data.metro, data.phone);

        // Step 3: Fill the second part of the order form and click "Заказать"
        orderPage.fillSecondForm(data.date, data.rentalPeriod, data.color, data.comment);

        // Step 4: Interact with the "Do you want to place an order?" confirmation popup
        ConfirmationPopup orderConfirmationPopup = new ConfirmationPopup(driver);
        // Assert that the confirmation popup is visible
        assertTrue(orderConfirmationPopup.isPopupVisible(), "Окно подтверждения заказа не появилось.");
        // Click "Да" to confirm the order
        orderConfirmationPopup.confirmOrder();

        // Step 5: Verify the "Order has been placed" success popup
        // We can reuse the same ConfirmationPopup object if its methods are generic enough
        // Alternatively, create a new instance if there's distinct behavior
        ConfirmationPopup orderSuccessPopup = new ConfirmationPopup(driver); // Reusing for clarity
        assertTrue(orderSuccessPopup.isPopupVisible(), "Окно об успешном создании заказа не появилось.");
        // Assert that the success popup text contains "Заказ оформлен"
        assertTrue(orderSuccessPopup.getPopupHeaderText().contains("Заказ оформлен"),
                "Текст об успешном оформлении заказа отсутствует или некорректен.");

        // Step 6: Click "Посмотреть статус" to close the success popup (optional, but good practice)
        orderSuccessPopup.clickViewStatus();
    }

    // Nested static class to hold order data for parameterized tests
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