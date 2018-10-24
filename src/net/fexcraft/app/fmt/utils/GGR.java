package net.fexcraft.app.fmt.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.fexcraft.app.fmt.FMTB;

/** CCR */
public class GGR {
	
    public float movespeed = 1.5f;//0.5f;
    public float maxlookrange = 85;
    public float sensivity = 1.0f;//= 0.05f;
    public Vec3f pos, rotation;
    
    public GGR(int x, int y, int z){
        pos = new Vec3f(x, y, z);
        rotation = new Vec3f(0, 0, 0);
    }
    
    public GGR(float x, float y, float z){
        pos = new Vec3f(x, y, z);
        rotation = new Vec3f(0, 0, 0);
    }

    public  void apply(){
        if(rotation.yCoord / 360 > 1){ rotation.yCoord -= 360; }
        else if(rotation.yCoord / 360 < -1){ rotation.yCoord += 360; }
        GL11.glLoadIdentity();
        GL11.glRotatef(rotation.xCoord, 1, 0, 0);
        GL11.glRotatef(rotation.yCoord, 0, 1, 0);
        GL11.glRotatef(rotation.zCoord, 0, 0, 1);
        GL11.glTranslatef(-pos.xCoord, -pos.yCoord, -pos.zCoord);
    }

    public  void acceptInput(float delta){
        acceptInputRotate(delta);
        acceptInputGrab();
        acceptInputMove(delta);
    }
    
    private boolean clickedL, clickedR, panning;
    private int wheel, updateTick, oldMouseX=-1,oldMouseY=-1;

    public  void acceptInputRotate(float delta){
        if(Mouse.isGrabbed()){
            float mouseDX = Mouse.getDX();
            float mouseDY = -Mouse.getDY();
            rotation.yCoord += mouseDX * sensivity * delta;
            rotation.xCoord += mouseDY * sensivity * delta;
            rotation.xCoord = Math.max(-maxlookrange, Math.min(maxlookrange, rotation.xCoord));
        }
        else{
        	if(!Mouse.isInsideWindow()) return;
        	if(Mouse.isButtonDown(0) && !clickedL) FMTB.get().UI.onButtonPress(0); clickedL = Mouse.isButtonDown(0);
        	if(Mouse.isButtonDown(1) && !clickedR) FMTB.get().UI.onButtonPress(1); clickedR = Mouse.isButtonDown(1);
        	if((wheel = Mouse.getDWheel()) != 0){
        		if(FMTB.get().UI.onScrollWheel(wheel)); else {
        			//TODO "zoom in/out


        		}
        	}
        }
    }

    public  void acceptInputGrab(){
        if(Mouse.isInsideWindow() && (Keyboard.isKeyDown(Keyboard.KEY_E) || (Mouse.isButtonDown(1) && !clickedR))){
            Mouse.setGrabbed(true);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)|| (!Mouse.isButtonDown(1) && clickedR)){
            Mouse.setGrabbed(false);
        }
    }

    public  void acceptInputMove(float delta){
        int diffX=0, diffY=0;
        if (Mouse.isButtonDown(2)) {
            if(oldMouseX==-1){
                oldMouseX=Mouse.getX();
                oldMouseY=Mouse.getY();
            }
            if(updateTick>2) {
                //offset it because if we do it every frame it doesn't always work, but every few frames is fine.
                diffX=Mouse.getX() - oldMouseX;
                diffY=Mouse.getY() - oldMouseY;
                pos.xCoord += diffX * 0.01;
                pos.yCoord += diffY * 0.01;
                updateTick=0;
            } else {
                updateTick++;
            }
            panning = true;
        } else if (panning) {
            oldMouseX=-1;
            updateTick=0;
            panning = false;
        }
        if(oldMouseX!=-1){

            Mouse.setCursorPosition(oldMouseX+diffX==0?0:(diffX/3), oldMouseY+ diffY==0?0:(diffY/3));
        }

    	if(!Mouse.isGrabbed()) return;
        boolean front = Keyboard.isKeyDown(Keyboard.KEY_W);
        boolean back = Keyboard.isKeyDown(Keyboard.KEY_S);
        boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
        boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
        boolean speedp = Keyboard.isKeyDown(Keyboard.KEY_R);
        boolean speedm = Keyboard.isKeyDown(Keyboard.KEY_F);
        boolean up = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean down = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        float nspeed;
        if(speedp) nspeed = movespeed * 5;
        else if(speedm) nspeed = movespeed / 2;
        else nspeed = movespeed;
        nspeed *= delta;
        if(up) pos.yCoord += nspeed;
        if(down) pos.yCoord -= nspeed;
        if(back){
            pos.xCoord -= Math.sin(Math.toRadians(rotation.yCoord)) * nspeed;
            pos.zCoord += Math.cos(Math.toRadians(rotation.yCoord)) * nspeed;
        }
        if(front){
            pos.xCoord += Math.sin(Math.toRadians(rotation.yCoord)) * nspeed;
            pos.zCoord -= Math.cos(Math.toRadians(rotation.yCoord)) * nspeed;
        }
        if(left){
            pos.xCoord += Math.sin(Math.toRadians(rotation.yCoord - 90)) * nspeed;
            pos.zCoord -= Math.cos(Math.toRadians(rotation.yCoord - 90)) * nspeed;
        }
        if(right){
            pos.xCoord += Math.sin(Math.toRadians(rotation.yCoord + 90)) * nspeed;
            pos.zCoord -= Math.cos(Math.toRadians(rotation.yCoord + 90)) * nspeed;
        }
    }
    
}