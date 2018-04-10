package com.kupferwerk.presentation.main;

import com.kupferwerk.domain.usecases.GetSortedPerformedWorkoutsUseCase;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.LCEObserver;
import com.kupferwerk.presentation.core.BaseView;
import com.kupferwerk.presentation.main.mapper.WorkoutToModelMapper;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

class MainPresenter implements MainContract.Presenter {

   private class GetSortedPerformedWorkoutObserver extends LCEObserver<List<PerformedWorkout>> {

      GetSortedPerformedWorkoutObserver(BaseView baseView) {
         super(baseView);
      }

      @Override
      public void onError(Throwable error) {
         super.onError(error);
      }

      @Override
      public void onNext(List<PerformedWorkout> performedWorkouts) {
         super.onNext(performedWorkouts);
         List<WorkoutViewModel> workoutViewModels = new ArrayList<>();
         workoutViewModels.addAll(workoutToModelMapper.transform(performedWorkouts));
         view.showPerformedWorkouts(workoutViewModels);
      }
   }

   private final GetSortedPerformedWorkoutsUseCase getSortedPerformedWorkoutsUseCase;
   private final WorkoutToModelMapper workoutToModelMapper;
   private MainContract.View view;

   @Inject
   MainPresenter(WorkoutToModelMapper workoutToModelMapper,
                 GetSortedPerformedWorkoutsUseCase getSortedPerformedWorkoutsUseCase) {
      this.getSortedPerformedWorkoutsUseCase = getSortedPerformedWorkoutsUseCase;
      this.workoutToModelMapper = workoutToModelMapper;
   }

   @Override
   public void addWorkout() {
      view.showAddWorkout();
   }

   @Override
   public void onBind() {
      view.showLoading(true);
      view.showContent(false);
      view.showError(false);
      getSortedPerformedWorkoutsUseCase.execute(new GetSortedPerformedWorkoutObserver(view));
   }

   @Override
   public void onUnbind() {

   }

   @Override
   public void setView(MainContract.View baseView) {
      view = baseView;
   }
}
