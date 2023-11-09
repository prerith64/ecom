package com.example.bottomnavigation;

import static android.content.Intent.getIntent;

import static com.example.bottomnavigation.CartFragment.cartAdapter;
import static com.example.bottomnavigation.CartFragment.loadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EQuires {
    public static String username;
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static   List<CategoryModel> categoryModelList=new ArrayList<CategoryModel>();
//    public static List<HomePageModel> homePageModelList=new ArrayList<>();

    public static List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();

    public static List<List<HomePageModel>> lists=new ArrayList<>();
    public static List<String> loadedCategoriesNames =new ArrayList<>();

         public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context){

             firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                             if(task.isSuccessful()){
                                 for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                     categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                                 }
                                 categoryAdapter.notifyDataSetChanged();
                             }else{
                                 String error =task.getException().getMessage();
                                 Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                             }
                         }
                     });



         }

         public static void loadFragmentData(final HomePageAdapter homePageAdapter,final Context context,final  int index,String categoryName){
             firebaseFirestore.collection("CATEGORIES")
                     .document(categoryName.toUpperCase()).collection("TOP_DEALS").orderBy("index").get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                             if(task.isSuccessful()){
                                 for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                     if((long)documentSnapshot.get("view_type") == 0){
                                         List<SliderModel> sliderModelList = new ArrayList<>();
                                         long no_of_banners = documentSnapshot.getLong("no_of_banners");
                                         for (long x = 1; x < no_of_banners+1 ; x++) {
                                             sliderModelList.add(new SliderModel(
                                                     documentSnapshot.getString("banner_" + x),
                                                     documentSnapshot.getString("banner_"+x+"_background")
                                             ));
                                         }

                                         lists.get(index).add(new HomePageModel(0,sliderModelList));

                                     }else if((long) documentSnapshot.get("view_type") == 1){
                                         lists.get(index).add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString()
                                                 ,documentSnapshot.get("background").toString()));

                                     }else if((long) documentSnapshot.get("view_type") == 2){
                                         List<WishlistModel> viewAllProductList=new ArrayList<>();
                                         List<HorizontalProductScrollModel> horizontalProductScrollModelList =new ArrayList<>();
                                         long no_of_products = documentSnapshot.getLong("no_of_products");
                                         for (long x = 1; x < no_of_products+1 ; x++) {
                                             horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
                                                     ,documentSnapshot.get("product_image_"+x).toString()
                                                     ,documentSnapshot.get("product_title_"+x).toString()
                                                     ,documentSnapshot.get("product_subtitle_"+x).toString()
                                                     ,documentSnapshot.get("product_price_"+x).toString())) ;
                                             viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_image_"+x).toString()
                                             ,documentSnapshot.get("product_full_title_"+x).toString()
                                             ,documentSnapshot.get("product_price_"+x).toString()
                                             ,documentSnapshot.get("cutted_price_"+x).toString()));
                                         }

                                         lists.get(index).add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList,viewAllProductList));



                                     }else if((long) documentSnapshot.get("view_type") == 3){
                                         List<HorizontalProductScrollModel> GridLayoutModelList =new ArrayList<>();
                                         long no_of_products = documentSnapshot.getLong("no_of_products");
                                         for (long x = 1; x < no_of_products+1 ; x++) {
                                             GridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
                                                     ,documentSnapshot.get("product_image_"+x).toString()
                                                     ,documentSnapshot.get("product_title_"+x).toString()
                                                     ,documentSnapshot.get("product_subtitle_"+x).toString()
                                                     ,documentSnapshot.get("product_price_"+x).toString())) ;
                                         }

                                         lists.get(index).add(new HomePageModel(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),GridLayoutModelList));
                                     } else if ((long) documentSnapshot.get("view_type") == 4) {
                                         List<WishlistModel> viewAllProduct=new ArrayList<>();

                                         long no_of_products = documentSnapshot.getLong("no_of_products");
                                         for (long x = 1; x < no_of_products+1 ; x++){
                                             viewAllProduct.add(new WishlistModel(documentSnapshot.get("product_image_"+x).toString()
                                                     ,documentSnapshot.get("product_full_title_"+x).toString()
                                                     ,documentSnapshot.get("product_price_"+x).toString()
                                                     ,documentSnapshot.get("cutted_price_"+x).toString()));

                                         }


                                       lists.get(index).add(new HomePageModel(4,"#ffffff",viewAllProduct));
                                     }

                                 }
                                 homePageAdapter.notifyDataSetChanged();
                             }else{
                                 String error =task.getException().getMessage();
                                 Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                             }
                         }
                     });

         }





   private static FirebaseDatabase database=FirebaseDatabase.getInstance();
    public static List<String> cartList =new ArrayList<>();
    public static List<CartItemModel> cartItemModelList=new ArrayList<>();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser user = firebaseAuth.getCurrentUser();
    public static void loadCartList(final Context context, Dialog dialog, boolean loadProductData){
        cartList.clear();
        firebaseFirestore.collection("USERS").document(user.getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.d("msg","hi "+user.getUid());
                        if(task.isSuccessful()){

                            for(long x=0;x<(long) task.getResult().get("list_size");x++){
                                cartList.add(task.getResult().get("product_ID_"+x).toString());

                                if(cartList.contains(productDetailsActivity.productID)){
                                    productDetailsActivity.ALREADY_ADDED_TO_CART=true;
                                }else{
                                    productDetailsActivity.ALREADY_ADDED_TO_CART=false;
                                }

                                if(loadProductData){
                                    loadingDialog.show();
                                    cartItemModelList.clear();
                                    final String productId=task.getResult().get("product_ID_"+x).toString();
                                    firebaseFirestore.collection("PRODUCTS").document(productId)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                       if(task.isSuccessful()){
                                                           int index=0;
                                                           if(cartList.size() >= 2){
                                                               index=cartList.size()-2;
                                                           }
                                                           cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM,productId,task.getResult().get("product_image_1").toString()
                                                                   ,task.getResult().get("product_title").toString()
                                                                   ,task.getResult().get("product_price").toString()
                                                                   ,task.getResult().get("cutted_price").toString()
                                                                   ,(long)task.getResult().get("product_qty")));


                                                           if(cartList.size()==0){
                                                               Log.d("mesg 1","hi "+cartList.size());
                                                               LinearLayout parent = (LinearLayout) CartAdapter.cartTotalAmount.getParent().getParent();
                                                               parent.setVisibility(View.INVISIBLE);
                                                               cartItemModelList.clear();
                                                           }
                                                           Log.d("mesg 1","hi "+cartList.size());
                                                           cartAdapter.notifyDataSetChanged();
                                                           dialog.dismiss();

                                                       }else {
                                                           String error =task.getException().getMessage();
                                                           Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                                                       }
                                                }
                                            });

                                }

                            }
                            if(cartList.size()>= 1){

                                cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));


                            }
                            cartAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }


    public static void removeFromCart(final int index, final Context context, TextView totalAmount){
        final String removeProductId=cartList.get(index);
        cartList.remove(index);
        Map<String , Object>updateCartList = new HashMap<>();
        for (int x=0;x<cartList.size();x++){
            updateCartList.put("product_ID_"+x,cartList.get(x));
        }
         updateCartList.put("list_size",(long) cartList.size());

        firebaseFirestore.collection("USERS").document(username).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        if(cartItemModelList.size()!=0){
                            cartItemModelList.remove(index);
                            cartAdapter.notifyDataSetChanged();
                        }
                        if(cartList.size()==0){
                            LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                            parent.setVisibility(View.GONE);
                            cartItemModelList.clear();
                        }

                        Toast.makeText(context,"Removed successfully",Toast.LENGTH_SHORT).show();
                        cartAdapter.notifyDataSetChanged();
                    }else {
                        cartList.add(index,removeProductId);
                        String error=task.getException().getMessage();
                        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                    }
                    productDetailsActivity.running_cart_query=false;
                });
    }


     public  static void loadOrders(Context context){
        myOrderItemModelList.clear();
        firebaseFirestore.collection("USERS").document(user.getUid()).collection("USER_ORDERS").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                           for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                           }
                        }else {
                            String error =task.getException().getMessage();
                            Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
     }

}
