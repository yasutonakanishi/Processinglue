import java.awt.BorderLayout;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class P4PDisplay {
	PApplet srcApplet;
	PImage	appletImage;
	
	JFrame	frame;
	boolean frameVisible = true;
	
	PVector	loc, rot;
	int 	dWidth=100,	dHeight=100;	//width and height of Display
		
	P4PDisplay(int dWidth, int dHeight, PApplet srcApplet, int appletWidth, int appletHeight){
		this.dWidth  = dWidth;
		this.dHeight = dHeight;
		this.srcApplet = srcApplet;			
		
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(appletWidth, appletHeight);        
        if(frameVisible)
        	frame.setVisible(true);
        frame.add(srcApplet, BorderLayout.CENTER);
        frame.setResizable(false);
        
		srcApplet.registerMethod("pre", this);
		srcApplet.registerMethod("post", this);		
		srcApplet.init();	
		this.appletImage = new PImage(appletWidth, appletHeight);
		
		this.loc = new PVector(0,0,0);
		this.rot = new PVector(0,0,0);
	}
	
	public void pre() {		
		this.srcApplet.unregisterMethod("pre", this);
		//* after unregistaration, this pre() is not called from the PApplet. Therefore, following methods are called only once.		
	}	
		
	public void post(){		
		try{
			this.srcApplet.loadPixels();
			for (int i = 0; i < this.srcApplet.pixels.length; i++) 
				this.appletImage.pixels[i] = this.srcApplet.pixels[i];
			this.appletImage.updatePixels();		
		}catch(ArrayIndexOutOfBoundsException aiobe){
			
		}
	}
	
	public	void	setLocation(float x, float y, float z){
		this.loc.x = x;
		this.loc.y = y;
		this.loc.z = z;
	}
	public	void	setRotation(float rx, float ry, float rz){
		this.rot.x = rx;
		this.rot.y = ry;
		this.rot.z = rz;
	}
	
	public	void	draw(PGraphics3D g){
		g.pushMatrix();		
		g.translate(loc.x, loc.y, loc.z);
		g.rotateX(rot.x);g.rotateY(rot.y);g.rotateZ(rot.z);		
		g.beginShape();		  
			g.texture(appletImage);
			g.vertex(-dWidth/2, -dHeight/2, 0, 0, 0);
			g.vertex( dWidth/2, -dHeight/2, 0, appletImage.width, 0);		  
			g.vertex( dWidth/2,  dHeight/2, 0, appletImage.width, appletImage.height);		 
			g.vertex(-dWidth/2,  dHeight/2, 0, 0, appletImage.height);			
		g.endShape();		
		g.popMatrix();		
	}
		
	public	PImage	getPImage(){
		return appletImage;
	}
	public PApplet	getApplet(){
		return this.srcApplet;
	}
}
