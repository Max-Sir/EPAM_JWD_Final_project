package by.sir.max.library.builder;

import by.sir.max.library.entity.book.*;
import by.sir.max.library.entity.book.bookcomponent.*;

public class BookBuilder {
    private String uuid;
    private Genre genre;
    private BookLanguage bookLanguage;
    private Publisher publisher;
    private Author author;
    private String title;
    private int publishYear;
    private int pagesQuantity;
    private String description;

    public String getUuid() {
        return uuid;
    }

    public BookBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookBuilder setGenreUUID(String uuid) {
        defineGenreIfNull();
        genre.setUuid(uuid);
        return this;
    }

    public BookBuilder setGenreTitle(String genreTitle) {
        defineGenreIfNull();
        this.genre.setGenreTitle(genreTitle);
        return this;
    }

    public BookLanguage getBookLanguage() {
        return bookLanguage;
    }

    public BookBuilder setBookLanguage(BookLanguage bookLanguage) {
        this.bookLanguage = bookLanguage;
        return this;
    }

    public BookBuilder setBookLanguageUUID(String uuid) {
        defineBookLanguageIfNull();
        bookLanguage.setUuid(uuid);
        return this;
    }

    public BookBuilder setBookLanguageTitle(String bookLanguage) {
        defineBookLanguageIfNull();
        this.bookLanguage.setLanguageTitle(bookLanguage);
        return this;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public BookBuilder setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder setPublisherUUID(String uuid) {
        definePublisherIfNull();
        publisher.setUuid(uuid);
        return this;
    }

    public BookBuilder setPublisherTitle(String publisher) {
        definePublisherIfNull();
        this.publisher.setPublisherTitle(publisher);
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder setAuthorUUID(String uuid) {
        defineAuthorIfNull();
        author.setUuid(uuid);
        return this;
    }

    public BookBuilder setAuthorName(String authorName) {
        defineAuthorIfNull();
        this.author.setAuthorName(authorName);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public BookBuilder setPublishYear(int publishYear) {
        this.publishYear = publishYear;
        return this;
    }

    public int getPagesQuantity() {
        return pagesQuantity;
    }

    public BookBuilder setPagesQuantity(int pagesQuantity) {
        this.pagesQuantity = pagesQuantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Book build() {
        return new Book(this);
    }

    private void defineGenreIfNull() {
        if (genre == null) {
            genre = new Genre();
        }
    }

    private void defineAuthorIfNull() {
        if (author == null) {
            author = new Author();
        }
    }

    private void definePublisherIfNull() {
        if (publisher == null) {
            publisher = new Publisher();
        }
    }

    private void defineBookLanguageIfNull() {
        if (bookLanguage == null) {
            bookLanguage = new BookLanguage();
        }
    }
}
