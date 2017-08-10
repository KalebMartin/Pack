package com.none.pack.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.none.pack.itemModels.Item;
import com.none.pack.R;

public class ItemView extends AppCompatActivity {

    private String databaseName;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Intent intent = getIntent();

        Log.d("ItemView", "Created");
        Log.d("ItemView", "Item - "+intent.getStringExtra("name"));

        item = new Item(intent);

        Log.d("ItemView", "Item succesfully loaded");

        TextView nameTitle = (TextView) findViewById(R.id.title);
        nameTitle.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(),0,item.getIcon(),0);
        TextView weight = (TextView) findViewById(R.id.weight);
        TextView totalWeight = (TextView) findViewById(R.id.total_weight);
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView description = (TextView) findViewById(R.id.description);
        nameTitle.setText(item.getName());
        weight.setText(item.getWeight().displayWeight());
        totalWeight.setText(item.getWeightTotal().displayWeight());
        quantity.setText(""+item.getQuantity());
        description.setText(item.getDescription());

        Log.d("ItemView", "Item values set into view fields");
    }

    /**
     * Should start an EditCreateItem activity in the edit mode
     */
    public void editItem() {
        Log.d("ItemView", "Attempting Edit Item");
        Intent editIntent = new Intent(this,EditCreateItem.class);
        item.addItemToIntent(editIntent);
        editIntent.putExtra("createPurpose","Edit");
        startActivityForResult(editIntent,1);

    }

    /**
     * Should Attempt to delete the item on user confirmation, finishing the activity
     */
    public void deleteItem() {
        Log.d("ItemView","Attempting Delete Item");
        new AlertDialog.Builder(this)
        .setTitle("Confirm Item Deletion")
        .setMessage("Do you really want to delete this item?")
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface i,int whichButton) {
                Intent deleteIntent = new Intent();
                item.addItemToIntent(deleteIntent);
                deleteIntent.putExtra("operation","delete");
                setResult(RESULT_OK,deleteIntent);
                ItemView.this.finish();
            }

        })
        .setNegativeButton("No",null).show();



    }

    /**
     * Will handle the results of a started activity - ItemView will currently only create an
     * EditCreateItem activity in the edit mode
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if(resultCode==RESULT_OK) {
                Item editedItem = new Item(data);
                if (editedItem != null) {
                    Intent editComplete = new Intent();
                    editedItem.addItemToIntent(editComplete);
                    editComplete.putExtra("operation", "edit");
                    setResult(RESULT_OK, editComplete);
                    finish();
                }
            }
            else {

            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_view_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_edit:
                editItem();
                break;
            case R.id.action_delete:
                deleteItem();
                break;
        }
        return true;
    }
}
