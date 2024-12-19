package com.github.sunsin.hogwarts;

import com.github.sunsin.hogwarts.model.dto.FacultyDto;
import com.github.sunsin.hogwarts.model.entity.Faculty;

public class TestData {

//    public static Faculty FACULTY_RED = Faculty.builder().

    public static Faculty getMockFaculty(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return faculty;
    }

    public static FacultyDto getMockFacultyDto(String name, String color) {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setName(name);
        facultyDto.setColor(color);
        return facultyDto;
    }


}
