package com.example.bottomnavigation;

import static com.example.bottomnavigation.EQuires.firebaseFirestore;
import static com.example.bottomnavigation.EQuires.username;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter {


    private List<CartItemModel> cartItemModelList;

    public static TextView cartTotalAmount;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView cartTotalAmount) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;

    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new cartItemViewholder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new cartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                long Quantity = cartItemModelList.get(position).getQuantity();
                CartItemModel cartItem = cartItemModelList.get(position);
                ((cartItemViewholder) viewHolder).setItemDetails(productID, resource, title, productPrice, cuttedPrice, Quantity, cartItem, position);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = calculateTotalItems(); // Calculate the total number of items
                int totalItemPrice = calculateTotalAmount();
                String deliveryPrice;
                int savedAmount = 0;
                int totalAmount;

                if (totalItemPrice > 500) {
                    deliveryPrice = "FREE";
                    totalAmount = totalItemPrice;
                } else {
                    deliveryPrice = "60";
                    totalAmount = totalItemPrice + 60;
                }

                ((cartTotalAmountViewholder) viewHolder).setTotalAmount(totalItems, totalItemPrice, deliveryPrice, totalAmount, savedAmount);

                break;
            default:
                return;
        }
    }

    private int calculateTotalItems() {
        int totalItems = 0;
        for (CartItemModel item : cartItemModelList) {
            if (item.getType() == CartItemModel.CART_ITEM) {
                totalItems += item.getQuantity();
            }
        }
        return totalItems;
    }

    private int calculateTotalAmount() {
        int totalItems = 0;
        int totalItemPrice = 0;
        Log.d("list", "cart " + EQuires.cartList.size());
        for (CartItemModel item : cartItemModelList) {
            Log.d("cart1", "cart model list " + cartItemModelList.size());
            if (item.getType() == CartItemModel.CART_ITEM) {
                totalItemPrice += Integer.parseInt(item.getProductPrice())*item.getQuantity();
                Log.d("myapp", "total item  " + totalItemPrice);
            }
        }

        return totalItemPrice;
    }


    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class cartItemViewholder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView productQuantity;
        private LinearLayout deleteBtn;

        public cartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image1);
            productTitle = itemView.findViewById(R.id.product_title1);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price1);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);

        }

        private void setItemDetails(String productID, String resource, String title, String productPriceText, String cuttedPriceText, long Quantity, CartItemModel cartItem, int position) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);
            productTitle.setText(title);
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            productQuantity.setText(String.valueOf(cartItem.getQuantity()));


            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showQuantityDialog(cartItem, position);
                }
            });


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!productDetailsActivity.running_cart_query) {
                        productDetailsActivity.running_cart_query = true;

                        EQuires.removeFromCart(position, itemView.getContext(), cartTotalAmount);
                    }
                }
            });
        }

        private void showQuantityDialog(final CartItemModel cartItem, final int position) {
            final Dialog quantityDialog = new Dialog(itemView.getContext());
            quantityDialog.setContentView(R.layout.dialog_quantity);
            quantityDialog.setCancelable(true);
            final EditText quantityEditText = quantityDialog.findViewById(R.id.editTextQuantity);
            Button applyButton = quantityDialog.findViewById(R.id.btnApply);
            Button cancelButton = quantityDialog.findViewById(R.id.btnCancel);


            // Initialize the dialog's EditText with the current quantity
            quantityEditText.setText(String.valueOf(cartItem.getQuantity()));

            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newQuantity = quantityEditText.getText().toString();
                    if (!newQuantity.isEmpty()) {
                        int newQuantityValue = Integer.parseInt(newQuantity);
                        cartItem.setQuantity(newQuantityValue);

                        // Update the product quantity in Firestore
                        updateQuantityInFirestore(cartItem, newQuantityValue);

                        // You can also notify your adapter to refresh the view
                        notifyDataSetChanged();
                    }
                    quantityDialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
            quantityDialog.dismiss();
        }
        });

            quantityDialog.show();
    }


        private void updateQuantityInFirestore( CartItemModel cartItem, int newQuantity) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference cartItemRef = db.collection("PRODUCTS").document(cartItem.getProductID());

            Map<String, Object> data = new HashMap<>();
            data.put("productQuantity", newQuantity);

            cartItemRef.update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Quantity updated successfully in Firestore
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure to update the quantity in Firestore
                        }
                    });
        }


    }

}



    class cartTotalAmountViewholder extends RecyclerView.ViewHolder{
        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;


        public  cartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);

            totalItems =itemView.findViewById(R.id.total_items);
            totalItemPrice=itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_price);
            totalAmount =itemView.findViewById(R.id.total_price);
            savedAmount =itemView.findViewById(R.id.saved_amount);
        }
        void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPriceText, int totalAmountText, int savedAmountText){
            totalItems.setText("Price("+totalItemText+" items");
            totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
            if(deliveryPriceText.equals("FREE")){
                deliveryPrice.setText(deliveryPriceText);
            }else {
                deliveryPrice.setText("Rs."+deliveryPriceText+"/-");
            }
            totalAmount.setText("Rs."+totalAmountText+"/-");
            CartAdapter.cartTotalAmount.setText("Rs."+totalAmountText+"/-");
            savedAmount.setText(" You saved Rs."+savedAmountText+"/- on this order");
        }




    }

