package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


  //  private FacultyRepository facultyRepository;
    //private FacultyService facultyService = new FacultyService(facultyRepository);


    private final StudentRepository studentRepository;

        public StudentService(StudentRepository studentRepository) {
            this.studentRepository = studentRepository;

        }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> getStudentByAge(int studentAge) {
        return studentRepository.findByAge(studentAge);
    }

    public List<Student> getStudentByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public List<Student> getStudentByFaculty(Long facultyId) {
        return studentRepository.findStudentByFaculty_Id(facultyId);
    }
   //public Faculty getFacultyByStudent(Long studentId) {
     //  return facultyService.getFacultyByStudent(studentId);
   //}
}

