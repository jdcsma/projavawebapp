package jun.projavawebapp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TicketRepository {

    private static final Object guard;
    private static final Map<Integer, Ticket> ticketDatabase;

    static {
        guard = new Object();
        ticketDatabase = new HashMap<>();
    }

    public static void addTicket(Ticket ticket) {
        if (ticket != null) {
            synchronized (guard) {
                ticketDatabase.put(ticket.getId(), ticket);
            }
        }
    }

    public static Ticket getTicket(String id) {
        try {
            return getTicket(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Ticket getTicket(int id) {
        synchronized (guard) {
            return ticketDatabase.get(id);
        }
    }

    public static void removeTicket(int id) {
        synchronized (guard) {
            ticketDatabase.remove(id);
        }
    }

    public static Collection<Ticket> getTickets() {
        synchronized (guard) {
            return Collections.unmodifiableCollection(ticketDatabase.values());
        }
    }
}
