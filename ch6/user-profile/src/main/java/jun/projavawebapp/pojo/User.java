package jun.projavawebapp.pojo;

import java.util.HashMap;
import java.util.Map;

public class User extends BaseUser {

    private Map<String, Boolean> permissions;

    public User() {
        permissions = new HashMap<>();
    }

    public User(long userID, String username,
                String firstName, String lastName,
                Map<String, Boolean> permissions) {
        super(userID, username, firstName, lastName);
        this.permissions = permissions;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }
}
