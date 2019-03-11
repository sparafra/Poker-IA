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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Object.Card;
import Object.Player;
import Object.Turn;

public class Game extends JFrame{

private static final int PADDING = 5;
	
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
	}
	
	public void drawPlayerCard(Player P1, Player P2)
	{
		cardP2 = new ArrayList<JLabel>();
		buttonP1 = new ArrayList<JButton>();
		buttonP2 = new ArrayList<JButton>();
		
		
		tk = Toolkit.getDefaultToolkit();
		
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
			System.out.println(core.getPlayer(1).getActualHand().getCards().get(k).toString());

			//System.out.println(M.getCard(core.getPlayer(1).getActualHand().getCards().get(k).toString()));
			img = M.getCard(core.getPlayer(1).getActualHand().getCards().get(k).toString());
			img = img.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
			imgI = new ImageIcon(img);
			obj.setIcon(imgI);
			Nord.add(obj);
			
		}
		
		
		obj = new JLabel();
		obj.setName("FichesP1");
		obj.setSize(50, 100);
		img = null;
		if(core.getPlayer(1).getStack().getStack() > LOW && core.getPlayer(1).getStack().getStack() < MIDDLE)
		{
			img = M.getFiches("LOW");
		}
		else if(core.getPlayer(1).getStack().getStack() > MIDDLE && core.getPlayer(1).getStack().getStack() < HIGH)
		{
			img = M.getFiches("MIDDLE");
		}
		else if(core.getPlayer(1).getStack().getStack() >= HIGH)
		{
			img = M.getFiches("HIGH");
			System.out.println(img);
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
			System.out.println(core.getPlayer(2).getActualHand().getCards().get(k).toString());

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
		if(core.getPlayer(2).getStack().getStack() > LOW && core.getPlayer(2).getStack().getStack() < MIDDLE)
		{
			img = M.getFiches("LOW");
		}
		else if(core.getPlayer(2).getStack().getStack() > MIDDLE && core.getPlayer(2).getStack().getStack() < HIGH)
		{
			img = M.getFiches("MIDDLE");
		}
		else if(core.getPlayer(2).getStack().getStack() >= HIGH)
		{
			img = M.getFiches("HIGH");
			System.out.println(img);
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
		
	    JButton CheckCall = new JButton("Check");  
	    CheckCall.setName("CheckCall");
	    //CheckCall.setBounds(50,100,80,30);
	    //CheckCall.setPreferredSize(new Dimension(100, 50));
	   // CheckCall.setSize(new Dimension(100, 50));
	    //CheckCall.setMinimumSize(new Dimension(100, 50));
	    
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
	    
	    
	    
		obj = new JLabel();
		obj.setName("PotImage");
		obj.setSize(50, 100);
		img = null;
		System.out.println("start");
		if(core.getPot().getPot() > 0 && core.getPot().getPot() <= LOW)
		{
			img = M.getFiches("LOW");
			img = img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
			System.out.println("Low");
		}
		else if(core.getPot().getPot() >= LOW && core.getPot().getPot() <= MIDDLE)
		{
			img = M.getFiches("MIDDLE");
			img = img.getScaledInstance(130, 100, Image.SCALE_SMOOTH);
			System.out.println("Middle");
		}
		else if(core.getPot().getPot() >= MIDDLE)
		{
			img = M.getFiches("HIGH");
			img = img.getScaledInstance(180, 100, Image.SCALE_SMOOTH);
			System.out.println("High");
		}
		else if(core.getPot().getPot() == (core.getBigBlind() + core.getSmallBlind()))
		{
			img = M.getFiches("LOW");
			img = img.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
		}
		
		System.out.println(core.getPot().getPot());
		imgI = new ImageIcon(img);
		obj.setIcon(imgI);
		Center.add(obj);
		
		obj = new JLabel();
		obj.setName("Pot");
		obj.setText(String.valueOf(core.getPot().getPot()));
		Center.add(obj);
		
		System.out.println(P1.getActualHand().getScore());
		System.out.println(P1.getActualHand().highCard());

		if(P1.getActualHand().fullHouse() != null)
			System.out.println(P1.getActualHand().fullHouse().get(0) + " - " + P1.getActualHand().fullHouse().get(1));
		System.out.println(P2.getActualHand().getScore());
		if(P2.getActualHand().fullHouse() != null)
			System.out.println(P2.getActualHand().fullHouse().get(0) + " - " + P2.getActualHand().fullHouse().get(1));
		
		revalidate();
		repaint();
		
		
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
				                System.out.println(label.getName());
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
		    	System.out.println("click");
		    	if(buttonP2.get(0).getText().equals("Scarta"))
		    	{
		    		
		    		for(int k=0; k<selectedCard.size(); k++)
		    		{
		    			System.out.println(selectedCard.get(k)-k);

		    			core.getPlayer(2).getActualHand().getCards().remove((int)selectedCard.get(k)-k);
		    		}
		    		drawCard(core.getPlayer(2), selectedCard.size());
		    		
		    		//AI SCARTA LE SUE CARTE
		    		
		    		core.nextTurn();
		    		
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHButton();
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
			        	
			        	}
			        	else
			        	{
			        		//check
			        		core.check(P2);
			        	}
			    		if(core.getTurn() == core.getTurn().DISCARDCARD)
			    		{
			    			buttonP2.get(0).setText("Scarta");
			    			buttonP2.get(1).setEnabled(false);
			    			buttonP2.get(2).setEnabled(false);
			    			JOptionPane.showMessageDialog(null, "Seleziona le carte da scartare", "Scarta le carte ", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		updateText();
			    		
			    		if(core.getFineHand())
				    	{
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
			           
		    			System.out.println(core.getWinner().getName() + " Winner ");
			    		System.out.println("Reset");
			    		core.reset();
			    		initGUI();
			    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
			    		initEHCard();
			    		initEHButton();
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
		    	core.fold(P2);
		    	if(core.getFineHand())
		    	{
		            //JOptionPane.showMessageDialog(null, core.getWinner().getName(), "Winner ", JOptionPane.INFORMATION_MESSAGE);
		           
	    			System.out.println(core.getWinner().getName() + " Winner ");
		    		System.out.println("Reset");
		    		core.reset();
		    		updateText();
		    		initGUI();
		    		drawPlayerCard(core.getPlayer(1), core.getPlayer(2));
		    		initEHCard();
		    		initEHButton();
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
                System.out.println(label.getName());
                if(label.getName().equals("StackValueP1"))
                {
                	
    				label.setText(String.valueOf(core.getPlayer(1).getStack().getStack()));
                }
            }
		}
		for (Component jc : Center.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                System.out.println(label.getName());
                if(label.getName().equals("Pot"))
                {
                	label.setText(String.valueOf(core.getPot().getPot() + core.getPot().getBet()));
    				
                }
            }
		}
		for (Component jc : South.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                System.out.println(label.getName());
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
