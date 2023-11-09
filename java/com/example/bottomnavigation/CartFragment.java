package com.example.bottomnavigation;

import static android.content.Intent.getIntent;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {


    private RecyclerView cartItemRecyclerView;
    Button order;

    private TextView totalAmount;
    public static Dialog loadingDialog;

    public static CartAdapter cartAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart,container,false);

          order=view.findViewById(R.id.cart_continue_btn);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });

        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        cartItemRecyclerView=view.findViewById(R.id.cart_items_recyclerView);
        totalAmount=view.findViewById(R.id.total_cart_amount);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

        if(EQuires.cartItemModelList.size()==0){
            Log.d("frag","Fragment");
            EQuires.cartList.clear();
            EQuires.loadCartList(getContext(),loadingDialog,true);
        }else {
            if(EQuires.cartItemModelList.get(EQuires.cartItemModelList.size()-1).getType()==CartItemModel.TOTAL_AMOUNT) {
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDialog.dismiss();
        }

        cartAdapter=new CartAdapter(EQuires.cartItemModelList,totalAmount);     //4:50
         cartItemRecyclerView.setAdapter(cartAdapter);
         cartAdapter.notifyDataSetChanged();
        return view;
    }


}