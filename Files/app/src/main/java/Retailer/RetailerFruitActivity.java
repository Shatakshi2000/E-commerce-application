package Retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.foodstuff.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RetailerFruitActivity extends AppCompatActivity {

    private TabLayout fruitsTabLayout;
    private ViewPager fruitsViewPager;
    TabItem tabMyShop,tabShopping;
    Toolbar retailerFruitsToolbar;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_fruit);

    fruitsTabLayout = findViewById(R.id.reatiler_tab_layout);
    fruitsViewPager = findViewById(R.id.retailer_viewpager);
    retailerFruitsToolbar = (Toolbar)findViewById(R.id.retailer_fruits_toolbar);
    tabMyShop = findViewById(R.id.products_on_sale);
    tabShopping=findViewById(R.id.product_to_buy);


    viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fruitsTabLayout.getTabCount());
    fruitsViewPager.setAdapter(viewPagerAdapter);

    fruitsTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            fruitsViewPager.setCurrentItem(tab.getPosition());
            if(tab.getPosition() == 0||tab.getPosition() == 1)
            {
                viewPagerAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

    fruitsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(fruitsTabLayout));
    // Listen for scroll

    }
}