package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableRepository<T> {
    Page<T> search(SearchCriteria searchCriteria, Pageable pageable);
}
