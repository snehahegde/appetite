package com.appetite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by Kavi on 2/22/16.
 */
public class MenulistAdapter extends ArrayAdapter<Menu> {

    Context context;
    private final List<Menu> menuList;


    public MenulistAdapter(Context context, int layout, List<Menu> menuList) {
        super(context, R.layout.menulist_rowlayout, menuList);
        this.context = context;
        this.menuList = menuList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Menu menuItems = menuList.get(position);
        //the rwo layout is inflated
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menulist_rowlayout, parent, false);

//        //item name
        TextView tv_itemName = (TextView) view.findViewById(R.id.itemName);
        tv_itemName.setText(menuItems.getItemName());
//
        //item price
        TextView tv_itemPrice = (TextView) view.findViewById(R.id.itemPrice);
        tv_itemPrice.setText(menuItems.getItemPrice());

        //item image
//        ImageView iv_itemImage = (ImageView) view.findViewById(R.id.itemImage);
//        iv_itemImage.setImageResource(menuItems.getItemImg()[position]);
//        ImageView tv_itemImg = (ImageView) view.findViewById(R.id.itemImage);
 //       byte[] imageAsBytes = Base64.decode(menuItems.getItemImg().getBytes(), 0);
        //Bitmap scaledImg = Bitmap.createScaledBitmap(imageAsBytes, 200, 200, false);
 //       tv_itemImg.setImageBitmap(BitmapFactory.decodeByteArray(
 //               imageAsBytes, 0, imageAsBytes.length));

        ImageView tv_itemImg = (ImageView) view.findViewById(R.id.itemImage);
        byte[] imageAsBytes = Base64.decode(menuItems.getItemImg().getBytes(), 0);
        tv_itemImg.setImageBitmap(BitmapFactory.decodeByteArray(
                imageAsBytes, 0, imageAsBytes.length));



        //this.notifyDataSetChanged();

        return view;

    }

}
