package com.example.bottomnavigation;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomePageModel> homePageModelList;


    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            case 4:
                return HomePageModel.WISHLIST_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup,false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomePageModel.STRIP_AD_BANNER:
                View stripadView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout, viewGroup,false);
                return new stripAdBannerViewholder(stripadView);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup,false);
                return new HorizontalProductViewholder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup,false);
                return new GridProductViewholder(gridProductView);
            case HomePageModel.WISHLIST_VIEW:
                View WishlistView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_view_all,viewGroup,false);
                return new WhishlistViewholder(WishlistView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder) viewholder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color=homePageModelList.get(position).getBackgroundColor();
                ((stripAdBannerViewholder)viewholder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutColor=homePageModelList.get(position).getBackgroundColor();
                String horizontalLayoutTitle=homePageModelList.get(position).getTitle();
                List<WishlistModel> viewAllProductList =homePageModelList.get(position).getViewAllProductList();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelLIst();
                ((HorizontalProductViewholder)viewholder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayoutTitle,layoutColor,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutColor=homePageModelList.get(position).getBackgroundColor();
                String gridLayoutTitle=homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelLIst();
                ((GridProductViewholder)viewholder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle,gridLayoutColor);
                break;
            case HomePageModel.WISHLIST_VIEW:
                List<WishlistModel> viewAllProduct =homePageModelList.get(position).getViewAllProduct();
                ((WhishlistViewholder)viewholder).setWhishlistViewholder(viewAllProduct);
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder {

        private ViewPager2 bannerSliderViewPager;
        private int currentPage = 2;
        private Timer timer;

        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;



        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);

        }

        private void setBannerSliderViewPager(List<SliderModel> sliderModelList) {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);

            bannerSliderViewPager.setCurrentItem(currentPage);


            ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // You can implement any required behavior here.
                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        pageLopper(sliderModelList);
                    }
                }
            };

            bannerSliderViewPager.registerOnPageChangeCallback(onPageChangeCallback);


            startbannerslideShow(sliderModelList);

            bannerSliderViewPager.setOnTouchListener((v, event) -> {
                pageLopper(sliderModelList);
                stopBannerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startbannerslideShow(sliderModelList);
                }
                return false;
            });
        }

        private void pageLopper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
        }

        private void startbannerslideShow(List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = () -> {
                if (currentPage >= sliderModelList.size()) {
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, false);//true
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }


    }

    public class stripAdBannerViewholder extends RecyclerView.ViewHolder{

        private ViewPager2 bannerSliderViewPager;
        private ImageView stripadImage;
        private ConstraintLayout stripadContainer;

        public stripAdBannerViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager); // Find ViewPager2 in stripAdView
            stripadImage = itemView.findViewById(R.id.strip_ad_image);
            stripadContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource,String color){
            Glide.with(itemView.getContext()).load(resource).apply( new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(stripadImage);
            stripadContainer.setBackgroundColor(Color.parseColor(color));
        }
    }


    public class HorizontalProductViewholder extends RecyclerView.ViewHolder{
        private ConstraintLayout container;
        private TextView horizontallayoutTitle;
        private Button horizontalviewAllbtn;
        private RecyclerView horizontalrecyclerview;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            container =itemView.findViewById(R.id.container);
            horizontallayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalviewAllbtn=itemView.findViewById(R.id.horizontal_scroll_viewall_btn);
            horizontalrecyclerview=itemView.findViewById(R.id.horizontal_scroll_layout_recycleview);
        }
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String color,List<WishlistModel> viewAllProductList){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontallayoutTitle.setText(title);


            if(horizontalProductScrollModelList.size() > 8){
               horizontalviewAllbtn.setVisibility(View.VISIBLE);
               horizontalviewAllbtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ViewAllActivity.wishlistModelList=viewAllProductList;
                       Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                       viewAllIntent.putExtra("layout_code",0);
                       viewAllIntent.putExtra("title",title);
                       itemView.getContext().startActivity(viewAllIntent);
                   }
               });
            }else{
                horizontalviewAllbtn.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager =new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalrecyclerview.setLayoutManager(linearLayoutManager);

            horizontalrecyclerview.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();

        }
    }




    public class GridProductViewholder extends RecyclerView.ViewHolder{

        ConstraintLayout container;
        private  TextView gridLayoutTitle;
        private Button gridLayoutViewBtn;

        private GridLayout gridProductLayout;
        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.grid_container);
             gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
             gridLayoutViewBtn =itemView.findViewById(R.id.grid_layout_layout_btn);
             gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }
        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String color){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);
//            gridProductLayout.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

            for (int x=0;x<4;x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_tile);
                TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_discription);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDescription.setText(horizontalProductScrollModelList.get(x).getProductDiscription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(x).getProductPrice()+"/-");
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                gridProductLayout.getChildAt(x).setOnClickListener((v)->{
                    Intent productDetailsIntent =new Intent(itemView.getContext(),productDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                });
            }

            gridLayoutViewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalProductScrollModelList=horizontalProductScrollModelList;
                    Intent viewAllIntent =new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });

        }
    }




    public class WhishlistViewholder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;


        public WhishlistViewholder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recycler_view1);

        }
        private void setWhishlistViewholder(List<WishlistModel> viewAllProduct){
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            WishlistAdapter adapter = new WishlistAdapter(viewAllProduct);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }


}
