package com.example.donapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import RecipientBottomNavigationFragment.HomeFragment;
import RecipientBottomNavigationFragment.NotificationFragment;
import RecipientBottomNavigationFragment.SettingsFragment;

public class RecipientMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_main_screen);

        BottomNavigationView bottomNav = findViewById(R.id.rbottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //WHEN THIS STATEMENT IS NOT PRESENT, A BLANK WHITE SCREEN WILL BE SHOWN BECAUSE NONE OF THE FRAGMENTS ARE CLICKED RIGHT NOW...
        //BY DEFAULT WE SELECT HOME FRAGMENT ON OPENING THE APP...
        getSupportFragmentManager().beginTransaction().replace(R.id.rfragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_rhome:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_rnotifications:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.nav_rsettings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    //THIS METHOD DOES THE TASK OF WHAT TO SHOW WHEN ANY OF THE FRAGMENTS IS CLICKED...
                    //HERE "rfragment container" is the id of the Recipient Main Screen xml which is place where to show the changes...
                    getSupportFragmentManager().beginTransaction().replace(R.id.rfragment_container,
                            selectedFragment).commit();

                    return true;
                }

            };
}
