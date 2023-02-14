package org.example.repository;

import org.example.model.BookAdditional;
import org.example.model.BookCenre;
import org.example.model.Profit;

import java.util.ArrayList;
import java.util.HashMap;

public interface BookRepository {
    int getCountOfSoldBooks();
    double getAllPriceOfSoldBooks();
    Profit getProfitByEmployee(long employeeId);
    ArrayList<BookAdditional> getCountOfSoldBooksByGenre();
    HashMap<BookCenre, Double> getPriceOfSoldBooksByGenre();
    BookCenre getMostPopularGenrelessTheAge (int age);
    BookCenre getMostPopularGenreMoreTheAge(int age);
}
