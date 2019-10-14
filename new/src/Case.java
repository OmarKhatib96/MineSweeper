
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;


class Case extends JPanel implements MouseListener{
	private String txt = "X";
	/**
	 * Dimension de la case
	 */
	private final static int DIM=5 ;
	/**Coordonée x de la case
	 * 
	 */
	private int x;
	/**
	 * Coordonnée y de la cases
	 */
	private int y;
	private Demineur demin;
	private boolean clicked=false;
	
	/**
	 * Couleur de bord de la case
	 */
	private Color couleurBord=new Color(60,60,100);

	/**
	 * Constructeur par défaut de la case
	 * @param x coordonnée x
	 * @param y coordonnée y
	 * @param Demin
	 */
	public Case (int x, int y,Demineur Demin) {//passer position
		this.x=x;
		this.y=y;
		this.demin=Demin;//Recopie dans le constructeur
		this.setBorder(BorderFactory.createLineBorder(couleurBord));
		setPreferredSize(new Dimension(DIM, DIM)); // taille de la case
		addMouseListener(this); // ajout listener souris
	}
	
	
	/** le dessin de la case */
	//@override 

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // appel m�thode m�re (efface le dessin pr�cedent)
		if(!clicked) {
			BufferedImage image;
			try {

				image=ImageIO.read(new File("new/img/overCase.img"));
				gc.drawImage(image, 1, 1, this.getWidth(), this.getHeight(), this);

			}catch (IOException e) {
				System.out.println("There is no such image file");
				e.printStackTrace();
			}
			
		}else {
			
			
			if(demin.getChamp().Ismin(x, y)) {
				BufferedImage image;
				try {

					image=ImageIO.read(new File("new/img/mine.png"));
					gc.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

				}catch (IOException e) {
					System.out.println("There is no such image file");
					e.printStackTrace();
				}
			}else
			{
				
				if(demin.getChamp().nbr_mines(x, y)==0) {
					System.out.println(x+","+y+" pas de mines autour");
					


				}else {
					BufferedImage image;
					try {

						image=ImageIO.read(new File("new/img/"+String.valueOf(demin.getChamp().nbr_mines(x, y))+".img"));
						gc.drawImage(image, 1, 1, this.getWidth(), this.getHeight(), this);

					}catch (IOException e) {
						System.out.println("There is no such image file");
						e.printStackTrace();
					}
					
					

				}
			}
		}
	}
	
	/** la gestion de la souris */

	

	public  void setClickedCase() {
		
		clicked=true;
		
	}
	public void mousePressed (MouseEvent e) {
		txt = "0"; // chgt du texte � redessiner
		repaint() ; // comme on veut redessiner, on force l�appel de paintComponent()
	}

	@Override
	/**
	 * Si on appuie sur le bouton de la souris (sur une case)
	 */
	public void mouseClicked(MouseEvent e) {
		
	
		
		if(!clicked && !demin.getLost()) {
		demin.set_nbr_cases_decouvertes(demin.Get_nbr_cases_decouvertes()+1);//incrementation du nombre de cases d�couverte
		clicked=true;
		if(demin.getConnected() && demin.getChampRecu())
		try {
			demin.getDos().writeInt(1);
			demin.getDos().writeInt(x);
			demin.getDos().writeInt(y);
			demin.getDos().writeUTF(demin.getPseudo());
			demin.getDos().writeInt(demin.getColorInt());
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//repaint();
		}
		
		
		if(!demin.getLost()) {//partie pas perdue
			//d�marrage partie 
					
				if(!demin.isStarted()) {
					demin.getGui().getCompteur().startCpt();//On démarre le compteur
					demin.setStarted(true);
					demin.setLost(false);
				}
				
		if(demin.getChamp().Ismin(x, y)) {
			if(demin.getConnected() && demin.getChampRecu()){

			DataOutputStream out= new DataOutputStream (demin.getDos());
				try {
					out.writeInt(10);
					out.writeBoolean(true);
					out.writeUTF(demin.getPseudo());
					out.writeInt(this.demin.getGui().getCompteur().GetCounter());
					out.writeInt(this.demin.Get_nbr_cases_decouvertes());
					out.writeInt(this.demin.getColorInt());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 demin.addScorehmOnline(this.demin.getChamp().getNiveau(),this.demin.Get_nbr_cases_decouvertes());
				 System.out.println(this.demin.getChamp().getNiveau().name());
				 demin.saveScore();

			
				 }
			
			JOptionPane.showMessageDialog(null, "You lost! Your score is "+String.valueOf(demin.Get_nbr_cases_decouvertes())+" boxes found with Time: "+String.valueOf(demin.getGui().getCompteur().GetCounter())+" seconds");

			demin.setLost(true);
			if(!demin.getConnected()){
				demin.addScorehmOffline(this.demin.getChamp().getNiveau(),this.demin.Get_nbr_cases_decouvertes());
				demin.saveScore();

			}
			
			demin.getGui().getCompteur().stopCpt();

		}else {
			
			
		}

		repaint();
	}
		if(demin.isWin()) {
			JOptionPane.showMessageDialog(null, "You win! your score is "+String.valueOf(demin.Get_nbr_cases_decouvertes())+" Time: "+String.valueOf(demin.getGui().getCompteur().GetCounter())+" seconds");
			
		}
}

	@Override
	public void mouseEntered(MouseEvent e) {
		

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
