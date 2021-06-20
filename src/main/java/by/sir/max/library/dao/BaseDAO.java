package by.sir.max.library.dao;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.builder.BookOrderBuilder;
import by.sir.max.library.builder.UserBuilder;
import by.sir.max.library.dto.BookDTO;
import by.sir.max.library.entity.book.*;
import by.sir.max.library.entity.book.bookcomponent.Author;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.entity.order.OrderStatus;
import by.sir.max.library.entity.order.OrderType;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.entity.user.UserRole;
import by.sir.max.library.exception.LibraryDAOException;
import by.sir.max.library.pool.ConnectionPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

    protected static final String USER_ID_COLUMN_NAME = "user.id";
    protected static final String USER_ROLE_ID_COLUMN_NAME = "user.role_id";
    protected static final String USER_EMAIL_COLUMN_NAME = "user.email";
    protected static final String USER_LOGIN_COLUMN_NAME = "user.login";
    protected static final String USER_PASSWORD_COLUMN_NAME = "user.password";
    protected static final String USER_FIRSTNAME_COLUMN_NAME = "user.firstname";
    protected static final String USER_LASTNAME_COLUMN_NAME = "user.lastname";
    protected static final String USER_PASSPORT_SN_COLUMN_NAME = "user.passport_serial_number";
    protected static final String USER_ADDRESS_COLUMN_NAME = "user.address";
    protected static final String USER_PHONE_COLUMN_NAME = "user.phone";
    protected static final String USER_BAN_STATUS_COLUMN_NAME = "user.is_banned";
    protected static final String USER_REGISTRATION_DATE_COLUMN_NAME = "user.registration_date";

    protected static final String BOOK_AUTHOR_UUID_COLUMN_NAME = "book_author.uuid";
    protected static final String BOOK_AUTHOR_COLUMN_NAME = "book_author.author";

    protected static final String BOOK_GENRE_UUID_COLUMN_NAME = "book_genre.uuid";
    protected static final String BOOK_GENRE_GENRE_COLUMN_NAME = "book_genre.genre";

    protected static final String BOOK_LANGUAGE_UUID_COLUMN_NAME = "book_language.uuid";
    protected static final String BOOK_LANGUAGE_COLUMN_NAME = "book_language.language";

    protected static final String BOOK_PUBLISHER_UUID_COLUMN_NAME = "book_publisher.uuid";
    protected static final String BOOK_PUBLISHER_COLUMN_NAME = "book_publisher.title";

    protected static final String BOOK_UUID_COLUMN_NAME = "book.uuid";
    protected static final String BOOK_BOOK_AUTHOR_UUID_COLUMN_NAME = "book.author_uuid";
    protected static final String BOOK_BOOK_GENRE_UUID_COLUMN_NAME = "book.genre_uuid";
    protected static final String BOOK_BOOK_LANGUAGE_UUID_COLUMN_NAME = "book.language_uuid";
    protected static final String BOOK_BOOK_PUBLISHER_UUID_COLUMN_NAME = "book.publisher_uuid";
    protected static final String BOOK_TITLE_COLUMN_NAME = "book.title";
    protected static final String BOOK_PUBLISH_YEAR_COLUMN_NAME = "book.publish_year";
    protected static final String BOOK_PAGES_QUANTITY_COLUMN_NAME = "book.pages_quantity";
    protected static final String BOOK_PAGES_DESCRIPTION_COLUMN_NAME = "book.description";

    protected static final String ORDER_UUID_COLUMN_NAME = "book_order.uuid";
    protected static final String ORDER_ORDER_TYPE_ID_COLUMN_NAME = "book_order.order_type_id";
    protected static final String ORDER_ORDER_STATUS_ID_COLUMN_NAME = "book_order.order_status_id";
    protected static final String ORDER_DATE_ID_COLUMN_NAME = "book_order.date";

    protected static final String BOOK_INSTANCE_UUID_COLUMN_NAME = "book_instance.uuid";
    protected static final String BOOK_INSTANCE_IS_AVAILABLE_COLUMN_NAME = "book_instance.is_available";

    protected static final String TOTAL_BOOK_INSTANCE_QUANTITY_COLUMN_NAME = "total_book_instance_quantity";
    protected static final String AVAILABLE_BOOK_INSTANCE_QUANTITY_COLUMN_NAME = "available_book_instance_quantity";


    protected final ConnectionPool pool;

    protected BaseDAO(){
        pool = ConnectionPool.getInstance();
    }

    protected void closeResultSet(ResultSet resultSet) throws LibraryDAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close resultSet", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void closeConnection(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close connection", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionCommitChanges(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("Can`t commit connection's changes", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionSetAutoCommit(Connection connection, boolean value) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.setAutoCommit(value);
            } catch (SQLException e) {
                LOGGER.error("Can`t set autocommmit to connection", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionsRollback(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Can`t rollback connection result", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected User constructUserByResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder().setId(resultSet.getInt(USER_ID_COLUMN_NAME))
                .setUserRole(UserRole.getRoleById(resultSet.getInt(USER_ROLE_ID_COLUMN_NAME)))
                .setEmail(resultSet.getString(USER_EMAIL_COLUMN_NAME))
                .setLogin(resultSet.getString(USER_LOGIN_COLUMN_NAME))
                .setPassword(resultSet.getString(USER_PASSWORD_COLUMN_NAME))
                .setFirstName(resultSet.getNString(USER_FIRSTNAME_COLUMN_NAME))
                .setLastName(resultSet.getNString(USER_LASTNAME_COLUMN_NAME))
                .setPassportSerialNumber(resultSet.getString(USER_PASSPORT_SN_COLUMN_NAME))
                .setAddress(resultSet.getNString(USER_ADDRESS_COLUMN_NAME))
                .setPhoneNumber(resultSet.getString(USER_PHONE_COLUMN_NAME))
                .setBanned(resultSet.getBoolean(USER_BAN_STATUS_COLUMN_NAME))
                .setRegistrationDate(resultSet.getTimestamp(USER_REGISTRATION_DATE_COLUMN_NAME))
                .build();
    }

    protected Author constructAuthorByResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setUuid(resultSet.getString(BOOK_AUTHOR_UUID_COLUMN_NAME));
        author.setAuthorName(resultSet.getString(BOOK_AUTHOR_COLUMN_NAME));
        return author;
    }

    protected Genre constructGenreByResultSet(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setUuid(resultSet.getString(BOOK_GENRE_UUID_COLUMN_NAME));
        genre.setGenreTitle(resultSet.getString(BOOK_GENRE_GENRE_COLUMN_NAME));
        return genre;
    }

    protected BookLanguage constructBookLanguageByResultSet(ResultSet resultSet) throws SQLException {
        BookLanguage bookLanguage = new BookLanguage();
        bookLanguage.setUuid(resultSet.getString(BOOK_LANGUAGE_UUID_COLUMN_NAME));
        bookLanguage.setLanguageTitle(resultSet.getString(BOOK_LANGUAGE_COLUMN_NAME));
        return bookLanguage;
    }

    protected Publisher constructPublisherByResultSet(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setUuid(resultSet.getString(BOOK_PUBLISHER_UUID_COLUMN_NAME));
        publisher.setPublisherTitle(resultSet.getString(BOOK_PUBLISHER_COLUMN_NAME));
        return publisher;
    }

    protected Book constructBookByResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder().setUuid(resultSet.getString(BOOK_UUID_COLUMN_NAME))
                .setGenre(constructGenreByResultSet(resultSet))
                .setBookLanguage(constructBookLanguageByResultSet(resultSet))
                .setPublisher(constructPublisherByResultSet(resultSet))
                .setAuthor(constructAuthorByResultSet(resultSet))
                .setTitle(resultSet.getString(BOOK_TITLE_COLUMN_NAME))
                .setPublishYear(resultSet.getInt(BOOK_PUBLISH_YEAR_COLUMN_NAME))
                .setPagesQuantity(resultSet.getInt(BOOK_PAGES_QUANTITY_COLUMN_NAME))
                .setDescription(resultSet.getString(BOOK_PAGES_DESCRIPTION_COLUMN_NAME))
                .build();
    }


    protected BookOrder constructOrderByResultSet(ResultSet resultSet) throws SQLException {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setAvailable(resultSet.getBoolean(BOOK_INSTANCE_IS_AVAILABLE_COLUMN_NAME));
        bookInstance.setUuid(resultSet.getString(BOOK_INSTANCE_UUID_COLUMN_NAME));
        bookInstance.setBook(constructBookByResultSet(resultSet));
        return new BookOrderBuilder().setUuid(resultSet.getString(ORDER_UUID_COLUMN_NAME))
                .setUser(new UserBuilder()
                        .setId(resultSet.getInt(USER_ID_COLUMN_NAME))
                        .setLogin(resultSet.getString(USER_LOGIN_COLUMN_NAME))
                        .setEmail(resultSet.getString(USER_EMAIL_COLUMN_NAME))
                        .build()
                )
                .setBookInstance(bookInstance)
                .setOrderType(OrderType.getOrderTypeById(resultSet.getInt(ORDER_ORDER_TYPE_ID_COLUMN_NAME)))
                .setOrderStatus(OrderStatus.getOrderStatusById(resultSet.getInt(ORDER_ORDER_STATUS_ID_COLUMN_NAME)))
                .setDate(resultSet.getTimestamp(ORDER_DATE_ID_COLUMN_NAME))
                .build();
    }

    protected BookDTO constructBookDTOByResultSet(ResultSet resultSet) throws SQLException {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBook(constructBookByResultSet(resultSet));
        bookDTO.setTotalAvailableBooksQuantity(resultSet.getInt(AVAILABLE_BOOK_INSTANCE_QUANTITY_COLUMN_NAME));
        bookDTO.setTotalBooksQuantity(resultSet.getInt(TOTAL_BOOK_INSTANCE_QUANTITY_COLUMN_NAME));
        return bookDTO;
    }

    protected static final String MIDDLE_OF_PREDICATE = " = '";
    protected static final String END_OF_PREDICATE = "' AND ";
    protected static final String WHERE_STATEMENT = " WHERE ";

    protected String constructFindQueryFromBook(Book book, String query) {
        StringBuilder queryWhereStatement = new StringBuilder();
        String bookGenreUUID = injectionProtection(book.getGenre().getUuid());
        String bookAuthorUUID = injectionProtection(book.getAuthor().getUuid());
        String bookPublisherUUID = injectionProtection(book.getPublisher().getUuid());
        String bookLanguageUUID = injectionProtection(book.getBookLanguage().getUuid());
        String bookTitle = injectionProtection(book.getTitle());
        int bookPublishYear = book.getPublishYear();
        int bookPagesQuantity = book.getPagesQuantity();

        if (!StringUtils.isBlank(bookAuthorUUID)) {
            queryWhereStatement
                    .append(BOOK_BOOK_AUTHOR_UUID_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookAuthorUUID)
                    .append(END_OF_PREDICATE);
        }
        if (!StringUtils.isBlank(bookLanguageUUID)) {
            queryWhereStatement
                    .append(BOOK_BOOK_LANGUAGE_UUID_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookLanguageUUID)
                    .append(END_OF_PREDICATE);
        }
        if (!StringUtils.isBlank(bookGenreUUID)) {
            queryWhereStatement
                    .append(BOOK_BOOK_GENRE_UUID_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookGenreUUID)
                    .append(END_OF_PREDICATE);
        }
        if (!StringUtils.isBlank(bookPublisherUUID)) {
            queryWhereStatement
                    .append(BOOK_BOOK_PUBLISHER_UUID_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookPublisherUUID)
                    .append(END_OF_PREDICATE);
        }
        if (!StringUtils.isBlank(bookTitle)) {
            queryWhereStatement
                    .append(BOOK_TITLE_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookTitle)
                    .append(END_OF_PREDICATE);
        }
        if (bookPublishYear != 0) {
            queryWhereStatement
                    .append(BOOK_PUBLISH_YEAR_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookPublishYear)
                    .append(END_OF_PREDICATE);
        }
        if (bookPagesQuantity != 0) {
            queryWhereStatement
                    .append(BOOK_PAGES_QUANTITY_COLUMN_NAME + MIDDLE_OF_PREDICATE)
                    .append(bookPagesQuantity)
                    .append(END_OF_PREDICATE);
        }
        if (queryWhereStatement.length() == 0) {
            return query;
        } else {
            return query + WHERE_STATEMENT
                    + queryWhereStatement.delete(
                    queryWhereStatement.length() - END_OF_PREDICATE.length() + 1, queryWhereStatement.length());
        }
    }

    private String injectionProtection(String value) {
        return !StringUtils.isBlank(value) ? value.replace("'", "\\'" ) : value;
    }
}
