package simulation;

import net.unitedfield.pglue.P4PDisplay;
import processing.core.PApplet;
import processing.opengl.PGraphics3D;
import simulation.p5.SoundSpectrum;

import com.rhizomatiks.bvh.BvhBone;
import com.rhizomatiks.bvh.BvhParser;

public class Perfume001DisplaySimulation extends PApplet{
	BvhParser parserA = new BvhParser();
	PBvh bvh1, bvh2, bvh3;

	PApplet	applet;
	P4PDisplay display;
		
	public void setup(){
	  size( 960, 540, P3D );
	  background( 0 );
	  noStroke();
	  frameRate(30);
	  
	  bvh1 = new PBvh( loadStrings( "nocchi.bvh" ) );
	  bvh2 = new PBvh( loadStrings( "aachan.bvh" ) );
	  bvh3 = new PBvh( loadStrings( "kashiyuka.bvh" ) );
	  
	  applet = new SoundSpectrum();
	  display = new P4PDisplay(this, 128, 120, applet, 512, 480, true);
	  display.translate(640, 60, 0);
		
	  loop();
	}

	public void draw(){
	  background(96);
	  
	  //camera
	  float _cos = cos(millis() / 5000.f);
	  float _sin = sin(millis() / 5000.f);
	  camera(width/4.f + width/4.f * _cos +200, height/2.0f-100, 550 + 150 * _sin,width/2.0f, height/2.0f, -400, 0, 1, 0);
	  
	  //ground 
	  fill( color( 255 ));
	  stroke(127);
	  line(width/2.0f, height/2.0f, -30, width/2.0f, height/2.0f, 30);
	  stroke(127);
	  line(width/2.0f-30, height/2.0f, 0, width/2.0f + 30, height/2.0f, 0);
	  stroke(255);
	  pushMatrix();
	  translate( width/2, height/2-10, 0);
	  scale(-1, -1, -1);
	  
	  //model
	  bvh1.update( millis() );
	  bvh2.update( millis() );
	  bvh3.update( millis() );
	  bvh1.draw();
	  bvh2.draw();
	  bvh3.draw();
	  popMatrix();
	  		
	  display.draw();		
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
	}
	//PBvh	 	
}

