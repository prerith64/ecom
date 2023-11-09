package com.example.bottomnavigation;

import static com.example.bottomnavigation.EQuires.lists;
import static com.example.bottomnavigation.EQuires.loadFragmentData;
import static com.example.bottomnavigation.EQuires.loadedCategoriesNames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private  List<CategoryModel> categoryModelList;
    private RecyclerView homePageRecycleView;
    private HomePageAdapter adapter;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_categories);
         String title=getIntent().getStringExtra("categoryName");
         getSupportActionBar().setTitle(title);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         categoryRecyclerView=findViewById(R.id.category_recycle_view);

        categoryModelList = new ArrayList<CategoryModel>();






/////////////////////////


        /////////// Horizontal product layout


/////////////////////////



        LinearLayoutManager testingLayoutManager =new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
         int listPosition=0;
        for(int x=0;x<loadedCategoriesNames.size();x++){
            if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition=x;
            }
        }
        if(listPosition==0){
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter =new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
            loadFragmentData(adapter,this,loadedCategoriesNames.size()-1,title);
        }else {
             adapter=new HomePageAdapter(lists.get(listPosition));
        }

        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("CATEGORIES")
//                .document("HOME").collection("TOP_DEALS").orderBy("index").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                                if((long)documentSnapshot.get("view_type") == 0){
//                                    List<SliderModel> sliderModelList = new ArrayList<>();
//                                    long no_of_banners = documentSnapshot.getLong("no_of_banners");
//                                    for (long x = 1; x < no_of_banners+1 ; x++) {
//                                        sliderModelList.add(new SliderModel(
//                                                documentSnapshot.getString("banner_" + x),
//                                                documentSnapshot.getString("banner_"+x+"_background")
//                                        ));
//                                    }
//
//                                    homePageModelList.add(new HomePageModel(0,sliderModelList));
//
//                                }else if((long) documentSnapshot.get("view_type") == 1){
//                                    homePageModelList.add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString()
//                                            ,documentSnapshot.get("background").toString()));
//
//                                }else if((long) documentSnapshot.get("view_type") == 2){
//                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList =new ArrayList<>();
//                                    long no_of_products = documentSnapshot.getLong("no_of_products");
//                                    for (long x = 1; x < no_of_products+1 ; x++) {
//                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
//                                                ,documentSnapshot.get("product_image_"+x).toString()
//                                                ,documentSnapshot.get("product_title_"+x).toString()
//                                                ,documentSnapshot.get("product_subtitle_"+x).toString()
//                                                ,documentSnapshot.get("product_price_"+x).toString())) ;
//                                    }
//
//                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList));
//
//
//
//                                }else if((long) documentSnapshot.get("view_type") == 3){
//                                    List<HorizontalProductScrollModel> GridLayoutModelList =new ArrayList<>();
//                                    long no_of_products = documentSnapshot.getLong("no_of_products");
//                                    for (long x = 1; x < no_of_products+1 ; x++) {
//                                        GridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
//                                                ,documentSnapshot.get("product_image_"+x).toString()
//                                                ,documentSnapshot.get("product_title_"+x).toString()
//                                                ,documentSnapshot.get("product_subtitle_"+x).toString()
//                                                ,documentSnapshot.get("product_price_"+x).toString())) ;
//                                    }
//
//                                    homePageModelList.add(new HomePageModel(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),GridLayoutModelList));
//                                }
//
//                            }
//                            adapter.notifyDataSetChanged();
//                        }else{
//                            String error =task.getException().getMessage();
//                            Toast.makeText(CategoriesActivity.this, error, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.main,menu);
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}