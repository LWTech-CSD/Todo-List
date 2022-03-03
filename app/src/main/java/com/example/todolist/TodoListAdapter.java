package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    public ArrayList<TodoItem> todoItems;
    private DetailDialog detailDialog;
    private FragmentManager fragmentManager;
    private TodoDBHelper dbHelper;
    private TextView emptyTextView;

    public TodoListAdapter(FragmentManager fragmentManager,
                           TextView emptyTextView,
                           TodoDBHelper dbHelper) {
        // CompletableFutures
        this.todoItems = dbHelper.getAllItems(); // could be slow
        this.fragmentManager = fragmentManager;
        this.dbHelper = dbHelper;
        this.emptyTextView = emptyTextView;
    }

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_layout, null);
        return new TodoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
        TodoItem item = todoItems.get(position);
        holder.doneCheckBox.setChecked(item.isDone());
        holder.itemTextView.setText(item.getText());
        holder.dateTextView.setText(item.getDate());

        detailDialog = new DetailDialog();
        holder.itemView.setOnLongClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("item_text", todoItems.get(position).getText());
            detailDialog.setArguments(bundle);
            detailDialog.show(fragmentManager, "details");
            return position == 1;
        });

        holder.deleteBtn.setOnClickListener(v -> {
            TodoItem itemToDelete = todoItems.get(holder.getAdapterPosition());
            if (dbHelper.deleteItem(itemToDelete.getId())) {
                todoItems.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
            }
            if (todoItems.size() == 0) {
                emptyTextView.setVisibility(View.VISIBLE);
            }
        });

        holder.doneCheckBox.setOnCheckedChangeListener((v, checked) -> {
            dbHelper.setDone(todoItems.get(holder.getAdapterPosition()).getId(), checked);
        });
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        CheckBox doneCheckBox;
        TextView dateTextView;
        ImageButton deleteBtn;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.item_text);
            doneCheckBox = itemView.findViewById(R.id.item_checkbox);
            dateTextView = itemView.findViewById(R.id.item_date);
            deleteBtn = itemView.findViewById(R.id.delete_item_btn);
        }
    }
}
