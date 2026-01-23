package com.abc.studentapp.layout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abc.studentapp.R;
import com.abc.studentapp.db.StudentDao;
import com.abc.studentapp.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentFormActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etAge, etDob, etPhone,
            etAddress, etDepartment, etSkills;
    RadioGroup rgGender;
    Spinner spCourse;
    CheckBox cbReading, cbSports, cbMusic, cbTerms;
    Button btnSubmit;

    StudentDao dao;
    long studentId = -1; // default: insert mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        dao = new StudentDao(this);

        initViews();
        setupSpinner();

        // 🔹 Check if edit mode
        if (getIntent().hasExtra("STUDENT_ID")) {
            studentId = getIntent().getLongExtra("STUDENT_ID", -1);
            loadStudentForEdit(studentId);
        }

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        etDob = findViewById(R.id.etDob);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etDepartment = findViewById(R.id.etDepartment);
        etSkills = findViewById(R.id.etSkills);

        rgGender = findViewById(R.id.rgGender);
        spCourse = findViewById(R.id.spCourse);

        cbReading = findViewById(R.id.cbReading);
        cbSports = findViewById(R.id.cbSports);
        cbMusic = findViewById(R.id.cbMusic);
        cbTerms = findViewById(R.id.cbTerms);

        btnSubmit = findViewById(R.id.btnSubmit);

    }

    private void setupSpinner() {
        String[] courses = {"Select Course", "BSc in CSE", "BBA", "MBA"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCourse.setAdapter(adapter);
    }

    private void loadStudentForEdit(long id) {
        Student s = dao.getStudentById(id);
        if (s == null) return;

        etName.setText(s.getName());
        etEmail.setText(s.getEmail());
        etPassword.setText(s.getPassword());
        etAge.setText(String.valueOf(s.getAge()));
        etDob.setText(s.getDateOfBirth());
        etPhone.setText(s.getPhone());
        etAddress.setText(s.getAddress());
        etDepartment.setText(s.getDepartment());
        etSkills.setText(s.getSkills());

        if (s.getGender().equals("Male")) rgGender.check(R.id.rbMale);
        else rgGender.check(R.id.rbFemale);

        // hobbies
        cbReading.setChecked(false);
        cbSports.setChecked(false);
        cbMusic.setChecked(false);
        for (String h : s.getHobbies()) {
            if (h.equals("Reading")) cbReading.setChecked(true);
            if (h.equals("Sports")) cbSports.setChecked(true);
            if (h.equals("Music")) cbMusic.setChecked(true);
        }

        cbTerms.setChecked(s.isTermsAccepted());
        btnSubmit.setText("Update Student");
    }

    private void submitForm() {
        // basic validation
        if (etName.getText().toString().isEmpty()) {
            etName.setError("Required");
            return;
        }
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Accept terms first", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = rgGender.getCheckedRadioButtonId() == R.id.rbMale ? "Male" : "Female";

        List<String> hobbies = new ArrayList<>();
        if (cbReading.isChecked()) hobbies.add("Reading");
        if (cbSports.isChecked()) hobbies.add("Sports");
        if (cbMusic.isChecked()) hobbies.add("Music");

        Student s = new Student(
                etName.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                Integer.parseInt(etAge.getText().toString()),
                gender,
                etDob.getText().toString(),
                etPhone.getText().toString(),
                etAddress.getText().toString(),
                spCourse.getSelectedItem().toString(),
                etDepartment.getText().toString(),
                etSkills.getText().toString(),
                hobbies,
                cbTerms.isChecked()
        );

        if (studentId == -1) {
            dao.insertStudent(s);
            Toast.makeText(this, "Student Registered Successfully", Toast.LENGTH_LONG).show();
        } else {
            dao.updateStudent(studentId, s);
            Toast.makeText(this, "Student Updated Successfully", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
