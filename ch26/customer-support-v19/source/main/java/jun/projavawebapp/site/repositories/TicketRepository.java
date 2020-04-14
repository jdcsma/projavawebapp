package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends
        CrudRepository<Ticket, Long>,
        SearchableRepository<Ticket> {

}
