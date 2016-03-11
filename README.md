#Processinglue
##Overview

### Prototyping of Processing exhibition in a virtual world
**Processinglue** is a package for simulating how an interactive system developed with Processing runs in a virtual space, and is a successor of [Citycompiler](https://github.com/yasutonakanishi/CC4p52).  

Processinglue offers a virtual display and virtual camera for PApplet that runs within another PApplet, thus this library enables a spatial simulation of Processing installation using a large display or a large screen.

It is possible to sketch or prototype interactive system with various sizes or locations of displays. This enables to take PApplet out from small display world and help to exhibit Processing installation. 

### Virtual I/O

Our virtual display can show any PApplet, however, in the current implmentation, only PApplet with the default renderer can be attached (P3D or OPENGL PApplet can't be attached). The image of  PApplet with mouse interaction is reflected to virtual display with moving the mouse pointer on another window for the PApplet. 

Our virtual camera is implemented as a subclass of Capture class (processing.video.Capture). In a simulation, a virtual camera captures an image in a virtual space, the image can be sent to a PApplet very easily, and a virtual display might show the processed image in the virtual space. It is implemented as a subclass of Capture class, and it is easy to swap virtual camera and real camera. This enables to prototype a realistic image processing code and simulate deployments of I/O devices.

Virtual projector or virtual distance sensor is working on CityCompiler, but is not implemented in Processinglue yet. A virtual projector can be also developed based on shadow mapping. You might develop different original shapes of virtual display with developing a sub class of PglueDisplay.

## Examples ##

### PglueDisplayTest

An installation simulation of a large display showing a PApplet.
![PglueDisplayTest](./images/PglueDisplayTest.png)

### PglueCaptureTest
An installation simulation of a large display showing a PApplet and a virtual flying camera infront of the virtual display.

![PglueCaptureTest](./images/PglueCaptureTest.png)


## How to install Processinglue

### Install with the Contribution Manager

Add contributed libraries by selecting the menu item _Sketch_ → _Import Library..._ → _Add Library..._ This will open the Contribution Manager, where you can browse for Processinglue, or any other library you want to install.

Not all available libraries have been converted to show up in this menu. If a library isn't there, it will need to be installed manually by following the instructions below.

### Manual Install

Contributed libraries may be downloaded separately and manually placed within the `libraries` folder of your Processing sketchbook. To find (and change) the Processing sketchbook location on your computer, open the Preferences window from the Processing application (PDE) and look for the "Sketchbook location" item at the top.

By default the following locations are used for your sketchbook folder: 
  * For Mac users, the sketchbook folder is located inside `~/Documents/Processing` 
  * For Windows users, the sketchbook folder is located inside `My Documents/Processing`

Download Processinglue from https://github.com/yasutonakanishi/Processinglue

Unzip and copy the contributed library's folder into the `libraries` folder in the Processing sketchbook. You will need to create this `libraries` folder if it does not exist.

The folder structure for library Processinglue should be as follows:

```
Processing
  libraries
    Processinglue
      examples
      library
        Processinglue.jar
      reference
      src
```
             
Some folders like `examples` or `src` might be missing. After library Processinglue has been successfully installed, restart the Processing application.

### Troubleshooting

If you're having trouble, have a look at the [Processing Wiki](https://github.com/processing/processing/wiki/How-to-Install-a-Contributed-Library) for more information, or contact the author [Yasuto Nakanishi](https://github.com/yasutonakanishi/).

