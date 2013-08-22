package simulation;

import net.unitedfield.pglue.Draw2PGraphics3D;
import net.unitedfield.pglue.P4PCapture;
import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import saito.objloader.OBJModel;
import simulation.p5.Distance2D;

public class P4PCaptureSimulation extends PApplet implements Draw2PGraphics3D {
	float rotX, rotY;
	
	PApplet	appletForDisplay;
	P4PDisplay vDisplay;
	float ry;
	
	P4PCapture	vCapture;
	
	public	void	setup(){
		size(640,480, P3D);
		frameRate(30);
		
		//appletForDisplay = new SineWave();
		appletForDisplay = new Distance2D();		
		vDisplay = new P4PDisplay(500, 100, appletForDisplay, 640, 360);	
		vDisplay.setLocation(0,-150,0);
	    
	    vCapture = new P4PCapture(this, 320, 240);
	    vCapture.setLocation(-100, -20, 80);
	    vCapture.setLookAt(0, 0, 0);
	}
	
	public void	draw(){	    				
		// at first, draw to off-screen and capture it
	    vCapture.read();
	    vCapture.showImageFrame();	    	    
	    
		translate(width/2, height/2+100, 0);
		rotateX(rotY);
		rotateY(rotX);
		
	    draw2PGraphics3D((PGraphics3D)(this.g));		
	}
	
	public	void	draw2PGraphics3D(PGraphics3D g3d){
		g3d.background(200);		
		g3d.lights();
		g3d.pushMatrix();
		//	    	
	    	//cross line at 0,0,0
			g3d.stroke(0);
			g3d.line(0, 0, -30, 0, 0, 30);
			g3d.line(-30, 0, 0, 30, 0, 0);
	    	
	    	// display		
	  	  	ry += 0.001;				
	  	  	vDisplay.setRotation(0f, ry, 0f);	  	  	
	  	  	vDisplay.draw(g3d);
	  	  	
	  	  	// capture
	  	  	vCapture.draw(g3d);
	    //
	  	g3d.popMatrix();
	}
	
	public void mouseDragged(){
	    rotX += (mouseX - pmouseX) * 0.01f;
	    rotY -= (mouseY - pmouseY) * 0.01f;
	}
}
