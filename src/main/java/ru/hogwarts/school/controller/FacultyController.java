package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<Optional<Faculty>> getFacultyById(@PathVariable Long facultyId) {
        Optional<Faculty> faculty = facultyService.getFacultyById(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity <Faculty> deleteStudent(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nameORcolor")
    public ResponseEntity<List<Faculty>> getFacultyByColorAndName(@RequestParam String facultyNameOrColor) {
        return ResponseEntity.ok(facultyService.getFacultyByNameOrColor(facultyNameOrColor));
    }

    @GetMapping("/studentsSet")
   public ResponseEntity<Set<Student>> getStudentByFacultySet(@RequestParam Long facultyId) {
      return ResponseEntity.ok(facultyService.getStudentsByFaculty(facultyId));
  }

    @GetMapping("/getNameFacultyMax")
    public ResponseEntity <Optional <String>> getNameFacultyMax() {
        return ResponseEntity.ok(facultyService.getNameFacultyMax());
    }
}