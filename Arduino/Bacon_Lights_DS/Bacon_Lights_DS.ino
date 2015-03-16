#include "Adafruit_WS2801.h"
#include "SPI.h"
#ifdef __AVR_ATtiny85__
 #include <avr/power.h>
#endif  

Adafruit_WS2801 front = Adafruit_WS2801(28, 10, 9);
Adafruit_WS2801 back = Adafruit_WS2801(28, 6, 5);

uint32_t baconGreen, baconOrange, red, green, blue, allianceColor, off, colors[2], colors2[2];
byte state[2], count, i, period, wait, complementArray[3], ds;
boolean isReversed[2], isStopped[2];

Adafruit_WS2801 strips[2] = {front, back};

void setup()
{
  pinMode(13, OUTPUT);
  
  front.begin();
  back.begin();
  
  off = Color(0, 0, 0);
  baconOrange = Color(255, 10, 0); //Final, Sarah approved
  baconGreen = Color(0, 255, 0); //Final, Sarah approved
  red = Color(255, 0, 0);
  green = Color(0, 255, 0);
  blue = Color(0, 0, 255);
  
  colors[0] = off;
  count = 0;
  period = 12;
  wait = 75;
  
  defaultInit();
}
void loop()
{
  advance();
}

void defaultInit()
{
  state[0] = 6;
  state[1] = 4;
  
  colors[0] = baconGreen;
  colors[1] = baconGreen;
  
  colors2[0] = off;
  colors2[1] = baconOrange;
  /*
  everyX(1, 0, green, strips[0]);
  everyX(1, 0, green, strips[1]);
  */
  front.show();
  back.show();
}

void advance() 
{
  byte z;
  for(z=0; z<2; z++)
  {
    switch(state[z])
    {
      case(1): if(!isStopped[z]) chase(z); break;
      case(2): pulse(z); break;
      case(3): displayDS(z); break;
      case(4): insanityBacon(z); break;
      case(5): arcReactorSpark(z); break;
      case(6): mirroredChase(z); break;
      case(7): pulseThroughBlack(z); break;
    }
    //insanityBacon(z);
    strips[z].show();
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

void mirroredChase(byte stripIndex)
{
  uint32_t bg, fg;
  byte numPixels = strips[stripIndex].numPixels();
  
  everyX(1, 0, bg, strips[stripIndex]);
  
  for(i=0; i<numPixels; i+=period)
  {
    int j = i-count;
    if(j<=numPixels/2) strips[stripIndex].setPixelColor(j, baconOrange);
    else break;
  }
  
  for(i=period/2; i<numPixels; i+=period)
  {
    int j = i-count;
    if(j<=numPixels/2) strips[stripIndex].setPixelColor(j, baconGreen);
    else break;
  }
  
  for(i=(numPixels/2); i<numPixels; i+=period)
  {
    int j = i+count;
    if(j>=numPixels/2) strips[stripIndex].setPixelColor(j, baconOrange);
    //else break;
  }
  
  for(i=(numPixels/2)-(period/2); i<numPixels; i+=period)
  {
    int j = i+count;
    if(j>=numPixels/2) strips[stripIndex].setPixelColor(j, baconGreen);
    //else break;
  }
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

void pulseThroughBlack(byte stripIndex)
{
  byte set[3], old[3];
  float c, d, scale[3];
  uint32_t color, base;
  
  d = (millis() + 2500) % 10000;
  c = millis() % 5000;
  
  if(d >= 5000) base = colors2[stripIndex];
  else base = colors[stripIndex];
  
  undoRGB(base);
  for(i=0; i<3; i++)
  {
    old[i] = complementArray[i];
  }
  
  undoRGB(off);
  for(i=0; i<3; i++)
  {
    scale[i] = complementArray[i];
    set[i] = complementArray[i];
  }
  
  scale[0] = old[0] - set[0];
  scale[1] = old[1] - set[1];
  scale[2] = old[2] - set[2];
  
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

void receiveCommand(int bytes)
{
  
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
