package by.sir.max.library.dao.user.impl;

import by.sir.max.library.dao.BaseDAO;
import by.sir.max.library.dao.SQLQueriesStorage;
import by.sir.max.library.dao.user.UserDAO;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String UNIQUE_LOGIN_MESSAGE = "user.login_UNIQUE";
    private static final String UNIQUE_EMAIL_MESSAGE = "user.email_UNIQUE";

    @Override
    public void registerUser(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.REGISTER_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getPassportSerialNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getPhoneNumber());
            preparedStatement.setBoolean(9, user.getBanned());
            preparedStatement.setString(10, user.getLogInToken());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            if(e.getMessage().contains(UNIQUE_LOGIN_MESSAGE)) {
                throw new LibraryDAOException("query.user.registration.emailAlreadyExist", e);
            } else if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
                throw new LibraryDAOException("query.user.registration.loginAlreadyExist", e);
            }
            LOGGER.warn(String.format("User %s registration common error", user), e);
            throw new LibraryDAOException("query.user.registration.commonError", e);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User %s registration error", user), e);
            throw new LibraryDAOException("query.user.registration.commonError", e);
        }
    }

    @Override
    public void updateUserProfileData(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_PROFILE_DATA)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.user.registration.emailAlreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User %s profile update error", user), e);
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void updateUserBanStatus(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BAN_STATUS)) {
            preparedStatement.setBoolean(1, user.getBanned());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User %s ban status update error", user), e);
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void updateRememberUserToken(int userId, String userToken) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_BY_ID)) {
            preparedStatement.setString(1, userToken);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User id: %d, token %s update error", userId, userToken), e);
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_TO_NULL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User id: %d, token delete error", userId), e);
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public User findUserByLogin(String userLogin) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User login: %s, finding error", userLogin), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserByEmail(String userEmail) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, userEmail);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User email: %s, finding error", userEmail), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserByIdAndToken(int userId, String token) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_ID_AND_TOKEN)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User userId: %d, token %s finding error", userId, token), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserById(int userId) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(String.format("User userId: %d finding error", userId), e);
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> findAllUsers() throws LibraryDAOException {
        List<User> users;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                users = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                users = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    users.add(constructUserByResultSet(resultSet));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("User list finding error", e);
            throw new LibraryDAOException("service.commonError", e);
        }
        return users;
    }

    private User extractFoundedUserFromResultSet(ResultSet resultSet) throws SQLException, LibraryDAOException {
        if (resultSet.next()) {
            return constructUserByResultSet(resultSet);
        } else {
            throw new LibraryDAOException("query.user.getUser.userNotFound");
        }
    }
}
