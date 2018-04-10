package com.kupferwerk.presentation.main.mapper;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.main.model.Performed;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class WorkoutToModelMapper {

   @Inject
   WorkoutToModelMapper() {
   }

   public List<WorkoutViewModel> transform(List<PerformedWorkout> performedWorkouts) {
      Collections.sort(performedWorkouts, new Comparator<PerformedWorkout>() {
         @Override
         public int compare(PerformedWorkout t1,
                            PerformedWorkout t2) {
            return t1.getWorkout()
                     .getName()
                     .compareTo(t2.getWorkout()
                                  .getName());
         }
      });
      String currentName = "";
      int index = -1;
      List<WorkoutViewModel> workoutViewModels = new ArrayList<>();
      for (PerformedWorkout performedWorkout : performedWorkouts) {
         if (currentName.equals(performedWorkout.getWorkout()
                                                .getName())) {
            // add to existing
            workoutViewModels.get(index)
                             .getPerformedList()
                             .add(new Performed(performedWorkout.getPerformedAt(),
                                   performedWorkout.getDuration()));
         } else {
            // new view model
            currentName = performedWorkout.getWorkout()
                                          .getName();
            WorkoutViewModel workoutViewModel = new WorkoutViewModel();
            workoutViewModel.setWorkout(performedWorkout.getWorkout());
            List<Performed> performedList = new ArrayList<>();
            performedList.add(
                  new Performed(performedWorkout.getPerformedAt(), performedWorkout.getDuration()));
            workoutViewModel.setPerformedList(performedList);
            workoutViewModels.add(workoutViewModel);
            index++;
         }
      }

      return workoutViewModels;
   }
}
