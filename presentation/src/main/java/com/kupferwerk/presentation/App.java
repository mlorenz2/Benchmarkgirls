package com.kupferwerk.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.kupferwerk.presentation.core.Injector;

public class App extends Application {

   @Override
   public void onCreate() {
      super.onCreate();
      Injector.init(this);
      Stetho.initializeWithDefaults(this);
   }
}
