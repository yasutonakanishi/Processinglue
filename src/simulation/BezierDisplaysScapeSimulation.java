package simulation;

import java.util.ArrayList;

import net.unitedfield.pglue.P4PBezierDisplay;
import processing.core.PApplet;
import processing.core.PVector;
import saito.objloader.OBJModel;
import simulation.p5.ColorBars;

public class BezierDisplaysScapeSimulation extends PApplet {
	float rotX, rotY;
	int	gridsize = 1000;
	
	OBJModel model;
	
	ArrayList<P4PBezierDisplay> displays;
	private float ry;
		
	public	void	setup(){
		size(640,480,P3D);

	    //model = new OBJModel(this, "WalkingGirl.obj", "absolute", TRIANGLES);
		model = new OBJModel(this, "WalkingGirl.obj");	    
	    model.scale(100);
	    model.translate(new PVector(0,0,200));
	    
		PVector p00 = new PVector(-200,0,0);
		PVector	p01 = new PVector(50,0,-100);
		PVector	p02 = new PVector(-50,0, 100);
		PVector	p03 = new PVector( 200,0,0);

		displays = new ArrayList<P4PBezierDisplay>();
		for(int i=0; i< 9; i++){
			PApplet applet = new ColorBars();
			P4PBezierDisplay bezierdisplay = new P4PBezierDisplay(this, p00,p01,p02,p03,150f+random(-20,80), applet, 200, 200, false);		
			bezierdisplay.translate(-400+i%3*400, 0, -400+i/3*400);
			
			displays.add(bezierdisplay);
		}
		
	}
	
	private void drawGrid() {
		fill( color( 0 ));
		stroke(255);			
		for(int i=-10; i<=10;i++){				
			line(i*50, 0, -gridsize/2, i*50, 0, gridsize/2);
			line(-gridsize/2,  0, i*50, gridsize/2, 0, i*50);				
		}
		noStroke();		
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
	    
	    //grid
	    drawGrid();
	    	
	    // displays	  	  	
	    ry += 0.0005f;
	    for(P4PBezierDisplay bezierdisplay : displays){
	    	bezierdisplay.rotate(0f, ry, 0f);	
	    	bezierdisplay.draw();
	    }
	    
	}

	public void mouseDragged(){
	    rotY += (mouseX - pmouseX) * 0.01f;
	    rotX -= (mouseY - pmouseY) * 0.01f;
	}	
}