package by.sir.max.library.dao.book;

import by.sir.max.library.entity.book.bookcomponent.BaseBookComponent;
import by.sir.max.library.exception.LibraryDAOException;

import java.util.List;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore T extends{@link BaseBookComponent} entity
 */
 public interface BookComponentDAO <T extends BaseBookComponent> {

    /**
     * Add a <tt>T extends {@link BaseBookComponent}</tt>  into data source.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     *
     * @param element the T extends {@link BaseBookComponent} object that must be save into date source
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     */
    void add(T element) throws LibraryDAOException;

    /**
     * Find and returns <tt>T extends{@link BaseBookComponent}</tt> from the data source.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     *
     * @param bookComponentUUID is the UUID of <tt>T extends{@link BaseBookComponent}</tt>
     * @return T extends{@link BaseBookComponent}
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     */
    T findByUUID(String bookComponentUUID) throws LibraryDAOException;

    /**
     * Find and returns all List {@link T} from the data source <tt>T extends{@link BaseBookComponent}</tt>.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     *
     * @return all T extends{@link BaseBookComponent}
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends{@link BaseBookComponent}</tt>
     */
    List<T> findAll() throws LibraryDAOException;
}
