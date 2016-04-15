package com.example.alexf.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private ListView taskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tasks = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);

        taskView = (ListView) findViewById(R.id.task_list);

        taskView.setAdapter(adapter);

        taskView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    tasks.remove(position);
                                                    adapter.notifyDataSetChanged();
                                                    return false;
                                                }
                                            });
    }

    public void addTask(View view) {
        EditText newTask = (EditText) findViewById(R.id.new_task);
        String newTaskText = newTask.getText().toString();
        if (!newTaskText.equals("")) {
            tasks.add(newTaskText);
            adapter.notifyDataSetChanged();
            newTask.setText("");
        }
    }
}
