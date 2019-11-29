package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Profile 
{
    private String nom;
    private String dateNaissance;
    private String avatar;
    private int lastLevel;
    private String pathToXMLFile;
    public boolean lastGameEnded = true;
    private ArrayList<Partie> parties;
    
    public Profile(String nom, String dateNaissance)
    {
        this.parties = new ArrayList<Partie>();
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }
    
    public Profile(String filename)
    {
        this.pathToXMLFile = filename;
        this.parties = new ArrayList<Partie>();
        try 
        {
            // analyse du document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder p = dbFactory.newDocumentBuilder();
            // récupération de la structure objet du document
            Document doc = p.parse(filename);
            
            this.nom = doc.getChildNodes().item(1).getChildNodes().item(1).getTextContent();
            this.avatar = doc.getChildNodes().item(1).getChildNodes().item(3).getTextContent();
            
            // For each game already existing, add it to our array
            for(int i=1; i<doc.getChildNodes().item(1).getChildNodes().item(7).getChildNodes().getLength(); i+=2)
            {
                Node game = doc.getChildNodes().item(1).getChildNodes().item(7).getChildNodes().item(i);
                Partie currentGame = new Partie(game);
                parties.add(currentGame);
            }

            char levelOfLastGame = doc.getChildNodes().item(1).getChildNodes().item(7).getLastChild().getPreviousSibling().getFirstChild().getAttributes().item(0).toString().charAt(7);
            this.lastLevel = Character.getNumericValue(levelOfLastGame);
            
            NamedNodeMap map = doc.getChildNodes().item(1).getChildNodes().item(7).getLastChild().getPreviousSibling().getAttributes();
            for (int i=0; i<map.getLength(); i++)
            {
                if (map.item(i).getNodeName().equals("found"))
                {
                    this.lastGameEnded = false;
                    break;
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void ajouterPartie(Partie p)
    {
        parties.add(p);
        saveInXMLProfile(parties.indexOf(p));
    }
    
    public int getLastLevel()
    {
        return this.lastLevel;
    }
    
    public String toString()
    {
        String str = "";
        
        for(int i=0; i<parties.size(); i++)
        {
            str += "Partie " + String.valueOf(i+1) + " : " + parties.get(i).toString() + "\n";
        }
        
        return str;
    }
    
    public void saveInXMLProfile(int partieIndex)
    {
        try 
        {
            // analyse du document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder p = dbFactory.newDocumentBuilder();
            // récupération de la structure objet du document
            Document doc = p.parse(this.pathToXMLFile);
            
            Node tab = doc.createTextNode("\t");
            Node enter = doc.createTextNode("\n");
            
            // Add the game to the profile in dom
            Element game = doc.createElement("game");
            Element word = doc.createElement("word");
            Element time = doc.createElement("time");
            
            word.appendChild(enter);
            word.appendChild(doc.createTextNode(parties.get(partieIndex).getMot()));
            word.setAttribute("level", String.valueOf(parties.get(partieIndex).getNiveau()));
            
            time.appendChild(doc.createTextNode(String.valueOf(parties.get(partieIndex).getTemps())));
            
            game.setAttribute("date", parties.get(partieIndex).getDate());
            if(!parties.get(partieIndex).getTrouve().equals("100"))
            {
                game.setAttribute("found", parties.get(partieIndex).getTrouve() + "%");
            }         
            game.appendChild(word); 
            game.appendChild(time);

            Node games = doc.getChildNodes().item(1).getChildNodes().item(7);
            games.appendChild(tab);
            games.appendChild(game);
            games.appendChild(enter);
            
            // Save the new XML file
            Source source = new DOMSource(doc);
            File xmlFile = new File("src/xml/profile.xml");
            StreamResult result = new StreamResult(new OutputStreamWriter(
                                  new FileOutputStream(xmlFile), "UTF-8"));
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
            
            System.out.println("Game saved in profile.xml");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) 
    {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    public static String profileDateToXmlDate(String profileDate) 
    {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
}
