package ru.hogwarts.school.exception;
import org.webjars.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException() {
        super("студент не найден");
    }
}
