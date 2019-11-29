package game;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Partie 
{
    private String date;
    private String mot;
    private int niveau;
    private String percentageTrouve;
    private int temps;
    
    public Partie(String date, String mot, int level , String percentageTrouve, int temps)
    {
        this.date = date;
        this.mot = mot;
        this.niveau = level;
        this.percentageTrouve = percentageTrouve;
        this.temps = temps;
    }
    
    public Partie(Node domPartie)
    {
        try 
        {
            this.date = domPartie.getAttributes().item(0).getNodeValue().toString();
            this.mot = domPartie.getFirstChild().getTextContent();
            this.niveau = Character.getNumericValue(domPartie.getFirstChild().getAttributes().item(0).toString().charAt(7));
            
            if(domPartie.getAttributes().getLength() == 2 && domPartie.getAttributes().getNamedItem("found") != null)
            {
                this.percentageTrouve = domPartie.getAttributes().getNamedItem("found").getNodeValue().toString().replace("%", "");
            }
            else
            {
                this.percentageTrouve = "100";
            }
            
            this.temps = Integer.valueOf(domPartie.getLastChild().getTextContent())*1000;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String getTrouve()
    {
        return this.percentageTrouve;
    }
    
    public void setTrouve(String percentageTrouve)
    {
        this.percentageTrouve = percentageTrouve;
    }
    
    public void setTemps(int temps)
    {
        this.temps = temps;
    }
    
    public String getMot()
    {
        return this.mot;
    }
    
    public int getTemps()
    {
        return this.temps/1000;
    }
    
    public int getNiveau()
    {
        return this.niveau;
    }
    
    @Override
    public String toString()
    {
        return "Date : " + date + " / Mot : " + mot + " / Niveau : " + niveau  + " / Pourcentage trouvé : " + percentageTrouve + "%" + " / Temps : " + temps/1000;
    }
    
    public String getDate()
    {
        return this.date;
    }
}
