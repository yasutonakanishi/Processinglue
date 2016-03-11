import net.unitedfield.processinglue.*;

class  Mouse1DContent  extends ContentPApplet {

  Mouse1DContent(int _width, int _height) {
    super(_width, _height);
  }

  void setup() {
    //size(640, 360); //change to surface.setSize(width, height) in the constructor  
    noStroke();
    rectMode(CENTER);
  }

  void draw() {
    background(0.0);

    float r1 = map(mouseX, 0, width, 0, height);
    float r2 = height-r1;

    fill(r1);
    rect(width/2 + r1/2, height/2, r1, r1);

    fill(r2);
    rect(width/2 - r2/2, height/2, r2, r2);
  }
}

//Basic -> Input -> Mouse1D
/*
void setup() {
 size(640, 360);
 noStroke();
 colorMode(RGB, height, height, height);
 rectMode(CENTER);
 }
 
 void draw() {
 background(0.0);
 
 float r1 = map(mouseX, 0, width, 0, height);
 float r2 = height-r1;
 
 fill(r1);
 rect(width/2 + r1/2, height/2, r1, r1);
 
 fill(r2);
 rect(width/2 - r2/2, height/2, r2, r2);
 }
 */