package com.appetite;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

public class ChefMenuItemActivity extends AppCompatActivity {

    String itemName,chefName,location,ingredients,foodImagePath;
    EditText et_quantity;
    EditText et_ingredients;
    int quantity;
    Button bt_post;
    Firebase mRef;
    public GoogleApiClient mGoogleApiClient;
    private static final int SELECT_PHOTO = 100;
    final static private String APP_KEY = "ya6xzn1c4rjsjdu";
    final static private String APP_SECRET = "tka1puxynswkeek";
    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu_item);

        AppKeyPair keyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session =
                new AndroidAuthSession(keyPair,
                        "bu-o0D7UlrAAAAAAAAAACqbhdtpfpEG8J1_KrFJWnCLfyxlnJ8q_44LSiXkk1yig");
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        ImageButton captureButton = (ImageButton)findViewById(R.id.camera_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickIntent = new Intent(Intent.ACTION_PICK);
                photoPickIntent.setType("image/*");
                startActivityForResult(photoPickIntent, SELECT_PHOTO);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //view item name
        TextView caption = (TextView) findViewById(R.id.view_itemName);
        caption.setText(getIntent().getExtras().getString("item_name"));
        caption.setTextColor(Color.BLACK);
        et_ingredients = (EditText) findViewById(R.id.et_ingredients);
        et_quantity = (EditText) findViewById(R.id.et_quantityLabel);
        bt_post = (Button) findViewById(R.id.postButton);

        postChefDetails();
    }

    public void postChefDetails(){
        // some change
        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients = et_ingredients.getText().toString();
                quantity = Integer.parseInt(et_quantity.getText().toString());
                itemName = getIntent().getExtras().getString("item_name");
                chefName = Login.userName;
                System.out.println(chefName + " " + itemName + " " + quantity + " " + ingredients + foodImagePath);

                mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);
                mRef.child(itemName).setValue(new ChefMenuItem(ingredients, quantity, foodImagePath));

//                Intent chefMenuList = new Intent(ChefMenuItemActivity.this,ChefMenuInfo.class);
//                chefMenuList.putExtra("menu_name",itemName);
//                startActivity(chefMenuList);
            }
        });
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (MainActivity.cookModule){
            getMenuInflater().inflate(R.menu.menu_cook, menu);}
        else{
            getMenuInflater().inflate(R.menu.menu_eat, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
        }

        else if(id == R.id.action_reviews){
            String chefName = Login.userName;
            Intent reviewsIntent = new Intent(this,ReviewsCookModuleActivity.class);
            reviewsIntent.putExtra("chef", chefName);
            startActivity(reviewsIntent);
            return true;
        }
        else if (id==R.id.action_logOut){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //mStatusTextView.setText("");
                            Intent returnToLogin = new Intent(getApplicationContext(), MainActivity.class);
                            returnToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(returnToLogin);
                            finish();
                            MainActivity.cookModule = false;
                            MainActivity.eatModule = false;


                        }
                    });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturned){
        super.onActivityResult(requestCode, resultCode, imageReturned);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK) {
                    try {
                        InputStream imageInputStream = this.getContentResolver().openInputStream(imageReturned.getData());
                        AsyncTask<InputStream, Void, String> StorageHandler = new AsyncTask<InputStream, Void, String>() {

                            @Override
                            protected String doInBackground(InputStream... inputStream) {
                                //BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream[0]);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                                int fileSize = 0;
                                byte[] inputByteBuffer = new byte[1024];

                                if(inputStream[0] != null) {
                                    while(true) {
                                        try {
                                            int readBytes = inputStream[0].read(inputByteBuffer);
                                            if (readBytes == -1) {
                                                break;
                                            }
                                            byteArrayOutputStream.write(inputByteBuffer, 0, readBytes);
                                            fileSize = fileSize + readBytes;
                                        } catch (IOException e) {
                                            Log.e("ioexception", "in reading the image");
                                        }
                                    }
                                }

                                byte[] inputArray = byteArrayOutputStream.toByteArray();
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputArray);
                                try {
                                    String name = randomUUID().toString();
                                    foodImagePath = "/"+"chef_image_"+name;
                                    DropboxAPI.Entry response = mDBApi.putFile("/"+"chef_image_"+name, byteArrayInputStream,
                                            fileSize, null, null);
                                    Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
                                } catch (DropboxException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        };
                        StorageHandler.execute(imageInputStream);
                    } catch (FileNotFoundException e) {
                        Log.d("notfound", "File not found");
                    }

                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


}
