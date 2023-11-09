package com.example.bottomnavigation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder viewholder, int position) {
         String resource=horizontalProductScrollModelList.get(position).getProductImage();
         String productID=horizontalProductScrollModelList.get(position).getProductID();
        ViewGroup.LayoutParams layoutParams = viewholder.itemView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height =ViewGroup.LayoutParams.MATCH_PARENT;
        viewholder.itemView.setLayoutParams(layoutParams);
        String title =horizontalProductScrollModelList.get(position).getProductTitle();
        String description=horizontalProductScrollModelList.get(position).getProductDiscription();
        String price =horizontalProductScrollModelList.get(position).getProductPrice();

        viewholder.setProductImage(resource);

        viewholder.setProductTitle(title);
        viewholder.setProductDescription(description);
        viewholder.setProductPrice(price);

    }

    @Override
    public int getItemCount() {
        if(horizontalProductScrollModelList.size() > 8){
            return 8;
        }else{
            return horizontalProductScrollModelList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private  TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage =itemView.findViewById(R.id.h_s_product_image);
            productTitle =itemView.findViewById(R.id.h_s_product_tile);
            productDescription =itemView.findViewById(R.id.h_s_product_discription);
            productPrice =itemView.findViewById(R.id.h_s_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productID = horizontalProductScrollModelList.get(getAdapterPosition()).getProductID();
                    Intent productDetailsIntent=new Intent(itemView.getContext(),productDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID", productID);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }


        private void setProductImage(String resource){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private  void setProductDescription(String Description){
            productDescription.setText(Description);
        }
        private  void setProductPrice(String price){
            productPrice.setText("Rs."+price+"/-");
        }
    }
}
