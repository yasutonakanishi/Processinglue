import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import SimpleOpenNI.SimpleOpenNI;

public class KinectPersonCaptureTest extends PApplet implements Draw2PGraphics3D {

	SimpleOpenNI	context;
	float			kinectZoomF =0.3f;
	float			kinectRotX = radians(180);  // by default rotate the hole scene 180deg around the x-axis, 
                                   // the data from openni comes upside down
	float			kinectRotY = radians(0);
	float			kinectX=0, kinectY=0, kinectZ=-1000;
	
	P4PCapture		capture;
	P4PDisplay		display;
	
	float rotX, rotY;

	public void setup(){
		size(1024,768,P3D);
				 
		capture	= new P4PCapture(this,320,240);
		Mirror2 mirror2 = new Mirror2();
		mirror2.setCapture(capture);
		display	= new P4PDisplay(800, 600, mirror2, 320, 240);		  
		capture.setLocation(width/2, 200, 350);		  
		capture.setLookAt(width/2,   200, -200);  
		display.setLocation(width/2, -300, -1500);
		
		context = new SimpleOpenNI(this);
		// disable mirror
		context.setMirror(false);
		// enable depthMap generation 
		if(context.enableDepth() == false){
			println("Can't open the depthMap, maybe the camera is not connected!"); 
			exit();
			return;
		}
		if(context.enableRGB() == false){
			println("Can't open the rgbMap, maybe the camera is not connected or there is no rgbSensor!"); 
			exit();
			return;
		}  
		// align depth data to image data
		context.alternativeViewPointDepthToImage();
		
		stroke(255,255,255);
		smooth();
		perspective(radians(45),
              PApplet.parseFloat(width)/PApplet.parseFloat(height), 
              10,150000);
	}

	public void draw(){							
		// update the kinect				
		context.update();		  	

		//capture contents drawn at offscreenPGraphics3D		  
		capture.read();  		  
		capture.showImageFrame();	  
		/* when size of camera and PApplet are different, draw to off-screen for camera and then draw to the main screen*/		

		//	move camera for main window
		this.translate(width/2, height/2, 0);
		this.rotateX(rotY);
		this.rotateY(rotX);
		//draw contents to this PGraphics3D		  			
		draw2PGraphics3D((PGraphics3D)(this.g));	
	}

	public void draw2PGraphics3D(PGraphics3D g3d) {		
		g3d.background(0);	
		g3d.lights();		
    	
		//ground 
		g3d.fill( color( 255 ));
		g3d.stroke(255);			
		for(int i=-10; i<=10;i++){				
			g3d.line(width/2.0f+i*50, height/2.0f, -500, width/2.0f+i*50, height/2.0f, 500);
			g3d.line(width/2.0f-500, height/2.0f,  i*50, width/2.0f+500, height/2.0f, i*50);				
		}	
		
		g3d.pushMatrix(); //begin ofkinect				
			g3d.rotateX(kinectRotX);
			g3d.rotateY(kinectRotY);
			g3d.scale(kinectZoomF);		
			PImage  rgbImage = context.rgbImage();
			int[]   depthMap = context.depthMap();
			int     steps   = 3;  // to speed up the drawing, draw every third point
			int     index;
			PVector realWorldPoint;
			int   pixelColor;
 
			g3d.strokeWeight(steps);
			g3d.translate(kinectX,kinectY,kinectZ);  // set the rotation center of the scene 1000 infront of the camera
			PVector[] realWorldMap = context.depthMapRealWorld();
			for(int y=0;y < context.depthHeight();y+=steps){
				for(int x=0;x < context.depthWidth();x+=steps){
					index = x + y * context.depthWidth();
					if(depthMap[index] > 0){ 
						// get the color of the point
						pixelColor = rgbImage.pixels[index];
						g3d.stroke(pixelColor);        
						// draw the projected point
						realWorldPoint = realWorldMap[index];
						g3d.point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);  // make realworld z negative, in the 3d drawing coordsystem +z points in the direction of the eye
					}
				}
			} 
		g3d.popMatrix(); // end of kinect		
					
		display.draw(g3d);
		capture.draw(g3d);					
	}
	
	public void mouseDragged(){
	    rotX += (mouseX - pmouseX) * 0.01f;
	    rotY -= (mouseY - pmouseY) * 0.01f;
	}
	
	public void keyPressed(){
		switch(key){
			case ' ':
				context.setMirror(!context.mirror());
				break;			
			case 'a':
				kinectX-=50;
				break;
			case 'd':
				kinectX+=50;
				break;
			case 'w':
				kinectY+=50;
				break;
			case 's':
				kinectY-=50;
				break;				
		}
		
		switch(keyCode){
			case LEFT:
				kinectRotY += 0.1f;
				break;
			case RIGHT:
				// zoom out
				kinectRotY -= 0.1f;
				break;
			case UP:
				if(keyEvent.isShiftDown())
					kinectZoomF += 0.02f;
				else
					kinectRotX += 0.1f;
				break;
			case DOWN:
				if(keyEvent.isShiftDown()){
					kinectZoomF -= 0.02f;
					if(kinectZoomF < 0.01f)
						kinectZoomF = 0.01f;
				}else
					kinectRotX -= 0.1f;
				break;			
		}
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "KinectPersonCaptureTest" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
