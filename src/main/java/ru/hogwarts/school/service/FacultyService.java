package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
   private final Map<Long, Faculty> facultyMap = new HashMap<>();
   private Long facultyId = 0L;

   public Faculty createFaculty(Faculty faculty) {
      facultyId++;
      Faculty createdFaculty=new Faculty(facultyId,faculty.getName(),faculty.getColor());
      facultyMap.put(facultyId, createdFaculty);
      return createdFaculty;
   }

   public Faculty  getFacultyById(Long facultyId) {
      return facultyMap.get(facultyId);
   }

   public Faculty  updateFaculty(Long facultyId, Faculty faculty) {
      facultyMap.put(facultyId, faculty);
      return faculty;
   }

   public Faculty  deleteFaculty(Long facultyId) {
      return facultyMap.remove(facultyId);
   }

   public List getFacultyByColor(String facultyColor) {
      List facultyColorList = facultyMap.entrySet().stream ()
              .filter(e->e.getValue().getColor().equals(facultyColor))
              .collect(Collectors.toList());
      return facultyColorList;
   }
}
