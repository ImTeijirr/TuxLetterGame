package game;

import env3d.Env;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import managment.DevineLeMot;
import managment.Dico;
import managment.LectureClavier;

public class Jeu 
{
    private Env env;
    private Tux tux;
    private Room room;
    private int level;
    private Dico dico;
    private Partie currentPartie;
    private Profile profile;
    
    /*
    public Jeu()
    {
        askForUserInput();
        initAll();
    }
    */
    
    public Jeu(String pathXmlProfile)
    {
        this.profile = new Profile(pathXmlProfile);
        this.level = profile.getLastLevel();
        
        if(this.profile.lastGameEnded)
        {
            this.level++;
        }
        
        // If level is 6, we restart at 1
        if (this.level == 6)
        {
            this.level = 1;
        }
        
        this.dico = new Dico("src/xml/dico.xml");
        initAll();
    }
    
    /*
    public void askForUserInput()
    {
        System.out.println("Input the level : ");
        level = LectureClavier.lireEntier();
        
        dico = new Dico("MonDico");
        System.out.println("Input a word : ");
        dico.addWordToDico(level, LectureClavier.lireChaine());
    }
    */
    
    public void initAll() 
    {
        env = new Env();
        
        // Instanciate a room 
        room = new Room();
        env.setRoom(room);
        
        // Instanciate a tux
        tux = new Tux(10, 10, 30);
    }
    
    public void jouer()
    {
        System.out.println("Current level : " + level);
        String currentWord = dico.getWordFromListLevel(level);
        System.out.println("Current word : " + currentWord);
        
        // Date actuelle
        LocalDate myDateObj = LocalDate.now();
        String myDate = myDateObj.toString();
        
        DevineLeMot devine = new DevineLeMot(currentWord, env, room, tux);
        devine.jouer();
        
        // Fin de la partie, on ajoute dans le profile
        profile.ajouterPartie(new Partie(myDate, currentWord, level, devine.getPercentageOfSuccess(), devine.getTemps()));
  
        if(devine.isFailed())
        {
            //Post-Process: game is finished
            System.out.println("\nFin des parties");
            System.out.println("\nHistorique : \n" + this.profile.toString());
            env.exit();
        }
    }
    
    public Profile getProfile()
    {
        return this.profile;
    }
    
    public void nextLevel()
    {
        this.level++;
    }
}
