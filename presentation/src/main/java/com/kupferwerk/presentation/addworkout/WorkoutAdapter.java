package com.kupferwerk.presentation.addworkout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.presentation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Workout> data = new ArrayList<>();
    private OnListItemClickedListener onListItemClickedListener;

    public WorkoutAdapter(
            OnListItemClickedListener onListItemClickedListener) {
        this.onListItemClickedListener = onListItemClickedListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 int position) {
        ((WorkoutViewHolder) holder).bindData(data.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        return new WorkoutViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_performed_list_item, parent,
                        false));
    }

    public void updateData(List<Workout> workoutList) {
        data.clear();
        data.addAll(workoutList);
        notifyDataSetChanged();
    }

    public Workout getWorkoutByName(String workoutName){
        Workout tmpWorkout = null;
        for (Workout workout : data){
            if (workout.getName().equals(workoutName)){
                tmpWorkout = workout;
                break;
            }
        }
        return tmpWorkout;
    }

    interface OnListItemClickedListener {
        void onListItemClicked(Workout workout);
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item)
        TextView listItem;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Workout workout) {
            listItem.setText(workout.getName());
        }

        @OnClick(R.id.list_item)
        void onClicked() {
            if (onListItemClickedListener != null) {
                onListItemClickedListener.onListItemClicked(data.get(getAdapterPosition()));
            }
        }
    }
}
