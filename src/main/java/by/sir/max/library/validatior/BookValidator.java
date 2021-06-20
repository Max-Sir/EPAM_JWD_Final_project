package by.sir.max.library.validatior;

import by.sir.max.library.entity.book.Book;
import by.sir.max.library.entity.book.bookcomponent.BaseBookComponent;
import by.sir.max.library.exception.ValidatorException;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

public class BookValidator {
    private static final String AUTHOR_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,40}$";
    private static final String LANGUAGE_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,15}$";
    private static final String PUBLISHER_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,45}$";
    private static final String GENRE_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,20}$";
    private static final String TITLE_REGEX = "^[^!@#$%^&*()_|+~\\d]{3,45}$";
    private static final int MAX_DESCRIPTION_FIELD_LENGTH = 500;
    private static final int MIN_PAGES_QUANTITY = 10;
    private static final int MAX_PAGES_QUANTITY = 32767;
    private static final int MIN_PUBLISH_YEAR = 1000;

    private final Calendar calendar;

    public BookValidator() {
        calendar = Calendar.getInstance();
    }

    public void validateNewBook(Book book) throws ValidatorException {
        if (book == null) {
            throw new ValidatorException("service.commonError");
        }
        validateTitle(book.getTitle());
        validateDescription(book.getDescription());
        validatePagesQuantity(book.getPagesQuantity());
        validatePublishYear(book.getPublishYear());
        validateBookComponents(book.getAuthor(), book.getGenre(), book.getBookLanguage(), book.getPublisher());
    }

    public void validateAuthor(String author) throws ValidatorException {
        if (StringUtils.isBlank(author) || !author.matches(AUTHOR_REGEX)) {
            throw new ValidatorException("validation.book.add.author");
        }
    }

    public void validateLanguage(String language) throws ValidatorException {
        if (StringUtils.isBlank(language) || !language.matches(LANGUAGE_REGEX)) {
            throw new ValidatorException("validation.book.add.language");
        }
    }

    public void validatePublisher(String publisher) throws ValidatorException {
        if (StringUtils.isBlank(publisher) || !publisher.matches(PUBLISHER_REGEX)) {
            throw new ValidatorException("validation.book.add.publisher");
        }
    }

    public void validateGenre(String genre) throws ValidatorException {
        if (StringUtils.isBlank(genre) || !genre.matches(GENRE_REGEX)) {
            throw new ValidatorException("validation.book.add.genre");
        }
    }

    private void validateTitle(String title) throws ValidatorException {
        if (StringUtils.isBlank(title) || !title.matches(TITLE_REGEX)) {
            throw new ValidatorException("validation.book.add.title");
        }
    }

    private void validateDescription(String description) throws ValidatorException {
        if (StringUtils.isBlank(description) || description.length() > MAX_DESCRIPTION_FIELD_LENGTH) {
            throw new ValidatorException("validation.book.add.description");
        }
    }

    public void validateBookQuantity(int bookQuantity) throws ValidatorException {
        if (bookQuantity < 1) {
            throw new ValidatorException("validation.book.add.bookQuantity");
        }
    }

    private void validatePagesQuantity(int pagesQuantity) throws ValidatorException {
        if (pagesQuantity < MIN_PAGES_QUANTITY || pagesQuantity > MAX_PAGES_QUANTITY) {
            throw new ValidatorException("validation.book.add.pagesQuantity");
        }
    }

    private void validatePublishYear(int publishYear) throws ValidatorException {
        if (publishYear < MIN_PUBLISH_YEAR || publishYear > calendar.get(Calendar.YEAR)) {
            throw new ValidatorException("validation.book.add.publishYear");
        }
    }

    private void validateBookComponents(BaseBookComponent... bookComponents) throws ValidatorException {
        for (BaseBookComponent component: bookComponents) {
            if (component == null || StringUtils.isBlank(component.getUuid())) {
                throw new ValidatorException("validation.book.add.bookComponent");
            }
        }
    }
}
