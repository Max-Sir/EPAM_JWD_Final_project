package by.sir.max.library.entity.book.bookcomponent;

import java.util.UUID;

public abstract class BaseBookComponent implements Comparable<BaseBookComponent> {
    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void defineUUID() {
        uuid = UUID.randomUUID().toString();
    }
}
