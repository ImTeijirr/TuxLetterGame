package game;

public class Room 
{
    int depth;
    int height;
    int width;
    String textureBottom;
    String textureNorth;
    String textureEast;
    String textureWest;
    String textureTop;
    String textureSouth;
    
    public Room()
    {
        this.textureBottom = "textures/skybox/interstellar/bottom.png";
        this.textureNorth = "textures/skybox/interstellar/north.png";
        this.textureEast = "textures/skybox/interstellar/east.png";
        this.textureWest = "textures/skybox/interstellar/west.png";
        
        this.depth = 30;
        this.height = 30;
        this.width = 30;
    }
    
    public int getDepth()
    {
        return this.depth;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public int getWidth()
    {
        return this.width;
    }
}
