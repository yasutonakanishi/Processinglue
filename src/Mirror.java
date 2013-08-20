import processing.core.PApplet;
import processing.video.Capture;

public class Mirror extends PApplet {

/**
 * Mirror 
 * by Daniel Shiffman.  
 *
 * Each pixel from the video source is drawn as a rectangle with rotation based on brightness.   
 */ 
 



// Size of each cell in the grid
int cellSize = 20;
// Number of columns and rows in our system
int cols, rows;
// Variable for capture device
Capture video;
boolean realDeployment = false;

public void setup() {
  size(320, 240);
  //frameRate(30);
  cols = width / cellSize;
  rows = height / cellSize;
  colorMode(RGB, 255, 255, 255, 100);

  if(realDeployment) { // added for P4P
	  video = new Capture(this, width, height);
	  video.start();
  }
  
  background(0);
}


public	void setCapture(Capture capture){ // added for P4P
	this.video = capture;
}

public void draw() { 
  if (video.available()) {
    video.read();
    video.loadPixels();
  
    // Begin loop for columns
    for (int i = 0; i < cols; i++) {
      // Begin loop for rows
      for (int j = 0; j < rows; j++) {
      
        // Where are we, pixel-wise?
        int x = i*cellSize;
        int y = j*cellSize;
        int loc = (video.width - x - 1) + y*video.width; // Reversing x to mirror the image
      
        float r = red(video.pixels[loc]);
        float g = green(video.pixels[loc]);
        float b = blue(video.pixels[loc]);
        // Make a new color with an alpha component
        int c = color(r, g, b, 75);
      
        // Code for drawing a single rect
        // Using translate in order for rotation to work properly
        pushMatrix();
        translate(x+cellSize/2, y+cellSize/2);
        // Rotation formula based on brightness
        rotate((2 * PI * brightness(c) / 255.0f));
        rectMode(CENTER);
        fill(c);
        noStroke();
        // Rects are larger than the cell for some overlap
        rect(0, 0, cellSize+6, cellSize+6);
        popMatrix();
      }
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mirror" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
