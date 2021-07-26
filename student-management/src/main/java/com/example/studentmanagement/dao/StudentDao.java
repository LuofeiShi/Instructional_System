package com.example.studentmanagement.dao;

import com.example.studentmanagement.model.Student;

import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Student, Long> {
}
