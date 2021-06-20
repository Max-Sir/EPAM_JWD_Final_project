package by.sir.max.library.service.impl;

import by.sir.max.library.dao.book.BookComponentDAO;
import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.exception.LibraryDAOException;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.exception.ValidatorException;
import by.sir.max.library.factory.DAOFactory;
import by.sir.max.library.factory.ValidatorFactory;
import by.sir.max.library.service.BookComponentService;
import by.sir.max.library.validatior.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookAuthorServiceImpl implements BookComponentService<Author> {
    private static final Logger LOGGER = LogManager.getLogger(BookAuthorServiceImpl.class);

    private final BookComponentDAO<Author> bookAuthorDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookAuthorServiceImpl() {
        bookAuthorDAO = DAOFactory.getInstance().getBookAuthorDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Author author) throws LibraryServiceException {
        if (author == null) {
            LOGGER.warn("author is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            bookValidator.validateAuthor(author.getAuthorName());
            author.defineUUID();
            bookAuthorDAO.add(author);
            bookComponentsCache.getAuthors().put(author);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Author findByUUID(String uuid) throws LibraryServiceException {
        if (uuid == null) {
            LOGGER.warn("uuid is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookAuthorDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
