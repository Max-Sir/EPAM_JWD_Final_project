package by.sir.max.library.dao.book;

import by.sir.max.library.dto.BookDTO;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.exception.LibraryDAOException;

import java.util.List;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Book} entity
 */
public interface BookDAO {

    /**
     * Saves the {@link Book} into data source and
     * add <tt>quantity</tt>{@link Book}'s into {@link BookInstanceDAO}.
     * Throws LibraryDAOException if an error occurs while writing a <tt>book</tt>
     *
     * @param book the {@link Book} that must be added to data source
     * @param quantity quantity of  {@link Book}'s that should be added into {@link BookInstanceDAO}.
     * @throws LibraryDAOException if an error occurs while writing a <tt>book</tt>
     */
    void addBook(Book book, int quantity) throws LibraryDAOException;

    /**
     * Retrieves and returns the {@link Book}
     * If no such book contains into data source returns throws
     * LibraryDAOException if an error occurs while getting a <tt>book</tt>
     *
     * @param bookUUID the {@link Book}'s uuid
     * @throws LibraryDAOException if an error occurs while adding a <tt>book</tt>
     * @return {@link Book}
     */
    Book findBookByUUID(String bookUUID) throws LibraryDAOException;

    /**
     * Returns the <tt>book's quantity</tt> by transferred {@link Book}
     * If no such book contains into data source returns throws
     * LibraryDAOException if an error occurs while getting a <tt>book</tt>
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query
     * @return a book quantity by constructed search query
     * @throws LibraryDAOException if an error occurs while getting a <tt>book</tt> quantity
     */
    int findBookQuantityByFields(Book book) throws LibraryDAOException;

    /**
     * Returns the {@link BookDTO} list which contains a {@link Book}
     * <tt>int</tt> common instance quantity, and <tt>int</tt> available instance quantity
     * If no such book contains into data source returns emptyList
     * LibraryDAOException if an error occurs while getting a <tt>book</tt>
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return list of {@link BookDTO} which contains a {@link Book},
     *         <tt>int</tt> common instance quantity, and <tt>int</tt> available instance quantity
     * @throws LibraryDAOException if an error occurs while getting a <tt>book</tt>
     */
    List<BookDTO> findBooksDTOByFields(Book book, int currentPage, int recordsPerPage) throws LibraryDAOException;

    /**
     * Returns the {@link BookDTO} list which contains a {@link Book}
     * <tt>int</tt> common instance quantity, and <tt>int</tt> available instance quantity
     * If no such book contains into data source returns emptyList
     * LibraryDAOException if an error occurs while getting a <tt>book</tt>
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return {@link BookDTO} which contains a {@link Book},
     *         <tt>int</tt> common instance quantity, and <tt>int</tt> available instance quantity
     * @throws LibraryDAOException if an error occurs while getting a <tt>book</tt>
     */
    List<BookDTO> findAllBooksDTO(int currentPage, int recordsPerPage) throws LibraryDAOException;
}
