package net.fexcraft.app.fmt.ui.editor;

import org.newdawn.slick.Color;

import net.fexcraft.app.fmt.FMTB;
import net.fexcraft.app.fmt.ui.generic.Button;
import net.fexcraft.app.fmt.ui.generic.TextField;
import net.fexcraft.app.fmt.utils.RGB;
import net.fexcraft.app.fmt.utils.TextureManager;

public class ShapeboxEditor extends Editor {

	public ShapeboxEditor(){
		super("shapebox_editor");
		final RGB rgb = new RGB(127, 127, 255);
		//
		String[] vals = new String[]{ "cor0", "cor1", "cor2", "cor3", "cor4", "cor5", "cor6", "cor7" }, xyz = new String[]{ "x", "y", "z" };
		for(int r = 0; r < vals.length; r++){
			for(int i = 0; i < 3; i++){
				int k = 70, j = k + 12 + 12 + 4; final int rr = r, ii = i;
				this.elements.put(vals[r] + xyz[i] + "-", new Button(this, vals[r] + xyz[i] + "-", 12, 26, 4 + (j * i), 30 + (r * 50), rgb){
					@Override
					protected boolean processButtonClick(int x, int y, boolean left){
						return FMTB.MODEL.updateValue((TextField)this.parent.getElement(vals[rr] + xyz[ii]), this.id);
					}
				}.setText(" < ", true).setTexture("ui/background").setLevel(-1));
				TextField field = new TextField(this, vals[r] + xyz[i], k, 16 + (j * i), 30 + (r * 50)).setAsNumberfield(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
				this.elements.put(vals[r] + xyz[i], field.setLevel(-1));
				this.elements.put(vals[r] + xyz[i] + "+", new Button(this, vals[r] + xyz[i] + "+", 12, 26, k + 16 + (j * i), 30 + (r * 50), rgb){
					@Override
					protected boolean processButtonClick(int x, int y, boolean left){
						return FMTB.MODEL.updateValue((TextField)this.parent.getElement(vals[rr] + xyz[ii]), this.id);
					}
				}.setText(" > ", true).setTexture("ui/background").setLevel(-1));
			}
		}
		this.addMultiplicator(430);
	}
	
	@Override
	public void renderSelf(int rw, int rh){
		super.renderSelf(rw, rh); TextureManager.unbind();
		for(int i = 0; i < 8; i++){
			font.drawString(4, 40 + (i * 50), "Corner " + (i + 1) + " [" + i + "]", Color.black);
		}
		font.drawString(4, 440, "Multiplicator/Rate", Color.black);
		RGB.glColorReset();
	}

}
