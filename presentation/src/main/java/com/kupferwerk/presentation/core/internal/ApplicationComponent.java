package com.kupferwerk.presentation.core.internal;

import com.kupferwerk.presentation.addworkout.AddWorkoutActivity;
import com.kupferwerk.presentation.detail.DetailActivity;
import com.kupferwerk.presentation.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = ApplicationModule.class)
public interface ApplicationComponent {
   void inject(MainActivity mainActivity);

   void inject(AddWorkoutActivity addWorkoutActivity);

   void inject(DetailActivity detailActivity);

}
