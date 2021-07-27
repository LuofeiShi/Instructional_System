package com.example.studentmanagement.service;

import com.example.studentmanagement.dao.StudentDao;
import com.example.studentmanagement.dao.UniversityClassDao;
import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentmanagement.exceptions.StudentEmptyNameException;
import com.example.studentmanagement.exceptions.StudentNonExistException;
import com.example.studentmanagement.mapper.StudentMapper;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.UniversityClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Service
public class StudentService {

  private StudentDao studentDao;
  private UniversityClassDao universityClassDao;
  private StudentMapper studentMapper;

  @Autowired
  public StudentService(StudentDao studentDao,
                        UniversityClassDao universityClassDao,
                        StudentMapper studentMapper) {
    this.studentDao = studentDao;
    this.universityClassDao = universityClassDao;
    this.studentMapper = studentMapper;
  }

  public Student addStudent(Student student) {
    if (student.getName().isEmpty()) {
      throw new StudentEmptyNameException("Student name cannot be empty");
    }
    return studentDao.save(student);
  }

  public Student updateStudent(Student student) {
    if (student.getId() == null || !studentDao.existsById(student.getId())) {
      throw new StudentNonExistException();
    }
    return studentDao.save(student);
  }

  public Student assignClass(Long studentId, Long classId) {
    if (!studentDao.existsById(studentId)) {
      throw new StudentNonExistException();
    }
    if (!universityClassDao.existsById(classId)) {
      throw new InvalidUniversityClassException("Cannot find class Id " + classId);
    }

    Student student = getStudentById(studentId).get();
    UniversityClass universityClass = universityClassDao.findById(classId).get();

    student.setUniversityClass(universityClass);
    return studentDao.save(student);
  }

  public List<Student> getAllStudents() {
    return (List<Student>) studentDao.findAll();
  }

  public Optional<Student> getStudentById(Long id) {
    return studentDao.findById(id);
  }

  public List<Student> getStudentByName(String name) {return studentDao.findByName(name); }

  public List<Student> getStudentsContainName(String name) {
    return studentMapper.getStudentsContainStrInName("%" + name + "%");
  }

  public List<Student> getStudentsInClass(int year, int number) {
    return studentMapper.getStudentsInClass(year, number);
  }
}
