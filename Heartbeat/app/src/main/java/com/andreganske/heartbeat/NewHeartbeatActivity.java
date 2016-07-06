package com.andreganske.heartbeat;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewHeartbeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_heartbeat);

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                EditText systolic = (EditText) findViewById(R.id.systolic_value);
                EditText diastolic = (EditText) findViewById(R.id.diastolic_value);

                Heartbeat heartbeat = new Heartbeat(
                        Integer.parseInt(systolic.getText().toString()),
                        Integer.parseInt(diastolic.getText().toString()));

                HeartbeatDBHelper.insertTask(MainActivity.mHelper.getWritableDatabase(), heartbeat);

                Snackbar.make(view, "Medição salva com sucesso", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                finish();
            }
        });
    }
}
