#include "Adafruit_WS2801.h"
#include "SPI.h"
#include "Wire.h"

Adafruit_WS2801 arcReactor = Adafruit_WS2801(1, 5, 3);
Adafruit_WS2801 brakeLights = Adafruit_WS2801(1, 8, 9);
Adafruit_WS2801 elevatorLights = Adafruit_WS2801(1, 6, 7);
Adafruit_WS2801 toteChute = Adafruit_WS2801(1, 10, 11);
Adafruit_WS2801 test = Adafruit_WS2801(62, 2, 4);

uint32_t baconGreen, baconOrange, red, blue, allianceColor, off, colors[5], colors2[5];
byte state[4], strip, count, i, period, wait, complementArray[3], ds;
boolean isReversed[4], isStopped[4];

Adafruit_WS2801 strips[5] = {test, arcReactor, brakeLights, elevatorLights, toteChute};

void setup()
{
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);
  
  test.begin();
  test.show();
  
  Wire.begin(0xA0);
  Wire.onReceive(receiveI2C);
  
  off = Color(0, 0, 0);
  baconOrange = Color(255, 10, 0); //Final, Sarah approved
  baconGreen = Color(0, 255, 0); //Final, Sarah approved
  red = Color(255, 0, 0);
  blue = Color(0, 0, 255);
  
  colors[0] = off;
  strip = 0;
  count = 0;
  period = 12;
  wait = 75;
  /*
  Serial.begin(9600);
  Serial.println("Initialized!");
  */
  
  digitalWrite(13, LOW);
}
void loop()
{
  advance();
}

void advance() 
{
  for(i=0; i<4; i++)
  {
    switch(state[i])
    {
      case(1): if(!isStopped[i]) chase(i); break;
      case(2): pulse(i); break;
      case(3): displayDS(i); break;
      case(4): insanityBacon(i); break;
      case(5): arcReactorSpark(i); break;
    }
  }
  
  for(i=0; i<4; i++)
  {
    strips[i].show();
  }
  
  count = millis() / wait;
  count %= period;
}

void chase(byte stripIndex)
{
  byte c;
  uint32_t bg, fg;
  
  fg = colors[stripIndex];
  bg = colors2[stripIndex];
  
  //delay(1);
  
  if(isReversed[stripIndex])
  {
    c = count;
  }
  else
  {
    c = period - count;
  }
  
  //delay(1);
  
  everyX(1, 0, bg, strips[stripIndex]);
  everyX(period, c, fg, strips[stripIndex]);
}

void pulse(byte stripIndex)
{
  byte set[3], old[3];
  float c, scale[3];
  uint32_t color;
  
  undoRGB(colors2[stripIndex]);
  for(i=0; i<3; i++)
  {
    old[i] = complementArray[i];
  }
  
  undoRGB(colors[stripIndex]);
  for(i=0; i<3; i++)
  {
    scale[i] = complementArray[i];
    set[i] = complementArray[i];
  }
  
  scale[0] = old[0] - set[0];
  scale[1] = old[1] - set[1];
  scale[2] = old[2] - set[2];
  
  c = millis() % 5000;
  
  scale[0] *= abs(1-(c/2500));
  scale[1] *= abs(1-(c/2500));
  scale[2] *= abs(1-(c/2500));
  
  set[0] += scale[0];
  set[1] += scale[1];
  set[2] += scale[2];
  
  color = Color(set[0], set[1], set[2]);
  
  everyX(1, 0, color, strips[stripIndex]);
}

void displayDS(byte stripIndex)
{
  byte c = millis() / 1000;
  c %= 4;
  
  everyX(1, 0, off, strips[stripIndex]);
  for(i=0; i<ds; i++)
  {
    everyX(4, i+c, colors[stripIndex], strips[stripIndex]);
  }
}

void insanityBacon(byte stripIndex)
{
  uint32_t q;
  for(i=0; i<strips[stripIndex].numPixels(); i++)
  {
    switch(random(0, 3))
    {
      case(0): q = baconOrange; break;
      case(1): q = baconGreen; break;
      case(2): q = off;
    }
    
    strips[stripIndex].setPixelColor(i, q);
  }
}

void arcReactorSpark(byte stripIndex)
{
  int q;
  for(i=0; i<strips[stripIndex].numPixels(); i++)
  {
    switch(random(0, 10))
    {
      case(0): q = random(0, 255); break;
      case(1): q = random(0, 150); break;
      case(2): q = random(0, 125); break;
      case(3): q = random(0, 100); break;
      case(4): q = random(0, 50); break;
      case(5): q = random(0, 25); break;
      case(6): q = random(0, 10); break;
      case(7): q = random(0, 10); break;
      case(8): q = random(0, 10); break;
      case(9): q = 0; break;
    }
    strips[stripIndex].setPixelColor(i, Color(q, q, q));
  }
}

/*
void allOff() /Breaks things
{
  for(i=0; i<4; i++)
  {
    state[i] = 0;
    everyX(1, 0, off, strips[i]);
  }
}
*/

void everyX(byte skip, byte offset, uint32_t color, Adafruit_WS2801 strip)
{
  byte i;
  for(i=offset; i<strip.numPixels(); i+=skip)
  {
    strip.setPixelColor(i, color);
    //delay(1);
  }
  return;
}

void receiveI2C(int bytes)
{
  digitalWrite(13, HIGH);
  //Serial.println("Receiving");
  while(Wire.available())
  {
    //Serial.println(char(Wire.peek()));
    switch(Wire.read())
    {
      //Set color
      case('a'): colors[strip] = baconOrange; break;
      case('s'): colors[strip] = baconGreen; break;
      case('d'): colors[strip] = red; break;
      case('f'): colors[strip] = Color(255, 255, 255); break;
      case('g'): colors[strip] = off; break;
      case('h'): colors[strip] = blue; break;
      
      //Set strip
      case('q'): strip = 1; break;
      case('w'): strip = 2; break;
      case('e'): strip = 3; break;
      case('r'): strip = 4; break;
      case('t'): strip = 0; break;
      
      //Set state;
      case('z'): state[strip] = 1; break; //Chase
      case('x'): state[strip] = 2; break; //Pulse
      case('c'): state[strip] = 3; break; //DS Output
      case('v'): state[strip] = 4; break; //Insanity Bacon
      case('b'): state[strip] = 5; break; //Arc Spark
      
      //Set variables
      case('i'): isReversed[strip] = true; isStopped[strip] = false; break;
      case('o'): isReversed[strip] = false; isStopped[strip] = false; break;
      case('p'): isStopped[strip] = true; break;
      
      //Special
      //case('0'): allOff(); break; //Breaks things
      case('1'): everyX(1, 0, colors[strip], strips[strip]); state[strip] = 0; break;
      case('4'): colors2[strip] = colors[strip]; break;
      
      //Customs
      case('6'): ds = 1; break;
      case('7'): ds = 2; break;
      case('8'): ds = 3; break;
      //case('9'): recieveCustomColor(strip); break;
      //case('!'): Serial.println("Success!"); everyX(1, 0, Color(255, 255, 0), strips[0]); break; //DEBUG
    }
  }
  digitalWrite(13, LOW);
}

void undoRGB(uint32_t c)
{
  byte r, g, b;
  
  complementArray[2] = c;
  c >>= 8;
  complementArray[1] = c;
  c >>= 8;
  complementArray[0] = c;
}

uint32_t Color(byte r, byte g, byte b)
{
  uint32_t c;
  c = r;
  c <<= 8;
  c |= g;
  c <<= 8;
  c |= b;
  return c;
}

uint32_t Wheel(byte WheelPos)
{
  if (WheelPos < 85) {
   return Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  } else if (WheelPos < 170) {
   WheelPos -= 85;
   return Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } else {
   WheelPos -= 170; 
   return Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
}
