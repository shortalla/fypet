package com.example.andrew.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 08/03/2016.
 */
public class ListDataAdaptor extends ArrayAdapter {
    List list = new ArrayList();
    public ListDataAdaptor(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView PET_NAME, PET_BODY;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;

        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.PET_NAME = (TextView) row.findViewById(R.id.text_pet_name);
            layoutHandler.PET_BODY = (TextView) row.findViewById(R.id.text_pet_body);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();
        }
        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.PET_NAME.setText(dataProvider.getPetName().toString());
        layoutHandler.PET_BODY.setText(dataProvider.getPetBody().toString());

        return row;
    }
}