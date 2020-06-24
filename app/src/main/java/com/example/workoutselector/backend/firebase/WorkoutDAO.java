package com.example.workoutselector.backend.firebase;

import com.example.workoutselector.backend.db.databases.AppDatabase;
import com.example.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.workoutselector.utils.Constants.GROUP_DESCRIPTION_FIELD;
import static com.example.workoutselector.utils.Constants.GROUP_DURATION_FIELD;

public class WorkoutDAO {

    private String title, author, author_url;
    private ArrayList<ExerciseGroupDAO> exerciseGroups = new ArrayList<>();
    private List<String> equipmentTags, areaTags;

    public WorkoutDAO(final String title, final String author, final String author_url,
                      List<String> equipmentTags, List<String> areaTags,
                      int workout_id, AppDatabase db) {
        this.title = title;
        this.author = author;
        this.author_url = author_url;
        this.equipmentTags = equipmentTags;
        this.areaTags = areaTags;

        // Grab the exercise groups
        if(db != null) {
            List<ExerciseGroupTable> exerciseGroupTables = db.appDao().getExerciseGroupsByID(workout_id);
            for (int i = 0; i < exerciseGroupTables.size(); i++) {
                ExerciseGroupTable group = exerciseGroupTables.get(i);

                exerciseGroups.add(
                        new ExerciseGroupDAO(
                                group.description,
                                group.duration,
                                group.repetitions,
                                group.id,
                                workout_id,
                                db
                        )
                );
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public ArrayList<ExerciseGroupDAO> getExerciseGroups() {
        return exerciseGroups;
    }

    public void setExerciseGroups(ArrayList<ExerciseGroupDAO> exerciseGroups) {
        this.exerciseGroups = exerciseGroups;
    }
}
