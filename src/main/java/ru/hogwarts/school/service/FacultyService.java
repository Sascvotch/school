package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService {

   // private StudentRepository studentRepository;
   private final StudentService studentService;// = new StudentService(studentRepository);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService=studentService;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> getFacultyByColor(String facultyColor) {
        return facultyRepository.findByColor(facultyColor);
    }

    public List<Faculty> getFacultyByNameOrColor(String facultyNameOrColor) {
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(facultyNameOrColor, facultyNameOrColor);
    }
   public List<Student> getStudentsByFaculty (Long facultyId) {
       return  studentService.getStudentByFaculty(facultyId);
  }
  //  public Set<Student> getStudentsByFaculty1 (Long facultyId) {
    //    return facultyRepository.findById(facultyId).get().getStudents();
   //}

}