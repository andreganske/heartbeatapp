package com.andreganske.heartbeat.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andreganske.heartbeat.db.Heartbeat;
import com.andreganske.heartbeat.adapter.HeartbeatAdapter;
import com.andreganske.heartbeat.db.HeartbeatDBHelper;
import com.andreganske.heartbeat.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    protected static HeartbeatDBHelper mHelper;
    private HeartbeatAdapter mAdapter;

    private ListView mTaskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mHelper = new HeartbeatDBHelper(this);

        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                mTaskListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                mTaskListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        mode.setTitle(String.valueOf(mTaskListView.getCheckedItemCount()));
                        mAdapter.toggleSelection(position);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.single_selection_context_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                        SparseBooleanArray selected = mAdapter.getSelectedIds();

                        if (item.getItemId() == R.id.menu_edit) {

                            Log.d(TAG, "item " + mAdapter.getItem(selected.keyAt(0)).toString() + " to edit!");

                        } else if (item.getItemId() == R.id.menu_delete) {

                            List<Heartbeat> heartbeats = new ArrayList<Heartbeat>();

                            short size = (short) selected.size();
                            for (byte i = 0; i < size; i++) {
                                if (selected.valueAt(i)) {
                                    heartbeats.add(mAdapter.getItem(selected.keyAt(i)));
                                }
                            }

                            HeartbeatDBHelper.remove(mHelper.getReadableDatabase(), heartbeats);

                            mode.finish();
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        mAdapter.resetSelectedIds();
                        updateUI();
                    }
                });

                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HeartbeatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        updateUI();
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI () {
        List<Heartbeat> heartbeats = HeartbeatDBHelper.fetchAllTasks(mHelper.getReadableDatabase());

        if (mAdapter == null) {
            mAdapter = new HeartbeatAdapter(this, R.layout.item_heartbeat, heartbeats);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(heartbeats);
            mAdapter.notifyDataSetChanged();
        }
    }

}
