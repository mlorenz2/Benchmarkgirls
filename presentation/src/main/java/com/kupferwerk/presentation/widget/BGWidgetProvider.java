package com.kupferwerk.presentation.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.main.MainActivity;
import com.kupferwerk.presentation.main.model.Performed;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BGWidgetProvider extends AppWidgetProvider {

   public static final String ACTION_DATA_UPDATED =
         "com.benchmarkgirls.benchmarkgirls.ACTION_DATA_UPDATED";

   @Override
   public void onReceive(@NonNull Context context,
                         @NonNull Intent intent) {
      super.onReceive(context, intent);
      if (ACTION_DATA_UPDATED.equals(intent.getAction())) {
         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
         int[] appWidgetIds =
               appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
         appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
      }
   }

   @Override
   public void onUpdate(Context context,
                        AppWidgetManager appWidgetManager,
                        int[] appWidgetIds) {
      for (int appWidgetId : appWidgetIds) {

         Intent intent = new Intent(context, MainActivity.class);
         PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

         RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
         views.setOnClickPendingIntent(R.id.widget, pendingIntent);
         views.setEmptyView(R.id.widget_list, R.id.widget_empty);
         views.setRemoteAdapter(R.id.widget_list, new Intent(context, BGWidgetService.class));
         appWidgetManager.updateAppWidget(appWidgetId, views);
      }
   }
}
