import processing.core.PApplet;
import processing.video.Capture;

public class Mirror2 extends PApplet {

/**
 * Mirror 2 
 * by Daniel Shiffman. 
 *
 * Each pixel from the video source is drawn as a rectangle with size based on brightness.  
 */
 
// Size of each cell in the grid
int cellSize = 15;
// Number of columns and rows in our system
int cols, rows;
// Variable for capture device
Capture video = null;

private boolean realDeployment=false;
//private boolean realDeployment=true;

public void setup() {
  size(320, 240);
  // Set up columns and rows
  cols = width / cellSize;
  rows = height / cellSize;
  colorMode(RGB, 255, 255, 255, 100);
  rectMode(CENTER);

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
	if(video==null)
		return;
	if (video.available()) {
		if(realDeployment == true)//added for P4P, in a virtual simulation, please call capture.read() in the simulation code.
			video.read();
		else
			video.loadPixels();		
		
		background(0, 0, 255);
		// Begin loop for columns
		for (int i = 0; i < cols;i++) {
			// Begin loop for rows
			for (int j = 0; j < rows;j++) {

				// Where are we, pixel-wise?
				int x = i * cellSize;
				int y = j * cellSize;
				int loc = (video.width - x - 1) + y*video.width; // Reversing x to mirror the image

				// Each rect is colored white with a size determined by brightness
				int c = video.pixels[loc];
				
				float sz = (brightness(c) / 255.0f) * cellSize;
				fill(255);
				noStroke();
				rect(x + cellSize/2, y + cellSize/2, sz, sz);
			}
		}
	}	
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mirror2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
