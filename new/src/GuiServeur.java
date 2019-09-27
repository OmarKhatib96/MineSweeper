
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.net.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;



public class GuiServeur extends JPanel{
	private JButton startBut=new JButton("Start Partie");
	private Serveur serveur;
	private JTextArea msgAreas=new JTextArea(20,20);
	
	public Serveur getServeur()
	{
		return this.serveur;
	}
	
	
	GuiServeur(Serveur serveur){
		this.serveur=serveur;
		setLayout(new BorderLayout());
		add(new JLabel("Serveur Démineur 2019"),BorderLayout.NORTH);
		add(msgAreas,BorderLayout.CENTER);
		add(startBut,BorderLayout.SOUTH);

		
	}
	
	/**
	 * * attente des clients
	 */
	public void addMsg(String str) {
		msgAreas.append(str);	
	}
	



	
}
