package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.Applicant;
import jun.projavawebapp.site.entities.Employee;
import jun.projavawebapp.site.entities.NewsArticle;
import jun.projavawebapp.site.entities.Person;
import jun.projavawebapp.site.entities.Resume;
import jun.projavawebapp.site.entities.User;
import jun.projavawebapp.site.repositories.ApplicantRepository;
import jun.projavawebapp.site.repositories.EmployeeRepository;
import jun.projavawebapp.site.repositories.NewsArticleRepository;
import jun.projavawebapp.site.repositories.PersonRepository;
import jun.projavawebapp.site.repositories.ResumeRepository;
import jun.projavawebapp.site.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultEverythingService implements EverythingService {

    @Inject
    ApplicantRepository applicantRepository;
    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    NewsArticleRepository newsArticleRepository;
    @Inject
    PersonRepository personRepository;
    @Inject
    ResumeRepository resumeRepository;
    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public void saveApplicant(Applicant applicant) {
        this.applicantRepository.save(applicant);
    }

    @Override
    @Transactional
    public List<Applicant> getAllApplicants() {
        return toList(this.applicantRepository.findAll());
    }

    @Override
    @Transactional
    public Applicant getApplicant(long id) {
        Applicant applicant = this.applicantRepository.findById(id).get();
        applicant.getResumes();
        return applicant;
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return toList(this.employeeRepository.findAll());
    }

    @Override
    @Transactional
    public Employee getEmployee(long id) {
        Employee employee = this.employeeRepository.findById(id).get();
        employee.getAddresses().size();
        return employee;
    }

    @Override
    @Transactional
    public void saveNewsArticle(NewsArticle newsArticle) {
        if (newsArticle.getId() < 1)
            newsArticle.setDateCreated(Instant.now());
        else
            newsArticle.setDateModified(Instant.now());

        this.newsArticleRepository.save(newsArticle);
    }

    @Override
    @Transactional
    public List<NewsArticle> getAllNewsArticles() {
        return toList(this.newsArticleRepository.findAll());
    }

    @Override
    @Transactional
    public void savePerson(Person person) {
        this.personRepository.save(person);
    }

    @Override
    @Transactional
    public List<Person> getAllPersons() {
        return toList(this.personRepository.findAll());
    }

    @Override
    @Transactional
    public void saveResume(Resume resume) {
        this.resumeRepository.save(resume);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getId() < 1)
            user.setDateJoined(Instant.now());

        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return toList(this.userRepository.findAll());
    }

    private static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
