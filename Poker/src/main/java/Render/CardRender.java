package Render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CardRender extends JPanel {

	
	Toolkit tk;
	Image I;
    
	public CardRender() {
		tk = Toolkit.getDefaultToolkit();
		try 
		{  
			I = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Poker\\src\\main\\resources\\Image\\Card\\Front\\10C.png");
			//image = ImageIO.read(new File("C:\\Users\\spara\\eclipse-workspace\\Poker\\src\\main\\resources\\Image\\Card\\Front\\10C.png"));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
            // handle exception...
		}
		
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(I, 100, 0, 100, 100, this); // see javadoc for more info on the parameters            
    }

	
}
