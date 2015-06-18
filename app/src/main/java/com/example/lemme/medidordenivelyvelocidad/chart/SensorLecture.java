package com.example.lemme.medidordenivelyvelocidad.chart;

import android.text.style.TtsSpan;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lemme on 6/17/15.
 */
public class SensorLecture extends SugarRecord<SensorLecture> {
    @SuppressWarnings("unused")
    private String sensorName;
    @SuppressWarnings("unused")
    private float sensorLecture;

    @SuppressWarnings("unused")
    private Date dateTime;

    @SuppressWarnings("unused")
    public SensorLecture() {
        super();
    }

    public SensorLecture(String sensorName, float sensorLecture) {
        this.sensorName = sensorName;
        this.sensorLecture = sensorLecture;
        dateTime = new Date();
    }

    public float getSensorLecture() {
        return sensorLecture;
    }

    public String getDateTimeGmt() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmt = df.format(dateTime);
        return gmt;
    }
}
