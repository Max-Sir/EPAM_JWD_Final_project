package by.sir.max.library.entity.book.bookcomponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

public class Author extends BaseBookComponent implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Author.class);

    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return  Objects.equals(uuid, author.uuid) &&
                Objects.equals(this.authorName, author.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, authorName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "uuid=" + uuid +
                ", author='" + authorName + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseBookComponent o) {
        if (o instanceof Author) {
            return authorName.compareTo(((Author) o).authorName);
        }
        LOGGER.warn(String.format("Try to compareTo incomparable types %s and %s", o.getClass(), Author.class));
        return 0;
    }
}
