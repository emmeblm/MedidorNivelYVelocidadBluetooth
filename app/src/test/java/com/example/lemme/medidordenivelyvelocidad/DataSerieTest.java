package com.example.lemme.medidordenivelyvelocidad;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.example.lemme.medidordenivelyvelocidad.chart.DataSerie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by lemme on 5/12/15.
 */
public class DataSerieTest {
    @Mock
    LineAndPointFormatter lineAndPointFormatterMock;

    DataSerie dataSerie;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        dataSerie = new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, lineAndPointFormatterMock, "Stub Name");
    }

    @Test
    public void shouldAddSensorLectureToSerie() throws Exception {
        assertEquals(dataSerie.getSensorLectures().size(), 0);
        dataSerie.addSensorLecture(1);
        assertEquals(dataSerie.getSensorLectures().size(), 1);
    }
}