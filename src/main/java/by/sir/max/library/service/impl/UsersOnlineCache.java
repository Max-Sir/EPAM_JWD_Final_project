package by.sir.max.library.service.impl;

import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.service.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class UsersOnlineCache implements Cache<String, User> {
    private static final Logger LOGGER = LogManager.getLogger(UsersOnlineCache.class);

    private final Map<String, User> userCache;

    private UsersOnlineCache(){
        userCache = new ConcurrentHashMap<>();
    }

    private static class ActiveUsersCacheSingletonHolder {
        static final UsersOnlineCache INSTANCE = new UsersOnlineCache();
    }

    static UsersOnlineCache getInstance() {
        return ActiveUsersCacheSingletonHolder.INSTANCE;
    }

    void put(User user) throws LibraryServiceException {
        if (user == null) {
            LOGGER.warn("Can't put null value to cache");
            throw new LibraryServiceException("service.commonError");
        }
        userCache.put(user.getLogin(), user);
    }

    public User get(String login) throws LibraryServiceException {
        if (login == null) {
            LOGGER.warn("Can't get null login from cache");
            throw new LibraryServiceException("service.commonError");
        }
        return userCache.get(login);

    }

    List<User> getAllValues() {
        return new ArrayList<>(userCache.values());
    }

    void remove(String login) {
        if (login == null) {
            LOGGER.warn("Can't remove null login from cache");
        } else {
            userCache.remove(login);
        }
    }

    void removeAll() {
        userCache.clear();
    }
}

