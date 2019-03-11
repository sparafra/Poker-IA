package Object;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	ArrayList<Card> cards;
	
	public Deck()
	{
		cards = new ArrayList<Card>();
		for(Face f : Face.values())
		{
			for(Suite s : Suite.values())
			{
				cards.add(new Card(f, s));
			}
		}
		Collections.shuffle(cards);
	}
	
	public Card getCard()
	{
		return cards.remove(0);
	}
	
	
}
