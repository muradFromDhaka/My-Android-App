package com.abc.campustrack.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.campustrack.R;
import com.abc.campustrack.entity.CampusTrack;

import java.util.List;

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.CampusViewHolder> {

    // 🔹 Click interface (PascalCase)
    public interface OnCampusClick {
        void onLongClick(CampusTrack campus);
    }

    private final Context context;
    private final List<CampusTrack> campusList;
    private final OnCampusClick listener;

    // 🔹 Constructor
    public CampusAdapter(Context context,
                         List<CampusTrack> campusList,
                         OnCampusClick listener) {
        this.context = context;
        this.campusList = campusList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CampusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_campus, parent, false);
        return new CampusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampusViewHolder holder, int position) {
        CampusTrack campus = campusList.get(position);

        holder.tvName.setText("Name: " + campus.getName());
        holder.tvEmail.setText("Email: " + campus.getEmail());
        holder.tvCourse.setText("Course: " + campus.getCourse());
        holder.tvGpa.setText("GPA: " + campus.getGpa());
        holder.tvDob.setText("DOB: " + campus.getDateOfBirth());
        holder.tvGender.setText("Gender: " + campus.getGender());
        holder.tvSkills.setText("Skills: " + campus.getSkills());

        // ✅ Safe hobbies handling (no crash, works on all Android versions)
        String hobbies = campus.getHobbies() == null || campus.getHobbies().isEmpty()
                ? "None"
                : TextUtils.join(", ", campus.getHobbies());

        holder.tvHobbies.setText("Hobbies: " + hobbies);

        // ✅ Long click callback
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onLongClick(campus);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return campusList == null ? 0 : campusList.size();
    }

    // 🔹 ViewHolder
    static class CampusViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvCourse, tvGpa, tvDob, tvGender, tvHobbies, tvSkills;

        public CampusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvGpa = itemView.findViewById(R.id.tvGpa);
            tvDob = itemView.findViewById(R.id.tvDob);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvHobbies = itemView.findViewById(R.id.tvHobbies);
            tvSkills = itemView.findViewById(R.id.tvSkills);
        }
    }
}
