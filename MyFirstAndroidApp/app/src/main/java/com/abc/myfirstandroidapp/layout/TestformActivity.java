package com.abc.myfirstandroidapp.layout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.db.UserDao;
import com.abc.myfirstandroidapp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class TestformActivity extends AppCompatActivity {

    EditText tvName, tvEmail, tvPass, tvPhone;
   CheckBox tvJava, tvAngular, tvAndroid, tvFlutter, tvTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_testform);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPass = findViewById(R.id.tvPass);
        tvPhone = findViewById(R.id.tvPhone);
        tvJava = findViewById(R.id.tvJava);
        tvAndroid = findViewById(R.id.tvAndroid);
        tvFlutter = findViewById(R.id.tvFlutter);
        tvAngular = findViewById(R.id.tvAngular);
        tvTerm = findViewById(R.id.tvTerm);
        RadioGroup tvGender = findViewById(R.id.tvGender);
        DatePicker tvDate = findViewById(R.id.tvDate);
        Spinner tvCountry = findViewById(R.id.tvCountry);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        TextView btnLogin = findViewById(R.id.btnLogin);

        btnSubmit.setOnClickListener(v ->{
            String name = tvName.getText().toString().trim();
            String email = tvEmail.getText().toString().trim();
            String pass = tvPass.getText().toString().trim();
            String phone = tvPhone.getText().toString().trim();

            int selectedGenderId = tvGender.getCheckedRadioButtonId();
            String gender = selectedGenderId == -1 ? "Not Selected" : ((RadioButton) findViewById(selectedGenderId)).getText().toString();

            String dob = tvDate.getDayOfMonth() + "/" + (tvDate.getMonth()+1) + "/" + tvDate.getYear();

            String[] countries = {"Bangladesh", "USA","UK","Pakisthan"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item,countries);
            tvCountry.setAdapter(adapter);

            String country = tvCountry.getSelectedItem().toString();

            List<String> skills = new ArrayList<>();
            if(tvJava.isChecked()) skills.add("Java");
            if(tvAngular.isChecked()) skills.add("Angular");
            if(tvFlutter.isChecked()) skills.add("Flutter");
            if(tvAndroid.isChecked()) skills.add("Android");
            String skillString = skills.isEmpty() ? "Not Selected" : String.join("|", skills);

            boolean terms = tvTerm.isChecked();

            UserDao userDao = new UserDao(this);
            User user = new User(name,email,pass,phone,gender,dob,country,skillString,terms);

            long id = userDao.insertUser(user);
            Toast.makeText(this, "Saved user ID: " +id, Toast.LENGTH_SHORT).show();

        });

    }
}