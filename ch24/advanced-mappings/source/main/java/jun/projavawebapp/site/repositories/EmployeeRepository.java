package jun.projavawebapp.site.repositories;

import jun.projavawebapp.site.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends
        CrudRepository<Employee, Long> {
}
