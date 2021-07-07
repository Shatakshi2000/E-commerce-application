package Retailer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodstuff.Adapter;
import com.example.foodstuff.HomeFragment;
import com.example.foodstuff.Model;
import com.example.foodstuff.PlacedOrderFragment;
import com.example.foodstuff.R;
import com.example.foodstuff.RetailHomeFragment;
import com.example.foodstuff.SettingFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RetailerHomeActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new RetailHomeFragment()).commit();
        navigationView.setCheckedItem(R.id.menu_home);

        // creating drawer layout


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp1 = null;
                switch (item.getItemId()) {

                    case R.id.menu_home:
                        temp1 = new RetailHomeFragment();
                        break;
                    case R.id.menu_current_orders:
                        temp1 = new RetailerCurrentOrdersFragment();
                        break;
                    case R.id.menu_placed_orders:
                        temp1 = new PlacedOrderFragment();
                        break;
                    case R.id.menu_history:
                        temp1 = new RetailerHistoryFragment();
                        break;
                    case R.id.menu_settings:
                        temp1 = new SettingFragment();
                        break;
                    case R.id.menu_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,temp1).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // creating recycler view in the home page

    }


}
