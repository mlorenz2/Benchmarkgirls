package com.kupferwerk.domain.repo;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;

import java.util.List;

import io.reactivex.Observable;

public interface WorkoutsRepository {

   Observable<List<PerformedWorkout>> getPerformedWorkouts();

   Observable<List<Workout>> getWorkouts();

   Observable<Void> saveWorkout(PerformedWorkout performedWorkout);
}
