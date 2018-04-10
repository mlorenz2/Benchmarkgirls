package com.kupferwerk.domain.usecases;

import com.kupferwerk.domain.UseCase;
import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.repo.WorkoutsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPerformedWorkoutsGroupedByName extends UseCase<List<PerformedWorkout>, String> {

   private WorkoutsRepository workoutsRepository;

   @Inject
   public GetPerformedWorkoutsGroupedByName(ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread,
                                            WorkoutsRepository workoutsRepository) {
      super(threadExecutor, postExecutionThread);
      this.workoutsRepository = workoutsRepository;
   }

   @Override
   public Observable<List<PerformedWorkout>> buildUseCaseObservable(final String workoutName) {
      return workoutsRepository.getPerformedWorkouts()
                               .flatMapIterable(list -> list)
                               .filter(performedWorkout -> performedWorkout.getWorkout()
                                                                           .getName()
                                                                           .equals(workoutName))
                               .toList()
                               .toObservable();
   }
}
