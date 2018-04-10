package com.kupferwerk.data.workout;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.domain.repo.WorkoutsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WorkoutsRepositoryImpl implements WorkoutsRepository {

   private final WorkoutsFromNetwork workoutsFromNetwork;

   @Inject
   WorkoutsRepositoryImpl(WorkoutsFromNetwork workoutsFromNetwork) {
      this.workoutsFromNetwork = workoutsFromNetwork;
   }

   @Override
   public Observable<List<PerformedWorkout>> getPerformedWorkouts() {
      return workoutsFromNetwork.getPerformedWorkouts();
   }

   @Override
   public Observable<List<Workout>> getWorkouts() {
      return workoutsFromNetwork.getWorkouts();
   }

   @Override
   public Observable<Void> saveWorkout(PerformedWorkout performedWorkout) {
      return workoutsFromNetwork.saveWorkout(performedWorkout);
   }
}
