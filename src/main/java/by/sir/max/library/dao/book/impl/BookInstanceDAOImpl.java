package by.sir.max.library.dao.book.impl;

import by.sir.max.library.dao.BaseDAO;
import by.sir.max.library.dao.SQLQueriesStorage;
import by.sir.max.library.dao.book.BookInstanceDAO;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BookInstanceDAOImpl  extends BaseDAO implements BookInstanceDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookInstanceDAOImpl.class);

    static void addBookInstance(String bookUUID, int quantity, Connection connection) throws LibraryDAOException {
        for (int i = 0; i < quantity; i++) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_INSTANCE)) {
                preparedStatement.setString(1, UUID.randomUUID().toString());
                preparedStatement.setString(2, bookUUID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.warn(String.format("Book uuid: %s, quantity: %d add instance error", bookUUID, quantity), e);
                throw new LibraryDAOException("query.bookInstance.creation.commonError", e);
            }
        }
    }

    static void updateBookInstanceAvailableStatus(String bookInstanceUUID, boolean isAvailable, Connection connection) throws LibraryDAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_BOOK_INSTANCE_AVAILABLE_STATUS)) {
            preparedStatement.setBoolean(1, isAvailable);
            preparedStatement.setString(2, bookInstanceUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn(String.format("Book uuid: %s, isAvailable status: %b update isAvailable status error", bookInstanceUUID, isAvailable), e);
            throw new LibraryDAOException("query.bookInstance.update.status", e);
        }
    }

    @Override
    public List<String> findAllAvailableBookInstanceUUIDsByBookUUID(String bookUUID) throws LibraryDAOException {
        List<String> bookInstanceUUIDs;
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_AVAILABLE_BOOK_INSTANCE_UUID_BY_BOOK_UUID)) {
            preparedStatement.setString(1, bookUUID);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                bookInstanceUUIDs = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                bookInstanceUUIDs = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    bookInstanceUUIDs.add(resultSet.getString(1));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("Book instance finding by book uuid: %s error", bookUUID), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
        return bookInstanceUUIDs;
    }
}



