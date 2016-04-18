package com.example.alexf.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ListActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tasks = new ArrayList<String>();

        try {
            Scanner scan = new Scanner(openFileInput("tasks.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                tasks.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        ListView taskView = (ListView) findViewById(R.id.task_list);
        if (taskView != null) {
            taskView.setAdapter(adapter);

            taskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    tasks.remove(position);
                    save();
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    public void addTask(View view) {
        EditText newTask = (EditText) findViewById(R.id.new_task);
        String newTaskText;
        if (newTask != null) {
            newTaskText = newTask.getText().toString();
            if (!newTaskText.equals("")) {
                tasks.add(newTaskText);
                save();
                adapter.notifyDataSetChanged();
                newTask.setText("");
            }
        }
    }


    private void save() {
        try {
            PrintStream out = new PrintStream(openFileOutput("tasks.txt", MODE_PRIVATE));
            for (int i = 0; i < tasks.size(); i++) {
                out.println(tasks.get(i));
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
