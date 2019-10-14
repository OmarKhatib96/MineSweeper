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
	/**
	 * BOuton quitter
	 */
	private JButton butQuit =new JButton("Quit") ;//Les mettre en attributs pour qu'ils soient accessible dans la classe
	/**
	 * Bouton pour envoyer un message
	 */
	private JButton sendButton = new JButton("Send");
	/**
	 * Menu pour quitter la partie
	 */
	private JMenuItem mQuit=new JMenuItem("Quitter",KeyEvent.VK_Q);//
	/**
	 * Pour lancer une nouvelle partie
	 */
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
	/**
	 * Surface des messages sur la gui
	 */
	private JTextPane msgArea;
	private JTextField inputTextField=new JTextField();

	private JButton connexionBut=new JButton("Connect");
	

/**
 * Permet de réinitialiser les cases du démineur
 */
	public void resetPanelMines(){

		panelMines.removeAll();

	}
	/**
	 * Matrice des cases 
	 */
	private  Case [][] tabCases;


/**
 * Permet de récupérer le tableau des cases du démineur
 * @return
 */
	public Case[][] getTabCases(){

		return tabCases;
	}
/** constructeuraddActionListen
* @param  Notre démineur
*/
 public IHMHello(Demineur Demin) {
		
setBackground(Color.black);

/**
 * Titre à afficher sur la gui du 
 */
JLabel title = new JLabel("Welcome on board! ");
title.setForeground(Color.DARK_GRAY);
title.setFont(new Font("TimesRoman", Font.BOLD,18));
Color titleColor=new Color(123,123,123);
title.setBackground(titleColor);
setLayout(new BorderLayout());
title.setHorizontalTextPosition(JLabel.NORTH_EAST);
title.setBorder(BorderFactory.createLineBorder(titleColor));
this.Demin=Demin;			
JPanel panelnorth=new JPanel(new BorderLayout());
add(panelMines,BorderLayout.CENTER);
/**
 * Instancie un compteur
 */
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
Color couleurConnexion=new Color(Demin.getColorInt());
panelConnexion.setBackground(couleurConnexion);
panelConnexion.setBorder(BorderFactory.createLineBorder(titleColor));
connexionBut.addActionListener(this);
panelnorth.add(panelConnexion,BorderLayout.SOUTH);
add(panelnorth,BorderLayout.NORTH);
panelnorth.setBackground(couleurConnexion);
EmptyBorder eb = new EmptyBorder(new Insets(12, 12, 12, 12));
msgArea=new JTextPane();
msgArea.setBorder(eb);
msgArea.setMargin(new Insets(0, 15, 15, 15));
JScrollPane jsp = new JScrollPane(msgArea);
jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
jsp.getViewport().add(msgArea);
panelSouth.add(msgArea,BorderLayout.NORTH);
panelSouth.add(jsp);
Box box = Box.createHorizontalBox();
add(box, BorderLayout.SOUTH);
box.add(inputTextField);
box.add(sendButton);
sendButton.addActionListener(this);
sendButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,ActionEvent.ACTION_PERFORMED));
mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
Demin.getRootPane().setDefaultButton(sendButton);
sendButton.setEnabled(false);
butQuit.setForeground(Color.DARK_GRAY);
butQuit.setFont(new Font("Papyrus", Font.PLAIN,18));
butQuit.addActionListener(this);
panelSouth.add(box,BorderLayout.SOUTH);
add(panelSouth,BorderLayout.SOUTH);
JMenuBar menuBar=new JMenuBar();
JMenu menuPartie=new JMenu("Partie");
menuBar.add(menuPartie);
JMenuItem mAide=new JMenuItem("Aide",KeyEvent.VK_Q);
menuBar.add(Box.createGlue());//To add between the 2 adds
mQuit.addActionListener(this);
mNew.addActionListener(this);
menuPartie.add(mQuit);
menuPartie.add(mNew);
Demin.setJMenuBar(menuBar);
JMenu menuHelp=new JMenu("Aide");
menuBar.add(menuHelp);
menuHelp.add(mAide);
//Raccourci clavier  ï¿½ partir du menu
JMenuItem itemExit= new JMenuItem("Exit",KeyEvent.VK_E);
//Raccourci clavier  ï¿½ partir de la fenï¿½tre
mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
//Activation dï¿½sactivation du menu
mQuit.setEnabled(true);//Pour activer dï¿½sactiver
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

/**
 * Fonction permettant d'afficher un message sur l'interface graphique du client
 * @param str message à afficher
 * @param c couleur du client
 */
public void addMsg(String str,Color c) {
	    StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = msgArea.getDocument().getLength();
        msgArea.setCaretPosition(len);
        msgArea.setCharacterAttributes(aset, false);
        msgArea.replaceSelection(str);
}



public void keyPressed(KeyEvent e) {
    if (e.getKeyCode()==KeyEvent.VK_ENTER){
        String x = inputTextField.getText();
			if(!x.isEmpty()) {
				
			    try {
			    
			    	DataOutputStream out = new DataOutputStream(Demin.getSocket().getOutputStream());
					out.writeInt(0);//Prï¿½venir le serveur qu'on va envoyer un message
					out.writeInt(Demin.getColorInt());//Envoi de la couleur aussi
				    out.writeUTF(Demin.getPseudo()+": "+x);
				    inputTextField.setText("");//Reset the field
				     
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			   
				
			}
		}	 
	 
 }
    



public void actionPerformed(ActionEvent e) {
	if(e.getSource()==butQuit  || e.getSource()==mQuit) 
	{
	
		int reponse=JOptionPane.showConfirmDialog(null, "Etes-vous sÃ»rs?","Bye-Bye",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if( reponse==JOptionPane.YES_OPTION)//Si l'utilisteur a appuyï¿½ sur oui
		 
			System.exit(0);
		}else if(e.getSource()==mNew) {
			
			Demin.getChamp().placeMines();
			newPartie();

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
				connexionBut.setEnabled(false);

			
			
		   

			DataOutputStream out;
			DataInputStream in;

			Demin.Connect2Server(hostField.getText(),Integer.parseInt(portField.getText()),Demin.getPseudo());
			try {
				out = new DataOutputStream(Demin.getSocket().getOutputStream());
				 out.writeUTF(Demin.getPseudo());
				 in = new DataInputStream(Demin.getSocket().getInputStream());
				 sendButton.setEnabled(true);//Activer le chat
				

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			
		}else if(e.getSource()==sendButton ) {
			String x = inputTextField.getText();
			if(!x.isEmpty()) {
				
			    try {
			    
			    	DataOutputStream out = new DataOutputStream(Demin.getSocket().getOutputStream());
					out.writeInt(0);//Prï¿½venir le serveur qu'on va envoyer un message
					out.writeInt(Demin.getColorInt());//Envoi de la couleur aussi
				    out.writeUTF(Demin.getPseudo()+": "+x);
				    inputTextField.setText("");//Reset the field
				     
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			   
				
			}
		}	 
	 
 }
 
/**
 * Permet de lancer une nouvelle partie
 */
 private void newPartie() {
	 for(int i=0;i<Demin.getChamp().getDimensionX();i++)
		 	for(int j=0;j<Demin.getChamp().getDimensionY();j++)
		 		tabCases[i][j].newPartie();
	 
	 	
	    Demin.getGui().getCompteur().stopCpt();
	    Demin.newPartie();
	
		 		
	 
	 
}

 /**
  * Permet de lancer une nouvelle partie avec un certain niveau
  * @param l niveu de la partie
  */
 private void newPartie(Level l) {
		 
	if(Demin.getConnected()){
			DataOutputStream out= new DataOutputStream (Demin.getDos());
			try {
				out.writeInt(10);
				out.writeBoolean(false);
				out.writeUTF(Demin.getPseudo());
				out.writeInt(this.Demin.getGui().getCompteur().GetCounter());
				out.writeInt(this.Demin.Get_nbr_cases_decouvertes());
				out.writeInt(this.Demin.getColorInt());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

				Demin.disconnect();
				connexionBut.setEnabled(true);//Reactiver le bouton connect 
		}	
		resetPanelMines();
	 	placeCases();
	 	Demin.pack();
	    Demin.getGui().getCompteur().stopCpt();
		Demin.newPartie();
		
 }
 
 /**
  * Permet de modifier la case du démineur qui correspond à la case cliquée par un joueur
  * @param x coordonnée x de la case
  * @param y coordonnée y de la case
  * @param c couleur de la case cliquée
  */
 
 public void SetJPanel(int x, int y,Color c)
 {	
	tabCases[x][y].setClickedCase();//C'est ï¿½a qui permet de modifier la couleur
	tabCases[x][y].setBackground(c);
	tabCases[x][y].getGraphics().setColor(c);
	tabCases[x][y].getGraphics().fillRect(0, 0, getWidth(), getHeight());
		 
 }



 /**
  * Permet  de placer les cases sur le démineur en fonction du champ du serveur 
  */
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