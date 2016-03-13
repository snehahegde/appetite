package com.appetite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChefMenulistAdapter extends ArrayAdapter<ChefList> {


    Context context;

    private final List<ChefList> chefMenuList;

    public ChefMenulistAdapter(Context context, int layout, List<ChefList> chefMenuList) {
        super(context, R.layout.cheflist_rowlayout, chefMenuList);
        {
            this.context = context;
            this.chefMenuList = chefMenuList;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChefList chefMenu = chefMenuList.get(position);
        //the rwo layout is inflated
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cheflist_rowlayout, parent, false);

//        //item name
        TextView tv_itemName = (TextView) view.findViewById(R.id.chefList_itemName);
        tv_itemName.setText(chefMenu.getMenuName());

        return view;
    }
}
