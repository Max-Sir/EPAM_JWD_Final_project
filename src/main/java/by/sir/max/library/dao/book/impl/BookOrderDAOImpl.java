package by.sir.max.library.dao.book.impl;

import by.sir.max.library.dao.BaseDAO;
import by.sir.max.library.dao.SQLQueriesStorage;
import by.sir.max.library.dao.book.BookOrderDAO;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookOrderDAOImpl extends BaseDAO implements BookOrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookOrderDAOImpl.class);

    @Override
    public void addBookOrder(BookOrder bookOrder) throws LibraryDAOException {
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder: %s add error. get connection exception", bookOrder), e);
            throw new LibraryDAOException("query.bookOrder.creation.commonError", e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_BOOK_ORDER)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, bookOrder.getUuid());
            preparedStatement.setInt(2, bookOrder.getUser().getId());
            preparedStatement.setString(3, bookOrder.getBookInstance().getUuid());
            preparedStatement.setInt(4, bookOrder.getOrderType().getId());
            preparedStatement.executeUpdate();
            BookInstanceDAOImpl.updateBookInstanceAvailableStatus(bookOrder.getBookInstance().getUuid(), false, connection);
            connection.commit();
        } catch (SQLException e) {
            connectionsRollback(connection);
            LOGGER.warn(String.format("BookOrder: %s add error", bookOrder), e);
            throw new LibraryDAOException("query.bookOrder.creation.commonError", e);
        } finally {
            connectionSetAutoCommit(connection, true);
            closeConnection(connection);
        }
    }

    @Override
    public void updateBookOrderStatus(BookOrder bookOrder) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_BOOK_ORDER_STATUS)) {
            preparedStatement.setInt(1, bookOrder.getOrderStatus().getId());
            preparedStatement.setString(2, bookOrder.getUuid());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder: %s update error", bookOrder), e);
            throw new LibraryDAOException("query.bookOrder.update.status", e);
        }
    }

    @Override
    public void cancelBookOrder(BookOrder bookOrder) throws LibraryDAOException {
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder: %s update error. get connection exception", bookOrder), e);
            throw new LibraryDAOException("query.bookOrder.update.status", e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_BOOK_ORDER_STATUS)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, bookOrder.getOrderStatus().getId());
            preparedStatement.setString(2, bookOrder.getUuid());
            preparedStatement.executeUpdate();
            BookInstanceDAOImpl.updateBookInstanceAvailableStatus(bookOrder.getBookInstance().getUuid(), true, connection);
            connection.commit();
        } catch (SQLException e) {
            connectionsRollback(connection);
            LOGGER.warn(String.format("BookOrder: %s update error", bookOrder), e);
            throw new LibraryDAOException("query.bookOrder.update.status", e);
        } finally {
            connectionSetAutoCommit(connection, true);
            closeConnection(connection);
        }
    }

    @Override
    public BookOrder findOrderByUUID(String uuid) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_ORDER_BY_UUID)) {
            preparedStatement.setString(1, uuid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return constructOrderByResultSet(resultSet);
            } else {
                LOGGER.info(String.format("Book order not found by uuid %s", uuid));
                throw new LibraryDAOException("query.order.read.notFound");
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder finding by uuid: %s  error", uuid), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<BookOrder> findOrdersByUserId(int userId, int currentPage, int recordsPerPage) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_ORDERS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recordsPerPage);
            preparedStatement.setInt(3, (currentPage - 1) * recordsPerPage);
            resultSet = preparedStatement.executeQuery();
            return getAllOrdersFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder list finding by userId: %d  error", userId), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<BookOrder> findAllOrdersByUserId(int userId) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_BOOK_ORDERS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            return getAllOrdersFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("BookOrder list finding by userId: %d  error", userId), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public int findOrdersQuantityByUserId(int userId) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_ORDERS_QUANTITY_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public int findOpenOrdersQuantity() throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_OPEN_BOOK_ORDERS_QUANTITY)) {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("BookOrder list with opes status finding error", e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<BookOrder> findAllOpenedOrders(int currentPage, int recordsPerPage) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_BOOK_ORDERS_WITH_OPEN_REQUEST)) {
            preparedStatement.setInt(1, recordsPerPage);
            preparedStatement.setInt(2, (currentPage - 1) * recordsPerPage);
            resultSet = preparedStatement.executeQuery();
            return getAllOrdersFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("BookOrder list with opes status finding error", e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    private List<BookOrder> getAllOrdersFromResultSet(ResultSet resultSet) throws SQLException {
        List<BookOrder> bookOrders;
        if (!resultSet.isBeforeFirst()) {
            bookOrders = Collections.emptyList();
        } else {
            resultSet.last();
            int listSize = resultSet.getRow();
            resultSet.beforeFirst();
            bookOrders = new ArrayList<>(listSize);
            while (resultSet.next()) {
                bookOrders.add(constructOrderByResultSet(resultSet));
            }
        }
        return bookOrders;
    }
}
