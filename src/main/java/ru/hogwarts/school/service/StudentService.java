package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }

    public Student createStudent(Student student) {
        LOGGER.debug("metod createStudent started");
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        LOGGER.debug("metod getAllStudent started");
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long studentId) {
        LOGGER.debug("metod getStudentById started");
        return studentRepository.findById(studentId);
    }

    public Student updateStudent(Student student) {
        LOGGER.debug("metod updateStudent started");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        LOGGER.debug("metod deleteStudent started");
        studentRepository.deleteById(studentId);
    }

    public List<Student> getStudentByAge(int studentAge) {
        LOGGER.debug("metod getStudentByAge started");
        return studentRepository.findByAge(studentAge);
    }

    public List<Student> getStudentByAgeBetween(int ageMin, int ageMax) {
        LOGGER.debug("metod getStudentByAgeBetween started");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Integer getCountStudent() {
        LOGGER.debug("metod getCountStudent started");
        return studentRepository.getCountStudent();
    }

    public Double getAVGAgeStudent() {
        LOGGER.debug("metod getAVGAgeStudent started");
        return studentRepository.getAVGAgeStudent();
    }

    public List<Student> getMAXIdStudent() {
        LOGGER.debug("metod getMAXIdStudent started");
        return studentRepository.getMAXIdStudent();
    }


}