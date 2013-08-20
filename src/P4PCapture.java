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
	Draw2PGraphics3D	parentPApplet;
	private	PGraphics3D	cameraGraphics;
	private	PImage		cameraImage;	
	
	private	PVector	loc, lookAt;
	PFrustumPyramid	frustum;
	
	private	JFrame	frame;
	private	JPanel	panel;
	
	public P4PCapture(PApplet parent, int requestWidth, int requestHeight) {
		super(parent, requestWidth, requestHeight);
		
		cameraGraphics = (PGraphics3D)parent.createGraphics(width, height, PApplet.P3D);
		this.parentPApplet = (Draw2PGraphics3D)parent;
		
		cameraImage = new PImage(requestWidth, requestHeight);
		
        frame = new JFrame("P4PCapture");
        frame.setLayout(new BorderLayout());
        frame.setSize(cameraGraphics.width, cameraGraphics.height);                	
       	panel = new JPanel();        	        
    	frame.add(panel, BorderLayout.CENTER);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.setResizable(false);        	        
    	frame.setVisible(false); 
    	
    	this.loc = new PVector(0,0,0);
    	this.lookAt = new PVector(0,0,0);
    	frustum = new PFrustumPyramid(loc, lookAt);	
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
	}
	
	public	void	setLookAt(int x, int y, int z){
		this.lookAt.x = x;
		this.lookAt.y = y;
		this.lookAt.z = z;
		frustum.setLookAt(x,y,z);
	}
	
	public	void	draw(PGraphics3D g){
		g.stroke(128);
		g.pushMatrix();
		g.translate(loc.x,loc.y,loc.z);		
		g.sphere(5);		
		g.popMatrix();		
		if(frustum != null)
			frustum.draw(g);
	}
	
	public	void showImageFrame(){                      
		if(panel !=null && frame!=null){
			if(frame.isVisible() ==false)
				frame.setVisible(true);
			panel.getGraphics().drawImage((Image)this.getNative(),0,0,null);			
		}
	}
}
