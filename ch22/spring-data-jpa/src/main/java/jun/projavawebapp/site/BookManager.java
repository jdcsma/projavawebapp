package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Author;
import jun.projavawebapp.site.entities.Book;
import jun.projavawebapp.site.entities.Publisher;

import java.util.List;

public interface BookManager {

    List<Author> getAuthors();

    List<Book> getBooks();

    List<Publisher> getPublishers();

    void saveAuthor(Author author);

    void saveBook(Book book);

    void savePublisher(Publisher publisher);
}
