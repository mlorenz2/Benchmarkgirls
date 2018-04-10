package com.kupferwerk.domain.usecases;

import com.kupferwerk.domain.UseCase;
import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;
import com.kupferwerk.domain.model.PerformedWorkout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetSortedPerformedWorkoutsUseCase extends UseCase<List<PerformedWorkout>, Void> {

   private final GetPerformedWorkoutsUseCase getPerformedWorkoutsUseCase;

   @Inject
   GetSortedPerformedWorkoutsUseCase(ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread,
                                     GetPerformedWorkoutsUseCase getPerformedWorkoutsUseCase) {
      super(threadExecutor, postExecutionThread);
      this.getPerformedWorkoutsUseCase = getPerformedWorkoutsUseCase;
   }

   @Override
   public Observable<List<PerformedWorkout>> buildUseCaseObservable(Void aVoid) {
      return getPerformedWorkoutsUseCase.execute(aVoid);
   }
}
