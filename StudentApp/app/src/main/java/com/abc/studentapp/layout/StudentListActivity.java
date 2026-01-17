package com.abc.studentapp.layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.studentapp.R;
import com.abc.studentapp.adapters.StudentAdapter;
import com.abc.studentapp.db.StudentDao;
import com.abc.studentapp.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter adapter;
    StudentDao studentDao;

    List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rvStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentDao = new StudentDao(this);

        // 🔹 Init adapter ONCE
        adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        loadStudents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents(); // refresh when coming back
    }

    // 🔹 Reload data only
    private void loadStudents() {
        studentList.clear();
        studentList.addAll(studentDao.getAllStudent());
        adapter.notifyDataSetChanged();
    }

    // 🔹 Update / Delete dialog
    private void showActionDialog(Student student) {

        String[] options = {"Update", "Delete"};

        new AlertDialog.Builder(this)
                .setTitle("Select Action")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        // ✏️ Update
                        Intent intent =
                                new Intent(this, StudentFormActivity.class);
                        intent.putExtra("studentId", student.getId());
                        startActivity(intent);

                    } else {
                        // 🗑 Delete
                        new AlertDialog.Builder(this)
                                .setTitle("Delete Student")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes", (d, w) -> {
                                    studentDao.deleteStudent(student.getId());
                                    Toast.makeText(
                                            this,
                                            "Student Deleted",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    loadStudents();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                })
                .show();
    }
}
