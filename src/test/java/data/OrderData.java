package data;

public class OrderData {
    public String entryPoint;
    public String name;       // Имя
    public String surname;    // Фамилия
    public String address;    // Адрес
    public String metro;      // Станция метро
    public String phone;      // Телефон
    public String date;       // Дата доставки
    public String rentalPeriod; // Срок аренды
    public String color;      // Цвет самоката
    public String comment;    // Комментарий для курьера

    // Конструктор для создания объекта data.OrderData
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
