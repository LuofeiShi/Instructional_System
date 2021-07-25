package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Student {

  private UUID id;

  private String name;

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Student(UUID id, String name) {
    this.id = id;
    this.name = name;
  }
}
