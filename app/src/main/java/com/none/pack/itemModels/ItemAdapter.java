package com.none.pack.itemModels;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.none.pack.R;
import com.none.pack.itemModels.Item;
import com.none.pack.itemModels.ItemComparator;
import com.none.pack.itemModels.SortParameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kaleb on 5/28/2017.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Item> datasource;

    public ItemAdapter(Context inContext, List<Item> items) {
        context = inContext;
        datasource = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *
     * @return Number of items in list
     */
    @Override
    public int getCount() {
        return datasource.size();
    }

    /**
     *
     * @param position Position of the item in the list
     * @return Item from list
     */
    @Override
    public Object getItem(int position) {
        return datasource.get(position);
    }

    /**
     *
     * @param position Position of the item in the list
     * @return ID of the item at given position
     */
    @Override
    public long getItemId(int position) {
        return datasource.get(position).getId();
    }

    /**
     *
     * @param position Position of item to be put in view
     * @param convertView unused
     * @param parent Parent ViewGroup
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.list_item_format,parent,false);
        TextView nameText = (TextView) rowView.findViewById(R.id.item_name);
        TextView weightText = (TextView) rowView.findViewById(R.id.item_weight);
        ImageView itemIcon = (ImageView) rowView.findViewById(R.id.item_icon);

        Item item = (Item) getItem(position);
        nameText.setText(item.getName());
        weightText.setText(item.getWeightTotal().displayWeight());
        itemIcon.setImageResource(item.getIcon());
        return rowView;
    }

    /**
     * Adds parameter item to the list data
     * @param item Item to be added
     */
    public void add(Item item) {
        datasource.add(item);
    }

    /**
     * Updates the given item by reassigning the item with a new value
     * Iterates through the list to find the unique ID match, and replaces on equal value found
     * If no match is found, the method will do nothing
     * @param item
     */
    public void edit(Item item) {
        Log.d("ItemAdapter","Attempting to replace item with ID "+item.getId()+" with new" +
                "edited item "+item.toString());
        for(int i = 0; i<datasource.size();i++) {
            if(datasource.get(i).getId()==item.getId()) {
                Log.d("ItemAdapter", "Item match found, replacing "+datasource.get(i).toString());
                datasource.set(i,item);
                break;
            }
        }
    }

    /**
     * Removes given item by removing item from the list with the same ID
     * Iterates through the list unit the given ID matches with the datasources ID, then removes
     * the item at that position
     * If no match is found, the method will do nothing
     * @param item
     */
    public void remove(Item item) {
        for(int i = 0; i<datasource.size();i++) {
            if(datasource.get(i).getId()==item.getId()) {
                datasource.remove(i);
                break;
            }
        }
    }

    public void sortList(SortParameter[] sortParams) {
        Collections.sort(datasource,new ItemComparator(sortParams));
    }
}
