package com.andreganske.heartbeat.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andreganske.heartbeat.db.Heartbeat;
import com.andreganske.heartbeat.db.HeartbeatDBHelper;
import com.andreganske.heartbeat.R;

import java.util.Map;

public class HeartbeatActivity extends AppCompatActivity {

    private static final String TAG = "HeartbeatActivity";

    private Heartbeat mHeartbeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_heartbeat);

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                EditText systolic = (EditText) findViewById(R.id.systolic_value);
                EditText diastolic = (EditText) findViewById(R.id.diastolic_value);

                if (mHeartbeat != null) {

                    mHeartbeat.setSystolic(Integer.parseInt(systolic.getText().toString()));
                    mHeartbeat.setDiastolic(Integer.parseInt(diastolic.getText().toString()));

                    HeartbeatDBHelper.update(MainActivity.mHelper.getWritableDatabase(), mHeartbeat);

                } else {
                    mHeartbeat = new Heartbeat(
                            Integer.parseInt(systolic.getText().toString()),
                            Integer.parseInt(diastolic.getText().toString()));

                    HeartbeatDBHelper.insert(MainActivity.mHelper.getWritableDatabase(), mHeartbeat);
                }

                finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int id = extras.getInt("id");
            mHeartbeat = HeartbeatDBHelper.findById(MainActivity.mHelper.getReadableDatabase(), id);

            EditText systolic = (EditText) findViewById(R.id.systolic_value);
            systolic.setText(mHeartbeat.getSystolic().toString());

            EditText diastolic = (EditText) findViewById(R.id.diastolic_value);
            diastolic.setText(mHeartbeat.getDiastolic().toString());

            Map<String, String> map = mHeartbeat.getDateAndTime();

            TextView date = (TextView) findViewById(R.id.heartbeat_created_at_date);
            date.setText(map.get("date"));

            TextView time = (TextView) findViewById(R.id.heartbeat_created_at_time);
            time.setText(map.get("time"));
        }
    }
}
