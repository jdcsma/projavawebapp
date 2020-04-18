package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TicketCommentRepository extends
        CrudRepository<TicketComment, Long> {
    Page<TicketComment> getByTicketId(long ticketId, Pageable p);
}
