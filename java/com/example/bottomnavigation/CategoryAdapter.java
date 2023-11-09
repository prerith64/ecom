package com.example.bottomnavigation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.ContentView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder viewholder, int position) {
        String Icon= categoryModelList.get(position).getCategoryIconLink();
        String Name=categoryModelList.get(position).getCategoryName();
        viewholder.setCategory(Name,position);
        viewholder.setCategoryIcon(Icon);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryIcon;
        private TextView categoryName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon=itemView.findViewById(R.id.category_icon);
            categoryName=itemView.findViewById(R.id.category_name);
        }
        private void setCategoryIcon(String iconUrl){
            ///to do set category here
            if(!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(categoryIcon);
            }
        }
        private void setCategory(final String name,int position){
            categoryName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0) {
                        Intent CategoryIntent = new Intent(itemView.getContext(), CategoriesActivity.class);
                        CategoryIntent.putExtra("categoryName", name);
                        itemView.getContext().startActivity(CategoryIntent);
                    }
                }
            });
        }
    }
}
