package com.liversedge.workoutselector.backend.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.liversedge.workoutselector.utils.Constants.AREAS_TAGS_FIELD;
import static com.liversedge.workoutselector.utils.Constants.AUTHOR_FIELD;
import static com.liversedge.workoutselector.utils.Constants.AUTHOR_URL_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EQUIPMENT_TAGS_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISES_DURATION_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISES_IS_TIMED;
import static com.liversedge.workoutselector.utils.Constants.EXERCISES_NAME_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISES_WEIGHT_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISE_EQUIPMENT_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISE_GROUP_MAJOR_FIELD;
import static com.liversedge.workoutselector.utils.Constants.EXERCISE_GROUP_MINOR_FIELD;
import static com.liversedge.workoutselector.utils.Constants.GROUP_DESCRIPTION_FIELD;
import static com.liversedge.workoutselector.utils.Constants.GROUP_DURATION_FIELD;
import static com.liversedge.workoutselector.utils.Constants.GROUP_REPETITION_FIELD;
import static com.liversedge.workoutselector.utils.Constants.META_EQUIPMENT_FIELD;
import static com.liversedge.workoutselector.utils.Constants.META_TARGET_FIELD;
import static com.liversedge.workoutselector.utils.Constants.TITLE_FIELD;

public class FirebaseWorkoutData {

    public static void loadWorkouts(final Context context) {
        final FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
        final AppDatabase localDB = Migrations.getInstance(context).db;

        firebaseDB.collection("workouts").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();

                        ArrayList<WorkoutDAO> workouts = new ArrayList<>();

                        for(int i=0; i<docs.size(); i++) {
                            DocumentSnapshot doc = docs.get(i);

                            String title = doc.getString(TITLE_FIELD);
                            String author = doc.getString(AUTHOR_FIELD);
                            String authorURL = doc.getString(AUTHOR_URL_FIELD);
                            List<String> equipmentTags = (List<String>) doc.get(EQUIPMENT_TAGS_FIELD);
                            List<String> areaTags = (List<String>) doc.get(AREAS_TAGS_FIELD);

                            // Get a promise that will return the groups
                            Task<QuerySnapshot> groupsSnapshot =
                                firebaseDB.collection("workouts")
                                .document(doc.getId())
                                .collection("groups")
                                .get();

                            // Insert workout to database
                            // Get the id of the workout
                            Long id = localDB.appDao().insertWorkout(
                                    new WorkoutTable(
                                            title,
                                            author,
                                            authorURL,
                                            equipmentTags,
                                            areaTags
                                    )
                            );

                            // Handle the groups snapshot
                            handleGroupSnapshot(groupsSnapshot, title, id, localDB);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO: Handle failure
                    }
                });
    }

    private static void handleGroupSnapshot(Task<QuerySnapshot> snapshot, final String workoutTitle,
                                            final Long workoutID, final AppDatabase localDB) {

        snapshot.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // The groups have successfully loaded so get their data
                List<DocumentSnapshot> groups = queryDocumentSnapshots.getDocuments();

                for (int j = 0; j < groups.size(); j++) {
                    DocumentSnapshot g = groups.get(j);
                    String description = g.getString(GROUP_DESCRIPTION_FIELD);
                    String duration = g.getString(GROUP_DURATION_FIELD);
                    Double repetitions = g.getDouble(GROUP_REPETITION_FIELD);

                    Task<QuerySnapshot> promise = getExercisePromises(workoutTitle, String.valueOf(j + 1));

                    Long groupID = localDB.appDao().insertExerciseGroup(
                            new ExerciseGroupTable(
                                    workoutID.intValue(),
                                    description,
                                    duration,
                                    repetitions
                            )
                    );

                    handleGroupSnapshot(promise, workoutID, groupID, localDB);
                }
            }
        });
    }

    private static void handleGroupSnapshot(Task<QuerySnapshot> snapshot, final Long workoutID,
                                            final Long groupID, final AppDatabase localDB) {
        snapshot.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // The exercises have loaded so load them in
                List<DocumentSnapshot> loadedExercises = queryDocumentSnapshots.getDocuments();
                ArrayList<Exercise> newExercises = new ArrayList<>();

                for (int k = 0; k < loadedExercises.size(); k++) {
                    DocumentSnapshot e = loadedExercises.get(k);

                    String equipment = (String) e.get(EXERCISE_EQUIPMENT_FIELD);
                    String groupMajor = (String) e.get(EXERCISE_GROUP_MAJOR_FIELD);
                    String groupMinor = (String) e.get(EXERCISE_GROUP_MINOR_FIELD);
                    String name = (String) e.get(EXERCISES_NAME_FIELD);
                    String weight = (String) e.get(EXERCISES_WEIGHT_FIELD);
                    Boolean isTimed = (Boolean) e.get(EXERCISES_IS_TIMED);

                    Boolean hasEnding = false;
                    String ending = "N/A";
                    Integer duration = -1;
                    if(e.get(EXERCISES_DURATION_FIELD).getClass() == Long.class) {
                        // The data is an integer type
                        Long asLong = (Long) e.get(EXERCISES_DURATION_FIELD);
                        duration = asLong.intValue();
                    } else {
                        // The data is passed in is a string with "m" or "metres" at the end
                        hasEnding = true;

                        String asString = (String) e.get(EXERCISES_DURATION_FIELD);

                        // Ends in "m"
                        if(asString.endsWith("m")) {
                            duration = Integer.valueOf(
                                    asString.substring(0, asString.length() - 1)
                            );
                            ending = "m";
                        } else if (asString.endsWith("cal")) {
                            duration = Integer.valueOf(
                                    asString.substring(0, asString.length() - 3)
                            );
                            ending = "cal";
                        }
                    }

                    Exercise exercise = new Exercise(
                            workoutID.intValue(),
                            groupID.intValue(),
                            equipment,
                            groupMajor,
                            groupMinor,
                            name,
                            weight,
                            duration,
                            isTimed,
                            hasEnding,
                            ending
                    );

                    newExercises.add(exercise);
                }

                localDB.appDao().insertAllExercises(newExercises);
            }
        });
    }

    private static Task<QuerySnapshot> getExercisePromises(String documentID, String index) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection("workouts")
                .document(documentID)
                .collection("groups")
                .document(index)
                .collection("exercises")
                .get();

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
