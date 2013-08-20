import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class PFrustumPyramid {
	PVector	loc, lookAt, dir;
	
	float near   = 2.5f;		
	float width  = 4;
	float height = 3;
	float scale	= 10;
	
	public	PFrustumPyramid(PVector _loc, PVector _lookAt){
		loc = new PVector(_loc.x, _loc.y, _loc.z);			
		lookAt = new PVector(_lookAt.x, _lookAt.y, _lookAt.z);
		dir = new PVector();
		PVector.sub(_lookAt, _loc, dir);
	}
	
	public	void	setLocation(float x, float y, float z){
		this.loc.x = x;
		this.loc.y = y;
		this.loc.z = z;
	}
	
	public	void	setLookAt(float x, float y, float z){
		this.lookAt.x = x;
		this.lookAt.y = y;
		this.lookAt.z = z;
		PVector.sub(lookAt, loc, dir);
	}
	
	public	void	setAspcetRatio(float width , float height){
		this.width = width;
		this.height = height;
	}
	
	public	void	setFov(float height, float near){
		this.height = height;
		this.near = near;
	}
	
	public	void	setScale(float scale){
		this.scale = scale;
	}
	
	public	void draw(PGraphics3D g){			  		  
		g.pushMatrix();		
		g.translate(loc.x,loc.y,loc.z);			
		
		double yAxisRot = Math.atan2((double)dir.x,(double)dir.z);			
		double xAxisRot = Math.atan2(-(double)dir.y,(double)(Math.sqrt(dir.x*dir.x+dir.z*dir.z)));						
		g.rotateY((float)yAxisRot);			
		g.rotateX((float)xAxisRot);			
						
		g.scale(scale);		  
			
		g.beginShape();// draw pyramid	
			g.vertex(-width/2, -height/2, near);			
			g.vertex( width/2, -height/2, near);			
			g.vertex( 0,  0,  0);			
		
			g.vertex( width/2, -height/2, near);			
			g.vertex( width/2,  height/2, near);			
			g.vertex( 0,  0,  0);
			
			g.vertex( width/2, height/2, near);			
			g.vertex(-width/2, height/2, near);			
			g.vertex( 0, 0,  0);
			
			g.vertex(-width/2,  height/2, near);			
			g.vertex(-width/2, -height/2, near);			
			g.vertex( 0,  0,  0);																					
		g.endShape();						

		g.popMatrix();		  
	}				
}	
