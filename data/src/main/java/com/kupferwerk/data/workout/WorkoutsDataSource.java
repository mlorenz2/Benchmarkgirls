package com.kupferwerk.data.workout;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;

import java.util.List;

import io.reactivex.Observable;

interface WorkoutsDataSource {

   Observable<List<PerformedWorkout>> getPerformedWorkouts();

   Observable<List<Workout>> getWorkouts();

   Observable<Void> saveWorkout(PerformedWorkout performedWorkout);
}
