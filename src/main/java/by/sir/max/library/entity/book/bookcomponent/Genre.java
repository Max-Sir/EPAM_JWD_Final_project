package by.sir.max.library.entity.book.bookcomponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

public class Genre extends BaseBookComponent implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Genre.class);

    private String genreTitle;

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return Objects.equals(uuid, genre1.uuid) &&
                Objects.equals(genreTitle, genre1.genreTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, genreTitle);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "uuid=" + uuid +
                ", genre='" + genreTitle + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseBookComponent o) {
        if (o instanceof Genre) {
            return genreTitle.compareTo(((Genre) o).genreTitle);
        }
        LOGGER.warn(String.format("Try to compareTo incomparable types %s and %s", o.getClass(), Genre.class));
        return 0;
    }
}
