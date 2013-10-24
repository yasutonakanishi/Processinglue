package net.unitedfield.pglue;

import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class P4PBezierDisplay  extends P4PDisplay {
	int	bezierRes = 32;
	PVector[] ctrlPts = new PVector[4];
	PVector	parametricPoints[];
		
	public P4PBezierDisplay(PApplet parentApplet, PVector ctrl0,PVector ctrl1, PVector ctrl2, PVector ctrl3, float dHeight,
			PApplet dstApplet, int appletWidth, int appletHeight, boolean frameVisible) {
		super(parentApplet, ctrl0.x-ctrl3.x, dHeight, dstApplet, appletWidth, appletHeight,frameVisible);	
		
		ctrlPts[0]=ctrl0; ctrlPts[1]=ctrl1; ctrlPts[2]=ctrl2; ctrlPts[3]=ctrl3; 		
		setParametricPoints();
	}
		
	public	void	setParametricPoints(){
		parametricPoints = new PVector[bezierRes+1];		
		for(int i=0;i<= bezierRes;i++){		    	  		
			  float t = (float)i/bezierRes;			
			  float bX = (1-t) * (1-t) * (1-t) * ctrlPts[0].x + 3 * (1-t) * (1-t) * t * ctrlPts[1].x + 3 * (1-t) * t * t * ctrlPts[2].x + t * t * t * ctrlPts[3].x;		    
			  float bY = (1-t) * (1-t) * (1-t) * ctrlPts[0].y + 3 * (1-t) * (1-t) * t * ctrlPts[1].y + 3 * (1-t) * t * t * ctrlPts[2].y + t * t * t * ctrlPts[3].y;
			  float bZ = (1-t) * (1-t) * (1-t) * ctrlPts[0].z + 3 * (1-t) * (1-t) * t * ctrlPts[1].z + 3 * (1-t) * t * t * ctrlPts[2].z + t * t * t * ctrlPts[3].z;
			  parametricPoints[i] = new PVector(bX,bY,bZ);
		}
	}
	
	public	void	drawShape(PGraphics3D g){		
	  g.beginShape(g.QUAD_STRIP);	  	
	  g.texture(appletImage);	  
	  if(parametricPoints[0].y==0 && parametricPoints[bezierRes].y==0){	        // height direction is y
		  for(int i=0;i<= bezierRes;i++){		    	  		
			  float u = appletImage.width / bezierRes *i;		  
			  g.vertex(parametricPoints[i].x,  -dHeight,       parametricPoints[i].z, u, 0);
			  g.vertex(parametricPoints[i].x,  0, parametricPoints[i].z, u, appletImage.height);		  
		  }
	  }else if(parametricPoints[0].z==0 && parametricPoints[bezierRes].z==0){	// height direction is z
		  for(int i=0;i<= bezierRes;i++){		    	  		
			  float u = appletImage.width / bezierRes *i;		  
			  g.vertex(parametricPoints[i].x, parametricPoints[i].y, 0, u, 0);
			  g.vertex(parametricPoints[i].x, parametricPoints[i].y, -dHeight, u, appletImage.height);		  
		  }
	  }else if(parametricPoints[0].z==0 && parametricPoints[bezierRes].z==0){	// height direction is x
		  for(int i=0;i<= bezierRes;i++){		    	  		
			  float u = appletImage.width / bezierRes *i;		  
			  g.vertex(0,       parametricPoints[i].y, parametricPoints[i].z, u, 0);
			  g.vertex(-dHeight, parametricPoints[i].y, parametricPoints[i].z, u, appletImage.height);		  
		  }
	  }
	  
	  g.endShape();  	  	  
	}	
	
}
