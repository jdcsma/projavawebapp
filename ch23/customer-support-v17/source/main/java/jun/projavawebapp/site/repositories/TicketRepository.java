package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends
        CrudRepository<TicketEntity, Long>,
        SearchableRepository<TicketEntity> {

}
