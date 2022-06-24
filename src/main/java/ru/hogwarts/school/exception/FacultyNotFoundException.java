package ru.hogwarts.school.exception;

import org.webjars.NotFoundException;

public class FacultyNotFoundException extends NotFoundException {
    public FacultyNotFoundException() {
        super("факультет не найден");
    }
}
