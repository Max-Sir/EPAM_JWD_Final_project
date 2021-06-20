package by.sir.max.library.entity.book;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.entity.book.bookcomponent.Publisher;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private String uuid;
    private Genre genre;
    private BookLanguage bookLanguage;
    private Publisher publisher;
    private Author author;
    private String title;
    private int publishYear;
    private int pagesQuantity;
    private String description;

    public Book(BookBuilder builder) {
        this.uuid = builder.getUuid();
        this.genre = builder.getGenre();
        this.bookLanguage = builder.getBookLanguage();
        this.publisher = builder.getPublisher();
        this.author = builder.getAuthor();
        this.title = builder.getTitle();
        this.publishYear = builder.getPublishYear();
        this.pagesQuantity = builder.getPagesQuantity();
        this.description = builder.getDescription();
    }

    public Book() {}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BookLanguage getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(BookLanguage bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getPagesQuantity() {
        return pagesQuantity;
    }

    public void setPagesQuantity(int pagesQuantity) {
        this.pagesQuantity = pagesQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publishYear == book.publishYear &&
                pagesQuantity == book.pagesQuantity &&
                Objects.equals(uuid, book.uuid) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(bookLanguage, book.bookLanguage) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, genre, bookLanguage, publisher, author, title, publishYear, pagesQuantity, description);
    }

    @Override
    public String toString() {
        return "Book{" +
                "uuid='" + uuid + '\'' +
                ", genre=" + genre +
                ", language=" + bookLanguage +
                ", publisher=" + publisher +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", publishYear=" + publishYear +
                ", pagesQuantity=" + pagesQuantity +
                ", description='" + description + '\'' +
                '}';
    }
}
