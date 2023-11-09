package com.example.bottomnavigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {


    private List<WishlistModel> wishlistModelList;
    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }


    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wistlist_item_layout,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int position) {
       String resource = wishlistModelList.get(position).getProductImage();
       String title=wishlistModelList.get(position).getProductTitle();
       String productPrice=wishlistModelList.get(position).getProductPrice();
       String cuttedPrice=wishlistModelList.get(position).getCuttedPrice();
       viewHolder.setData(resource,title,productPrice,cuttedPrice);
    }

    @Override
    public int getItemCount() {
        Log.d("Myapp","size "+wishlistModelList.size());
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private LinearLayout deletebtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image1);
            productTitle=itemView.findViewById(R.id.product_title1);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_price1);


        }
        private void setData(String resource,String title,String price,String cuttedprice){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);
            productTitle.setText(title);
            productPrice.setText("Rs."+price+"/-");
            cuttedPrice.setText("Rs."+cuttedprice+"/-");



        }
    }
}
