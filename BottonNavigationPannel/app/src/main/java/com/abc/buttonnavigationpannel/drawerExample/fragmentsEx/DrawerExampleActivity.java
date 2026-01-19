package com.abc.buttonnavigationpannel.drawerExample.fragmentsEx;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.abc.buttonnavigationpannel.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerExampleActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drawer_example);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar, R.string.open, R.string.close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item->{
            int id = item.getItemId();
            Fragment fragment = null;

            if(id== R.id.nav_home){
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
                fragment = new HomeFragment();
            }else if(id== R.id.nav_profile) {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                fragment = new ProfileFragment();
            }else if (id == R.id.nav_settings) {
                    Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                    fragment = new SettingsFragment();

            } else if (id == R.id.nav_logout) {
                    Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
                }


                if (fragment != null) {
                    loadFragment(fragment);
                }
                drawerLayout.closeDrawers();
                return true;
            });
        }

        private void loadFragment(Fragment fragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        public void setToolbarTitle(String title) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }

    }