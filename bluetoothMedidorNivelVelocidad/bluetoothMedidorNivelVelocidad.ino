#include <SoftwareSerial.h>
#define LED 12
#define RxD 10
#define TxD 11
#define SENSOR_VELOCIDAD A0
#define SENSOR_NIVEL A1

char incomingByte = '0';
int medidaSensor;
SoftwareSerial BluetoothSerial(RxD, TxD);

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(SENSOR_VELOCIDAD, INPUT);
  pinMode(SENSOR_NIVEL, INPUT);
  digitalWrite(LED, LOW);
  BluetoothSerial.begin(9600);
  delay(500);
  BluetoothSerial.flush();
  delay(500);
  Serial.begin(9600);
  Serial.println("Press 1 to LED ON or 0 to LED OFF...");
}

void loop() {
  if (BluetoothSerial.available() > 0) {
    incomingByte = BluetoothSerial.read();
  }
  
  if (incomingByte != '0') {
    if(incomingByte == 'S') {
       medidaSensor = analogRead(SENSOR_VELOCIDAD);
       BluetoothSerial.println(medidaSensor);
       Serial.println(medidaSensor);
       delay(1000);
    } else if(incomingByte == 'L') {
       medidaSensor = analogRead(SENSOR_NIVEL);
       BluetoothSerial.println(medidaSensor);
       Serial.println(medidaSensor);
       delay(1000);
    }
  }
}
