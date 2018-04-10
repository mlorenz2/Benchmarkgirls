package com.kupferwerk.presentation.core;

import com.kupferwerk.presentation.App;
import com.kupferwerk.presentation.core.internal.ApplicationComponent;
import com.kupferwerk.presentation.core.internal.ApplicationModule;
import com.kupferwerk.presentation.core.internal.DaggerApplicationComponent;

public final class Injector {
   private static ApplicationComponent appComponent;

   public static ApplicationComponent getAppComponent() {
      return appComponent;
   }

   public static void init(App app) {
      appComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(app))
            .build();
   }
}
