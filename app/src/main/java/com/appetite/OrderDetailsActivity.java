package com.appetite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailsActivity extends AppCompatActivity {
    private int quantityAvailable;
    private int quantityRequired;
    private EditText quantityRequiredTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.appetite.R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(com.appetite.R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView quantityAvailableTextView = (TextView)findViewById(R.id.quantityAvailable);
        quantityAvailable = Integer.parseInt(quantityAvailableTextView.getText().toString());

        totalTextView = (TextView)findViewById(R.id.totalTextView);
        quantityRequiredTextView = (EditText)findViewById(R.id.quantityRequired);
        quantityRequiredTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    quantityRequired = Integer.parseInt(quantityRequiredTextView.getText().toString());
                    totalTextView.setText("$" + String.valueOf(quantityRequired * 10));
                } catch (NumberFormatException e) {

                }

            }
    });


        Button payButton = (Button)findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!quantityRequiredTextView.getText().toString().equals("")) {
                    quantityRequired = Integer.parseInt(quantityRequiredTextView.getText().toString());
                    if (quantityRequired == 0) {
                        Context context = getApplicationContext();
                        String text = "Please enter a valid quantity";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        if (quantityRequired > quantityAvailable) {
                            Context context = getApplicationContext();
                            String text = "Quantity required should be less than or equal to the available quantity";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            totalTextView.setText("");
                        } else {
                            Intent intent = new Intent(OrderDetailsActivity.this, OrderConfirmationActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Context context = getApplicationContext();
                    String text = "Please enter the required quantity";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                quantityRequiredTextView.setText("");

            }
        });

    }

    //@Override

}
