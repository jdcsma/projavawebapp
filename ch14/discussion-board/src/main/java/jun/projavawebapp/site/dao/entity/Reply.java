package jun.projavawebapp.site.dao.entity;

import java.time.Instant;

public class Reply {

    private long id;
    private long discussionId;
    private String user;
    private String message;
    private Instant created;

    // Mutators & Accessors

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", discussionId=" + discussionId +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", created=" + created +
                '}';
    }
}
