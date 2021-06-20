package by.sir.max.library.service;

import by.sir.max.library.entity.book.bookcomponent.BaseBookComponent;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.validatior.BookValidator;

/**
 * Interface describes the behavior of Cache
 *
 * @param <T> the type of book component
 */
public interface BookComponentService<T> {

    /**
     * Saves BookComponent into cache and database
     *
     * @param element is a {@link BaseBookComponent}'s child instance
     * @throws LibraryServiceException if T extends {@link BaseBookComponent} is null or
     *                          if <tt>element</tt>'s fields not accords to specify pattern
     *                          {@link BookValidator} or if element has already into database
     *                          or if an error occurs while writing new <tt>element</tt> into
     *                          database
     */
    void add(T element) throws LibraryServiceException;

    /**
     * Find BookComponent by uuid
     *
     * @param uuid T extends {@link BaseBookComponent}'s uuid
     * @return T extends {@link BaseBookComponent}
     * @throws LibraryServiceException if T extends {@link BaseBookComponent} is null or
     *                          occurs after searching T extends {@link BaseBookComponent}
     *                          into the data source
     */
    T findByUUID(String uuid) throws LibraryServiceException;
}
