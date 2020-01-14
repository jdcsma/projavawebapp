package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.UserPrincipal;

public interface UserRepository extends
        GenericRepository<Long, UserPrincipal> {
    UserPrincipal getByUsername(String username);
}
