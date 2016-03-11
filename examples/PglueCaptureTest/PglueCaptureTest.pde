/**
This is a sample using PglueCapture. 
It simulates a flying camera and a large display that shows the captured image by the flying camera.
The first window is the simulation 3D PApplet and the second small window is the content PApplet of PglueDisplay.
You can move the camera for the simulation; arrow keys change the direction and WSADQZ keys change the location.
@author Yasuto Nakanishi
*/

import net.unitedfield.processinglue.*;

PglueCapture  pgCapture;
PglueDisplay  pgDisplay;
CaptureShowContent  captureContentApplet;
PglueFloor pgFloor;
PShape ironMan, c3po;

PVector cameraLoc, cameraDir, cameraMoveDir;
float cameraSpeed = 15.0, cameraRotateSpeed = 0.01, cameraPitch, cameraYaw;

void  setup() {
  size(600, 600, P3D);  
  cameraLoc = new PVector(0, -200, 1200);
  cameraDir = new PVector();
  cameraPitch = 0;
  cameraYaw = PI;
  updateCameraDir();
  cameraMoveDir = new PVector();

  pgCapture = new PglueCapture(320, 240, this, "renderLandscape");
  pgCapture.setLookAt(0, -180, 0);
  pgCapture.setLocation(0, -360, 600);

  captureContentApplet  = new CaptureShowContent(320, 240, pgCapture);
  captureContentApplet.setup();

  pgDisplay = new PglueDisplay(640, 360, captureContentApplet, this);
  pgDisplay.translate(0, 0, -100);

  pgFloor = new PglueFloor(2000, 20, this);
  ironMan = loadShape("Iron_Man/Iron_Man.obj");
  c3po    = loadShape("C-3PO_clean/C-3PO.obj");
}

void  draw() {  
  pgCapture.setLocation((int)(200.0*cos(ry)), -180+(int)(200.0*sin(ry)), 600);
  pgCapture.read();

  camera(cameraLoc.x, cameraLoc.y, cameraLoc.z, cameraLoc.x+cameraDir.x*100.0, cameraLoc.y+cameraDir.y*100.0, cameraLoc.z+cameraDir.z*100.0, 0, 1, 0);
  renderLandscape((PGraphics3D)this.g);
}

float ry;
void renderLandscape(PGraphics3D dstg) {
  background(128);
  dstg.lights();

  dstg.pushMatrix();
  dstg.translate(0, -100, 200);
  dstg.rotateZ(PI);
  dstg.scale(50);
  dstg.rotateY(ry*0.2);
  dstg.shape(ironMan);  
  ry += 0.02;
  dstg.popMatrix();

  dstg.pushMatrix();
  dstg.translate(200, 0, 200);
  dstg.rotateZ(PI);
  dstg.rotateY(PI/2);  
  dstg.scale(50);
  dstg.shape(c3po);
  dstg.popMatrix();

  pgCapture.draw(dstg);
  pgDisplay.draw(dstg);
  pgFloor.draw(dstg);
}

void keyPressed() {
  if (key == CODED) {      
    if (keyCode == UP) {    
      cameraPitch -= cameraRotateSpeed;
    } else if (keyCode == DOWN) {
      cameraPitch += cameraRotateSpeed;
    } else if (keyCode == RIGHT) {
      cameraYaw -= cameraRotateSpeed;
    } else if (keyCode == LEFT) {
      cameraYaw += cameraRotateSpeed;
    }
    updateCameraDir();
  } else {
    if (key == 'w') {
      cameraMoveDir = PVector.mult(cameraDir, cameraSpeed);
    } else if (key == 's') {
      cameraMoveDir = PVector.mult(cameraDir, -cameraSpeed);
    }
    if (key == 'q') {      
      cameraMoveDir.x = cameraMoveDir.z = 0;
      cameraMoveDir.y = -cameraSpeed;
    } else if (key == 'z') {
      cameraMoveDir.x = cameraMoveDir.z = 0;
      cameraMoveDir.y = cameraSpeed;
    } else if (key == 'a') {
      cameraMoveDir.x = cos(cameraYaw) * cameraSpeed;
      cameraMoveDir.z = -sin(cameraYaw) * cameraSpeed;
    } else if (key == 'd') {
      cameraMoveDir.x = -cos(cameraYaw) * cameraSpeed;
      cameraMoveDir.z = sin(cameraYaw) * cameraSpeed;
    }
    cameraLoc.add(cameraMoveDir);
  }
}

void updateCameraDir() {
  cameraDir.x = sin(cameraYaw);    
  cameraDir.y = sin(cameraPitch);
  cameraDir.z = cos(cameraYaw);
  cameraDir.normalize();
}