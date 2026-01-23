package com.abc.campustrack.layout;

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

import com.abc.campustrack.R;
import com.abc.campustrack.adapters.CampusAdapter;
import com.abc.campustrack.db.CampusTrackDao;
import com.abc.campustrack.entity.CampusTrack;

import java.util.List;

public class CampusListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CampusAdapter campusAdapter;
    CampusTrackDao campusTrackDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_campus_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rvCampus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        campusTrackDao = new CampusTrackDao(this);

        // ✅ load first time
        loadCampus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ✅ refresh after create/update/delete
        loadCampus();
    }

    private void loadCampus() {
        List<CampusTrack> campusList = campusTrackDao.getCampusTrack();

        campusAdapter = new CampusAdapter(this, campusList, campus -> {
            String[] options = {"Update", "Delete"};

            new AlertDialog.Builder(this)
                    .setTitle("Select Action")
                    .setItems(options, (dialog, which) -> {

                        if (which == 0) {
                            Intent intent = new Intent(this, CampusCreateActivity.class);
                            intent.putExtra("campusId", campus.getId());
                            startActivity(intent);

                        } else if (which == 1) {
                            new AlertDialog.Builder(this)
                                    .setTitle("Delete Campus")
                                    .setMessage("Do you want to delete this campus?")
                                    .setPositiveButton("Yes", (d, w) -> {
                                        campusTrackDao.deleteCampus(campus.getId());
                                        loadCampus();
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    })
                    .show();
        });

        // ✅ THIS WAS MISSING
        recyclerView.setAdapter(campusAdapter);
    }
}
