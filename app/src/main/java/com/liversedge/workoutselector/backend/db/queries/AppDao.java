package com.liversedge.workoutselector.backend.db.queries;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.entities.GroupTable;
import com.liversedge.workoutselector.backend.db.entities.SettingTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;

import java.util.List;

@Dao
public interface AppDao {

    /* Getters */
    @Query("SELECT * FROM settings")
    List<SettingTable> getSettings();

    @Query("SELECT * FROM groups")
    List<GroupTable> getGroups();

    @Query("SELECT * FROM settings WHERE name=:name")
    List<SettingTable> getSettingByName(String name);

    @Query("SELECT * FROM workouts")
    List<WorkoutTable> getAllWorkouts();

    @Query("SELECT * FROM exercise_groups WHERE workout_id = :id ")
    List<ExerciseGroupTable> getExerciseGroupsByID(int id);

    @Query("SELECT * FROM exercises WHERE group_id = :group_id ORDER BY id ")
    List<Exercise> getExercisesByGroupID(int group_id);

    @Query("SELECT * FROM exercises WHERE id = :id")
    List<Exercise> getExerciseByID(int id);

    @Query("SELECT * FROM exercise_groups WHERE id = :id")
    List<ExerciseGroupTable> getExerciseGroupsByGroupID(int id);

    @Query("SELECT * FROM exercises WHERE workoutID = :id ORDER BY group_id, id")
    List<Exercise> getExercisesByWorkoutID(int id);

    @Query("SELECT * FROM workouts WHERE id = :id")
    List<WorkoutTable> getWorkoutByID(int id);

    /* Updates */
    @Query("UPDATE settings SET activated = :activated WHERE id = :id")
    int updateSetting(int id, boolean activated);

    /* Inserters */

    @Insert
    Long insertWorkout(WorkoutTable workout);

    @Insert
    Long insertExerciseGroup(ExerciseGroupTable workout);

    @Insert
    void insertAllExercises(List<Exercise> exercises);

    @Insert
    void insertAllGroups(List<GroupTable> groups);

    @Insert
    void insertSettings(SettingTable setting);

    @Insert
    void insertAllSettings(List<SettingTable> settings);

    /* Deleters */
    @Query("DELETE FROM settings")
    void deleteSettings();

    @Query("DELETE FROM groups")
    void deleteGroups();

    @Query("DELETE FROM workouts")
    void deleteWorkouts();

    @Query("DELETE FROM exercise_groups")
    void deleteExerciseGroups();

    @Query("DELETE FROM exercises")
    void deleteExercises();

    @Query("DELETE FROM settings where id=:id")
    void deleteSettingWithID(int id);
}
