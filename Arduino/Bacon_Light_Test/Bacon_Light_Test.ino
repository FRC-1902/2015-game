#include <Adafruit_WS2801.h>
#include <SPI.h>

#define dataPin 2
#define clockPin 4

#define stripPin 9
#define brightness 255

int i, j, k;
int a, b, c;
long targetMillis;
boolean isBlue, isNeutral;
uint32_t baconGreen, baconOrange;

Adafruit_WS2801 strip = Adafruit_WS2801(10, dataPin, clockPin);

void setup()
{
  randomSeed(analogRead(A1));
  
  strip.begin();
  //strip.setBrightness(brightness);
  strip.show();
  
  baconGreen = Color(82, 174, 74);
  baconOrange = Color(234, 82, 36);
  
  bootupAnimation2();
}

void loop()
{
  flashFadeThroughWhite(10);
  insanityChase(10);
}

void bootupAnimation2() //Bacon Approved!
{
  boolean flashed[strip.numPixels()];
  byte pixelToFlash;
  byte lastPixelFlashed = 0;
  uint32_t colorToFlash;
  
  flashed[0] = true;
  
  for(i=1; i<strip.numPixels(); i++)
  {
    flashed[i] = false;
  }
  
  setEntireStrip(0, 0, 0);
  
  for(i=0; i<strip.numPixels(); i++)
  {
    pixelToFlash = random((strip.numPixels() - i));
    
    /*
    do
    {
      a = random(0, 200);
      b = random(0, 200);
      c = random(0, 200);
    } while(a+b+c > 150);
    */
    if(random(0, 2) == 1)
    {
      colorToFlash = baconOrange;
    }
    else
    {
      colorToFlash = baconGreen;
    }
    
    byte currentPixel = 0;
    
    for(j=0; j<pixelToFlash; j++)
    {
      do
      {
        currentPixel++;
      }
      while(flashed[currentPixel]);
      
    }
    
    strip.setPixelColor(currentPixel, 255, 255, 255);
    strip.setPixelColor(lastPixelFlashed, colorToFlash);
    strip.show();
    
    flashed[currentPixel] = true;
    lastPixelFlashed = currentPixel;
    
    //while(digitalRead(buttonRed) == HIGH);
    
    delay(20);
  }
  strip.setPixelColor(lastPixelFlashed, baconOrange);
  
  delay(1000);
  
  fadeOut();
}

void flashFadeThroughWhite(long time) //Bacon Approved!
{
  targetMillis = millis() + (1000 * time);
  
  byte pixelToFlash;
  byte lastPixelFlashed = 0;
  byte colors[125][3];
  boolean state = true;
  
  for(i=0; i<125; i++)
  {
    for(j=0; j<4; j++)
    {
      colors[i][j] = 0;
    }
  }
  
  delay(10);
  
  do
  {
    pixelToFlash = random(125);
    
    /*
    do
    {
      a = random(0, 200);
      b = random(0, 200);
      c = random(0, 200);
    } while(a+b+c > 150);
    */
    if(random(0, 2) == 1)
    {
      a = 234;
      b = 82;
      c = 36;
    }
    else
    {
      a = 82;
      b = 174;
      c = 74;
    }
    
    //colorToFlash = strip.Color(a, b, c);
    
    colors[lastPixelFlashed][1] = a;
    colors[lastPixelFlashed][2] = b;
    colors[lastPixelFlashed][3] = c;
    
    colors[pixelToFlash][1] = 255;
    colors[pixelToFlash][2] = 255;
    colors[pixelToFlash][3] = 255;
    
    lastPixelFlashed = pixelToFlash;

    for(i=0; i<125; i++)
    {
      for(j=0; j<4; j++)
      {
        if(colors[i][j] > 1)
        {
          colors[i][j] -= 1;
        }
        else
        {
          colors[i][j] = 0;
        }
      }
    }
    
    for(i=0; i<121; i++)
    {
      strip.setPixelColor(i, colors[i][1], colors[i][2], colors[i][3]);
    }
    
    strip.show();
    
    //while(digitalRead(buttonRed) == HIGH);
    
    delay(20);
  } while(millis() < targetMillis);
}

void insanityChase(long time) //Bacon Approved!
{
  targetMillis = millis() + (1000 * time);
  
  int halfPix = strip.numPixels()/2;
  
  do
  {
    for(i=0; i<strip.numPixels(); i++)
    {
      strip.setPixelColor(i, baconGreen);
    }
    
    j = millis()/time;
    j = j % 10;
    
    for(i=j; i<halfPix; i+= 10)
    {
      strip.setPixelColor(i, baconOrange);
      strip.setPixelColor(strip.numPixels() - i, baconOrange);
    }
    
    strip.show();
    
    delay(time);
  } while(millis() < targetMillis);
}

void setEntireStrip(int r, int g, int b)
{
  for(i=0; i<strip.numPixels(); i++)
  {
    strip.setPixelColor(i, r, g, b);
  }
  strip.show();
}

void fadeOut()
{
  for(i=brightness; i>0; i--)
  {
    //strip.setBrightness(i);
    strip.show();
    delay(0);
  }
  
  setEntireStrip(0, 0, 0);
  
  //strip.setBrightness(brightness);
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t wheel(byte WheelPos) {
  if(WheelPos < 85) {
   return Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  } else if(WheelPos < 170) {
   WheelPos -= 85;
   return Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } else {
   WheelPos -= 170;
   return Color(0, WheelPos * 3, 255 - WheelPos * 3);
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
