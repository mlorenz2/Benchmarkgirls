package com.kupferwerk.domain.usecases;

import com.kupferwerk.domain.UseCase;
import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.domain.repo.WorkoutsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetWorkoutsUseCase extends UseCase<List<Workout>, Void> {

   private final WorkoutsRepository workoutsRepository;

   @Inject
   GetWorkoutsUseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread,
                      WorkoutsRepository workoutsRepository) {
      super(threadExecutor, postExecutionThread);
      this.workoutsRepository = workoutsRepository;
   }

   @Override
   public Observable<List<Workout>> buildUseCaseObservable(Void aVoid) {
      return workoutsRepository.getWorkouts();
   }
}
