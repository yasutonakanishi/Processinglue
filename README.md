# Processinglue

## Overview


In **Processinglue**, Processing on virtual display runs in P3D Processing.

**Processinglue** is a package for simulating how an interactive system developed using Processing runs in a P3D virtual space, and is a successor of [Citycompiler]. It is possible to sketch or prototype interactive system with various size or location of displays. It aims to take PApplet out from small display world.

Virtual Input/Output devices for processing are available as follows.

* virtual display (flat, cylinder, bezier surface)
* virtual camera

Virtual display can show any PApplet, however, in the current implmentation, only PApplet with the default renderer can be attached (P3D or OPENGL PApplet can't be attached). The image of  PApplet with mouse interaction is reflected to virtual display with moving the mouse pointer on another window for the PApplet.

Virtual cameara is implemented as a subclass of Capture class in Processing, thus it can be used same as Capture class and it is easy to swap virtual camera and real camera.

Virtual projector or virtual distance sensor is working on CityCompiler, but is not implemented in Processinglue yet.

Sample movies are uploaded at [Processinglue sample movies on vimeo] and [CityCompiler sample movies on vimeo].

Copyright &copy; 2012 Yasuto Nakanishi (<naka@acm.org>) Licensed under the BSD License.

[Citycompiler]:https://github.com/yasutonakanishi/CC4p52
[CityCompiler sample movies on vimeo]:https://vimeo.com/groups/166800
[Processinglue sample movies on vimeo]:https://vimeo.com/groups/205577