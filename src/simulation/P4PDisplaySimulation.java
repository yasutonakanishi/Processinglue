package simulation;

import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import saito.objloader.OBJModel;
import simulation.p5.Distance2D;

public class P4PDisplaySimulation extends PApplet {
	PApplet	appletForDisplay;
	P4PDisplay display;	
	float ry;

	OBJModel model;
	float rotX, rotY;
	
	public	void	setup(){
		size(640,480,P3D);
		frameRate(30);
		
		//appletForDisplay = new SineWave();
		appletForDisplay = new Distance2D();		
		display = new P4PDisplay(500, 100, appletForDisplay, 640, 360);	
		display.setLocation(0,-150,0);

	    //model = new OBJModel(this, "WalkingGirl.obj", "absolute", TRIANGLES);
		model = new OBJModel(this, "WalkingGirl.obj");	    
	    model.scale(100);
	    model.translate(new PVector(0,0,300));   
	}
	
	public void	draw(){
	    background(200);
	    lights();
	    pushMatrix();
	    //
	    	translate(width/2, height/2, 0);
	    	rotateX(rotY);
	    	rotateY(rotX);
	    	
	    	//cross line at 0,0,0
	  	  	stroke(0);
	  	  	line(0, 0, -30, 0, 0, 30);
	  	  	line(-30, 0, 0, 30, 0, 0);
	    	
	  	  	// girl
			noStroke();	 
	    	model.draw();

	    	// display
	  	  	ry += 0.001;				
	  	  	display.setRotation(0f, ry, 0f);				
	  	  	display.draw((PGraphics3D)(this.g));
	    //
	    popMatrix();	    	    
	}

	public void mouseDragged(){
	    rotX += (mouseX - pmouseX) * 0.01f;
	    rotY -= (mouseY - pmouseY) * 0.01f;
	}
}