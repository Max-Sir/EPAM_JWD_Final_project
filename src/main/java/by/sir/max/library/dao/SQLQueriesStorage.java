package by.sir.max.library.dao;

public class SQLQueriesStorage {
    public static final String REGISTER_USER = "INSERT INTO user " +
            "(email, login, password, firstname, lastname, passport_serial_number, address, phone, is_banned, log_in_token) " +
            "value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login = (?)";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = (?)";
    public static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = (?)";
    public static final String FIND_USER_BY_ID_AND_TOKEN = "SELECT * FROM user WHERE id = (?) AND log_in_token = (?)";
    public static final String FIND_ALL_USERS = "SELECT * FROM user";
    public static final String UPDATE_USER_PROFILE_DATA = "UPDATE user SET email = (?), password = (?), address = (?)," +
            "  phone = (?) WHERE id = (?)";
    public static final String UPDATE_USER_BAN_STATUS = "UPDATE user SET is_banned = (?) WHERE id = (?)";
    public static final String UPDATE_USER_LOG_IN_TOKEN_TO_NULL = "UPDATE user SET user.log_in_token = NULL WHERE user.id = (?)";
    public static final String UPDATE_USER_LOG_IN_TOKEN_BY_ID = "UPDATE user SET user.log_in_token = (?) WHERE user.id = (?)";

    public static final String INSERT_BOOK_AUTHOR = "INSERT INTO book_author (uuid, author) value (?, ?)";
    public static final String FIND_BOOK_AUTHOR_BY_UUID = "SELECT * FROM book_author WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_AUTHORS = "SELECT * FROM book_author";

    public static final String INSERT_BOOK_PUBLISHER = "INSERT INTO book_publisher (uuid, title) value (?, ?)";
    public static final String FIND_BOOK_PUBLISHER_BY_UUID = "SELECT * FROM book_publisher WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_PUBLISHERS = "SELECT * FROM book_publisher";

    public static final String INSERT_BOOK_GENRE = "INSERT INTO book_genre (uuid, genre) value (?, ?)";
    public static final String FIND_BOOK_GENRE_BY_UUID = "SELECT * FROM book_genre WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_GENRES = "SELECT * FROM book_genre";

    public static final String INSERT_BOOK_LANGUAGE = "INSERT INTO book_language (uuid, language) value (?, ?)";
    public static final String FIND_BOOK_LANGUAGE_BY_UUID = "SELECT * FROM book_language WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_LANGUAGES = "SELECT * FROM book_language";

    public static final String INSERT_BOOK = "INSERT INTO book (uuid, genre_uuid, language_uuid, publisher_uuid, " +
            "author_uuid, title, publish_year, pages_quantity, description) value (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_BOOK_BY_UUID = "SELECT book.uuid, book.title, book.publish_year, book.pages_quantity, book.description, " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\"" +
            "FROM book " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid " +
            "WHERE book.uuid = (?)";
    public static final String FIND_ALL_BOOKS = "SELECT book.uuid, book.title, book.publish_year, book.pages_quantity, book.description, " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\", " +
            "COUNT(book_instance.book_uuid) as \"total_book_instance_quantity\", " +
            "COUNT(CASE WHEN book_instance.is_available = 1 THEN 1 ELSE NULL END) as \"available_book_instance_quantity\"" +
            "FROM book " +
            "LEFT JOIN book_instance ON book_instance.book_uuid = book.uuid " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid ";
    public static final String FIND_ALL_BOOKS_QUANTITY = "SELECT COUNT(book.uuid) FROM book ";
    public static final String FIND_BOOK_UUID_BY_FIELDS = "SELECT uuid FROM book " +
            "WHERE book.genre_uuid = (?) AND book.language_uuid = (?) AND book.publisher_uuid = (?) " +
            "AND book.author_uuid = (?) AND book.title = (?) AND book.publish_year = (?) AND book.pages_quantity = (?)";

    public static final String INSERT_BOOK_INSTANCE = "INSERT INTO book_instance (uuid, book_uuid) value (?, ?)";
    public static final String UPDATE_BOOK_INSTANCE_AVAILABLE_STATUS = "UPDATE book_instance SET book_instance.is_available = (?) WHERE book_instance.uuid = (?)";
    public static final String FIND_AVAILABLE_BOOKS_QUANTITY_BY_UUID = "SELECT count(book_instance.uuid) as available_book_quantity" +
            "FROM book_instance WHERE book_instance.book_uuid = (?) AND book_instance.is_available = 1";
    public static final String FIND_AVAILABLE_BOOK_INSTANCE_UUID_BY_BOOK_UUID = "SELECT book_instance.uuid " +
            "FROM book_instance WHERE book_instance.book_uuid = (?) AND book_instance.is_available = 1";
    public static final String FIND_BOOKS_QUANTITY_BY_UUID = "SELECT count(book_instance.uuid) as available_book_quantity" +
            "FROM book_instance WHERE book_instance.book_uuid = (?)";

    public static final String INSERT_BOOK_ORDER = "INSERT INTO book_order (uuid, user_id, book_instance_uuid, order_type_id) " +
            "value (?, ?, ?, ?)";

    public static final String UPDATE_BOOK_ORDER_STATUS = "UPDATE book_order SET order_status_id = (?) WHERE uuid = (?)";

    public static final String FIND_BOOK_ORDERS_QUANTITY_BY_USER_ID = "SELECT COUNT(uuid) FROM book_order WHERE user_id = (?)";

    public static final String FIND_OPEN_BOOK_ORDERS_QUANTITY = "SELECT COUNT(uuid) FROM book_order WHERE order_status_id = 1 OR order_status_id = 3 ";

    public static final String FIND_BOOK_ORDER_BY_UUID = "SELECT book_order.*, " +
            "book.uuid as \"book.uuid\", book.title as \"book.title\", book.publish_year as \"book.publish_year\", " +
            "book.pages_quantity as \"book.pages_quantity\", book.description as \"book.description\", " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\"," +
            "user.id as \"user.id\", user.login as \"user.login\", user.email as \"user.email\", " +
            "book_instance.uuid as \"book_instance.uuid\", book_instance.is_available as \"book_instance.is_available\"" +
            "FROM book_order " +
            "LEFT JOIN book_instance ON book_order.book_instance_uuid = book_instance.uuid " +
            "LEFT JOIN book ON book_instance.book_uuid = book.uuid " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid " +
            "LEFT JOIN user ON user.id = book_order.user_id " +
            "WHERE book_order.uuid = (?)";

    public static final String FIND_BOOK_ORDERS_BY_USER_ID = "SELECT book_order.*, " +
            "book.uuid as \"book.uuid\", book.title as \"book.title\", book.publish_year as \"book.publish_year\", " +
            "book.pages_quantity as \"book.pages_quantity\", book.description as \"book.description\", " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\"," +
            "user.id as \"user.id\", user.login as \"user.login\", user.email as \"user.email\", " +
            "book_instance.uuid as \"book_instance.uuid\", book_instance.is_available as \"book_instance.is_available\"" +
            "FROM book_order " +
            "LEFT JOIN book_instance ON book_order.book_instance_uuid = book_instance.uuid " +
            "LEFT JOIN book ON book_instance.book_uuid = book.uuid " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid " +
            "LEFT JOIN user ON user.id = book_order.user_id " +
            "WHERE book_order.user_id = (?) " +
            "ORDER BY book_order.date LIMIT (?) OFFSET (?)" ;

    public static final String FIND_ALL_BOOK_ORDERS_BY_USER_ID = "SELECT book_order.*, " +
            "book.uuid as \"book.uuid\", book.title as \"book.title\", book.publish_year as \"book.publish_year\", " +
            "book.pages_quantity as \"book.pages_quantity\", book.description as \"book.description\", " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\"," +
            "user.id as \"user.id\", user.login as \"user.login\", user.email as \"user.email\", " +
            "book_instance.uuid as \"book_instance.uuid\", book_instance.is_available as \"book_instance.is_available\"" +
            "FROM book_order " +
            "LEFT JOIN book_instance ON book_order.book_instance_uuid = book_instance.uuid " +
            "LEFT JOIN book ON book_instance.book_uuid = book.uuid " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid " +
            "LEFT JOIN user ON user.id = book_order.user_id " +
            "WHERE book_order.user_id = (?) " +
            "ORDER BY book_order.date ";

    public static final String FIND_BOOK_ORDERS_WITH_OPEN_REQUEST = "SELECT book_order.*, " +
            "book.uuid as \"book.uuid\", book.title as \"book.title\", book.publish_year as \"book.publish_year\", " +
            "book.pages_quantity as \"book.pages_quantity\", book.description as \"book.description\", " +
            "book_genre.uuid as \"book_genre.uuid\", book_genre.genre as \"book_genre.genre\", " +
            "book_publisher.uuid as \"book_publisher.uuid\", book_publisher.title as \"book_publisher.title\", " +
            "book_author.uuid as \"book_author.uuid\", book_author.author as \"book_author.author\", " +
            "book_language.uuid as \"book_language.uuid\" , book_language.language as \"book_language.language\"," +
            "user.id as \"user.id\", user.login as \"user.login\", user.email as \"user.email\", " +
            "book_instance.uuid as \"book_instance.uuid\", book_instance.is_available as \"book_instance.is_available\"" +
            "FROM book_order " +
            "LEFT JOIN book_instance ON book_order.book_instance_uuid = book_instance.uuid " +
            "LEFT JOIN book ON book_instance.book_uuid = book.uuid " +
            "LEFT JOIN book_genre ON book.genre_uuid = book_genre.uuid "  +
            "LEFT JOIN book_publisher ON book.publisher_uuid = book_publisher.uuid " +
            "LEFT JOIN book_author ON book.author_uuid = book_author.uuid " +
            "LEFT JOIN book_language ON book.language_uuid = book_language.uuid " +
            "LEFT JOIN user ON user.id = book_order.user_id " +
            "WHERE order_status_id = 1 OR order_status_id = 3 " +
            "ORDER BY book_order.date LIMIT ? OFFSET ? ";

    private SQLQueriesStorage() {}
}
