package com.kupferwerk.presentation.main.model;

public class Performed {

   private long duration;
   private long durationDif;
   private long performedAt;

   public Performed(long performedAt,
                    long duration) {
      this.performedAt = performedAt;
      this.duration = duration;
   }

   public long getDuration() {
      return duration;
   }

   public void setDuration(long duration) {
      this.duration = duration;
   }

   public long getDurationDiff() {
      return durationDif;
   }

   public long getPerformedAt() {
      return performedAt;
   }

   public void setPerformedAt(long performedAt) {
      this.performedAt = performedAt;
   }

   public void setDurationDif(long durationDif) {
      this.durationDif = durationDif;
   }
}
