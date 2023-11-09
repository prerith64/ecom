package com.example.bottomnavigation;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.annotations.concurrent.Background;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private final List<SliderModel> sliderModelList;

    public SliderAdapter(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        ConstraintLayout bannerContainer = view.findViewById(R.id.banner_continer);

        // Use viewType to get the correct SliderModel
        SliderModel sliderModel = sliderModelList.get(viewType);

//        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModel.getBackgroundcolor())));
        bannerContainer.setBackgroundColor(Color.parseColor(sliderModel.getBackgroundcolor()));
        ImageView banner = view.findViewById(R.id.banner_slider);
        Glide.with(parent.getContext()).load(sliderModel.getBanner()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(banner);

        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderModel model = sliderModelList.get(position);
        ImageView banner = holder.banner;
        Glide.with(banner.getContext())
                .load(model.getBanner())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(banner);
        ConstraintLayout bannerContainer=holder.bannerContainer;
        bannerContainer.setBackgroundColor(Color.parseColor(model.getBackgroundcolor()));
    }

    @Override
    public int getItemCount() {
        return sliderModelList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView banner;
        ConstraintLayout bannerContainer;
        public SliderViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_slider);
            bannerContainer=itemView.findViewById(R.id.banner_continer);
        }
    }
}
