package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Attachment;

public interface AttachmentRepository extends
        GenericRepository<Long, Attachment> {
    Iterable<Attachment> getByTicketId(long ticketId);
}
