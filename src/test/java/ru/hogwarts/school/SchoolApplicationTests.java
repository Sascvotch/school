package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.net.URI;
import java.util.Optional;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    @InjectMocks
    private StudentService studentService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    private static final Student student = new Student();
    private static final Long id = 100L;


    @BeforeEach
    void setUp() {
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        studentService.createStudent(student);
        student.setId(id);
        student.setName("student1");
        student.setAge(23);
    }

    @AfterEach
    void tearUp() {
        studentService.deleteStudent(id);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/" + id, Student.class))
                .isEqualTo(student);
    }

    @Test
    public void testCreateStudent() {
        studentService.createStudent(student);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class)).isEqualTo(student);
    }

    @Test
    public void testUpdateStudent() {
        student.setName("student2");
        studentService.updateStudent(student);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("student2"));
    }

    @Test
    public void testDeleteStudent() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/student" + "/" + id, HttpMethod.DELETE, entity, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

}