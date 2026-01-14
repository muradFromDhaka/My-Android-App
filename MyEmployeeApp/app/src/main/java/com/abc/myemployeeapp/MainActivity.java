package com.abc.myemployeeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myemployeeapp.layout.EmployeeFormActivity;
import com.abc.myemployeeapp.layout.EmployeeListActivity;

public class MainActivity extends AppCompatActivity {

    Button btnList, btnForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnList = findViewById(R.id.btnList);
        btnForm =findViewById(R.id.btnForm);

        btnForm.setOnClickListener(v ->{
            Intent intent = new Intent(this, EmployeeFormActivity.class);
            startActivity(intent);
        });

        btnList.setOnClickListener(v ->{
            Intent intent = new Intent(this, EmployeeListActivity.class);
            startActivity(intent);
        });

    }
}