package simulation;

import net.unitedfield.pglue.Draw2PGraphics3D;
import net.unitedfield.pglue.P4PCapture;
import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.opengl.PGraphics3D;
import simulation.p5.opticalflow;

import com.rhizomatiks.bvh.BvhBone;
import com.rhizomatiks.bvh.BvhParser;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Perfume001CaptureSimulation extends PApplet implements Draw2PGraphics3D {
	BvhParser parserA = new BvhParser();
	PBvh bvh1, bvh2, bvh3;

	Minim minim;
	AudioPlayer player;

	PApplet		applet;
	P4PDisplay 	display;
	P4PCapture	capture;
		
	public void setup(){
	  size(960, 540, P3D );
	  background( 0 );
	  noStroke();
	  frameRate(30);
	  
	  // set perfume
	  bvh1 = new PBvh( loadStrings( "nocchi.bvh" ) );
	  bvh2 = new PBvh( loadStrings( "aachan.bvh" ) );
	  bvh3 = new PBvh( loadStrings( "kashiyuka.bvh" ) );
	  	  	   
	  // set devices: camera, PApplet, display
	  capture	= new P4PCapture(this,320,240);
	  opticalflow opt = new opticalflow();
	  opt.setCapture(capture);
	  display	= new P4PDisplay(this, 1600, 1200, opt, 320, 240, true);	  	  	  
	  // deploy devices
	  capture.setLocation(width/2, 200, 350);
	  capture.setLookAt(width/2,   200, -200);
	  display.translate(width/2, -300, -1500);

	  // set music
	  minim = new Minim(this);
	  player = minim.loadFile("Perfume_globalsite_sound.wav");
	  player.play();
	  
	  loop();
	}

	public void draw(){ 	  
	  //drawn contents to off-screen PGraphics3D and capture
	  capture.read();  
	  capture.showImageFrame();	  
	  
	  //move camera for main window
	  float _cos = cos(millis() / 5000.f);
	  float _sin = sin(millis() / 5000.f);
	  camera( width/4.f + width/4.f * _cos +200, height/2.0f-30, 550 + 150 * _sin, 
			  width/2.0f, height/2.0f, -400, 
			  0, 1, 0);
	  /* when size of camera and PApplet are different, draw to off-screen for camera and then draw to the main screen*/ 	  
	  //draw contents to main window PGraphics3D
	  draw2PGraphics3D((PGraphics3D)(this.g));	
	}

	@Override
	public void draw2PGraphics3D(PGraphics3D g3d) {
		g3d.background(0);	
		  
		//ground 
		g3d.fill( color( 255 ));
		g3d.stroke(255);			
		for(int i=-10; i<=10;i++){				
			g3d.line(width/2.0f+i*50, height/2.0f, -500, width/2.0f+i*50, height/2.0f, 500);
			g3d.line(width/2.0f-500, height/2.0f,  i*50, width/2.0f+500, height/2.0f, i*50);				
		}		
					
		g3d.stroke(255);			
		//  model
		g3d.pushMatrix();
			g3d.translate( width/2, height/2-10, 0);
			g3d.scale(-1, -1, -1);		
			//model
			bvh1.update( millis() );		  
			bvh2.update( millis() );		  
			bvh3.update( millis() );		  
			bvh1.draw(g3d);		 
			bvh2.draw(g3d);		 
			bvh3.draw(g3d);		
		g3d.popMatrix();
		
		//display
		//if(g3d == this.g) // if you want that display is not captured with camera. this works like putting a optical filter to camera
			//display.draw(g3d);
		display.draw(g3d);	
		capture.draw(g3d);
	}
	
	// PBvh
	public class PBvh{
	  public BvhParser parser;  

	  public PBvh(String[] data)
	  {
	    parser = new BvhParser();
	    parser.init();
	    parser.parse( data );
	  }
	  
	  public void update( int ms )
	  {
	    parser.moveMsTo( ms );//30-sec loop 
	    parser.update();
	  }
	  	
	  public void draw()
	  {
	    fill(color(255));
	    
	    for( BvhBone b : parser.getBones())
	    {
	      pushMatrix();
	      translate(b.absPos.x, b.absPos.y, b.absPos.z);
	      ellipse(0, 0, 2, 2);
	      popMatrix();
	      if (!b.hasChildren())
	      {
	        pushMatrix();
	        translate( b.absEndPos.x, b.absEndPos.y, b.absEndPos.z);
	        ellipse(0, 0, 10, 10);
	        popMatrix();
	      }	        
	    }
	  }
	  
	  public void draw(PGraphics3D g3d){		    
		  g3d.fill(color(255));		    		    
		  for( BvhBone b : parser.getBones()){		    	
			  g3d.pushMatrix();		    	
			  g3d.translate(b.absPos.x, b.absPos.y, b.absPos.z);			  
			  g3d.ellipse(0, 0, 3, 3);
			  g3d.popMatrix();		    	
			  if (!b.hasChildren()){		    		
				  g3d.pushMatrix();		    		
				  g3d.translate( b.absEndPos.x, b.absEndPos.y, b.absEndPos.z);			  
				  g3d.ellipse(0, 0, 15, 15);
				  g3d.popMatrix();		    	
			  }		    
		  }		  
	  }	  
	}
	//PBvh	 	
}

