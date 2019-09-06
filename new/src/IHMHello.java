import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IHMHello extends JPanel implements ActionListener {
	
	private JButton butQuit =new JButton("Quit") ; 
	private JButton mQuitter=new JButton("Quitter");
/** constructeur
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
			if(Demin.getChamp().Ismin(i, j))//S'il y a une mines dans les coordon�es i,j
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
JMenuItem mQuitter=new JMenuItem("Quitter",KeyEvent.VK_Q);
JMenuItem mAide=new JMenuItem("Help",KeyEvent.VK_Q);
menuPartie.add(mQuitter);
mQuitter.addActionListener(this);
menuPartie.add(mQuitter);

Demin.setJMenuBar(menuBar);
JMenu menuHelp=new JMenu("Help");
menuBar.add(menuHelp);

menuHelp.add(mAide);




 
 }
 
 
 public void actionPerformed(ActionEvent e) {
	if(e.getSource()==butQuit  || e.getSource()==mQuitter) {
	
		int reponse=JOptionPane.showConfirmDialog(null, "�tes-vous s�rs?","Bye-Bye",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if( reponse==JOptionPane.YES_OPTION)
		 
			System.exit(0);
		}
	 
	 
 }
}