package com.kupferwerk.presentation.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class OverviewWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   interface OnCollapsedListener {
      void onCollapsed(boolean isCollapsed);
      void onClicked();
   }

   class ItemViewHolder extends RecyclerView.ViewHolder {

       @OnClick(R.id.main_overview_item)
       public void onClickOverview(){
           onCollapsedListener.onClicked();
       }
      @BindView (R.id.overview_last_performed_date)
      TextView lastPerformed;
      @BindView (R.id.overview_workout_count)
      TextView workoutCount;
      @BindView (R.id.overview_workout_name)
      TextView workoutName;

      ItemViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      void bindData(final WorkoutViewModel workouts) {
         lastPerformed.setText(workouts.getLastPerformed());
         workoutCount.setText(String.valueOf(workouts.getPerformedCount()));
         workoutName.setText(workouts.getWorkout()
                                     .getName());
      }
   }

   private static final int COLLAPSE_COUNT = 4;
   private boolean collapsed = true;
   private List<WorkoutViewModel> data;
   private OnCollapsedListener onCollapsedListener;

   OverviewWorkoutAdapter(OnCollapsedListener onCollapsedListener) {
      this.onCollapsedListener = onCollapsedListener;
      this.data = new ArrayList<>();
   }

   @Override
   public int getItemCount() {
      if (collapsed && data.size() > COLLAPSE_COUNT) {
         return COLLAPSE_COUNT;
      } else {
         return data.size();
      }
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                int position) {
      ((ItemViewHolder) holder).bindData(data.get(position));
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
      return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                                              .inflate(R.layout.main_overview_item, parent, false));
   }

   void updateCollapsed() {
      collapsed = !collapsed;
      notifyDataSetChanged();
      onCollapsedListener.onCollapsed(collapsed);
   }

   void updateData(List<WorkoutViewModel> workoutViewModels) {
      data.clear();
      data.addAll(workoutViewModels);
      notifyDataSetChanged();
   }
}
