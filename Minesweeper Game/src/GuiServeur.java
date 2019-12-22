
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
import java.awt.*;  



public class GuiServeur extends JPanel implements ActionListener {
	private JButton startBut=new JButton("Start Partie");
	private Serveur serveur;
	private JTextArea msgAreas=new JTextArea(20,20);
	private Choice c; 

	
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
		 c=new Choice();  
		c.setBounds(100,100, 75,75);  
        c.add("EASY");  
        c.add("MEDIUM");  
        c.add("HARD");  
    
		add(c,BorderLayout.NORTH);

		
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
			String choice=c.getItem(c.getSelectedIndex());//Récupérer le niveau choisi
			if(choice.equals("EASY"))
				serveur.setChamp(new Champ("Mineur game", new Level(lvl.EASY)));

			if(choice.equals("MEDIUM"))
				serveur.setChamp(new Champ("Mineur game", new Level(lvl.MEDIUM)));


			if(choice.equals("HARD"))
				serveur.setChamp(new Champ("Mineur game", new Level(lvl.HARD)));


			int sizeX=serveur.getChamp().getDimensionX();
			System.out.println("(serv x="+sizeX);
			int sizeY=serveur.getChamp().getDimensionY();
			System.out.println("serv y="+sizeY);
			serveur.resetCaseDiscovered();

			for(int clientOut=0;clientOut<this.serveur.getListOut().size();clientOut++) {//Diffuser aux autre clients

				try {
					this.serveur.getListOut().get(clientOut).writeInt(2);//Commande start
					serveur.getListOut().get(clientOut).writeInt(sizeX);
					serveur.getListOut().get(clientOut).writeInt(sizeY);
					serveur.getListOut().get(clientOut).writeUTF(serveur.getChamp().getNiveau().name());
					System.out.println("From GuiServeur "+serveur.getChamp().getNiveau().name());
					for(int i=0;i<sizeX;i++)
						for(int j=0;j<sizeY;j++)
							serveur.getListOut().get(clientOut).writeBoolean(serveur.getChamp().getTab()[i][j]);


				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//Commande start
			}
					
		
		}
				

	}
	
}
