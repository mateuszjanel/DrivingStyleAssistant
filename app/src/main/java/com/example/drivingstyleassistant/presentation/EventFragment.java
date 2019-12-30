package com.example.drivingstyleassistant.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.domain.entities.EventTypeConverter;
import com.example.drivingstyleassistant.domain.entities.Events;
import com.example.drivingstyleassistant.domain.helpers.EventHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class EventFragment extends Fragment {

   ArrayList<Events> eventsArrayList = new ArrayList<>();
   EventListAdapter adapter;
   Events.EventType eventType;
   int eventTypeCode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Context appContext = getActivity().getApplicationContext();
        EventHelper eventHelper = new EventHelper();
        eventTypeCode = getArguments().getInt("typeCode");
        this.eventType = EventTypeConverter.toEventType(eventTypeCode);
        List tempList = eventHelper.getAllEventsOfType(eventType);
        eventsArrayList = new ArrayList<>(tempList);

        ListView listView = getActivity().findViewById(R.id.list);
        adapter = new EventListAdapter(getActivity(), R.layout.fragment_event_item, eventsArrayList);
        listView.setAdapter(adapter);
    }
}
