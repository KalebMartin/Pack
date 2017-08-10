package com.none.pack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.none.pack.ItemSortOptionsDialogFragment;
import com.none.pack.itemModels.Item;
import com.none.pack.itemModels.ItemAdapter;
import com.none.pack.itemModels.ItemsDataSource;
import com.none.pack.R;
import com.none.pack.itemModels.SortParameter;

import java.util.List;

public class ItemList extends AppCompatActivity implements ItemSortOptionsDialogFragment.SortListener {


    private ItemsDataSource datasource;
    private List<Item> itemList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        listView = (ListView) findViewById(R.id.Item_List);

        datasource = new ItemsDataSource(this);
        datasource.open();
        List<Item> values = datasource.getAllItems();
        itemList=values;
        ItemAdapter adapter = new ItemAdapter(this,values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item chosenItem = itemList.get(position);
                Intent itemViewIntent = new Intent(view.getContext(),ItemView.class);
                chosenItem.addItemToIntent(itemViewIntent);
                startActivityForResult(itemViewIntent,1);
            }
        });
        Log.d("ItemList","Adapter listener set");




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_list_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_items:
                ItemSortOptionsDialogFragment dialog = new ItemSortOptionsDialogFragment();
                dialog.show(getFragmentManager(),"SortOptionsFragmentDialog");
        }
        return true;
    }

    public void addNewItem(View view) {
        Intent createItem = new Intent(this,EditCreateItem.class);
        createItem.putExtra("createPurpose", "Create");
        startActivityForResult(createItem, 50);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ItemList","Attempting activity result");
        ItemAdapter adapter = (ItemAdapter) listView.getAdapter();
        /* Item Delete or Edit Request */
        if(requestCode==1) {
            if(resultCode==RESULT_OK) {
                Item item = new Item(data);
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

        /* Item Create Request */
        if(requestCode==50) {
            if(resultCode==RESULT_OK) {
                Log.d("ItemList","Create Item returned succesful");
                Log.d("ItemList",data.getStringExtra("name"));
                Log.d("ItemList",data.getIntExtra("pounds",0)+"");
                Log.d("ItemList",data.getIntExtra("decimal",0)+"");
                Log.d("ItemList",data.getIntExtra("quantity",0)+"");
                Log.d("ItemList",data.getStringExtra("description"));
                Item item = new Item(data);
                datasource.open();
                Log.d("ItemList", "Data retrieved?");
                adapter.add(datasource.createItem(item));
                adapter.notifyDataSetChanged();
                datasource.close();


            }
        }

    }

    @Override
    public void sort(SortParameter[] sortParams) {
        ItemAdapter adapter = (ItemAdapter) listView.getAdapter();
        adapter.sortList(sortParams);
        adapter.notifyDataSetChanged();
    }

}
