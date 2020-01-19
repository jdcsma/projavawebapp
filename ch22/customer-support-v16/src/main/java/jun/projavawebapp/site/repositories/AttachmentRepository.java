package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends
        CrudRepository<Attachment, Long> {
    Iterable<Attachment> getByTicketId(long ticketId);
}
