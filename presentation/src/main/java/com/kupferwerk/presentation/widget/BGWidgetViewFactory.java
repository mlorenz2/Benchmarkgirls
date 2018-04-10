package com.kupferwerk.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.main.model.Performed;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BGWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {

   private Context context;
   private List<WorkoutViewModel> dataList = new ArrayList<>();
   private FirebaseDatabase database = FirebaseDatabase.getInstance();
   private DatabaseReference saveRef = database.getReference("userid/data");

   public BGWidgetViewFactory(Context context) {
      this.context = context;
   }

   @Override
   public int getCount() {
      return dataList.size();
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public RemoteViews getLoadingView() {
      return new RemoteViews(context.getPackageName(), R.layout.main_overview_item);
   }

   @Override
   public RemoteViews getViewAt(int position) {
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_overview_item);

      WorkoutViewModel workoutViewModel = dataList.get(position);
      views.setTextViewText(R.id.overview_workout_name, workoutViewModel.getWorkout()
                                                                        .getName());
      views.setTextViewText(R.id.overview_workout_count, "" + workoutViewModel.getPerformedCount());
      views.setTextViewText(R.id.overview_last_performed_date, workoutViewModel.getLastPerformed());

      return views;
   }

   @Override
   public int getViewTypeCount() {
      return 1;
   }

   @Override
   public boolean hasStableIds() {
      return true;
   }

   @Override
   public void onCreate() {
      updateWorkouts();
   }

   @Override
   public void onDataSetChanged() {
      Intent intent = new Intent();
      intent.setAction(BGWidgetProvider.ACTION_DATA_UPDATED);
      context.sendBroadcast(intent);
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
      appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
   }

   @Override
   public void onDestroy() {

   }

   private List<WorkoutViewModel> transform(List<PerformedWorkout> performedWorkouts) {
      Collections.sort(performedWorkouts, new Comparator<PerformedWorkout>() {
         @Override
         public int compare(PerformedWorkout t1,
                            PerformedWorkout t2) {
            return t1.getWorkout()
                     .getName()
                     .compareTo(t2.getWorkout()
                                  .getName());
         }
      });
      String currentName = "";
      int index = -1;
      List<WorkoutViewModel> workoutViewModels = new ArrayList<>();
      for (PerformedWorkout performedWorkout : performedWorkouts) {
         if (currentName.equals(performedWorkout.getWorkout()
                                                .getName())) {
            // add to existing
            workoutViewModels.get(index)
                             .getPerformedList()
                             .add(new Performed(performedWorkout.getPerformedAt(),
                                   performedWorkout.getDuration()));
         } else {
            // new view model
            currentName = performedWorkout.getWorkout()
                                          .getName();
            WorkoutViewModel workoutViewModel = new WorkoutViewModel();
            workoutViewModel.setWorkout(performedWorkout.getWorkout());
            List<Performed> performedList = new ArrayList<>();
            performedList.add(
                  new Performed(performedWorkout.getPerformedAt(), performedWorkout.getDuration()));
            workoutViewModel.setPerformedList(performedList);
            workoutViewModels.add(workoutViewModel);
            index++;
         }
      }

      return workoutViewModels;
   }

   private void updateWorkouts() {
      saveRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }

         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            List<PerformedWorkout> performedWorkoutList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
               performedWorkoutList.add(snapshot.getValue(PerformedWorkout.class));
            }

            List<WorkoutViewModel> workoutViewModels = new ArrayList<>();
            workoutViewModels.addAll(transform(performedWorkoutList));

            dataList.addAll(workoutViewModels);

            Intent intent = new Intent();
            intent.setAction(BGWidgetProvider.ACTION_DATA_UPDATED);
            context.sendBroadcast(intent);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds =
                  appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
         }
      });
   }
}
