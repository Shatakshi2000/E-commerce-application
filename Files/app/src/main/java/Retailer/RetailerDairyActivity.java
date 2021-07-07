package Retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.foodstuff.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RetailerDairyActivity extends AppCompatActivity {

    private TabLayout dairyTabLayout;
    private ViewPager dairyViewPager;
    TabItem tabMyShop,tabShopping;
    Toolbar retailerDairyToolbar;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dairy);

        dairyTabLayout = findViewById(R.id.reatiler_tab_layout);
        dairyViewPager = findViewById(R.id.retailer_viewpager);
        retailerDairyToolbar = (Toolbar)findViewById(R.id.retailer_dairy_toolbar);
        tabMyShop = findViewById(R.id.products_on_sale);
        tabShopping=findViewById(R.id.product_to_buy);


        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),dairyTabLayout.getTabCount());
        dairyViewPager.setAdapter(viewPagerAdapter);

        dairyTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                dairyViewPager.setCurrentItem(tab.getPosition());
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

        dairyViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(dairyTabLayout));
        // Listen for scroll

    }
}
