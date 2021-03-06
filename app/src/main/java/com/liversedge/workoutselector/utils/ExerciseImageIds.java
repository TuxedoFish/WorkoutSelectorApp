package com.liversedge.workoutselector.utils;

import android.graphics.drawable.Drawable;

import com.liversedge.workoutselector.R;

import java.util.HashMap;
import java.util.Map;

public class ExerciseImageIds {

    Map<String, Integer> nameToImageID = new HashMap<>();
    Map<String, String> nameToVideoID = new HashMap<>();
    Map<String, Integer> nameToAuthorID = new HashMap<>();
    int defaultImageID = R.drawable.ic_info_24px;
    int defaultAuthorID = R.drawable.ic_face_24px;
    String defaultVideoID = "";

    public ExerciseImageIds() {
        /**
         * IMAGES
         */
        // Exercise
        nameToImageID.put("leg raise", R.drawable.leg_raises);
        nameToImageID.put("squat", R.drawable.squats);
        nameToImageID.put("bicep curl", R.drawable.bicep_curl);
        nameToImageID.put("jack knives", R.drawable.jack_knives);
        nameToImageID.put("mountain climber", R.drawable.mountain_climber);
        nameToImageID.put("up & down plank", R.drawable.plank);
        nameToImageID.put("plank", R.drawable.plank);
        nameToImageID.put("push up", R.drawable.push_up);
        nameToImageID.put("push", R.drawable.push_up);
        nameToImageID.put("press up", R.drawable.push_up);
        nameToImageID.put("tricep dip", R.drawable.tricep_dips);
        nameToImageID.put("dip", R.drawable.tricep_dips);
        nameToImageID.put("sit up", R.drawable.situp);
        nameToImageID.put("lunge", R.drawable.lunge);
        nameToImageID.put("burpee", R.drawable.burpee);
        nameToImageID.put("chest press", R.drawable.chestpress);
        nameToImageID.put("air squat", R.drawable.airsquat);
        nameToImageID.put("ab wheel roll out", R.drawable.abwheelrollout);
        nameToImageID.put("alternating half v sit up", R.drawable.alternatinghalfvsitup);
        nameToImageID.put("alternating heel touch", R.drawable.alternatingheeltouches);
        nameToImageID.put("barbell lunge", R.drawable.barbelllunge);
        nameToImageID.put("barbell squat", R.drawable.barbellsquat);
        nameToImageID.put("barbell thruster", R.drawable.barbellthruster);
        nameToImageID.put("box jump", R.drawable.boxjump);
        nameToImageID.put("barbell deadlift", R.drawable.deadlift);
        nameToImageID.put("push press", R.drawable.pushpress);
        nameToImageID.put("pull up", R.drawable.pullup);
        nameToImageID.put("russian twist", R.drawable.russiantwist);
        nameToImageID.put("rest", R.drawable.rest);
        nameToImageID.put("butterfly sit up", R.drawable.butterflysitup);
        nameToImageID.put("chin up", R.drawable.chinup);
        nameToImageID.put("cossack squat with bicep curl", R.drawable.cossacksquat);
        nameToImageID.put("cossack squat", R.drawable.cossacksquat);
        nameToImageID.put("devil press", R.drawable.devilpress);
        nameToImageID.put("devils press", R.drawable.devilpress);
        nameToImageID.put("double under", R.drawable.doubleunder);
        nameToImageID.put("dumbell alternative lunge", R.drawable.dumbelllunge);
        nameToImageID.put("dumbell bench press", R.drawable.dumbellbenchpress);
        nameToImageID.put("dumbell clean", R.drawable.clean);
        nameToImageID.put("barbell clean", R.drawable.barbellclean);
        nameToImageID.put("dumbell cluster", R.drawable.clean);
        nameToImageID.put("dumbell deadlift", R.drawable.deadlift);
        nameToImageID.put("dumbell devil press", R.drawable.devilpress);
        nameToImageID.put("dumbell glute bridge", R.drawable.dumbellglutebridge);
        nameToImageID.put("glute bridge", R.drawable.dumbellglutebridge);
        nameToImageID.put("dumbell goblet squat", R.drawable.dumbellgobletsquat);
        nameToImageID.put("dumbell lunge", R.drawable.dumbelllunge);
        nameToImageID.put("dumbell man maker", R.drawable.dumbellmanmaker);
        nameToImageID.put("dumbell push press", R.drawable.pushpress);
        nameToImageID.put("dumbell renegrade row", R.drawable.dumbellrenegaderow);
        nameToImageID.put("dumbell romanian deadlift", R.drawable.dumbellromaniandeadlift);
        nameToImageID.put("dumbell single arm snatch", R.drawable.dumbellsinglearmsnatch);
        nameToImageID.put("dumbell snatch", R.drawable.dumbellsinglearmsnatch);
        nameToImageID.put("dumbell squat clean", R.drawable.dumbellsquatclean);
        nameToImageID.put("dumbell squat", R.drawable.dumbellsquats);
        nameToImageID.put("dumbell thruster", R.drawable.dumbellthruster);
        nameToImageID.put("floor lying dumbell chest press", R.drawable.floorlyingdumbellchestpress);
        nameToImageID.put("barbell front squat", R.drawable.barbellfrontsquat);
        nameToImageID.put("barbell glute bridge", R.drawable.barbellglutebridge);
        nameToImageID.put("hand release push ups with shoulder tap", R.drawable.push_up);
        nameToImageID.put("hand releasing push up", R.drawable.push_up);
        nameToImageID.put("handstand push up (HSPU)", R.drawable.handstandpushup);
        nameToImageID.put("handstand push up", R.drawable.handstandpushup);
        nameToImageID.put("hang power clean", R.drawable.hangpowerclean);
        nameToImageID.put("hollw hold", R.drawable.hollowhold);
        nameToImageID.put("hollow hold", R.drawable.hollowhold);
        nameToImageID.put("inverted row", R.drawable.invertedrow);
        nameToImageID.put("jumping squat", R.drawable.jumpingsquat);
        nameToImageID.put("kettlebell goblet squat", R.drawable.kettlebellgobletsquat);
        nameToImageID.put("kettlebell swing", R.drawable.kettlebellswing);
        nameToImageID.put("plate ground to overhead", R.drawable.plategroundtooverhead);
        nameToImageID.put("power clean", R.drawable.clean);
        nameToImageID.put("clean", R.drawable.clean);
        nameToImageID.put("barbell push press", R.drawable.barbellpushpress);
        nameToImageID.put("reverse lunge", R.drawable.reverselunge);
        nameToImageID.put("single arm dumbell push press", R.drawable.singlearmdumbellpushpress);
        nameToImageID.put("single arm dumbell snatch", R.drawable.singlearmdumbellsnatch);
        nameToImageID.put("single skip", R.drawable.singleskip);
        nameToImageID.put("single-leg v sit up", R.drawable.singlelegvsitup);
        nameToImageID.put("step over box", R.drawable.stepoverbox);
        nameToImageID.put("stretch crunch", R.drawable.stretchcrunch);
        nameToImageID.put("shoulder press", R.drawable.strictshoulderpress);
        nameToImageID.put("sumo squat up right row", R.drawable.sumosquatuprightrow);
        nameToImageID.put("toes to bar", R.drawable.toestobar);
        nameToImageID.put("v-crunch", R.drawable.vcrunch);
        nameToImageID.put("wall walk", R.drawable.wallwalk);
        nameToImageID.put("barbell bench press", R.drawable.benchpress);
        // Cardio
        nameToImageID.put("run", R.drawable.run);
        nameToImageID.put("sprint", R.drawable.run);
        nameToImageID.put("row", R.drawable.row);
        nameToImageID.put("skip", R.drawable.doubleunder);
        // Descriptions
        nameToImageID.put("hidden", android.R.color.transparent);
        nameToImageID.put("image_1", R.drawable.ic_looks_one_24px);
        nameToImageID.put("image_2", R.drawable.ic_looks_two_24px);
        nameToImageID.put("image_3", R.drawable.ic_looks_3_24px);
        nameToImageID.put("image_4", R.drawable.ic_looks_4_24px);
        nameToImageID.put("image_5", R.drawable.ic_looks_5_24px);

        /**
         * VIDEOS
         */
        nameToVideoID.put("ab wheel roll out", "A3uK5TPzHq8"); // t=224
        nameToVideoID.put("air squat", "C_VtOYc6j5c");
        nameToVideoID.put("squat", "C_VtOYc6j5c");
        nameToVideoID.put("alternating half v sit up", "8WaDzRANv94"); // t=4
        nameToVideoID.put("alternating heel touches", "2zhFTw2epoc");
        nameToVideoID.put("alternating lunge jump", "6SFgE2_og_s");
        nameToVideoID.put("alternating dumbell snatch", "3mlhF3dptAo");
        nameToVideoID.put("alternating lung", "L8fvypPrzzs");
        nameToVideoID.put("barbell bench press", "SCVCLChPQFY");
        nameToVideoID.put("barbell clean", "EKRiW9Yt3Ps");
        nameToVideoID.put("barbell deadlift", "op9kVnSso6Q");
        nameToVideoID.put("barbell lunge", "ZPoMxOrIeO4");
        nameToVideoID.put("barbell push press", "iaBVSJm78ko");
        nameToVideoID.put("barbell squat", "ultWZbUMPL8");
        nameToVideoID.put("barbell thruster", "L219ltL15zk");
        nameToVideoID.put("box jump", "52r_Ul5k03g");
        nameToVideoID.put("box jump over", "-Tz4BF2ne2A");
        nameToVideoID.put("burpee", "TU8QYVW0gDU");
        nameToVideoID.put("burpee box jump", "GLktGkmcvWE");
        nameToVideoID.put("burpee over bar", "A6gQLuMMiA4"); // t=11
        nameToVideoID.put("burpee over the machine", "TomAxZVxZe0");
        nameToVideoID.put("burpee to plate", "NE1sENZASmY");
        nameToVideoID.put("burpee tuck jump", "26meQbtfE3g");
        nameToVideoID.put("butterfly sit up", "dagbwNWCMlY");
        nameToVideoID.put("chin up", "brhRXlOhsAM"); // t=146
        nameToVideoID.put("cossack squat with bicep curl", "YvxmS5BIPi8");
        nameToVideoID.put("devil press", "zlqEtAUds-I");
        nameToVideoID.put("devils press", "zlqEtAUds-I");
        nameToVideoID.put("dip", "JR0PUrVAFyA");
        nameToVideoID.put("double under", "82jNjDS19lg");
        nameToVideoID.put("dumbell alternative lunge", "PFwB_6NWP60");
        nameToVideoID.put("dumbell bench press", "rkvSI8A7MJs");
        nameToVideoID.put("dumbell clean", "3PnWQwabq4Y");
        nameToVideoID.put("dumbell clusters", "uiKyT6_7bho");
        nameToVideoID.put("dumbell deadlift", "ev5Bx5a_mss");
        nameToVideoID.put("dumbell devil press", "zlqEtAUds-I");
        nameToVideoID.put("dumbell glute bridge", "JpDDoINkrog");
        nameToVideoID.put("dumbell goblet squat", "a-dqF4NL2K4");
        nameToVideoID.put("dumbell lunge", "PFwB_6NWP60");
        nameToVideoID.put("dumbell man maker", "H7iF5rNSoC4");
        nameToVideoID.put("dumbell push press", "JqztIwOb4Hs");
        nameToVideoID.put("dumbell renegade row", "G1AcX8Y_byg");
        nameToVideoID.put("dumbell romanian deadlift", "UsOjCcxSJaI");
        nameToVideoID.put("dumbell single arm snatch", "mx8mr0xV8L0");
        nameToVideoID.put("dumbell squat clean", "SYxObzJ3gn0");
        nameToVideoID.put("dumbell squat", "rotBMUQHdZk");
        nameToVideoID.put("dumbell thruster", "VdPjfmRcQfw");
        nameToVideoID.put("floor lying dumbell chest press", "lNdi7VEf2Ew"); // t=18
        nameToVideoID.put("barbell front squat", "-iPN3LLg3Yo");
        nameToVideoID.put("barbell glute bridge", "achuJ0T3E7g");
        nameToVideoID.put("hand release push ups with shoulder tap", "zPrcw-WIdZs");
        nameToVideoID.put("hand releasing push ups", "zPrcw-WIdZs");
        nameToVideoID.put("handstand push up", "ic8cg0SQ8A4");
        nameToVideoID.put("hang power clean", "akDqb1TFPl0");
        nameToVideoID.put("hollw hold", "VqtPkI5pP3o");
        nameToVideoID.put("inverted row", "zbLAEQ3e9gw");
        nameToVideoID.put("kettlebell goblet squat", "5ZzkcZbFyJo");
        nameToVideoID.put("kettlebell swing", "tjSGgsLpSLY");
        nameToVideoID.put("lateral burpee over dumbell", "MRKWzuBkFZY");
        nameToVideoID.put("lunge", "L8fvypPrzzs");
        nameToVideoID.put("lunge while holding dumbell", "PFwB_6NWP60");
        nameToVideoID.put("maximum amount of burpee in the remaining time", "TU8QYVW0gDU");
        nameToVideoID.put("mountain climber", "SfoBQkyrhbs");
        nameToVideoID.put("oh reverse lunge to curtsey lunge (left leg)", "7HY7yH1XPgE");
        nameToVideoID.put("oh reverse lunge to curtsey lunge (right leg)", "7HY7yH1XPgE");
        nameToVideoID.put("plank", "l_pYnZj2FJg");
        nameToVideoID.put("plank hold", "l_pYnZj2FJg");
        nameToVideoID.put("plate ground to overhead", "eT0H7hvL2RI");
        nameToVideoID.put("power clean", "EKRiW9Yt3Ps");
        nameToVideoID.put("pull up", "gfRjCi4JSM8");
        nameToVideoID.put("push up", "9QTESdxFOsI");
        nameToVideoID.put("push press", "iaBVSJm78ko");
        nameToVideoID.put("reverse lunge", "dwl6udmeGS4");
        nameToVideoID.put("run", "NYjw5woEMIA");
        nameToVideoID.put("row", "HCo9_CXoJj0");
        nameToVideoID.put("russian twist", "vsEk9-OrROs");
        nameToVideoID.put("single arm dumbell push press", "wXbeU7eUhJ8");
        nameToVideoID.put("single arm dumbell snatch", "3mlhF3dptAo");
        nameToVideoID.put("single skip", "hCuXYrTOMxI");
        nameToVideoID.put("single-leg v sit up", "0EIDVDbzVNo");
        nameToVideoID.put("sit up", "dagbwNWCMlY");
        nameToVideoID.put("sprint", "NYjw5woEMIA");
        nameToVideoID.put("squat switch blade (jump twist)", "iHvwi51an20");
        nameToVideoID.put("step over box", "lUSmIRNqt9w");
        nameToVideoID.put("stretch crunch", "yHMKRFnvn2U");
        nameToVideoID.put("strict pull up", "gfRjCi4JSM8");
        nameToVideoID.put("shoulder press", "_aISMzimYEA");
        nameToVideoID.put("sumo squat up right row", "o6QniJ9FaGA");
        nameToVideoID.put("toes to bar", "pgBwmyI36aE");
        nameToVideoID.put("tricep dip", "XXvuYGCxpkk");
        nameToVideoID.put("v-crunch", "5UNy8HdV6jE"); // t=11
        nameToVideoID.put("wall walk", "JsGNOjxsu9A");

        /**
         * Fitness authors
         */
        nameToAuthorID.put("luca feser", R.drawable.lucaprofile);
        nameToAuthorID.put("brani keckes", R.drawable.brani);
        nameToAuthorID.put("harriot whelan", R.drawable.hariot);
    }

    public String getVideoByExerciseName(String name) {
        String w = name.toLowerCase();
        Integer n = w.length();

        // Try word and any other plural variants
        String resourceID = findPluralVideo(w);
        if(resourceID != null) { return resourceID; }

        String[] l = w.split(" ");

        // Try consecutive triple words
        for(int i=0; i<l.length-2; i++) {
            resourceID = findPluralVideo(l[i] + " " + l[i+1] + " " + l[i+2]);
            if(resourceID != null) { return resourceID; }
        }

        // Try consecutive double words
        for(int i=0; i<l.length-1; i++) {
            resourceID = findPluralVideo(l[i] + " " + l[i+1]);
            if(resourceID != null) { return resourceID; }
        }

        // Try individual words
        for(int i=0; i<l.length; i++) {
            resourceID = findPluralVideo(l[i]);
            if(resourceID != null) { return resourceID; }
        }

        return resourceID==null ? defaultVideoID : resourceID;
    }

    private String findPluralVideo(String w) {

        // Try the actual word
        Integer n = w.length();
        String resourceID = nameToVideoID.get(w);
        if(resourceID != null) { return resourceID; }

        // Try taking -s of last word i.e
        // push ups => push up
        if(w.endsWith("s")) {
            resourceID = nameToVideoID.get(w.substring(0, n-1));
            if(resourceID != null) {
                return resourceID;
            }
        }
        // Same with ending in -es i.e
        // crunches => crunch
        if(w.endsWith("es")) {
            resourceID = nameToVideoID.get(w.substring(0, n-2));
            if(resourceID != null) {
                return resourceID;
            }
        }

        return null;
    }

    public Integer getImageByExerciseName(String name) {
        String w = name.toLowerCase();
        Integer n = w.length();

        // Try word and any other plural variants
        Integer resourceID = findPluralImages(w);
        if(resourceID != null) { return resourceID; }

        String[] l = w.split(" ");

        // Try consecutive triple words
        for(int i=0; i<l.length-2; i++) {
            resourceID = findPluralImages(l[i] + " " + l[i+1]+ " " + l[i+2]);
            if(resourceID != null) { return resourceID; }
        }

        // Try consecutive double words
        for(int i=0; i<l.length-1; i++) {
            resourceID = findPluralImages(l[i] + " " + l[i+1]);
            if(resourceID != null) { return resourceID; }
        }

        // Try individual words
        for(int i=0; i<l.length; i++) {
            resourceID = findPluralImages(l[i]);
            if(resourceID != null) { return resourceID; }
        }

        return resourceID==null ? defaultImageID : resourceID;
    }

    private Integer findPluralImages(String w) {

        // Try the actual word
        Integer n = w.length();
        Integer resourceID = nameToImageID.get(w);
        if(resourceID != null) { return resourceID; }

        // Try taking -s of last word i.e
        // push ups => push up
        if(w.endsWith("s")) {
            resourceID = nameToImageID.get(w.substring(0, n-1));
            if(resourceID != null) {
                return resourceID;
            }
        }
        // Same with ending in -es i.e
        // crunches => crunch
        if(w.endsWith("es")) {
            resourceID = nameToImageID.get(w.substring(0, n-2));
            if(resourceID != null) {
                return resourceID;
            }
        }

        return null;
    }

    public Integer getAuthorImage(String author) {
        Integer resourceID = nameToAuthorID.get(author.toLowerCase());
        return resourceID != null ? resourceID : defaultAuthorID;
    }
}
