/**
This is a sample using PglueDisplay. 
It simulates a large display showing a PApplet.
The first window is the simulation 3D PApplet and the second small window is the content PApplet of PglueDisplay.
You can change the location and the size of the display with ControlP5 sliders,
and change the angle to see the simulation by dragging your mouse.
@author Yasuto Nakanishi
*/

import net.unitedfield.processinglue.*;
import controlP5.*;

PglueFloor pgFloor;
PglueDisplay pgDisplay;
int  pgDisplayX, pgDisplayY, pgDisplayZ, pgDisplayWidth, pgDisplayHeight;
ControlP5 cp5;
ContentPApplet content;
PShape model;
float rotX, rotY;

void setup() {
  size(600, 600, P3D);

  pgFloor = new PglueFloor(2000, 20, this);
  model = loadShape("C-3PO_clean/C-3PO.obj");
  model.scale(50);
  model.rotateZ(PI);

  content = new Mouse1DContent(640, 360);
  pgDisplay = new PglueDisplay(640, 180, content, this);
  cp5 = new ControlP5(this);
  cp5.addSlider("pgDisplayX").setPosition(50, 50).setRange(-500, 500);
  cp5.addSlider("pgDisplayY").setPosition(50, 70).setRange(-500, 500);
  cp5.addSlider("pgDisplayZ").setPosition(50, 90).setRange(-500, 500);
  cp5.addSlider("pgDisplayWidth").setPosition(50, 110).setRange(0, 1000).setValue(640);
  cp5.addSlider("pgDisplayHeight").setPosition(50, 130).setRange(0, 1000).setValue(180);  
}

void draw() {
  pgDisplay.size(pgDisplayWidth, pgDisplayHeight);
  pgDisplay.translate(pgDisplayX, pgDisplayY, pgDisplayZ);
  

  background(128);    
  lights();

  pushMatrix();
  translate(width/2, height/2, 0);
  rotateX(rotY);
  rotateY(rotX);
  scale(0.5);

  pushMatrix();
  noStroke();
  translate(0, 0, -200);
  shape(model);    
  translate(100, 0, -50);
  shape(model);    
  popMatrix();

  pgDisplay.draw();
  pgFloor.draw();
  popMatrix();
}

void mouseDragged() {
  rotX += (mouseX - pmouseX) * 0.01;
  rotY -= (mouseY - pmouseY) * 0.01;
}