package managment;

import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Dico 
{
    ArrayList<String> listLevel1;
    ArrayList<String> listLevel2;
    ArrayList<String> listLevel3;
    ArrayList<String> listLevel4;
    ArrayList<String> listLevel5;
    
    String pathToDicoFile;
    
    public Dico(String pathToDicoFile)
    {
        this.pathToDicoFile = pathToDicoFile;
        listLevel1 = new ArrayList<String>();
        listLevel2 = new ArrayList<String>();
        listLevel3 = new ArrayList<String>();
        listLevel4 = new ArrayList<String>();
        listLevel5 = new ArrayList<String>();
        
        try 
        {
            // analyse du document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder p = dbFactory.newDocumentBuilder();
            // récupération de la structure objet du document
            Document doc = p.parse(pathToDicoFile);

            for(int i = 1; i< doc.getChildNodes().item(0).getChildNodes().getLength(); i += 2)
            {
                String mot = doc.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).getTextContent().toString();
                int level = Integer.valueOf(doc.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(3).getTextContent().toString());
                switch(level)
                {
                    case 1: listLevel1.add(mot); break;
                    case 2: listLevel2.add(mot); break;
                    case 3: listLevel3.add(mot); break;
                    case 4: listLevel4.add(mot); break;
                    case 5: listLevel5.add(mot); break;
                    default: System.out.println("ERROR : Undefined level !!");
                }
            }   
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public String getWordFromListLevel(int level)
    {
        ArrayList<String> currentList = null;
        switch(level)
        {
            case 1: currentList = listLevel1; break;
            case 2: currentList = listLevel2; break;
            case 3: currentList = listLevel3; break;
            case 4: currentList = listLevel4; break;
            case 5: currentList = listLevel5; break;
        }
        
        if (currentList.size() != 0)
        {
            int randIndex = new Random().nextInt((currentList.size()-1 - 0) + 1) + 0;
            return currentList.get(randIndex);
        }
        else
            return "Impossible to find string in this list, empty list";
    }
    
    public boolean addWordToDico(int level, String word)
    {
        switch(level)
        {
            case 1: listLevel1.add(word); return true;
            case 2: listLevel2.add(word); return true;
            case 3: listLevel3.add(word); return true;
            case 4: listLevel4.add(word); return true;
            case 5: listLevel5.add(word); return true;
        }
        return false;
    }
    
    public String getPathToDicoFile()
    {
        return pathToDicoFile;
    }
}
