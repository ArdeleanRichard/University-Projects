#include <Servo.h>
#include <NewPing.h>
// Pins of motor 1
#define mpin00 5
#define mpin01 6
// Pins of motor 2
#define mpin10 3
#define mpin11 11
#define DEBUG true
Servo srv;  
NewPing sonar(13, 12,200);

#include <SoftwareSerial.h>

//SoftwareSerial mySerial(2, 7); // RX, TX

void setup() {
   //Serial.begin(9600);
   // configuration of motor pins as output, initially 0
   digitalWrite(mpin00, 0);
   digitalWrite(mpin01, 0);
   digitalWrite(mpin10, 0);
   digitalWrite(mpin11, 0);
   pinMode (mpin00, OUTPUT);
   pinMode (mpin01, OUTPUT);
   pinMode (mpin10, OUTPUT);
   pinMode (mpin11, OUTPUT);
   // LED pin
   pinMode(13, OUTPUT);

   
   Serial.begin(115200);
   Serial.begin(115200);
   pinMode(LED_BUILTIN, OUTPUT);
   pinMode(2, OUTPUT);
   digitalWrite(LED_BUILTIN, LOW);
   
   sendData("AT+RST\r\n", 2000, false); // reset module
   sendData("AT+CWMODE=2\r\n", 1000, false); // configure as access point
   sendData("AT+CIFSR\r\n", 1000, DEBUG); // get ip address
   sendData("AT+CWSAP_DEF=\"Ciubo1\",\"11111111\",5,3,1,0\r\n", 2000, DEBUG); // get SSID info (network name) 
   sendData("AT+CIPMUX=1\r\n", 1000, false); // configure for multiple connections
   sendData("AT+CIPSERVER=1,80\r\n", 1000, false); // turn on server on port 80

}


// Function to control a motor
// Input: pins m1 and m2, direction and speed
void StartMotor (int m1, int m2, int forward, int speed)
{
   if (speed==0) // stop
   {
   digitalWrite(m1, 0); 
   digitalWrite(m2, 0);
   }
   else
   {
   if (forward)
   {
   digitalWrite(m2, 0);
   analogWrite (m1, speed); // use PWM
   }
   else
   {
   digitalWrite(m1, 0);
   analogWrite(m2, speed);
   }
   }
}


// Safety function
// Commands motors to stop, then delays
void delayStopped(int ms)
{
   StartMotor (mpin00, mpin01, 0, 0);
   StartMotor (mpin10, mpin11, 0, 0);
   delay(ms);
}



// Set three angles
// When finished, the servo remains in the middle (90 degrees)
void playWithServo(int pin)
{
   srv.attach(pin);
   srv.write(0);
   delay(1000);
   srv.write(180);
   delay(1000);
   srv.write(90);
   delay(1000);
   srv.detach();
}


volatile int v[10];
void readDirection(int pin)
{
   srv.attach(pin);
  for(int i=0;i<=6;i++)
  {
    srv.write(i*30);
    v[i]=sonar.ping_cm();
    Serial.println(v[i]);
    delay(500);
  }
   srv.write(90);
   delay(500);
   srv.detach();
}



String sendData(String command, const int timeout, boolean debug) 
{
   String response = "";
   Serial.print(command); // send command to the esp8266
   long int time = millis();
   while ((time + timeout) > millis()) {
      while (Serial.available()) {
          char c = Serial.read(); // read next char
          response += c;
      }
   }
    if (response.indexOf("/l1") != -1 ) {
        StartMotor (mpin00, mpin01, 1, 64);
        StartMotor (mpin10, mpin11, 1, 64);
        delay(500);
        //delayStopped(500);
   }
   if (response.indexOf("/l2") != -1) {
        StartMotor (mpin00, mpin01, 0, 64);
        StartMotor (mpin10, mpin11, 0, 64);
        delay(500);
        //delayStopped(500);
   }
   if (response.indexOf("/l3") != -1) {
        StartMotor (mpin00, mpin01, 0, 64);
        StartMotor (mpin10, mpin11, 1, 64);
        delay(500);
        //delayStopped(500);
   }
   if (response.indexOf("/l4") != -1) {
        StartMotor (mpin00, mpin01, 1, 64);
        StartMotor (mpin10, mpin11, 0, 64);
        delay(500);
        //delayStopped(500);
   }
   while (response.indexOf("/l5") != -1 ) {
       int dist=sonar.ping_cm();
       digitalWrite(13, 1);
       if(dist<10)
       {
         StartMotor (mpin00, mpin01, 1, 0);
         StartMotor (mpin10, mpin11, 1, 0);
      
         readDirection(8);
         int maxv=v[0], maxi=0;
         for(int i=0;i<=6;i++)
         {
          //Serial.println(v[i]);
          if((v[i]>maxv && maxv!=0) || v[i]==0)
           {
            maxv=v[i];
            maxi=i;
           }
           
         }
         //Serial.println(maxi);
         //Serial.println();
    
         if(maxi<3)
         {
           StartMotor (mpin00, mpin01, 0, 64);
           StartMotor (mpin10, mpin11, 1, 64);
           delay ((3-maxi)*100+250);
          }
          if(maxi>3)
          {
            StartMotor (mpin00, mpin01, 1, 64);
            StartMotor (mpin10, mpin11, 0, 64);
            delay ((maxi-3)*100+250);
          }
   
         delay (50); // How long the motors are on
         //delayStopped(500); // How long the motors are off
       }
       else
       {
         StartMotor (mpin00, mpin01, 1, 64);
         StartMotor (mpin10, mpin11, 1, 64);
        
         delay (50);
         //delayStopped(500);
       }
   }
   if (debug) {
      //Serial.print(response);
   }
   return response;
}


void loop() 
{ 
   if (Serial.available()) {
     if (Serial.find("+IPD,")) {
       delay(500);
       int connectionId = Serial.read() - 48; // read() function returns
       // ASCII decimal value and 0 (the first decimal number) starts at 48
       String webpage = "<h1>Hello World!</h1><a href=\"/l1\"><button>FORWARD</button></a>";
       String cipSend = "AT+CIPSEND=";
       cipSend += connectionId;
       cipSend += ",";
       webpage += "<a href=\"/l2\"><button>BACKWARD</button></a>";
       webpage += "<a href=\"/l3\"><button>LEFT</button></a>";
       webpage += "<a href=\"/l4\"><button>RIGHT</button></a>";
       webpage += "<a href=\"/l5\"><button>ACTIVATE</button></a>";
      
       cipSend += webpage.length();
       cipSend += "\r\n";
       sendData(cipSend, 100, DEBUG);
       sendData(webpage, 150, DEBUG);
      
       String closeCommand = "AT+CIPCLOSE=";
       closeCommand += connectionId; // append connection id
       closeCommand += "\r\n";
       sendData(closeCommand, 300, DEBUG);
     }
   }
  

} 
