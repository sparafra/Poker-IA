package Game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import DLV.AIcore;
import DLV.Decision;
import DLV.cardChanged;
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
	boolean cardChanged;
	AIcore AI;
	boolean allIn;
	public Core(Player P1, Player P2)
	{
		this.P1 = P1;
		this.P2 = P2;
		pot = null;
		deck = null;
		fineHand=false;
		firstCall = true;
		cardChanged = false;
		allIn = false;
		turn = Turn.FIRSTBET;
	}
	public void shuffleDeck()
	{
		deck = new Deck();
	}
	public boolean isAllIn() {return allIn;}
	
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
				int F1 = P1.getActualHand().highCard();
				int F2 = P2.getActualHand().highCard();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.PAIR)
			{
				int F1 = P1.getActualHand().pair();
				int F2 = P2.getActualHand().pair();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.TWOPAIR)
			{
				ArrayList<Integer> F1 = P1.getActualHand().twoPair();
				ArrayList<Integer> F2 = P2.getActualHand().twoPair();
				
				if(F1.get(0) > F2.get(0) && F1.get(0) > F2.get(1) || F1.get(1) > F2.get(0) && F1.get(1) > F2.get(1))
					return P1;
				else if(F1.get(0) == F2.get(0) && F1.get(1) == F2.get(1))
					return null;
				else
					return P2;
				
			}
			else if(P1.getActualHand().getScore() == Score.TRIS)
			{
				int F1 = P1.getActualHand().tris();
				int F2 = P2.getActualHand().tris();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.STRAIGHT)
			{
				int F1 = P1.getActualHand().straight();
				int F2 = P2.getActualHand().straight();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.FLUSH)
			{
				int F1 = P1.getActualHand().flush();
				int F2 = P2.getActualHand().flush();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.FULLHOUSE)
			{
				ArrayList<Integer> F1 = P1.getActualHand().twoPair();
				ArrayList<Integer> F2 = P2.getActualHand().twoPair();
				
				if(F1.get(0) > F2.get(0))
					return P1;
				else if(F1.get(0) == F2.get(0))
				{
					if(F1.get(1) > F2.get(1))
						return P1;
					else
						return P2;
				}
				else
					return P2;
					
			}
			else if(P1.getActualHand().getScore() == Score.POKER)
			{
				int F1 = P1.getActualHand().poker();
				int F2 = P2.getActualHand().poker();
				if(F1 > F2)
					return P1;
				else
					return P2;
			}
			else if(P1.getActualHand().getScore() == Score.STRAIGHTFLUSH)
			{
				int F1 = P1.getActualHand().straightFlush();
				int F2 = P2.getActualHand().straightFlush();
				if(F1 > F2)
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
	
	public Player getGameWinner()
	{
		if(P1.getStack().getStack() == 0)
			return P2;
		else if(P2.getStack().getStack() == 0)
			return P1;
		else 
			return null;
			
		
	}
	public void call(Player P)
	{
		if(P == P1 && P1.isMyTurn())
		{
			//MODIFICHE BACKUP SOLO ALL'INTERNO DELL ELSE

			if(P1.getStack().getStack() < pot.getBet())
			{
				P2.getStack().setStack((pot.getBet() - P1.getStack().getStack()));
				pot.setBet(pot.getBet() - P1.getStack().getStack());
				P1.getStack().takeByStack(pot.getBet());
				pot.add(pot.getBet()*2);
				pot.setBet(0);
				allIn = true;
			}
			else
			{
				P1.getStack().takeByStack(pot.getBet());
				pot.add(pot.getBet()*2);
				pot.setBet(0);
			}
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
			//MODIFICHE BACKUP SOLO ALL'INTERNO DELL ELSE
			if(P2.getStack().getStack() < pot.getBet())
			{
				
				P1.getStack().setStack((pot.getBet() - P2.getStack().getStack()));
				pot.setBet(pot.getBet() - P1.getStack().getStack());
				P2.getStack().takeByStack(pot.getBet());
				pot.add(pot.getBet()*2);
				pot.setBet(0);
				allIn = true;
			}
			else
			{
				P2.getStack().takeByStack(pot.getBet());
				pot.add(pot.getBet()*2);
				pot.setBet(0);
			}
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
				if(firstCall)
					firstCall = false;
				
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
			else if(P1.getStack().getStack() < bet)
			{
				allIn = true;
				bet = P1.getStack().getStack();
				if(pot.getBet() > 0)
				{
					if(pot.getBet() > bet)
					{
						P2.getStack().sumToStack(pot.getBet() - bet);
						pot.add(bet);
						P1.getStack().takeByStack(bet);
						
					}
					else
					{
						P1.getStack().takeByStack(bet);
		    			pot.setBet(bet);
					}
				}
				else
				{
					P1.getStack().takeByStack(bet);
	    			pot.setBet(bet);
				}
				return true;
			}
			else
				return false;
			
		}
		else if(P == P2 && P2.isMyTurn())
		{
			if(P2.getStack().getStack() >= bet)
			{
				if(firstCall)
					firstCall = false;
				
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
			else if(P2.getStack().getStack() < bet)
			{
				allIn = true;
				bet = P2.getStack().getStack();
				if(pot.getBet() > 0)
				{
					if(pot.getBet() > bet)
					{
						P1.getStack().sumToStack(pot.getBet() - bet);
						pot.add(bet);
						P2.getStack().takeByStack(bet);
						
					}
					else
					{
						P2.getStack().takeByStack(bet);
		    			pot.setBet(bet);
					}
				}
				else
				{
					P2.getStack().takeByStack(bet);
	    			pot.setBet(bet);
				}
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
		System.out.println("Prima P1: "+P1.getStack().getStack());
		System.out.println("Prima P2: "+P2.getStack().getStack());
		System.out.println("Pot: " +pot.getPot());
		System.out.println("Bet: "+pot.getBet());

		if(P == P1 && P1.isMyTurn())
		{
			P2.getStack().sumToStack(pot.getPot());
		}
		else if(P == P2 && P2.isMyTurn())
		{
			P1.getStack().sumToStack(pot.getPot());
		}
		System.out.println(P1.getStack().getStack());
		System.out.println(P2.getStack().getStack());

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
		firstCall = true;
		//pot.setPot(smallBlind+bigBlind);
		allIn = false;
		AI = new AIcore();
		//Decision d = AI.getFinalDecision(P1.getActualHand(), P1.getStack(), pot, new cardChanged(0));
		//System.out.println(d.toString());
		//AI.Do(P1.getActualHand());
		
		
		
		
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
			P1.setMyTurn(false);
			P2.setMyTurn(true);
		}
		else
		{
			P1.setDealer(true);
			P2.setDealer(false);
			P1.setMyTurn(true);
			P2.setMyTurn(false);
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
			System.out.println("P1 Dealer");

			raise(P1, smallBlind);
			raise(P2, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - smallBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - bigBlind);
		}
		else
		{
			System.out.println("P2 Dealer");
			System.out.println(P2.isMyTurn());

			raise(P2, smallBlind);
			raise(P1, bigBlind);
			//P1.getStack().setStack(P1.getStack().getStack() - bigBlind);
			//P2.getStack().setStack(P2.getStack().getStack() - smallBlind);
		}
		System.out.println(pot.getPot());
		turn = Turn.FIRSTBET;
		firstCall = true;
		allIn = false;
		//pot.setPot(smallBlind+bigBlind);
	}
	
	public AIcore getAIcore() {return AI;}
	public boolean getCardChanged() {
		return cardChanged;
	}
	public void setCardChanged(boolean cardChanged) {
		this.cardChanged = cardChanged;
	}
	
}
