package com.appetite;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pravallika on 3/9/16.
 */
public class DishInfoFrag extends Fragment {
    ImageView chefDishPic;
    TextView chefIngredients, chefQuantity;
    EditText quantityInput;
    String quantityOrdered, chefName, chef,dish,quan;
    Button orderButton;
    Firebase mRef,mRef1;
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
        chefQuantity.setText(String.valueOf(remainingQuantity));
        Firebase.setAndroidContext(getContext());

        mRef = new Firebase("https://app-etite.firebaseio.com/" + DishDetailsActivity.chefName);

        Map<String, Object> quantityOrderedMap = new HashMap<String, Object>();
        quantityOrderedMap.put(DishDetailsActivity.chefDishName + "/quantityOrdered", quantityOrdered);
        System.out.println("Testing" + DishDetailsActivity.chefName);
        quantityOrderedMap.put(DishDetailsActivity.chefDishName + "/quantity", String.valueOf(remainingQuantity));
        mRef.updateChildren(quantityOrderedMap);

        mRef1 = new Firebase("https://app-etite.firebaseio.com/orders/");
        Map<String, Object> orders = new HashMap<String, Object>();
        orders.put("chef", DishDetailsActivity.chefName);
        orders.put("customer", Login.userName);
        orders.put("dish", DishDetailsActivity.chefDishName);
        orders.put("quantity", quantityOrdered);
        mRef1.updateChildren(orders);
        showAlertDialog();
        mRef1 = new Firebase("https://app-etite.firebaseio.com/orders");
//        mRef1.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("Testing:Ondatachange called");
//
//                //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                System.out.println("Testing:inside for loop");
//
//                // if (postSnapshot.child("chef").getValue() != null && postSnapshot.child("dish") != null && postSnapshot.child("quantity") != null) {
//                chef = dataSnapshot.child("chef").getValue().toString();
//                System.out.println("Testing chef value" + dataSnapshot.child("chef").getValue().toString());
//                dish = dataSnapshot.child("dish").getValue().toString();
//                quan = dataSnapshot.child("quantity").getValue().toString();
//                System.out.println("Notify Testing : " + chef + " " + Login.userName);
//                if (Login.userName.equals(chef)) {
//                    notifyChef();
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("The read failed: ", firebaseError.getMessage());
//
//            }
 //       });
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Order Confirmation");
        alertDialog.setMessage("Please confirm the order");
        //positive response
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


//                SmsManager smsMngr = SmsManager.getDefault();
//                smsMngr.sendTextMessage("+4088932829", null, "You have received and order from " + Login.userName + " for" + quantityInput + " " + DishDetailsActivity.chefDishName + ".", null, null);
                Toast.makeText(getActivity(), "Order placed!You will be notified about the pick up time shortly.", Toast.LENGTH_LONG).show();
                quantityInput.setText(" ");
            }
        });
        alertDialog.create().show();

    }
    public void notifyChef(){
        int requestCode = 0;
        int flags = 0;
        Intent i = new Intent(getContext(),ChefMenuInfo.class);
        i.putExtra("menu_item",dish);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getContext(), requestCode, i, flags);
        int id = 12345;
        Notification notification = new Notification.Builder(getContext())
                .setContentTitle("Orders")
                .setContentText("You have received an order")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[0])
                .build();
      //  notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }
}
