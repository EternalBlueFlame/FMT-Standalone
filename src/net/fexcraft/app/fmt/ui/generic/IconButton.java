package net.fexcraft.app.fmt.ui.generic;

import net.fexcraft.app.fmt.ui.Element;
import net.fexcraft.app.fmt.utils.RGB;

public abstract class IconButton extends Element {
	
	private RGB hovercolor = new RGB(234, 255, 234);
	private String texture;

	public IconButton(Element parent, String id, String texture, int x, int y){
		super(parent, id); this.width = 20; this.height = 20;
		this.x = x; this.y = y; this.texture = texture;
	}

	@Override
	public void renderSelf(int rw, int rh){
		if(hovered) hovercolor.glColorApply();
		this.renderIcon(x, y, texture);
		if(hovered) RGB.glColorReset();
	}
	
	public static class Empty extends IconButton{

		public Empty(Element parent, String id, String texture, int x, int y){
			super(parent, id, texture, x, y);
		}

		@Override
		protected boolean processButtonClick(int x, int y, boolean left){
			return false;
		}
		
	}

}
