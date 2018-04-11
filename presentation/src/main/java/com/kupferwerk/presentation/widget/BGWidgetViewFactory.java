package com.kupferwerk.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.kupferwerk.domain.DefaultObserver;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.usecases.GetSortedPerformedWorkoutsUseCase;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.core.Injector;
import com.kupferwerk.presentation.main.mapper.WorkoutToModelMapper;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

public class BGWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {

   private class GetSortedPerformedWorkoutObserver extends DefaultObserver<List<PerformedWorkout>> {
      @Override
      public void onNext(List<PerformedWorkout> performedWorkouts) {
         super.onNext(performedWorkouts);
         dataList.clear();
         dataList.addAll(workoutToModelMapper.transform(performedWorkouts));
         mCountDownLatch.countDown();
      }
   }

   @Inject
   GetSortedPerformedWorkoutsUseCase getSortedPerformedWorkoutsUseCase;
   @Inject
   WorkoutToModelMapper workoutToModelMapper;
   private Context context;
   private List<WorkoutViewModel> dataList = new ArrayList<>();
   private CountDownLatch mCountDownLatch;

   public BGWidgetViewFactory(Context context) {
      Injector.getAppComponent()
              .inject(this);
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
   }

   @Override
   public void onDataSetChanged() {
      mCountDownLatch = new CountDownLatch(1);
      updateWorkouts();
      try {
         mCountDownLatch.await();
         Intent intent = new Intent();
         intent.setAction(BGWidgetProvider.ACTION_DATA_UPDATED);
         context.sendBroadcast(intent);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void onDestroy() {

   }

   private void updateWorkouts() {
      getSortedPerformedWorkoutsUseCase.execute(new GetSortedPerformedWorkoutObserver());
   }
}
