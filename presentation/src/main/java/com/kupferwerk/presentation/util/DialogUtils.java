package com.kupferwerk.presentation.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.presentation.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

public class DialogUtils {

   public interface CreatedWorkoutCallback {
      void createdWorkout(PerformedWorkout performedWorkout);
   }

   public static void showWorkout(final Activity activity,
                                  final Workout workout,
                                  final CreatedWorkoutCallback createdWorkoutCallback) {

      final View customDialogView = activity.getLayoutInflater()
                                            .inflate(R.layout.performed_workout_time, null);

      ListView listView = ButterKnife.findById(customDialogView, R.id.exercise_list);
      listView.setDividerHeight(0);
      listView.setAdapter(
            new ArrayAdapter<>(activity, R.layout.detail_overview_item, workout.getExercises()));

      final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
      dialogBuilder.setMessage(
            activity.getString(R.string.add_workout_dialog_title, workout.getName(),
                  workout.getDescription()))
                   .setView(customDialogView)
                   .setPositiveButton(activity.getString(R.string.log), null)
                   .setNegativeButton(activity.getString(R.string.cancel), null);
      final AlertDialog dialog = dialogBuilder.create();
      dialog.setOnShowListener(new DialogInterface.OnShowListener() {

         @Override
         public void onShow(DialogInterface dialogInterface) {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                  PerformedWorkout performedWorkout = new PerformedWorkout();
                  performedWorkout.setWorkout(workout);
                  performedWorkout.setPerformedAt(new Date().getTime());
                  NumberPicker m = ButterKnife.findById(customDialogView, R.id.numberPickerM);
                  NumberPicker h = ButterKnife.findById(customDialogView, R.id.numberPickerH);
                  NumberPicker s = ButterKnife.findById(customDialogView, R.id.numberPickerS);
                  long min = m.getValue();
                  long hour = h.getValue();
                  long second = s.getValue();
                  long millis = TimeUnit.HOURS.toMillis(hour) + TimeUnit.MINUTES.toMillis(min) +
                        TimeUnit.SECONDS.toMillis(second);
                  if (millis == 0) {
                     Toast.makeText(activity, activity.getString(R.string.error_too_less_duration),
                           Toast.LENGTH_SHORT)
                          .show();
                  } else {
                     performedWorkout.setDuration(millis);
                     createdWorkoutCallback.createdWorkout(performedWorkout);
                     dialog.dismiss();
                  }
               }
            });
         }
      });
      dialog.show();
   }
}
