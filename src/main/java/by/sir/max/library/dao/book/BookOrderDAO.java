package by.sir.max.library.dao.book;

import by.sir.max.library.entity.book.BookInstance;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryDAOException;

import java.util.List;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link BookOrder} entity
 */
public interface BookOrderDAO {

    /**
     * Saves the {@link BookOrder} into data source
     * Set {@link BookInstance} status to unavailable
     * Throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     *
     * @param bookOrder the {@link BookOrder} that must be added to data source
     * @throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     */
    void addBookOrder(BookOrder bookOrder) throws LibraryDAOException;

    /**
     * Updates the {@link BookOrder}'s status in a data source.
     * Throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     *
     * @param bookOrder the {@link BookOrder} that must be updated to data source
     * @throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     */
    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryDAOException;

    /**
     * Updates the {@link BookOrder}'s status in a data source to cancel.
     * Set {@link BookInstance} status to available
     * Throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     *
     * @param bookOrder the {@link BookOrder} that must be updated to data source
     * @throws LibraryDAOException if an error occurs while writing a <tt>bookOrder</tt>
     */
    void cancelBookOrder(BookOrder bookOrder) throws LibraryDAOException;

    /**
     * Retrieves and returns the {@link BookOrder} by uuid
     * If no such BookOrder contains into data source returns throws
     * LibraryDAOException if an error occurs while getting a <tt>BookOrder</tt>
     *
     * @param uuid the {@link BookOrder}'s uuid
     * @throws LibraryDAOException if an error occurs while adding a <tt>BookOrder</tt>
     * @return {@link BookOrder} by founded uuid
     */
    BookOrder findOrderByUUID(String uuid) throws LibraryDAOException;

    /**
     * Returns the <tt>BookOrders quantity</tt> by <tt>userId</tt>
     * If no such BookOrder contains into data source returns throws
     * LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     *
     * @param userId userId is the {@link User}'s id, {@link BookOrder} field
     * @return a BookOrders quantity of userId
     * @throws LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt> quantity
     */
    int findOrdersQuantityByUserId(int userId) throws LibraryDAOException;

    /**
     * Returns the {@link BookOrder} list
     * If no such BookOrder contains into data source returns emptyList
     * LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     *
     * @param userId is the {@link User}'s id, {@link BookOrder} field
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return {@link BookOrder}
     * @throws LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     */
    List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryDAOException;

    /**
     * Returns the {@link BookOrder} list
     * If no such BookOrder contains into data source returns emptyList
     * LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     *
     * @param userId is the {@link User}'s id, {@link BookOrder} field
     * @return {@link BookOrder} list
     * @throws LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     */
    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryDAOException;

    /**
     * Returns the opened <tt>BookOrders quantity</tt>
     * If no such BookOrder contains into data source returns throws
     * LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     *
     * @return a opened BookOrders quantity
     * @throws LibraryDAOException if an error occurs while getting a opened <tt>BookOrders</tt> quantity
     */
    int findOpenOrdersQuantity() throws LibraryDAOException;

    /**
     * Returns the {@link BookOrder} list which with open status
     * If no such BookOrder contains into data source returns emptyList
     * LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return {@link BookOrder} list which with open status
     * @throws LibraryDAOException if an error occurs while getting a <tt>BookOrders</tt>
     */
    List<BookOrder> findAllOpenedOrders(int currentPage, int recordsPerPage) throws LibraryDAOException;
}
