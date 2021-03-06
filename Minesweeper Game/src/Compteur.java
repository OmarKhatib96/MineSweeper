
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Compteur extends JPanel implements Runnable {
	private Thread processScores;
	private int counter=0;
	private static int WIDTH=50;
	private static int HEIGHT=50;
	Compteur(int nbJoueur){
		processScores=new Thread(this);
		processScores.start();
		
	}
	
	
	
	public int GetCounter() {
		
		return counter;
	}
	public void run() {
		
		while(processScores!=null)
				try {
					repaint();//on est dans le composant lui-m�me
					processScores.sleep(1000);
					//affScores();
					counter++;
					
				}catch(InterruptedException e) {e.printStackTrace();}
		
		
	}
	
	/**
	 * Permet de lancer le compteur
	 */
	public void startCpt(){
		//counter=0;
		processScores=new Thread(this);
		processScores.start();
		
		
		
		
	}
	
	/**
	 * Permet de stopper le compteur
	 */
	public void stopCpt(){
		processScores=null;

		counter=0;
		
		
		
	}
	
	/**
	 * Constructeur par d�faut du compteur
	 */
	Compteur(){
		processScores=new Thread(this);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);//�a efface correctment le composant
		gc.setColor(new Color(0,0,0));//		
		super.paintComponent(gc); // appel m�thode m�re (efface le dessin pr�cedent)
		gc.drawString(String.valueOf(counter),getWidth()/2,getHeight()/2);
		gc.setColor(new Color(0,0,0));
		gc.drawRect(0,0,getWidth()-1,getHeight()-1);


}

}