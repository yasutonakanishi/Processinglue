# Processinglue

## Overview


In **Processinglue**, Processing on virtual display runs in P3D Processing.

**Processinglue** is a package for simulating how an interactive system developed with Processing runs in a virtual space, and is a successor of [Citycompiler]. 

It is possible to sketch or prototype interactive system with various sizes or locations of displays. This enables to take PApplet out from small display world and help to exhibit [Processing installation].

## Virtual I/0 devices##

Virtual Input/Output devices for processing are available as follows.

* virtual display (flat, cylinder, bezier surface)
* virtual camera

Virtual display can show any PApplet, however, in the current implmentation, only PApplet with the default renderer can be attached (P3D or OPENGL PApplet can't be attached). The image of  PApplet with mouse interaction is reflected to virtual display with moving the mouse pointer on another window for the PApplet.

Virtual cameara is implemented as a subclass of Capture class (processing.video.Capture). In a simulation, a virtual camera captures an image in a virtual space, the image is sent to a PApplet, and a virtual display shows the processed image in the virtual space. Because it is implemented as a subclass of Capture class, it is easy to swap virtual camera and real camera. This enables to prototype a realistic image processing code and simulate  deployments of I/O devices.

Virtual projector or virtual distance sensor is working on CityCompiler, but is not implemented in Processinglue yet.

## Samples ##
Sample movies are uploaded at [Processinglue sample movies on vimeo] and [CityCompiler sample movies on vimeo].

A short sample code is as follows.	
	
	public class ShortSample extends PApplet {	
		PApplet	applet;
		P4PDisplay display;
	
		public	void	setup(){
			size(640,480,P3D);
		
			applet = new SomePApplet();		

			// set width/height of display and specify the width/height of applet
			// the final value decides if a window showing the applet is visible or not
			display = new P4PDisplay(this, 160, 90, applet, 640, 360, true);
			display.translate(0,-100,0);		
		}
	
		public void	draw(){
		    background(200);
	    	lights();
		    translate(width/2, height/2, 0);
		    display.draw(); 	
		}
	}

For sample simulations, [class showing BVH data by Rhizomatiks] and [saito obj loader] are in the uploaded project file.


Copyright &copy; 2012 Yasuto Nakanishi (<naka@acm.org>) Licensed under the BSD License.

[Citycompiler]:https://github.com/yasutonakanishi/CC4p52
[Processing installation]:http://processing.org/exhibition/
[CityCompiler sample movies on vimeo]:https://vimeo.com/groups/166800
[Processinglue sample movies on vimeo]:https://vimeo.com/groups/205577
[class showing BVH data by Rhizomatiks]:https://github.com/perfume-dev/example-processing
[saito obj loader]:http://code.google.com/p/saitoobjloader/