package by.sir.max.library.command;

import by.sir.max.library.command.reciever.admin.*;
import by.sir.max.library.command.reciever.page.*;
import by.sir.max.library.command.reciever.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandStorage {
    ERROR_PAGE(new ErrorPage(), "errorPage"),
    HOME_PAGE(new HomePage(), "indexPage"),
    REGISTER_PAGE(new RegisterPage(), "registerPage"),
    LOG_IN_PAGE(new LogInPage(), "logInPage"),
    PROFILE_PAGE(new ProfilePage(), "profilePage"),
    ADMIN_PAGE(new UsersOnlinePage(), "adminPage"),
    USERS_LIST_PAGE(new AllUsersPage(), "allUsersList"),
    USER_ORDERS_PAGE(new MyOrdersPage(),"userOrdersPage"),
    BOOK_CATALOG_PAGE(new FindBookPage(),"bookCatalogPage"),

    ADD_BOOK_ORDER(new AddBookOrder(), "addBookOrder"),
    APPROVE_BOOK_ORDER(new ApproveBookOrder(), "approveBookOrder"),
    CANCEL_BOOK_ORDER(new CancelBookOrder(), "cancelBookOrder"),
    RETURN_BOOK_ORDER(new ReturnBookOrder(), "returnBookOrder"),

    ADD_BOOK_PAGE(new AddBookPage(), "addBookPage"),
    ADD_BOOK(new AddBook(), "addBook"),
    ADD_BOOK_COMPONENT_PAGE(new AddBookComponentPage(), "addBookComponentPage"),
    ADD_BOOK_AUTHOR(new AddBookAuthor(), "addBookAuthor"),
    ADD_BOOK_PUBLISHER(new AddBookPublisher(), "addBookPublisher"),
    ADD_BOOK_GENRE(new AddBookGenre(), "addBookGenre"),
    ADD_BOOK_LANGUAGE(new AddBookLanguage(), "addBookLanguage"),
    OPEN_ORDERS_PAGE(new OpenOrdersPage(), "openOrdersPage"),

    SWITCH_LANG(new SwitchLanguageCommand(), "switchLang"),
    REGISTER_USER(new RegisterUserCommand(), "registerUser"),
    UPDATE_PROFILE_USER(new UpdateUserInfoCommand(), "updateUserInfo"),
    LOG_IN(new LogInCommand(), "logIn"),
    FORGET_PASSWORD_GENERATE_EMAIL(new ForgetPasswordEmailSendingCommand(), "sendForgetPasswordData"),
    FORGET_PASSWORD_LOG_IN(new LogInByTokenLink(), "logInByForgetPasswordLink"),
    POST_REGISTRATION_ACCOUNT_APPROVAL(new PostRegistrationAccountApproval(), "postRegistrationAccountApproval"),

    LOG_OUT(new LogOutCommand(), "logOut"),
    TOGGLE_USER_BAN(new ToggleUserBan(),"banUser");


    private static final Logger LOGGER = LogManager.getLogger(CommandStorage.class);

    private final Command command;
    private final String commandName;

    CommandStorage(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public static Command getCommandByName(String commandName) {
        return getCommandEnumByName(commandName).command;
    }

    public static CommandStorage getCommandEnumByName(String commandName) {
        for (CommandStorage type: CommandStorage.values()) {
            if (type.commandName.equals(commandName)){
                return type;
            }
        }
        LOGGER.warn(String.format("Command %s is not found, forward to HomePage", commandName));
        return HOME_PAGE;
    }
}
