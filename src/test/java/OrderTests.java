package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.ConfirmationPopup;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        driver.quit();
    }

    static Stream<OrderData> orderDataProvider() {
        return Stream.of(
                new OrderData("top", "Иван", "Петров", "Москва, Тверская 1", "Чистые пруды", "89001234567",
                        "01.08.2025", "двое суток", "black", "Позвонить заранее"),

                new OrderData("bottom", "Мария", "Сидорова", "Санкт-Петербург, Невский пр., 100", "Парк культуры", "89007654321",
                        "02.08.2025", "сутки", "grey", "")
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
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

        ConfirmationPopup popup = new ConfirmationPopup(driver);
        assertTrue(popup.isPopupVisible(), "Окно подтверждения не появилось.");
    }

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