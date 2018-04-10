package com.kupferwerk.presentation.addworkout;

import com.kupferwerk.domain.model.PerformedWorkout;
import com.kupferwerk.domain.model.Workout;
import com.kupferwerk.presentation.core.BasePresenter;
import com.kupferwerk.presentation.core.BaseView;

import java.util.List;

class AddWorkoutContract {

    interface Presenter extends BasePresenter<AddWorkoutContract.View> {
        void addWorkout(Workout workout);

        void persistWorkout(PerformedWorkout performedWorkout);
    }

    interface View extends BaseView {
        void showAddWorkout(Workout workoutName);

        void showSavedSuccessful();

        void showWorkoutList(List<Workout> workouts);

        void showSearchSuggestions(String[] workoutNameSuggestions);

    }
}
