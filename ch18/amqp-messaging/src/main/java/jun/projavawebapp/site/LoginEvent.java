package jun.projavawebapp.site;

public class LoginEvent extends AuthenticationEvent {
    public LoginEvent(String username) {
        super(username);
    }
}
