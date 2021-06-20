package by.sir.max.library.service;

import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.service.impl.UsersOnlineCache;
import by.sir.max.library.validatior.UserValidator;

import java.util.List;
/**
 * Describes the behavior of {@link User} entity.
 */
public interface UserService {

    /**
     * Log in and return {@link User} instance
     *
     * @param login {@link User}'s login
     * @param password {@link User}'s password
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>login</tt> or <tt>password</tt> is null or
     *                          empty or if <tt>login</tt> or <tt>password</tt> do
     *                          not accords to specify pattern {@link UserValidator}
     *                          or if {@link User} with <tt>login</tt> and <tt>password</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User logInByPassword(String login, String password) throws LibraryServiceException;

    /**
     * Log in and return {@link User} instance
     *
     * @param token {@link User}'s token access
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>token</tt> or <tt>password</tt> is null
     *                          or if {@link User} with <tt>token</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User logInByToken(String token) throws LibraryServiceException;

    /**
     * Log out {@link User} remove user from cache
     *
     * @param login {@link User}'s login
     * @throws LibraryServiceException if login is null
     */
    void logOut(String login) throws LibraryServiceException;

    /**
     * Find user {@link User} instance by <tt>login</tt>
     *
     * @param login {@link User}'s login
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>login</tt> is null or empty or if <tt>login</tt>
     *                          or not accords to specify pattern {@link UserValidator}
     *                          or if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUserByLogin(String login) throws LibraryServiceException;

    /**
     * Find user {@link User} instance by <tt>id</tt>
     *
     * @param id {@link User}'s id
     * @return {@link User} instance
     * @throws LibraryServiceException if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUserById(int id) throws LibraryServiceException;

    /**
     * Find all users list {@link User}
     *
     * @return all users list {@link User}
     * @throws LibraryServiceException if {@link User} in empty
     *                          occurs after searching {@link User} into the data source
     */
    List<User> findAllUsers() throws LibraryServiceException;

    /**
     * Find all online users list {@link User}
     *
     * @return all online users list {@link User} from {@link UsersOnlineCache}
     */
    List<User> findUsersOnline();

    /**
     * Generate user's access token by <tt>id</tt> which uses for forget password functional
     *
     * @param id {@link User}'s id
     * @return {@link String} user's access token
     * @throws LibraryServiceException if {@link User} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    String getUpdatedRememberUserToken(int id) throws LibraryServiceException;

    /**
     * Sending access token with link to <tt>email</tt>
     *
     * @param email {@link User}'s email
     * @param pageRootUrl is page URL
     * @throws LibraryServiceException if {@link User} with <tt>email</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source or if something goes wrong while sending email
     */
    void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) throws LibraryServiceException;

    /**
     * Deleting user's access token by <tt>id</tt>
     *
     * @param id {@link User}'s id
     * @throws LibraryServiceException if {@link User} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    void deleteRememberUserToken(int id) throws LibraryServiceException;

    /**
     * Registers {@link User} with filled fields, allow access to service after using
     *                        single-use auth-token, which sent to email
     *
     * @param user {@link User} is filled user instance
     * @param pageRootUrl is page url which sends by email with single-use auth-token
     *
     * @throws LibraryServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@link UserValidator}
     *                          or if user with <tt>email</tt> or <tt>login</tt> has already exist
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    void registerUser(User user, String pageRootUrl) throws LibraryServiceException;

    /**
     * Ends registration of {@link User} by single-use auth-token and changing user ban status to false
     *
     * @param token {@link User}'s log in single-use auth-token
     * @throws LibraryServiceException if <tt>token</tt> in null or if an error occurs while
     *                          writing {@link User}'s ban status into data source
     */
    void postRegistrationApprovalByToken(String token) throws LibraryServiceException;

    /**
     * Update {@link User} with filled fields
     *
     * @param user {@link User} is filled user instance
     * @throws LibraryServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@link UserValidator}
     *                          or if user with <tt>email</tt> has already exist
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    void updateUserProfileData(User user) throws LibraryServiceException;

    /**
     * Update {@link User}'s ban status
     * @param user {@link User} is filled user instance
     * @throws LibraryServiceException if an error occurs while writing new
     *                          {@link User} into data source
     */
    void updateUserBanStatus(User user) throws LibraryServiceException;

    /**
     * Returns {@link Cache} map arguments is <tt>String,User</tt> which contains online users
     *
     * @return {@link Cache} map arguments is <tt>String,User</tt> online users cache
     */
    Cache<String, User> getUsersOnlineCache();
}
