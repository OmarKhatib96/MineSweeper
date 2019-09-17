
import java.awt.GridLayout;
import java.awt.event.* ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.* ; 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.GridBagLayout;


public class Demineur extends JFrame {
	
	private static final String FILENAME="score.dat";
	private static Level lev;
	private static Champ champ=	new Champ("Mineur game", new Level(lvl.EASY));
	private static int nbr_cases_decouvertes=0;

	
	
	private boolean started=false;
	private IHMHello gui;
	private boolean lost=false;
	
	public int  Get_nbr_cases_decouvertes() {
		return nbr_cases_decouvertes;
		
		
	}
	
	 public void newPartie() {
		 
		 setStarted(false);
		 
		 setLost(false);
		 nbr_cases_decouvertes=0;
	 }
	
	public boolean isWin()
	{
		boolean win= nbr_cases_decouvertes+champ.getNbMines()== champ.getDimensionX()*champ.getDimensionY();
		if(win)
			saveScore();
		return win;
	}
	
	private void saveScore() {
		
		Path path=Paths.get(FILENAME);
		if(!Files.exists(path)) {//si le fichier n'existe pas
			for(int i=0;i<lvl.values().length;i++) {
				
				if(Champ.getLevel())
			}
			
		}
	}
	
	public void set_nbr_cases_decouvertes(int nbr_cases) {
		nbr_cases_decouvertes=nbr_cases;//reset when new game
		
	}
	
	public boolean getLost() {
		return lost;
	}
	
	public void setLost(boolean lost) {
		this.lost=lost;
	}
	public IHMHello getGui() {
		return gui;
	}

	public void setGui(IHMHello gui) {
		this.gui = gui;
	}

	public void startCompteur() {
		
		
	}


	public Demineur()//Constructeur par défaut du Demineur
	{
		
		
    	Champ champ=new Champ();    	 	
    	champ.affText();       	   	
    	System.out.println(champ);//Affichage de C(après surcharge)
				
	}
	
public boolean isStarted() {return started;}
	
	public void setStarted(boolean started) {
		
		this.started=started;
	}
	public  Demineur(String name,Level lv)//Constructeur surchargé
	{
		
		super("Démineur ISMIN");
		lev=lv;
		Champ champ2= new Champ(name,lv); 
		champ=champ2;
    	champ2.affText();    	    	
	     gui= new IHMHello(this) ;//Renommer IHMHello à GUi
		setContentPane(gui) ;//mettre un panel au milieu		
    	//JPanel monPanel = new JPanel() ;
    	//add(monPanel) ; 
    	pack();
		setVisible(true) ; 
		
	}
	
	
	public Champ getChamp()
	{
		
		return champ;
	}
	
    public static void main(String args [])
    {
    	Level l=new Level(lvl.EASY);
    	new Demineur("Mineur game",l);
    	
    	
    }
    
    
  
  //Destructeur
  	protected void finalize() throws Throwable
  	{
  		super.finalize();
  	}

  	/** Quit the application */
  	public void quit() {
  	System.out.println("Bye-Bye ");
  	System.exit(0) ;
  	 } 
	
    
}



