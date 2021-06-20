package by.sir.max.library.service;

import by.sir.max.library.dto.BookDTO;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.entity.book.BookInstance;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.service.impl.CommonBookComponentsCache;
import by.sir.max.library.validatior.BookValidator;

import java.util.List;

/**
 * Interface describes the behavior of {@link Book} entity
 */
public interface BookService {

    /**
     * Saves {@link Book}, add <tt>quantity</tt> of {@link BookInstance}s.
     *
     * @param book Book instance
     * @param quantity is <tt>quantity</tt> of {@link BookInstance}s.
     * @throws LibraryServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@link BookValidator}
     *                          or if Book with this fields has already exist
     *                          or if an error occurs while writing new {@link Book} into
     *                          data source
     */
    void add(Book book, int quantity) throws LibraryServiceException;

    /**
     * Find book {@link Book} instance by <tt>uuid</tt>
     *
     * @param uuid {@link Book}'s uuid
     * @return {@link Book} instance
     * @throws LibraryServiceException if {@link Book} with <tt>uuid</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    Book findByUUID(String uuid) throws LibraryServiceException;

    /**
     * Find book list of {@link BookDTO} which follow the book fields pattern
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query into DAO layer
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return book list {@link BookDTO} which follow the book fields pattern
     * @throws LibraryServiceException if {@link BookDTO} is empty or if book is null
     *                          or occurs after searching {@link Book} into the data source
     */
    List<BookDTO> findByFields(Book book, int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of {@link BookDTO}s which follow the book fields pattern
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query into DAO layer
     * @return quantity of {@link BookDTO}s which follow the book fields pattern
     * @throws LibraryServiceException if {@link BookDTO} list is empty or if book is null
     *                          or occurs after searching {@link Book} into the data source
     */
    int findBookQuantityByFields(Book book) throws LibraryServiceException;

    /**
     * Find all book list of {@link BookDTO}
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return all book list {@link BookDTO}
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    List<BookDTO> findAllBookDTO(int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Returns {@link CommonBookComponentsCache} which contains book components collections
     *
     * @return {@link CommonBookComponentsCache} which contains book components collections
     */
    CommonBookComponentsCache getBookComponentsCache();
}
