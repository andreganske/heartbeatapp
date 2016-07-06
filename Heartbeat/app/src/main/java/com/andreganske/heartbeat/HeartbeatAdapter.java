package com.andreganske.heartbeat;

import android.content.Context;
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
            TextView value = (TextView) view.findViewById(R.id.heartbeat_value);
            value.setText(item.getSystolic() + "x" + item.getDiastolic());

            TextView created_at = (TextView) view.findViewById(R.id.heartbeat_created_at);
            created_at.setText(item.getCreatedAt());
        }

        return view;
    }
}
