package com.kupferwerk.presentation.addworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.presentation.BaseActivity;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.core.Injector;
import com.kupferwerk.presentation.util.DialogUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddWorkoutActivity extends BaseActivity implements AddWorkoutContract.View {

   @BindView (R.id.content)
   View content;
   @BindView (R.id.error)
   View error;
   @BindView (R.id.loading)
   ProgressBar loading;
   @Inject
   AddWorkoutPresenter presenter;
   @BindView (R.id.recycler)
   RecyclerView recycler;
   @BindView (R.id.search_view)
   MaterialSearchView searchView;
   @BindView (R.id.toolbar)
   Toolbar toolbar;
   private FirebaseAnalytics mFirebaseAnalytics;
   private WorkoutAdapter workoutAdapter =
         new WorkoutAdapter(new WorkoutAdapter.OnListItemClickedListener() {
            @Override
            public void onListItemClicked(Workout workout) {
               presenter.addWorkout(workout);
            }
         });

   public static Intent start(Activity activity) {
      return new Intent(activity, AddWorkoutActivity.class);
   }

   @Override
   public void onBackPressed() {
      if (searchView.isSearchOpen()) {
         searchView.closeSearch();
      } else {
         super.onBackPressed();
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_main, menu);
      MenuItem item = menu.findItem(R.id.action_search);
      searchView.setMenuItem(item);
      return true;
   }

   @Override
   public void showAddWorkout(final Workout workout) {
      mFirebaseAnalytics.logEvent("addWorkoutDialog", null);
      DialogUtils.showWorkout(this, workout, new DialogUtils.CreatedWorkoutCallback() {
         @Override
         public void createdWorkout(PerformedWorkout performedWorkout) {
            presenter.persistWorkout(performedWorkout);
         }
      });
   }

   @Override
   public void showContent(boolean isVisible) {
      if (isVisible) {
         content.setVisibility(View.VISIBLE);
      } else {
         content.setVisibility(View.GONE);
      }
   }

   @Override
   public void showError(boolean isVisible) {
      if (isVisible) {
         error.setVisibility(View.VISIBLE);
      } else {
         error.setVisibility(View.GONE);
      }
   }

   @Override
   public void showLoading(boolean isVisible) {
      if (isVisible) {
         loading.setVisibility(View.VISIBLE);
      } else {
         loading.setVisibility(View.GONE);
      }
   }

   @Override
   public void showSavedSuccessful() {
      finish();
   }

   @Override
   public void showSearchSuggestions(String[] workoutNameSuggestions) {
      searchView.setSuggestions(workoutNameSuggestions);
      searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent,
                                 View view,
                                 int position,
                                 long id) {
            String query = (String) parent.getItemAtPosition(position);
            handleSearchQuery(query);
         }
      });
   }

   @Override
   public void showWorkoutList(List<Workout> workouts) {
      recycler.setLayoutManager(new LinearLayoutManager(this));
      workoutAdapter.updateData(workouts);
      recycler.setAdapter(workoutAdapter);
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
              .inject(this);
      setContentView(R.layout.add_workout_activity);
      ButterKnife.bind(this);
      presenter.setView(this);
      initToolbar();
      initSearchView();
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
   }

   @Override
   protected void onPause() {
      super.onPause();
      presenter.onUnbind();
   }

   @Override
   protected void onResume() {
      super.onResume();
      presenter.onBind();
      mFirebaseAnalytics.logEvent("AddWorkout", null);
   }

   @OnClick (R.id.retry)
   void onClickRetry() {
      presenter.onBind();
      mFirebaseAnalytics.logEvent("retry_addWorkout", null);
   }

   private void handleSearchQuery(String query) {
      Bundle bundle = new Bundle();
      bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
      mFirebaseAnalytics.logEvent("search", bundle);
      Workout workout = workoutAdapter.getWorkoutByName(query);
      if (workout != null) {
         presenter.addWorkout(workout);
      } else {
         Toast.makeText(AddWorkoutActivity.this, getString(R.string.query_not_found, query),
               Toast.LENGTH_SHORT)
              .show();
      }
      searchView.closeSearch();
   }

   private void initSearchView() {
      searchView.setVoiceSearch(false);
      searchView.setSubmitOnClick(false);

      searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextChange(String newText) {
            return false;
         }

         @Override
         public boolean onQueryTextSubmit(String query) {

            handleSearchQuery(query);
            return true;
         }
      });
   }

   private void initToolbar() {
      setSupportActionBar(toolbar);
      if (getSupportActionBar() == null) {
         return;
      }
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
      getSupportActionBar().setTitle(R.string.add_new_girl);
   }
}
