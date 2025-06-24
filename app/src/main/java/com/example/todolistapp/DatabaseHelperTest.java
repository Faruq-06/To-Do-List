package com.example.todolistapp;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {

    private DatabaseHelper dbHelper;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        dbHelper = new DatabaseHelper(context);
    }

    @After
    public void tearDown() {
        // Close database and delete to ensure test isolation
        dbHelper.close();
        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Test
    public void insertTask_returnsValidId() {
        long id = dbHelper.addTask("Write JUnit test");
        assertTrue("ID should be > 0", id > 0);
    }

    @Test
    public void getAllTasks_returnsTasks() {
        dbHelper.addTask("Prepare test");
        List<Task> tasks = dbHelper.getAllTasks();
        assertFalse("Task list should not be empty", tasks.isEmpty());
    }

    @Test
    public void getAllTasks_emptyDatabase() {
        List<Task> tasks = dbHelper.getAllTasks();
        assertTrue("Task list should be empty", tasks.isEmpty());
    }

    @Test
    public void deleteTask_removesCorrectTask() {
        // Create and verify task exists
        long id = dbHelper.addTask("Delete me");
        assertTrue("Task should exist before deletion", taskExists(id));

        // Delete and verify removal
        dbHelper.deleteTask(id); // Use long version
        assertFalse("Task should not exist after deletion", taskExists(id));
    }

    @Test
    public void updateTask_modifiesContent() {
        // Create task
        long id = dbHelper.addTask("Original text");

        // Update and verify
        dbHelper.updateTask(id, "Updated text");
        Task task = getTaskById(id);
        assertEquals("Task content should be updated", "Updated text", task.getContent());
    }

    // Helper method to check task existence
    private boolean taskExists(long id) {
        for (Task task : dbHelper.getAllTasks()) {
            if (task.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Helper method to get task by ID
    private Task getTaskById(long id) {
        for (Task task : dbHelper.getAllTasks()) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
}