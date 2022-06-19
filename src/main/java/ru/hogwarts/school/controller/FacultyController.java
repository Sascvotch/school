package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity getFacultyById(@PathVariable Long facultyId) {
       Optional <Faculty> faculty =facultyService.getFacultyById(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity deleteStudent(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public ResponseEntity getFacultyByColorAndName( @RequestParam(required = false)  String facultyName, @RequestParam String facultyColor) {
        if (facultyName == null) {
            return ResponseEntity.ok(facultyService.getFacultyByColor(facultyColor));
        } else {
            return ResponseEntity.ok(facultyService.getFacultyByNameOrColor(facultyName, facultyColor));
        }
    }

   @GetMapping("/students")
  public ResponseEntity getStudentByFaculty(@RequestParam Long facultyId) {
    return ResponseEntity.ok(facultyService.getStudentsByFaculty(facultyId));
  }

}
