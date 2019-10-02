import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class IHMHello extends JPanel implements ActionListener {
	
	private JButton butQuit =new JButton("Quit") ;//Les mettre en attributs pour qu'ils soient accessible dans la classe
	private JButton sendButton = new JButton("Send");

	private JMenuItem mQuit=new JMenuItem("Quitter",KeyEvent.VK_Q);//
	private JMenuItem mNew=new JMenuItem("Nouvelle Partie",KeyEvent.VK_Q);//
	private Demineur Demin;
	private JMenuItem mEasy=new JMenuItem("Easy",KeyEvent.VK_E);
	private JMenuItem mMedium=new JMenuItem("Medium",KeyEvent.VK_E);
	private JMenuItem mHard=new JMenuItem("Hard",KeyEvent.VK_E);
	private JPanel panelMines=new JPanel();
	private boolean Started=false;
	private Compteur compteur;
	private JPanel panelSouth;
	private JPanel panelConnexion=new JPanel();
	private JTextField hostField=new JTextField(Demineur.HOSTNAME,20);
	private JTextField portField=new JTextField(String.valueOf(Demineur.PORT),6);
	private JTextField pseudoField=new JTextField(Demineur.PSEUDO,15);
	private JTextArea msgArea;
	private JTextField inputTextField=new JTextField();
	//msgArea=new  JTextArea(10,20);

	private JButton connexionBut=new JButton("Connect");
	
	private  Case [][] tabCases;
/** constructeuraddActionListener
* @param la classe contenant les traitements
*/
 public IHMHello(Demineur Demin) {
		
setBackground(Color.black);

//ImageIcon quitIcon = new ImageIcon("sortieCLR.gif"); // image
JLabel title = new JLabel("Welcome on board");

setLayout(new BorderLayout());
title.setHorizontalTextPosition(JLabel.CENTER);
//Case case_demineur=new Case();

//case_demineur.paintComponent(gc);
this.Demin=Demin;
placeCases();
			
		
JPanel panelnorth=new JPanel(new BorderLayout());
add(panelMines,BorderLayout.CENTER);
 compteur=new Compteur();
 Joueur joueur= new Joueur("Joueur1");
 //panelnorth.add(joueur.getPseudo());
//panel north 
panelnorth.add(title,BorderLayout.NORTH);
panelnorth.add(compteur,BorderLayout.CENTER);
panelSouth= new JPanel(new BorderLayout());
//JPanel panelConnexion=new JPanel();

//JTextField hostField=new JTextField(20);
panelConnexion.add(new JLabel("Serveur"));
hostField=new JTextField("localhost");
panelConnexion.add(hostField);
panelConnexion.add(pseudoField);
panelConnexion.add(portField);
panelConnexion.add(connexionBut);
connexionBut.addActionListener(this);
panelnorth.add(panelConnexion,BorderLayout.SOUTH);

add(panelnorth,BorderLayout.NORTH);

//text area
msgArea=new  JTextArea(5,20);

msgArea.append("Bonne Partie\n");
panelSouth.add(msgArea,BorderLayout.NORTH);


//Writing message area
Box box = Box.createHorizontalBox();
add(box, BorderLayout.SOUTH);
//Demin.getTextField = new JTextField();
//JTextField inputTextField=new JTextField();
//sendButton = new JButton("Send");
box.add(inputTextField);
box.add(sendButton);
sendButton.addActionListener(this);


//butquit

butQuit.setForeground(Color.DARK_GRAY);
butQuit.setFont(new Font("Papyrus", Font.PLAIN,18));
butQuit.addActionListener(this);
//panelSouth.add(butQuit,BorderLayout.SOUTH);
panelSouth.add(box,BorderLayout.SOUTH);


//add(butQuit,BorderLayout.SOUTH);

add(panelSouth,BorderLayout.SOUTH);
JMenuBar menuBar=new JMenuBar();

//Le menu Partie
JMenu menuPartie=new JMenu("Partie");
menuBar.add(menuPartie);
JMenuItem mAide=new JMenuItem("Aide",KeyEvent.VK_Q);
//menuPartie.add(mQuit);
menuBar.add(Box.createGlue());//To add between the 2 adds

mQuit.addActionListener(this);
mNew.addActionListener(this);

menuPartie.add(mQuit);
menuPartie.add(mNew);
Demin.setJMenuBar(menuBar);
JMenu menuHelp=new JMenu("Aide");
menuBar.add(menuHelp);
menuHelp.add(mAide);
//Raccourci clavier  à partir du menu
JMenuItem itemExit= new JMenuItem("Exit",KeyEvent.VK_E);
//Raccourci clavier  à partir de la fenêtre

mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));

//Activation désactivation du menu
mQuit.setEnabled(true);//Pour activer désactiver
mQuit.addActionListener(this);
mEasy.addActionListener(this);
mMedium.addActionListener(this);
mHard.addActionListener(this);


//Level
JMenu menuLevel=new JMenu("Niveaux");
menuLevel.add(mEasy);
menuLevel.add(mMedium);
menuLevel.add(mHard);


menuPartie.add(menuLevel);

//JMenuItem mnewpartie=new JMenuItem("Nouvelle partie",KeyEvent.VK_Q);
menuPartie.add(mQuit);



}
 
 
 public Compteur getCompteur() {
	return compteur;
}


public void setCompteur(Compteur compteur) {
	this.compteur = compteur;
}

public void addMsg(String str) {
	msgArea.append(str);	
}

public void actionPerformed(ActionEvent e) {
	 //Level lev=Level();
	if(e.getSource()==butQuit  || e.getSource()==mQuit) 
	{
	
		int reponse=JOptionPane.showConfirmDialog(null, "êtes-vous sûrs?","Bye-Bye",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if( reponse==JOptionPane.YES_OPTION)//Si l'utilisteur a appuyé sur oui
		 
			System.exit(0);
		}else if(e.getSource()==mNew) {
			
			Demin.getChamp().placeMines();
			newPartie();
			
			//Demin.getGui().getCompteur().startCpt();

			
			
		}else if(e.getSource()==mEasy) {
			Level l=new Level(lvl.EASY);
			Demin.getChamp().newPartie(l);
			newPartie(l);
			
		}else if(e.getSource()==mMedium) {	
			Level l=new Level(lvl.MEDIUM);
			Demin.getChamp().newPartie(l);
			newPartie(l);


			
		}else if(e.getSource()==mHard) {
			Level l=new Level(lvl.HARD);
			Demin.getChamp().newPartie(l);
			newPartie(l);

		}else if(e.getSource()==connexionBut) {
			String Message="Welcome to the Démineur ISMIN game, please choose a nickname";
			String nickname=JOptionPane.showInputDialog(Message);
			Demin.setPseudo(nickname);
			//Add pop up window box;
			DataOutputStream out;
			
			Demin.Connect2Server(hostField.getText(),Integer.parseInt(portField.getText()),Demin.getPseudo());
			try {
				out = new DataOutputStream(Demin.getSocket().getOutputStream());
			     out.writeUTF(Demin.getPseudo());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Demin.setCmd(5);//To get the pseud

			
		}else if(e.getSource()==sendButton ) {
			System.out.println("Button send!!!!");
			String x = inputTextField.getText();
			if(!x.isEmpty()) {
				
			    try {
			    
			    	DataOutputStream out = new DataOutputStream(Demin.getSocket().getOutputStream());
				     out.writeUTF("0");
				//	InputStream input = new FileInputStream(x) ;
					
					//InputStream stream = new ByteArrayInputStream(x.getBytes(StandardCharsets.UTF_8));
					//int count = stream.available(); 
					//emin.setCmd(0);
				    //Thread message=new Thread();
					//message.start();
					 DataOutputStream out1 = new DataOutputStream(Demin.getSocket().getOutputStream());
				     out1.writeUTF(Demin.getPseudo()+": "+x);
				     //Writer wb=new Writer()
				    // msgArea.write(out);
				     inputTextField.setText("");
				     
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
				Demin.getDis();
				
			}
			//msgArea
		}
	
	//int x=e.getX();
    //int y=e.getY();
    //System.out.println(x+","+y);//these co-ords are relative to the componen
	 
	 
 }
 
 private void newPartie() {
	 for(int i=0;i<Demin.getChamp().getDimensionX();i++)
		 	for(int j=0;j<Demin.getChamp().getDimensionY();j++)
		 		tabCases[i][j].newPartie();
	 
	 	
	    Demin.getGui().getCompteur().stopCpt();
		//Demin.getGui().setCompteur(new Compteur());
	    Demin.newPartie();
		//Demin.setStarted(false);//réinitialisation
		//Demin.setLost(false);

		 		
	 
	 
}

 
 private void newPartie(Level l) {
	 	panelMines.removeAll();
	 	placeCases();
	 	Demin.pack();

	    Demin.getGui().getCompteur().stopCpt();
	    Demin.newPartie();
		//Demin.getGui().setCompteur(new Compteur());
		//Demin.setStarted(false);//réinitialisation
		//Demin.setLost(false);
 }
 
 private void  placeCases(){
	 


//case_demineur.paintComponent(gc);

int X=Demin.getChamp().getDimensionX();
int Y=Demin.getChamp().getDimensionY();
panelMines.setLayout(new GridLayout(X,Y));


tabCases= new Case[Demin.getChamp().getDimensionX()][Demin.getChamp().getDimensionY()];



for(int j=0;j<Y;j++) {	
		for(int i=0;i<X;i++) {
			tabCases[i][j]=new Case(i,j,Demin);
			panelMines.add(tabCases[i][j]);
 }

 
}
}
 
}