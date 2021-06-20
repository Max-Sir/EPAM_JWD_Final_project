package by.sir.max.library.validator;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.exception.ValidatorException;

import by.sir.max.library.factory.ValidatorFactory;
import by.sir.max.library.validatior.BookValidator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookValidatorTest {
    private static final BookValidator bookValidator =  ValidatorFactory.getInstance().getBookValidator();

    private Book validBook;
    private Book invalidBookWithNullFields;

    @Before
    public void init(){
        validBook = new BookBuilder()
                .setGenreUUID("1")
                .setBookLanguageUUID("1")
                .setPublisherUUID("1")
                .setAuthorUUID("1")
                .setTitle("Title")
                .setPublishYear(1992)
                .setPagesQuantity(300)
                .setDescription("Description")
                .build();

        invalidBookWithNullFields = new Book();
    }

    @Test
    public void testValidateNewBookPositive() throws ValidatorException {
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookTitleNegative() throws ValidatorException {
        validBook.setTitle("");
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookDescriptionNegative() throws ValidatorException {
        validBook.setDescription("");
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookPagesQuantityNegative() throws ValidatorException {
        validBook.setPagesQuantity(-1);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookPublishYearNegative() throws ValidatorException {
        validBook.setPublishYear(-1);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookAuthorUUIDNegative() throws ValidatorException {
        Author author = new Author();
        author.setUuid("");
        validBook.setAuthor(author);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookGenreUUIDNegative() throws ValidatorException {
        Genre genre = new Genre();
        genre.setUuid("");
        validBook.setGenre(genre);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookLanguageUUIDNegative() throws ValidatorException {
        BookLanguage bookLanguage = new BookLanguage();
        bookLanguage.setUuid("");
        validBook.setBookLanguage(bookLanguage);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookPublisherUUIDNegative() throws ValidatorException {
        Publisher publisher = new Publisher();
        publisher.setUuid("");
        validBook.setPublisher(publisher);
        bookValidator.validateNewBook(validBook);
        assertNotNull(validBook);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewNullBookNegative() throws ValidatorException {
        bookValidator.validateNewBook(null);
        assertNull(null);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewBookWithNullFieldsNegative() throws ValidatorException {
        bookValidator.validateNewBook(invalidBookWithNullFields);
        assertNotNull(invalidBookWithNullFields);
    }
}
