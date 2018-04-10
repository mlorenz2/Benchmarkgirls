package com.kupferwerk.domain.model;

import java.util.List;

public class Workout {

   private String description;
   private List<String> exercises;
   private String id;
   private String name;

   public Workout() {

   }

   public Workout(String description,
                  List<String> exercises,
                  String id,
                  String name) {
      this.description = description;
      this.exercises = exercises;
      this.id = id;
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public List<String> getExercises() {
      return exercises;
   }

   public void setExercises(List<String> exercises) {
      this.exercises = exercises;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
