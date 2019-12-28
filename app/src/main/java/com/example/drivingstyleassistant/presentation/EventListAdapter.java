package com.example.drivingstyleassistant.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.domain.entities.Events;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Events> {

    int itemId;
    ArrayList<Events> events;
    LayoutInflater mInflater;
    Context context;

    public EventListAdapter(Context context, int itemId, ArrayList<Events> events) {
        super(context, itemId, events);
        this.events = events;
        this.itemId = itemId;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if(convertView == null) {
            View view = mInflater.inflate(itemId, parent, false);
            viewHolder = ViewHolder.create((LinearLayout) view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Events events = getItem(position);
        viewHolder.date.setText(events.getTimestamp().toString());
        viewHolder.speed.setText(String.valueOf(events.getSpeed()));
        viewHolder.value.setText(String.valueOf(events.getgForce()));
        viewHolder.type.setText(events.getEventType().toString());

        return viewHolder.rootView;
    }

    public static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView date;
        public final TextView speed;
        public final TextView value;
        public final TextView type;

        public ViewHolder(LinearLayout rootView, TextView date, TextView speed, TextView value, TextView type) {
            this.rootView = rootView;
            this.date = date;
            this.speed = speed;
            this.value = value;
            this.type = type;
        }

        public static ViewHolder create (LinearLayout rootView){
            TextView date = rootView.findViewById(R.id.date);
            TextView speed = rootView.findViewById(R.id.speed);
            TextView value = rootView.findViewById(R.id.value);
            TextView type = rootView.findViewById(R.id.type);

            return new ViewHolder(rootView, date, speed, value, type);
        }

    }
}
