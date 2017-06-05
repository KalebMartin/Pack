package com.none.pack;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ItemList extends ListActivity {


    private ItemsDataSource datasource;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        datasource = new ItemsDataSource(this);
        datasource.open();

        List<Item> values = datasource.getAllItems();

        ItemAdapter adapter = new ItemAdapter(this,values);
        setListAdapter(adapter);
        itemList=values;


    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    public void addTestItem(View view) {
        ItemAdapter adapter = (ItemAdapter) getListAdapter();
        Item item = new Item("Sword",5,0,1,"Basic sword");
        adapter.add(datasource.createItem(item));
        adapter.notifyDataSetChanged();

    }

    public void addNewItem(View view) {
        Intent createItem = new Intent(this,EditCreateItem.class);
        createItem.putExtra("createPurpose", "Create");
        startActivityForResult(createItem, 50);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item chosenItem = itemList.get(position);
        Intent itemViewIntent = new Intent(this,ItemView.class);
        chosenItem.addItemToIntent(itemViewIntent);
        startActivityForResult(itemViewIntent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ItemList","Attempting activity result");
        ItemAdapter adapter = (ItemAdapter) getListAdapter();
        /* Item Delete or Edit Request */
        if(requestCode==1) {
            if(resultCode==RESULT_OK) {
                Item item = Item.getItemFromIntent(data);
                if(item!=null) {
                    if(data.getStringExtra("operation").equals("delete")) {
                        datasource.open();
                        adapter.remove(datasource.deleteItem(item));
                        adapter.notifyDataSetChanged();
                        datasource.close();
                    }
                    else if(data.getStringExtra("operation").equals("edit")) {
                        datasource.open();
                        adapter.edit(datasource.updateItem(item));
                        adapter.notifyDataSetChanged();
                        datasource.close();
                        Intent itemViewIntent = new Intent(this,ItemView.class);
                        item.addItemToIntent(itemViewIntent);
                        startActivityForResult(itemViewIntent,1);
                    }
                }
            }
            else if(resultCode==RESULT_CANCELED) {

            }
        }
        /* Item Create Request */
        if(requestCode==50) {
            if(resultCode==RESULT_OK) {
                Log.d("ItemList","Create Item returned succesful");
                Log.d("ItemList",data.getStringExtra("name"));
                Log.d("ItemList",data.getIntExtra("pounds",0)+"");
                Log.d("ItemList",data.getIntExtra("decimal",0)+"");
                Log.d("ItemList",data.getIntExtra("quantity",0)+"");
                Log.d("ItemList",data.getStringExtra("description"));
                Item item = Item.getItemFromIntent(data);
                if(item!=null) {
                    datasource.open();
                    Log.d("ItemList", "Data retrieved?");
                    adapter.add(datasource.createItem(item));
                    adapter.notifyDataSetChanged();
                    datasource.close();
                }


            }
        }

    }

}
