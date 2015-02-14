#include "Adafruit_WS2801.h"
#include "SPI.h"
#include "Wire.h"

Adafruit_WS2801 arcReactor = Adafruit_WS2801(1, 5, 3);
Adafruit_WS2801 brakeLights = Adafruit_WS2801(1, 8, 9);
Adafruit_WS2801 elevatorLights = Adafruit_WS2801(1, 6, 7);
Adafruit_WS2801 test = Adafruit_WS2801(61, 2, 4);

uint32_t baconGreen, baconOrange, red, blue, allianceColor, off, colors[5], colors2[5];
byte state[4], strip, count, i, period, wait, complementArray[3], ds;
boolean isReversed[4], isStopped[4];

Adafruit_WS2801 strips[5] = {test, arcReactor, brakeLights, elevatorLights};

void setup()
{
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);
  
  test.begin();
  test.show();
  
  Wire.begin(0xA0);
  //Wire.onReceive(receiveI2C);
  
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
  
  Serial.begin(9600);
  Serial.println("Initialized!");
  
  
  digitalWrite(13, LOW);
}
void loop()
{
  receiveI2C(0);
  advance();
}

void advance() 
{
  for(i=0; i<4; i++)
  {
    switch(state[i])
    {
      case(1): if(!isStopped[i]) chase(i); break;
      case(2): if(!isStopped[i]) pulse(i); break;
      case(3): displayDS(i); break;
    }
  }
  
  for(i=0; i<4; i++)
  {
    strips[i].show();
  }
  
  count = millis() / wait;
  count %= period;
}

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

void chase(byte stripIndex)
{
  byte c;
  uint32_t bg, fg;
  
  fg = colors[stripIndex];
  bg = colors2[stripIndex];
  
  //delay(1);
  /*
  if(isReversed[stripIndex])
  {*/
    c = count;
  /*}
  else`
  {
    c = period - count;
  }
  */
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

void displayDS(int stripIndex)
{
  everyX(1, 0, off, strips[stripIndex]);
  for(i=0; i<ds; i++)
  {
    everyX(4, i, colors[stripIndex], strips[stripIndex]);
  }
}

void allOff()
{
  for(i=0; i<4; i++)
  {
    state[i] = 0;
    everyX(1, 0, off, strips[i]);
  }
}

void recieveCustomColor(byte stripIndex)
{
  byte s, c[3];
  for(i=0; i<3; i++)
  {
    s = 0;
    while(Wire.peek() != '.')
    {
      if(Wire.available())
      {
        if(isdigit(Wire.peek()))
        {
          s *= 10;
          s += Wire.read() - '0';
        }
        else
        {
          Wire.read();
        }
      }
    }
    Wire.read();
    //Serial.println(s);
    c[i] = s;
  }
  
  colors[stripIndex] = Color(c[0], c[1], c[2]);
}

void recieveDriverStationPosition()
{
  //while(true)
  {
    if(Wire.available())
    {
      if(isdigit(Wire.peek()))
      {
        ds = (Wire.read() - '0');
        return;
      }
      else
      {
        ds = 0;
        return;
      }
    }
  }
}

void receiveI2C(int bytes)
{
  digitalWrite(13, HIGH);
  //Serial.println("Receiving");
  while(Serial.available())
  {
    //Serial.println(char(Wire.peek()));
    switch(Serial.read())
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
      case('t'): strip = 0; break;
      
      //Set state;
      case('z'): state[strip] = 1; break; //Chase
      case('x'): state[strip] = 2; break; //Pulse
      case('c'): state[strip] = 3; break; //DS Output
      
      //Set variables
      case('i'): isReversed[strip] = true; isStopped[strip] = false; break;
      case('o'): isReversed[strip] = false; isStopped[strip] = false; break;
      case('p'): isStopped[strip] = true; break;
      
      //Special
      //case('0'): allOff(); break;
      case('1'): everyX(1, 0, colors[strip], strips[strip]); state[strip] = 0; break;
      case('4'): colors2[strip] = colors[strip]; break;
      
      //Customs
      case('8'): recieveDriverStationPosition(); break;
      case('9'): recieveCustomColor(strip); break;
      //case('!'): Serial.println("Success!"); everyX(1, 0, Color(255, 255, 0), strips[0]); break; //DEBUG
    }
  }
  digitalWrite(13, LOW);
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

void undoRGB(uint32_t c)
{
  byte r, g, b;
  
  complementArray[2] = c;
  c >>= 8;
  complementArray[1] = c;
  c >>= 8;
  complementArray[0] = c;
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
