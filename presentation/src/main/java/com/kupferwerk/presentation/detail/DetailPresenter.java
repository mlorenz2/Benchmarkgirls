package com.kupferwerk.presentation.detail;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.usecases.GetPerformedWorkoutsGroupedByName;
import com.kupferwerk.domain.usecases.SaveWorkoutUseCase;
import com.kupferwerk.presentation.LCEObserver;
import com.kupferwerk.presentation.core.BaseView;
import com.kupferwerk.presentation.main.mapper.WorkoutToModelMapper;
import com.kupferwerk.presentation.main.model.Performed;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

class DetailPresenter implements DetailContract.Presenter {

   private class AddWorkoutObserver extends LCEObserver<Void> {

      AddWorkoutObserver(BaseView baseView) {
         super(baseView);
      }

      @Override
      public void onComplete() {
         super.onComplete();
         view.showWorkoutAdded();
      }
   }

   class GetObserver extends LCEObserver<List<PerformedWorkout>> {

      public GetObserver(BaseView baseView) {
         super(baseView);
      }

      @Override
      public void onNext(List<PerformedWorkout> performedWorkouts) {
         super.onNext(performedWorkouts);
         List<WorkoutViewModel> workoutViewModels = new ArrayList<>();
         workoutViewModels.addAll(workoutToModelMapper.transform(performedWorkouts));
         workoutViewModel = workoutViewModels.get(0);
         sort(workoutViewModel);
         setDiffs(workoutViewModel);
         view.showPerformed(workoutViewModel);
      }
   }

   private GetPerformedWorkoutsGroupedByName getPerformedWorkoutsGroupedByName;
   private SaveWorkoutUseCase saveWorkoutUseCase;
   private DetailContract.View view;
   private String workoutName;
   private WorkoutToModelMapper workoutToModelMapper;
   private WorkoutViewModel workoutViewModel;

   @Inject
   DetailPresenter(GetPerformedWorkoutsGroupedByName getPerformedWorkoutsGroupedByName,
                   WorkoutToModelMapper workoutToModelMapper,
                   SaveWorkoutUseCase saveWorkoutUseCase) {
      this.getPerformedWorkoutsGroupedByName = getPerformedWorkoutsGroupedByName;
      this.workoutToModelMapper = workoutToModelMapper;
      this.saveWorkoutUseCase = saveWorkoutUseCase;
   }

   @Override
   public void addWorkout() {
      view.showAddWorkout(workoutViewModel);
   }

   @Override
   public void init(String name) {
      this.workoutName = name;
   }

   @Override
   public void onBind() {
      view.showLoading(true);
      view.showContent(false);
      view.showError(false);
      if (workoutName != null) {
         getPerformedWorkoutsGroupedByName.execute(new GetObserver(view), workoutName);
      } else {
         throw new RuntimeException("WorkoutName must be set");
      }
   }

   @Override
   public void onUnbind() {

   }

   @Override
   public void persistWorkout(PerformedWorkout performedWorkout) {
      saveWorkoutUseCase.execute(new AddWorkoutObserver(view), performedWorkout);
   }

   @Override
   public void setView(DetailContract.View baseView) {
      this.view = baseView;
   }

   private void setDiffs(WorkoutViewModel workoutViewModel) {
      List<Performed> performedList = workoutViewModel.getPerformedList();
      long firstDuration = performedList.get(performedList.size() - 1)
                                        .getDuration();
      for (int i = performedList.size() - 1; i >= 0; i--) {
         Performed performed = performedList.get(i);
         if (i == performedList.size() - 1) {
            performed.setDurationDif(0);
         } else {
            performed.setDurationDif(performed.getDuration() - firstDuration);
         }
      }
   }

   private void sort(WorkoutViewModel workoutViewModel) {
      Collections.sort(workoutViewModel.getPerformedList(), new Comparator<Performed>() {
         @Override
         public int compare(Performed t1,
                            Performed t2) {
            return Long.compare(t2.getPerformedAt(), t1.getPerformedAt());
         }
      });
   }
}
