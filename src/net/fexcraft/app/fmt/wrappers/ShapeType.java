package net.fexcraft.app.fmt.wrappers;

public enum ShapeType {
	
	BOX, SHAPEBOX, FLEXBOX, TRAPEZOID, FLEXTRAPEZOID, CYLINDER, SPHERE, OBJ;

	public boolean isCuboid(){
		return this == BOX || this == SHAPEBOX || this== FLEXBOX || this==TRAPEZOID || this==FLEXTRAPEZOID;
	}

	public boolean isShapebox(){
		return this == SHAPEBOX;
	}

	public boolean isCylinder(){
		return this == CYLINDER;
	}

}
