package by.sir.max.library.entity.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum UserRole {
    ADMIN(1),
    USER(2),
    GUEST(3);

    private static final Logger LOGGER = LogManager.getLogger(UserRole.class);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole getRoleById(int id) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.id == id) {
                return userRole;
            }
        }
        LOGGER.warn(String.format("Role with id: %d is not found", id));
        throw new EnumConstantNotPresentException(UserRole.class, String.format("Role with id: %d is not found", id));
    }
}
