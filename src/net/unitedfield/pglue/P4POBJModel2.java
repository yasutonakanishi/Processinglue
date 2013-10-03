package net.unitedfield.pglue;

import objloader2.Face;
import objloader2.Material2;
import objloader2.OBJModel2;
import objloader2.Segment;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

public class P4POBJModel2 extends OBJModel2 {

	public P4POBJModel2(PApplet _parent, String _filename) {
		super(_parent, _filename);
	}

	public	void	draw(PGraphics3D g3d){
		drawModel(g3d);
	}
	
	protected	void	drawModel(PGraphics3D g3d){
		try {
			PVector v = null, vt = null, vn = null;

			Material2 tmpMaterial = null;

			Segment tmpModelSegment;
			Face tmpModelElement;

			// render all triangles
			for (int s = 0; s < getSegmentCount(); s++) {

				tmpModelSegment = segments.get(s);

				tmpMaterial = materials.get(tmpModelSegment.materialName);

				// if the material is not assigned for some
				// reason, it uses the default material setting
				if (tmpMaterial == null) {
					tmpMaterial = materials.get(defaultMaterialName);

					debug.println("Material '" + tmpModelSegment.materialName + "' not defined");
				}

				if (useMaterial) {
					g3d.ambient(255.0f * tmpMaterial.Ka[0], 255.0f * tmpMaterial.Ka[1], 255.0f * tmpMaterial.Ka[2]);
					g3d.specular(255.0f * tmpMaterial.Ks[0], 255.0f * tmpMaterial.Ks[1], 255.0f * tmpMaterial.Ks[2]);
					g3d.fill(255.0f * tmpMaterial.Kd[0], 255.0f * tmpMaterial.Kd[1], 255.0f * tmpMaterial.Kd[2], 255.0f * tmpMaterial.d);
				}

				for (int f = 0; f < tmpModelSegment.getFaceCount(); f++) {
					tmpModelElement = (tmpModelSegment.getFace(f));

					if (tmpModelElement.getVertIndexCount() > 0) {

						g3d.textureMode(PConstants.NORMAL);
						g3d.beginShape(drawMode); // specify render mode
						if (useTexture == false || tmpMaterial.map_Kd == null)
							useTexture = false;

						if (useTexture) {
							if (texture != null)
								g3d.texture(texture);
							else
								g3d.texture(tmpMaterial.map_Kd); 
						}

						for (int fp = 0; fp < tmpModelElement.getVertIndexCount(); fp++) {
							v = vertices.get(tmpModelElement.getVertexIndex(fp));

							if (v != null) {
								try {
									if (tmpModelElement.normalIndices.size() > 0) {
										vn = normalVertices.get(tmpModelElement.getNormalIndex(fp));
										g3d.normal(vn.x, vn.y, vn.z);
									}

									if (useTexture) {
										vt = textureVertices.get(tmpModelElement.getTextureIndex(fp));
										g3d.vertex(v.x, v.y, v.z, vt.x, vt.y);
									} else
										g3d.vertex(v.x, v.y, v.z);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else
								g3d.vertex(v.x, v.y, v.z);
						}

						g3d.endShape();

						g3d.textureMode(PConstants.IMAGE);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
