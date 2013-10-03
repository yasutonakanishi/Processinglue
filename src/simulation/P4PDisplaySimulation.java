package simulation;

import net.unitedfield.pglue.P4PCylinderDisplay;
import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import saito.objloader.OBJModel;
import simulation.p5.Distance2D;
import simulation.p5.SineWave;

public class P4PDisplaySimulation extends PApplet {
	float rotX, rotY;
	
	OBJModel model;

	PApplet	appletForDisplay;
	P4PDisplay display;
	float ry;
	
	public	void	setup(){
		size(640,480,P3D);
		frameRate(30);
		
		appletForDisplay = new Distance2D();		
		display = new P4PDisplay(this, 500, 100, appletForDisplay, 640, 360, true);
		display.translate(0,-150,0);
				
	    //model = new OBJModel(this, "WalkingGirl.obj", "absolute", TRIANGLES);
		model = new OBJModel(this, "WalkingGirl.obj");	    
	    model.scale(100);
	    model.translate(new PVector(0,0,300));   
	}
	
	public void	draw(){
	    background(200);
	    lights();

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
	  	  	display.rotate(0f, ry, 0f);	
	  	  	display.draw(); 	
	}

	public void mouseDragged(){
	    rotX += (mouseX - pmouseX) * 0.01f;
	    rotY -= (mouseY - pmouseY) * 0.01f;
	}
}