package com.example.calatour.calatour.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calatour.calatour.R;
import com.example.calatour.calatour.model.Offer;

import java.util.List;

/**
 * Created by Rici on 19-Nov-18.
 */

public class OfferAdapter extends ArrayAdapter<Offer> {
    public OfferAdapter(Context context, List<Offer> objects) {
        super(context, 0, objects);
        // reference of	the	objects	list is	sent to the super class which will be needed to	implement getCount, getItem, getItemId methods
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Offer getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // get a reference to the LayoutInflate service
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // check if we can reuse a previously defined cell which now is not visible anymore
        View myRow = (convertView == null) ? inflater.inflate(R.layout.books_list_context, parent, false) : convertView;

        // get the visual elements and update them with the information from the model
        TextView title = myRow.findViewById(R.id.textTitle);
        TextView description = myRow.findViewById(R.id.textDescription);
        TextView price = myRow.findViewById(R.id.textPrice);
        ImageView image = myRow.findViewById(R.id.image);

        title.setText(getItem(position).getTitle());
        price.setText(getItem(position).getPrice().toString());
        description.setText(getItem(position).getDescription());
        image.setImageResource(getItem(position).getImage());


        return myRow;
    }



}