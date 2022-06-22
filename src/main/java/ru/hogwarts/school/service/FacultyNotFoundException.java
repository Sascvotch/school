package ru.hogwarts.school.service;

import org.webjars.NotFoundException;

public class FacultyNotFoundException extends NotFoundException {
    public FacultyNotFoundException() {
        super("факультет не найден");
    }
}
