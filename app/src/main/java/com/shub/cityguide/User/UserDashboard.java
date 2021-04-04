package com.shub.cityguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.shub.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.shub.cityguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.shub.cityguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.shub.cityguide.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    static final float END_SCALE = 0.7f;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featuredRecycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);


        //Menu Hooks

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);



       navigationDrawer();

        featuredRecycler();
    }

    //Navigation Drawer Functions
    private void navigationDrawer() {
    //        navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navigation_view);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setScrimColor(getResources().getColor((R.color.colorPrimary)));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    //Normal Function
    public void callRetailerScreens(View view){
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;
            case R.id.nav_login:startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));

                break;
        }

        return true;
    }

    // Recycler view functions
    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass>featuredLoacations = new ArrayList<>();
        featuredLoacations.add(new FeaturedHelperClass(R.drawable.macd_image,"Mcdonald's","(MCD) is a fast food, limited service restaurant in over 100 countries"));
        featuredLoacations.add(new FeaturedHelperClass(R.drawable.macd_image,"Edenrobe","(MCD) is a fast food, limited service restaurant in over 100 countries"));
        featuredLoacations.add(new FeaturedHelperClass(R.drawable.macd_image,"Sweet and Bakers","(MCD) is a fast food, limited service restaurant in over 100 countries"));

        adapter = new FeaturedAdapter(featuredLoacations);
        featuredRecycler.setAdapter(adapter);

    }


}