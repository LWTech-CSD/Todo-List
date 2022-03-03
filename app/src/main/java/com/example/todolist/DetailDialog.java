package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DetailDialog extends DialogFragment {
    private TextView deleteItemText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.delete_item_layout, null);
        builder.setView(view);
        deleteItemText = view.findViewById(R.id.delete_item_text);
        String itemText = getArguments().getString("item_text");
        deleteItemText.setText(String.format("Todo : '%s'", itemText));
        builder.setPositiveButton("OK", (v, which) -> {});
        return builder.create();
    }
}
