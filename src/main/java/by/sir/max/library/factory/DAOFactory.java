package by.sir.max.library.factory;

import by.sir.max.library.dao.book.*;
import by.sir.max.library.dao.book.impl.*;
import by.sir.max.library.dao.user.UserDAO;
import by.sir.max.library.dao.user.impl.UserDAOImpl;
import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.entity.book.bookcomponent.Publisher;

public class DAOFactory {
    private final UserDAO userDAO;
    private final BookComponentDAO<Author> bookAuthorDAO;
    private final BookComponentDAO<Genre> bookGenreDAO;
    private final BookComponentDAO<Publisher> bookPublisherDAO;
    private final BookComponentDAO<BookLanguage> bookLanguageDAO;
    private final BookInstanceDAO bookInstanceDAO;
    private final BookDAO bookDAO;
    private final BookOrderDAO bookOrderDAO;

    private DAOFactory(){
        userDAO = new UserDAOImpl();
        bookAuthorDAO = new BookAuthorDAOImpl();
        bookLanguageDAO = new BookLanguageDAOImpl();
        bookPublisherDAO = new BookPublisherDAOImpl();
        bookGenreDAO = new BookGenreDAOImpl();
        bookInstanceDAO = new BookInstanceDAOImpl();
        bookDAO = new BookDAOImpl();
        bookOrderDAO = new BookOrderDAOImpl();
    }

    private static class DAOFactorySingletonHolder {
        static final DAOFactory INSTANCE = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactorySingletonHolder.INSTANCE;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public BookComponentDAO<Author> getBookAuthorDAO() {
        return bookAuthorDAO;
    }

    public BookComponentDAO<Genre> getBookGenreDAO() {
        return bookGenreDAO;
    }

    public BookComponentDAO<BookLanguage> getBookLanguageDAO() {
        return bookLanguageDAO;
    }

    public BookComponentDAO<Publisher> getBookPublisherDAO() {
        return bookPublisherDAO;
    }

    public BookInstanceDAO getBookInstanceDAO() {
        return bookInstanceDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public BookOrderDAO getBookOrderDAO() {
        return bookOrderDAO;
    }
}
