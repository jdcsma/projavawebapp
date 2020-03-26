package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.School;

import java.util.List;

public interface SchoolService {

    List<School> getSchools();

    School getSchool(long id);

    School getSchool(String name);

    void deleteSchool(long id);

    void saveSchool(School school);
}
