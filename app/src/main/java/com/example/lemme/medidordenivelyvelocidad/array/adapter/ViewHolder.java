package com.example.lemme.medidordenivelyvelocidad.array.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.lemme.medidordenivelyvelocidad.chart.SensorLecture;

import java.util.HashMap;

/**
 * Created by lemme on 6/18/15.
 */
public class ViewHolder {
    private TextView date;
    private TextView lecture;

    public ViewHolder(HashMap<String, View> sensorLectureInfoViews) {
        date = (TextView) sensorLectureInfoViews.get("Date");
        lecture = (TextView) sensorLectureInfoViews.get("Lecture");
    }

    public void setSensorLectureInfoOnEachView(SensorLecture sensorLecture) {
        date.setText(sensorLecture.getDateTimeGmt());
        lecture.setText(String.valueOf(sensorLecture.getSensorLecture()));
    }
}
