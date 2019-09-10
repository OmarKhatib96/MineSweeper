import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IHMHello extends JPanel implements ActionListener {
	
	private JButton butQuit =new JButton("Quit") ;//Les mettre en attributs pour qu'ils soient accessible dans la classe
	private JMenuItem mQuit=new JMenuItem("Quitter",KeyEvent.VK_Q);//
	private JMenuItem mNew=new JMenuItem("Nouvelle Partie",KeyEvent.VK_Q);//
	private Demineur Demin;
	private JMenuItem mEasy=new JMenuItem("Easy",KeyEvent.VK_E);
	private JMenuItem mMedium=new JMenuItem("Medium",KeyEvent.VK_E);
	private JMenuItem mHard=new JMenuItem("Hard",KeyEvent.VK_E);
	private JPanel panelMines=new JPanel();
	
	private  Case [][] tabCases;
	//private Graphics gc=new Graphics();
	//private JButton mQuit=new JButton("Quitter");
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
			
			/*if(Demin.getChamp().Ismin(i, j)) {//S'il y a une mines dans les coordonées i,j
				
				panelMines.add(new JLabel("X")); 
				panelMines.add( new JLabel(new ImageIcon(new ImageIcon("img/mine.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH))));
			}
				else
				panelMines.add(new JLabel(String.valueOf(Demin.getChamp().nbr_mines(i, j))));
*/
 

 
add(panelMines,BorderLayout.CENTER);
add(title,BorderLayout.NORTH);

butQuit.setForeground(Color.DARK_GRAY);
butQuit.setFont(new Font("Papyrus", Font.PLAIN,18));
butQuit.addActionListener(this);
add(butQuit,BorderLayout.SOUTH);


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

//mQuit.setToolTipText("The End");
//Coller le menu à droite

//ToolTips








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


			
		}
	
	//int x=e.getX();
    //int y=e.getY();
    //System.out.println(x+","+y);//these co-ords are relative to the componen
	 
	 
 }
 
 private void newPartie() {
	 for(int i=0;i<Demin.getChamp().getDimensionX();i++)
		 	for(int j=0;j<Demin.getChamp().getDimensionY();j++)
		 		tabCases[i][j].newPartie();
		 		
	 
	 
}
 
 private void newPartie(Level l) {
	 	panelMines.removeAll();
	 	placeCases();
	 	Demin.pack();
 }
 
 private void  placeCases(){
	 


//case_demineur.paintComponent(gc);

int X=Demin.getChamp().getDimensionX();
int Y=Demin.getChamp().getDimensionY();
panelMines.setLayout(new GridLayout(X,Y));

//JLabel label = new JLabel(new ImageIcon(new ImageIcon(ImageIcon("img/mine.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
//creation des tab des cases

tabCases= new Case[Demin.getChamp().getDimensionX()][Demin.getChamp().getDimensionY()];
for(int j=0;j<Y;j++) {	
		for(int i=0;i<X;i++) {
			tabCases[i][j]=new Case(i,j,Demin);
			panelMines.add(tabCases[i][j]);
 }

 
}
}
 
}