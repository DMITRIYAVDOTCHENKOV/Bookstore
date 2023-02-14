package org.example.model;

public class BookAdditional {
    private BookCenre genre;
    private int count;

    public BookAdditional(BookCenre genre, int count) {
        this.genre = genre;
        this.count = count;
    }

    public BookCenre getGenre() {
        return genre;
    }

    public void setGenre(BookCenre genre) {
        this.genre = genre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
