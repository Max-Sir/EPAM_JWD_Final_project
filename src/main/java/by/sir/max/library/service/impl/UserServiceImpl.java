package by.sir.max.library.service.impl;

import by.sir.max.library.builder.UserBuilder;
import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.dao.user.UserDAO;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.UtilException;
import by.sir.max.library.exception.LibraryDAOException;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.exception.ValidatorException;
import by.sir.max.library.factory.DAOFactory;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.factory.UtilFactory;
import by.sir.max.library.factory.ValidatorFactory;
import by.sir.max.library.service.BookOrderService;
import by.sir.max.library.service.UserService;
import by.sir.max.library.util.EmailDistributorUtil;
import by.sir.max.library.util.EmailMessageLocalizationDispatcher;
import by.sir.max.library.util.EmailMessageType;
import by.sir.max.library.util.HashGeneratorUtil;
import by.sir.max.library.validatior.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final int TOKEN_VALUE_COOKIE_INDEX = 0;
    private static final int USER_ID_COOKIE_INDEX = 1;

    private final UserDAO userDAO;
    private final UsersOnlineCache usersOnlineCache;
    private final UserValidator validator;
    private final HashGeneratorUtil hashGeneratorUtil;
    private final BookOrdersCache bookOrdersCache;
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailLocalizationDispatcher;

    public UserServiceImpl(){
        userDAO = DAOFactory.getInstance().getUserDAO();
        usersOnlineCache = UsersOnlineCache.getInstance();
        bookOrdersCache = BookOrdersCache.getInstance();
        validator = ValidatorFactory.getInstance().getUserValidator();
        hashGeneratorUtil = UtilFactory.getInstance().getHashGeneratorUtil();
        emailDistributorUtil = UtilFactory.getInstance().getEmailDistributorUtil();
        emailLocalizationDispatcher = UtilFactory.getInstance().getEmailMessageLocalizationDispatcher();
    }

    @Override
    public User logInByPassword(String login, String password) throws LibraryServiceException {
        if (StringUtils.isAnyBlank(login, password)) {
            LOGGER.info("invalid input values");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            User user = userDAO.findUserByLogin(login);
            if (hashGeneratorUtil.validatePassword(password, user.getPassword())) {
                if (user.getBanned()) {
                    throw new LibraryServiceException("validation.user.login.isBanned");
                }
                initCacheAfterLogIn(user);
                return user;
            } else {
                throw new LibraryServiceException("validation.user.login.incorrect");
            }
        } catch (UtilException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User logInByToken(String token) throws LibraryServiceException {
        if (token == null) {
            LOGGER.warn("token is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            String[] tokenComponents = token.split(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
            int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
            String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];
            User user = userDAO.findUserByIdAndToken(userId, tokenValue);
            if (user != null) {
                if (user.getBanned()) {
                    throw new LibraryServiceException("validation.user.login.isBanned");
                }
                initCacheAfterLogIn(user);
                return user;
            }
            LOGGER.warn(String.format("Cant use token %s for log in", token));
            throw new LibraryServiceException("service.commonError");
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void logOut(String login) throws LibraryServiceException {
        if (login == null) {
            LOGGER.warn("login is null");
            throw new LibraryServiceException("service.commonError");
        }
        usersOnlineCache.remove(login);
        bookOrdersCache.remove(login);
    }

    @Override
    public User findUserByLogin(String login) throws LibraryServiceException {
        if (login == null) {
            LOGGER.warn("login is null");
            throw new LibraryServiceException("service.commonError");
        }
        User user = null;
        try {
            user = usersOnlineCache.get(login);
        } catch (LibraryServiceException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        if (user == null) {
            LOGGER.warn("User is not in cache");
            try {
                user = userDAO.findUserByLogin(login);
            } catch (LibraryDAOException e) {
                throw new LibraryServiceException(e.getMessage(), e);
            }
        }
        return user;
    }

    @Override
    public User findUserById(int id) throws LibraryServiceException {
        try {
            return userDAO.findUserById(id);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAllUsers() throws LibraryServiceException {
        try {
            List<User> userList = userDAO.findAllUsers();
            if (userList.isEmpty()) {
                throw new LibraryServiceException("query.user.getUsers.usersNotFound");
            } else {
                return userList;
            }
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findUsersOnline() {
        List<User> list = usersOnlineCache.getAllValues();
        Collections.sort(list);
        return list;
    }

    @Override
    public String getUpdatedRememberUserToken(int id) throws LibraryServiceException {
        String token = UUID.randomUUID().toString();
        try {
            userDAO.updateRememberUserToken(id, token);
            return token + JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER + id;
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) throws LibraryServiceException {
        if (StringUtils.isAnyBlank(email, pageRootUrl)) {
            throw new LibraryServiceException("service.commonError");
        }
        try {
            User user = userDAO.findUserByEmail(email);
            String token = getUpdatedRememberUserToken(user.getId());
            String userLogInLink = constructLogInLink(CommandStorage.FORGET_PASSWORD_LOG_IN.getCommandName(), pageRootUrl, token);
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_FORGET_PASSWORD);
            String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_FORGET_PASSWORD, userLogInLink);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, email);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        } catch (UtilException e) {
            throw new LibraryServiceException("service.commonError", e);
        }
    }

    @Override
    public void deleteRememberUserToken(int id) throws LibraryServiceException {
        try {
            userDAO.deleteRememberUserToken(id);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void registerUser(User user, String pageRootUrl) throws LibraryServiceException {
        if (user == null || StringUtils.isBlank(pageRootUrl)) {
            throw new LibraryServiceException("service.commonError");
        }
        try {
            validator.validateNewUser(user);
        } catch (ValidatorException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
        try {
            user.setPassword(hashGeneratorUtil.generateHash(user.getPassword()));
            user.setBanned(true);
            userDAO.registerUser(user);

            user = userDAO.findUserByLogin(user.getLogin());
            String token = getUpdatedRememberUserToken(user.getId());

            String userLogInLink = constructLogInLink(CommandStorage.POST_REGISTRATION_ACCOUNT_APPROVAL.getCommandName(), pageRootUrl, token);
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_USER_REGISTRATION_LINK);
            String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_USER_REGISTRATION_LINK, userLogInLink);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, user.getEmail());
        } catch (UtilException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void postRegistrationApprovalByToken(String token) throws LibraryServiceException {
        if (StringUtils.isBlank(token)) {
            LOGGER.info("invalid input token");
            throw new LibraryServiceException("service.commonError");
        }
        String[] tokenComponents = token.split(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
        int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
        User user = new UserBuilder()
                .setId(userId)
                .setBanned(false)
                .build();
        try {
            userDAO.updateUserBanStatus(user);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserProfileData(User user) throws LibraryServiceException {
        if (user == null) {
            throw new LibraryServiceException("service.commonError");
        }
        try {
            validator.validateUpdatedUser(user);
        } catch (ValidatorException e) {
            LOGGER.info(String.format("invalid %s %n update data %s", user.toString(), e.getMessage()));
            throw new LibraryServiceException(e.getMessage(), e);
        }
        try {
            user.setPassword(hashGeneratorUtil.generateHash(user.getPassword()));
            userDAO.updateUserProfileData(user);
            if(usersOnlineCache.get(user.getLogin()) != null) {
                usersOnlineCache.put(user);
            }
        } catch (UtilException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserBanStatus(User user) throws LibraryServiceException {
        if (user == null) {
            LOGGER.warn("User is null");
            throw new LibraryServiceException("service.commonError");
        }
        try {
            userDAO.updateUserBanStatus(user);
            String status = user.getBanned() ? EmailMessageType.MESSAGE_USER_BAN_STATUS_BANNED : EmailMessageType.MESSAGE_USER_BAN_STATUS_UNBANNED;
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_USER_BAN_STATUS_UPDATED);
            String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_USER_BAN_STATUS_UPDATED, status);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, user.getEmail());
            if(usersOnlineCache.get(user.getLogin()) != null) {
                usersOnlineCache.put(user);
            }
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        } catch (UtilException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        }
    }

    @Override
    public UsersOnlineCache getUsersOnlineCache() {
        return usersOnlineCache;
    }

    private void initCacheAfterLogIn(User user){
        BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();
        try {
            usersOnlineCache.put(user);
        } catch (LibraryServiceException e) {
            LOGGER.warn(String.format("Can't put user %s in cache", user), e);
        }
        try {
            bookOrdersCache.put(user.getLogin(), bookOrderService.findAllOrdersByUserId(user.getId()));
        } catch (LibraryServiceException e) {
            LOGGER.warn(String.format("Can't put user's %s book orders in cache", user), e);
        }
    }

    private String constructLogInLink(String commandName, String pageRootUrl, String token) {
        return pageRootUrl + '?' +JSPAttributeStorage.COMMAND + '=' + commandName
                + '&' + JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN + '=' + token;
    }
}
