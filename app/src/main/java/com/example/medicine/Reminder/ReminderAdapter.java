package com.example.medicine.Reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.R;
import com.example.medicine.SQLConnect;
import com.example.medicine.databinding.ReminderItemRowBinding;

import java.sql.Connection;
import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    ArrayList<Reminder> reminders;
    Context context;
    TextView tvName, tvCategory, tvNumber, tvDate, tvTime;
    ReminderItemRowBinding reminderItemRowBinding;
    LinearLayout layoutReminder;
    Connection connection = null;


    public ReminderAdapter(ArrayList<Reminder> reminders, Context context) {
        this.reminders = reminders;
        this.context = context;
    }

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        reminderItemRowBinding = ReminderItemRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(reminderItemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, final int position) {
        Reminder reminder = reminders.get(position);
        holder.bind(reminder);
        connection = SQLConnect.setConnection();
        final int id = reminder.idReminder;

        layoutReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog("Hoàn thành?", "Bạn có muốn hoàn thành công việc", R.drawable.ic_info, id, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ReminderItemRowBinding reminderItemRowBinding) {
            super(reminderItemRowBinding.getRoot());

            tvName = reminderItemRowBinding.tvName;
            tvCategory = reminderItemRowBinding.tvCategory;
            tvNumber = reminderItemRowBinding.tvNum;
            tvDate = reminderItemRowBinding.tvDate;
            tvTime = reminderItemRowBinding.tvTime;
            layoutReminder = reminderItemRowBinding.layoutReminder;
        }

        public void bind(final Reminder reminder) {
            tvName.setText(reminder.name);
            tvCategory.setText(reminder.category);
            tvNumber.setText(reminder.num);
            tvDate.setText(reminder.date);
            tvTime.setText(reminder.time);
        }
    }


    public void createDialog(String title, String message, int icon, final int id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SQLConnect.updateReminder(id);
                reminders.remove(position);
                notifyDataSetChanged();
                dialogInterface.cancel();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
