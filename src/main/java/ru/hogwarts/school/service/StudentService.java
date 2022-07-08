package ru.hogwarts.school.service;

import org.jboss.jandex.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }

    public Student createStudent(Student student) {
        LOGGER.debug("metod createStudent started");
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        LOGGER.debug("metod getAllStudent started");
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long studentId) {
        LOGGER.debug("metod getStudentById started");
        return studentRepository.findById(studentId);
    }

    public Student updateStudent(Student student) {
        LOGGER.debug("metod updateStudent started");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        LOGGER.debug("metod deleteStudent started");
        studentRepository.deleteById(studentId);
    }

    public List<Student> getStudentByAge(int studentAge) {
        LOGGER.debug("metod getStudentByAge started");
        return studentRepository.findByAge(studentAge);
    }

    public List<Student> getStudentByAgeBetween(int ageMin, int ageMax) {
        LOGGER.debug("metod getStudentByAgeBetween started");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Integer getCountStudent() {
        LOGGER.debug("metod getCountStudent started");
        return studentRepository.getCountStudent();
    }

    public Double getAVGAgeStudent() {
        LOGGER.debug("metod getAVGAgeStudent started");
        return studentRepository.getAVGAgeStudent();
    }

    public List<Student> getMAXIdStudent() {
        LOGGER.debug("metod getMAXIdStudent started");
        return studentRepository.getMAXIdStudent();
    }

    public List<String> getStudentByNameWithA() {
        LOGGER.debug("metod getStudentByNameWithA started");
        List<Student> students = studentRepository.findAll();
        List<String> studentsWithA = students.stream()
                .map(e -> e.getName())
                .filter(e -> e.startsWith("A"))
                .sorted((e1, e2) -> e1.compareTo(e2))
                .map(e -> e.toUpperCase())
                .collect(Collectors.toList());
        return studentsWithA;
    }

    public Double getAVGAgeStudentStream() {
        LOGGER.debug("metod getAVGAgeStudentStream started");
        List<Student> students = studentRepository.findAll();
        Double avgAge = students.stream()
                .mapToDouble(e -> e.getAge())
                .average()
                .getAsDouble();
        return avgAge;
    }

    public void getStudentsName() {
        LOGGER.debug("metod getStudentsName started");
        List<Student> students = studentRepository.findAll();
        final StudentService studentService = new StudentService(studentRepository);

        studentService.printName(0, students.get(0));
        studentService.printName(1, students.get(1));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            studentService.printName(2, students.get(2));
            System.out.println(Thread.currentThread().getName());
            studentService.printName(3, students.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            studentService.printName(4, students.get(4));
            System.out.println(Thread.currentThread().getName());
            studentService.printName(5, students.get(5));
        }).start();

        // students.stream().parallel()
        //       .map(e -> e.getName())
        //     .peek(e -> System.out.println(e))
        //   .collect(Collectors.toList());


        //  Runnable task=()-> {
        //    System.out.println("0" + students.get(0).getName());
        //  System.out.println("1" + students.get(1).getName());
        //   };
        // Thread th1 = new Thread(task);
        // th1.start();

        // Runnable task2=()-> {
        //   System.out.println("2" + students.get(2).getName());
        //  System.out.println("3" + students.get(3).getName());
        // };
        //Thread th2 = new Thread(task2);
        // th2.start();

        // Runnable task3=()-> {
        //   System.out.println("4" + students.get(4).getName());
        // System.out.println("5" + students.get(5).getName());
        // };
        // Thread th3 = new Thread(task3       );
        //th3.start();
        //   printName(0,students.get(0));


        //   System.out.println("0"+students.get(0).getName());
        //   System.out.println("1"+students.get(1).getName());
        //   new Thread(() -> {
        //     System.out.println("2"+students.get(2).getName());
        //   System.out.println("3"+students.get(3).getName());
        // }).start();
        //new Thread(() -> {
        //  System.out.println("4"+students.get(4).getName());
        // System.out.println("5"+students.get(5).getName());
        //}).start();
    }

    public void getStudentsNameSynch() {
        LOGGER.debug("metod getStudentsNameSynch started");
        List<Student> students = studentRepository.findAll();
        final StudentService studentService = new StudentService(studentRepository);

        studentService.printNameSynch(0, students.get(0));
        studentService.printNameSynch(1, students.get(1));
        new Thread(() -> {
            studentService.printNameSynch(2, students.get(2));
            studentService.printNameSynch(3, students.get(3));
        }).start();
        new Thread(() -> {
            studentService.printNameSynch(4, students.get(4));
            studentService.printNameSynch(5, students.get(5));
        }).start();

    }

    private void printName(int number, Student student) {
        System.out.println(number + " " + student.getName());
    }

    private void printNameSynch(int number, Student student) {
        synchronized (StudentService.class) {
            System.out.println(number + " " + student.getName());
        }
    }
}