package com.kupferwerk.presentation.addworkout;

import com.kupferwerk.domain.DefaultObserver;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.domain.usecases.GetWorkoutsUseCase;
import com.kupferwerk.domain.usecases.SaveWorkoutUseCase;
import com.kupferwerk.presentation.LCEObserver;
import com.kupferwerk.presentation.core.BaseView;

import java.util.List;

import javax.inject.Inject;

class AddWorkoutPresenter implements AddWorkoutContract.Presenter {

   private class AddWorkoutObserver extends LCEObserver<List<Workout>> {

      AddWorkoutObserver(BaseView baseView) {
         super(baseView);
      }

      @Override
      public void onNext(List<Workout> workouts) {
         super.onNext(workouts);
         view.showWorkoutList(workouts);
         String[] workoutSuggestions = new String[workouts.size()];
         for (int i = 0; i < workouts.size(); i++) {
            Workout workout = workouts.get(i);
            workoutSuggestions[i] = workout.getName();
         }
         view.showSearchSuggestions(workoutSuggestions);
      }
   }

   private class SaveWorkoutObserver extends DefaultObserver<Void> {
      @Override
      public void onComplete() {
         super.onComplete();
         view.showSavedSuccessful();
      }

      @Override
      public void onError(Throwable error) {
         super.onError(error);
      }
   }

   private final GetWorkoutsUseCase getWorkoutsUseCase;
   private final SaveWorkoutUseCase saveWorkoutUseCase;
   private AddWorkoutContract.View view;

   @Inject
   AddWorkoutPresenter(GetWorkoutsUseCase getWorkoutsUseCase,
                       SaveWorkoutUseCase saveWorkoutUseCase) {
      this.getWorkoutsUseCase = getWorkoutsUseCase;
      this.saveWorkoutUseCase = saveWorkoutUseCase;
   }

   @Override
   public void addWorkout(Workout workout) {
      view.showAddWorkout(workout);
   }

   @Override
   public void onBind() {
      view.showLoading(true);
      view.showContent(false);
      view.showError(false);
      getWorkoutsUseCase.execute(new AddWorkoutObserver(view));
   }

   @Override
   public void onUnbind() {
      getWorkoutsUseCase.dispose();
   }

   @Override
   public void persistWorkout(PerformedWorkout performedWorkout) {
      saveWorkoutUseCase.execute(new SaveWorkoutObserver(), performedWorkout);
   }

   @Override
   public void setView(AddWorkoutContract.View baseView) {
      view = baseView;
   }
}
