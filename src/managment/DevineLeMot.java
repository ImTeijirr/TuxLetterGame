package managment;

import env3d.Env;
import game.Room;
import game.Tux;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class DevineLeMot 
{
    private Env env;
    private Tux tux;
    private ArrayList<Letter> letters;
    private int nbLettresRestantes;
    private int temps;
    private Chronometre chrono;
    private boolean failed = false;
    private String percentageOfSuccess;
    
    public DevineLeMot(String mot, Env env, Room room, Tux tux)
    {
        this.env = env;
        this.letters = new ArrayList<Letter>();
        this.tux = tux;
        this.chrono = new Chronometre(5000);
        this.nbLettresRestantes = mot.length();
        
        // Ajout a la liste
        for(int i=0; i<mot.length(); i++)
        {
            Random r = new Random();
            int lowX = 1;
            int highX = room.getWidth();
            int lowZ = 20;
            int highZ = room.getDepth();
            int randomX = r.nextInt(highX-lowX) + lowX;
            int randomZ = r.nextInt(highZ-lowZ) + lowZ;
            
            Letter l = new Letter(mot.charAt(i), randomX, randomZ);
            letters.add(l);
        }
        
        // Sets up the camera
        env.setCameraXYZ(15, 20, 70);
        env.setCameraPitch(-10);
        
        // Turn off the default controls
        env.setDefaultControl(false);
    }
    
    public void jouer() 
    {
        // Remove tux for new games
        if (tux != null)
        {
            env.removeObject(tux);
        }
        // Insert Tux
        env.addObject(tux);
        
        // Add the letters
        for (int i=0; i<letters.size(); i++)
        {
            env.addObject(letters.get(i));
        }
        
        // Start chrono
        chrono.start();
        
        // Remember the word's size for statistics if failure
        double tailleMot = letters.size();
        
        // The main game loop
        do 
        {
            // Ask for user input, check if it collides and remove letters if necessary
            checkUserKey();
            for(int i=0; i<letters.size(); i++)
            {
                if (tuxMeetsLetter())
                {
                    env.removeObject(letters.get(i));
                    letters.remove(i);
                    nbLettresRestantes -= 1;
                }
            }

            // Update display
            env.advanceOneFrame();
            
            if(nbLettresRestantes == 0)
            {
                chrono.stop();
                this.temps = getTemps();
                System.out.println("Success ! Your time was " + Integer.toString(temps/1000) + " seconds");
                this.percentageOfSuccess = "100";
                return;
            }
        } while (env.getKey() != 1);
      
        //we have to keep the data to save our score (chrono, temps, nbLettresRestantes) 
        chrono.stop();
        this.temps = getTemps();
        System.out.println("Fail ! Your time was " + Integer.toString(temps/1000) + " seconds");
        failed = true;
        
        // Statistics of endgame
        double nbLettreTrouvees = (tailleMot - Double.valueOf(nbLettresRestantes));
        double successPercentage = nbLettreTrouvees / tailleMot * 100;
        String truncatedPercentage = new DecimalFormat("#").format(successPercentage).toString();
        System.out.println("Found : " + truncatedPercentage + " % -> " + nbLettreTrouvees + " letters of " + tailleMot + " letters!");
        this.percentageOfSuccess = truncatedPercentage;
    }
    
    public void checkUserKey()
    {
        int keyPressed = env.getKey();
        tux.move(keyPressed);
    }
    
    private boolean tuxMeetsLetter()
    {
        if (collision(tux, letters.get(0)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private double distance(Tux tux, Letter letter)
    {
        return Math.sqrt(Math.pow((letter.getX() - tux.getX()), 2) + Math.pow((letter.getZ() - tux.getZ()), 2));
    }
    
    private boolean collision(Tux tux, Letter letter)
    {
        if (distance(tux, letter) < 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private int getNbLettresRestantes()
    {
        return this.nbLettresRestantes;
    }
    
    public int getTemps()
    {
        return (int) chrono.getTime();
    }
    
    public boolean isFailed()
    {
        return failed;
    }
    
    public String getPercentageOfSuccess()
    {
        return this.percentageOfSuccess;
    }
}
