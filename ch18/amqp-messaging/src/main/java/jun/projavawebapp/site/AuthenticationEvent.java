package jun.projavawebapp.site;

import java.io.Serializable;

public abstract class AuthenticationEvent extends ClusterEvent {
    public AuthenticationEvent(Serializable source) {
        super(source);
    }
}
