package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("metod createFaculty started");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getFacultyById(Long facultyId) {
        logger.debug("metod getFacultyById started");
        return facultyRepository.findById(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        logger.debug("metod updateFaculty started");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.debug("metod deleteFaculty started");
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> getFacultyByColor(String facultyColor) {
        logger.debug("metod getFacultyByColor started");
        return facultyRepository.findByColor(facultyColor);
    }

    public List<Faculty> getFacultyByNameOrColor(String facultyNameOrColor) {
        logger.debug("metod getFacultyByNameOrColor started");
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(facultyNameOrColor, facultyNameOrColor);
    }

    public Set<Student> getStudentsByFaculty(Long facultyId) {
        logger.debug("metod getStudentsByFaculty started");
        return facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException()).getStudents();
    }
}