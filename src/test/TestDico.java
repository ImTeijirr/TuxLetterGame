package test;

import managment.Dico;

public class TestDico 
{
    public static void main(String[] args)
    {
        Dico d = new Dico("");
        d.addWordToDico(1, "Salut");
        d.addWordToDico(3, "Yoyoyo");
        
        System.out.println(d.getWordFromListLevel(1) + " - " + d.getWordFromListLevel(2) + " - " + d.getWordFromListLevel(3));
    }
}
