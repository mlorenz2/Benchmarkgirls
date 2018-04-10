package com.kupferwerk.presentation.main;

import com.kupferwerk.presentation.core.BasePresenter;
import com.kupferwerk.presentation.core.BaseView;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.List;

interface MainContract {

   interface Presenter extends BasePresenter<MainContract.View> {
      void addWorkout();
   }

   interface View extends BaseView {
      void showAddWorkout();

      void showPerformedWorkouts(List<WorkoutViewModel> workouts);
   }
}
