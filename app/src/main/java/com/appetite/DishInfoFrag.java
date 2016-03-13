package com.appetite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pravallika on 3/9/16.
 */
public class DishInfoFrag extends Fragment {
    ImageView chefDishPic;
    TextView chefIngredients, chefQuantity;
    EditText quantityInput;
    String quantityOrdered, chefName, chefDishName;
    Button orderButton;
    Firebase mRef;
    int remainingQuantity = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.tab_dish_info, container, false);
        chefDishPic = (ImageView) v.findViewById(R.id.chefDishPic);
        chefDishPic.setImageResource(R.drawable.salmon_slaw);

        chefIngredients = (TextView) v.findViewById(R.id.chefIngredients);
        chefIngredients.setText(DishDetailsActivity.chefDishIngredients);
        System.out.println(" " + DishDetailsActivity.chefDishQuantity);
        chefQuantity = (TextView) v.findViewById(R.id.chefQuantity);

        chefQuantity.setText(DishDetailsActivity.chefDishQuantity);

        quantityInput = (EditText) v.findViewById(R.id.etUserQuantity);
        orderButton = (Button) v.findViewById(R.id.btOrder);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
        return v;
    }

    public void placeOrder() {
        quantityOrdered = quantityInput.getText().toString();
        remainingQuantity = Integer.parseInt(DishDetailsActivity.chefDishQuantity) - Integer.parseInt(quantityOrdered);
        System.out.println("DIFF: " + remainingQuantity);
        chefQuantity.setText(String.valueOf(remainingQuantity));
        Firebase.setAndroidContext(getContext());

        mRef = new Firebase("https://app-etite.firebaseio.com/" + DishDetailsActivity.chefName);
//
//        Map<String, Object> quantityOrderedMap = new HashMap<String, Object>();
//        quantityOrderedMap.put(DishDetailsActivity.chefDishName + "/quantityOrdered", quantityOrdered);
//        quantityOrderedMap.put(DishDetailsActivity.chefDishName + "/quantity",String.valueOf(remainingQuantity));

       // mRef.updateChildren(quantityOrderedMap);
        System.out.println("987654321 : "+"updating quantity ordered and remaining quantity from dishInfoFrag class");

        showAlertDialog();

    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Order Confirmation");
        alertDialog.setMessage("Please confirm the order");
        //positive response
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SmsManager smsMngr = SmsManager.getDefault();
                smsMngr.sendTextMessage("+4088932829", null, "You have received and order from " + Login.userName + " for" + quantityInput + " " + DishDetailsActivity.chefDishName + ".", null, null);
                Toast.makeText(getActivity(), "Order placed!", Toast.LENGTH_LONG).show();
                quantityInput.setText(" ");
            }
        });


        alertDialog.create().show();

    }
}
