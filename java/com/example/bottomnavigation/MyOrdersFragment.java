package com.example.bottomnavigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {
      private RecyclerView myOrderRecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrderRecyclerview=view.findViewById(R.id.myorder_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerview.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList =new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.categoryfurniture,"soft water repelling sopha","order placed"));


        MyOrderAdapter myOrderAdapter =new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerview.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }
}