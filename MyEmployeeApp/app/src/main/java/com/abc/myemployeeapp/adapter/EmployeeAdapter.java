package com.abc.myemployeeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.myemployeeapp.R;
import com.abc.myemployeeapp.entity.Employee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    // 🔹 Long click interface
    public interface OnEmployeeClick {
        void onLongClick(Employee employee);
    }

    private Context context;
    private List<Employee> employeeList;
    private OnEmployeeClick listener;

    // 🔹 Constructor
    public EmployeeAdapter(Context context, List<Employee> employeeList, OnEmployeeClick listener) {
        this.context = context;
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee e = employeeList.get(position);

        holder.tvName.setText(e.getName());
        holder.tvEmail.setText("email: " +e.getEmail());
        holder.tvDepartment.setText("Department: " +e.getDepartment());
        holder.tvSalary.setText("Salary: " + e.getSalary());

        long joiningDate = e.getJoiningDate();
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        String formattedDate = sdf.format(new Date(joiningDate));

        holder.tvJoiningDate.setText("Joining Date: " + formattedDate);


        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onLongClick(e);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return employeeList == null ? 0 : employeeList.size();
    }

    // 🔹 ViewHolder
    static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvDepartment, tvSalary, tvJoiningDate;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvJoiningDate = itemView.findViewById(R.id.tvJoiningDate);
        }
    }
}
