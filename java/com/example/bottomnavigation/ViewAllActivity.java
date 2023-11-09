package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
    private GridView gridview;
    public static List<WishlistModel> wishlistModelList=new ArrayList<>();
    public  static List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView=findViewById(R.id.recycler_view1);
        gridview=findViewById(R.id.grid_view);


        int layout_code=getIntent().getIntExtra("layout_code",-1);
        if (layout_code == 0) {
            Log.d("MyApp", "Setting up RecyclerView for layout_code == 0");

            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.d("MyApp", "RecyclerView setup completed.");
        }
        else if (layout_code==1) {
            gridview.setVisibility(View.VISIBLE);

            GridProductLayoutAdapter gridProductLayoutAdapter=new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridview.setAdapter(gridProductLayoutAdapter);
        }


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