package com.example.workoutselector.utils;

import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

public class NachoTextViewHelper {

    public static void removeDuplicates(NachoTextView v, List<String> allowedValues) {
        // Get the current list
        List<String> oldChips = v.getChipValues();
        ArrayList<String> newChips = new ArrayList<>();

        for(int i=0; i<oldChips.size(); i++) {
            // Get the value
            String val = oldChips.get(i);

            // Check it hasnt already been added +
            // that it is allowed
            if(!newChips.contains(val)
                && allowedValues.contains(val)) {
                newChips.add(val);
            }
        }

        if(newChips.size() != oldChips.size()) {
            v.setText(newChips);
        }
    }

}
