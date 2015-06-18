package com.example.lemme.medidordenivelyvelocidad.chart;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import java.util.ArrayList;

/**
 * Created by lemme on 4/27/15.
 */
public class DataSerie {
    private String name;
    private ArrayList<Float> serie;
    private SimpleXYSeries.ArrayFormat format;
    private LineAndPointFormatter lineAndPointFormatter;

    public DataSerie(SimpleXYSeries.ArrayFormat format, LineAndPointFormatter lineAndPointFormatter, String name) {
        this.format = format;
        this.lineAndPointFormatter = lineAndPointFormatter;
        this.name = name;
        serie = new ArrayList<>();
    }

    public void addSensorLecture(float sensorLecture) {
        serie.add(new Float(sensorLecture));
        if(serie.size() > Utilities.MAXIMUM_LENGHT_DATA_SERIE_DISPLAYED) {
            serie.remove(0);
        }
    }

    public SimpleXYSeries.ArrayFormat getFormat() {
        return format;
    }

    public LineAndPointFormatter getLineAndPointFormatter() {
        return lineAndPointFormatter;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Float> getSerie() {
        return serie;
    }
}
