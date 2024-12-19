package com.github.sunsin.hogwarts.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long id) {
        super("Student with id " + id + " not found");
    }
}
