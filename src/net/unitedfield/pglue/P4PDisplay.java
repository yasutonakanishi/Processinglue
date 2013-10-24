package net.unitedfield.pglue;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class P4PDisplay {
	PApplet parentApplet, dstApplet;
	PImage	appletImage;
	
	JFrame	frame;
	private final int	frameBarHeight =20;
	boolean frameVisible = false;
	
	PVector	loc, rot;
	float 	dWidth=100,	dHeight=100;	//width and height of Display
		
	public	P4PDisplay(PApplet parentApplet, float dWidth, float dHeight, 
						PApplet dstApplet, int appletWidth, int appletHeight, boolean frameVisible){
		this.parentApplet = parentApplet;
		this.dWidth  = dWidth;
		this.dHeight = dHeight;
		this.dstApplet = dstApplet;			
		this.frameVisible = frameVisible;
		
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(appletWidth, appletHeight+frameBarHeight);        
        if(frameVisible)
        	frame.setVisible(true);
        frame.add(dstApplet, BorderLayout.CENTER);
        frame.setResizable(false);
        
		dstApplet.registerMethod("pre", this);
		dstApplet.registerMethod("post", this);		
		dstApplet.init();	
		this.appletImage = new PImage(appletWidth, appletHeight);
		
		this.loc = new PVector(0,0,0);
		this.rot = new PVector(0,0,0);
	}
	
	public void pre() {		
		this.dstApplet.unregisterMethod("pre", this);
		//* after unregistaration, this pre() is not called from the PApplet. Therefore, following methods are called only once.		
	}	
		
	public void post(){		
		try{
			this.dstApplet.loadPixels();
			for (int i = 0; i < this.dstApplet.pixels.length; i++) 
				this.appletImage.pixels[i] = this.dstApplet.pixels[i];
			this.appletImage.updatePixels();		
		}catch(ArrayIndexOutOfBoundsException aiobe){			
		}
	}
	
	public	void	translate(float x, float y, float z){
		this.loc.x = x;
		this.loc.y = y;
		this.loc.z = z;
	}
	
	public	void	rotate(float rx, float ry, float rz){
		this.rot.x = rx;
		this.rot.y = ry;
		this.rot.z = rz;
	}
	
	public	void	draw(){
		this.draw((PGraphics3D)(this.parentApplet.g));
	}
	
	public	void	draw(PGraphics3D g){
		g.pushMatrix();		
		g.translate(loc.x, loc.y, loc.z);
		g.rotateX(rot.x);g.rotateY(rot.y);g.rotateZ(rot.z);		
		drawShape(g);
		g.popMatrix();
	}
	
	protected	void	drawShape(PGraphics3D g){
		g.beginShape();		  
		g.texture(appletImage);
		g.vertex(-dWidth/2, -dHeight, 0, 0, 0);
		g.vertex( dWidth/2, -dHeight, 0, appletImage.width, 0);		  
		g.vertex( dWidth/2,  0,       0, appletImage.width, appletImage.height);		 
		g.vertex(-dWidth/2,  0,       0, 0, appletImage.height);
//		g.vertex(-dWidth/2, 0, 0, 0, 0);
//		g.vertex( dWidth/2, 0, 0, appletImage.width, 0);		  
//		g.vertex( dWidth/2, dHeight, 0, appletImage.width, appletImage.height);		 
//		g.vertex(-dWidth/2, dHeight, 0, 0, appletImage.height);
		g.endShape();	
	}
		
	public	PImage	getPImage(){
		return this.appletImage;
	}
	public PApplet	getApplet(){
		return this.dstApplet;
	}
}
