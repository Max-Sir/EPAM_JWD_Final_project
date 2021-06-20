package by.sir.max.library.dao.book.impl;

import by.sir.max.library.dao.BaseDAO;
import by.sir.max.library.dao.SQLQueriesStorage;
import by.sir.max.library.dao.book.BookComponentDAO;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookPublisherDAOImpl extends BaseDAO implements BookComponentDAO<Publisher> {
    private static final Logger LOGGER = LogManager.getLogger(BookPublisherDAOImpl.class);

    @Override
    public void add(Publisher publisher) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_PUBLISHER)) {
            preparedStatement.setString(1, publisher.getUuid());
            preparedStatement.setString(2, publisher.getPublisherTitle());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.publisher.creation.alreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("Publisher %s, add error", publisher), e);
            throw new LibraryDAOException("query.publisher.creation.commonError", e);
        }
    }

    @Override
    public Publisher findByUUID(String bookPublisherUUID) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_PUBLISHER_BY_UUID)) {
            preparedStatement.setString(1, bookPublisherUUID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructPublisherByResultSet(resultSet);
            } else {
                LOGGER.info(String.format("Publisher not found by uuid %s", bookPublisherUUID));
                throw new LibraryDAOException("query.publisher.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("Publisher finding by uuid %s error", bookPublisherUUID), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<Publisher> findAll() throws LibraryDAOException {
        List<Publisher> publishers;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_PUBLISHERS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                LOGGER.info("publishers is empty");
                publishers = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                publishers = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    publishers.add(constructPublisherByResultSet(resultSet));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("Publisher List finding error", e);
            throw new LibraryDAOException("service.commonError", e);
        }
        return publishers;
    }

}
