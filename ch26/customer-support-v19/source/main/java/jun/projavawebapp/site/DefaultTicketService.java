package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Attachment;
import jun.projavawebapp.site.entities.Ticket;
import jun.projavawebapp.site.entities.TicketComment;
import jun.projavawebapp.site.repositories.AttachmentRepository;
import jun.projavawebapp.site.repositories.SearchResult;
import jun.projavawebapp.site.repositories.TicketCommentRepository;
import jun.projavawebapp.site.repositories.TicketRepository;
import jun.projavawebapp.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService {

    @Inject
    TicketRepository ticketRepository;
    @Inject
    TicketCommentRepository commentRepository;
    @Inject
    AttachmentRepository attachmentRepository;
    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public List<Ticket> getAllTickets() {
        Iterable<Ticket> iterable = this.ticketRepository.findAll();
        List<Ticket> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public Page<SearchResult<Ticket>> search(String query, boolean useBooleanMode,
                                             Pageable pageable) {
        return this.ticketRepository.search(query, useBooleanMode, pageable);
    }

    @Override
    @Transactional
    public Ticket getTicket(long id) {
        Ticket ticket = this.ticketRepository.findById(id).orElse(null);
        if (ticket != null) {
            ticket.getNumberOfAttachments();
        }
        return ticket;
    }

    @Override
    @Transactional
    public void save(Ticket ticket) {
        if (ticket.getId() < 1)
            ticket.setDateCreated(Instant.now());

        this.ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<TicketComment> getComments(long ticketId, Pageable pageable) {
        return this.commentRepository.getByTicketId(ticketId, pageable);
    }

    @Override
    @Transactional
    public void save(TicketComment comment, long ticketId) {
        if (comment.getId() < 1) {
            comment.setTicketId(ticketId);
            comment.setDateCreated(Instant.now());
        }

        this.commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Attachment getAttachment(long id) {
        Attachment attachment = this.attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            attachment.getContents();
        }
        return attachment;
    }
}
