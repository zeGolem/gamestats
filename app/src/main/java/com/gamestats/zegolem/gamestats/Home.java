package com.gamestats.zegolem.gamestats;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = findViewById(R.id.drawer_layout);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        final FragmentManager fragmentManager = this.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment HomeFrag = new HomeFragment();
        final Fragment OverwatchFrag = new OverwatchFragment();
        //fragmentTransaction.add(R.id.fragment_container, HomeFrag, "home");
        //fragmentTransaction.commitNow();
        //fragmentManager.executePendingTransactions();

        displayFragment(HomeFrag, "", "home");



        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // System.out.println("========\nPRESSED : " + menuItem.getTitle());

                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
//                                FragmentManager fm = getSupportFragmentManager();
//                                FragmentTransaction ft = fm.beginTransaction();
//                                // fragmentTransaction.remove(fragmentManager.findFragmentByTag("overwatch")).commitNow();
//                                ft.replace(R.id.fragment_container, HomeFrag, "home");
//                                ft.commit();
//                                fm.executePendingTransactions();
                                displayFragment(HomeFrag, "overwatch", "home");
                                break;
                            case R.id.nav_overwatch:
//                                FragmentManager fm = getSupportFragmentManager();
//                                FragmentTransaction ft = fm.beginTransaction();
//                                Fragment old = fm.findFragmentByTag("home");
//                                if(old!=null)
//                                    ft.remove(old).commit();
//                                ft.replace(R.id.fragment_container, OverwatchFrag, "overwatch");
//                                ft.commit();
//                                fm.executePendingTransactions();
                                displayFragment(OverwatchFrag, "home", "overwatch");
                                break;

                        }


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }


                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        //Overwatch



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayFragment(Fragment newFragment, String oldFragmentTag, String newFragmentTag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, newFragmentTag);
        ft.commitAllowingStateLoss();

    }


}
