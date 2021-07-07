package Retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodstuff.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RetailerVegetableActivity extends AppCompatActivity {

    private TabLayout vegetablesTabLayout;
    private ViewPager vegetablesViewPager;
    TabItem tabMyShop,tabShopping;
    Toolbar retailerVegetablesToolbar;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_vegetable);

        vegetablesTabLayout = findViewById(R.id.reatiler_tab_layout);
        vegetablesViewPager = findViewById(R.id.retailer_viewpager);
        retailerVegetablesToolbar = (Toolbar)findViewById(R.id.retailer_vegetables_toolbar);
        tabMyShop = findViewById(R.id.products_on_sale);
        tabShopping=findViewById(R.id.product_to_buy);



        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),vegetablesTabLayout.getTabCount());
        vegetablesViewPager.setAdapter(viewPagerAdapter);

        vegetablesTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                vegetablesViewPager.setCurrentItem(tab.getPosition());
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

        vegetablesViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(vegetablesTabLayout));
        // Listen for scroll

    }
    }
