package com.kupferwerk.presentation.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BGWidgetService extends RemoteViewsService {
   @Override
   public RemoteViewsFactory onGetViewFactory(Intent intent) {
      return new BGWidgetViewFactory(getApplicationContext());
   }
}
