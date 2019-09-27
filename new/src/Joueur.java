import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;




public class Joueur extends JPanel  {

	private String Pseudo;
	 
	//public static final int PORT=10000;
	private String NomMachine;
	

	Joueur(String Pseudo){
		
		this.Pseudo=Pseudo;
	}
	
	public String getPseudo() {
		return Pseudo;
	}
		
}
