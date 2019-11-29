package game;

import env3d.EnvObject;
import org.lwjgl.input.Keyboard;

public class Tux extends EnvObject
{
    public Tux(double x, double y, double z)
    {
        setX(x);
        setY(y);
        setZ(z);
        setScale(1);
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");
    }
    
    public void move(int currentKey) 
    {
        if (currentKey == Keyboard.KEY_UP) 
        {
           this.setZ(this.getZ() - 1);
        }
        if (currentKey == Keyboard.KEY_DOWN) 
        {
           this.setZ(this.getZ() + 1);
        }
        if (currentKey == Keyboard.KEY_RIGHT) 
        {
           this.setX(this.getX() + 1);
        }
        if (currentKey == Keyboard.KEY_LEFT) 
        {
           this.setX(this.getX() - 1);
        }
    }
}
