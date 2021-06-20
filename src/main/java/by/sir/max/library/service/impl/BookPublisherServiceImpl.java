package by.sir.max.library.service.impl;

import by.sir.max.library.dao.book.BookComponentDAO;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.exception.LibraryDAOException;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.exception.ValidatorException;

import by.sir.max.library.factory.DAOFactory;
import by.sir.max.library.factory.ValidatorFactory;
import by.sir.max.library.service.BookComponentService;
import by.sir.max.library.validatior.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookPublisherServiceImpl implements BookComponentService<Publisher> {
    private static final Logger LOGGER = LogManager.getLogger(BookPublisherServiceImpl.class);

    private final BookComponentDAO<Publisher> bookPublisherDAO;
    private final BookValidator bookValidator;
    private final CommonBookComponentsCache bookComponentsCache;

    public BookPublisherServiceImpl() {
        bookPublisherDAO = DAOFactory.getInstance().getBookPublisherDAO();
        bookValidator = ValidatorFactory.getInstance().getBookValidator();
        bookComponentsCache = CommonBookComponentsCache.getInstance();
    }

    @Override
    public void add(Publisher publisher) throws LibraryServiceException {
        if (publisher == null) {
            LOGGER.warn("publisher is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            bookValidator.validatePublisher(publisher.getPublisherTitle());
            publisher.defineUUID();
            bookPublisherDAO.add(publisher);
            bookComponentsCache.getPublishers().put(publisher);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Publisher findByUUID(String uuid) throws LibraryServiceException {
        if (uuid == null) {
            LOGGER.warn("uuid is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            return bookPublisherDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }
}
