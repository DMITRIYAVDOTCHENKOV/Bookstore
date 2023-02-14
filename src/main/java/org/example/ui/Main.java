package org.example.ui;

import org.example.model.*;
import org.example.repository.BookRepository;
import org.example.repository.BookRepositoryImpl;
import org.example.repository.datasource.BookDataSource;
import org.example.repository.datasource.BookDataSourceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;

public class Main {


    public static void main(String[] args) {
        BookDataSource bookDataSource = new BookDataSourceImpl();
        BookRepository bookRepository = new BookRepositoryImpl(bookDataSource);
        BookAdapter adapter = new BookAdapter(bookRepository, bookDataSource);
        adapter.show();
    }
}