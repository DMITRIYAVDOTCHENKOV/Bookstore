package org.example.repository;

import org.example.model.*;
import org.example.repository.datasource.BookDataSource;
import org.example.repository.datasource.BookDataSourceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class BookRepositoryImpl implements BookRepository{
    private final BookDataSource bookDataSource;

    public BookRepositoryImpl(BookDataSource bookDataSource) {
        this.bookDataSource = bookDataSource;
    }
    /**
     * Получить наиболее популярный жанр для заказчиков младше возраста #age
     *
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public BookCenre getMostPopularGenrelessTheAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<Long>();

        for (Customer customer : bookDataSource.getCustomers()) {
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
    public  BookCenre getMostPopularGenreMoreTheAge(int age) {
        ArrayList<Long> customersIds = new ArrayList<Long>();

        for (Customer customer : bookDataSource.getCustomers()) {
            if (customer.getAge() > age) {
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }
    private BookCenre getMostPopularBookGenre(ArrayList<Long> customersIds) {
        int countArt = 0, countPr = 0, countPs = 0;

        for (Order order : bookDataSource.getOrders()) {
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

    public  ArrayList<BookAdditional> getCountOfSoldBooksByGenre() {
        ArrayList<BookAdditional> result = new ArrayList<>();
        int countArt = 0, countPr = 0, countPs = 0;
        for (Order order : bookDataSource.getOrders()) {
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
    private int getCountOfSoldBooksByGenre(Order order, BookCenre genre) {
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
    public  HashMap<BookCenre, Double> getPriceOfSoldBooksByGenre() {
        HashMap<BookCenre, Double> result = new HashMap<>();
        double priceArt = 0, pricePr = 0, pricePs = 0;

        for (Order order : bookDataSource.getOrders()) {
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
    private double getPriceOfSoldBooksByGenre(Order order, BookCenre genre) {
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
    public  Profit getProfitByEmployee(long employeeId) {
        int count = 0;
        double price = 0;
        for (Order order : bookDataSource.getOrders()) {
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
    public  double getAllPriceOfSoldBooks() {
        double price = 0;
        for (Order order : bookDataSource.getOrders()) {
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
    public double getPriceOfSoldBooksInOrder(Order order) {
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
    public  int getCountOfSoldBooks() {
        int count = 0;
        for (Order order : bookDataSource.getOrders()) {
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
    private Book getBookById(long id) {
        Book current = null;
        for (Book book : bookDataSource.getBooks()) {
            if (book.getId() == id) {
                current = book;
                break;
            }
        }
        return current;
    }
}
