package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Applicant;
import org.springframework.data.repository.CrudRepository;

public interface ApplicantRepository extends
        CrudRepository<Applicant, Long> {
}
