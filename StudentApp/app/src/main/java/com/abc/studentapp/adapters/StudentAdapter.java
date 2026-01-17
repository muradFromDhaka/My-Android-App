package com.abc.studentapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.studentapp.R;
import com.abc.studentapp.entity.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentVH> {

    private List<Student> list;

    public StudentAdapter(List<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_student, parent, false);
        return new StudentVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentVH holder, int position) {
        Student s = list.get(position);
        holder.tvName.setText(s.getName());
        holder.tvCourse.setText(s.getCourse());
        holder.tvEmail.setText(s.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class StudentVH extends RecyclerView.ViewHolder {

        TextView tvName, tvCourse, tvEmail;

        public StudentVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
