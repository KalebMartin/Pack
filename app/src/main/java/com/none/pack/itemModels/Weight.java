package com.none.pack.itemModels;

/**
 * Weight class
 * Maintains weight in two integers to avoid floating point rounding errors
 * Decimal out to 2 places
 * None-negative values
 *
 * @author Kaleb
 * @version 1.0
 * @since 1.0
 */

public class Weight {
    int pounds;
    int decimal;

    /**
     * Default weight constructor
     * values will default to 0
     */
    public Weight() {
        pounds=0;
        decimal=0;
    }

    /**
     * Defined weight constructor
     * Will default to 0 if invalid input is found
     * @param p input pounds
     * @param d input decimal value
     */
    public Weight(int p, int d) {
        if(verifyDecimal(d)) {
            decimal = d;
        } else {
            decimal=0;
        }
        if(verifyPounds(p)) {
            pounds=p;
        } else {
            pounds=0;
        }

    }

    /**
     * Getter method for string representation of the weight values with the decimal point
     * Should add leading 0 for decimal values from 0 to 9
     *
     * @return The string representation of the weight
     */
    public String displayWeight() {
        if(decimal>=0&&decimal<10) {
            return(pounds+".0"+decimal);
        }
        return(pounds+"."+decimal);
    }

    /**
     * Verifies that the decimal value is 0<=n<100
     *
     * @param testDecimal the deciaml value to be checked
     *
     * @return whether the given decimal is valid.
     */
    public boolean verifyDecimal(int testDecimal) {
        return !(testDecimal < 0 || testDecimal > 99);

    }

    /**
     * Verifies that the pound value is >0
     *
     * @param testPounds the pound value to be checked
     * @return whether the given pound is valid
     */
    public boolean verifyPounds(int testPounds) {
        return testPounds >= 0;
    }

    /**
     * Getter class for pound value
     * @return pounds
     */
    public int getPounds() {
        return pounds;
    }

    /**
     * Getter class for decimal value
     * @return decimal
     */
    public int getDecimal() {
        return decimal;
    }

    /**
     * Class for addition of a weight value to this object
     * Should overflow decimals adding above 100 to add a pound
     * @param addedWeight - the weight class to be added to this one
     */
    public void addWeight(Weight addedWeight) {
        pounds = pounds+addedWeight.getPounds();
        decimal = decimal+addedWeight.getDecimal();
        if(decimal>100) {
            decimal-=100;
            pounds+=1;
        }

    }

    /**
     * Class for the subtraction of a weight value from this object
     * Should underflow decimal value if below 0 to remove a pound
     * @param subtractedWeight
     */
    public void subtractWeight(Weight subtractedWeight) {
        pounds = pounds-subtractedWeight.getPounds();
        decimal = decimal-subtractedWeight.getDecimal();
        if(decimal<0) {
            decimal=100-decimal;
            pounds-=1;
        }
        if(pounds<0) {
            pounds=0;
            decimal=0;
        }

    }

    /**
     * Class for the multiplication of an integer to this weight value
     * Will return the current weight if an invalid number is given
     *
     * @param num The number for the weight to be multiplied by
     * @return The multiplied weight
     */
    public Weight multiplyWeight(int num) {
        if(num<1) {
            return this;
        }
        int newPounds = pounds*num;
        int newDecimal = decimal*num;
        while(newDecimal>=100) {
            newDecimal-=100;
            newPounds+=1;
        }
        return new Weight(newPounds,newDecimal);
    }

    /**
     * method for comparison of weights - will return true if this weight is heavier
     * than the other weight, or false if it is equal or lighter than the other weight
     * @param otherWeight the weight to be compared against
     * @return true if heavier, false if equal or lighter weight
     */
    public boolean isHeavierThan(Weight otherWeight) {
        if(pounds>otherWeight.getPounds()) {
            return true;
        }
        else if(pounds==otherWeight.getPounds()) {
            return decimal > otherWeight.getDecimal();
        }
        else {
            return false;
        }
    }

    /**
     * Method for properly formatted string display of a decimal value
     * @return Formatted string
     */
    public String displayDecimal() {
        if(decimal<10) {
            return "0"+decimal;
        }
        return decimal+"";
    }

    /**
     * Method for ensuring the weight has valid values
     * @return Boolean value of validity
     */
    public boolean valid() {
        return pounds >= 0 &&decimal >= 0 && decimal < 100;
    }
}