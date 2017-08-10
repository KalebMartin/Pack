package com.none.pack.itemModels;


import android.content.Intent;

import com.none.pack.R;

/**
 * Item Class
 * Holds and handles all item information
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
     2 - Helmet
     3 - Shield
     4 - Body Armor
     5 - Melee Weapon
     6 - Ranged Weapon
     7 - Arrows
     8 - Quiver
     9 - Food
     10 - Vial
     11 - Pouch
     12 - Coins
     13 - Gem
     14 - Art Object
     15 - Special

     All others should be detected as invalid - item should then be reset as generic
     */
    private int type;
    /**
     *
     */
    private final int MAX_TYPE = 15;
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
        findWeightTotal();
    }

    /**
     * Standard constructor for new Item
     * @param inName name of new item
     * @param weightPound int of the pound value of the constructor
     * @param weightDecimal int of the decimal value of the constructor
     * @param inQuantity int quantity of the new item
     * @param inDescription the description of the new item
     */
    public Item( int inType, String inName, int weightPound, int weightDecimal, int inQuantity, String inDescription) {
        name = inName;
        weight = new Weight(weightPound,weightDecimal);
        description = inDescription;
        quantity = inQuantity;
        findWeightTotal();
        type = inType;

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
        return validType();
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
    public int getType() { return type; }

    /**
     * Sets Type to input parameter - for usage by subclasses
     * @param inType The int type of the item - for changing icon/sorting
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

        switch (getType()){
            case 1:
                return R.drawable.ic_genericicon;
            case 2:
                return R.drawable.ic_armoricon;
            case 3:
                return R.drawable.ic_armoricon2;
            case 4:
                return R.drawable.ic_armoricon3;
            case 5:
                return R.drawable.ic_weaponicon;
            case 6:
                return R.drawable.ic_weaponicon2;
            case 7:
                return R.drawable.ic_arrowicon;
            case 8:
                return R.drawable.ic_quivericon;
            case 9:
                return R.drawable.ic_foodicon;
            case 10:
                return R.drawable.ic_potionicon;
            case 11:
                return R.drawable.ic_pouchicon;
            case 12:
                return R.drawable.ic_coinicon;
            case 13:
                return R.drawable.ic_gemicon;
            case 14:
                return R.drawable.ic_articon;
            case 15:
                return R.drawable.ic_specialicon;
        }
        setType(1);
        return R.drawable.ic_genericicon;
    }

    /**
     * Will check that the type holds a valid value for the object that contains it - for Items
     * it should be 1
     * @return Whether type is valid
     */
    public boolean validType() {
        if(type>MAX_TYPE||type<0) {
            return false;
        }
        return true;
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
