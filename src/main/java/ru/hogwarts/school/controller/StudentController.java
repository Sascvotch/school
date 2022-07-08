package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.OptionalDouble;

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
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
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
    public ResponseEntity<List<Student>> getStudentByAge(@RequestParam Integer studentAgeMin, @RequestParam(required = false) Integer studentAgeMax) {
        if (studentAgeMax == null) {
            return ResponseEntity.ok(studentService.getStudentByAge(studentAgeMin));
        } else {
            return ResponseEntity.ok(studentService.getStudentByAgeBetween(studentAgeMin, studentAgeMax));
        }
    }

    @GetMapping("/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@RequestParam Long studentId) {
        Student student = studentService.getStudentById(studentId).orElseThrow();
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountStudent() {
        return ResponseEntity.ok(studentService.getCountStudent());
    }

    @GetMapping("/avg_age_sql")
    public ResponseEntity<Double> getAVGAgeStudent() {
        return ResponseEntity.ok(studentService.getAVGAgeStudent());
    }

    @GetMapping("/max_id")
    public ResponseEntity<List<Student>> getMAXIdStudent() {
        return ResponseEntity.ok(studentService.getMAXIdStudent());
    }
    @GetMapping("/student_a")
    public ResponseEntity<List<String>> getStudentWithA() {
        return ResponseEntity.ok(studentService.getStudentByNameWithA());
    }
    @GetMapping("/avg_age_stream")
    public ResponseEntity<OptionalDouble> getAVGAgeStudentStream() {
        return ResponseEntity.ok(studentService.getAVGAgeStudentStream());
    }
}
