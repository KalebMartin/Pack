package com.none.pack;


import android.content.Intent;

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
    private long id;
    private String name;
    private Weight weight;
    private int quantity;
    private String description;
    private Weight weightTotal;

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
        if(quantity>1) {
            weightTotal=weight.multiplyWeight(quantity);
        }
        else {
            weightTotal=weight;
        }

    }

    /**
     * Method for validating the values of the item
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
    }

    /**
     * Obtains an Item from a given intent
     * @param intent Intent for Item to be gained from
     * @return New Item with given values
     */
    public static Item getItemFromIntent(Intent intent) {
        Item item = new Item(intent.getStringExtra("name"),
                intent.getIntExtra("pounds",0),
                intent.getIntExtra("decimal",0),
                intent.getIntExtra("quantity",1),
                intent.getStringExtra("description"));
        item.setId(intent.getLongExtra("id",0));
        if(item.getId()<0) {
            return null;
        }
        return item;
    }


}
