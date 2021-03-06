package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import DLV.Decision;
import DLV.cardChanged;
import Object.Card;
import Object.Player;
import Object.Turn;

public class Game extends JFrame{

private static final int PADDING = 5;
	
private final static String newline = "\n";

	Toolkit tk;
	Core core;
	Player P1;
	Player P2;
	Media M;
	
	JPanel Nord;
	JPanel Center;
	JPanel South;
	JLabel background;
	
	//ArrayList<JLabel> cardP1;
	ArrayList<JButton> buttonP1;
	ArrayList<JLabel> cardP2;
	ArrayList<JButton> buttonP2;
	JSpinner betValue;
	
	JTextArea History;
	JButton Debug;
	
	boolean DebugMode; 
	
	ArrayList<Integer> selectedCard;
	
	int Turn = 0;
	
	float MIDDLE = 700;
	float LOW = 200;
	float HIGH = 1300;
	
	public Game()
	{
		super("Game");
		P1 = new Player("AI");
		P2 = new Player("Francesco");
		core = new Core(P1, P2);
		M = new Media();
		//cardP1 = new ArrayList<JLabel>();
		cardP2 = new ArrayList<JLabel>();
		buttonP1 = new ArrayList<JButton>();
		buttonP2 = new ArrayList<JButton>();
		selectedCard = new ArrayList<Integer>();
		
		History = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(History); 
		History.setEditable(false);
		History.setName("History");
		
		DebugMode = true;
		
		initGUI();
		StartGame();
		initEHCard();
		initEHButton();
	}
	
	public void StartGame()
	{
		
		core.shuffleDeck();
		core.newGame(1500, 50, 100);
		
		drawPlayerCard(P1, P2);
		
		if(core.P1.isMyTurn())
		{
			if(core.getPlayer(1).isMyTurn())
			{
				Nord.setBackground(Color.green);
				South.setBackground(Color.red);
			}
			else
			{
				Nord.setBackground(Color.red);
				South.setBackground(Color.green);
			}
			if(core.getCardChanged())
				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
			else
				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

		}
	}
	
	public void DoMovesAI(Decision d)
	{
		
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Do AI MOVES: ");
		if(DebugMode)
			History.append("-------- START HAND ------- " + newline + core.P1.getActualHand().getCards().toString() + newline);
		else
			History.append("-------- START HAND ------- "+ newline);

		if(d.getMossa() == 0)
		{
			core.fold(P1);
			History.append("Fold" + newline);

			System.out.println("AI FOLD");
			if(core.getTurn() == core.getTurn().DISCARDCARD)
    		{
    			core.setCardChanged(true);
    			buttonP2.get(0).setText("Scarta");
    			buttonP2.get(1).setEnabled(false);
    			buttonP2.get(2).setEnabled(false);
    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
    			//JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
    		}
    		
    		updateText();
    		
			
	    	if(core.getFineHand())
	    	{
	            //JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
	    		History.append("Giocatore Vince La Mano" + newline);
	    		History.append("-------- FINE HAND -------" + newline);
				System.out.println("Giocatore Winner ");
	    		System.out.println("Reset");
	    		core.reset();
	    		updateText();
	    		initGUI();
	    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
	    		initEHCard();
	    		initEHButton();
	    		if(core.P1.isMyTurn())
	    		{
	    			
	    			if(core.getCardChanged())
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
	    			else
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

	    		}
	    	}
	    	if(core.getPlayer(1).isMyTurn())
			{
				Nord.setBackground(Color.green);
				South.setBackground(Color.red);
			}
			else
			{
				Nord.setBackground(Color.red);
				South.setBackground(Color.green);
			}
		}
		else if(d.getMossa() == 1)
		{
			//Check/Call
	    	if(core.getPlayer(1).isMyTurn())
	    	{
	    		//System.out.println("my turn");
	    		if(core.getPot().getBet() == 0)
	        	{
	        		//check
	        		core.check(P1);
	    			History.append("CHECK" + newline);

	    			System.out.println("AI CHECK");

	        	}
	    		
	    		if(core.getTurn() == core.getTurn().DISCARDCARD)
	    		{
	    			core.setCardChanged(true);
	    			buttonP2.get(0).setText("Scarta");
	    			buttonP2.get(1).setEnabled(false);
	    			buttonP2.get(2).setEnabled(false);
	    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
	    			//JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
	    		}
	    		
	    		updateText();
	    	}
	    	if(core.getFineHand())
	    	{
	    		History.append(core.getWinner().getName() + " Vince La Mano ");
	    		History.append("-------- FINE HAND -------" + newline);

	            //JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
	    		System.out.println(core.getWinner().getName() + " Winner ");
	    		System.out.println("Reset");
	    		core.reset();
	    		initGUI();
	    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
	    		initEHCard();
	    		initEHButton();
	    		if(core.P1.isMyTurn())
	    		{
	    			
	    			
	    			if(core.getCardChanged())
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
	    			else
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

	    		}
	    	}
	    	if(core.getPlayer(1).isMyTurn())
			{
				Nord.setBackground(Color.green);
				South.setBackground(Color.red);
			}
			else
			{
				Nord.setBackground(Color.red);
				South.setBackground(Color.green);
			}
		}
		else if(d.getMossa() == 2)
		{
			//Check/Call
	    	//System.out.println("click");
	    	if(core.getPlayer(1).isMyTurn())
	    	{
	    		if(core.getPot().getBet() > 0 && core.getPot().getBet() == d.getBet())
	        	{
	        		//check
	        		core.call(P1);
	    			History.append("CALL" + newline);

	    			System.out.println("AI CALL");

	        	}
	    		else if(core.getPot().getBet() == 0 && core.getPot().getBet() == d.getBet())
	    		{
	    			core.check(P1);
	    			History.append("CHECK" + newline);

	    			System.out.println("AI CHECK");

	    		}
	    		
	    		if(core.getTurn() == core.getTurn().DISCARDCARD)
	    		{
	    			core.setCardChanged(true);
	    			buttonP2.get(0).setText("Scarta");
	    			buttonP2.get(1).setEnabled(false);
	    			buttonP2.get(2).setEnabled(false);
	    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
	    			//JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
	    		}
	    		
	    		updateText();
	    	}
	    	if(core.getFineHand())
	    	{
	    		History.append(core.getWinner().getName() + " Vince La Mano ");
	    		History.append("-------- FINE HAND -------" + newline);

	            //JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
	    		System.out.println(core.getWinner().getName() + " Winner ");
	    		System.out.println("Reset");
	    		core.reset();
	    		initGUI();
	    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
	    		initEHCard();
	    		initEHButton();
	    		if(core.P1.isMyTurn())
	    		{
	    			
	    			
	    			if(core.getCardChanged())
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
	    			else
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

	    		}
	    	}
	    	if(core.getPlayer(1).isMyTurn())
			{
				Nord.setBackground(Color.green);
				South.setBackground(Color.red);
			}
			else
			{
				Nord.setBackground(Color.red);
				South.setBackground(Color.green);
			}
		}
		else if(d.getMossa() == 3)
		{
			if(core.getPlayer(1).isMyTurn())
	    	{
	    		System.out.println("AI RAISE");
				

	    		float bet = Float.parseFloat(betValue.getValue().toString());
	    		if(bet > 0)
	        	{
	    			
	    			if(bet < core.getPlayer(1).getStack().getStack())
	    			{
	    			
		        		//call
		        		if(!core.raise(P1, bet))
				            JOptionPane.showMessageDialog(null, "Il tuo stack è inferiore alla tua puntata", "Error", JOptionPane.INFORMATION_MESSAGE);
		        		else
		        		{
			    			buttonP2.get(0).setText("Call " + core.getPot().getBet());
			    			History.append("RAISE" + newline);
		        		}
	    			}
	    			else
	    			{
	    				core.call(P1);
	    				History.append("CALL / ALLIN" + newline);
	    			}
	        	}
	    		updateText();
	    		if(core.getFineHand())
		    	{
		            JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
		            History.append(core.getWinner().getName() + " Vince La Mano ");
		    		History.append("-------- FINE HAND -------" + newline);

	    			System.out.println(core.getWinner().getName() + " Winner ");
		    		System.out.println("Reset");
		    		core.reset();
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHCard();
		    		initEHButton();
		    		if(core.P1.isMyTurn())
		    		{
		    			
		    			if(core.getCardChanged())
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
		    			else
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

		    		}
		    	}
	    		if(core.getPlayer(1).isMyTurn())
    			{
    				Nord.setBackground(Color.green);
    				South.setBackground(Color.red);
    			}
    			else
    			{
    				Nord.setBackground(Color.red);
    				South.setBackground(Color.green);
    			}
	    	}
		}
		
	}
	
	public void drawPlayerCard(Player P1, Player P2)
	{
		//BACKUP TOGLIERE IF 
		if(core.getGameWinner() == null)
		{
			//System.out.println(core.getPlayer(2).getStack().getStack());
			cardP2 = new ArrayList<JLabel>();
			buttonP1 = new ArrayList<JButton>();
			buttonP2 = new ArrayList<JButton>();
			
			
			tk = Toolkit.getDefaultToolkit();
			
			if(DebugMode)
				Nord.add(History);
			
			JLabel obj;
			Image img;
			ImageIcon imgI;
			
			if(core.getPlayer(1).isDealer())
			{
				obj = new JLabel();
				obj.setName("Dealer");
				obj.setSize(50, 50);
	
				img = M.getDealer();
				img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				imgI = new ImageIcon(img);
				obj.setIcon(imgI);
				Nord.add(obj);
			}
			
			for(int k=0; k<core.getPlayer(1).getActualHand().getCards().size(); k++)
			{
				obj = new JLabel();
				obj.setName("Card"+k);
				obj.setSize(50, 60);
				//System.out.println(core.getPlayer(1).getActualHand().getCards().get(k).toString());
	
				//System.out.println(M.getCard(core.getPlayer(1).getActualHand().getCards().get(k).toString()));
				if(DebugMode)
					img = M.getCard(core.getPlayer(1).getActualHand().getCards().get(k).toString());
				else
					img = M.getBackCard(1);
				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				imgI = new ImageIcon(img);
				obj.setIcon(imgI);
				Nord.add(obj);
				
			}
			
			
			obj = new JLabel();
			obj.setName("FichesP1");
			obj.setSize(50, 100);
			img = null;
			if(core.getPlayer(1).getStack().getStack() > 0 && core.getPlayer(1).getStack().getStack() <= LOW)
			{
				img = M.getFiches("LOW");
			}
			else if(core.getPlayer(1).getStack().getStack() > LOW && core.getPlayer(1).getStack().getStack() <= MIDDLE)
			{
				img = M.getFiches("MIDDLE");
			}
			else if(core.getPlayer(1).getStack().getStack() > MIDDLE)
			{
				img = M.getFiches("HIGH");
				//System.out.println(img);
			}
			img = img.getScaledInstance(180, 100, Image.SCALE_SMOOTH);
			imgI = new ImageIcon(img);
			obj.setIcon(imgI);
			Nord.add(obj);
			
			obj = new JLabel();
			obj.setName("StackValueP1");
			obj.setText(String.valueOf(core.getPlayer(1).getStack().getStack()));
			Nord.add(obj);
			
			
			
			JButton CheckCallP1 = new JButton("Check");  
		    CheckCallP1.setName("CheckCallP1");
		    //CheckCall.setBounds(50,100,80,30);
		    //CheckCall.setPreferredSize(new Dimension(100, 50));
		   // CheckCall.setSize(new Dimension(100, 50));
		    //CheckCall.setMinimumSize(new Dimension(100, 50));
		    
		    JButton RaiseP1 = new JButton("Raise");
		    RaiseP1.setName("RaiseP1");
		    //Raise.setBounds(50,100,80,30);
		    //Raise.setPreferredSize(new Dimension(100, 50));
		    JButton FoldP1 = new JButton("Fold");
		    FoldP1.setName("FoldP1");
		    //Fold.setBounds(50,100,80,30);
		    //Fold.setPreferredSize(new Dimension(100, 50));
		    
		    buttonP1.add(CheckCallP1);
		    buttonP1.add(RaiseP1);
		    buttonP1.add(FoldP1);
		    
		    JPanel ButtonsP1 = new JPanel();
	        ButtonsP1.setName("ButtonsPanel");
	        // Set the BoxLayout to be X_AXIS: from left to right
	        BoxLayout boxlayout1 = new BoxLayout(ButtonsP1, BoxLayout.Y_AXIS);
	         
	        // Set the Boxayout to be Y_AXIS from top to down
	        //BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
	 
	        ButtonsP1.setLayout(boxlayout1);
		    
	        ButtonsP1.add(CheckCallP1);
	        ButtonsP1.add(RaiseP1);
	        ButtonsP1.add(FoldP1);
			
	        Nord.add(ButtonsP1);
			
	        if(DebugMode)
	        	Debug = new JButton("DEBUG ON");
	        else
	        	Debug = new JButton("DEBUG OFF");
	        
	        
	        South.add(Debug);
	        if(core.getPlayer(2).isDealer())
			{
				obj = new JLabel();
				obj.setName("Dealer");
				obj.setSize(50, 50);
	
				img = M.getDealer();
				img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				imgI = new ImageIcon(img);
				obj.setIcon(imgI);
				South.add(obj);
			}
	        
			for(int k=0; k<core.getPlayer(2).getActualHand().getCards().size(); k++)
			{
				obj = new JLabel();
				obj.setSize(50, 60);
				//System.out.println(core.getPlayer(2).getActualHand().getCards().get(k).toString());
	
				//System.out.println(M.getCard(core.getPlayer(1).getActualHand().getCards().get(k).toString()));
				img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(k).toString());
				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				imgI = new ImageIcon(img);
				obj.setIcon(imgI);
				obj.setName("Card" + k);
				South.add(obj);
				cardP2.add(obj);
			}
			obj = new JLabel();
			obj.setSize(50, 100);
			img = null;
			if(core.getPlayer(2).getStack().getStack() > 0 && core.getPlayer(2).getStack().getStack() <= LOW)
			{
				img = M.getFiches("LOW");
			}
			else if(core.getPlayer(2).getStack().getStack() > LOW && core.getPlayer(2).getStack().getStack() <= MIDDLE)
			{
				img = M.getFiches("MIDDLE");
			}
			else if(core.getPlayer(2).getStack().getStack() > MIDDLE)
			{
				img = M.getFiches("HIGH");
				//System.out.println(img);
			}
			img = img.getScaledInstance(180, 100, Image.SCALE_SMOOTH);
			imgI = new ImageIcon(img);
			obj.setIcon(imgI);
			obj.setName("FichesP2");
			South.add(obj);
			
			
			
			obj = new JLabel();
			obj.setName("StackValueP2");
			obj.setText(String.valueOf(core.getPlayer(2).getStack().getStack()));
			South.add(obj);
			
			SpinnerModel value =  
		             new SpinnerNumberModel(core.getSmallBlind(), //initial value  
		                core.getSmallBlind(), //minimum value  
		                core.getPlayer(2).getStack().getStack(), //maximum value  
		                core.getSmallBlind()); //step  
		    betValue = new JSpinner(value);  
		    betValue.setName("BetValue");
		    betValue.setBounds(100,100,50,30);    
		            
		    South.add(betValue);
			
		    JButton CheckCall;
		    
		    if(core.getPlayer(2).isDealer() && core.getPot().getBet() > 0)
		    {
		    	CheckCall = new JButton("Call " + core.getPot().getBet());  
		    	CheckCall.setName("CheckCall");
		    }
		    else
		    {
		    	CheckCall = new JButton("Check");  
		    	CheckCall.setName("CheckCall");	
		    }
		    JButton Raise = new JButton("Raise");
		    Raise.setName("Raise");
		    //Raise.setBounds(50,100,80,30);
		    //Raise.setPreferredSize(new Dimension(100, 50));
		    JButton Fold = new JButton("Fold");
		    Fold.setName("Fold");
		    //Fold.setBounds(50,100,80,30);
		    //Fold.setPreferredSize(new Dimension(100, 50));
		    
		    buttonP2.add(CheckCall);
		    buttonP2.add(Raise);
		    buttonP2.add(Fold);
		    
		    JPanel Buttons = new JPanel();
	        Buttons.setName("ButtonsPanel");
	        // Set the BoxLayout to be X_AXIS: from left to right
	        BoxLayout boxlayout = new BoxLayout(Buttons, BoxLayout.Y_AXIS);
	         
	        // Set the Boxayout to be Y_AXIS from top to down
	        //BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
	 
	        Buttons.setLayout(boxlayout);
		    
	        Buttons.add(CheckCall);
	        Buttons.add(Raise);
	        Buttons.add(Fold);
	        
		    South.add(Buttons);
		    
		    if(core.getPlayer(1).isMyTurn())
			{
				Nord.setBackground(Color.green);
				South.setBackground(Color.red);
			}
			else
			{
				Nord.setBackground(Color.red);
				South.setBackground(Color.green);
			}
		    
		    
			obj = new JLabel();
			obj.setName("PotImage");
			obj.setSize(50, 100);
			img = null;
			//System.out.println("start");
			if(core.getPot().getPot() > 0 && core.getPot().getPot() <= LOW)
			{
				img = M.getFiches("LOW");
				img = img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				//System.out.println("Low");
			}
			else if(core.getPot().getPot() > LOW && core.getPot().getPot() <= MIDDLE)
			{
				img = M.getFiches("MIDDLE");
				img = img.getScaledInstance(130, 100, Image.SCALE_SMOOTH);
				//System.out.println("Middle");
			}
			else if(core.getPot().getPot() > MIDDLE)
			{
				img = M.getFiches("HIGH");
				img = img.getScaledInstance(180, 100, Image.SCALE_SMOOTH);
				//System.out.println("High");
			}
			else if(core.getPot().getPot() == (core.getBigBlind() + core.getSmallBlind()))
			{
				img = M.getFiches("LOW");
				img = img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
			}
			
			//System.out.println(core.getPot().getPot());
			imgI = new ImageIcon(img);
			obj.setIcon(imgI);
			Center.add(obj);
			
			obj = new JLabel();
			obj.setName("Pot");
			obj.setText(String.valueOf(core.getPot().getPot()));
			Center.add(obj);
			
			//System.out.println(P1.getActualHand().getScore());
			//System.out.println(P1.getActualHand().highCard());
	
			/*
			if(P1.getActualHand().fullHouse() != null)
				System.out.println(P1.getActualHand().fullHouse().get(0) + " - " + P1.getActualHand().fullHouse().get(1));
			System.out.println(P2.getActualHand().getScore());
			if(P2.getActualHand().fullHouse() != null)
				System.out.println(P2.getActualHand().fullHouse().get(0) + " - " + P2.getActualHand().fullHouse().get(1));
			*/
			revalidate();
			repaint();
		}
		else
		{
			
			int res = JOptionPane.showConfirmDialog(null, "Il giocatore " + core.getGameWinner().getName() + " ha vinto la partita. Vuoi ricominciare?", "Partita Finita", JOptionPane.YES_NO_OPTION);
			System.out.println(res);
			if(res == 0)
			{
				StartGame();
			}
			else if(res == 1)
			{
				System.exit(0);

			}
		}
		
	}
	
	public void initEHCard()
	{
		cardP2.get(0).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	if(core.getTurn() == core.getTurn().DISCARDCARD)
		    	{
		    		if(selectedCard.indexOf(0) != -1)
		    		{
		    			selectedCard.remove(selectedCard.indexOf(0));
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card0"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(0).toString());
				    				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		else
		    		{
		    			selectedCard.add(0);
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card0"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(0).toString());
				    				img = img.getScaledInstance(130, 160, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		
		    	}

				revalidate();
				repaint();
		    	
		    }  
		});
		cardP2.get(1).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  	
		    	if(core.getTurn() == core.getTurn().DISCARDCARD)
		    	{
		    		if(selectedCard.indexOf(1) != -1)
		    		{
		    			selectedCard.remove(selectedCard.indexOf(1));
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card1"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(1).toString());
				    				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		else
		    		{
		    			selectedCard.add(1);
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card1"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(1).toString());
				    				img = img.getScaledInstance(130, 160, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		
		    	}
		    }  
		    
		}); 
		cardP2.get(2).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		   
		    	if(core.getTurn() == core.getTurn().DISCARDCARD)
		    	{
		    		if(selectedCard.indexOf(2) != -1)
		    		{
		    			selectedCard.remove(selectedCard.indexOf(2));
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card2"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(2).toString());
				    				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		else
		    		{
		    			selectedCard.add(2);
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card2"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(2).toString());
				    				img = img.getScaledInstance(130, 160, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		
		    	}
		    }  
		    
		}); 
		cardP2.get(3).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	if(core.getTurn() == core.getTurn().DISCARDCARD)
		    	{
		    		if(selectedCard.indexOf(3) != -1)
		    		{
		    			selectedCard.remove(selectedCard.indexOf(3));
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card3"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(3).toString());
				    				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		else
		    		{
		    			selectedCard.add(3);
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card3"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(3).toString());
				    				img = img.getScaledInstance(130, 160, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		
		    	}
		    }  
		}); 
		cardP2.get(4).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	if(core.getTurn() == core.getTurn().DISCARDCARD)
		    	{
		    		if(selectedCard.indexOf(4) != -1)
		    		{
		    			selectedCard.remove(selectedCard.indexOf(4));
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card4"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(4).toString());
				    				img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		else
		    		{
		    			selectedCard.add(4);
		    			for (Component jc : South.getComponents()) {
				            if (jc instanceof JLabel) {
				                JLabel label = (JLabel) jc;
				                //System.out.println(label.getName());
				                if(label.getName().equals("Card4"))
				                {
				                	Image img = M.getCard(core.getPlayer(2).getActualHand().getCards().get(4).toString());
				    				img = img.getScaledInstance(130, 160, Image.SCALE_SMOOTH);
				    				ImageIcon imgI = new ImageIcon(img);
				    				label.setIcon(imgI);
				    				//System.out.println(SelectedCard);
				                }
				            }
		    			}
		    		}
		    		
		    	}
		    }  
		}); 
	}
	public void initEHButton()
	{
		Debug.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	if(DebugMode)
		    	{
			    	DebugMode = false;
			    	Debug.setText("DEBUG OFF");
		    	}
		    	else
		    	{
		    		DebugMode = true;
			    	Debug.setText("DEBUG ON");
		    	}
		    	initGUI();
	    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
	    		initEHCard();
	    		initEHButton();
		    }  
		});
		buttonP1.get(0).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	
		    	//Check/Call
		    	System.out.println("click");
		    	if(core.getPlayer(1).isMyTurn())
		    	{
		    		System.out.println("my turn");
		    		if(core.getPot().getBet() > 0)
		        	{
		        		//call
		        		core.call(P1);
		        		System.out.println("call");
		        	
		        	}
		        	else
		        	{
		        		//check
		        		core.check(P1);
		        	}
		    		if(core.getTurn() == core.getTurn().DISCARDCARD)
		    		{
		    			buttonP2.get(0).setText("Scarta");
		    			buttonP2.get(1).setEnabled(false);
		    			buttonP2.get(2).setEnabled(false);
		    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
		    			//JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
		    		}
		    		updateText();
		    	}
		    	if(core.getFineHand())
		    	{
		    		History.append(core.getWinner().getName() + " Vince La Mano ");
		            JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
		    		System.out.println(core.getWinner().getName() + " Winner ");
		    		System.out.println("Reset");
		    		core.reset();
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHCard();
		    		initEHButton();
		    	}
		    }  
		}); 
		buttonP2.get(0).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	//SelectedCard = 1;
		    	//Check/Call
		    	//System.out.println("click");
		    	if(buttonP2.get(0).getText().equals("Scarta"))
		    	{
		    		Collections.sort(selectedCard); 
		    		for(int k=0; k<selectedCard.size(); k++)
		    		{
		    			//System.out.println(selectedCard.get(k));
		    			//System.out.println(k);
		    			
		    			//System.out.println(selectedCard.get(k)-k);

		    			core.getPlayer(2).getActualHand().getCards().remove((int)selectedCard.get(k)-k);
		    		}
		    		drawCard(core.getPlayer(2), selectedCard.size());
		    		selectedCard.clear();
		    		
		    		//AI SCARTA LE SUE CARTE
		    		ArrayList<Card> cardToDiscard = core.getAIcore().getCardToDiscard(core.getPlayer(1).getActualHand());
		    		for(int k=0; k<cardToDiscard.size(); k++)
		    		{
		    			core.getPlayer(1).getActualHand().getCards().remove(cardToDiscard.get(k));
		    		}
		    		drawCard(core.getPlayer(1), cardToDiscard.size());

		    		
		    		core.nextTurn();
		    		
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHButton();
		    		
		    		if(core.isAllIn())
		    		{
		    			core.nextTurn();
		    			if(core.getFineHand())
				    	{
				            JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
				            History.append(core.getWinner().getName() + " Vince La Mano ");
			    			System.out.println(core.getWinner().getName() + " Winner ");
				    		System.out.println("Reset");
				    		core.reset();
				    		initGUI();
				    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
				    		initEHCard();
				    		initEHButton();
				    	}
		    		}
		    		if(core.P1.isMyTurn() && core.getTurn() != core.getTurn().DISCARDCARD )
		    		{
		    			if(core.getPlayer(1).isMyTurn())
		    			{
		    				Nord.setBackground(Color.green);
		    				South.setBackground(Color.red);
		    			}
		    			else
		    			{
		    				Nord.setBackground(Color.red);
		    				South.setBackground(Color.green);
		    			}
		    			//Decision d = core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0));
		    			if(core.getCardChanged())
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
		    			else
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

		    		}
		    	}
		    	else
		    	{
			    	if(core.getPlayer(2).isMyTurn())
			    	{
			    		System.out.println("my turn");
			    		if(core.getPot().getBet() > 0)
			        	{
			        		//call
			        		core.call(P2);
			    			buttonP2.get(0).setText("Check");
			    			System.out.println(core.getTurn());
			        	}
			        	else
			        	{
			        		//check
			        		core.check(P2);
			        	}
			    		if(core.getTurn() == core.getTurn().DISCARDCARD)
			    		{
			    			core.setCardChanged(true);
			    			buttonP2.get(0).setText("Scarta");
			    			buttonP2.get(1).setEnabled(false);
			    			buttonP2.get(2).setEnabled(false);
			    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		updateText();
			    		
			    		
			    		if(core.getFineHand())
				    	{
				            JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
				            History.append(core.getWinner().getName() + " Vince La Mano ");
			    			//System.out.println(core.getWinner().getName() + " Winner ");
				    		System.out.println("Reset");
				    		core.reset();
				    		initGUI();
				    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
				    		initEHCard();
				    		initEHButton();
				    	}
			    		
			    		if(core.P1.isMyTurn() && core.getTurn() != core.getTurn().DISCARDCARD && !core.getFineHand())
			    		{
			    			if(core.getPlayer(1).isMyTurn())
			    			{
			    				Nord.setBackground(Color.green);
			    				South.setBackground(Color.red);
			    			}
			    			else
			    			{
			    				Nord.setBackground(Color.red);
			    				South.setBackground(Color.green);
			    			}
			    			//Decision d = core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0));
			    			if(core.getCardChanged())
			    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
			    			else
			    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

			    		}
			    	}
		    	}
		    }  
		}); 
		buttonP2.get(1).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	//SelectedCard = 1;
		    	//Raise
		    	System.out.println("raise");
		    	if(core.getPlayer(2).isMyTurn())
		    	{
		    		System.out.println("my turn");
		    		float bet = Float.parseFloat(betValue.getValue().toString());
		    		if(bet > 0)
		        	{
		        		//call
		        		if(!core.raise(P2, bet))
				            JOptionPane.showMessageDialog(null, "Il tuo stack è inferiore alla tua puntata", "Error", JOptionPane.INFORMATION_MESSAGE);

		        	
		        	}
		    		updateText();
		    		if(core.getFineHand())
			    	{
			            JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
			            History.append(core.getWinner().getName() + " Vince La Mano ");
		    			System.out.println(core.getWinner().getName() + " Winner ");
			    		System.out.println("Reset");
			    		core.reset();
			    		initGUI();
			    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
			    		initEHCard();
			    		initEHButton();
			    	}
		    		if(core.P1.isMyTurn() && !core.getFineHand())
		    		{
		    			if(core.getPlayer(1).isMyTurn())
		    			{
		    				Nord.setBackground(Color.green);
		    				South.setBackground(Color.red);
		    			}
		    			else
		    			{
		    				Nord.setBackground(Color.red);
		    				South.setBackground(Color.green);
		    			}
		    			//Decision d = core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0));
		    			if(core.getCardChanged())
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
		    			else
		    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

		    		}
		    	}

		        
		    }  
		}); 
		buttonP2.get(2).addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  		       
		    	//SelectedCard = 1;
		    	//Fold
				System.out.println("FOLD PLAYER");

		    	core.fold(P2);
		    	if(core.getFineHand())
		    	{
		            //JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
		    		History.append(core.getWinner().getName() + " Vince La Mano ");
	    			System.out.println("AI Winner ");
		    		System.out.println("Reset");
		    		core.reset();
		    		updateText();
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHCard();
		    		initEHButton();
		    	}
		    	//System.out.println("FOLD");
		    	if(core.P1.isMyTurn())
	    		{
		    		if(core.getPlayer(1).isMyTurn())
	    			{
	    				Nord.setBackground(Color.green);
	    				South.setBackground(Color.red);
	    			}
	    			else
	    			{
	    				Nord.setBackground(Color.red);
	    				South.setBackground(Color.green);
	    			}
	    			//Decision d = core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0));
	    			if(core.getCardChanged())
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(1)));
	    			else
	    				DoMovesAI(core.getAIcore().getFinalDecision(core.P1.getActualHand(), core.P1.getStack(), core.getPot(), new cardChanged(0)));

	    		}
		        
		    }  
		}); 
	}
	
	public void removeCard(Player P, Card C)
	{
		if(P == core.getPlayer(1))
		{
			core.getPlayer(1).getActualHand().getCards().remove(C);
		}
		else if(P == core.getPlayer(2))
		{
			core.getPlayer(2).getActualHand().getCards().remove(C);
		}
	}
	public void drawCard(Player P, int nCard)
	{
		if(P == core.getPlayer(1))
		{
			for(int k=0; k<nCard; k++)
			{
				core.getPlayer(1).getActualHand().getCards().add(core.drawCard());
			}
		}
		else if(P == core.getPlayer(2))
		{
			for(int k=0; k<nCard; k++)
			{
				core.getPlayer(2).getActualHand().getCards().add(core.drawCard());
			}
		}
	}
	
	public void updateText()
	{
		for (Component jc : Nord.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                //System.out.println(label.getName());
                if(label.getName().equals("StackValueP1"))
                {
                	
    				label.setText(String.valueOf(core.getPlayer(1).getStack().getStack()));
                }
            }
		}
		for (Component jc : Center.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                //System.out.println(label.getName());
                if(label.getName().equals("Pot"))
                {
                	label.setText(String.valueOf(core.getPot().getPot() + core.getPot().getBet()));
    				
                }
            }
		}
		for (Component jc : South.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                //System.out.println(label.getName());
                if(label.getName().equals("StackValueP2"))
                {
                	label.setText(String.valueOf(core.getPlayer(2).getStack().getStack()));
    				
                }
            }
		}
	}
	
	public void initGUI()
	{

		getContentPane().removeAll();
		
		getContentPane().setBackground(Color.red);
		Nord = new JPanel();
		Center = new JPanel();
		South = new JPanel();
		
		Nord.setBackground(Color.red);
		Center.setBackground(Color.red);
		South.setBackground(Color.red);
		
		Nord.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));
		Center.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, (900-260-PADDING*2-25)/2));
		South.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));
		
		
		getContentPane().add(Nord, BorderLayout.NORTH);
		getContentPane().add(Center,BorderLayout.CENTER);
		getContentPane().add(South,BorderLayout.SOUTH);
		
		
		setSize(1500, 900);
		setVisible(true);
	}
	
	
	
}
