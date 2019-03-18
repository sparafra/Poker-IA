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
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
		/*
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});*/
	}
	public ArrayList<Card> getCards(){return cards;}
	public void setCards(ArrayList<Card> cards) 
	{
		this.cards = cards;
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
	}
	
	
	public Score getScore()
	{
		int point;
		ArrayList<Integer> pointFaces;
		
		//Calculate hand score
		point = straightFlush();
		if(point != -1)
		{
			return Score.STRAIGHTFLUSH;
		}
		
		point = poker();
		if(point != -1)
		{
			return Score.POKER;
		}
		
		pointFaces = fullHouse();
		if(pointFaces != null)
		{
			return Score.FULLHOUSE;
		}
		
		point = flush();
		if(point != -1)
		{
			return Score.FLUSH;
		}
		
		point = straight();
		if(point!= -1)
		{
			return Score.STRAIGHT;
		}
		
		point = tris();
		if(point != -1)
		{
			return Score.TRIS;
		}
		
		pointFaces = twoPair();
		if(pointFaces != null)
		{
			return Score.TWOPAIR;
		}
		
		point = pair();
		if(point != -1)
		{
			return Score.PAIR;
		}
		
		point = highCard();
		if(point != -1)
		{
			return Score.HIGHCARD;
		}
		
		
		
		return null;
	}
	public int highCard()
	{
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
		return cards.get(4).getF();
	}
	
	public int pair()
	{
		int highcard = -1;
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
				if(highcard == -1 || highcard < cards.get(k).getF())
				{
					highcard = cards.get(k).getF();
				}
			}
		}
		return highcard;
	}
	
	public ArrayList<Integer> twoPair()
	{
		int highcard1 = -1;
		int highcard2 = -1;
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
				if(highcard1 == -1)
				{
					highcard1 = cards.get(k).getF();
				}
				else if(highcard2 == -1 && highcard1 != cards.get(k).getF())
				{
					highcard2 = cards.get(k).getF();
				}
			}
		}
		if(highcard1 != -1 && highcard2 != -1)
		{
			ArrayList<Integer> point = new ArrayList<Integer>();
			point.add(highcard1);
			point.add(highcard2);
			return point;
		}
		else {
			return null;
		}
	}
	
	public int tris()
	{
		int highcard = -1;
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
				if(highcard == -1 || highcard < cards.get(k).getF())
				{
					highcard = cards.get(k).getF();
				}
			}
		}
		return highcard;
	}
	
	public int straight()
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
		
		//ArrayList<Face> point = new ArrayList<Face>();
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
		/*
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		        return o1.getF().compareTo(o2.getF());
		    }
		});
		*/
		for(int k=0; k<cards.size()-1; k++)
		{
			if(cards.get(k+1).getF() != cards.get(k).getF() +1 && cards.get(k+1).getF() != 14 && cards.get(k).getF() != 5)
			{
				return -1;
			}
		}
		
		if(cards.get(4).getF() == 14 && cards.get(3).getF() == 5) // SCALA DA ASSO A 5
		{
			return 5;
		}
		else
		{
			return cards.get(4).getF();
		}
	}
	
	public int flush()
	{
		//return la carta alta del colore
		
		Collections.sort(cards, new Comparator<Card>() {
		    public int compare(Card o1, Card o2) {
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
		
		String color = cards.get(0).getS();
		for(int k=0; k<cards.size(); k++)
		{
			if(cards.get(k).getS() != color)
			{
				return -1;
			}
		}
		return cards.get(cards.size()-1).getF();
	}
	
	public ArrayList<Integer> fullHouse()
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
		    	if(o1.getF() < o2.getF())
		    		return -1;
		    	else if(o1.getF() == o2.getF())
		    		return 0;
		    	else 
		    		return 1;
		    	
		    }
		});
		ArrayList<Integer> point = new ArrayList<Integer>();
		
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
	
	public int poker()
	{
		int f = cards.get(0).getF();
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
			return -1;
		}
		
	}
	public int straightFlush()
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
		if(straight() != -1 && flush() != -1)
		{
			return straight();
		}
		return -1;
	}

}
