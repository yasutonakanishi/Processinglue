package net.unitedfield.pglue;

import processing.core.PApplet;
import processing.opengl.PGraphics3D;

public class P4PCylinderDisplay extends P4PDisplay {
	float	radius;	
	int tubeRes = 32;
	float[] tubeX ,tubeZ;
	
	public P4PCylinderDisplay(PApplet parentApplet, int dWidth, int dHeight, int tubeRes, 
			PApplet dstApplet, int appletWidth, int appletHeight, boolean frameVisible) {
		super(parentApplet, dWidth, dHeight, dstApplet, appletWidth, appletHeight, frameVisible);		

		this.radius = (float)(dWidth)/ (float)(Math.PI * 2.0);
		this.tubeRes = tubeRes;
		setCircumferencePoints(tubeRes);
	}
	
	public P4PCylinderDisplay(PApplet parentApplet, float radius, float dHeight, int tubeRes, 
			PApplet srcApplet, int appletWidth, int appletHeight, boolean frameVisible) {
		super(parentApplet, (int)(radius * Math.PI *2.0), (int)dHeight, srcApplet, appletWidth, appletHeight, frameVisible);		
		
		this.radius = radius;
		this.tubeRes = tubeRes;		
		setCircumferencePoints(tubeRes);
	}
	
	private	void	setCircumferencePoints(int tubeRes){
		tubeX = new float[tubeRes+1];
		tubeZ = new float[tubeRes+1];
		float angle = 360.0f / tubeRes;
	    for (int i = 0; i < tubeRes+1; i++) {
	      tubeX[i] = parentApplet.cos(parentApplet.radians(i * angle));
	      tubeZ[i] = parentApplet.sin(parentApplet.radians(i * angle));
	    }
	}
	
	public	void	draw(PGraphics3D g){
		g.pushMatrix();		
		g.translate(loc.x, loc.y, loc.z);
		g.rotateX(rot.x);g.rotateY(rot.y);g.rotateZ(rot.z);		
	  	  g.beginShape(g.QUAD_STRIP);
	  	  g.texture(appletImage);	  	  
	  	  for (int i = 0; i < tubeRes+1; i++) {
	  	    float x = tubeX[i] * radius;
	  	    float z = tubeZ[i] * radius;
	  	    float u = appletImage.width / tubeRes * i;
	  	    g.vertex(x, -dHeight, z, u, 0);
	  	    g.vertex(x, 0, z, u, appletImage.height);
	  	  }
	  	  g.endShape();  
		g.popMatrix();	
	}			  
}
