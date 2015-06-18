package com.example.lemme.medidordenivelyvelocidad;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.example.lemme.medidordenivelyvelocidad.bluetooth.ConnectedThread;
import com.example.lemme.medidordenivelyvelocidad.chart.Chart;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by lemme on 5/12/15.
 */
public class ConnectedThreadTest {
    @Mock
    BluetoothSocket bluetoothSocketMock;
    @Mock
    Chart chartMock;
    @Mock
    InputStream inputStreamMock;
    @Mock
    OutputStream outputStreamMock;
    @Mock
    Handler handlerMock;

    ConnectedThread connectedThread;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldCreateAConnectedThreadWithInputAndOutputStreamsFromBluetoothSocket() throws Exception {
        newConnectedThreadWithValidInputAndOutputStreams();
        InputStream resultInputStream = connectedThread.getReaderStream();
        OutputStream resultOutputStream = connectedThread.getWriterStream();
        assertEquals(inputStreamMock, resultInputStream);
        assertEquals(outputStreamMock, resultOutputStream);
    }

    @Test
    public void shouldLeaveInputAndOutputStreamNullWhenIOExceptionReadingFromBluetoothSocket() throws Exception {
        doThrow(IOException.class).when(bluetoothSocketMock).getInputStream();
        doThrow(IOException.class).when(bluetoothSocketMock).getOutputStream();
        connectedThread = new ConnectedThread(bluetoothSocketMock, chartMock);
        assertNull(connectedThread.getReaderStream());
        assertNull(connectedThread.getWriterStream());
    }

    @Test
    public void shouldTerminateTheThreadWhenIOExceptionOccursWhileRunning() throws Exception {
        newConnectedThreadWithValidInputAndOutputStreams();
        connectedThread.setHandler(handlerMock);
        when(inputStreamMock.read(any(byte[].class))).thenReturn(1);
        doThrow(IOException.class).when(handlerMock).obtainMessage(anyInt(), anyInt(), anyInt(), any(byte[].class));
        assertTrue(connectedThread.isRunning());
        connectedThread.run();
        assertFalse(connectedThread.isRunning());
    }

    @Test
    public void shouldWriteAStringInTheConnectedThread() throws Exception {
        newConnectedThreadWithValidInputAndOutputStreams();
        doNothing().when(outputStreamMock).write(any(byte[].class));
        connectedThread.write(anyString());
        verify(outputStreamMock).write(any(byte[].class));
    }

    private void newConnectedThreadWithValidInputAndOutputStreams() throws IOException {
        when(bluetoothSocketMock.getInputStream()).thenReturn(inputStreamMock);
        when(bluetoothSocketMock.getOutputStream()).thenReturn(outputStreamMock);
        connectedThread = new ConnectedThread(bluetoothSocketMock, chartMock);
    }
}