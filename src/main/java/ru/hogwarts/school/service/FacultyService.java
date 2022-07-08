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
import java.util.OptionalDouble;
import java.util.Set;

@Service
public class FacultyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        LOGGER.debug("metod createFaculty started");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getFacultyById(Long facultyId) {
        LOGGER.debug("metod getFacultyById started");
        return facultyRepository.findById(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        LOGGER.debug("metod updateFaculty started");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        LOGGER.debug("metod deleteFaculty started");
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> getFacultyByColor(String facultyColor) {
        LOGGER.debug("metod getFacultyByColor started");
        return facultyRepository.findByColor(facultyColor);
    }

    public List<Faculty> getFacultyByNameOrColor(String facultyNameOrColor) {
        LOGGER.debug("metod getFacultyByNameOrColor started");
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(facultyNameOrColor, facultyNameOrColor);
    }

    public Set<Student> getStudentsByFaculty(Long facultyId) {
        LOGGER.debug("metod getStudentsByFaculty started");
        return facultyRepository.findById(facultyId).orElseThrow(() -> {
            LOGGER.error("There is not faculty with id = " + facultyId);
            return new FacultyNotFoundException();
        }).getStudents();
    }

    public Optional <String> getNameFacultyMax() {
        LOGGER.debug("metod getNameFacultyMax started");
        List<Faculty> faculties = facultyRepository.findAll();
        Optional  <String> nameFacultyMax = faculties.stream().parallel()
                .map(e -> e.getName())
                .reduce((e1, e2) -> {
                    if (e1.length() > e2.length()) return e1;
                    else return e2;
                });
        return nameFacultyMax;
    }
}