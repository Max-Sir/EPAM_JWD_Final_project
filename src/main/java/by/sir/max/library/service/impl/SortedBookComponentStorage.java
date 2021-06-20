package by.sir.max.library.service.impl;

import by.sir.max.library.entity.book.bookcomponent.BaseBookComponent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SortedBookComponentStorage<T extends BaseBookComponent> {
    private final List<T> components;

    SortedBookComponentStorage() {
        components = new CopyOnWriteArrayList<>();
    }

    void put(T element) {
        components.add(element);
        Collections.sort(components);
    }

    void removeByUUID(String uuid) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getUuid().equals(uuid)) {
                components.remove(i);
                break;
            }
        }
    }

    void remove(T element) {
        components.remove(element);
    }

    public List<T> getAllValuesList() {
        return components;
    }

    void putAllElements(Collection<T> collection) {
        components.addAll(collection);
        Collections.sort(components);
    }
}
