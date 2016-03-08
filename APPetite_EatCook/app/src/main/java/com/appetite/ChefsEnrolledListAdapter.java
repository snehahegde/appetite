package com.appetite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.List;

public class ChefsEnrolledListAdapter extends ArrayAdapter<ChefsEnrolledList> {


    Context context;
    private final List<ChefsEnrolledList> chefsList;
    ImageView chefImg;

    public ChefsEnrolledListAdapter(Context context, int layout, List<ChefsEnrolledList> chefsList) {
        super(context, R.layout.activity_chefs_enrolled_list_adapter, chefsList);
        this.context = context;
        this.chefsList = chefsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChefsEnrolledList chefsEnrolledList = chefsList.get(position);
        //the rwo layout is inflated
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_chefs_enrolled_list_adapter, parent, false);

       //chef's name
        TextView tv_chefName = (TextView) view.findViewById(R.id.chefsName);
        tv_chefName.setText(chefsEnrolledList.getChefName());

        //chef's pic
        chefImg = (ImageView) view.findViewById(R.id.chef_pic);
        new LoadProfileImage(chefImg).execute(chefsEnrolledList.getChefPic());



        return view;

    }
    /**
     * Background Async task to load user profile picture from url
     */
    public static class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}
