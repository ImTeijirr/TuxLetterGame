package game;

import env3d.Env;
import managment.LectureClavier;

public class Game 
{   
    public static void main(String[] args) 
    {
        //Instanciate a new Jeu with an existing profile
        Jeu j = new Jeu("src/xml/profile.xml");
        
        //Play the game
        j.jouer();

        System.out.println("Do you want do launch another game?");
        
        while (LectureClavier.lireChaine().equals("yes"))
        {
            j.nextLevel();
            j.jouer();
            System.out.println("Do you want do launch another game?");
        }
        System.out.println("\nFin des parties");
        System.out.println("\nHistorique : \n" + j.getProfile().toString());
    }
}
