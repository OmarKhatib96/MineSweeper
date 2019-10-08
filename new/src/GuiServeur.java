
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
		add(new JLabel("Serveur D�mineur 2019"),BorderLayout.NORTH);
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
		
		if(e.getSource()==startBut ) 
		{
			int sizeX=serveur.getChamp().getDimensionX();
			System.out.println("(serv x="+sizeX);
			int sizeY=serveur.getChamp().getDimensionY();
			System.out.println("serv y="+sizeY);

			for(int clientOut=0;clientOut<this.serveur.getListOut().size();clientOut++) {//Diffuser aux autre clients

				try {
					this.serveur.getListOut().get(clientOut).writeInt(2);//Commande start
					serveur.getListOut().get(clientOut).writeInt(sizeX);
					serveur.getListOut().get(clientOut).writeInt(sizeY);
					
					for(int i=0;i<sizeX;i++)
						for(int j=0;j<sizeY;j++)
							serveur.getListOut().get(clientOut).writeBoolean(serveur.getChamp().getTab()[i][j]);
			//Serveur.getChamp().getTab


				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//Commande start
			}
					
		
		}
				

	}
	
}
