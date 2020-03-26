package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.School;
import jun.projavawebapp.site.repositories.SchoolRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultSchoolService implements SchoolService {

    private SchoolRepository schoolRepository;

    @Transactional
    @Override
    public List<School> getSchools() {
        List<School> schools = new ArrayList<>();
        this.schoolRepository.findAll().forEach(schools::add);
        return schools;
    }

    @Transactional
    @Override
    public School getSchool(long id) {
        return this.schoolRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public School getSchool(String name) {
        return this.schoolRepository.findByName(name).orElse(null);
    }

    @Transactional
    @Override
    public void deleteSchool(long id) {
        this.schoolRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveSchool(School school) {
        this.schoolRepository.save(school);
    }

    @Inject
    public void setSchoolRepository(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }
}
