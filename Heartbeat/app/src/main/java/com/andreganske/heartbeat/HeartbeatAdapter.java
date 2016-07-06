package com.andreganske.heartbeat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andre on 05/07/2016.
 */
public class HeartbeatAdapter extends ArrayAdapter<Heartbeat> {

    private static final String TAG = "HeartbeatAdapter";

    HeartbeatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    HeartbeatAdapter(Context context, int textViewResourceId, List<Heartbeat> heartbeats) {
        super(context, textViewResourceId, heartbeats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_heartbeat, null);
        }

        Heartbeat item = getItem(position);

        if (item != null) {
            TextView systolic = (TextView) view.findViewById(R.id.heartbeat_systolic);
            systolic.setText(item.getSystolic().toString());

            TextView diastolic = (TextView) view.findViewById(R.id.heartbeat_diastolic);
            diastolic.setText(item.getDiastolic().toString());

            TextView created_at = (TextView) view.findViewById(R.id.heartbeat_created_at);
            created_at.setText(item.getCreatedAt());

            TextView classification = (TextView) view.findViewById(R.id.heartbeat_classification);

            if (item.getSystolic() < 120 && item.getDiastolic() < 80) {
                view.setBackgroundColor(Color.GREEN);
                classification.setText("Normal");
            } else if (item.getSystolic() < 139 && item.getDiastolic() < 89) {
                view.setBackgroundColor(Color.GREEN);
                classification.setText("Pré-hipertensão");
            } else if (item.getSystolic() < 159 && item.getDiastolic() < 99) {
                view.setBackgroundColor(Color.YELLOW);
                classification.setText("Hipertensão nível 1");
            } else if (item.getSystolic() < 179 && item.getDiastolic() < 110) {
                view.setBackgroundColor(Color.YELLOW);
                classification.setText("Hipertensão nível 2");
            } else {
                view.setBackgroundColor(Color.RED);
                classification.setText("Crise hipertensíva!");
            }
        }

        return view;
    }
}
