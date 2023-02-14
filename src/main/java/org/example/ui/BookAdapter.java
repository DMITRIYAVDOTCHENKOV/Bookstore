package org.example.ui;

import org.example.model.BookAdditional;
import org.example.model.BookCenre;
import org.example.model.Employee;
import org.example.repository.BookRepository;
import org.example.repository.datasource.BookDataSource;

import java.util.ArrayList;
import java.util.HashMap;

public class BookAdapter {
    private final BookRepository repository;
    private final BookDataSource bookDataSource;


    public BookAdapter(BookRepository repository, BookDataSource bookDataSource) {
        this.repository = repository;
        this.bookDataSource = bookDataSource;
    }

    public void show() {

        String booksInfo = String.format("Общее кол-во проданных книг %d на сумму %f", repository.getCountOfSoldBooks(),
                repository.getAllPriceOfSoldBooks());
        System.out.println(booksInfo);

        for (Employee employee : bookDataSource.getEmployees()) {
            System.out.println(employee.getName() + " продал(а) " + repository.getProfitByEmployee(employee.getId()).toString());
        }

        ArrayList<BookAdditional> soldBooksCount = repository.getCountOfSoldBooksByGenre();//кол-во книг
        HashMap<BookCenre, Double> soldBookPrice = repository.getPriceOfSoldBooksByGenre();//кол-во жанров

        String soldBookSrt = "По жанру: %s продано %d книг(и) общей стоимостью %f";

        //
        for (BookAdditional bookAdditional : soldBooksCount) {
            double price = soldBookPrice.get(bookAdditional.getGenre());
            System.out.printf((soldBookSrt) + "%n", bookAdditional.getGenre().name(), bookAdditional.getCount(),
                    price);
        }

        int age = 30;
        String analyzeGenreSrt = "Покупатели младше %d лет выбирают жанр %s";
        System.out.printf((analyzeGenreSrt) + "%n", 30, repository.getMostPopularGenrelessTheAge(30));


        String analyzeGenreSrt2 = "Покупатели старше %d лет выбирают жанр %s";
        System.out.printf((analyzeGenreSrt2) + "%n", 30, repository.getMostPopularGenreMoreTheAge(30));

    }
}
