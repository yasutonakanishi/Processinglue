package simulation;

import net.unitedfield.pglue.P4PCylinderDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import saito.objloader.OBJModel;
import simulation.p5.ColorBars;
import simulation.p5.SineWave;

public class P4PCylinderDisplaySimulation extends PApplet {
	float rotX, rotY;
		
	OBJModel model;
	
	PApplet	applet1, applet2;
	P4PCylinderDisplay cylinderdisplay1, cylinderdisplay2;
	
	public	void	setup(){
		size(640,480,P3D);

	    //model = new OBJModel(this, "WalkingGirl.obj", "absolute", TRIANGLES);
		model = new OBJModel(this, "WalkingGirl.obj");	    
	    model.scale(100);
	    model.translate(new PVector(0,0,300));
	    
		applet1 = new ColorBars();
		applet2 = new SineWave();
		cylinderdisplay1 = new P4PCylinderDisplay(this, 30f, 100f, 32, applet1, 200,200, false);
		cylinderdisplay2 = new P4PCylinderDisplay(this, 29f, 100f, 32, applet2, 640,360, false);
		cylinderdisplay1.translate(0,-10, 0);
		cylinderdisplay2.translate(0,-10, 0);		
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
  	  	cylinderdisplay1.draw();	
  	  	cylinderdisplay2.draw();
	}

	public void mouseDragged(){
	    rotY += (mouseX - pmouseX) * 0.01f;
	    rotX -= (mouseY - pmouseY) * 0.01f;
	}	
}