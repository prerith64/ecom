package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bottomnavigation.AddressAdapter;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentRadioGroup;
    private RadioButton radioButtonCashOnDelivery;
    private RadioButton radioButtonOnlinePayment;
    private Button placeOrderButton;
    private RecyclerView selectedAddressRecyclerView;

    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        radioButtonCashOnDelivery = findViewById(R.id.radioButtonCashOnDelivery);
        radioButtonOnlinePayment = findViewById(R.id.radioButtonOnlinePayment);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        selectedAddressRecyclerView = findViewById(R.id.selectedAddressRecyclerView);

        // Initialize the RecyclerView and its adapter
        adapter = new AddressAdapter(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomAddress address) {
                adapter.selectAddress(address);
            }
        },false);
        selectedAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedAddressRecyclerView.setAdapter(adapter);

        // Get the selected address from AddressActivity
        String selectedAddress = getIntent().getStringExtra("selectedAddress");
        Log.d("PaymentActivity", "Selected Address: " + selectedAddress);
        // Display the selected address
        if (selectedAddress != null) {
            CustomAddress address = new CustomAddress("1", selectedAddress, "1234567890"); // Replace "1" with a unique ID
            adapter.addAddress(address);
            Log.d("PaymentActivity", "Address added to the adapter: " + address.getAddress());
        }

        placeOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setTitle("Order History");
                builder.setMessage("Order placed");

                // Add a positive button and define its action
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Replace the current Fragment with the MyOrdersFragment
                        FragmentManager fragmentManager = getSupportFragmentManager(); // Use getSupportFragmentManager() for AndroidX
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, new MyOrdersFragment()); // Replace with your Fragment class
                        transaction.addToBackStack(null);
                        transaction.commit();

                        dialog.dismiss();
                        finish();
                    }
                });

                // Add a negative button (optional)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform an action when the "Cancel" button is clicked
                        // For example, you can close the dialog or do nothing.
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
