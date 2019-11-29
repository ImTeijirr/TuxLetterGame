package managment;

import env3d.EnvObject;

public class Letter extends EnvObject
{
    private char letter;
    
    public Letter(char l, double x, double z)
    {
        this.letter = Character.toLowerCase(l);
        String path;
        setX(x);
        setY(10);
        setZ(z);
        setScale(1);
        
        if (this.letter == ' ')
        {
            path = "models/letter/cube.png";
        }
        else
        {
            path = "models/letter/" + this.letter + ".png";
        }
        setTexture(path);
    }
}
