package jun.projavawebapp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ticket {

    private int id;
    private String customer;
    private String subject;
    private String body;
    private Map<String, Attachment> attachments;

    public Ticket(int id, String customer,
                  String subject, String body,
                  Map<String, Attachment> attachments) {
        this.id = id;
        this.customer = customer;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void addAttachment(Attachment attachment) {
        if (attachment == null) {
            return;
        }
        if (attachments == null) {
            attachments = new HashMap<>();
        }
        attachments.put(attachment.getName(), attachment);
    }

    public boolean hasAttachment(String name) {
        if (attachments == null) {
            return false;
        }
        return attachments.containsKey(name);
    }

    public Attachment getAttachment(String name) {
        if (attachments == null) {
            return null;
        }
        return attachments.get(name);
    }

    public int getNumberOfAttachments() {
        if (attachments == null) {
            return 0;
        }
        return attachments.size();
    }

    public Collection<Attachment> getAttachments() {
        if (attachments == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(attachments.values());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
