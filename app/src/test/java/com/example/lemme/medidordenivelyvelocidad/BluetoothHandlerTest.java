package com.example.lemme.medidordenivelyvelocidad;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by lemme on 5/12/15.
 */
public class BluetoothHandlerTest {
    @Mock
    BluetoothAdapter bluetoothAdapterMock;
    @Mock
    BluetoothDevice bluetoothDeviceMock;
    @Mock
    BluetoothSocket bluetoothSocketMock;
    @Mock
    MainActivity mainActivityMock;

    BluetoothHandler bluetoothHandler;
    String methodName = "createInsecureRfcommSocketToServiceRecord";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        bluetoothHandler = new BluetoothHandler(bluetoothAdapterMock, mainActivityMock);
        Utilities.bluetoothSocket = bluetoothSocketMock;
        when(bluetoothDeviceMock.getClass().getMethod(methodName, UUID.class).invoke(bluetoothDeviceMock, Utilities.SPP_UUID_SERVICE)).thenReturn(bluetoothSocketMock);
        when(bluetoothDeviceMock.createInsecureRfcommSocketToServiceRecord(Utilities.SPP_UUID_SERVICE)).thenReturn(bluetoothSocketMock);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowBluetoothExceptionExitAppBluetoothAdapterNotSupported() throws BluetoothExceptionExitApp {
        thrown.expect(BluetoothExceptionExitApp.class);
        thrown.expectMessage(Utilities.BLUETOOTH_NOT_SUPPORTED);

        BluetoothAdapter defaultAdapterNotPresent = null;

        BluetoothHandler bluetoothHandler = new BluetoothHandler(defaultAdapterNotPresent, mainActivityMock);
        bluetoothHandler.validateIfBluetoothAdapterIsSupported();
    }

    @Test
    public void shouldThrowBluetoothExceptionAdapterDisabled() throws BluetoothExceptionAdapterDisabled {
        thrown.expect(BluetoothExceptionAdapterDisabled.class);
        thrown.expectMessage(Utilities.BLUETOOTH_ADAPTER_DISABLED);

        when(bluetoothAdapterMock.isEnabled()).thenReturn(false);
        bluetoothHandler.checkBluetoothAdapterState();
    }

    @Test
    public void shouldReturnTrueIfBluetoothAdapterIsEnabled() throws Exception {
        when(bluetoothAdapterMock.isEnabled()).thenReturn(true);
        assertTrue(bluetoothHandler.validateBluetoothAdapterState());
    }

    @Test
    public void shouldCallCreateInsecureRfcommSocketToServiceRecordMethod() {
        try {
            bluetoothHandler.createBluetoothSocket(bluetoothDeviceMock, Utilities.MINIMUM_SDK_VERSION);
            verify(bluetoothDeviceMock, times(1)).getClass().getMethod(methodName, UUID.class).invoke(bluetoothDeviceMock, Utilities.SPP_UUID_SERVICE);
        } catch (Exception ex) { }
    }

    @Test
    public void shouldCreateABluetoothSocketWhenBuildVersionSDKIsLessThanTen() throws Exception {
        int buildVersion = new Random().nextInt(10);
        bluetoothHandler.createBluetoothSocket(bluetoothDeviceMock, buildVersion);

        verify(bluetoothDeviceMock, times(1)).createInsecureRfcommSocketToServiceRecord(Utilities.SPP_UUID_SERVICE);
        assertEquals(Utilities.bluetoothSocket, bluetoothSocketMock);
    }

    @Test
    public void shouldCreateAndSaveAnInsecureRfcommSocketToServiceRecordInUtilitiesBluetoothSocket() throws Exception {
        bluetoothHandler.createInsecureRfCommSocketToServiceRecord(bluetoothDeviceMock);
        assertEquals(Utilities.bluetoothSocket, bluetoothSocketMock);
    }

    @Test(expected = BluetoothExceptionExitApp.class)
    public void shouldThrowABluetoothExceptionExitAppBecauseOfDeviceNull() throws Exception {
        BluetoothDevice bluetoothDeviceNotFound = null;
        bluetoothHandler.createBluetoothSocket(bluetoothDeviceNotFound, Utilities.MINIMUM_SDK_VERSION);
    }

    @Test
    public void shouldEstablishASuccessfulConnectionWithTheBluetoothModule() throws Exception {
        doNothing().when(bluetoothSocketMock).connect();
        bluetoothHandler.establishConnectionWithTheBluetoothModule();
        verify(bluetoothSocketMock).connect();
    }

    @Test
    public void shouldDisconnectBluetoothSocketWhenConnectingCausedAnException() throws Exception {
        doThrow(new IOException()).when(bluetoothSocketMock).connect();
        doNothing().when(bluetoothSocketMock).close();
        bluetoothHandler.establishConnectionWithTheBluetoothModule();
        verify(bluetoothSocketMock).close();
    }
}