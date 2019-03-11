package Object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {

	ArrayList<Card> cards;
	public Hand()
	{
		
	}
	public Hand(ArrayList<Card> cards) 
	{
		this.cards = cards;
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
	}
	public ArrayList<Card> getCards(){return cards;}
	public void setCards(ArrayList<Card> cards) 
	{
		this.cards = cards;
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
	}
	
	public Score getScore()
	{
		Face point;
		ArrayList<Face> pointFaces;
		
		//Calculate hand score
		point = straightFlush();
		if(point != null)
		{
			return Score.STRAIGHTFLUSH;
		}
		
		point = poker();
		if(point != null)
		{
			return Score.POKER;
		}
		
		pointFaces = fullHouse();
		if(pointFaces!=null)
		{
			return Score.FULLHOUSE;
		}
		
		point = flush();
		if(point != null)
		{
			return Score.FLUSH;
		}
		
		point = straight();
		if(point!=null)
		{
			return Score.STRAIGHT;
		}
		
		point = tris();
		if(point != null)
		{
			return Score.TRIS;
		}
		
		pointFaces = twoPair();
		if(pointFaces!=null)
		{
			return Score.TWOPAIR;
		}
		
		point = pair();
		if(point != null)
		{
			return Score.PAIR;
		}
		
		point = highCard();
		if(point != null)
		{
			return Score.HIGHCARD;
		}
		
		
		
		return null;
	}
	public Face highCard()
	{
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
		return cards.get(4).getF();
	}
	
	public Face pair()
	{
		Face highcard = null;
		for(int k=0; k<cards.size(); k++)
		{
			int occorrenze=0;
			for(int i=0; i<cards.size(); i++)
			{
				if(cards.get(k).getF() == cards.get(i).getF())
				{
					occorrenze++;
				}
			}
			if(occorrenze >= 2)
			{
				if(highcard == null || highcard.ordinal() < cards.get(k).getF().ordinal())
				{
					highcard = cards.get(k).getF();
				}
			}
		}
		return highcard;
	}
	
	public ArrayList<Face> twoPair()
	{
		Face highcard1 = null;
		Face highcard2 = null;
		for(int k=0; k<cards.size(); k++)
		{
			int occorrenze=0;
			for(int i=0; i<cards.size(); i++)
			{
				if(cards.get(k).getF() == cards.get(i).getF())
				{
					occorrenze++;
				}
			}
			if(occorrenze >= 2)
			{
				if(highcard1 == null)
				{
					highcard1 = cards.get(k).getF();
				}
				else if(highcard2 == null && highcard1 != cards.get(k).getF())
				{
					highcard2 = cards.get(k).getF();
				}
			}
		}
		if(highcard1 != null && highcard2 != null)
		{
			ArrayList<Face> point = new ArrayList<Face>();
			point.add(highcard1);
			point.add(highcard2);
			return point;
		}
		else {
			return null;
		}
	}
	
	public Face tris()
	{
		Face highcard = null;
		for(int k=0; k<cards.size(); k++)
		{
			int occorrenze=0;
			for(int i=0; i<cards.size(); i++)
			{
				if(cards.get(k).getF() == cards.get(i).getF())
				{
					occorrenze++;
				}
			}
			if(occorrenze >= 3)
			{
				if(highcard == null || highcard.ordinal() < cards.get(k).getF().ordinal())
				{
					highcard = cards.get(k).getF();
				}
			}
		}
		return highcard;
	}
	
	public Face straight()
	{
		/*
		Card c1 = new Card(Face.TWO, Suite.CLUBS);
		Card c2 = new Card(Face.THREE, Suite.CLUBS);
		Card c3 = new Card(Face.FOUR, Suite.CLUBS);
		Card c4 = new Card(Face.FIVE, Suite.CLUBS);
		Card c5 = new Card(Face.ACE, Suite.CLUBS);
		
		cards.clear();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		*/
		
		ArrayList<Face> point = new ArrayList<Face>();
		
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
		
		for(int k=0; k<cards.size()-1; k++)
		{
			if(cards.get(k+1).getF().ordinal() != cards.get(k).getF().ordinal() +1 && cards.get(k+1).getF() != Face.ACE && cards.get(k).getF() != Face.FIVE)
			{
				return null;
			}
		}
		
		if(cards.get(4).getF() == Face.ACE && cards.get(3).getF() == Face.FIVE)
		{
			return Face.FIVE;
		}
		else
		{
			return cards.get(4).getF();
		}
	}
	
	public Face flush()
	{
		//return la carta alta del colore
		
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
		
		Suite color = cards.get(0).getS();
		for(int k=0; k<cards.size(); k++)
		{
			if(cards.get(k).getS() != color)
			{
				return null;
			}
		}
		return cards.get(cards.size()-1).getF();
	}
	
	public ArrayList<Face> fullHouse()
	{
		//return in pos[0] la carta per formare il tris e in pos[1] quella per la coppia
		
		/*
		Card c1 = new Card(Face.TWO, Suite.CLUBS);
		Card c2 = new Card(Face.TWO, Suite.DIAMONDS);
		Card c3 = new Card(Face.TWO, Suite.HEARTS);
		Card c4 = new Card(Face.FIVE, Suite.CLUBS);
		Card c5 = new Card(Face.FIVE, Suite.HEARTS);
		
		cards.clear();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		*/
		
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
		ArrayList<Face> point = new ArrayList<Face>();
		
		if(cards.get(0).getF() == cards.get(1).getF() && cards.get(0).getF() == cards.get(2).getF() && cards.get(3).getF() == cards.get(4).getF() )
		{
			point.add(cards.get(0).getF());
			point.add(cards.get(3).getF());
			return point;
		}
		else if(cards.get(0).getF() == cards.get(1).getF() && cards.get(2).getF() == cards.get(3).getF() && cards.get(2).getF() == cards.get(4).getF() )
		{
			point.add(cards.get(2).getF());
			point.add(cards.get(0).getF());
			return point;
		}
		else
		{
			return null;
		}
	}
	
	public Face poker()
	{
		Face f = cards.get(0).getF();
		int j=0;
		for(int k=0; k<cards.size(); k++)
		{
			if(cards.get(k).getF() == f)
				j++;
		}
		if(j == 4)
		{
			return f;
		}
		else
		{
			return null;
		}
		
	}
	public Face straightFlush()
	{
		/*
		Card c1 = new Card(Face.TEN, Suite.CLUBS);
		Card c2 = new Card(Face.JACK, Suite.CLUBS);
		Card c3 = new Card(Face.QUEEN, Suite.CLUBS);
		Card c4 = new Card(Face.KING, Suite.CLUBS);
		Card c5 = new Card(Face.ACE, Suite.CLUBS);
		
		cards.clear();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		*/
		if(straight() != null && flush() != null)
		{
			return straight();
		}
		return null;
	}
}
