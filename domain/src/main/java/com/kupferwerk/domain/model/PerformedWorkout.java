package com.kupferwerk.domain.model;

public class PerformedWorkout {

   private long duration;
   private long performedAt;
   private Workout workout;

   public PerformedWorkout() {
   }

   public long getDuration() {
      return duration;
   }

   public void setDuration(long duration) {
      this.duration = duration;
   }

   public long getPerformedAt() {
      return performedAt;
   }

   public void setPerformedAt(long performedAt) {
      this.performedAt = performedAt;
   }

   public Workout getWorkout() {
      return workout;
   }

   public void setWorkout(Workout workout) {
      this.workout = workout;
   }
}
