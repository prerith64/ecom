package com.example.bottomnavigation;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view = convertView;
        }

        ImageView productImage = view.findViewById(R.id.h_s_product_image);
        TextView productTitle = view.findViewById(R.id.h_s_product_tile);
        TextView productDescription = view.findViewById(R.id.h_s_product_discription);
        TextView productPrice = view.findViewById(R.id.h_s_product_price);

        Glide.with(parent.getContext()).load(horizontalProductScrollModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);
        productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
        productDescription.setText(horizontalProductScrollModelList.get(position).getProductDiscription());
        productPrice.setText("Rs."+horizontalProductScrollModelList.get(position).getProductPrice()+"/-");

        return view;
    }

}
