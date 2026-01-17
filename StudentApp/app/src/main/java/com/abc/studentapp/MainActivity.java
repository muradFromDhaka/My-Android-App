package com.abc.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.studentapp.layout.StudentFormActivity;
import com.abc.studentapp.layout.StudentListActivity;

public class MainActivity extends AppCompatActivity {

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

        Button btnForm = findViewById(R.id.btnForm);
        Button btnList = findViewById(R.id.btnList);

        btnForm.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentFormActivity.class);
            startActivity(intent);
        });

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentListActivity.class);
            startActivity(intent);
        });
    }
}