package by.sir.max.library.factory;

import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.service.BookComponentService;
import by.sir.max.library.service.BookOrderService;
import by.sir.max.library.service.BookService;
import by.sir.max.library.service.impl.*;
import by.sir.max.library.service.UserService;
import by.sir.max.library.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService;
    private final BookService bookService;
    private final BookComponentService<Author> bookAuthorService;
    private final BookComponentService<Genre> bookGenreService;
    private final BookComponentService<BookLanguage> bookLanguageService;
    private final BookComponentService<Publisher> bookPublisherService;
    private final BookOrderService bookOrderService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        bookService = new BookServiceImpl();
        bookAuthorService = new BookAuthorServiceImpl();
        bookGenreService = new BookGenreServiceImpl();
        bookLanguageService = new BookLanguageServiceImpl();
        bookPublisherService = new BookPublisherServiceImpl();
        bookOrderService = new BookOrderServiceImpl();
    }

    private static class ServiceFactorySingletonHolder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return ServiceFactorySingletonHolder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public BookComponentService<Author> getBookAuthorService() {
        return bookAuthorService;
    }

    public BookComponentService<Genre> getBookGenreService() {
        return bookGenreService;
    }

    public BookComponentService<BookLanguage> getBookLanguageService() {
        return bookLanguageService;
    }

    public BookComponentService<Publisher> getBookPublisherService() {
        return bookPublisherService;
    }

    public BookOrderService getBookOrderService() {
        return bookOrderService;
    }
}
