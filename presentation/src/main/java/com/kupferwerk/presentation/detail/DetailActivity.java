package com.kupferwerk.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.presentation.BaseActivity;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.core.Injector;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;
import com.kupferwerk.presentation.util.DialogUtils;
import com.kupferwerk.presentation.util.TimeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailContract.View {
   public static final String KEY_WORKOUT = "key.workout";
   @BindView (R.id.best_performed)
   TextView bestPerformed;
   @BindView (R.id.error)
   View error;
   @BindView (R.id.exercise_list)
   ListView listView;
   @BindView (R.id.loading)
   View loading;
   @BindView (R.id.card_view)
   View overviewCard;
   @Inject
   DetailPresenter presenter;
   @BindView (R.id.recycler)
   RecyclerView recyclerView;
   @BindView (R.id.toolbar)
   Toolbar toolbar;
   @BindView (R.id.workout_description)
   TextView workoutDescription;
   @BindView (R.id.workout_name)
   TextView workoutName;
   private FirebaseAnalytics mFirebaseAnalytics;

   public static Intent start(Context context,
                              String workoutName) {
      Intent intentToStart = new Intent(context, DetailActivity.class);
      intentToStart.putExtra(KEY_WORKOUT, workoutName);
      return intentToStart;
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.add_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.add) {
         presenter.addWorkout();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void showAddWorkout(final WorkoutViewModel workoutViewModel) {
      mFirebaseAnalytics.logEvent("add_workout", null);
      DialogUtils.showWorkout(this, workoutViewModel.getWorkout(),
            new DialogUtils.CreatedWorkoutCallback() {
               @Override
               public void createdWorkout(PerformedWorkout performedWorkout) {
                  mFirebaseAnalytics.logEvent("workout_added", null);
                  presenter.persistWorkout(performedWorkout);
               }
            });
   }

   @Override
   public void showContent(boolean isVisible) {
      if (isVisible) {
         overviewCard.setVisibility(View.VISIBLE);
         recyclerView.setVisibility(View.VISIBLE);
      } else {
         overviewCard.setVisibility(View.GONE);
         recyclerView.setVisibility(View.GONE);
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
   public void showPerformed(WorkoutViewModel workoutViewModels) {
      initRecycler(workoutViewModels);
      workoutName.setText(workoutViewModels.getWorkout()
                                           .getName());
      workoutDescription.setText(workoutViewModels.getWorkout()
                                                  .getDescription());
      bestPerformed.setText(
            TimeUtils.bestPerformed(this, workoutViewModels.getBestPerformedTime()));
      listView.setDividerHeight(0);
      listView.setAdapter(new ArrayAdapter<>(this, R.layout.detail_overview_item,
            workoutViewModels.getWorkout()
                             .getExercises()));
   }

   @Override
   public void showWorkoutAdded() {
      presenter.onBind();
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
              .inject(this);
      setContentView(R.layout.detail_activity);
      ButterKnife.bind(this);
      presenter.setView(this);
      presenter.init(getIntent().getExtras()
                                .getString(KEY_WORKOUT));

      initToolbar();
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
      mFirebaseAnalytics.logEvent("Detail", null);
   }

   @OnClick (R.id.retry)
   void onClickRetry() {
      presenter.onBind();
      mFirebaseAnalytics.logEvent("retry_detail", null);
   }

   private void initRecycler(WorkoutViewModel workoutViewModel) {
      recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
      DetailWorkoutAdapter detailWorkoutAdapter = new DetailWorkoutAdapter();
      detailWorkoutAdapter.updateData(workoutViewModel);
      recyclerView.setAdapter(detailWorkoutAdapter);
   }

   private void initToolbar() {
      setSupportActionBar(toolbar);
      if (getSupportActionBar() == null) {
         return;
      }
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("");
   }
}
