package com.example.bottomnavigation;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.Viewholder holder, int position) {
        int resource = myOrderItemModelList.get(position).getProductImage();
        String title = myOrderItemModelList.get(position).getProductTitle();
        String deliveryDate = myOrderItemModelList.get(position).getDeliverystatus();
        holder.setData(resource, title, deliveryDate);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size(); // Return the actual item count
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.order_image);
            productTitle = itemView.findViewById(R.id.order_title);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
        }

        private void setData(int resource, String title, String deliveredDate) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            // Use ContextCompat.getColor() to retrieve color
            if (deliveredDate.equals("Cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.purple_500)));
            } else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.green)));
            }

            deliveryStatus.setText(deliveredDate);
        }
    }
}
