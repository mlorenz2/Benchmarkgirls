package com.kupferwerk.domain.usecases;

import com.kupferwerk.domain.UseCase;
import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;
import com.kupferwerk.domain.repo.WorkoutsRepository;
import com.kupferwerk.domain.model.PerformedWorkout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPerformedWorkoutsUseCase extends UseCase<List<PerformedWorkout>, Void> {

   private final WorkoutsRepository workoutsRepository;

   @Inject
   GetPerformedWorkoutsUseCase(ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread,
                               WorkoutsRepository workoutsRepository) {
      super(threadExecutor, postExecutionThread);
      this.workoutsRepository = workoutsRepository;
   }

   @Override
   public Observable<List<PerformedWorkout>> buildUseCaseObservable(Void aVoid) {
      return workoutsRepository.getPerformedWorkouts();
   }
}
