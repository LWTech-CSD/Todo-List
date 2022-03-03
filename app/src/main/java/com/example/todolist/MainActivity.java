package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements CreateDialog.TodoItemListener {

    RecyclerView todoList;
    TextView emptyTextMsg;
    TodoDBHelper dbHelper;
    TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = findViewById(R.id.todo_list);
        todoList.setLayoutManager(new LinearLayoutManager(this));
        emptyTextMsg = findViewById(R.id.empty_text);

        dbHelper = new TodoDBHelper(getApplicationContext(),
                TodoDBHelper.DB_NAME,
                null,
                TodoDBHelper.VERSION);

        adapter = new TodoListAdapter(getSupportFragmentManager(), emptyTextMsg, dbHelper);

        if (adapter.todoItems.size() > 0) {
            emptyTextMsg.setVisibility(View.INVISIBLE);
        }

        todoList.setAdapter(adapter);

        CreateDialog createDialog = new CreateDialog();

        FloatingActionButton fab = findViewById(R.id.add_item_btn);
        fab.setOnClickListener((v) -> createDialog.show(getSupportFragmentManager(), "Create an Item"));
    }

    @Override
    public void setItem(String text, String date) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Text or Date was empty. No item saved.", Toast.LENGTH_SHORT).show();
        } else {
            int id = dbHelper.insertItem(text, date);
            adapter.todoItems.add(new TodoItem(id, text, date, false));
            adapter.notifyItemInserted(adapter.todoItems.size());
            emptyTextMsg.setVisibility(View.INVISIBLE);
        }
    }
}