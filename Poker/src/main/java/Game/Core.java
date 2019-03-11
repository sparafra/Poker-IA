package Game;

import java.util.ArrayList;

import Object.Card;
import Object.Deck;
import Object.Face;
import Object.Hand;
import Object.Player;
import Object.Pot;
import Object.Score;
import Object.Stack;
import Object.Turn;

public class Core {

	Player P1;
	Player P2;
	Pot pot;
	Deck deck;
	float smallBlind;
	float bigBlind;
	Turn turn;
	boolean fineHand;
	boolean firstCall;
	
	public Core(Player P1, Player P2)
	{
		this.P1 = P1;
		this.P2 = P2;
		pot = null;
		deck = null;
		fineHand=false;
		firstCall = true;
		turn = Turn.FIRSTBET;
	}
	public void shuffleDeck()
	{
		deck = new Deck();
	}
	public Turn getTurn() {return turn;}
	public void nextTurn() {turn = turn.next();}
	public void check(Player P)
	{
		if(P == P1 && P1.isMyTurn() && pot.getBet() == 0)
		{	
			P1.setMyTurn(false);
			P2.setMyTurn(true);
			if(!P1.isDealer())
				turn = turn.next();
			if(turn == Turn.SHOWDOWN)
			{
				turn = Turn.FIRSTBET;
				
				fineHand = true;
				System.out.println("FINE");
				if(getWinner() == P1)
				{
					P1.getStack().sumToStack(pot.getPot());
				}
				else
				{
					P2.getStack().sumToStack(pot.getPot());
				}
				//fine mano
			}
		}
		else if(P == P2 && P2.isMyTurn() && pot.getBet() == 0)
		{
			P1.setMyTurn(true);
			P2.setMyTurn(false);
			if(!P2.isDealer())
				turn = turn.next();
			if(turn == Turn.SHOWDOWN)
			{
				turn = Turn.FIRSTBET;
				fineHand = true;
				System.out.println("FINE");
				if(getWinner() == P1)
				{
					P1.getStack().sumToStack(pot.getPot());
				}
				else
				{
					P2.getStack().sumToStack(pot.getPot());
				}
				//fine mano
			}
				
		}
	}
	
	public Player getWinner()
	{
		if(P1.getActualHand().getScore().ordinal() > P2.getActualHand().getScore().ordinal())
		{
			return P1;
		}
		else if(P1.getActualHand().getScore().ordinal() == P2.getActualHand().getScore().ordinal())
		{
			if(P1.getActualHand().getScore() == Score.HIGHCARD)
			{
				Face F1 = P1.getActualHand().highCard();
				Face F2 = P2.getActualHand().highCard();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.PAIR)
			{
				Face F1 = P1.getActualHand().pair();
				Face F2 = P2.getActualHand().pair();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.TWOPAIR)
			{
				ArrayList<Face> F1 = P1.getActualHand().twoPair();
				ArrayList<Face> F2 = P2.getActualHand().twoPair();
				
				if(F1.get(0).ordinal() > F2.get(0).ordinal() && F1.get(0).ordinal() > F2.get(1).ordinal() || F1.get(1).ordinal() > F2.get(0).ordinal() && F1.get(1).ordinal() > F2.get(1).ordinal())
					return P1;
				else if(F1.get(0).ordinal() == F2.get(0).ordinal() && F1.get(1).ordinal() == F2.get(1).ordinal())
					return null;
				else
					return P2;
				
			}
			else if(P1.getActualHand().getScore() == Score.TRIS)
			{
				Face F1 = P1.getActualHand().tris();
				Face F2 = P2.getActualHand().tris();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.STRAIGHT)
			{
				Face F1 = P1.getActualHand().straight();
				Face F2 = P2.getActualHand().straight();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.FLUSH)
			{
				Face F1 = P1.getActualHand().flush();
				Face F2 = P2.getActualHand().flush();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.FULLHOUSE)
			{
				ArrayList<Face> F1 = P1.getActualHand().twoPair();
				ArrayList<Face> F2 = P2.getActualHand().twoPair();
				
				if(F1.get(0).ordinal() > F2.get(0).ordinal())
					return P1;
				else if(F1.get(0).ordinal() == F2.get(0).ordinal())
				{
					if(F1.get(1).ordinal() > F2.get(1).ordinal())
						return P1;
					else
						return P2;
				}
				else
					return P2;
					
			}
			else if(P1.getActualHand().getScore() == Score.POKER)
			{
				Face F1 = P1.getActualHand().poker();
				Face F2 = P2.getActualHand().poker();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.STRAIGHTFLUSH)
			{
				Face F1 = P1.getActualHand().straightFlush();
				Face F2 = P2.getActualHand().straightFlush();
				if(F1.ordinal() > F2.ordinal())
					return P1;
				else
					return P2;
			}
			else
				return null;
		}
		else
		{
			return P2;
		}
	}
	
	public void call(Player P)
	{
		if(P == P1 && P1.isMyTurn())
		{
			P1.getStack().takeByStack(pot.getBet());
			pot.add(pot.getBet()*2);
			pot.setBet(0);
			P1.setMyTurn(false);
			P2.setMyTurn(true);
			if(!firstCall)
				turn = turn.next();
			else
				firstCall = false;
			if(turn == Turn.SHOWDOWN)
			{
				turn = Turn.FIRSTBET;
				fineHand = true;
				System.out.println("FINE");
				if(getWinner() == P1)
				{
					P1.getStack().sumToStack(pot.getPot());
				}
				else
				{
					P2.getStack().sumToStack(pot.getPot());
				}
				//fine mano
			}
		}
		else if(P == P2 && P2.isMyTurn())
		{
			P2.getStack().takeByStack(pot.getBet());
			pot.add(pot.getBet()*2);
			pot.setBet(0);
			P1.setMyTurn(true);
			P2.setMyTurn(false);
			if(!firstCall)
				turn = turn.next();
			else
				firstCall = false;
			if(turn == Turn.SHOWDOWN)
			{
				turn = Turn.FIRSTBET;
				fineHand = true;
				System.out.println("FINE");
				if(getWinner() == P1)
				{
					P1.getStack().sumToStack(pot.getPot());
				}
				else
				{
					P2.getStack().sumToStack(pot.getPot());
				}
				//fine mano
			}
		}
		
	}
	public boolean raise(Player P, float bet)
	{
		if(P == P1  && P1.isMyTurn())
		{
			if(P1.getStack().getStack() >= bet)
			{
				if(pot.getBet() > 0)
	    		{
	    			P1.getStack().takeByStack(pot.getBet());
	    			pot.add(pot.getBet()*2);
	    			P1.getStack().takeByStack(bet);
	    			pot.setBet(bet);
	    		}
	    		else
	    		{
	    			P1.getStack().takeByStack(bet);
	    			pot.setBet(bet);
	    		}
				P2.setMyTurn(true);
				P1.setMyTurn(false);
				return true;
			}
			else
				return false;
			
		}
		else if(P == P2 && P2.isMyTurn())
		{
			if(P2.getStack().getStack() >= bet)
			{
				if(pot.getBet() > 0)
	    		{
	    			P2.getStack().takeByStack(pot.getBet());
	    			pot.add(pot.getBet()*2);
	    			P2.getStack().takeByStack(bet);
	    			pot.setBet(bet);
	    		}
	    		else
	    		{
	    			P2.getStack().takeByStack(bet);
	    			pot.setBet(bet);
	    		}
				P1.setMyTurn(true);
				P2.setMyTurn(false);
				return true;
			}
			else
				return false;
			
		}
		return false;
		
	}
	public void fold(Player P)
	{
		fineHand = true;
		if(P == P1 && P1.isMyTurn())
		{
			P2.getStack().sumToStack(pot.getPot()+pot.getBet());
		}
		else if(P == P2 && P2.isMyTurn())
		{
			P1.getStack().sumToStack(pot.getPot()+pot.getBet());
		}
		
	}
	public Card drawCard(){
		return deck.getCard();
	}
	public Pot getPot() {return pot;}
	public Player getPlayer(int index)
	{
		if(index == 1)
			return P1;
		else
			return P2;
	}
	public boolean getFineHand() {return fineHand;} 
	public float getSmallBlind() {
		return smallBlind;
	}
	public void setSmallBlind(float smallBlind) {
		this.smallBlind = smallBlind;
	}
	public float getBigBlind() {
		return bigBlind;
	}
	public void setBigBlind(float bigBlind) {
		this.bigBlind = bigBlind;
	}
	public void newGame(float initialStack, float bigBlind, float smallBlind)
	{
		P1.setStack(new Stack(initialStack));
		P2.setStack(new Stack(initialStack));
		P1.setDealer(true);
		P1.setMyTurn(true);
		P2.setDealer(false);
		P2.setMyTurn(false);
		ArrayList<Card> p1Hand = new ArrayList<Card>();
		ArrayList<Card> p2Hand = new ArrayList<Card>();
		
		for(int k=0; k<5; k++)
		{
			p1Hand.add(deck.getCard());
			p2Hand.add(deck.getCard());
		}
		P1.setActualHand(new Hand(p1Hand));
		P2.setActualHand(new Hand(p2Hand));
		
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		
		pot = new Pot();
		
		if(P1.isDealer())
		{
			raise(P1, smallBlind);
			raise(P2, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - smallBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - bigBlind);
		}
		else
		{
			raise(P2, smallBlind);
			raise(P1, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - bigBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - smallBlind);
		}
		turn = Turn.FIRSTBET;
		
		//pot.setPot(smallBlind+bigBlind);
		
		
		
	}
	public void reset()
	{
		fineHand = false;
		firstCall = true;
		shuffleDeck();
		
		if(P1.isDealer())
		{
			P1.setDealer(false);
			P2.setDealer(true);
		}
		else
		{
			P1.setDealer(true);
			P2.setDealer(false);
		}
		
		ArrayList<Card> p1Hand = new ArrayList<Card>();
		ArrayList<Card> p2Hand = new ArrayList<Card>();
		
		for(int k=0; k<5; k++)
		{
			p1Hand.add(deck.getCard());
			p2Hand.add(deck.getCard());
		}
		P1.setActualHand(new Hand(p1Hand));
		P2.setActualHand(new Hand(p2Hand));
		
		pot = new Pot();
		
		if(P1.isDealer())
		{
			raise(P1, smallBlind);
			raise(P2, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - smallBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - bigBlind);
		}
		else
		{
			raise(P2, smallBlind);
			raise(P1, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - bigBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - smallBlind);
		}
		turn = Turn.FIRSTBET;
		
		//pot.setPot(smallBlind+bigBlind);
	}
	
}
