import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IHMHello extends JPanel implements ActionListener {
	
	private JButton butQuit =new JButton("Quit") ;//Les mettre en attributs pour qu'ils soient accessible dans la classe
	private JMenuItem mQuit=new JMenuItem("Quitter",KeyEvent.VK_Q);//
	//private JButton mQuit=new JButton("Quitter");
/** constructeuraddActionListener
* @param la classe contenant les traitements
*/
 public IHMHello(Demineur Demin) {
		
setBackground(Color.cyan);

//ImageIcon quitIcon = new ImageIcon("sortieCLR.gif"); // image
JLabel title = new JLabel("Welcome on board");





JPanel panelMines =new JPanel();
int X=Demin.getChamp().getDimensionX();
int Y=Demin.getChamp().getDimensionY();
panelMines.setLayout(new GridLayout(X,Y));


for(int j=0;j<Y;j++)	
		for(int i=0;i<X;i++) 			
			if(Demin.getChamp().Ismin(i, j))//S'il y a une mines dans les coordonées i,j
				panelMines.add(new JLabel("X")); 
			else
				panelMines.add(new JLabel(String.valueOf(Demin.getChamp().nbr_mines(i, j))));

add(title,BorderLayout.NORTH);
add(panelMines,BorderLayout.CENTER);
butQuit.setForeground(Color.DARK_GRAY);
butQuit.setFont(new Font("Papyrus", Font.PLAIN,18));
butQuit.addActionListener(this);
add(butQuit,BorderLayout.SOUTH);


JMenuBar menuBar=new JMenuBar();

//Le menu Partie
JMenu menuPartie=new JMenu("Partie");
menuBar.add(menuPartie);
JMenuItem mAide=new JMenuItem("Help",KeyEvent.VK_Q);
menuPartie.add(mQuit);
mQuit.addActionListener(this);
menuPartie.add(mQuit);
Demin.setJMenuBar(menuBar);
JMenu menuHelp=new JMenu("Help");
menuBar.add(menuHelp);
menuHelp.add(mAide);
}
 
 
 public void actionPerformed(ActionEvent e) {
	if(e.getSource()==butQuit  || e.getSource()==mQuit) 
	{
	
		int reponse=JOptionPane.showConfirmDialog(null, "êtes-vous sûrs?","Bye-Bye",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if( reponse==JOptionPane.YES_OPTION)//Si l'utilisteur a appuyé sur oui
		 
			System.exit(0);
		}
	 
	 
 }
}