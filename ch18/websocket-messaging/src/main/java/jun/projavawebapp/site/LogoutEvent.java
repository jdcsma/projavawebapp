package jun.projavawebapp.site;

public class LogoutEvent extends AuthenticationEvent
{
    public LogoutEvent(String username)
    {
        super(username);
    }
}
