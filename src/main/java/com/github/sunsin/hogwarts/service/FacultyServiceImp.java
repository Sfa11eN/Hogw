package com.github.sunsin.hogwarts.service;

import com.github.sunsin.hogwarts.exception.FacultyNotFoundException;
import com.github.sunsin.hogwarts.exception.NotUniqueFacultyNameException;
import com.github.sunsin.hogwarts.mapper.FacultyMapper;
import com.github.sunsin.hogwarts.mapper.StudentMapper;
import com.github.sunsin.hogwarts.model.dto.FacultyDto;
import com.github.sunsin.hogwarts.model.dto.StudentDto;
import com.github.sunsin.hogwarts.model.entity.Faculty;
import com.github.sunsin.hogwarts.repository.FacultyRepository;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImp implements FacultyService {

    private final FacultyMapper facultyMapper;
    private final StudentMapper studentMapper;
    private final FacultyRepository facultyRepository;

    public FacultyServiceImp(FacultyMapper facultyMapper, StudentMapper studentMapper, FacultyRepository facultyRepository) {
        this.facultyMapper = facultyMapper;
        this.studentMapper = studentMapper;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Collection<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public Faculty getFaculty(int id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));

    }

    @Override
    public Faculty getFaculty(String name) {
        return facultyRepository.findByName(name).orElseThrow(() -> new FacultyNotFoundException(name));
    }

    @Transactional
    @Override
    public Faculty updateFaculty(int id, FacultyDto dto) {
        if (facultyRepository.findById(id).isPresent()) {
            dto.setId(id);
            return save(facultyMapper.toEntity(dto));
        }
        throw new FacultyNotFoundException(id);
    }

    @Override
    public Collection<Faculty> findFaculty(String name, String color) {
        if (color == null && name == null) {
            return facultyRepository.findAll();
        }
        return facultyRepository.findAllByNameOrColor(name, color);
    }

    @Override
    public void deleteFaculty(int id) {
        facultyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Faculty addFaculty(FacultyDto dto) {
        return save(facultyMapper.toEntity(dto));
    }

    @Override
    @Transactional
    public Collection<StudentDto> getFacultyStudents(int id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        if (faculty.getStudents() == null) {
            return List.of();
        }
        return faculty.getStudents().stream().map(studentMapper::toDto).toList();
    }

    private Faculty save(Faculty faculty) {
        try {
            return facultyRepository.saveAndFlush(faculty);

        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new NotUniqueFacultyNameException(faculty.getName());
            }
            throw e;
        }
    }
}
