package Game;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Media {
	
	ArrayList<Image> CardsFront;
	ArrayList<Image> CardsBack;
	
	HashMap<String, Image> cards;
	HashMap<String, Image> fiches;
	
	Image dealer;
	
	String Path;
	Toolkit tk;
	
	public Media()
	{
		tk = Toolkit.getDefaultToolkit();
		CardsFront = new ArrayList<Image>();
		CardsBack = new ArrayList<Image>();
		cards = new HashMap<String, Image>();
		fiches = new HashMap<String, Image>();
		LoadImage();
		
	}
	public void LoadImage()
	{
		Path = "";
		try {
			Path = new File(".").getCanonicalPath();
			Path += "\\src\\main\\resources\\Images";
			System.out.println(Path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dealer = tk.getImage(Path + "\\Dealer\\dealer.png");
		
		LinkedList<File> FrontCards = getFiles(Path + "\\Card\\Front");
		LinkedList<File> BackCards = getFiles(Path + "\\Card\\Back");
		LinkedList<File> Fiches = getFiles(Path + "\\Fiches");
		
		for(int k=0; k<FrontCards.size(); k++)
		{
			Image i = tk.getImage(Path + "\\Card\\Front\\" + FrontCards.get(k).getName());
			cards.put(FrontCards.get(k).getName().substring(0, FrontCards.get(k).getName().indexOf(".")), i);
			//System.out.println(FrontCards.get(k).getName().substring(0, FrontCards.get(k).getName().indexOf(".")));
			CardsFront.add(i);
		}
		
		for(int k=0; k<BackCards.size(); k++)
		{
			Image i = tk.getImage(Path + "\\Card\\Back\\" + BackCards.get(k).getName());
			CardsBack.add(i);
		}
		for(int k=0; k<Fiches.size(); k++)
		{
			Image i = tk.getImage(Path + "\\Fiches\\" + Fiches.get(k).getName());
			fiches.put(Fiches.get(k).getName().substring(0, Fiches.get(k).getName().indexOf(".")), i);
		}
		
		
	}
	public Image getCard(String FaceSuite)
	{
		return cards.get(FaceSuite);
	}
	public Image getFiches(String range)
	{
		return fiches.get(range);
	}
	public Image getBackCard(int pos)
	{
		return CardsBack.get(pos);
	}
	public Image getDealer()
	{
		return dealer;
	}
	private static LinkedList<File> getFiles(String Path)
	{
		File directory = new File(Path);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        LinkedList<File> Files = new LinkedList<File>();
        for (File file : fList)
        {
            if (file.isFile())
            {
            	Files.add(file);
            }
        }
        return Files;
	}
}
