package com.kupferwerk.data.workout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Observable;

@Singleton
class WorkoutsFromNetwork implements WorkoutsDataSource {

   private static final String REFERENCE = "Workouts";
   private FirebaseDatabase database = FirebaseDatabase.getInstance();
   private DatabaseReference saveRef = database.getReference("userid/data");
   private DatabaseReference workoutReference = database.getReference(REFERENCE);

   @Inject
   WorkoutsFromNetwork() {
   }

   @Override
   public Observable<List<PerformedWorkout>> getPerformedWorkouts() {
      return RxFirebaseDatabase.observeSingleValueEvent(saveRef, dataSnapshot -> {
         List<PerformedWorkout> performedWorkoutList = new ArrayList<>();

         for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            performedWorkoutList.add(snapshot.getValue(PerformedWorkout.class));
         }
         return performedWorkoutList;
      })
                               .toObservable();
   }

   @Override
   public Observable<List<Workout>> getWorkouts() {
      return RxFirebaseDatabase.observeSingleValueEvent(workoutReference, dataSnapshot -> {
         List<Workout> workoutList = new ArrayList<>();
         for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            workoutList.add(snapshot.getValue(Workout.class));
         }
         return workoutList;
      })
                               .toObservable();
   }

   @Override
   public Observable<Void> saveWorkout(PerformedWorkout performedWorkout) {
      return Observable.create(emitter -> {
         DatabaseReference newRef = saveRef.push();
         newRef.setValue(performedWorkout);
         emitter.onComplete();
      });
   }
}
