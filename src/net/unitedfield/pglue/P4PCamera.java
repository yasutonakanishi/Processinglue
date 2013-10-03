package net.unitedfield.pglue;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class P4PCamera {
	//Draw2Offscreen	parentPApplet;
	public	PGraphics3D	cameraGraphics;
	PImage		cameraImage;
	PVector	loc, lookAt;
	PFrustumPyramid	frustum;
	
	private	JFrame	frame;
	private final int	frameBarHeight =20;
	private	JPanel	panel;
	
	public	P4PCamera(PApplet parent, int width, int height){		
		cameraGraphics = (PGraphics3D)parent.createGraphics(width, height, PApplet.P3D);
		//this.parentPApplet = (Draw2Offscreen)parent;
		
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(cameraGraphics.width, cameraGraphics.height+frameBarHeight);                	
       	panel = new JPanel();        	        
    	frame.add(panel, BorderLayout.CENTER);
    	frame.setResizable(false);        	        
    	frame.setVisible(false); 
    	
    	this.loc = new PVector(0,0,0);
    	this.lookAt = new PVector(0,0,0);    	
    	frustum = new PFrustumPyramid(loc, lookAt, cameraGraphics);	
	}
		
	public	void	setLocation(float x, float y, float z){
		this.loc.x = x;
		this.loc.y = y;
		this.loc.z = z;
		frustum.setLocation(x, y, z);
	}
	
	public	void	setLookAt(float x, float y, float z){
		this.lookAt.x = x;
		this.lookAt.y = y;
		this.lookAt.z = z;
		frustum.setLookAt(x,y,z);
	}
		
	public	PImage capture(Draw2PGraphics3D dst){
		cameraGraphics.beginDraw();
		cameraGraphics.camera(
				loc.x, loc.y, loc.z,			//eyeX, eyeY, eyeZ, 
				lookAt.x, lookAt.y, lookAt.z, 	//centerX, centerY, centerZ,
				0, 1, 0);					// upX, upY, upZ)		
		dst.draw2PGraphics3D(cameraGraphics); //draw 3D world as same as parent PApplet
		cameraGraphics.endDraw();				
		cameraImage = cameraGraphics.get(0, 0, cameraGraphics.width, cameraGraphics.height);
		return cameraImage;
	}
		
	public	void showImageFrame(){                      
		if(panel !=null && frame!=null){
			if(frame.isVisible() == false)
				frame.setVisible(true);
			panel.getGraphics().drawImage((Image)cameraImage.getNative(),0,0,null);			
		}
	}	
	
	
	public	void	draw(PGraphics3D g){		
		g.stroke(128);
		g.strokeWeight(1);		
		g.pushMatrix();
		g.translate(loc.x,loc.y,loc.z);		
		g.sphere(3);		
		g.popMatrix();		
		if(frustum != null)
			frustum.draw(g);
	}	
}