package com.kupferwerk.presentation.main.model;

import com.kupferwerk.domain.model.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutViewModel {
   private static final String LAST_PERFORMED_DATE = "dd.MM.yyyy";
   List<Performed> performedList;
   Workout workout;

   public WorkoutViewModel() {
   }

   public long getBestPerformedTime() {
      if (performedList.size() > 0) {
         long bestPerformed = performedList.get(0)
                                           .getDuration();
         for (Performed performed : performedList) {
            if (bestPerformed > performed.getDuration()) {
               bestPerformed = performed.getDuration();
            }
         }
         return bestPerformed;
      } else {
         return 0;
      }
   }

   public String getLastPerformed() {
      long lastPerformed = 0;
      for (Performed performed : performedList) {
         long performedAt = performed.getPerformedAt();
         if (lastPerformed < performedAt) {
            lastPerformed = performedAt;
         }
      }
      SimpleDateFormat sdf = new SimpleDateFormat(LAST_PERFORMED_DATE, Locale.getDefault());
      return sdf.format(new Date(lastPerformed));
   }

   public long getPerformedCount() {
      return performedList.size();
   }

   public List<Performed> getPerformedList() {
      return performedList;
   }

   public void setPerformedList(List<Performed> performedList) {
      this.performedList = performedList;
   }

   public Workout getWorkout() {
      return workout;
   }

   public void setWorkout(Workout workout) {
      this.workout = workout;
   }
}
