package com.example.todolistapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTask;
    private Button btnAddTask;
    private RecyclerView recyclerView;

    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTask = findViewById(R.id.editTask);
        btnAddTask = findViewById(R.id.btnAddTask);
        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(this);
        taskList = dbHelper.getAllTasks();

        taskAdapter = new TaskAdapter(taskList, task -> {
            dbHelper.deleteTask(task.getId());
            taskList.remove(task);
            taskAdapter.notifyDataSetChanged();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> {
            String text = editTask.getText().toString().trim();
            if (!text.isEmpty()) {
                long id = dbHelper.addTask(text);
                Task newTask = new Task((int) id, text);
                taskList.add(0, newTask); // add to top
                taskAdapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
                editTask.setText("");
            }
        });
    }
}