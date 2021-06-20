package by.sir.max.library.dto;

import by.sir.max.library.entity.book.Book;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private Book book;
    private int totalBooksQuantity;
    private int totalAvailableBooksQuantity;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getTotalBooksQuantity() {
        return totalBooksQuantity;
    }

    public void setTotalBooksQuantity(int totalBooksQuantity) {
        this.totalBooksQuantity = totalBooksQuantity;
    }

    public int getTotalAvailableBooksQuantity() {
        return totalAvailableBooksQuantity;
    }

    public void setTotalAvailableBooksQuantity(int totalAvailableBooksQuantity) {
        this.totalAvailableBooksQuantity = totalAvailableBooksQuantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "book=" + book +
                ", totalBooksQuantity=" + totalBooksQuantity +
                ", totalAvailableBooksQuantity=" + totalAvailableBooksQuantity +
                '}';
    }
}
