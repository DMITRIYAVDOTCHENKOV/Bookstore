package org.example.repository.datasource;

import org.example.model.*;


import java.util.ArrayList;
import java.util.List;

public class BookDataSourceImpl implements BookDataSource {
    private static final List<Book> books = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();

    public BookDataSourceImpl() {initData();}

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

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }
}
