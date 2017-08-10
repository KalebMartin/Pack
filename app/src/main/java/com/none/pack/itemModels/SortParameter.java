package com.none.pack.itemModels;

/**
 * Created by Kaleb on 7/27/2017.
 */

public enum SortParameter {
    TYPE_ASCENDING("Type"), TYPE_DESCENDING("Type-Reverse"), NAME_ASCENDING("Name"),
    NAME_DESCENDING("Name-Reverse"), WEIGHT_ASCENDING("Lightest"), WEIGHT_DESCENDING("Heaviest"),
    NEWEST("Newest"), OLDEST("Oldest");

    private String displayString;

    private SortParameter(String displayString) {
        this.displayString=displayString;
    }

    @Override
    public String toString() {
        return displayString;
    }
}