package com.kupferwerk.presentation.navigation;

import android.app.Activity;
import android.content.Intent;

import com.kupferwerk.presentation.addworkout.AddWorkoutActivity;
import com.kupferwerk.presentation.detail.DetailActivity;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Router {

   @Inject
   Router() {
   }

   public void navigateToAddWorkout(Activity activity) {
      Intent intentToStart = AddWorkoutActivity.start(activity);
      activity.startActivity(intentToStart);
   }

   public void navigateToWorkoutDetail(Activity activity, String workoutName) {
      Intent intenToStart = DetailActivity.start(activity, workoutName);
      activity.startActivity(intenToStart);
   }
}
