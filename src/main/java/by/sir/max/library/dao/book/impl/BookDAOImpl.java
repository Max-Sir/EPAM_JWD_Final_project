package by.sir.max.library.dao.book.impl;

import by.sir.max.library.dao.BaseDAO;
import by.sir.max.library.dao.SQLQueriesStorage;
import by.sir.max.library.dao.book.BookDAO;
import by.sir.max.library.dto.BookDTO;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAOImpl extends BaseDAO implements BookDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookDAOImpl.class);

    private static final String LIMIT_OFFSET_STATEMENT = " LIMIT ? OFFSET ? ";
    private static final String GROUP_BY_UUID_STATEMENT = " GROUP BY uuid ";
    private static final String ORDER_BY_STATEMENT = " ORDER BY  ";

    @Override
    public void addBook(Book book, int quantity) throws LibraryDAOException {
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            LOGGER.warn(String.format("Book: %s, quantity: %d add error. get connection exception", book, quantity), e);
            throw new LibraryDAOException("query.book.creation.commonError", e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, book.getUuid());
            preparedStatement.setString(2, book.getGenre().getUuid());
            preparedStatement.setString(3, book.getBookLanguage().getUuid());
            preparedStatement.setString(4, book.getPublisher().getUuid());
            preparedStatement.setString(5, book.getAuthor().getUuid());
            preparedStatement.setString(6, book.getTitle());
            preparedStatement.setInt(7, book.getPublishYear());
            preparedStatement.setInt(8, book.getPagesQuantity());
            preparedStatement.setString(9, book.getDescription());
            preparedStatement.executeUpdate();
            BookInstanceDAOImpl.addBookInstance(book.getUuid(), quantity, connection);
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.info(String.format("Book %s is already exist", book));
            BookInstanceDAOImpl.addBookInstance(getBookUUIDFromBookFields(book, connection), quantity, connection);
            connectionCommitChanges(connection);
        } catch (SQLException e) {
            connectionsRollback(connection);
            LOGGER.warn(String.format("Book: %s, quantity: %d add error", book, quantity), e);
            throw new LibraryDAOException("query.book.creation.commonError", e);
        } finally {
            connectionSetAutoCommit(connection, true);
            closeConnection(connection);
        }
    }

    @Override
    public Book findBookByUUID(String bookUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_BY_UUID)){
            preparedStatement.setString(1, bookUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructBookByResultSet(resultSet);
            } else {
                LOGGER.info(String.format("Book not found by uuid %s", bookUUID));
                throw new LibraryDAOException("query.book.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("Book finding by uuid: %s error", bookUUID), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public int findBookQuantityByFields(Book book) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(constructFindQueryFromBook(book, SQLQueriesStorage.FIND_ALL_BOOKS_QUANTITY));
            ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("Book finding: %s error", book), e);
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public List<BookDTO> findBooksDTOByFields(Book book, int currentPage, int recordsPerPage) throws LibraryDAOException {
        return findBooksDTOByQuery(constructFindQueryFromBook(book, SQLQueriesStorage.FIND_ALL_BOOKS)
                + GROUP_BY_UUID_STATEMENT + ORDER_BY_STATEMENT + BOOK_AUTHOR_COLUMN_NAME + LIMIT_OFFSET_STATEMENT
                , currentPage, recordsPerPage);
    }

    @Override
    public List<BookDTO> findAllBooksDTO(int currentPage, int recordsPerPage) throws LibraryDAOException {
        return findBooksDTOByQuery(SQLQueriesStorage.FIND_ALL_BOOKS + LIMIT_OFFSET_STATEMENT, currentPage, recordsPerPage);
    }

    private List<BookDTO> findBooksDTOByQuery(String query, int currentPage, int recordsPerPage) throws LibraryDAOException {
        List<BookDTO> bookDTOList;
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recordsPerPage);
            preparedStatement.setInt(2, (currentPage - 1) * recordsPerPage);
            resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int listSize = resultSet.getRow();
            resultSet.beforeFirst();
            bookDTOList = new ArrayList<>(listSize);
            while (resultSet.next()) {
                bookDTOList.add(constructBookDTOByResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("Book list finding error", e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
        if (bookDTOList.size() == 1 && bookDTOList.get(0).getBook().getUuid() == null) {
                bookDTOList = Collections.emptyList();
        }
        return bookDTOList;
    }

    private String getBookUUIDFromBookFields(Book book, Connection connection) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_UUID_BY_FIELDS)){
            preparedStatement.setString(1, book.getGenre().getUuid());
            preparedStatement.setString(2, book.getBookLanguage().getUuid());
            preparedStatement.setString(3, book.getPublisher().getUuid());
            preparedStatement.setString(4, book.getAuthor().getUuid());
            preparedStatement.setString(5, book.getTitle());
            preparedStatement.setInt(6, book.getPublishYear());
            preparedStatement.setInt(7, book.getPagesQuantity());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                LOGGER.info(String.format("Book %s not found by ", book));
                throw new LibraryDAOException("query.book.read.notFound");
            }
        } catch (SQLException | LibraryDAOException e) {
            LOGGER.warn(String.format("Book uuid by fields finding: %s error", book), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }
}
