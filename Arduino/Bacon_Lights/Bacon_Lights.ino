#include "Adafruit_WS2801.h"
#include "SPI.h"

uint8_t dataPin  = 2;    // Yellow wire on Adafruit Pixels
uint8_t clockPin = 4;    // Green wire on Adafruit Pixels
uint8_t numPixels = 13;

uint32_t baconGreen, baconOrange;
byte chaseOffset = 0;

Adafruit_WS2801 strip = Adafruit_WS2801(numPixels, dataPin, clockPin);

void setup()
{
  strip.begin();
  strip.show();
  
  Serial.begin(9600);
  
  baconOrange = Color(255, 10, 0); //Final, Sarah approved
  baconGreen = Color(0, 255, 0); //Final, Sarah approved
}

void loop()
{
  /*
  colorWipe(baconGreen, 50, strip);
  colorWipe(baconOrange, 50, strip);
  
  everyX(2, 0, baconOrange, strip);
  everyX(2, 1, baconGreen, strip);
  delay(1000);
  everyX(1, 0, Color(0, 0, 0), strip);
  delay(2000);
  */
  if(Serial.available())
  {
    switch(Serial.read())
    {
      case('o'): colorWipe(baconOrange, 50, strip); return;
      case('g'): colorWipe(baconGreen, 50, strip); return;
      default: colorWipe(Color(0, 0, 0), 50, strip);
    }
  }
}

void colorWipe(uint32_t c, uint8_t wait, Adafruit_WS2801 strip) {
  int i;
  
  for (i=0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, c);
      strip.show();
      delay(wait);
  }
}

void chase(boolean reverse, boolean invert, byte spacing, Adafruit_WS2801 strip)
{
  uint32_t topColor, bottomColor;
  byte newChaseOffset;
  
  if(!invert)
  {
    topColor = baconGreen;
    bottomColor = baconOrange;
  }
  else
  {
    topColor = baconOrange;
    bottomColor = baconGreen;
  }
  
  everyX(1, 0, bottomColor, strip);
  everyX(spacing, chaseOffset, topColor, strip);
  
  if(!reverse)
  {
    chaseOffset ++;
  }
  else
  {
    chaseOffset --;
  }
  chaseOffset = chaseOffset % spacing;
}

void everyX(byte skip, byte offset, uint32_t color, Adafruit_WS2801 strip)
{
  byte i;
  for(i=offset; i<strip.numPixels(); i+=skip)
  {
    strip.setPixelColor(i, color);
  }
  
  strip.show();
}

void rainbowCycle(uint8_t wait, Adafruit_WS2801 strip) {
  int i, j;
  
  for (j=0; j < 256 * 5; j++) {     // 5 cycles of all 25 colors in the wheel
    for (i=0; i < strip.numPixels(); i++) {
      // tricky math! we use each pixel as a fraction of the full 96-color wheel
      // (thats the i / strip.numPixels() part)
      // Then add in j which makes the colors go around per pixel
      // the % 96 is to make the wheel cycle around
      strip.setPixelColor(i, Wheel( ((i * 256 / strip.numPixels()) + j) % 256) );
    }  
    strip.show();   // write all the pixels out
    delay(wait);
  }
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
