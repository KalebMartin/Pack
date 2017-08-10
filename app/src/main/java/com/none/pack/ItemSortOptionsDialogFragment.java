package com.none.pack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.none.pack.itemModels.SortParameter;


/**
 * Created by Kaleb on 7/30/2017.
 */

public class ItemSortOptionsDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    Spinner primarySpinner;
    Spinner secondarySpinner;
    SortParameter[] selectedParams;
    SortListener mListener;

    public interface SortListener {
        public void sort(SortParameter[] params);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedParams = new SortParameter[2];
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.dialog_sort_options,null);
        builder.setView(view);
        builder.setTitle(R.string.sort_dialog_title);
        primarySpinner = (Spinner) view.findViewById(R.id.spinnerPrimary);
        secondarySpinner = (Spinner) view.findViewById(R.id.spinnerSecondary);
        primarySpinner.setAdapter(new ArrayAdapter<SortParameter>(view.getContext(),
                android.R.layout.simple_spinner_item,SortParameter.values()));
        secondarySpinner.setAdapter(new ArrayAdapter<SortParameter>(view.getContext(),
                android.R.layout.simple_spinner_item,SortParameter.values()));
        primarySpinner.setOnItemSelectedListener(this);
        secondarySpinner.setOnItemSelectedListener(this);

        builder.setPositiveButton(R.string.sort_positive, new DialogInterface.OnClickListener() {
            //Ensure Params are correct and call base class method, then dismiss
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.sort(selectedParams);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            //Cancel - no changes, just dismiss dialog
            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Log.d("ItemSortDialog","Item Selected In Spinner");
        switch(parent.getId()) {
            case R.id.spinnerPrimary:
                Log.d("ItemSortDialog","Spinenr Primary set");
                selectedParams[0]=(SortParameter)parent.getItemAtPosition(pos);
                break;
            case R.id.spinnerSecondary:
                selectedParams[1]=(SortParameter)parent.getItemAtPosition(pos);
                break;
        }

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SortListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()+ "must implement SortListener");
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
