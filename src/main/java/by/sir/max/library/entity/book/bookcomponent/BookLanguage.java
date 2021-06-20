package by.sir.max.library.entity.book.bookcomponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

public class BookLanguage extends BaseBookComponent implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(BookLanguage.class);

    private String languageTitle;

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLanguage bookLanguage1 = (BookLanguage) o;
        return Objects.equals(getUuid(), bookLanguage1.getUuid()) &&
                Objects.equals(getLanguageTitle(), bookLanguage1.getLanguageTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getLanguageTitle());
    }

    @Override
    public String toString() {
        return "Language{" +
                "uuid=" + uuid +
                ", language='" + languageTitle + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseBookComponent o) {
        if (o instanceof BookLanguage) {
            return languageTitle.compareTo(((BookLanguage) o).languageTitle);
        }
        LOGGER.warn(String.format("Try to compareTo incomparable types %s and %s", o.getClass(), BookLanguage.class));
        return 0;
    }
}
