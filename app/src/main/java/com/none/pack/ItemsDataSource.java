package com.none.pack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaleb on 5/25/2017.
 */

public class ItemsDataSource {
    private SQLiteDatabase database;
    private ItemDatabaseHelper helper;
    private String[] allColumns= {ItemDatabaseHelper.getColumnId(), ItemDatabaseHelper.getKeyName(),
            ItemDatabaseHelper.getKeyPounds(), ItemDatabaseHelper.getKeyDecimal(),
            ItemDatabaseHelper.getKeyQuantity(), ItemDatabaseHelper.getKeyDescription()};

    /**
     * Default Constructor - Calls Database Helper for creation
     * @param context
     */
    public ItemsDataSource(Context context) {
        helper = new ItemDatabaseHelper(context);
    }

    /**
     * Constructor for creating specific bag database - Calls database helper for creation
     * @param context
     * @param bagName Name of the bag- used to make new database name
     */
    public ItemsDataSource(Context context, String bagName) {
        helper = new ItemDatabaseHelper(context,bagName);
    }

    /**
     * Opens database for interaction
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    /**
     * Closes database
     */
    public void close() {
        helper.close();
    }

    /**
     * Inserts given item into the database and assigns it an ID value
     * @param item Item to be inserted into database
     * @return New Item with the input values as well as the unique ID retrieved from the database
     */
    public Item createItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDatabaseHelper.getKeyName(),item.getName());
        values.put(ItemDatabaseHelper.getKeyPounds(),item.getWeight().getPounds());
        values.put(ItemDatabaseHelper.getKeyDecimal(),item.getWeight().getDecimal());
        values.put(ItemDatabaseHelper.getKeyQuantity(),item.getQuantity());
        values.put(ItemDatabaseHelper.getKeyDescription(),item.getDescription());
        long insertID = database.insert(ItemDatabaseHelper.getTableName(),null, values);
        Cursor cursor = database.query(ItemDatabaseHelper.getTableName(),allColumns,
                ItemDatabaseHelper.getColumnId() + " = " + insertID, null, null, null, null);
        cursor.moveToFirst();
        Item newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    /**
     * Updates the Item listing in the database to the values given in the parameter Item
     * @param item Item with values to update & given ID
     * @return The same Item
     */
    public Item updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDatabaseHelper.getKeyName(),item.getName());
        values.put(ItemDatabaseHelper.getKeyPounds(),item.getWeight().getPounds());
        values.put(ItemDatabaseHelper.getKeyDecimal(),item.getWeight().getDecimal());
        values.put(ItemDatabaseHelper.getKeyQuantity(),item.getQuantity());
        values.put(ItemDatabaseHelper.getKeyDescription(),item.getDescription());
        database.update(ItemDatabaseHelper.getTableName(),values,
                ItemDatabaseHelper.getColumnId()+"="+item.getId(),null);
        return item;
    }

    /**
     * Deletes the Item listed in the database based on the ID of the parameter item
     * @param item
     * @return
     */
    public Item deleteItem(Item item) {
        try {
            long id = item.getId();
            database.delete(ItemDatabaseHelper.getTableName(),
                    ItemDatabaseHelper.getColumnId() + " = " + id, null);
            return item;
        }
        catch(SQLException e) {
            Log.d("DELETE", "Exception caught");
        }
        return null;
    }

    /**
     * Returns a list of all of the items in the database
     * @return
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<Item>();
        Cursor cursor = database.query(ItemDatabaseHelper.getTableName(),allColumns,
                null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Item item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    private Item cursorToItem(Cursor cursor) {
        String name = cursor.getString(1);
        int pounds = cursor.getInt(2);
        int decimal = cursor.getInt(3);
        int quantity = cursor.getInt(4);
        String description = cursor.getString(5);
        Item newItem = new Item(name,pounds,decimal,quantity,description);
        newItem.setId(cursor.getLong(0));
        return newItem;
    }
}
