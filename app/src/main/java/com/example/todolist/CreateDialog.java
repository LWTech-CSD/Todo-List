package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class CreateDialog extends DialogFragment {
    private EditText itemText;
    private CalendarView datePicker;
    private int mm, dd, yy;

    private TodoItemListener listener;

    public interface TodoItemListener {
        void setItem(String text, String date);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_item_layout, container);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (TodoItemListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.create_item_layout, null);
        itemText = view.findViewById(R.id.item_text_input);
        datePicker = view.findViewById(R.id.date_picker);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datePicker.getDate());
        mm = cal.get(Calendar.MONTH) + 1;
        dd = cal.get(Calendar.DAY_OF_MONTH);
        yy = cal.get(Calendar.YEAR);
        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            dd = dayOfMonth;
            yy = year;
            mm = month + 1;
        });

        builder.setTitle("Todo Item");
        builder.setView(view);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String text = itemText.getText().toString();
            if (TextUtils.isEmpty(text)) {
                itemText.setError("ERROR");
            } else {
                listener.setItem(text, String.format("%02d/%02d/%d", mm, dd, yy));
            }
        });

        return builder.create();
    }
}
