package com.none.pack.itemModels;


import android.content.Intent;

import com.none.pack.R;

/**
 * Base Item Class
 * For handling generic/unspecified items
 *
 * @author Kaleb
 * @version 1.0
 * @since 1.0
 *
 */

public class Item {
    /**
    Type of item - used to distinguish what icon the item should have

    Type -  Icon
    1 - Generic
    2 - Pouch
    3 - Coins
    4 - Helmet
    5 - Shield
    6 - Body Armor
    7 - Melee
    8 - Ranged
    9 - Special
    10 - Vial
    11 - Food
    12 - Arrow
    13 - Quiver
    14 - Gem
    15 - Art Object

     All others should be detected as invalid - return either bug or
     */
    private int type;
    /**
     *     Unique ID given by database - should be set immediately after SQL insertion
      */
    private long id;
    /**
     * Item's Name -
     * Should be a non-null, non-blank string with a length of less than 50
     * Should be restricted by input to only alphanumeric characters
     */
    private String name;
    /**
     * Weight of the object
     * Is optional
     * {@link Weight}
     */
    private Weight weight;
    /**
     * The number of items
     * Should be a non-null int value above 0
     *
     */
    private int quantity;
    /**
     * Item's description - holds any details about the item that the user decides to input
     * Can be null or blank, but should not be over 5000 characters long
     */
    private String description;
    /**
     * The total weight of the objects found by multiplying the object's weight by the quantity
     * Is optional
     */
    private Weight weightTotal;

    /**
     */
    public Item(Intent intent) {
        getItemFromIntent(intent);
        type = 1;
        findWeightTotal();
    }

    /**
     * Standard constructor for new Item
     * @param inName
     * @param weightPound
     * @param weightDecimal
     * @param inQuantity
     * @param inDescription
     */
    public Item(String inName, int weightPound, int weightDecimal, int inQuantity, String inDescription) {
        name = inName;
        weight = new Weight(weightPound,weightDecimal);
        description = inDescription;
        quantity = inQuantity;
        findWeightTotal();
        type = 1;

    }

    /**
     * Method for validating the values of the item's fields
     *
     * @return boolean of validity of the item
     */
    public boolean validItem() {
        if(name.length()>50||name.equals("")) {
            return false;
        }
        if(!weight.valid()) {
            return false;
        }
        if(!weightTotal.valid()) {
            return false;
        }
        if(description.length()>2000) {
            return false;
        }
        if(quantity<1) {
            return false;
        }
        if(!validType()) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return Name of Item
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Weight of Item
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     *
     * @return Description of Item
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return Quantity of Item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @return The total weight of the item based on the weight and quantity
     */
    public Weight getWeightTotal() {
        return weightTotal;
    }

    /**
     *
     * @return Short String description of Item - Item's Name + Weight
     */
    @Override
    public String toString() {
        return getName()+" "+getWeightTotal().displayWeight();
    }

    /**
     *
     * @return unique ID of item
     */
    public long getId() { return id; }

    /**
     * Sets ID to unique ID obtained from database
     * @param inId unique ID of item
     */
    public void setId(long inId) {
        id = inId;
    }

    /**
     * @return Type of item
     */
    public int getType() { return type; };

    /**
     * Sets Type to input parameter - for usage by subclasses
     * @param inType
     */
    public void setType(int inType) { type = inType; }

    /**
     * Adds all of the Item values to the given Intent
     * @param intent Intent that values should be added to
     */
    public void addItemToIntent(Intent intent) {
        intent.putExtra("id",getId());
        intent.putExtra("name",getName());
        intent.putExtra("pounds",getWeight().getPounds());
        intent.putExtra("decimal",getWeight().getDecimal());
        intent.putExtra("quantity",getQuantity());
        intent.putExtra("description",getDescription());
        intent.putExtra("type",getType());
    }

    /**
     * Obtains an Item from a given intent
     * @param intent Intent for Item to be gained from
     * @return New Item with given values
     */
    public void getItemFromIntent(Intent intent) {
        name = intent.getStringExtra("name");
        weight = new Weight(intent.getIntExtra("pounds",0),intent.getIntExtra("decimal",0));
        quantity = intent.getIntExtra("quantity",1);
        description=intent.getStringExtra("description");
        setId(intent.getLongExtra("id",0));
        type = intent.getIntExtra("type",1);
    }

    /**
     * Returns the int ID of the icon correlated with the item
     * @return Icon ID
     */
    public int getIcon() {
        return R.drawable.base_item;
    }

    /**
     * Will check that the type holds a valid value for the object that contains it - for Items
     * it should be 1
     * @return Whether type is valid
     */
    public boolean validType() {
        if(type==1) {
            return true;
        }
        return false;
    }

    /**
     * Will calculate and set the total weight of the item based upon the weight value and the
     * quantity
     */
    public void findWeightTotal() {
        if(quantity>1) {
            weightTotal=weight.multiplyWeight(quantity);
        }
        else {
            weightTotal=weight;
        }
    }


}
