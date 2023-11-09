package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.bottomnavigation.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity  {
   ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        Intent intent=getIntent();
        String usernameUser = intent.getStringExtra("username");
        EQuires.username=usernameUser;
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item->{
            int itemId = item.getItemId();
            if(itemId==R.id.home){
                replaceFragment(new HomeFragment());
            }else  if(itemId==R.id.Categories){
                replaceFragment(new CategoriesFragment());
            }else  if(itemId==R.id.Orders){
                replaceFragment(new MyOrdersFragment());
            }else  if(itemId==R.id.cart) {
                replaceFragment(new CartFragment());
            }
           return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

    }

}

