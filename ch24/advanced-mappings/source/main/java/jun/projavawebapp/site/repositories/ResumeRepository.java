package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends
        CrudRepository<Resume, Long> {
}
