package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final HashMap<Long, Student> studentMap = new HashMap<>();
    private Long studentId = 0L;

    public Student createStudent(Student student) {
        studentId=studentId+1;
       student.setId(studentId);
        studentMap.put(studentId, student);
        return student;
    }

    public HashMap<Long, Student> getAllStudent() {
        return studentMap;
    }

    public Student getStudentById(Long studentId) {
        return studentMap.get(studentId);
    }

    public Student updateStudent(long studentId, Student student) {
        studentMap.put(studentId, student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        return studentMap.remove(studentId);
    }

    public List<Student> getStudentByAge(int studentAge) {
        List studentAgeList = studentMap.entrySet().stream()
                .filter(e -> e.getValue().getAge() == studentAge)
                .collect(Collectors.toList());
        return studentAgeList;
    }
}
