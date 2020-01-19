package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book getOneByIsbn(String isbn);
}
