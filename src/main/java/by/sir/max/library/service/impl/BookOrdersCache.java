package by.sir.max.library.service.impl;

import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.service.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookOrdersCache implements Cache<String, BookOrdersCache.SortedUserBookOrdersCache> {
    private static final Logger LOGGER = LogManager.getLogger(BookOrdersCache.class);

    private final Map<String, SortedUserBookOrdersCache> userOrdersCache;
    private final SortedUserBookOrdersCache sortedUserBookOrdersCache;

    private BookOrdersCache(){
        userOrdersCache = new ConcurrentHashMap<>();
        sortedUserBookOrdersCache = new SortedUserBookOrdersCache();
    }

    private static class BookOrdersCacheSingletonHolder {
        static final BookOrdersCache INSTANCE = new BookOrdersCache();
    }

    static BookOrdersCache getInstance() {
        return BookOrdersCacheSingletonHolder.INSTANCE;
    }

    void put(String login, List<BookOrder> orders) throws LibraryServiceException {
        if (login == null || orders == null) {
            LOGGER.warn("Can't put null value to cache");
            throw new LibraryServiceException("service.commonError");
        }
        sortedUserBookOrdersCache.addAllElements(orders);
        userOrdersCache.put(login, sortedUserBookOrdersCache);
    }

    @Override
    public SortedUserBookOrdersCache get(String userLogin) throws LibraryServiceException {
        if (userLogin == null) {
            LOGGER.warn("Can't get null login from cache");
            throw new LibraryServiceException("service.commonError");
        }
        return userOrdersCache.get(userLogin);
    }

    void remove(String login) {
        if (login == null) {
            LOGGER.warn("Can't remove null login from cache");
        } else {
            userOrdersCache.remove(login);
        }
    }

    void removeAll() {
        userOrdersCache.clear();
    }

    public static class SortedUserBookOrdersCache {
        List<BookOrder> userBookOrders = new CopyOnWriteArrayList<>();

        public void put(BookOrder bookOrder) throws LibraryServiceException {
            if (bookOrder == null) {
                LOGGER.warn("Can't put null bookOrder to cache");
                throw new LibraryServiceException("service.commonError");
            }
            userBookOrders.add(bookOrder);
            Collections.sort(userBookOrders);
        }

        BookOrder getBookOrderByUUID(String uuid) throws LibraryServiceException {
            if (uuid == null) {
                LOGGER.warn("Can't get user's book order by null uuid from cache");
                throw new LibraryServiceException("service.commonError");
            }
            for (BookOrder bookOrder: userBookOrders) {
                if (bookOrder.getUuid().equals(uuid)) {
                    return bookOrder;
                }
            }
            LOGGER.warn("Can't get user's book order by uuid from cache");
            throw new LibraryServiceException("service.commonError");
        }

        public List<BookOrder> getAllValuesList() {
            return userBookOrders;
        }

        void removeByUUID(String uuid) throws LibraryServiceException {
            if (uuid == null) {
                LOGGER.warn("Can't remove user's book order by uuid from cache");
                throw new LibraryServiceException("service.commonError");
            }
            for (int i = 0; i < userBookOrders.size(); i++) {
                if (userBookOrders.get(i).getUuid().equals(uuid)) {
                    userBookOrders.remove(i);
                    break;
                }
            }
        }

        void removeAll() {
            userBookOrders.clear();
        }

        private void addAllElements(Collection<BookOrder> collection) throws LibraryServiceException {
            if (collection == null) {
                LOGGER.warn("Can't add null collection to cache");
                throw new LibraryServiceException("service.commonError");
            }
            userBookOrders.addAll(collection);
            Collections.sort(userBookOrders);
        }
    }
}
