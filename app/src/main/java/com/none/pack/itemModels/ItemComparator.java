package com.none.pack.itemModels;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Kaleb on 7/27/2017.
 */

public class ItemComparator implements Comparator<Item> {
    private SortParameter[] parameters;

    public ItemComparator(SortParameter[] parameters) {
        this.parameters=parameters;
    }

    public int compare(Item i1, Item i2) {
        int comparison;
        for(SortParameter parameter : parameters) {
            switch(parameter) {
                case TYPE_ASCENDING:
                    comparison = i1.getType()-i2.getType();
                    if(comparison != 0) return comparison;
                    break;
                case TYPE_DESCENDING:
                    comparison = i2.getType()-i1.getType();
                    if(comparison != 0) return comparison;
                    break;
                case NAME_ASCENDING:
                    Log.d("ItemComparator","NameAscending attempted");
                    comparison = i1.getName().compareTo(i2.getName());
                    if(comparison != 0) return comparison;
                    break;
                case NAME_DESCENDING:
                    comparison = i2.getName().compareTo(i1.getName());
                    if(comparison != 0) return comparison;
                    break;
                case WEIGHT_ASCENDING:
                    comparison = i1.getWeightTotal().compareTo(i2.getWeightTotal());
                    if(comparison != 0) return comparison;
                    break;
                case WEIGHT_DESCENDING:
                    comparison = i2.getWeightTotal().compareTo(i1.getWeightTotal());
                    if(comparison != 0) return comparison;
                    break;
                //TODO: Fix neweset and oldest case to allow for longs that would overflow int
                case NEWEST:
                    comparison = (int) (i1.getId()-i2.getId());
                    if(comparison != 0) return comparison;
                    break;
                case OLDEST:
                    comparison = (int) (i2.getId()-i1.getId());
                    if(comparison !=0) return comparison;
                    break;
            }
        }
        return 0;
    }
}
