package com.example.lemme.medidordenivelyvelocidad.chart;

import com.orm.SugarRecord;

/**
 * Created by lemme on 6/17/15.
 */
public class SensorLecture extends SugarRecord<SensorLecture> {
    @SuppressWarnings("unused")
    private String sensorName;

    @SuppressWarnings("unused")
    private float sensorLecture;

    @SuppressWarnings("unused")
    public SensorLecture() {
        super();
    }

    public SensorLecture(String sensorName, float sensorLecture) {
        this.sensorName = sensorName;
        this.sensorLecture = sensorLecture;
    }

    public float getSensorLecture() {
        return sensorLecture;
    }
}
