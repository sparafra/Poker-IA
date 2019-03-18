package Object;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	ArrayList<Card> cards;
	
	ArrayList<String> Suites;
	ArrayList<Integer> Faces;
	
	public Deck()
	{
		Suites = new ArrayList<String>();
		Faces = new ArrayList<Integer>();

		Suites.add("CLUBS");
		Suites.add("HEARTS");
		Suites.add("SPADES");
		Suites.add("DIAMONDS");
		
		Faces.add(2);
		Faces.add(3);
		Faces.add(4);
		Faces.add(5);
		Faces.add(6);
		Faces.add(7);
		Faces.add(8);
		Faces.add(9);
		Faces.add(10);
		Faces.add(11);
		Faces.add(12);
		Faces.add(13);
		Faces.add(14);
		
		
		cards = new ArrayList<Card>();
		for(int f : Faces)
		{
			for(String s : Suites)
			{
				cards.add(new Card(f, s));
			}
		}
		/*
		for(Face f : Face.values())
		{
			for(Suite s : Suite.values())
			{
				cards.add(new Card(f, s));
			}
		}
		*/
		Collections.shuffle(cards);
	}
	
	public Card getCard()
	{
		return cards.remove(0);
	}
	
	
}
