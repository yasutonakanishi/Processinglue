package simulation;

import net.unitedfield.pglue.P4PBezierDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import saito.objloader.OBJModel;
import simulation.p5.Distance2D;

public class P4PBezierDisplaySimulation extends PApplet {
	float rotX, rotY;
		
	OBJModel model;
	
	PApplet	applet1;
	P4PBezierDisplay bezierdisplay;
		
	public	void	setup(){
		size(640,480,P3D);

	    //model = new OBJModel(this, "WalkingGirl.obj", "absolute", TRIANGLES);
		model = new OBJModel(this, "WalkingGirl.obj");	    
	    model.scale(100);
	    model.translate(new PVector(0,0,300));
	    
//		PVector p00 = new PVector(20, 0,-200);
//		PVector	p01 = new PVector(180,0,-30);
//		PVector	p02 = new PVector(220,0,-200);
//		PVector	p03 = new PVector(450,0,-100);
		PVector p00 = new PVector(-200,0,0);
		PVector	p01 = new PVector(50,0,-100);
		PVector	p02 = new PVector(-50,0, 100);
		PVector	p03 = new PVector( 200,0,0);
//		applet1 = new ColorBars();
//		bezierdisplay = new P4PBezierDisplay(this, p00,p01,p02,p03, 300f, applet1, 200,200, false);		
		applet1 = new Distance2D();
		bezierdisplay = new P4PBezierDisplay(this, p00,p01,p02,p03, 300f, applet1,640,360,true);		
		bezierdisplay.translate(0, 0, 0);
	}
	
	public void	draw(){
	    background(200);
	    lights();
	    	
	    translate(width/2, height/2, 0);
	    rotateX(rotX);	    	
	    rotateY(rotY);
	    		    
	    //cross line at 0,0,0	  	  	
	    stroke(0);	  	
	    line(0, 0, -30, 0, 0, 30);	  	  	
	    line(-30, 0, 0, 30, 0, 0);
	    		  	  	
	    // girl			
	    noStroke();	 	    	
	    model.draw();
	    	
	    // display	  	  	
	    bezierdisplay.draw();
	}

	public void mouseDragged(){
	    rotY += (mouseX - pmouseX) * 0.01f;
	    rotX -= (mouseY - pmouseY) * 0.01f;
	}	
}