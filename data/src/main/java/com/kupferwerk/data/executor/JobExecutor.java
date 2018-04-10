package com.kupferwerk.data.executor;

import android.support.annotation.NonNull;

import com.kupferwerk.domain.executor.ThreadExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
@Singleton
public class JobExecutor implements ThreadExecutor {
   private static class JobThreadFactory implements ThreadFactory {
      private int counter;

      @Override
      public Thread newThread(@NonNull Runnable runnable) {
         return new Thread(runnable, "android_" + counter++);
      }
   }

   private static final int KEEP_ALIVE = 10;
   private static final int MAX_THREAD_POOL_COUNT = 5;
   private static final int THREAD_POOL_COUNT = 3;
   private final ThreadPoolExecutor threadPoolExecutor;

   @Inject
   JobExecutor() {
      this.threadPoolExecutor =
            new ThreadPoolExecutor(THREAD_POOL_COUNT, MAX_THREAD_POOL_COUNT, KEEP_ALIVE,
                                   TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                                   new JobThreadFactory());
   }

   @Override
   public void execute(@NonNull Runnable runnable) {
      this.threadPoolExecutor.execute(runnable);
   }
}
