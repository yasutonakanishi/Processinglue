import processing.video.*;

class CaptureShowContent  extends ContentPApplet {
  Capture video;

  CaptureShowContent(int _width, int _height, Capture _video) {
    super(_width, _height);
    this.video = _video;
  }

  void  setup() {
    //size(320, 240); //change to surface.setSize(width, height) in the constructor
    noStroke();
  }

  void  draw() {
    background(0);

    if (video != null) {
      video.loadPixels();        
      image(video, 0, 0, width, height); // Draw the webcam video onto the screen
    }
  }
}