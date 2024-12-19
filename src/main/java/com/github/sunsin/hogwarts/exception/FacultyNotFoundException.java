package com.github.sunsin.hogwarts.exception;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(int facultyId) {
        super("Faculty with id " + facultyId + " not found");
    }

    public FacultyNotFoundException(String facultyName) {
        super("Faculty with name " + facultyName + " not found");
    }
}
