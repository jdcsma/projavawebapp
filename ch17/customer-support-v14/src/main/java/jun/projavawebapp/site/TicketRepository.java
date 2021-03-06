package jun.projavawebapp.site;

import java.util.List;

public interface TicketRepository
{
    List<Ticket> getAll();
    Ticket get(long id);
    void add(Ticket ticket);
    void update(Ticket ticket);
    void delete(long id);
}
