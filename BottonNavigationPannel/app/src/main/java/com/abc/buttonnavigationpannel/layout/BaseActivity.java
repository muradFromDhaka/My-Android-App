package com.abc.buttonnavigationpannel.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.buttonnavigationpannel.MainActivity;
import com.abc.buttonnavigationpannel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

   public void setupBase(int layoutResId, int selectedItemId){
       setContentView(R.layout.activity_base);

       getLayoutInflater().inflate(
               layoutResId,
               findViewById(R.id.contentContainer),
               true
       );

       BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
       bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item->{
            int id = item.getItemId();
            if(id==selectedItemId) return true;

            Intent intent = null;

            if(id==R.id.nav_home){
                intent= new Intent(this, MainActivity.class);
            }else if(id==R.id.nav_first){
                intent= new Intent(this, FirstActivity.class);
            }else if(id==R.id.nav_second){
                intent= new Intent(this, SecondActivity.class);
            }else if(id==R.id.nav_third){
                intent= new Intent(this, ThirdActivity.class);
            }else if (id==R.id.nav_fourth){
                intent= new Intent(this, FourthActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish();
            }
            return true;

        });
    }
}