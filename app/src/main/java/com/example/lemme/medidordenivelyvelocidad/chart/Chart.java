package com.example.lemme.medidordenivelyvelocidad.chart;

import android.view.View;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.HashMap;

/**
 * Created by lemme on 5/8/15.
 */
public class Chart {

    private HashMap<String, Object> serieOptions;
    private XYPlot chartPlot;
    private DataSerie serie;
    private SimpleXYSeries chartSerie;
    private LineAndPointFormatter serieFormat;

    public Chart(View chartView, HashMap<String, Object> serieOptions) {
        this.serieOptions = serieOptions;
        this.chartPlot = (XYPlot) chartView;
    }

    public void initializeSerie() {
        serie = new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, serieFormat, (String) serieOptions.get("Name"));
    }

    public void setDefaultSerieFormat() {
        int lineColor = (Integer) serieOptions.get("Line Color");
        int pointColor = (Integer) serieOptions.get("Point Color");
        int fillColor = (Integer) serieOptions.get("Fill Color");
        this.serieFormat = new LineAndPointFormatter(lineColor, pointColor, fillColor , new PointLabelFormatter(0));
    }

    public void updateChart() {
        chartPlot.removeSeries(chartSerie);
        chartSerie = new SimpleXYSeries(serie.getSerie(), serie.getFormat(), serie.getName());
        chartPlot.addSeries(chartSerie, serie.getLineAndPointFormatter());
        chartPlot.setDomainStepValue((Integer) serieOptions.get("Sampling Step"));
        chartPlot.setRangeBoundaries((Integer) serieOptions.get("Min Y-Axis Value"), (Integer) serieOptions.get("Max Y-Axis Value"), BoundaryMode.FIXED);

        chartPlot.redraw();
    }

    public DataSerie getSerie() {
        return serie;
    }

    public void setSerieFormat(LineAndPointFormatter serieFormat) {
        this.serieFormat = serieFormat;
    }
}
