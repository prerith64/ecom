package com.example.bottomnavigation;

//import static com.example.bottomnavigation.EQuires.database;

import static android.content.Intent.getIntent;

import static com.example.bottomnavigation.EQuires.cartItemModelList;
import static com.example.bottomnavigation.EQuires.cartList;
import static com.example.bottomnavigation.EQuires.user;
import static com.example.bottomnavigation.EQuires.username;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class productDetailsActivity extends AppCompatActivity {
      private ViewPager productImagesViewpager;
      private LinearLayout add_to_cart;
      private TabLayout viewPagerIndicator;
     private TextView productTitle;
     private TextView productPrice;
     private TextView cuttedPrice;
    public static Dialog loadingDialog;
      public static  boolean ALREADY_ADDED_TO_CART=false;
      public static boolean running_cart_query=false;
      private Button buyNowbtn;
      private Dialog signInDialog;
    private DocumentSnapshot documentSnapshot;

      private FirebaseFirestore firebaseFirestore;
      private FirebaseDatabase database=FirebaseDatabase.getInstance();

      public static String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewpager=findViewById(R.id.product_images_viewpager);
        viewPagerIndicator=findViewById(R.id.viewpager_indicater);
        buyNowbtn=findViewById(R.id.buy_now_btn);
        productTitle =findViewById(R.id.productTitle);
        productPrice=findViewById(R.id.productPrice);
        cuttedPrice=findViewById(R.id.cuttedPrice);
        add_to_cart=findViewById(R.id.add_to_cart_btn);
        buyNowbtn=findViewById(R.id.buy_now_btn);




//
//       buyNowbtn.setOnClickListener((v)->{
//
//
//       });


       add_to_cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (!running_cart_query) {
                   running_cart_query = true;
                   if (ALREADY_ADDED_TO_CART) {
                       running_cart_query = false;
                       Toast.makeText(productDetailsActivity.this, "Already added to cart!", Toast.LENGTH_SHORT).show();
                   } else {
                       loadingDialog=new Dialog(productDetailsActivity.this);
                       loadingDialog.setContentView(R.layout.loading_progress_dialog);
                       loadingDialog.setCancelable(false);
                       loadingDialog.getWindow().setBackgroundDrawable(productDetailsActivity.this.getDrawable(R.drawable.slider_background));
                       loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                       loadingDialog.show();
                       EQuires.cartItemModelList = new ArrayList<CartItemModel>();
                       Map<String, Object> addProduct = new HashMap<>();
                       addProduct.put("product_ID_" + String.valueOf(EQuires.cartList.size()), productID);
                       addProduct.put("list_size", (long) (cartList.size() + 1));
                       firebaseFirestore.collection("USERS").document(user.getUid()).collection("USER_DATA").document("MY_CART")
                               .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful()) {

                                           Log.d("cart","cart model List"+cartItemModelList.size());
                                           Log.d("mesg"," cart size"+cartList.size());
                                           if (cartItemModelList.size() != 0) {
                                               EQuires.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString()
                                                       , documentSnapshot.get("product_title").toString()
                                                       , documentSnapshot.get("product_price").toString()
                                                       , documentSnapshot.get("cutted_price").toString()
                                                       , (long) 1));
                                           }
                                           ALREADY_ADDED_TO_CART = true;
                                           EQuires.cartList.add(productID);
                                           Toast.makeText(productDetailsActivity.this, "Addedd to cart succesfully!", Toast.LENGTH_SHORT).show();

                                           running_cart_query = false;
                                           loadingDialog.dismiss();
                                       } else {
                                           running_cart_query = false;
                                           String error = task.getException().getMessage();
                                           Toast.makeText(productDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });
                   }

               }
           }
        });

//         add_to_cart.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Toast.makeText(productDetailsActivity.this,"Item added to Cart",Toast.LENGTH_SHORT).show();
//             }
//         });


        buyNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(productDetailsActivity.this,"the order is placed",Toast.LENGTH_SHORT).show();
            }
        });







        firebaseFirestore=FirebaseFirestore.getInstance();
        List<String> productImages =new ArrayList<>();
        productID=getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {       //document("cet4IqZ9khbYbNYkBPtR")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                             DocumentSnapshot documentSnapshot=task.getResult();
                             for(long x=1;x<(long)documentSnapshot.get("no_of_product_images")+ 1;x++){
                              productImages.add(documentSnapshot.get("product_image_"+x).toString());
                             }
                            ProductIMagesAdapter productIMagesAdapter =new ProductIMagesAdapter(productImages);
                            productImagesViewpager.setAdapter(productIMagesAdapter);
                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            productPrice.setText("Rs."+documentSnapshot.get("product_price").toString()+"/-");
                            cuttedPrice.setText("Rs."+documentSnapshot.get("cutted_price").toString()+"/-");

                            if(EQuires.cartList.size()==0){
                                 Log.d("Pro","product");
                                EQuires.loadCartList(productDetailsActivity.this,loadingDialog,true); //24:59 62
                            }
       if(EQuires.cartList.contains(productID)){
           ALREADY_ADDED_TO_CART=true;

       }else {
           ALREADY_ADDED_TO_CART=false;
       }
                        }else{
                            String error=task.getException().getMessage();
                            Toast.makeText(productDetailsActivity.this,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });



//        List<int> productImages =new ArrayList<>();
//        productImages.add(R.drawable.banner1);
//        productImages.add(R.drawable.banner2);
//        productImages.add(R.drawable.baseline_add_24);
//        productImages.add(R.drawable.baseline_home_24);


        viewPagerIndicator.setupWithViewPager(productImagesViewpager,true);





//        signInDialog=new Dialog(productDetailsActivity.this);
//        signInDialog.setContentView(R.layout.sign_in_dialog);
//        signInDialog.setCancelable(true);
//        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        Button dialogSignInBtn=signInDialog.findViewById(R.id.sign_in_btn);
//        Button dialogSignUpBtn=signInDialog.findViewById(R.id.sign_up_btn);
//        final Intent registerIntent=new Intent(productDetailsActivity.this,CartFragment.class);
//
//        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signInDialog.dismiss();
//                startActivity(registerIntent);
//            }
//        });
//        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signInDialog.dismiss();
//                startActivity(registerIntent);
//            }
//        });


    }




    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}