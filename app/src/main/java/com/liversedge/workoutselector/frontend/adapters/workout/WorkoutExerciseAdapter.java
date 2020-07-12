package com.liversedge.workoutselector.frontend.adapters.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.frontend.activity.WorkoutActivity;
import com.liversedge.workoutselector.frontend.videos.YoutubeVideoFragment;
import com.liversedge.workoutselector.utils.AnimationHandler;
import com.liversedge.workoutselector.utils.ExerciseImageIds;
import com.liversedge.workoutselector.utils.ImageHelper;
import com.liversedge.workoutselector.utils.TimeFormatter;

import java.util.List;

public class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseAdapter.ViewHolder>{

    // Recycler view elements
    private List<IWorkoutExercise> exercises;
    private WorkoutActivity context;
    private View view;
    private ViewHolder viewHolder;

    // For changing video
    private ExerciseImageIds exerciseImageIds;
    private FragmentManager fragmentManager;

    // For animation
    private final static float ANIMATION_PROPORTION = 0.5f;
    private int triggerAnimationIndex = -1;

    // For timing purposes
    private int activeExerciseID = 0;
    private float currentTime = 0.0f;
    private int groupStartIndex, repetitions, repeatGroupFor = 1;
    private boolean isTimedWorkout;
    private int videoOpenIndex = -1;

    public interface WorkoutUpdates {
        public void finishWorkout();
        public void speakNewWord(String word);
    }
    private WorkoutUpdates parent;

    public WorkoutExerciseAdapter(WorkoutActivity context, FragmentManager fragmentManager,
                                  List<IWorkoutExercise> exerciseNames, WorkoutUpdates parent) {
        this.exercises = exerciseNames;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.exerciseImageIds = new ExerciseImageIds();
        this.parent = parent;

        // Setup the active exercise
        repeatGroupFor = ((DescriptionExerciseItem)exercises.get(0)).getRepetitions();
        groupStartIndex = 1;
        repetitions = 0;
        activeExerciseID ++;
        this.exercises.get(activeExerciseID).setActive(true);

        // Check if this is a timed workout
        isTimedWorkout = true;
        for(int i=0; i<exercises.size(); i++) {

            IWorkoutExercise e = exercises.get(i);

            // Contains an exercise that we cannot time
            if(e.getClass() == WorkoutExerciseItem.class) {
                if(!e.getExercise().isTimed) { isTimedWorkout = false; }
            }

        }

    }

    public boolean isTimedWorkout() {
        return isTimedWorkout;
    }

    public float getTimeRemaining() {
        return (exercises.get(activeExerciseID).getExercise().duration*1000) - currentTime;
    }

    /**
     * Called when a user clicks to move to the next exercise
     *
     * @return
     */
    public void completeExercise() {

        // Deactivate old exercise
        exercises.get(activeExerciseID).setActive(false);
        exercises.get(activeExerciseID).setTimeTaken(currentTime);

        // Reset time and change id
        activeExerciseID ++;
        if(exercises.get(activeExerciseID).getClass() == DescriptionExerciseItem.class
            || activeExerciseID == exercises.size()) {
            repetitions ++;
            if(repetitions == repeatGroupFor) {
                if(activeExerciseID == exercises.size()) {
                    // End workout
                    parent.finishWorkout();
                    return;
                } else {
                    // Move onto next exercise group
                    activeExerciseID ++;
                    groupStartIndex = activeExerciseID;
                    repetitions = 0;
                    repeatGroupFor = ((DescriptionExerciseItem)exercises.get(activeExerciseID)).getRepetitions();
                }
            } else {
                // Go back to the start of this group
                activeExerciseID = groupStartIndex;
            }
        }
        currentTime = 0.0f;

        // Activate new exercise and update views
        exercises.get(activeExerciseID).setActive(true);
        parent.speakNewWord(exercises.get(activeExerciseID).getExercise().name + " " + " for " +
                exercises.get(activeExerciseID).getExercise().duration + " seconds");
    }

    /**
     * Updates the timer of the current exercise
     *
     * @param dt
     */
    public void update(float dt) {
        // If it is timed
        if(isTimedWorkout) {
            currentTime += dt;
            if((exercises.get(activeExerciseID).getExercise().duration*1000) - currentTime < 0) {
                completeExercise();
            }
        }
    }

    public void setExercises(List<IWorkoutExercise> exercises) {
        this.exercises = exercises;

        // Setup the active exercise
        while(this.exercises.get(activeExerciseID).getClass() == DescriptionExerciseItem.class) { activeExerciseID ++; }
        this.exercises.get(activeExerciseID).setActive(true);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView exerciseNameTextView, timeTakenTextView,
                groupDescriptionTextView;
        private Button showVideoButton;
        private ConstraintLayout exerciseViewHolder, exerciseTimerView;
        private FrameLayout youtubeVideo;
        private View dividerLine;
        private ImageView exerciseImageView;

        public ViewHolder(View v){

            super(v);

            exerciseImageView = (ImageView) v.findViewById(R.id.exerciseImageLargeExerciseItem);
            dividerLine = (View) v.findViewById(R.id.dividerExerciseLargeItem);
            youtubeVideo = (FrameLayout) v.findViewById(R.id.exerciseItemVideo);
            showVideoButton = (Button) v.findViewById(R.id.showVideoButton);
            exerciseNameTextView = (TextView) v.findViewById(R.id.timeElapsedDescription);
            timeTakenTextView = (TextView) v.findViewById(R.id.exerciseDuration);
            groupDescriptionTextView = (TextView) v.findViewById(R.id.titleDescriptionText);
            exerciseViewHolder = (ConstraintLayout) v.findViewById(R.id.exerciseViewHolder);
            exerciseTimerView = (ConstraintLayout) v.findViewById(R.id.authorContainerView);
        }
    }

    @Override
    public WorkoutExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = LayoutInflater.from(context).inflate(R.layout.exercise_item_large, parent,false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        if(exercises.get(position).getClass() == DescriptionExerciseItem.class) {
            final String description = exercises.get(position).getDescription();

            // Hide the exercise view and the youtube video
            holder.exerciseViewHolder.setVisibility(View.GONE);
            holder.exerciseTimerView.setVisibility(View.GONE);
            holder.youtubeVideo.setVisibility(View.GONE);
            holder.dividerLine.setVisibility(View.GONE);

            // Set the description text
            holder.groupDescriptionTextView.setVisibility(View.VISIBLE);
            holder.groupDescriptionTextView.setText(description);

        } else {
            final Exercise exercise = exercises.get(position).getExercise();
            final boolean isVideoShown = exercises.get(position).isVideoShown();
            final boolean nextState = exercises.get(position).isNextState();
            final boolean isActive = exercises.get(position).isActive();
            final float exerciseTakenTime = exercises.get(position).getTimeTaken();

            // Show the exercise view
            holder.exerciseViewHolder.setVisibility(View.VISIBLE);
            holder.exerciseTimerView.setVisibility(View.VISIBLE);
            holder.dividerLine.setVisibility(View.VISIBLE);

            // Update the image
            Integer image = exerciseImageIds.getImageByExerciseName(exercise.name);

            RoundedBitmapDrawable exerciseIconDrawable = ImageHelper.getRoundedImage(image, 100, context);
            holder.exerciseImageView.setImageDrawable(exerciseIconDrawable);

            // Hide the description view
            holder.groupDescriptionTextView.setVisibility(View.GONE);

            // Highlight the active exercise
//            if(isActive && isTimedWorkout) {
//                // Highlight card and update time
//                holder.exerciseViewHolder.setBackground(context.getResources().getDrawable(R.drawable.highlight_card));
//            } else {
//                // Set time taken part to placeholder
//                holder.exerciseViewHolder.setBackground(context.getResources().getDrawable(R.drawable.card));
//            }

            String name = exercise.name;
            Integer duration = exercise.duration;
            String equipment = exercise.equipment.equals("none") ? "No equipment needed" : exercise.equipment;

            if(duration == -1) {
                holder.timeTakenTextView.setText("-");
            } else if (!exercise.isTimed) {
                holder.timeTakenTextView.setText(String.valueOf(duration) + (exercise.hasEnding ? exercise.ending : ""));
            } else {
                holder.timeTakenTextView.setText(TimeFormatter.convertMilliSecondsToString(duration*1000));
            }

            holder.exerciseNameTextView.setText(name);

            if(isVideoShown && nextState) {

                holder.youtubeVideo.setVisibility(View.VISIBLE);
                holder.showVideoButton.setEnabled(true);
                holder.showVideoButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            }

            if (!isVideoShown && !nextState) {

                holder.youtubeVideo.setVisibility(View.GONE);

                if(videoOpenIndex != -1) {
                    holder.showVideoButton.setEnabled(false);
                    holder.showVideoButton.setBackgroundColor(context.getResources().getColor(R.color.textColorMid));
                    holder.showVideoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_arrow_24px, 0, 0, 0);
                } else {
                    holder.showVideoButton.setEnabled(true);
                    holder.showVideoButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.showVideoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_arrow_24px, 0, 0, 0);
                }

            }

            if (!isVideoShown && nextState) {

                // Update state
                exercises.get(position).setVideoShown(true);

                // Create the video
                // Create Youtube Fragment instance by passing a Youtube Video ID

                final String videoID = exerciseImageIds.getVideoByExerciseName(exercise.name);

                // Generate a new id
                int newContainerId = View.generateViewId(); // Generate unique container id
                holder.youtubeVideo.setId(newContainerId); // Set container id

                // Change the fragment
                YoutubeVideoFragment youtubeFragment =
                        YoutubeVideoFragment.newInstance(videoID);
                fragmentManager.beginTransaction()
                        .replace(newContainerId, youtubeFragment).commit();

                // Make it appear
                AnimationHandler.slide_down(holder.youtubeVideo, ANIMATION_PROPORTION);

                // Enabled video button
                holder.showVideoButton.setEnabled(true);
                holder.showVideoButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                // Change video button icon
                holder.showVideoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop_24px, 0, 0, 0);
            }

            holder.showVideoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!exercises.get(position).isVideoShown()) {

                        // Update state
                        exercises.get(position).setNextState(true);
                        videoOpenIndex = position;
                        notifyDataSetChanged();

                    } else {

                        // Update state
                        exercises.get(position).setNextState(false);
                        exercises.get(position).setVideoShown(false);

                        // Delete old fragment
                        int containerId = holder.youtubeVideo.getId();
                        YoutubeVideoFragment oldFragment = (YoutubeVideoFragment) fragmentManager.findFragmentById(containerId);
                        if (oldFragment != null) {
                            fragmentManager.beginTransaction().remove(oldFragment).commit();
                        }

                        // Slide it up
                        AnimationHandler.slide_up(holder.youtubeVideo, ANIMATION_PROPORTION);

                        // Enabled video button
                        holder.showVideoButton.setEnabled(true);
                        holder.showVideoButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                        // Change video button icon
                        holder.showVideoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_arrow_24px, 0, 0, 0);

                        // Update state
                        videoOpenIndex = -1;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private String convertSecondsToString(int seconds) {
        int minutes = (int) Math.floor(seconds / 60);
        seconds = seconds - (minutes * 60);

        String minutesAsString = String.valueOf(minutes);
        if(minutes < 10) { minutesAsString = "0" + minutesAsString;}

        String secondsAsString = String.valueOf(seconds);
        if(seconds < 10) { secondsAsString = "0" + secondsAsString;}

        return minutesAsString + ":" + secondsAsString;
    }

    @Override
    public int getItemCount(){
        return exercises.size();
    }
}
