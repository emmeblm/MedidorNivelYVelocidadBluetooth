package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemme.medidordenivelyvelocidad.bluetooth.BluetoothHandler;
import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;


public class MainActivity extends Activity {

    private Switch bluetoothSwitch;
    private Button goToSpeedChart;
    private Button goToLevelChart;
    private TextView bluetoothState;
    private BroadcastReceiver broadcastReceiver;
    private BluetoothHandler bluetoothHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothSwitch = (Switch) this.findViewById(R.id.bluetoothOnOff);
        goToSpeedChart = (Button) this.findViewById(R.id.btnSpeedChart);
        goToLevelChart = (Button) this.findViewById(R.id.btnLevelChart);
        bluetoothState = (TextView) this.findViewById(R.id.moduleState);

        initializeBluetoothHandler(new BluetoothHandler(BluetoothAdapter.getDefaultAdapter(), this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = initializeOnCheckedChangeListener(bluetoothSwitch);
        setCheckedStateListenerToBluetoothSwitch(bluetoothSwitch, onCheckedChangeListener);
        setEnabledStateToChartButtons(bluetoothSwitch.isChecked());
        createBroadcastReceiverToListenToBluetoothAdapterState();
        registerBroadcastReceiverToBluetoothEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastReceiverToBluetoothEvents();
    }

    private CompoundButton.OnCheckedChangeListener initializeOnCheckedChangeListener(final Switch bluetoothSwitch) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    continueConnecting(bluetoothHandler.connectToBluetoothDevice());
                }
                else {
                    bluetoothHandler.disconnectFromBluetoothDevice();
                    unregisterBroadcastReceiverToBluetoothEvents();
                    setEnabledStateToChartButtons(false);
                    bluetoothState.setText(getString(R.string.disconnected));
                }

            }
        };
        return onCheckedChangeListener;
    }

    public void setCheckedStateListenerToBluetoothSwitch(final Switch bluetoothSwitch, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        bluetoothSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void continueConnecting(Boolean isContinue) {
        if(isContinue) {
            setEnabledStateToChartButtons(bluetoothSwitch.isChecked());
            bluetoothState.setText(getString(R.string.connected));
            bluetoothHandler.continueConnecting();
        }
        else
            setEnabledStateToChartButtons(false);
    }

    private void unregisterBroadcastReceiverToBluetoothEvents() {
        try {
            this.unregisterReceiver(broadcastReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setEnabledStateToChartButtons(Boolean bluetoothSwitchIsChecked) {
        goToSpeedChart.setEnabled(bluetoothSwitchIsChecked);
        goToLevelChart.setEnabled(bluetoothSwitchIsChecked);

        if(bluetoothSwitchIsChecked) {
            bluetoothState.setText(getString(R.string.connected));
            goToSpeedChart.setTextColor(getResources().getColor(R.color.whiteEnabled));
            goToLevelChart.setTextColor(getResources().getColor(R.color.whiteEnabled));
            goToSpeedChart.setBackgroundColor(getResources().getColor(R.color.transparentBlueBluetooth));
            goToLevelChart.setBackgroundColor(getResources().getColor(R.color.transparentBlueBluetooth));
        }
        else {
            bluetoothState.setText(getString(R.string.disconnected));
            goToSpeedChart.setTextColor(getResources().getColor(R.color.grayDisabled));
            goToLevelChart.setTextColor(getResources().getColor(R.color.grayDisabled));
            goToSpeedChart.setBackgroundColor(Color.TRANSPARENT);
            goToLevelChart.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void initializeBluetoothHandler(BluetoothHandler handler) {
        bluetoothHandler = handler;
    }

    public void promptUserToEnableBluetooth() {
        Intent bluetoothEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivityForResult(bluetoothEnableIntent, Utilities.ENABLE_BLUETOOTH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Utilities.ENABLE_BLUETOOTH_REQUEST && resultCode == RESULT_OK)
            continueConnecting(bluetoothHandler.connectToBluetoothDevice());

        if(resultCode == RESULT_CANCELED) {
            unregisterBroadcastReceiverToBluetoothEvents();
            bluetoothHandler.disconnectFromBluetoothDevice();
        }
    }

    public void exitAppWithError(String errorType, String errorMessage) {
        String messageToShow = errorType + Utilities.ERROR_MESSAGE_JOIN_CHAR + errorMessage;
        Toast.makeText(getBaseContext(), messageToShow, Toast.LENGTH_SHORT);
        unregisterBroadcastReceiverToBluetoothEvents();
        bluetoothHandler.disconnectFromBluetoothDevice();
        finish();
    }

    public void createBroadcastReceiverToListenToBluetoothAdapterState() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Boolean bluetoothAdapterOFF = (state == BluetoothAdapter.STATE_OFF ||  state == BluetoothAdapter.STATE_TURNING_OFF);
                if(action == BluetoothAdapter.ACTION_STATE_CHANGED && bluetoothAdapterOFF) {
                    unregisterBroadcastReceiverToBluetoothEvents();
                    bluetoothHandler.disconnectFromBluetoothDevice();
                    bluetoothSwitch.setChecked(false);
                    setEnabledStateToChartButtons(bluetoothSwitch.isChecked());
                }
            }
        };
    }

    public void registerBroadcastReceiverToBluetoothEvents() {
        IntentFilter bluetoothStateChangedFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(broadcastReceiver, bluetoothStateChangedFilter);
    }

    public void onClickStartActivitySpeedometer(View view) {
        Intent intentProfile = new Intent(this, SpeedometerActivity.class);
        startActivity(intentProfile);
    }

    public void onClickStartActivityLevelMeter(View view) {
        Intent intentProfile = new Intent(this, LevelMeterActivity.class);
        startActivity(intentProfile);
    }

    @Override
    public void onBackPressed() {
        unregisterBroadcastReceiverToBluetoothEvents();
        bluetoothHandler.disconnectFromBluetoothDevice();
        bluetoothState.setText(getString(R.string.disconnected));
        bluetoothSwitch.setChecked(false);
        setEnabledStateToChartButtons(false);
        super.onBackPressed();
    }
}
