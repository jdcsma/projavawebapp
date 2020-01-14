package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Book;

public interface BookRepository
        extends GenericRepository<Long, Book> {
    Book getByIsbn(String isbn);
}
