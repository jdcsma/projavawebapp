package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Applicant;
import jun.projavawebapp.site.entities.Employee;
import jun.projavawebapp.site.entities.NewsArticle;
import jun.projavawebapp.site.entities.Person;
import jun.projavawebapp.site.entities.Resume;
import jun.projavawebapp.site.entities.User;

import java.util.List;

public interface EverythingService {

    void saveApplicant(Applicant applicant);

    List<Applicant> getAllApplicants();

    Applicant getApplicant(long id);

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployee(long id);

    void saveNewsArticle(NewsArticle newsArticle);

    List<NewsArticle> getAllNewsArticles();

    void savePerson(Person person);

    List<Person> getAllPersons();

    void saveResume(Resume resume);

    void saveUser(User user);

    List<User> getAllUsers();
}
