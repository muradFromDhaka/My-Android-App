package com.abc.myemployeeapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myemployeeapp.R;
import com.abc.myemployeeapp.db.EmployeeDao;
import com.abc.myemployeeapp.entity.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeFormActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etAge, etSalary;
    CheckBox etJava, etFlutter, etAndroid, etAngular;

    Spinner etDepartment, etActivity;
    Button btnSave;

    EmployeeDao employeeDao;
    long employeeId = -1; // add / update বোঝার জন্য

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔹 View binding
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);
        etActivity = findViewById(R.id.etActivity);
        etDepartment = findViewById(R.id.etDepartment);
        etJava = findViewById(R.id.etJava);
        etFlutter = findViewById(R.id.etFlutter);
        etAndroid = findViewById(R.id.etAndroid);
        etAngular = findViewById(R.id.etAngular);
        btnSave = findViewById(R.id.btnSave);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("HR", "Finance", "Marketing", "IT", "Research & Development"));
        etDepartment.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("Active", "Inactive"));
        etActivity.setAdapter(adapter2);

        employeeDao = new EmployeeDao(this);


        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String active = etActivity.getSelectedItem().toString();
            String department = etDepartment.getSelectedItem().toString();
//          String jDate = etJoiningDate.getDayOfMonth()+ "/" + (etJoiningDate.getMonth() + 1) + "/" + etJoiningDate.getYear();
            long joiningDate = System.currentTimeMillis();
            int age = etAge.getText().toString().isEmpty() ? 0 :
                    Integer.parseInt(etAge.getText().toString());

            double salary = etSalary.getText().toString().isEmpty() ? 0 :
                    Double.parseDouble(etSalary.getText().toString());


            List<String> skills = new ArrayList<>();
            if (etJava.isChecked()) skills.add("Java");
            if (etAngular.isChecked()) skills.add("Angular");
            if (etFlutter.isChecked()) skills.add("Flutter");
            if (etAndroid.isChecked()) skills.add("Android");

            String stringSkills = skills.isEmpty() ? " " : String.join(",", skills);

            Employee emp = new Employee(
                    name,
                    email,
                    phone,
                    age,
                    salary,
                    active,
                    joiningDate,
                    department,
                    stringSkills
            );

            long id = employeeDao.insertEmployee(emp);
            Toast.makeText(this, "Saved employee ID:" + id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EmployeeListActivity.class);
            startActivity(intent);
        });

        // 🔹 Update mode check
        if (getIntent().hasExtra("id")) {
            employeeId = getIntent().getLongExtra("id", -1);
            loadEmployee(employeeId);
            btnSave.setText("Update Employee");
        }

        // 🔹 Save / Update button
        btnSave.setOnClickListener(v -> saveEmployee());

    }



    // ================= SAVE OR UPDATE =================
    private void saveEmployee() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String department = etDepartment.getSelectedItem().toString();
        String active = etActivity.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Name and Email are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = etAge.getText().toString().isEmpty() ? 0 :
                Integer.parseInt(etAge.getText().toString());

        double salary = etSalary.getText().toString().isEmpty() ? 0 :
                Double.parseDouble(etSalary.getText().toString());

        // 🔹 Skills
        List<String> skills = new ArrayList<>();
        if (etJava.isChecked()) skills.add("Java");
        if (etAngular.isChecked()) skills.add("Angular");
        if (etFlutter.isChecked()) skills.add("Flutter");
        if (etAndroid.isChecked()) skills.add("Android");
        String skillString = skills.isEmpty() ? "" : String.join(",", skills);

        // 🔹 Joining date logic
        long joiningDate;
        if (employeeId == -1) {
            joiningDate = System.currentTimeMillis(); // add
        } else {
            joiningDate = employeeDao.getEmployeeById(employeeId).getJoiningDate(); // keep old
        }

        Employee emp = new Employee(
                name,
                email,
                phone,
                age,
                salary,
                active,
                joiningDate,
                department,
                skillString
        );

        if (employeeId == -1) {
            employeeDao.insertEmployee(emp);
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        } else {
            emp.setId(employeeId);
            employeeDao.updateEmployee(emp);
            Toast.makeText(this, "Employee Updated", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(this, EmployeeListActivity.class));
        finish();
    }

    // ================= LOAD EMPLOYEE FOR UPDATE =================
    private void loadEmployee(long id) {
        Employee e = employeeDao.getEmployeeById(id);
        if (e == null) return;

        etName.setText(e.getName());
        etEmail.setText(e.getEmail());
        etPhone.setText(e.getPhone());
        etAge.setText(String.valueOf(e.getAge()));
        etSalary.setText(String.valueOf(e.getSalary()));

        setSpinnerSelection(etDepartment, e.getDepartment());
        setSpinnerSelection(etActivity, e.getActive());

        if (e.getSkills() != null) {
            if (e.getSkills().contains("Java")) etJava.setChecked(true);
            if (e.getSkills().contains("Angular")) etAngular.setChecked(true);
            if (e.getSkills().contains("Flutter")) etFlutter.setChecked(true);
            if (e.getSkills().contains("Android")) etAndroid.setChecked(true);
        }
    }

    // ================= SPINNER HELPER =================
    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        int position = adapter.getPosition(value);
        spinner.setSelection(position);
    }


}
