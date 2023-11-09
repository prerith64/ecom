package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AddressActivity extends AppCompatActivity {

    private EditText addressEditText;
    private EditText mobileNumberEditText; // New mobile number field
    private Button saveButton;
    private Button ContinueBtn;
    private RecyclerView recyclerView;
    private AddressAdapter adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        databaseReference = FirebaseDatabase.getInstance().getReference("addresses");

        addressEditText = findViewById(R.id.editTextAddress);
        mobileNumberEditText = findViewById(R.id.editTextMobileNumber); // Initialize mobile number field
        saveButton = findViewById(R.id.buttonSave);
        ContinueBtn=findViewById(R.id.buttonContinue);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AddressAdapter(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomAddress address) {
                adapter.selectAddress(address);
            }
        },true);

        recyclerView.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        // Retrieve addresses from Firebase and update the RecyclerView
        retrieveAddressesFromFirebase();


        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure that the selected address is not null
                if (adapter.getSelectedAddress() != null) {
                    // Navigate to PaymentActivity and pass the selected address
                    Intent paymentIntent = new Intent(AddressActivity.this, PaymentActivity.class);
                    paymentIntent.putExtra("selectedAddress", adapter.getSelectedAddress().getAddress());
                    startActivity(paymentIntent);
                } else {
                    Toast.makeText(AddressActivity.this, "Please select an address.", Toast.LENGTH_SHORT).show();
                }
            }
        });









    }

    private void saveAddress() {
        String address = addressEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();

        if (!address.isEmpty()) {
            // Create a unique key for the address
            String addressId = databaseReference.push().getKey();

            // Create an Address object
            CustomAddress addressObj = new CustomAddress(addressId, address, mobileNumber);

            // Save the address to Firebase Database
            databaseReference.child(addressId).setValue(addressObj);

            // Clear the input fields
            addressEditText.setText("");
            mobileNumberEditText.setText("");

            // Display a success message or perform any other action
            Toast.makeText(this, "Address saved!", Toast.LENGTH_SHORT).show();
        } else {
            // Display an error message for empty address
            Toast.makeText(this, "Please enter an address.", Toast.LENGTH_SHORT).show();
        }

        // After saving, update the RecyclerView
        retrieveAddressesFromFirebase();
    }

    private void retrieveAddressesFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear(); // Clear the existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CustomAddress address = snapshot.getValue(CustomAddress.class);
                    adapter.addAddress(address);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }




}
