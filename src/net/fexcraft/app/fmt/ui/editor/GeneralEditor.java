package net.fexcraft.app.fmt.ui.editor;

import org.newdawn.slick.Color;

import net.fexcraft.app.fmt.FMTB;
import net.fexcraft.app.fmt.ui.generic.Button;
import net.fexcraft.app.fmt.ui.generic.TextField;
import net.fexcraft.app.fmt.utils.RGB;
import net.fexcraft.app.fmt.utils.TextureManager;

public class GeneralEditor extends Editor {

	public GeneralEditor(){
		super("general_editor");
		final RGB rgb = new RGB(127, 127, 255);
		//
		String[] vals = new String[]{ "size", "pos", "off", "rot", "tex" }, xyz = new String[]{ "x", "y", "z" };
		for(int r = 0; r < vals.length; r++){
			for(int i = 0; i < 3; i++){
				int k = 70, j = k + 12 + 12 + 4; final int rr = r, ii = i;
				this.elements.put(vals[r] + xyz[i] + "-", new Button(this, vals[r] + xyz[i] + "-", 12, 26, 4 + (j * i), 30 + (r * 50), rgb){
					@Override
					protected boolean processButtonClick(int x, int y, boolean left){
						return FMTB.MODEL.updateValue((TextField)this.parent.getElement(vals[rr] + xyz[ii]), this.id);
					}
				}.setText(" < ", true).setTexture("ui/background").setLevel(-1));
				TextField field = new TextField(this, vals[r] + xyz[i], k, 16 + (j * i), 30 + (r * 50));
				if(vals[r].equals("tex")){
					switch(i){
						case 0: field.setAsNumberfield(-FMTB.MODEL.textureX, FMTB.MODEL.textureX, true); break;
						case 1: field.setAsNumberfield(-FMTB.MODEL.textureY, FMTB.MODEL.textureY, true); break;
						case 2: field.setText(" - - - ", true); field.enabled = false; break;
					}
				}
				else if(vals[r].equals("size")){
					field.setAsNumberfield(0, Integer.MAX_VALUE, true);
				}
				else if(vals[r].equals("rot")){
					field.setAsNumberfield(-360, 360, true);
				}
				else{
					field.setAsNumberfield(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
				}
				this.elements.put(vals[r] + xyz[i], field.setLevel(-1));
				this.elements.put(vals[r] + xyz[i] + "+", new Button(this, vals[r] + xyz[i] + "+", 12, 26, k + 16 + (j * i), 30 + (r * 50), rgb){
					@Override
					protected boolean processButtonClick(int x, int y, boolean left){
						return FMTB.MODEL.updateValue((TextField)this.parent.getElement(vals[rr] + xyz[ii]), this.id);
					}
				}.setText(" > ", true).setTexture("ui/background").setLevel(-1));
			}
		}
		//
		this.elements.put("group-", new Button(this, "group-", 12, 26, 4, 280, rgb){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				FMTB.MODEL.changeGroupIndex(-1); return true;
			}
		}.setText(" < ", true).setTexture("ui/background").setLevel(-1));
		//
		this.elements.put("group", new TextField(this, "group", 270, 16, 280).setText("null", true).setLevel(-1));
		this.elements.put("group+", new Button(this, "group+", 12, 26, 282, 280, rgb){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				FMTB.MODEL.changeGroupIndex(+1); return true;
			}
		}.setText(" > ", true).setTexture("ui/background").setLevel(-1));
		//
		this.addMultiplicator(330);
	}
	
	@Override
	public void renderSelf(int rw, int rh){
		super.renderSelf(rw, rh); TextureManager.unbind();
		font.drawString(4,  40, "Measurements", Color.black);
		font.drawString(4,  90, "Position (x/y/z)", Color.black);
		font.drawString(4, 140, "Offset (x/y/z)", Color.black);
		font.drawString(4, 190, "Rotation (degrees)", Color.black);
		font.drawString(4, 240, "Texture (x/y)", Color.black);
		font.drawString(4, 290, "Group", Color.black);
		font.drawString(4, 340, "Multiplicator/Rate", Color.black);
		RGB.glColorReset();
	}

}
