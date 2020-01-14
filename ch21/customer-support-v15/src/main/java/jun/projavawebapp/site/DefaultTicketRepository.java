package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.TicketEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultTicketRepository extends
        GenericJpaRepository<Long, TicketEntity>
        implements TicketRepository {
}
