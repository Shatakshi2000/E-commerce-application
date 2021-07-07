package Retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.foodstuff.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RetailerSpiceActivity extends AppCompatActivity {

    private TabLayout spiceTabLayout;
    private ViewPager spiceViewPager;
    TabItem tabMyShop,tabShopping;
    Toolbar retailerSpiceToolbar;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_spice);

        spiceTabLayout = findViewById(R.id.reatiler_tab_layout);
        spiceViewPager = findViewById(R.id.retailer_viewpager);
        retailerSpiceToolbar = (Toolbar)findViewById(R.id.retailer_spices_toolbar);
        tabMyShop = findViewById(R.id.products_on_sale);
        tabShopping=findViewById(R.id.product_to_buy);


        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),spiceTabLayout.getTabCount());
        spiceViewPager.setAdapter(viewPagerAdapter);

        spiceTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                spiceViewPager.setCurrentItem(tab.getPosition());
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

        spiceViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(spiceTabLayout));
        // Listen for scroll

    }
    }
