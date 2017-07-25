package com.none.pack;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for the creation of the SQLite database
 * Created by Kaleb on 5/23/2017.
 */

public class ItemDatabaseHelper extends SQLiteOpenHelper {
    private static String TABLE_NAME = "items";
    private static final String KEY_TYPE = "type";
    private static final String COLUMN_ID = "item_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POUNDS = "pounds";
    private static final String KEY_DECIMAL = "decimal";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_EXTRA = "extra";

    private static String DATABASE_NAME = "items.db";

    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    KEY_TYPE + " integer, " +
                    COLUMN_ID+ " integer PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " text, " +
                    KEY_POUNDS + " integer, " +
                    KEY_DECIMAL + " integer, " +
                    KEY_QUANTITY + " integer, " +
                    KEY_DESCRIPTION + " text, " +
                    KEY_EXTRA + " text);";


    /**
     * Default Constructor, will only call superclass
     * @param context Context
     */
    public ItemDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Constuctor for creating a databse using the bag's name as the database title
     * @param context
     * @param bagName Name of the database
     */
    public ItemDatabaseHelper(Context context, String bagName) {
        super(context, bagName,null,1);

    }

    /**
     * Overriden on Create Method - Will create the database using the creation string
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    /**
     * Overriden on Upgrade Method
     * @param db
     * @param oldversion
     * @param newversion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }

    /**
     *
     * @return The Key description for the type column
     */
    public static String getKeyType() { return KEY_TYPE; }

    /**
     *
     * @return The Table's Name
     */
    public static String getTableName() {
        return TABLE_NAME;
    }

    /**
     *
     * @return The Key description for the ID column
     */
    public static String getColumnId() {
        return COLUMN_ID;
    }

    /**
     *
     * @return The Key description for the Name coulmn
     */
    public static String getKeyName() {
        return KEY_NAME;
    }

    /**
     *
     * @return The Key descprtion for the Weight's Pounds value
     */
    public static String getKeyPounds() {
        return KEY_POUNDS;
    }

    /**
     *
     * @return The Key description for the Weight's decimal value
     */
    public static String getKeyDecimal() {
        return KEY_DECIMAL;
    }

    /**
     *
     * @return The Key description for the quantity column
     */
    public static String getKeyQuantity() {
        return KEY_QUANTITY;
    }

    /**
     *
     * @return The Key description for the description column
     */
    public static String getKeyDescription() {
        return KEY_DESCRIPTION;
    }

    /**
     *
     * @return The Key description for the extra column
     */
    public static String getKeyExtra() { return KEY_EXTRA; }

    /**
     *
     * @return The Name of the database as obtained from the given bagname
     */
    public static String getDBName() {
        return DATABASE_NAME;
    }
}
