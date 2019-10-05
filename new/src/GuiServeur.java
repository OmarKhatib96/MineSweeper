
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.net.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class GuiServeur extends JPanel implements ActionListener {
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
		startBut.addActionListener(this);

		
	}
	
	/**
	 * * attente des clients
	 */
	public void addMsg(String str) {
		msgAreas.append(str);	
	}
	

	public void sendStart() {
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		 //Level lev=Level();
		if(e.getSource()==startBut ) 
		{
			for(int clientOut=0;clientOut<this.serveur.getListOut().size();clientOut++) {//Diffuser aux autre clients

				try {
					this.serveur.getListOut().get(clientOut).writeInt(2);//Commande start
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//Commande start
			}
					
		
		}
				

	}
	
}
