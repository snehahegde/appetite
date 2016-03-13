package com.appetite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ChefProfile extends AppCompatActivity {

    TextView gname,gmail;
    String profilePicUrl,username,email,contactNo,location;
    ImageView profileImg;
    EditText phone,address;
    Button save;
    Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);
        //TODO lat long
        gname = (TextView) findViewById(R.id.tv_googleUsername);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        gname.setText(username);

        gmail = (TextView) findViewById(R.id.gmail_id);
        Bundle bdle = getIntent().getExtras();
        email = bdle.getString("email");
        gmail.setText(email);

        phone = (EditText)findViewById(R.id.etPhone);
        address = (EditText) findViewById(R.id.etAddress);
        save = (Button) findViewById(R.id.bSave);

        profileImg = (ImageView) findViewById(R.id.img_profilePic);
        Bundle picBundle = getIntent().getExtras();
        profilePicUrl = picBundle.getString("picUrl");

        if(profilePicUrl!=null){

            new LoadProfileImage(profileImg).execute(profilePicUrl);
            System.out.println("PhotoLink: " + profilePicUrl);

        }else{

            profileImg.setImageResource(R.drawable.profile_icon);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactNo = phone.getText().toString();

                location = address.getText().toString();

                mRef = new Firebase("https://app-etite.firebaseio.com/userInfo/chef");
                mRef.child(username).setValue(new User(username, email, profilePicUrl,contactNo,location));

                Intent menuList = new Intent(getApplicationContext(),MenuList.class);
                startActivity(menuList);

            }
        });




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
