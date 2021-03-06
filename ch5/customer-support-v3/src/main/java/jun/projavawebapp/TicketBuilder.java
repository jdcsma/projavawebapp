package jun.projavawebapp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TicketBuilder {

    private static final Object guard;
    private static volatile int sequence;

    private int id;
    private String customer;
    private String subject;
    private String body;
    private Map<String, Attachment> attachments;

    static {
        guard = new Object();
        sequence = 1;
    }

    public TicketBuilder setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public TicketBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public TicketBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public TicketBuilder addAttachment(Attachment attachment) {
        if (attachment != null) {
            if (attachments == null) {
                attachments = new LinkedHashMap<>();
            }
            attachments.put(attachment.getName(), attachment);
        }
        return this;
    }

    public Ticket build() throws NullPointerException {

        Objects.requireNonNull(this.customer, "customer must not null");
        Objects.requireNonNull(this.subject, "subject must not null");
        Objects.requireNonNull(this.body, "body must not null");

        synchronized (guard) {
            this.id = sequence++;
        }

        return new Ticket(this.id,
                this.customer, this.subject,
                this.body, this.attachments);
    }
}
