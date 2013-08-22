package net.unitedfield.pglue;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import processing.video.Capture;

public class P4PCapture extends Capture{
	protected	Draw2PGraphics3D	parentPApplet;
	protected	PGraphics3D	cameraGraphics;
	protected	PImage		cameraImage;	
	
	protected	PVector	loc, lookAt, dir;
	protected	PFrustumPyramid	frustum;
	protected	FacingPlane	facingPlane;
	
	protected	JFrame	frame;
	private final int	frameBarHeight =20;
	protected	JPanel	panel;
	
	public P4PCapture(PApplet parent, int requestWidth, int requestHeight) {
		super(parent, requestWidth, requestHeight);
		
		cameraGraphics = (PGraphics3D)parent.createGraphics(requestWidth, requestHeight, PApplet.P3D);
		this.parentPApplet = (Draw2PGraphics3D)parent;
		
		cameraImage = new PImage(requestWidth, requestHeight);
		
        frame = new JFrame("P4PCapture");
        frame.setLayout(new BorderLayout());
        frame.setSize(requestWidth, requestHeight+frameBarHeight);            	
       	panel = new JPanel();        	        
    	frame.add(panel, BorderLayout.CENTER);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.setResizable(false);        	        
    	frame.setVisible(false); 
    	
    	loc    = new PVector(0,0,0);
    	lookAt = new PVector(0,0,0);
    	dir    = new PVector(0,0,0); 
    	facingPlane = new FacingPlane();
       	frustum     = new PFrustumPyramid(loc, lookAt, cameraGraphics);	
	}
		  
	protected void initGStreamer(PApplet parent, int rw, int rh, String src, String idName, Object idValue, String fps) {
		// intentionally override Capture.init() so that this class doesn't open GStreamer.  
		//do nothing because this class is Virtual Capture		  
		;
	}

    @Override
    public boolean available() {
    	return cameraImage != null;
    }
    
	@Override
    public void read(){	
		// make a offscreen image
		cameraGraphics.beginDraw();		
		cameraGraphics.camera(
				loc.x, loc.y, loc.z,			//eyeX, eyeY, eyeZ, 
				lookAt.x, lookAt.y, lookAt.z, 	//centerX, centerY, centerZ,
				0, 1, 0);						//upX, upY, upZ)		
		parentPApplet.draw2PGraphics3D(cameraGraphics); //draw 3D world as same as parent PApplet		
		cameraGraphics.endDraw();						
		cameraImage = cameraGraphics.get(0, 0, cameraGraphics.width, cameraGraphics.height);
		
		//copy cameraImage to pixels			
        this.copy(cameraImage,0,0,width,height,0,0,width,height);      
		newFrame = true;		
	}
	
	public	void	setLocation(int x, int y, int z){
		this.loc.x = x;
		this.loc.y = y;
		this.loc.z = z;
		frustum.setLocation(x, y, z);
		facingPlane.update();
	}
	public	PVector	getLocation(){
		return loc;
	}
	
	public	void	setLookAt(int x, int y, int z){
		this.lookAt.x = x;
		this.lookAt.y = y;
		this.lookAt.z = z;
		frustum.setLookAt(x,y,z);
		facingPlane.update();
	}
	public	PVector	getLookAt(){
		return	lookAt;
	}
	public	PVector getDirection(){
		return dir; 
	}
	public	PGraphics3D getPGraphics3D(){
		return cameraGraphics;
	}	
	public	FacingPlane	getFacingPlane(){
		return	facingPlane;
	}
	
	public	void	draw(PGraphics3D g){
		g.stroke(128);
		g.strokeWeight(1); 
		g.pushMatrix();
		g.translate(loc.x,loc.y,loc.z);		
		g.sphere(5);		
		g.popMatrix();
		
		if(frustum != null)		frustum.draw(g);	
		//if(facingPlane != null)	facingPlane.draw(g);		
	}
	
	public	void showImageFrame(){                      
		if(panel !=null && frame!=null){
			if(frame.isVisible() ==false)
				frame.setVisible(true);
			panel.getGraphics().drawImage((Image)this.getNative(),0,0,null);			
		}
	}
	
	public class	FacingPlane	{
		PVector	xaxis, yaxis;
		PVector	dirPoint, planeXPoint, planeYPoint;
		
		FacingPlane(){				
		}		

		private	void	update(){		
			PVector.sub(lookAt, loc, dir);
			try{
				dir.normalize();						  
				xaxis = new PVector();			  
				yaxis = new PVector();
				PVector tmp1 = new PVector(loc.x-lookAt.x, 0, loc.z-lookAt.z);				
				PVector.cross(dir, tmp1, xaxis);			  
				xaxis.normalize();
				PVector.cross(dir, xaxis, yaxis);			  
				yaxis.normalize();	
				yaxis.mult(-1f);//PApplet yAxis heads bottom				
//				System.out.println("dir    :"+dir);	
//				System.out.println("tmp1   :"+tmp1);	
//				System.out.println("xaxis  :"+xaxis);
//				System.out.println("yaxis  :"+yaxis);				
			}catch(Exception e){			
			}
		}
		
		public	PVector getXaxis(){
			return xaxis;
		}
		public	PVector	getYaxis(){
			return yaxis;
		}
		
		public void draw(PGraphics3D g) {
			PVector xaxisL = new PVector(xaxis.x, xaxis.y, xaxis.z);
			xaxisL.mult(g.cameraAspect * 10f);
			PVector yaxisL = new PVector(yaxis.x, yaxis.y, yaxis.z);
			yaxisL.mult(10f);
			
			float near = 50f;			  				
			dirPoint = new PVector(loc.x + dir.x*near, loc.y + dir.y*near, loc.z + dir.z*near); 			  
			planeXPoint = new PVector();				
			planeYPoint = new PVector();			  
			PVector.add(dirPoint, xaxisL, planeXPoint);
			PVector.add(dirPoint, yaxisL, planeYPoint);	
			
			g.line(dirPoint.x, dirPoint.y, dirPoint.z, planeXPoint.x, planeXPoint.y, planeXPoint.z);
			g.line(dirPoint.x, dirPoint.y, dirPoint.z, planeYPoint.x, planeYPoint.y, planeYPoint.z);			
		}
	}//FacingPlane
}
