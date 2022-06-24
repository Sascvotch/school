package ru.hogwarts.school.exception;

import org.webjars.NotFoundException;

import java.util.function.Supplier;

public class AvatarNotFoundException extends NotFoundException  {
    public AvatarNotFoundException() {
        super("аватар не найден");
    }
}
