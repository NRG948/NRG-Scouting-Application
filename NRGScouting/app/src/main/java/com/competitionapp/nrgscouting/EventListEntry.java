package com.competitionapp.nrgscouting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peyton Lee on 2/17/2018.
 */

public class EventListEntry extends Fragment {

    public ArrayList<Entry.TimeEvent> timeEventList = new ArrayList<Entry.TimeEvent>();
    ListView eventListView;
    TimeEventAdapter timeEventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_list, container, false);

    }

    public void onStart() {
        super.onStart();

        //1:36.450 =
        /*
        timeEventList.add(new Entry.TimeEvent(96450, Entry.EventType.PICKED_CUBE_0, Entry.CubeDropType.NONE_0));
        timeEventList.add(new Entry.TimeEvent(123150, Entry.EventType.DROPPED_CUBE_1, Entry.CubeDropType.EXCHANGE_4));
        timeEventList.add(new Entry.TimeEvent(1234, Entry.EventType.ALLY_END_3, Entry.CubeDropType.NONE_0));
        */

        eventListView = (ListView) this.getView().findViewById(R.id.timeEventList);
        timeEventAdapter = new TimeEventAdapter(this.getContext(), timeEventList);
        eventListView.setAdapter(timeEventAdapter);

    }

    public String convertTimeToText(int timestamp) {
        String min = "0";
        String sec = "00";
        String mSec = "000";

        min = String.valueOf(timestamp/60000);
        timestamp = timestamp%60000;

        sec = String.valueOf(timestamp/1000);
        timestamp = timestamp%1000;

        mSec = String.valueOf(timestamp/10);

        return "" + min +":" + formatToLength(sec, 2) + "." + formatToLength(mSec, 2);
    }

    public String formatToLength(String input, int length) {
        for(int i = length - input.length(); i > 0; i--) {
            input += "0";
        }
        return input;
    }

    public class TimeEventAdapter extends ArrayAdapter<Entry.TimeEvent> {

        ArrayList<Entry.TimeEvent> timeEventList;

        public TimeEventAdapter(Context context, ArrayList<Entry.TimeEvent> timeEventList) {
            super(context, 0, timeEventList);
            this.timeEventList = timeEventList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            if(timeEventList.size() <= position) { return null;}
            Entry.TimeEvent timeEvent = timeEventList.get(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_time_event, parent, false);
            }
            ImageView eventIcon = (ImageView) convertView.findViewById(R.id.eventIcon);
            TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
            TextView timestamp = (TextView) convertView.findViewById(R.id.eventTimestamp);
            //NEEDS TO BE FORMATTED
            timestamp.setText(convertTimeToText(timeEvent.timestamp));
            switch (timeEvent.type) {
                case PICKED_CUBE_0:
                    eventIcon.setImageResource(R.drawable.ic_picked_cube);
                    eventName.setText("Gained Cube");
                    break;
                case DROPPED_CUBE_1:
                    eventIcon.setImageResource(R.drawable.ic_drop_cube);
                    switch (timeEvent.cubeDropType) {
                        case NONE_0:
                            eventName.setText("Dropped Cube (None)");
                            break;
                        case ALLY_SWITCH_1:
                            eventName.setText("Dropped Cube (Ally Switch)");
                            break;
                        case OPP_SWITCH_2:
                            eventName.setText("Dropped Cube (Opponent Switch)");
                            break;
                        case SCALE_3:
                            eventName.setText("Dropped Cube (Scale)");
                            break;
                        case EXCHANGE_4:
                            eventName.setText("Dropped Cube (Exchange)");
                            break;
                    }
                    break;
                case ALLY_START_2:
                    eventIcon.setImageResource(R.drawable.ic_switch);
                    eventName.setText("Claimed Ally Switch");
                    break;
                case ALLY_END_3:
                    eventIcon.setImageResource(R.drawable.ic_switch);
                    eventName.setText("Lost Ally Switch");
                    break;
                case OPP_START_4:
                    eventIcon.setImageResource(R.drawable.ic_switch);
                    eventName.setText("Claimed Opponent Switch");
                    break;
                case OPP_END_5:
                    eventIcon.setImageResource(R.drawable.ic_switch);
                    eventName.setText("Lost Opponent Switch");
                    break;
                case SCALE_START_6:
                    eventIcon.setImageResource(R.drawable.ic_scale);
                    eventName.setText("Claimed Scale");
                    break;
                case SCALE_END_7:
                    eventIcon.setImageResource(R.drawable.ic_scale);
                    eventName.setText("Lost Scale");
                    break;
                case BOOST_8:
                    eventIcon.setImageResource(R.drawable.ic_boost_powerup);
                    eventName.setText("Used Boost Powerup");
                    break;
                case FORCE_9:
                    eventIcon.setImageResource(R.drawable.ic_force_powerup);
                    eventName.setText("Used Force Powerup");
                    break;
            }

            return convertView;
        }

    }

}
