package Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import Render.CardRender;

public class Menu extends JFrame {

	private static final int PADDING = 5;
	
	Toolkit tk;
	
	public Menu()
	{
		super("Menu");
		tk = Toolkit.getDefaultToolkit();
		
		JPanel Nord = new JPanel();
		JPanel Center = new JPanel();
		JPanel South = new JPanel();
		
		Nord.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, 0));
		Center.setLayout(new BorderLayout());
		South.setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, 0));
		
		
		Image i = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Poker\\src\\main\\resources\\Image\\Card\\Front\\10C.png");
		Image i2 = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Poker\\src\\main\\resources\\Image\\Card\\Front\\10C.png");
		i = i.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
		i2 = i2.getScaledInstance(100, 130, Image.SCALE_SMOOTH);
		ImageIcon ii = new ImageIcon(i);
		ImageIcon ii2 = new ImageIcon(i2);
		
		JLabel l = new JLabel();
		l.setSize(50, 60);
		l.setIcon(ii);
		
		JLabel l2 = new JLabel();
		l2.setSize(50, 60);
		l2.setIcon(ii2);
		
	    //JLayeredPane pane = new JLayeredPane();  
	    //pane.add(l, new Integer(1));
	    //pane.add(l2, new Integer(2));
		
		Nord.add(l);
		Nord.add(l2);
		
		/*
		JPanel j = new CardRender();
		j.setSize(100,100);
		Nord.add(j);
		*/
		Nord.add(new JLabel("AI Fiches"));
		South.add(new JLabel("PlayerCard"));
		South.add(new JLabel("Player Fiches"));

		
		getContentPane().add(Nord, BorderLayout.NORTH);
		getContentPane().add(Center,BorderLayout.CENTER);
		getContentPane().add(South,BorderLayout.SOUTH);
		
		setSize(1500, 900);
		setVisible(true);
	}
	
}
