package simulation;

import net.unitedfield.pglue.Draw2PGraphics3D;
import net.unitedfield.pglue.P4PCamera;
import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.opengl.PGraphics3D;
import simulation.p5.Distance2D;

public class P4PCameraSimulation extends PApplet implements Draw2PGraphics3D {
	float rotX, rotY;
	
	PApplet	appletForDisplay;
	P4PDisplay vDisplay;
	float ry;
	
	P4PCamera	vCamera;
	
	public	void	setup(){
		size(640,480, P3D);
		frameRate(30);
		
		//appletForDisplay = new SineWave();
		appletForDisplay = new Distance2D();		
		vDisplay = new P4PDisplay(500, 100, appletForDisplay, 640, 360);	
		vDisplay.setLocation(0,-150,0);
 	    
	    vCamera = new P4PCamera(this, 320, 240);
	    vCamera.setLocation(200, 0, -100);
	    vCamera.setLookAt(0, 0, 0);
	}
	
	public void	draw(){						    	  
	    vCamera.capture(this);//camera calls draw2PGraphics3DOff
	    vCamera.showImageFrame();	    
	    	    
		this.g.translate(width/2, height/2, 0);
		this.g.rotateX(rotY);
		this.g.rotateY(rotX);	
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
	  	  	
	  	  	vCamera.draw(g3d);
	    //
	  	g3d.popMatrix();
	}
	
	public void mouseDragged(){
	    rotX += (mouseX - pmouseX) * 0.01f;
	    rotY -= (mouseY - pmouseY) * 0.01f;
	}
}
