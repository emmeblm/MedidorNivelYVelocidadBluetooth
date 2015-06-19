package com.example.lemme.medidordenivelyvelocidad.array.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.chart.SensorLecture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lemme on 6/18/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<SensorLecture> {
    private final Activity context;
    private final ArrayList<SensorLecture> sensorLectures;

    public CustomArrayAdapter(Activity context, ArrayList<SensorLecture> sensorLectures) {
        super(context, R.layout.row_sensor_lecture, sensorLectures);
        this.context = context;
        this.sensorLectures = sensorLectures;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = createNewRowView();
        }
        SensorLecture sensorLecture = sensorLectures.get(position);
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.setSensorLectureInfoOnEachView(sensorLecture);

        return rowView;
    }

    private View createNewRowView() {
        LayoutInflater inflater = context.getLayoutInflater();
        View newRowView = inflater.inflate(R.layout.row_sensor_lecture, null);
        final ViewHolder viewHolder = createSensorLectureViewHolder(newRowView);
        newRowView.setTag(viewHolder);
        return newRowView;
    }

    private ViewHolder createSensorLectureViewHolder(View newRowView) {
        HashMap<String, View> sensorLectureInfoViews = new HashMap<>();
        sensorLectureInfoViews.put("Date", newRowView.findViewById(R.id.rowTextViewDate));
        sensorLectureInfoViews.put("Lecture", newRowView.findViewById(R.id.rowTextViewLecture));
        ViewHolder viewHolder = new ViewHolder(sensorLectureInfoViews);
        return viewHolder;
    }
}
