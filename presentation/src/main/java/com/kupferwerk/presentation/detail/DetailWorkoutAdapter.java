package com.kupferwerk.presentation.detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kupferwerk.presentation.R;
import com.kupferwerk.presentation.main.model.Performed;
import com.kupferwerk.presentation.main.model.WorkoutViewModel;
import com.kupferwerk.presentation.util.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

class DetailWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   class HeaderViewHolder extends RecyclerView.ViewHolder {

      @BindView (R.id.best_performed)
      TextView bestPerformed;
      @BindView (R.id.workout_name)
      TextView workoutName;

      HeaderViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      void bind(WorkoutViewModel viewModel) {
         workoutName.setText(viewModel.getWorkout()
                                      .getName());
         bestPerformed.setText(
               TimeUtils.bestPerformed(itemView.getContext(), viewModel.getBestPerformedTime()));
      }
   }

   class ItemViewHolder extends RecyclerView.ViewHolder {

      @BindView (R.id.workout_diff)
      TextView workoutDiff;
      @BindView (R.id.workout_duration)
      TextView workoutDuration;
      @BindView (R.id.workout_performed)
      TextView workoutPerformed;

      ItemViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      void bind(Performed performed) {
         workoutPerformed.setText(TimeUtils.performedDate(performed.getPerformedAt()));
         workoutDuration.setText(
               TimeUtils.performedDuration(itemView.getContext(), performed.getDuration()));
         if (getAdapterPosition() != viewModel.getPerformedList()
                                              .size() - 1) {
            long durationDif = performed.getDurationDiff();
            workoutDiff.setText(TimeUtils.performedDuration(itemView.getContext(), durationDif));
            if (durationDif > 0) {
               workoutDiff.setTextColor(
                     ContextCompat.getColor(itemView.getContext(), R.color.monza));
            } else if (durationDif < 0) {
               workoutDiff.setTextColor(
                     ContextCompat.getColor(itemView.getContext(), R.color.bilbao));
            } else {
               workoutDiff.setTextColor(
                     ContextCompat.getColor(itemView.getContext(), R.color.black_85));
            }
         } else {
            workoutDiff.setText("");
         }
      }
   }

   private static final int ITEM_LAYOUT = R.layout.detail_performed_item;

   private WorkoutViewModel viewModel;

   DetailWorkoutAdapter() {
   }

   @Override
   public int getItemCount() {
      return viewModel.getPerformedList()
                      .size();
   }

   @Override
   public int getItemViewType(int position) {
      return ITEM_LAYOUT;
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                int position) {
      ((ItemViewHolder) holder).bind(viewModel.getPerformedList()
                                              .get(position));
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
      return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                                              .inflate(ITEM_LAYOUT, parent, false));
   }

   void updateData(WorkoutViewModel viewModel) {
      this.viewModel = viewModel;
      notifyDataSetChanged();
   }
}
