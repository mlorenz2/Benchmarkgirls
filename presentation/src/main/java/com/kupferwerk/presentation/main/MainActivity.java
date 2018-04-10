package com.kupferwerk.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kupferwerk.presentation.BaseActivity;
import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.core.Injector;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;
import com.kupferwerk.presentation.navigation.Router;
import com.kupferwerk.presentation.widget.BGWidgetProvider;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

   @BindView (R.id.overview_collapse_icon)
   ImageView collapseIcon;
   @BindView (R.id.content)
   View content;
   @BindView (R.id.error)
   View error;
   @BindView (R.id.fab)
   FloatingActionButton fab;
   @BindView (R.id.loading)
   View loading;
   @BindView (R.id.main_overview_recycler)
   RecyclerView overviewRecycler;
   @BindView (R.id.main_workout_recycler)
   RecyclerView performedWorkoutsRecycler;
   @Inject
   MainPresenter presenter;
   @Inject
   Router router;
   private FirebaseAnalytics mFirebaseAnalytics;
   private OverviewWorkoutAdapter overviewWorkoutAdapter;
   private PerformedWorkoutAdapter performedWorkoutAdapter;

   @Override
   public void showAddWorkout() {
      router.navigateToAddWorkout(this);
   }

   @Override
   public void showContent(boolean isVisible) {
      if (isVisible) {
         content.setVisibility(View.VISIBLE);
         fab.setVisibility(View.VISIBLE);
      } else {
         content.setVisibility(View.GONE);
         fab.setVisibility(View.GONE);
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
   public void showPerformedWorkouts(List<WorkoutViewModel> workoutViewModels) {
      performedWorkoutAdapter.updateData(workoutViewModels);
      overviewWorkoutAdapter.updateData(workoutViewModels);
      sendBroadcast(new Intent(BGWidgetProvider.ACTION_DATA_UPDATED));
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
              .inject(this);
      setContentView(R.layout.main_activity);
      ButterKnife.bind(this);
      presenter.setView(this);
      initRecycler();
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
      mFirebaseAnalytics.logEvent("Dashboard", null);
   }

   @OnClick (R.id.fab)
   void onClickAddWorkout() {
      presenter.addWorkout();
   }

   @OnClick (R.id.retry)
   void onClickRetry() {
      presenter.onBind();
      mFirebaseAnalytics.logEvent("retry_dashboard", null);
   }

   @OnClick (R.id.card_view)
   void onCollapse() {
      if (overviewWorkoutAdapter != null) {
         overviewWorkoutAdapter.updateCollapsed();
      }
   }

   private void initOverviewRecycler() {
      overviewRecycler.setLayoutManager(new LinearLayoutManager(this));
      overviewWorkoutAdapter =
            new OverviewWorkoutAdapter(new OverviewWorkoutAdapter.OnCollapsedListener() {
               @Override
               public void onClicked() {
                  overviewWorkoutAdapter.updateCollapsed();
               }

               @Override
               public void onCollapsed(boolean isCollapsed) {
                  if (isCollapsed) {
                     collapseIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,
                           R.drawable.ic_arrow_drop_down_black_24dp));
                     mFirebaseAnalytics.logEvent("overview_collapsed", null);
                  } else {
                     collapseIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,
                           R.drawable.ic_arrow_drop_up_black_24dp));
                     mFirebaseAnalytics.logEvent("overview_expanded", null);
                  }
               }
            });
      overviewRecycler.setAdapter(overviewWorkoutAdapter);
   }

   private void initPerformedRecycler() {
      performedWorkoutsRecycler.setLayoutManager(new LinearLayoutManager(this));
      performedWorkoutAdapter =
            new PerformedWorkoutAdapter(new PerformedWorkoutAdapter.OnItemClickedListener() {

               @Override
               public void onItemClicked(WorkoutViewModel workoutViewModels) {
                  router.navigateToWorkoutDetail(MainActivity.this, workoutViewModels.getWorkout()
                                                                                     .getName());
               }
            });
      performedWorkoutsRecycler.setAdapter(performedWorkoutAdapter);
   }

   private void initRecycler() {
      initPerformedRecycler();
      initOverviewRecycler();
   }
}
