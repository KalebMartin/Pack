package com.none.pack;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemView extends AppCompatActivity {

    private String databaseName;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Intent intent = getIntent();

        Log.d("ItemView", "Created");

        item = Item.getItemFromIntent(intent);

        setTitle(item.getName());
        TextView weight = (TextView) findViewById(R.id.weight);
        TextView totalWeight = (TextView) findViewById(R.id.total_weight);
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView description = (TextView) findViewById(R.id.description);
        weight.setText(item.getWeight().displayWeight());
        totalWeight.setText(item.getWeightTotal().displayWeight());
        quantity.setText(""+item.getQuantity());
        description.setText(item.getDescription());
    }

    public void editItem(View view) {
        Log.d("ItemView", "Attempting Edit Item");
        Intent editIntent = new Intent(this,EditCreateItem.class);
        item.addItemToIntent(editIntent);
        editIntent.putExtra("createPurpose","Edit");
        startActivityForResult(editIntent,1);

    }

    public void deleteItem(View view) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if(resultCode==RESULT_OK) {
                Item editedItem = Item.getItemFromIntent(data);
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
}
