package ru.hogwarts.school.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query(value = "SELECT COUNT(*) as count FROM student", nativeQuery = true)
    Integer getCountStudent();

    @Query(value = "SELECT AVG(age) as age FROM student", nativeQuery = true)
    Integer getAVGAgeStudent();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getMAXIdStudent();

}