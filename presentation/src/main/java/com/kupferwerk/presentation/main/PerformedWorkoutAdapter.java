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

class PerformedWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   interface OnItemClickedListener {
      void onItemClicked(WorkoutViewModel workoutViewModels);
   }

   class ItemViewHolder extends RecyclerView.ViewHolder {

      private final OnItemClickedListener onItemClickedListener;
      @BindView (R.id.list_item)
      TextView listItem;

      ItemViewHolder(View itemView, OnItemClickedListener onItemClickedListener) {
         super(itemView);
         ButterKnife.bind(this, itemView);
         this.onItemClickedListener = onItemClickedListener;
      }

      void bindData(final WorkoutViewModel workouts) {
         listItem.setText(workouts.getWorkout().getName());
         listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClickedListener.onItemClicked(workouts);
            }
         });
      }
   }

   private List<WorkoutViewModel> data;
   private OnItemClickedListener onItemClickedListener;

   PerformedWorkoutAdapter(OnItemClickedListener onItemClickedListener) {
      this.onItemClickedListener = onItemClickedListener;
      this.data = new ArrayList<>();
   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      ((ItemViewHolder) holder).bindData(data.get(position));
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.main_performed_list_item, parent, false),
                                onItemClickedListener);
   }

   void updateData(List<WorkoutViewModel> workoutViewModels) {
      data.clear();
      data.addAll(workoutViewModels);
      notifyDataSetChanged();
   }
}
