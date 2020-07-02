package com.liversedge.workoutselector.utils;

public class Constants {

    // Debugging
    public static boolean DEBUGGING = true;
    public static String DEBUG_TAG = "HML";
    // For debugging to test out a new data set
    public static boolean FORCE_RELOAD = false;

    // Firebase fields
    public static String TITLE_FIELD = "Title";
    public static String AUTHOR_FIELD = "Author";
    public static String EQUIPMENT_TAGS_FIELD = "Tags_Equipment";
    public static String AREAS_TAGS_FIELD = "Tags_Areas";
    public static String AUTHOR_URL_FIELD = "Author_URL";

    public static String EXERCISES_NAME_FIELD = "name";
    public static String EXERCISE_EQUIPMENT_FIELD = "equipment";
    public static String EXERCISE_GROUP_MAJOR_FIELD = "group_major";
    public static String EXERCISE_GROUP_MINOR_FIELD = "group_minor";
    public static String EXERCISES_WEIGHT_FIELD = "weight";
    public static String EXERCISES_ID_FIELD = "id";
    public static String EXERCISES_DURATION_FIELD = "duration";
    public static String EXERCISES_IS_TIMED = "is_timed";

    public static String GROUP_DESCRIPTION_FIELD = "description";
    public static String GROUP_DURATION_FIELD = "description";
    public static String GROUP_REPETITION_FIELD = "repetitions";

    public static String EQUIPMENT_FIELD = "Equipment";
    public static String TARGET_FIELD = "Target";
    public static String REPETITIONS_FIELD = "Repetitions";

    // Meta firebase fields
    public static String META_TARGET_FIELD = "groups";
    public static String META_EQUIPMENT_FIELD = "equipment";

    // Keys for passing keys between activities
    public static final String INTENT_TIME_TAKEN = "net.liversedge.cameratutorial.TIME_TAKEN";
    public static final String INTENT_EXERCISE_ID = "net.liversedge.cameratutorial.EXERCISE_ID";
    public static final String INTENT_WORKOUT_ID = "net.liversedge.cameratutorial.WORKOUT_ID";
}
