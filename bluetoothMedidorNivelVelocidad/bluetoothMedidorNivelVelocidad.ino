#include <SoftwareSerial.h>
#define ECHO_ULTRASONIDO 8
#define TRIG_ULTRASONIDO 7
#define RxD 10
#define TxD 11
#define POTENCIOMETRO A0
#define MOTOR 3

float distancia;
float nivel;
long tiempo;
char incomingByte = '0';
int out= 0;
int input = 0;
float velocidadAngular, velocidadLineal;
int NbTopsFan; 
int rpm;
int hallsensor = 2;
char fan = 1;
SoftwareSerial BluetoothSerial(RxD, TxD);

typedef struct{               
  char fantype;
  unsigned int fandiv;
}fanspec;
fanspec fanspace[3]={{0,1},{1,2},{2,8}};

void setup() {
  BluetoothSerial.begin(9600);
  delay(500);
  BluetoothSerial.flush();
  delay(500);
  Serial.begin(9600);
  //SETUP SPEEDOMETER
  pinMode(MOTOR, OUTPUT);
  pinMode(POTENCIOMETRO, INPUT);
  pinMode(hallsensor, INPUT); 
  attachInterrupt(0, rpm_fun, RISING);
  //SETUP LEVEL METER
  pinMode(ECHO_ULTRASONIDO, OUTPUT);
  pinMode(TRIG_ULTRASONIDO,INPUT);
  digitalWrite(ECHO_ULTRASONIDO, LOW);
}

void loop() {
  if (BluetoothSerial.available() > 0) {
    incomingByte = BluetoothSerial.read();
  }
  
  if (incomingByte != '0') {
    if(incomingByte == 'S') {
      Serial.print("Velocidad ");
      speedometer();
    } else if(incomingByte == 'L') {
      Serial.print("Nivel ");
      levelmeter();
    }
  }
}

void speedometer() {
  input = analogRead(POTENCIOMETRO);
  calcularPwm();
  analogWrite(MOTOR, out);
  NbTopsFan = 0;			
  delay (2000);	
  rpm = ((NbTopsFan * 60)/fanspace[fan].fandiv);
  velocidadAngular = (rpm*6.283183)/60;
  velocidadLineal = velocidadAngular*0.00385;
  Serial.print("Velocidad Lineal ");
  Serial.print(velocidadLineal);
  Serial.println("m/s");
  BluetoothSerial.println(velocidadLineal);
}

void levelmeter() {
  digitalWrite(ECHO_ULTRASONIDO, HIGH);
  delayMicroseconds(10);
  digitalWrite(ECHO_ULTRASONIDO,LOW);
  tiempo=pulseIn(TRIG_ULTRASONIDO, HIGH);
  distancia=float(0.017*tiempo);
  nivel = 17 - distancia;
  Serial.println(distancia);
  BluetoothSerial.println(distancia);
  delay(2000);
}

void rpm_fun () { 
 NbTopsFan++; 
}

void calcularPwm() {
  out = input / 4;
  if(out < 15 ) {
    out = 0;
  } else if(out > 255) {
    out = 255;
  }
}
