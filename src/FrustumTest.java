import processing.core.PApplet;
import processing.core.PVector;


public class FrustumTest extends PApplet {
	float angle1, angle2;
	FrustumPyramid frustum;
	
	float x1=10, y1=30, z1=20;	 
	float x2=150, y2=100, z2=50;
	
	public	void setup(){
		size(640, 480, P3D);				  
		frustum = new FrustumPyramid(new PVector(x2,y2,z2), new PVector(x1,y1,z1));
	}
	
	public	void	draw() {
		  background(204);
		  translate(width/2, height/2);
		  angle1 +=0.01;
		  angle2 +=0.02; 
		  rotateX(angle1);
		  rotateY(angle2);		  
		  
		  line(x1, y1, z1, x2, y2, z2);		  
		  frustum.draw();		

/*
		  pushMatrix();
		  	translate(x2,y2,z2);
			double yAxisRot = Math.atan2((double)x2,(double)z2);
			double xAxisRot = Math.atan2((double)y2,(double)(Math.sqrt(x2*x2+z2*z2)));			
			rotateY(-(float)yAxisRot);
			rotateX((float)xAxisRot);			
			
			scale(10);		  
			beginShape();
				int	depth =8;
				int	width =4;
				int height=3;
				vertex(-width, -height, depth);
				vertex( width, -height, depth);
				vertex( 0,  0,  0);

				vertex( width, -height, depth);
				vertex( width,  height, depth);
				vertex( 0,  0,  0);

				vertex( width, height, depth);
				vertex(-width, height, depth);
				vertex( 0, 0,  0);

				vertex(-width,  height, depth);
				vertex(-width, -height, depth);
				vertex( 0,  0,  0);			
															
			endShape();
			line( width, height, depth, -width, -height, depth);
			line( width, -height, depth, -width, height, depth);		  
		  popMatrix();
*/		  
		  		  
	}		
	
	
	class	FrustumPyramid	{
		PVector	loc, lookAt, dir;
		
		int	near	= 5;		
		int	width 	= 4;
		int height	= 3;
		int	scale	= 10;
		
		FrustumPyramid(PVector _loc, PVector _lookAt){
			loc = new PVector(_loc.x, _loc.y, _loc.z);			
			lookAt = new PVector(_lookAt.x, _lookAt.y, _lookAt.z);
			dir = new PVector();
			PVector.sub(_lookAt, _loc, dir);
		}
		
		public	void	setAspcetRatio(int width , int height){
			this.width = width;
			this.height = height;
		}
		
		public	void	setFov(int height, int near){
			this.height = height;
			this.near = near;
		}
		
		public	void	setScale(int scale){
			this.scale = scale;
		}
		
		public	void draw(){			  
			  pushMatrix();
			  	translate(loc.x,loc.y,loc.z);
				double yAxisRot = Math.atan2((double)dir.x,(double)dir.z);
				double xAxisRot = Math.atan2(-(double)dir.y,(double)(Math.sqrt(dir.x*dir.x+dir.z*dir.z)));			
				rotateY((float)yAxisRot);
				rotateX((float)xAxisRot);			
				
				scale(scale);		  
				beginShape();
					vertex(-width/2, -height/2, near);
					vertex( width/2, -height/2, near);
					vertex( 0,  0,  0);

					vertex( width/2, -height/2, near);
					vertex( width/2,  height/2, near);
					vertex( 0,  0,  0);

					vertex( width/2, height/2, near);
					vertex(-width/2, height/2, near);
					vertex( 0, 0,  0);

					vertex(-width/2,  height/2, near);
					vertex(-width/2, -height/2, near);
					vertex( 0,  0,  0);			
																
				endShape();
				line( width/2, height/2, near, -width/2, -height/2, near);
				line( width/2, -height/2, near, -width/2, height/2, near);		  
			  popMatrix();
			  
		}				
	}	
}

