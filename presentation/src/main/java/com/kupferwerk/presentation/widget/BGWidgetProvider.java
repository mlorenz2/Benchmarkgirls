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

import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.main.MainActivity;

public class BGWidgetProvider extends AppWidgetProvider {

   public static final String ACTION_DATA_UPDATED =
         "com.benchmarkgirls.benchmarkgirls.ACTION_DATA_UPDATED";

   static void updateAppWidget(Context context,
                               AppWidgetManager appWidgetManager,
                               int appWidgetId) {
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
      views.setOnClickPendingIntent(R.id.widget, createHeaderOnClick(context));
      views.setEmptyView(R.id.widget_list, R.id.widget_empty);
      views.setRemoteAdapter(R.id.widget_list, new Intent(context, BGWidgetService.class));
      appWidgetManager.updateAppWidget(appWidgetId, views);
   }

   private static PendingIntent createHeaderOnClick(Context context) {
      Intent intent = new Intent(context, MainActivity.class);
      return PendingIntent.getActivity(context, 0, intent, 0);
   }

   @Override
   public void onReceive(@NonNull Context context,
                         @NonNull Intent intent) {
      super.onReceive(context, intent);
      if (ACTION_DATA_UPDATED.equals(intent.getAction())) {
         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
         int[] appWidgetIds =
               appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
         for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
         }
      }
   }

   @Override
   public void onUpdate(Context context,
                        AppWidgetManager appWidgetManager,
                        int[] appWidgetIds) {
      for (int appWidgetId : appWidgetIds) {
         updateAppWidget(context, appWidgetManager, appWidgetId);
      }
   }
}
