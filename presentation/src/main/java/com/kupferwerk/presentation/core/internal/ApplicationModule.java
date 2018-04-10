package com.kupferwerk.presentation.core.internal;

import android.content.Context;

import com.kupferwerk.data.executor.JobExecutor;
import com.kupferwerk.data.workout.WorkoutsRepositoryImpl;
import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;
import com.kupferwerk.domain.repo.WorkoutsRepository;
import com.kupferwerk.presentation.App;
import com.kupferwerk.presentation.core.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

   private App app;

   public ApplicationModule(App app) {
      this.app = app;
   }

   @Provides
   @Singleton
   Context provideContext() {
      return app.getApplicationContext();
   }

   @Provides
   @Singleton
   PostExecutionThread providePostExecutionThread(UIThread uiThread) {
      return uiThread;
   }

   @Provides
   @Singleton
   ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
      return jobExecutor;
   }

   @Provides
   @Singleton
   WorkoutsRepository provideWorkoutsRepository(WorkoutsRepositoryImpl workoutsRepository) {
      return workoutsRepository;
   }
}
