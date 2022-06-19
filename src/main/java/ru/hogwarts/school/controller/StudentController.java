package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity <Optional<Student>> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity <Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity <Student> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }


    @GetMapping("/age")
    public ResponseEntity <List<Student>> getStudentByAge(@RequestParam Integer studentAgeMin, @RequestParam(required = false) Integer studentAgeMax) {
        if (studentAgeMax == null) {
            return ResponseEntity.ok(studentService.getStudentByAge(studentAgeMin));
        } else {
            return ResponseEntity.ok(studentService.getStudentByAgeBetween(studentAgeMin, studentAgeMax));
        }
    }

    @GetMapping("/faculty")
    public ResponseEntity <Faculty> getFacultyByStudent(@RequestParam Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student.get().getFaculty());
    }
}