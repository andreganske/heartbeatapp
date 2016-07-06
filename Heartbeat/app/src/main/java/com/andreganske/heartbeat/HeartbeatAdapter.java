package com.andreganske.heartbeat;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
                classification.setTextColor(ContextCompat.getColor(getContext(), R.color.greenLight));
                classification.setText("Normal");
            } else if (item.getSystolic() < 139 && item.getDiastolic() < 89) {
                classification.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                classification.setText("Pré-hipertensão");
            } else if (item.getSystolic() < 159 && item.getDiastolic() < 99) {
                classification.setTextColor(ContextCompat.getColor(getContext(), R.color.amber));
                classification.setText("Hipertensão nível 1");
            } else if (item.getSystolic() < 179 && item.getDiastolic() < 110) {
                classification.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentLight));
                classification.setText("Hipertensão nível 2");
            } else {
                classification.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentDark));
                classification.setText("Crise hipertensíva!");
            }
        }

        return view;
    }
}
