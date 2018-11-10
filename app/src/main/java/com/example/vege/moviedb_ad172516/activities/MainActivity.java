package com.example.vege.moviedb_ad172516.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.fragments.popular.PopularFragment;
import com.example.vege.moviedb_ad172516.fragments.SearchFragment;
import com.example.vege.moviedb_ad172516.fragments.toprated.TopRatedFragment;
import com.example.vege.moviedb_ad172516.fragments.UpcomingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private int layoutID = R.id.mainContainer;
    private PopularFragment popularFragment = new PopularFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //R
        mBottomNavigationView = findViewById(R.id.navigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(layoutID, new PopularFragment()).commit();

        getWindow().setStatusBarColor(getResources().getColor(R.color.popularDark));

    }

    //BottomNavigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.navigation_popular:
                            selectedFragment = new PopularFragment();
                            //cambiara tambien el color del btmmnavview
                            mBottomNavigationView.setBackgroundColor(getResources().getColor(R.color.popular));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.popularDark));
                            break;
                        case R.id.navigation_topRated:
                            selectedFragment = new TopRatedFragment();
                            mBottomNavigationView.setBackgroundColor(getResources().getColor(R.color.toprated));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.topratedDark));
                            break;
                        case R.id.navigation_upcoming:
                            selectedFragment = new UpcomingFragment();
                            mBottomNavigationView.setBackgroundColor(getResources().getColor(R.color.upcoming));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.upcomingDark));
                            break;
                        case R.id.navigation_search:
                            selectedFragment = new SearchFragment();
                            mBottomNavigationView.setBackgroundColor(getResources().getColor(R.color.search));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.searchDark));
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(layoutID, selectedFragment).commit();

                    return true;

                }
            };

}
