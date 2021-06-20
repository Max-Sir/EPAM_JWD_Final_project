package by.sir.max.library.service;

import by.sir.max.library.entity.book.Book;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.service.impl.BookOrdersCache;

import java.util.List;

/**
 * Interface describes the behavior of {@link BookOrder} entity
 */
public interface BookOrderService {

    /**
     * Saves {@link BookOrder} into cache if user online and into database
     *
     * @param bookOrder {@link BookOrder} instance
     * @throws LibraryServiceException if <tt>bookOrder</tt> is null
     *                          or if an error occurs while writing new {@link BookOrder}
     *                          into data source
     */
    void addBookOrder(BookOrder bookOrder) throws LibraryServiceException;

    /**
     * Update {@link BookOrder}'s status into cache if user online and into database
     *
     * @param bookOrder {@link BookOrder} instance
     * @throws LibraryServiceException if <tt>bookOrder</tt> is null
     *                          or if an error occurs while writing {@link BookOrder}'s
     *                          status into data source
     */
    void updateBookOrderStatus(BookOrder bookOrder) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link BookOrder} which user id is <tt>userId</tt>
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @return BookOrder list {@link BookOrder} which user id is <tt>userId</tt>
     * @throws LibraryServiceException if {@link BookOrder} is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link BookOrder} which user id is <tt>userId</tt>
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return BookOrder list {@link BookOrder} which user id is <tt>userId</tt>
     * @throws LibraryServiceException if {@link BookOrder} list is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of {@link BookOrder}s which follow the book fields pattern
     *
     * @param userId is the {@link BookOrder}'s {@link User}'s id
     * @return quantity of {@link BookOrder}s which user id is <tt>userId</tt>
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    int findOrdersQuantityByUserId(int userId) throws LibraryServiceException;

    /**
     * Find BookOrder list of {@link BookOrder} which status is pending or return
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return BookOrder list {@link BookOrder} which status is pending or return
     * @throws LibraryServiceException if {@link BookOrder} is empty
     *                          or occurs after searching {@link BookOrder} into the data source
     */
    List<BookOrder> findAllOpenOrders(int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of all {@link BookOrder} which status is pending or return
     *
     * @return quantity of all {@link BookOrder} which status is pending or return
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    int findOpenOrdersQuantity() throws LibraryServiceException;


    /**
     * Returns {@link Cache} which contains {@link BookOrder}'s of online {@link User}'s
     *                  map arguments is <tt>BookOrdersCache.SortedUserBookOrdersCache</tt>
     *
     * @return {@link Cache} which contains {@link BookOrder}'s of online {@link User}'s
     *                  map arguments is <tt>BookOrdersCache.SortedUserBookOrdersCache</tt>
     */
    Cache<String, BookOrdersCache.SortedUserBookOrdersCache> getBookOrdersCache();
}
