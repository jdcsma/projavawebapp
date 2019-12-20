package jun.projavawebapp.site.controller.form;

public class ReplyForm {

    private String user;
    private String message;

    // Mutators & Accessors

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

    @Override
    public String toString() {
        return "ReplyForm{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
