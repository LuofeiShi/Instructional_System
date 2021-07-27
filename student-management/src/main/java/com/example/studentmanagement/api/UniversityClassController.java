package com.example.studentmanagement.api;

import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentmanagement.model.UniversityClass;
import com.example.studentmanagement.service.UniversityClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {

  private UniversityClassService universityClassService;

  @Autowired
  public UniversityClassController(UniversityClassService universityClassService) {
    this.universityClassService = universityClassService;
  }

  @GetMapping
  List<UniversityClass> getAllClasses() {
    return universityClassService.getAllClasses();
  }

  @PostMapping
  @RequestMapping("/add")
  public ResponseEntity<String> addClass(@RequestBody UniversityClass universityClass) {
    try {
      UniversityClass savedUniversityClass = universityClassService.addClass(universityClass);
      return ok("Added class. " + savedUniversityClass.toString());
    } catch (InvalidUniversityClassException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
