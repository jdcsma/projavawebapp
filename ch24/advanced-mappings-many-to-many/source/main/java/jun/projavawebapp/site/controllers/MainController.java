package jun.projavawebapp.site.controllers;

import jun.projavawebapp.site.entities.Student;
import jun.projavawebapp.site.entities.School;
import jun.projavawebapp.site.services.SchoolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final Logger log = LogManager.getLogger();

    private SchoolService schoolService;

    @RequestMapping(value = "/schools")
    public String schools(Map<String, Object> model) {
        List<School> schools = this.schoolService.getSchools();
        model.put("schools", schools);
        return "list-schools";
    }

    @RequestMapping(value = "/school")
    public String school(
            Map<String, Object> model,
            @RequestParam("id") long schoolId) {

        List<School> schools = new ArrayList<>();

        School school = this.schoolService.getSchool(schoolId);
        if (school != null) {
            schools.add(school);
        }
        model.put("schools", schools);

        return "list-schools";
    }

    @RequestMapping(value = "/saveSchool")
    public View saveSchool(
            @RequestParam("schoolName") String schoolName,
            @RequestParam("studentName") String studentName) {

        School school = this.schoolService.getSchool(schoolName);

        if (school == null) {
            school = new School();
            school.setName(schoolName);
        }

        Student student = new Student();
        student.setName(studentName);

        school.addStudent(student);

        this.schoolService.saveSchool(school);

        return new RedirectView("/schools", true, false);
    }

    @RequestMapping(value = "/deleteStudent")
    public View deleteStudent(
            @RequestParam("schoolName") String schoolName,
            @RequestParam("studentName") String studentName)
            throws ServletException, IOException {

        Student student = new Student();
        student.setName(studentName);

        School school = this.schoolService.getSchool(schoolName);
        school.removeStudent(student);

        this.schoolService.saveSchool(school);

        return new RedirectView("/schools", true, false);
    }

    @Inject
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
}
