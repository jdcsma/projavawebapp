package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Author;
import jun.projavawebapp.site.entities.Book;
import jun.projavawebapp.site.entities.Publisher;
import jun.projavawebapp.site.repositories.AuthorRepository;
import jun.projavawebapp.site.repositories.BookRepository;
import jun.projavawebapp.site.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultBookManager implements BookManager {

    @Inject
    AuthorRepository authorRepository;
    @Inject
    BookRepository bookRepository;
    @Inject
    PublisherRepository publisherRepository;

    @Override
    @Transactional
    public List<Author> getAuthors() {
        return this.toList(this.authorRepository.findAll());
    }

    @Override
    @Transactional
    public List<Book> getBooks() {
        return this.toList(this.bookRepository.findAll());
    }

    @Override
    @Transactional
    public List<Publisher> getPublishers() {
        return this.toList(this.publisherRepository.findAll());
    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public void saveAuthor(Author author) {
        this.authorRepository.save(author);
    }

    @Override
    @Transactional
    public void saveBook(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public void savePublisher(Publisher publisher) {
        this.publisherRepository.save(publisher);
    }
}
