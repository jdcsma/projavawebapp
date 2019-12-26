package jun.projavawebapp.site;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTicketRepository implements TicketRepository {

    private volatile long TICKET_ID_SEQUENCE = 1L;

    private final Map<Long, Ticket> ticketDatabase = new LinkedHashMap<>();

    @Override
    public synchronized List<Ticket> getAll() {
        return new ArrayList<>(this.ticketDatabase.values());
    }

    @Override
    public synchronized Ticket get(long id) {
        return this.ticketDatabase.get(id);
    }

    @Override
    public synchronized void add(Ticket ticket) {
        ticket.setId(this.getNextTicketId());
        this.ticketDatabase.put(ticket.getId(), ticket);
    }

    @Override
    public synchronized void update(Ticket ticket) {
        this.ticketDatabase.put(ticket.getId(), ticket);
    }

    private long getNextTicketId() {
        return this.TICKET_ID_SEQUENCE++;
    }
}
