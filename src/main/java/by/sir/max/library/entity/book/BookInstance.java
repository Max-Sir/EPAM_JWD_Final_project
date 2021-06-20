package by.sir.max.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class BookInstance implements Serializable {
    private String uuid;
    private Book book;
    private boolean isAvailable;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInstance that = (BookInstance) o;
        return isAvailable == that.isAvailable &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, book, isAvailable);
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "uuid='" + uuid + '\'' +
                ", book=" + book +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
