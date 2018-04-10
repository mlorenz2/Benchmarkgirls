package com.kupferwerk.presentation;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kupferwerk.presentation.core.BaseView;

public abstract class BaseActivity extends AppCompatActivity {

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         NavUtils.navigateUpFromSameTask(this);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}