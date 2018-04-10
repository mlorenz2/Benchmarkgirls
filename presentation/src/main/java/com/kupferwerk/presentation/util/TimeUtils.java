package com.kupferwerk.presentation.util;

import android.content.Context;

import com.kupferwerk.presentation.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

   private static final SimpleDateFormat date_ddMMyyyy =
         new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

   public static String bestPerformed(Context context,
                                      long duration) {
      long[] durations = getCalculatedDuration(duration);
      long hours = durations[0];
      long minutes = durations[1];
      long seconds = durations[2];

      if (hours > 0) {
         return context.getString(R.string.detail_header_time_HMS, hours, minutes, seconds);
      } else if (hours == 0 && minutes > 0) {
         return context.getString(R.string.detail_header_time_MS, minutes, seconds);
      } else {
         return context.getString(R.string.detail_header_time_S, seconds);
      }
   }

   public static String performedDate(long performedAt) {
      return date_ddMMyyyy.format(new Date(performedAt));
   }

   public static String performedDuration(Context context,
                                          long duration) {
      long[] durations = getCalculatedDuration(duration);
      long hours = durations[0];
      long minutes = durations[1];
      long seconds = durations[2];
      if (hours != 0) {
         if (minutes < 0 || seconds < 0) {
            return context.getString(R.string.detail_item_duration_time_HMS_negative,
                  Math.abs(hours), Math.abs(minutes), seconds);
         }
         return context.getString(R.string.detail_item_duration_time_HMS, hours, minutes,
               seconds);
      } else {
         if (minutes < 0 || seconds < 0) {
            return context.getString(R.string.detail_item_duration_time_MS_negative,
                  Math.abs(minutes), Math.abs(seconds));
         }
         return context.getString(R.string.detail_item_duration_time_MS, minutes, seconds);
      }
   }

   private static long[] getCalculatedDuration(long duration) {
      long hours = TimeUnit.MILLISECONDS.toHours(duration);
      long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
      long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

      return new long[] { hours, minutes, seconds };
   }
}
