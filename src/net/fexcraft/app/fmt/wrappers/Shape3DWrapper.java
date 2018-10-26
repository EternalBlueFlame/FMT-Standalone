package net.fexcraft.app.fmt.wrappers;

import com.google.gson.JsonObject;
import net.fexcraft.app.fmt.utils.Vec3f;
import net.fexcraft.lib.tmt.Coord2D;
import net.fexcraft.lib.tmt.ModelRendererTurbo;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Shape3DWrapper extends BoxWrapper {

	public ArrayList<Coord2D> coords = new ArrayList<>();
	public float depth;
	public int shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, mr_side;

	public Shape3DWrapper(GroupCompound compound){
		super(compound);
	}
	
	@Override
	public void recompile(){
		if(turbo != null && turbo.displaylist() != null){ GL11.glDeleteLists(turbo.displaylist(), 1); turbo = null; }
		turbo = new ModelRendererTurbo(null, textureX, textureY, compound.textureX, compound.textureY);
		//float x, float y, float z, ArrayList<Coord2D> coordinates,
		// float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction
		turbo.addShape3D(off.xCoord, off.yCoord, off.zCoord, coords,
				depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, mr_side);
		turbo.setRotationPoint(pos.xCoord, pos.yCoord, pos.zCoord);
		turbo.rotateAngleX = rot.xCoord; turbo.rotateAngleY = rot.yCoord; turbo.rotateAngleZ = rot.zCoord;
		//
		if(lines != null && lines.displaylist() != null){ GL11.glDeleteLists(lines.displaylist(), 0); lines = null; }
		lines = new ModelRendererTurbo(null, textureX, textureY, compound.textureX, compound.textureY);
		turbo.addShape3D(off.xCoord, off.yCoord, off.zCoord, coords,
				depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, mr_side);
		turbo.setRotationPoint(pos.xCoord, pos.yCoord, pos.zCoord);
		lines.lines = true;
		lines.setRotationPoint(pos.xCoord, pos.yCoord, pos.zCoord);
		lines.rotateAngleX = rot.xCoord; lines.rotateAngleY = rot.yCoord; lines.rotateAngleZ = rot.zCoord;
	}

	@Override
	public ShapeType getType(){
		return ShapeType.SHAPEBOX;
	}
	
	@Override
	public float getFloat(String id, boolean x, boolean y, boolean z){
		switch(id){
			case "depth":{ return depth;}
			case "shapeTexture":{return x?shapeTextureWidth:shapeTextureHeight;}
			case "sideTexture": {return x? sideTextureWidth: sideTextureHeight;}
			case "side":{return mr_side;}
			default:{
				return x?coords.get(Integer.parseInt(id.substring(3))).xCoord:
						coords.get(Integer.parseInt(id.substring(3))).yCoord;
			}
		}
	}
	
	@Override
	public boolean setFloat(String id, boolean x, boolean y, boolean z, float value){
		if(super.setFloat(id, x, y, z, value)) return true;

		switch(id){
			case "depth":{ depth=value; return true;}
			case "shapeTexture":{
				if(x) {
					shapeTextureWidth=(int)value;
				} else {
					shapeTextureHeight=(int)value;
				}
				return true;
			}
			case "sideTexture": {
				if(x) {
					sideTextureWidth=(int)value;
				} else {
					sideTextureHeight=(int)value;
				}
				return true;
			}
			case "side":{mr_side=(int)value; return true;}
			default:{
				int index = Integer.parseInt(id.substring(3));
				while (coords.size()<index){
					coords.add(new Coord2D(0,0,0,0));
				}
				if(x) {
					coords.get(index).xCoord=value;
				} else {
					coords.get(index).yCoord=value;
				}
				return true;
			}
		}
	}

	@Override
	protected JsonObject populateJson(JsonObject obj, boolean export){
		obj = super.populateJson(obj, export);

		for(int i=0; i<coords.size();i++){
			if(coords.get(i).xCoord!=0) obj.addProperty("corx"+i, coords.get(i).xCoord);
			if(coords.get(i).yCoord!=0) obj.addProperty("cory"+i, coords.get(i).yCoord);
		}

		if(depth!=0) obj.addProperty("depth", depth);
		if(shapeTextureWidth!=0) obj.addProperty("shapetexwidth", shapeTextureWidth);
		if(shapeTextureHeight!=0) obj.addProperty("shapetexheight", shapeTextureHeight);
		if(sideTextureWidth!=0) obj.addProperty("sidetexwidth", sideTextureWidth);
		if(sideTextureHeight!=0) obj.addProperty("sidetexheight", sideTextureHeight);
		if(mr_side!=0) obj.addProperty("side", mr_side);

		return obj;
	}


	public Shape3DWrapper setCoords(){
		//cor0 = xyz0; cor1 = xyz1; cor2 = xyz2; cor3 = xyz3; cor4 = xyz4; cor5 = xyz5; cor6 = xyz6; cor7 = xyz7; return this;
		return this;
	}
	
}
