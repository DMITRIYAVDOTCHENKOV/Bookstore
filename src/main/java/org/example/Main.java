package org.example;

import org.example.model.*;

import java.util.ArrayList;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initData();
        String booksInfo = String.format("Общее кол-во проданных книг %d на сумму %f",getCountOfSoldBooks(),
                getAllPriceOfSoldBooks());
        System.out.println(booksInfo);

    }

    /**
     * получить общую сумму заказов
     *
     * @return
     */
    public static double getAllPriceOfSoldBooks() {
        double price = 0;
        for (Order order : orders) {
            price += getPriceOfSoldBooksInOrder(order);
        }
        return price;
    }

    /**
     * получить общую стоимость одного заказа
     *
     * @param order заказ по которому считается стоимость
     * @return общая стоимость для всех  проданных книг
     */
    public static double getPriceOfSoldBooksInOrder(Order order) {
        double price = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null) {
                price += book.getPrice();
            }
        }
        return price;
    }

    /**
     * Получаем общее кол-во проданных книг
     *
     * @return
     */
    public static int getCountOfSoldBooks() {
        int count = 0;
        for (Order order : orders) {
            count += order.getBooks().length;
        }
        return count;
    }

    /**
     * Ищим нужную нам книгу по уникальному номеру
     * ,если номер совпадает, то книга найдена, и выходим
     *
     * @param id уникальный номер книжки
     * @return выдаем найденную книгу
     */
    public static Book getBookById(long id) {
        Book current = null;
        for (Book book : books) {
            if (book.getId() == id) {
                current = book;
                break;
            }
        }
        return current;
    }

    public static void initData() {
        //продавцы
        employees.add(new Employee(1, "Иванов Иван", 32));
        employees.add(new Employee(2, "Петров Петр", 36));
        employees.add(new Employee(3, "Васильева Алиса", 28));

        //покупатели
        customers.add(new Customer(1, "Сидоров Алексей", 43));
        customers.add(new Customer(2, "Романов Дмитрий", 56));
        customers.add(new Customer(3, "Симинов Керри", 19));
        customers.add(new Customer(4, "Кириенко Данил", 43));
        customers.add(new Customer(5, "Воронцова Элина", 25));

        //книги
        books.add(new Book(1, "Война и Мир", "Толстой Лев", 1600, BookCenre.Art));
        books.add(new Book(2, "Преступление и наказание", "Достоевский Федор", 600, BookCenre.Art));
        books.add(new Book(3, "Мертвые души", "Гоголь Николай", 760, BookCenre.Art));
        books.add(new Book(4, "Руслан и Людмила", "Пушкин Александр", 3000, BookCenre.Art));
        books.add(new Book(5, "Введение в психоанализ", "Фрейд Зигмунд", 2600, BookCenre.Psychology));
        books.add(new Book(6, "Психология влияния. Убеждай. Воздействуй. Защишайся", "Чалдин Роберт", 1300, BookCenre.Psychology));
        books.add(new Book(7, "Как перестать беспокоится и начать жить", "Карнеги Дейл", 5000, BookCenre.Psychology));
        books.add(new Book(8, "Gang of Four", "Гамма Эрих", 570, BookCenre.Programming));
        books.add(new Book(9, "Совершенный код", "Макконели Стив", 3570, BookCenre.Programming));
        books.add(new Book(10, "Рефакторинг и Улучшение существующего кода", "Фауэл Мартин", 1700, BookCenre.Programming));
        books.add(new Book(11, "Алгоритмы. Построение и анализ", "Корман Томас", 6000, BookCenre.Programming));

        //Заказы
        orders.add(new Order(1, 1, 1, new long[]{8, 9, 10, 11}));
        orders.add(new Order(1, 2, 2, new long[]{1}));
        orders.add(new Order(1, 2, 3, new long[]{5, 6, 7}));
        orders.add(new Order(2, 2, 4, new long[]{1, 2, 3, 4}));
        orders.add(new Order(2, 3, 5, new long[]{2, 5, 9}));


    }
}