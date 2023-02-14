package org.example;

import org.example.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initData();
        String booksInfo = String.format("Общее кол-во проданных книг %d на сумму %f", getCountOfSoldBooks(),
                getAllPriceOfSoldBooks());
        System.out.println(booksInfo);

        for (Employee employee : employees) {
            System.out.println(employee.getName() + " продал(а) " + getProfitByEmployee(employee.getId()).toString());
        }
        ArrayList<BookAdditional> soldBooksCount = getCountOfSoldBooksByGenre();//кол-во книг
        HashMap<BookCenre, Double> soldBookPrice = getPriceOfSoldBooksByGenre();//кол-во жанров

        String soldBookSrt = "По жанру: %s продано %d книг(и) общей стоимостью %f";

        //
        for (BookAdditional bookAdditional : soldBooksCount) {
            double price = soldBookPrice.get(bookAdditional.getGenre());
            System.out.printf((soldBookSrt) + "%n", bookAdditional.getGenre().name(), bookAdditional.getCount(),
                    price);
        }
        int age = 30;
        String analyzeGenreSrt = "Покупатели младше %d лет выбирают жанр %s";
        System.out.printf((analyzeGenreSrt) + "%n",30,getMostPopularGenrelessTheAge(30));


        String analyzeGenreSrt2 = "Покупатели старше %d лет выбирают жанр %s";
        System.out.printf((analyzeGenreSrt2) + "%n",30,getMostPopularGenreMoreTheAge(30));

    }

    /**
     * Получить наиболее популярный жанр для заказчиков младше возраста #age
     *
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookCenre getMostPopularGenrelessTheAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<Long>();

        for (Customer customer : customers) {
            if (customer.getAge() < age) {
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }

    /**
     * Получить наименее популярный жанр для заказчиков старше возраста #age
     *
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookCenre getMostPopularGenreMoreTheAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<Long>();

        for (Customer customer : customers) {
            if (customer.getAge() > age) {
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }

    private static BookCenre getMostPopularBookGenre(ArrayList<Long> customersIds) {
        int countArt = 0, countPr = 0, countPs = 0;

        for (Order order : orders) {
            if (customersIds.contains(order.getCustomerId())) {
                countArt += getCountOfSoldBooksByGenre(order, BookCenre.Art);
                countPr += getCountOfSoldBooksByGenre(order, BookCenre.Programming);
                countPs += getCountOfSoldBooksByGenre(order, BookCenre.Psychology);
            }
        }
        ArrayList<BookAdditional> result = new ArrayList<>();
        result.add(new BookAdditional(BookCenre.Art, countArt));
        result.add(new BookAdditional(BookCenre.Programming, countPr));
        result.add(new BookAdditional(BookCenre.Psychology, countPs));
        result.sort(new Comparator<BookAdditional>() {
            @Override
            public int compare(BookAdditional left, BookAdditional right) {
                return right.getCount() - left.getCount();
            }
        });
        return result.get(0).getGenre();
    }

    public static ArrayList<BookAdditional> getCountOfSoldBooksByGenre() {
        ArrayList<BookAdditional> result = new ArrayList<>();
        int countArt = 0, countPr = 0, countPs = 0;
        for (Order order : orders) {
            countArt += getCountOfSoldBooksByGenre(order, BookCenre.Art);
            countPr += getCountOfSoldBooksByGenre(order, BookCenre.Programming);
            countPs += getCountOfSoldBooksByGenre(order, BookCenre.Psychology);
        }
        result.add(new BookAdditional(BookCenre.Art, countArt));
        result.add(new BookAdditional(BookCenre.Programming, countPr));
        result.add(new BookAdditional(BookCenre.Psychology, countPs));

        return result;
    }

    /**
     * Получить кол-во книг в одном заказе по определенному жанру
     *
     * @param order заказ
     * @param genre жанр
     * @return кол-во книг
     */
    public static int getCountOfSoldBooksByGenre(Order order, BookCenre genre) {
        int count = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre) {
                count++;
            }
        }
        return count;
    }


    /**
     * Получаем стоимость проданных книг по жанру
     *
     * @return
     */
    public static HashMap<BookCenre, Double> getPriceOfSoldBooksByGenre() {
        HashMap<BookCenre, Double> result = new HashMap<>();
        double priceArt = 0, pricePr = 0, pricePs = 0;

        for (Order order : orders) {
            priceArt += getPriceOfSoldBooksByGenre(order, BookCenre.Art);
            pricePr += getPriceOfSoldBooksByGenre(order, BookCenre.Programming);
            pricePs += getPriceOfSoldBooksByGenre(order, BookCenre.Psychology);
        }
        result.put(BookCenre.Art, priceArt);
        result.put(BookCenre.Programming, pricePr);
        result.put(BookCenre.Psychology, pricePs);
        return result;
    }

    /**
     * Получить общую сумму книг в одном заказе по определенному жанру
     *
     * @param order заказ
     * @param genre жанр
     * @return общая стоимость
     */
    public static double getPriceOfSoldBooksByGenre(Order order, BookCenre genre) {
        double price = 0;

        for (long bookId : order.getBooks()) {
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre) {
                price += book.getPrice();
            }
        }
        return price;
    }


    /**
     * Получить общее код-во и общую стоимость товара для продовца
     *
     * @param employeeId уникальный номер продавца
     * @return кол-во и общую стоимость указанного продавцы
     */
    public static Profit getProfitByEmployee(long employeeId) {
        int count = 0;
        double price = 0;
        for (Order order : orders) {
            if (order.getEmployeeId() == employeeId) {
                price += getPriceOfSoldBooksInOrder(order);
                count += order.getBooks().length;
            }
        }
        return new Profit(count, price);
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