package simulation.p5;
import processing.core.PApplet;

public class SineWave extends PApplet {

/**
 * Sine Wave
 * by Daniel Shiffman.  
 * 
 * Render a simple sine wave. 
 */
 
int xspacing = 16;   // How far apart should each horizontal location be spaced
int w;              // Width of entire wave

float theta = 0.0f;  // Start angle at 0
float amplitude = 75.0f;  // Height of wave
float period = 500.0f;  // How many pixels before the wave repeats
float dx;  // Value for incrementing X, a function of period and xspacing
float[] yvalues;  // Using an array to store height values for the wave

public void setup() {
  size(640, 360);
  w = width+16;
  dx = (TWO_PI / period) * xspacing;
  yvalues = new float[w/xspacing];
}

public void draw() {
  background(0);
  calcWave();
  renderWave();
}

public void calcWave() {
  // Increment theta (try different values for 'angular velocity' here
  theta += 0.02f;

  // For every x value, calculate a y value with sine function
  float x = theta;
  for (int i = 0; i < yvalues.length; i++) {
    yvalues[i] = sin(x)*amplitude;
    x+=dx;
  }
}

public void renderWave() {
  noStroke();
  fill(255);
  // A simple way to draw the wave with an ellipse at each location
  for (int x = 0; x < yvalues.length; x++) {
    ellipse(x*xspacing, height/2+yvalues[x], 16, 16);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SineWave" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
