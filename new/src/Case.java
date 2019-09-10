import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;


class Case extends JPanel implements MouseListener{
	private String txt = "X";
	private final static int DIM=30 ;
	private int x;
	private int y;
	private Demineur demin;
	private boolean clicked;



	public Case (int x, int y,Demineur Demin) {//passer position
		this.x=x;
		this.y=y;
		this.demin=Demin;//Recopie dans le constructeur
		setPreferredSize(new Dimension(DIM, DIM)); // taille de la case
		addMouseListener(this); // ajout listener souris
	}


	/** le dessin de la case */
	//@override 

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
		//gc.drawRect(1,1,2,2);
		if(!clicked) {

			gc.setColor(new Color(158,158,158)); // grey 
			gc.fillRect(1, 1, getWidth(), getHeight());
		}else {
			if(demin.getChamp().Ismin(x, y)) {
				BufferedImage image;
				try {

					image=ImageIO.read(new File("img/mine.png"));
					gc.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

					//(this.getClass().getRessource())*

				}catch (IOException e) {

					e.printStackTrace();
				}
			}else
			{
				if(demin.getChamp().nbr_mines(x, y)==0) {

					gc.setColor(new Color(9,125,139));
					gc.fillRect(0, 0, getWidth(), getHeight());


				}else {
					gc.setColor(new Color(0,125,139));
					gc.fillRect(0, 0, getWidth(), getHeight());
					gc.setColor(new Color(0,0,0));
					gc.drawString(String.valueOf(demin.getChamp().nbr_mines(x,y)),getWidth()/2,getHeight()/2);

				}
			}
		}
		//gc.drawString(txt, 10,10); // dessin du texte à la position 10, 10
	}
	/** la gestion de la souris */

	public void mousePressed (MouseEvent e) {
		txt = "0"; // chgt du texte à redessiner
		repaint() ; // comme on veut redessiner, on force l’appel de paintComponent()
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked=true;
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/*if(demin.getChamp().Ismin(x, y))
		setToolTipText("Mine!");
	else
		setToolTipText("No!");
		 */

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void newPartie() {
		
		clicked=false;
		repaint();
	}
} 
//1-Dessiner case gris comme ça on remplace Jlabel par cette case grise
//2-La souris 
//3-Quand on clique ça donner une bombe