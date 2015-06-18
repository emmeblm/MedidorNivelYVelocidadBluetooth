package com.example.lemme.medidordenivelyvelocidad;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.example.lemme.medidordenivelyvelocidad.chart.Chart;
import com.example.lemme.medidordenivelyvelocidad.chart.DataSerie;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by lemme on 5/12/15.
 */
public class ChartTest {
    @Mock
    private XYPlot chartViewMock;
    @Mock
    private PointLabelFormatter pointLabelFormatter;
    @Mock
    private LineAndPointFormatter lineAndPointFormatterMock;

    Chart chart;
    private HashMap<String, Object> serieOptionsStub;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        initializeStubs();
        chart = new Chart(chartViewMock, serieOptionsStub);
        chart.setSerieFormat(lineAndPointFormatterMock);
        chart.initializeSerie();
        doNothing().when(chartViewMock).removeSeries(any(SimpleXYSeries.class));
        when(chartViewMock.addSeries(any(SimpleXYSeries.class), any(LineAndPointFormatter.class))).thenReturn(true);
        doNothing().when(chartViewMock).setDomainStepValue(anyInt());
        doNothing().when(chartViewMock).setRangeBoundaries(anyInt(), anyInt(), any(BoundaryMode.class));
        doNothing().when(chartViewMock).redraw();
    }

    @Test
    public void shouldGetASerieWithSpecifiedSerieOptionsStub() throws Exception {
        DataSerie result = chart.getSerie();

        assertEquals(result.getName(), "Stub Name");
        assertEquals(result.getFormat(), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
        assertEquals(result.getLineAndPointFormatter(), lineAndPointFormatterMock);
    }

    @Test
    public void testName() throws Exception {
        chart.updateChart();
        verify(chartViewMock, times(1)).removeSeries(any(SimpleXYSeries.class));
        verify(chartViewMock, times(1)).addSeries(any(SimpleXYSeries.class), any(LineAndPointFormatter.class));
        verify(chartViewMock, times(1)).setDomainStepValue(anyInt());
        verify(chartViewMock, times(1)).setRangeBoundaries(anyInt(), anyInt(), any(BoundaryMode.class));
        verify(chartViewMock, times(1)).redraw();

    }

    private void initializeStubs() {
        serieOptionsStub = new HashMap<>();
        serieOptionsStub.put("Name", "Stub Name");
        serieOptionsStub.put("Line Color", 1);
        serieOptionsStub.put("Point Color", 1);
        serieOptionsStub.put("Fill Color", 1);
        serieOptionsStub.put("Sampling Step", Utilities.SENSOR_SAMPLING_STEP);
        serieOptionsStub.put("Min Y-Axis Value", Utilities.MIN_Y_AXIS_VALUE_SPEEDOMETER);
        serieOptionsStub.put("Max Y-Axis Value", Utilities.MAX_Y_AXIS_VALUE_SPEEDOMETER);
    }
}