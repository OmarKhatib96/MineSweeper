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
import javax.swing.border.EmptyBorder;



import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
    //tPane = new JTextPane();                

	private JTextPane msgArea;
	private JTextField inputTextField=new JTextField();
	//msgArea=new  JTextArea(10,20);

	private JButton connexionBut=new JButton("Connect");
	


	public void resetPanelMines(){

		panelMines.removeAll();


	}
	private  Case [][] tabCases;



	public Case[][] getTabCases(){

		return tabCases;
	}
/** constructeuraddActionListen
* @param la classe contenant les traitements
*/
 public IHMHello(Demineur Demin) {
		
setBackground(Color.black);

//ImageIcon quitIcon = new ImageIcon("sortieCLR.gif"); // image
JLabel title = new JLabel("                                           Welcome on board");
title.setForeground(Color.DARK_GRAY);
title.setFont(new Font("TimesRoman", Font.BOLD,18));
Color titleColor=new Color(128,128,128);
title.setBackground(titleColor);

setLayout(new BorderLayout());
title.setHorizontalTextPosition(JLabel.NORTH_EAST);
//Case case_demineur=new Case();
title.setBorder(BorderFactory.createLineBorder(titleColor));
//case_demineur.paintComponent(gc);
this.Demin=Demin;
//placeCases();
			
JPanel panelnorth=new JPanel(new BorderLayout());

//panelnorth.set
add(panelMines,BorderLayout.CENTER);
compteur=new Compteur();
compteur.setBackground(titleColor);
compteur.setBorder(BorderFactory.createLineBorder(titleColor));
panelnorth.add(title,BorderLayout.NORTH);
panelnorth.add(compteur,BorderLayout.CENTER);
panelSouth= new JPanel(new BorderLayout());

panelConnexion.add(new JLabel("Serveur"));
hostField=new JTextField("localhost");
panelConnexion.add(hostField);
panelConnexion.add(pseudoField);
panelConnexion.add(portField);
panelConnexion.add(connexionBut);

Color couleurConnexion=new Color(178, 66, 2);
panelConnexion.setBackground(couleurConnexion);
panelConnexion.setBorder(BorderFactory.createLineBorder(titleColor));

connexionBut.addActionListener(this);
panelnorth.add(panelConnexion,BorderLayout.SOUTH);

add(panelnorth,BorderLayout.NORTH);
panelnorth.setBackground(titleColor);

EmptyBorder eb = new EmptyBorder(new Insets(12, 12, 12, 12));

//tPane = new JTextPane();                

//text area
//msgArea=new  JTextArea(5,20);
msgArea=new JTextPane();

msgArea.setBorder(eb);
//tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
msgArea.setMargin(new Insets(15, 15, 15, 15));
//jsp.getViewport().add(msgArea);

JScrollPane jsp = new JScrollPane(msgArea);

jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

jsp.getViewport().add(msgArea);


panelSouth.add(msgArea,BorderLayout.NORTH);
panelSouth.add(jsp);
//this.add(jsp);
//panelSouth.add(jsp);

//Writing message area
Box box = Box.createHorizontalBox();
add(box, BorderLayout.SOUTH);

box.add(inputTextField);
box.add(sendButton);
sendButton.addActionListener(this);


//butquit

butQuit.setForeground(Color.DARK_GRAY);
butQuit.setFont(new Font("Papyrus", Font.PLAIN,18));
butQuit.addActionListener(this);
panelSouth.add(box,BorderLayout.SOUTH);



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
//Raccourci clavier  � partir du menu
JMenuItem itemExit= new JMenuItem("Exit",KeyEvent.VK_E);
//Raccourci clavier  � partir de la fen�tre
mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
//Activation d�sactivation du menu
mQuit.setEnabled(true);//Pour activer d�sactiver
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
menuPartie.add(mQuit);

}
 
 public Compteur getCompteur() {
	return compteur;
}

public void setCompteur(Compteur compteur) {
	this.compteur = compteur;
}

public void addMsg(String str,Color c) {
	//msgArea.replaceSelection(str);	
	    StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = msgArea.getDocument().getLength();
        msgArea.setCaretPosition(len);
        msgArea.setCharacterAttributes(aset, false);
        msgArea.replaceSelection(str);
}

public void actionPerformed(ActionEvent e) {
	if(e.getSource()==butQuit  || e.getSource()==mQuit) 
	{
	
		int reponse=JOptionPane.showConfirmDialog(null, "�tes-vous s�rs?","Bye-Bye",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if( reponse==JOptionPane.YES_OPTION)//Si l'utilisteur a appuy� sur oui
		 
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
			
			String Message="Please enter your nickname\n";
			String pseudofield = pseudoField.getText();

			while(pseudofield.isEmpty()) {
			
			String nickname=JOptionPane.showInputDialog(Message);

			}
			
		

				Demin.setPseudo(pseudofield);
			
			
			
		   

			
		 
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

			
		}else if(e.getSource()==sendButton ) {
			String x = inputTextField.getText();
			if(!x.isEmpty()) {
				
			    try {
			    
			    	DataOutputStream out = new DataOutputStream(Demin.getSocket().getOutputStream());
					out.writeInt(0);//Pr�venir le serveur qu'on va envoyer un message
					out.writeInt(Demin.getColorInt());//Envoi de la couleur aussi
				    out.writeUTF(Demin.getPseudo()+": "+x);
				    inputTextField.setText("");//Reset the field
				     
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			   
				
			}
		}	 
	 
 }
 
 private void newPartie() {
	 for(int i=0;i<Demin.getChamp().getDimensionX();i++)
		 	for(int j=0;j<Demin.getChamp().getDimensionY();j++)
		 		tabCases[i][j].newPartie();
	 
	 	
	    Demin.getGui().getCompteur().stopCpt();
	    Demin.newPartie();
	
		 		
	 
	 
}

 
 private void newPartie(Level l) {
		resetPanelMines();
	 	placeCases();
	 	Demin.pack();
	    Demin.getGui().getCompteur().stopCpt();
	    Demin.newPartie();
		
 }
 
 
 public void SetJPanel(int x, int y,Color c)
 {	
	tabCases[x][y].setClickedCase();//C'est �a qui permet de modifier la couleur
	tabCases[x][y].setBackground(c);
	tabCases[x][y].getGraphics().setColor(c);
	tabCases[x][y].getGraphics().fillRect(0, 0, getWidth(), getHeight());
		 
 }



public void  placeCases(){

int X=Demin.getChamp().getDimensionX();
int Y=Demin.getChamp().getDimensionY();
panelMines.setLayout(new GridLayout(X,Y));


tabCases= new Case[Demin.getChamp().getDimensionX()][Demin.getChamp().getDimensionY()];



for(int i=0;i<X;i++) {	
		for(int j=0;j<Y;j++) {
			tabCases[i][j]=new Case(i,j,Demin);
			panelMines.add(tabCases[i][j]);
 }

 
}
}
 
}