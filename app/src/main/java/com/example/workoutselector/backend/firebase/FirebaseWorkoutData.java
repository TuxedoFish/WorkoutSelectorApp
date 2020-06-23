package com.example.workoutselector.backend.firebase;

import androidx.annotation.NonNull;

import com.example.workoutselector.frontend.adapters.Exercise;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.workoutselector.utils.Constants.AUTHOR_FIELD;
import static com.example.workoutselector.utils.Constants.EQUIPMENT_FIELD;
import static com.example.workoutselector.utils.Constants.EXERCISES_DURATION_FIELD;
import static com.example.workoutselector.utils.Constants.EXERCISES_IS_TIMED;
import static com.example.workoutselector.utils.Constants.EXERCISES_NAME_FIELD;
import static com.example.workoutselector.utils.Constants.META_EQUIPMENT_FIELD;
import static com.example.workoutselector.utils.Constants.META_TARGET_FIELD;
import static com.example.workoutselector.utils.Constants.REPETITIONS_FIELD;
import static com.example.workoutselector.utils.Constants.TARGET_FIELD;
import static com.example.workoutselector.utils.Constants.TITLE_FIELD;

public class FirebaseWorkoutData {

    public static void loadWorkouts(final FirebaseWorkoutLoader callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("workouts").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();

                        ArrayList<WorkoutDAO> workouts = new ArrayList<>();

                        for(int i=0; i<docs.size(); i++) {
                            DocumentSnapshot doc = docs.get(i);

                            String title = doc.getString(TITLE_FIELD);
                            String author = doc.getString(AUTHOR_FIELD);
                            String repetitions = doc.getString(REPETITIONS_FIELD);

                            List<Boolean> exercises_is_timed  = (List<Boolean>) doc.get(EXERCISES_IS_TIMED);
                            List<String> exercises_names = (List<String>) doc.get(EXERCISES_NAME_FIELD);
                            List<String> exercises_durations = (List<String>) doc.get(EXERCISES_DURATION_FIELD);

                            ArrayList<Exercise> exercises = new ArrayList<>();

                            for(int j=0; i<exercises_names.size(); i++) {
                                exercises.add(
                                        new Exercise(
                                                exercises_names.get(i),
                                                exercises_is_timed.get(i),
                                                exercises_durations.get(i)
                                        )
                                );
                            }

                            List<String> equipment = (List<String>) doc.get(EQUIPMENT_FIELD);
                            List<String> target = (List<String>) doc.get(TARGET_FIELD);

                            workouts.add(new WorkoutDAO(title, author, exercises, equipment, target, repetitions));
                        }

                        callback.onWorkoutsLoadedSuccess(workouts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onWorkoutsLoadedFailure();
                    }
                });
    }

    public static void loadTargetAreas(final FirebaseWorkoutLoader callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("metadata").document("tags").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> targets = (List<String>) documentSnapshot.get(META_TARGET_FIELD);
                        callback.onTargetsLoadedSuccess(targets);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onTargetsLoadedFailure();
                    }
                });
    }

    public static void loadEquipment(final FirebaseWorkoutLoader callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("metadata").document("tags").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> targets = (List<String>) documentSnapshot.get(META_EQUIPMENT_FIELD);
                        callback.onEquipmentLoadedSuccess(targets);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onEquipmentLoadedFailure();
                    }
                });
    }
}
