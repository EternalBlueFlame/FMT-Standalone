package net.fexcraft.app.fmt.porters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.fexcraft.app.fmt.utils.JsonUtil;
import net.fexcraft.app.fmt.utils.Vec3f;
import net.fexcraft.app.fmt.wrappers.BoxWrapper;
import net.fexcraft.app.fmt.wrappers.CylinderWrapper;
import net.fexcraft.app.fmt.wrappers.GroupCompound;
import net.fexcraft.app.fmt.wrappers.PolygonWrapper;
import net.fexcraft.app.fmt.wrappers.ShapeboxWrapper;
import net.fexcraft.lib.tmt.ModelRendererTurbo;

/**
* Tool to parse `ModelRendererTurbo` objects from JSON.
* @Author Ferdinand Calo' (FEX___96)
*/
public class JsonToTMT {
	
	//def
	public static final float def = 0f;
	public static final int idef = 0;
	//common
	public static final String[] format  = new String[]{"format", "form", "f"};//1.1f
	public static final String[] width  = new String[]{"width",  "wid", "w"};
	public static final String[] height = new String[]{"height", "hgt", "h"};
	public static final String[] depth  = new String[]{"depth",  "dep", "d"};
	public static final String[] offx = new String[]{"offset_x", "off_x", "offx", "ox"};
	public static final String[] offy = new String[]{"offset_y", "off_y", "offy", "oy"};
	public static final String[] offz = new String[]{"offset_z", "off_z", "offz", "oz"};
	public static final String[] expansion = new String[]{"expansion", "exp", "e"};
	public static final String[] scale = new String[]{"scale", "s"};
	public static final String[] texturex = new String[]{"texture_x", "texturex", "tex_x", "tx"};
	public static final String[] texturey = new String[]{"texture_y", "texturey", "tex_y", "ty"};
	//
	public static final String[] posx = new String[]{"rotation_point_x", "pos_x", "posx", "px", "x"};
	public static final String[] posy = new String[]{"rotation_point_y", "pos_y", "posy", "py", "y"};
	public static final String[] posz = new String[]{"rotation_point_z", "pos_z", "posz", "pz", "z"};
	public static final String[] rotx = new String[]{"rotation_angle_x", "rotangle_x", "rotanglex", "rot_x", "rx"};
	public static final String[] roty = new String[]{"rotation_angle_y", "rotangle_y", "rotangley", "rot_y", "ry"};
	public static final String[] rotz = new String[]{"rotation_angle_z", "rotangle_z", "rotanglez", "rot_z", "rz"};
	//settings
	public static final String[] oldrot = new String[]{"old_ration", "old_rotation_order", "oro"};
	public static final String[] mirror = new String[]{"mirror", "mir", "m"};
	public static final String[] flip = new String[]{"flip", "fl", "usd"};
	//cyl
	public static final String[] radius = new String[]{"radius", "rad", "r"};
	public static final String[] length = new String[]{"length", "len", "l"};
	public static final String[] segments = new String[]{"segments", "seg", "sg"};
	public static final String[] basescale = new String[]{"base_scale", "basescale", "bs"};
	public static final String[] topscale = new String[]{"top_scale", "topscale", "ts"};
	public static final String[] direction = new String[]{"direction", "dir", "facing"};
	
	public final static ModelRendererTurbo parse(JsonObject obj, int tx, int ty){
		ModelRendererTurbo model = new ModelRendererTurbo(null, get(texturex, obj, idef), get(texturey, obj, idef), tx, ty);
		//
		float x = get(offx, obj, def);
		float y = get(offy, obj, def);
		float z = get(offz, obj, def);
		int w = get(width, obj, idef);
		int h = get(height, obj, idef);
		int d = get(depth, obj, idef);
		//
		switch(obj.get("type").getAsString()){
			case "box": case "cube": case "b": {
				model.addBox(x, y, z, w, h, d, get(expansion, obj, def));
				break;
			}
			case "shapebox": case "sbox": case "sb": {
				model.addShapeBox(x, y, z, w, h, d, get(scale, obj, def),
						get("x0", obj, def), get("y0", obj, def), get("z0", obj, def),
						get("x1", obj, def), get("y1", obj, def), get("z1", obj, def),
						get("x2", obj, def), get("y2", obj, def), get("z2", obj, def),
						get("x3", obj, def), get("y3", obj, def), get("z3", obj, def),
						get("x4", obj, def), get("y4", obj, def), get("z4", obj, def),
						get("x5", obj, def), get("y5", obj, def), get("z5", obj, def),
						get("x6", obj, def), get("y6", obj, def), get("z6", obj, def),
						get("x7", obj, def), get("y7", obj, def), get("z7", obj, def)
					);
				break;
			}
			case "cylinder": case "cyl": case "c": {
				model.addCylinder(x, y, z, get(radius, obj, 1f), get(length, obj, 1f), get(segments, obj, 16), get(basescale, obj, 1f), get(topscale, obj, 1f), get(direction, obj, 4));
				break;
			}
			case "cone": case "cn": {
				model.addCone(x, y, z, get(radius, obj, 1f), get(length, obj, 1f), get(segments, obj, 12), get(basescale, obj, 1f), get(direction, obj, 4));
				break;
			}
		}
		model.mirror = JsonUtil.getIfExists(obj, mirror, false);
		model.flip = JsonUtil.getIfExists(obj, flip, false);
		//
		model.rotateAngleX = get(rotx, obj, def);
		model.rotateAngleY = get(roty, obj, def);
		model.rotateAngleZ = get(rotz, obj, def);
		//
		model.boxName = obj.has("name") ? obj.get("name").getAsString() : null;
		model.setRotationPoint(get(posx, obj, def), get(posy, obj, def), get(posz, obj, def));
		return model;
	}

	public final static ModelRendererTurbo[] parse(JsonArray array, int tx, int ty){
		if(array != null){
			ModelRendererTurbo[] model = new ModelRendererTurbo[array.size()];
			for(int i = 0; i < array.size(); i++){
				model[i] = parse(array.get(i).getAsJsonObject(), tx, ty);
			}
			return model;
		}
		return new ModelRendererTurbo[0];
	}

	public final static ModelRendererTurbo[] parse(String string, JsonObject object, int tx, int ty){
		if(object.has(string) && object.get(string).isJsonArray()){
			return parse(object.get(string).getAsJsonArray(), tx, ty);
		}
		return new ModelRendererTurbo[0];
	}
	
	public static final float get(String s, JsonObject obj, float def){
		if(obj.has(s)){
			return obj.get(s).getAsFloat();
		}
		return def;
	}
	
	public static final float get(String[] s, JsonObject obj, float def){
		for(String str : s){
			if(obj.has(str)){
				return obj.get(str).getAsFloat();
			}
		}
		return 0;
	}
	
	public static final int get(String[] s, JsonObject obj, int def){
		for(String str : s){
			if(obj.has(str)){
				return obj.get(str).getAsInt();
			}
		}
		return 0;
	}
	
	//---//WRAPPERS//---//
	
	public final static PolygonWrapper parseWrapper(GroupCompound compound, JsonObject obj){
		PolygonWrapper polygon = null;
		switch(obj.get("type").getAsString()){
			case "box": case "cube": case "b":{
				BoxWrapper cuboid = new BoxWrapper(compound);
				cuboid.size.xCoord = get(width, obj, idef);
				cuboid.size.yCoord = get(height, obj, idef);
				cuboid.size.zCoord= get(depth, obj, idef);
				polygon = cuboid; break;
			}
			case "shapebox": case "sbox": case "sb": {
				ShapeboxWrapper shapebox = new ShapeboxWrapper(compound);
				shapebox.size.xCoord = get(width, obj, idef);
				shapebox.size.yCoord = get(height, obj, idef);
				shapebox.size.zCoord= get(depth, obj, idef);
				//
				shapebox.cor0 = new Vec3f(get("x0", obj, def), get("y0", obj, def), get("z0", obj, def));
				shapebox.cor1 = new Vec3f(get("x1", obj, def), get("y1", obj, def), get("z1", obj, def));
				shapebox.cor2 = new Vec3f(get("x2", obj, def), get("y2", obj, def), get("z2", obj, def));
				shapebox.cor3 = new Vec3f(get("x3", obj, def), get("y3", obj, def), get("z3", obj, def));
				shapebox.cor4 = new Vec3f(get("x4", obj, def), get("y4", obj, def), get("z4", obj, def));
				shapebox.cor5 = new Vec3f(get("x5", obj, def), get("y5", obj, def), get("z5", obj, def));
				shapebox.cor6 = new Vec3f(get("x6", obj, def), get("y6", obj, def), get("z6", obj, def));
				shapebox.cor7 = new Vec3f(get("x7", obj, def), get("y7", obj, def), get("z7", obj, def));
				polygon = shapebox; break;
			}
			case "cylinder": case "cyl": case "c": case "cone": case "cn": {
				CylinderWrapper cylinder = new CylinderWrapper(compound);
				cylinder.radius = get(radius, obj, 1f);
				cylinder.length = get(length, obj, 1f);
				cylinder.segments = get(segments, obj, 16);
				cylinder.direction = get(direction, obj, 4);
				cylinder.base = get(basescale, obj, 1f);
				cylinder.top = get(topscale, obj, 1f);
				polygon = cylinder; break;
			}
		}
		polygon.textureX = get(texturex, obj, idef);
		polygon.textureY = get(texturey, obj, idef);
		polygon.mirror = JsonUtil.getIfExists(obj, mirror, false);
		polygon.flip = JsonUtil.getIfExists(obj, flip, false);
		polygon.rot.xCoord = get(rotx, obj, def);
		polygon.rot.yCoord = get(roty, obj, def);
		polygon.rot.zCoord = get(rotz, obj, def);
		polygon.pos.xCoord = get(posx, obj, def);
		polygon.pos.yCoord = get(posy, obj, def);
		polygon.pos.zCoord = get(posz, obj, def);
		polygon.name = obj.has("name") ? obj.get("name").getAsString() : null;
		polygon.visible = obj.has("visible") ? obj.get("visible").getAsBoolean() : true;
		return polygon;
	}
	
}