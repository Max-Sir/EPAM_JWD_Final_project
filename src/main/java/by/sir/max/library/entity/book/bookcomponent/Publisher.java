package by.sir.max.library.entity.book.bookcomponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

public class Publisher extends BaseBookComponent implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Publisher.class);

    private String publisherTitle;

    public String getPublisherTitle() {
        return publisherTitle;
    }

    public void setPublisherTitle(String publisherTitle) {
        this.publisherTitle = publisherTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher1 = (Publisher) o;
        return Objects.equals(uuid, publisher1.uuid) &&
                Objects.equals(publisherTitle, publisher1.publisherTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, publisherTitle);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "uuid=" + uuid +
                ", publisher='" + publisherTitle + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseBookComponent o) {
        if (o instanceof Publisher) {
            return publisherTitle.compareTo(((Publisher) o).publisherTitle);
        }
        LOGGER.warn(String.format("Try to compareTo incomparable types %s and %s", o.getClass(), Publisher.class));
        return 0;
    }
}
