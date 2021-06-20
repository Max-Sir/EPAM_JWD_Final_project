package by.sir.max.library.service.impl;

import by.sir.max.library.dao.book.BookInstanceDAO;
import by.sir.max.library.dao.book.BookOrderDAO;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.entity.order.OrderStatus;
import by.sir.max.library.exception.LibraryDAOException;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.exception.UtilException;
import by.sir.max.library.factory.DAOFactory;
import by.sir.max.library.factory.UtilFactory;
import by.sir.max.library.service.BookOrderService;
import by.sir.max.library.util.EmailDistributorUtil;
import by.sir.max.library.util.EmailMessageLocalizationDispatcher;
import by.sir.max.library.util.EmailMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class BookOrderServiceImpl implements BookOrderService {
    private static final Logger LOGGER = LogManager.getLogger(BookOrderServiceImpl.class);

    private final BookOrderDAO bookOrderDAO;
    private final BookInstanceDAO bookInstanceDAO;
    private final BookOrdersCache bookOrdersCache;
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailMessageLocalizationDispatcher;

    public BookOrderServiceImpl(){
        bookOrderDAO = DAOFactory.getInstance().getBookOrderDAO();
        bookInstanceDAO = DAOFactory.getInstance().getBookInstanceDAO();
        bookOrdersCache = BookOrdersCache.getInstance();
        emailDistributorUtil = UtilFactory.getInstance().getEmailDistributorUtil();
        emailMessageLocalizationDispatcher = UtilFactory.getInstance().getEmailMessageLocalizationDispatcher();
    }

    @Override
    public void addBookOrder(BookOrder bookOrder) throws LibraryServiceException {
        if (bookOrder == null) {
            LOGGER.warn("bookOrder is null");
            throw new LibraryServiceException("service.commonError");
        }
        List<String> availableBookInstanceUUIDs;
        try {
            String bookUUID = bookOrder.getBookInstance().getBook().getUuid();
            availableBookInstanceUUIDs = bookInstanceDAO.findAllAvailableBookInstanceUUIDsByBookUUID(bookUUID);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
        LibraryServiceException bookIsUnavailableException = new LibraryServiceException("query.bookInstance.find.empty");
        for (int i = 0; i < availableBookInstanceUUIDs.size(); i++) {
            try {
                bookOrder.setUuid(UUID.randomUUID().toString());
                bookOrder.getBookInstance().setUuid(availableBookInstanceUUIDs.get(i));
                bookOrderDAO.addBookOrder(bookOrder);
                BookOrdersCache.SortedUserBookOrdersCache currentUserCachedOrders = bookOrdersCache.get(bookOrder.getUser().getLogin());
                if (currentUserCachedOrders != null) {
                    currentUserCachedOrders.put(bookOrderDAO.findOrderByUUID(bookOrder.getUuid()));
                }
                return;
            } catch (LibraryDAOException e) {
                if (i == availableBookInstanceUUIDs.size() - 1) {
                    LOGGER.info(String.format("All books %s is already booked", bookOrder.getBookInstance().getBook()));
                    bookIsUnavailableException = new LibraryServiceException(e.getMessage(), e);
                } else {
                    LOGGER.info(String.format("Book instance %s is already booked", bookOrder.getBookInstance()));
                }
            }
        }
        throw bookIsUnavailableException;
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        if (bookOrder == null) {
            LOGGER.warn("bookOrder is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            if (bookOrder.getOrderStatus() == OrderStatus.CLOSE) {
                bookOrderDAO.cancelBookOrder(bookOrder);
            } else {
                bookOrderDAO.updateBookOrderStatus(bookOrder);
            }
            String author = bookOrder.getBookInstance().getBook().getAuthor().getAuthorName();
            String title = bookOrder.getBookInstance().getBook().getTitle();
            String status = bookOrder.getOrderStatus().name().replace('_', ' ').toLowerCase();
            String messageTitle = emailMessageLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_ORDER_STATUS_UPDATED);
            String messageText = emailMessageLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_ORDER_STATUS_UPDATED,title, author, status);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, bookOrder.getUser().getEmail());
            updateBookOrderCacheOrderStatus(bookOrder);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        } catch (UtilException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        }
    }

    @Override
    public List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOrdersByUserId(userId);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryServiceException {
        try {
            return bookOrderDAO.findOrdersByUserId(userId, currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int findOrdersQuantityByUserId(int userId) throws LibraryServiceException {
        try {
            return bookOrderDAO.findOrdersQuantityByUserId(userId);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<BookOrder> findAllOpenOrders(int currentPage, int recordsPerPage) throws LibraryServiceException {
        try {
            return bookOrderDAO.findAllOpenedOrders(currentPage, recordsPerPage);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int findOpenOrdersQuantity() throws LibraryServiceException {
        try {
            return bookOrderDAO.findOpenOrdersQuantity();
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    public BookOrdersCache getBookOrdersCache() {
        return bookOrdersCache;
    }

    private void updateBookOrderCacheOrderStatus(BookOrder bookOrder) throws LibraryServiceException {
        BookOrdersCache.SortedUserBookOrdersCache currentUserCachedOrders = bookOrdersCache.get(bookOrder.getUser().getLogin());
        if (currentUserCachedOrders != null) {
            currentUserCachedOrders.getBookOrderByUUID(bookOrder.getUuid()).setOrderStatus(bookOrder.getOrderStatus());
        }
    }
}
