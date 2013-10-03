package simulation.p5;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorBars extends PApplet {
	int BAR_NUM = 100;
	float[] x = new float[BAR_NUM];
	float[] xSpeed = new float[BAR_NUM];
	float[] bWidth = new float[BAR_NUM];
	int[] bColor = new int[BAR_NUM];
	PImage maskImage;

	public void setup() {
		size(200, 200);
		frameRate(30);
		smooth();
		colorMode(HSB, 360, 100, 100, 100);
		noStroke();
		for (int i=0; i<BAR_NUM; i++) {
			x[i] = random(width);
			xSpeed[i] = random(-1, 1);
			bWidth[i] = random(2, 200);
			bColor[i] = color(random(360), random(90, 100), random(50, 100), 20);
		}
		//maskImage = loadImage("mask.png");
	}

	public void draw() {
		background(0);
		for (int i=0; i<BAR_NUM; i++) {
			fill(bColor[i]);
			rect(x[i], 0, bWidth[i], height);
			x[i] += xSpeed[i];
			if (x[i] > width || x[i] < -bWidth[i]) {
				xSpeed[i] *= -1;
			}
		}
		//image(maskImage, 0, 0);
	}
}