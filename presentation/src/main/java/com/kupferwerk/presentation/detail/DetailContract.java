package com.kupferwerk.presentation.detail;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.core.BasePresenter;
import com.kupferwerk.presentation.core.BaseView;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

interface DetailContract {

   interface Presenter extends BasePresenter<DetailContract.View> {

      void addWorkout();

      void init(String name);

      void persistWorkout(PerformedWorkout performedWorkout);
   }

   interface View extends BaseView {
      void showAddWorkout(WorkoutViewModel workoutViewModel);

      void showPerformed(WorkoutViewModel workoutViewModels);

      void showWorkoutAdded();
   }
}
