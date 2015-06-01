package com.example.bcasaleiro.checker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bcasaleiro.checker.Models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private final String DEBUGTAG = "MainAcivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;

    private ArrayList<Task> myTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTasks = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rvAdapter = new TasksAdapter();

        ((TasksAdapter)rvAdapter).addItem(new Task("O casaleiro é fixe"), 0);
        ((TasksAdapter)rvAdapter).addItem(new Task("O mendes é conas"), 1);

        recyclerView.setAdapter(rvAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class TasksAdapter extends RecyclerView.Adapter<ViewHolderItem> {

        private View view;

        @Override
        public ViewHolderItem onCreateViewHolder(ViewGroup viewGroup, int position) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_tasks, viewGroup, false);
            return new ViewHolderItem(view);
        }

        @Override
        public void onBindViewHolder(ViewHolderItem viewHolderItem, int position) {
            Log.d(DEBUGTAG, "Item: " + this.getItem(position).getName() + " at the position " + position);

            viewHolderItem.setNameTextView(this.getItem(position).getName());
        }

        @Override
        public int getItemCount() {
            return myTasks.size();
        }

        public Task getItem(int position) {
            return myTasks.get(position);
        }

        public void addItem(Task task, int position) {
            myTasks.add(position, task);
            notifyItemInserted(position);
        }

        public Task popItem(int position) {
            Task task = myTasks.get(position);
            myTasks.remove(position);
            notifyItemRemoved(position);

            return task;
        }

        public Task popItem(Task task) {
            int position = myTasks.indexOf(task);
            Task auxTask = myTasks.get(position);
            myTasks.remove(position);
            notifyItemRemoved(position);

            return auxTask;
        }
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder{

        private View parent;
        private TextView nameTextView;

        public ViewHolderItem(View itemView) {
            super(itemView);
            this.parent = itemView;
            this.nameTextView = (TextView) parent.findViewById(R.id.task_name);

        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public void setNameTextView(String nameTextView) {
            this.nameTextView.setText(nameTextView);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
