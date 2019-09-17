
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
	private static int WIDTH=100;
	private static int HEIGHT=80;
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
					repaint();//on est dans le composant lui-même
					processScores.sleep(1000);
					//affScores();
					counter++;
					
				}catch(InterruptedException e) {e.printStackTrace();}
		
		
	}
	
	public void startCpt(){
		//counter=0;
		processScores=new Thread(this);
		processScores.start();
		
		
		
		
	}
	
	
	public void stopCpt(){
		processScores=null;

		counter=0;
		
		
		
	}
	
	Compteur(){
		processScores=new Thread(this);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);//ça efface correctment le composant
		gc.setColor(new Color(255,87,34));//		
		super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
		gc.drawString(String.valueOf(counter),getWidth()/2,getHeight()/2);
		gc.setColor(new Color(0,150,136));
		gc.drawRect(0,0,getWidth()-1,getHeight()-1);

}

}