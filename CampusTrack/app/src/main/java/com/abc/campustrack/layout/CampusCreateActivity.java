package com.abc.campustrack.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.campustrack.R;
import com.abc.campustrack.db.CampusTrackDao;
import com.abc.campustrack.entity.CampusTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CampusCreateActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etPhone, etAddress, etGpa;
    private DatePicker dpDob;
    private RadioGroup rgGender;
    private Spinner spCourse;
    private CheckBox cbReading, cbSports, cbMusic;
    private CheckBox etJava, etAngular, etFlutter, etAndroid, etSQL;
    private CheckBox cbTerms;
    private Button btnSubmit;

    private CampusTrackDao campusTrackDao;
    private long campusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_campus_create);

        // ---------- Bind Views ----------
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etGpa = findViewById(R.id.etGpa);

        dpDob = findViewById(R.id.dpDob);
        rgGender = findViewById(R.id.rgGender);
        spCourse = findViewById(R.id.spCourse);

        cbReading = findViewById(R.id.cbReading);
        cbSports = findViewById(R.id.cbSports);
        cbMusic = findViewById(R.id.cbMusic);

        etJava = findViewById(R.id.etJava);
        etAngular = findViewById(R.id.etAngular);
        etFlutter = findViewById(R.id.etFlutter);
        etAndroid = findViewById(R.id.etAndroid);
        etSQL = findViewById(R.id.etSQL);

        cbTerms = findViewById(R.id.cbTerms);
        btnSubmit = findViewById(R.id.btnSubmit);

        // ---------- DAO ----------
        campusTrackDao = new CampusTrackDao(this);

        // ---------- Spinner ----------
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("MSc", "BSc", "BBA", "MBA", "MA")
        );
        spCourse.setAdapter(courseAdapter);

        // ---------- Get Intent ----------
        campusId = getIntent().getLongExtra("campusId", -1);

        if (campusId != -1) {
            loadCampusForUpdate(campusId);
            btnSubmit.setText("Update");
        }

        // ---------- Submit ----------
        btnSubmit.setOnClickListener(v -> saveCampus());
    }

    private void saveCampus() {

        CampusTrack campus = new CampusTrack();

        campus.setName(etName.getText().toString().trim());
        campus.setEmail(etEmail.getText().toString().trim());
        campus.setPassword(etPassword.getText().toString().trim());
        campus.setPhone(etPhone.getText().toString().trim());
        campus.setAddress(etAddress.getText().toString().trim());

        float gpa = etGpa.getText().toString().isEmpty()
                ? 0f
                : Float.parseFloat(etGpa.getText().toString());
        campus.setGpa(gpa);

        String dob = dpDob.getDayOfMonth() + "/"
                + (dpDob.getMonth() + 1) + "/"
                + dpDob.getYear();
        campus.setDateOfBirth(dob);

        int genderId = rgGender.getCheckedRadioButtonId();
        String gender = genderId == -1
                ? "Not selected"
                : ((RadioButton) findViewById(genderId)).getText().toString();
        campus.setGender(gender);

        campus.setCourse(spCourse.getSelectedItem().toString());

        // ---------- Hobbies ----------
        List<String> hobbies = new ArrayList<>();
        if (cbReading.isChecked()) hobbies.add("Reading");
        if (cbSports.isChecked()) hobbies.add("Sports");
        if (cbMusic.isChecked()) hobbies.add("Music");
        campus.setHobbies(hobbies);

        // ---------- Skills ----------
        List<String> skills = new ArrayList<>();
        if (etJava.isChecked()) skills.add("Java");
        if (etAngular.isChecked()) skills.add("Angular");
        if (etAndroid.isChecked()) skills.add("Android");
        if (etFlutter.isChecked()) skills.add("Flutter");
        if (etSQL.isChecked()) skills.add("SQL");

        campus.setSkills(skills.isEmpty() ? "" : String.join(",", skills));

        campus.setTermsAccepted(cbTerms.isChecked());

        if (campusId == -1) {
            campusTrackDao.insertCampus(campus);
            Toast.makeText(this, "Campus added", Toast.LENGTH_SHORT).show();
        } else {
            campus.setId(campusId);
            campusTrackDao.updateCampus(campusId, campus);
            Toast.makeText(this, "Campus updated", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(this, CampusListActivity.class));
        finish();
    }

    private void loadCampusForUpdate(long id) {
        CampusTrack campus = campusTrackDao.getCamusTrackById(id);

        etName.setText(campus.getName());
        etEmail.setText(campus.getEmail());
        etPassword.setText(campus.getPassword());
        etPhone.setText(campus.getPhone());
        etAddress.setText(campus.getAddress());
        etGpa.setText(String.valueOf(campus.getGpa()));

        // Gender
        if ("Male".equals(campus.getGender()))
            ((RadioButton) findViewById(R.id.rbMale)).setChecked(true);
        else if ("Female".equals(campus.getGender()))
            ((RadioButton) findViewById(R.id.rbFemale)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.rbOthers)).setChecked(true);

        // Course
        setSpinner(spCourse, campus.getCourse());

        // Hobbies
        cbReading.setChecked(campus.getHobbies().contains("Reading"));
        cbSports.setChecked(campus.getHobbies().contains("Sports"));
        cbMusic.setChecked(campus.getHobbies().contains("Music"));

        // Skills
        etJava.setChecked(campus.getSkills().contains("Java"));
        etAngular.setChecked(campus.getSkills().contains("Angular"));
        etAndroid.setChecked(campus.getSkills().contains("Android"));
        etFlutter.setChecked(campus.getSkills().contains("Flutter"));
        etSQL.setChecked(campus.getSkills().contains("SQL"));

        // Terms
        cbTerms.setChecked(campus.isTermsAccepted());
    }

    private void setSpinner(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        spinner.setSelection(adapter.getPosition(value));
    }
}
