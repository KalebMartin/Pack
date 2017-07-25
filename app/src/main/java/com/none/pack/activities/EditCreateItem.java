package com.none.pack.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.none.pack.itemModels.Item;
import com.none.pack.R;

import java.lang.Integer;

public class EditCreateItem extends AppCompatActivity {
    private Item item;
    private EditText name;
    private EditText weightPounds;
    private EditText weightDecimal;
    private EditText quantity;
    private EditText description;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_item);

        Log.d("CreateItem","Created");

        Intent intent = getIntent();

        name= (EditText) findViewById(R.id.ItemName);
        weightPounds = (EditText) findViewById(R.id.ItemPounds);
        weightDecimal = (EditText) findViewById(R.id.ItemDecimal);
        quantity = (EditText) findViewById(R.id.ItemQuantity);
        description = (EditText) findViewById(R.id.ItemDescription);


        mode = intent.getStringExtra("createPurpose");
        if(mode.equals("Create")) {

            Log.d("CreateItem", "Create Item mode detected");
        }
        else if(mode.equals("Edit")) {
            Log.d("CreateItem", "Edit Item mode detected");

            Log.d("CreateItem","Changed Item text");
            item = new Item(intent);
            Log.d("CreateItem","Got Item " + item.toString()+" with ID "+item.getId());
            name.setText(item.getName());
            weightPounds.setText(String.format("%d",item.getWeight().getPounds()));
            weightDecimal.setText(item.getWeight().displayDecimal());
            quantity.setText(String.format("%d",item.getQuantity()));
            description.setText(item.getDescription());
            Log.d("CreateItem","EditTexts changed to item values");
        }
    }

    public void attemptFinish() {
        Log.d("CreateItem","AttemptFinish Launched, attempting try block");
        Item newItem;
        try {
            Log.d("CreateItem","Try block");
            if(weightDecimal.getText().toString().equals("00")) {
                weightDecimal.setText("0");
            }
            if(weightDecimal.getText().toString().length()==1) {
                weightDecimal.setText(weightDecimal.getText().toString()+"0");
            }
            newItem = new Item(name.getText().toString(),
                    Integer.parseInt(weightPounds.getText().toString()),
                    Integer.parseInt(weightDecimal.getText().toString()),
                    Integer.parseInt(quantity.getText().toString()),
                    description.getText().toString());
            if(newItem.validItem()) {
                Log.d("CreateItem", "Valid Item Detected, attempting to finish item "+newItem.toString());
                Intent finishInfo = new Intent();
                if(mode.equals("Create")) {
                    newItem.setId(0);
                }
                else {
                    newItem.setId(item.getId());
                }
                newItem.addItemToIntent(finishInfo);
                setResult(RESULT_OK,finishInfo);
                this.finish();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Invalid Item")
                        .setMessage("The Item entered is invalid").show();
            }
        }
        catch (NumberFormatException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Weight/Quantity Error")
                    .setMessage("The Quantity must be above 1, and the weight must be" +
                            "properly formatted to 2 decimal places").show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_edit_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_finish:
                attemptFinish();
                break;
        }
        return true;
    }
}
