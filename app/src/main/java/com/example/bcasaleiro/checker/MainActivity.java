package com.example.bcasaleiro.checker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private FloatingActionButton fab;

    private ArrayList<Task> myTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTasks = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton) findViewById(R.id.fab);

        rvAdapter = new TasksAdapter();

        ((TasksAdapter)rvAdapter).addItem(new Task("O casaleiro é fixe"), 0);
        ((TasksAdapter)rvAdapter).addItem(new Task("O mendes é conas"), 1);

        recyclerView.setAdapter(rvAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ((TasksAdapter)rvAdapter).addItemToEnd(new Task("O casaleiro é brutal"));
            }
        });

        final ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                if(getMovementFlags(recyclerView, viewHolder) != ItemTouchHelper.ACTION_STATE_DRAG) {
                    return true;
                }

                return false;
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                Log.d(DEBUGTAG, "Moved " + myTasks.get(fromPos) + " from: " + fromPos + " to: " + toPos);

                ((TasksAdapter) rvAdapter).moveItem(fromPos, toPos);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(DEBUGTAG, "Swiped " + myTasks.get(viewHolder.getAdapterPosition()).toString());
                ((TasksAdapter) rvAdapter).popItem(viewHolder.getAdapterPosition());
            }
        });

        mIth.attachToRecyclerView(recyclerView);

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
            Log.d(DEBUGTAG, "Binded Item: " + this.getItem(position).getName() + " at the position " + position);

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

        public void addItemToEnd(Task task) {
            myTasks.add(myTasks.size(), task);
            notifyItemInserted(myTasks.size() - 1);
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

        public void moveItem(int fromPos, int toPos) {
            Task task = myTasks.get(fromPos);
            myTasks.remove(fromPos);
            myTasks.add(toPos, task);

            notifyItemMoved(fromPos, toPos);
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
