package com.example.drivingstyleassistant.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.domain.entities.Events;
import com.example.drivingstyleassistant.presentation.EventFragment.OnListFragmentInteractionListener;
import com.example.drivingstyleassistant.presentation.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
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

    }

    public class ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
