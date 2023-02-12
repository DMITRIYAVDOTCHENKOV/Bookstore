package org.example.model;

public class Book {
    private long id; //уникальный номер книги
    private String title; //Название книги
    private String author; // автор книги
    private double price; // цена на книгу
    private BookCenre genre; //жанр

    public Book(long id, String title, String author, double price, BookCenre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookCenre getGenre() {
        return genre;
    }

    public void setGenre(BookCenre genre) {
        this.genre = genre;
    }
}
