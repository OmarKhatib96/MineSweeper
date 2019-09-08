
import java.awt.GridLayout;
import java.awt.event.* ;
import javax.swing.* ; 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridBagLayout;


public class Demineur extends JFrame {
	
	
	private static Level lev;
	private static Champ champ;
	
	public Demineur()//Constructeur par d�faut du Demineur
	{
		
		
    	Champ champ=new Champ();    	 	
    	champ.affText();       	   	
    	System.out.println(champ);//Affichage de C(apr�s surcharge)
				
	}
	
	public  Demineur(String name,Level lv)//Constructeur surcharg�
	{
		
		super("D�mineur ISMIN");
		lev=lv;
		Champ champ2= new Champ(name,lv); 
		champ=champ2;
    	champ2.affText();    	    	
		IHMHello gui= new IHMHello(this) ;//Renommer IHMHello � GUi
		setContentPane(gui) ;//mettre un panel au milieu		
    	JPanel monPanel = new JPanel() ;
    	add(monPanel) ; 
    	pack();
		setVisible(true) ; 
		
	}
	
	
	public Champ getChamp()
	{
		
		return champ;
	}
	
    public static void main(String args [])
    {
    	//new Demineur();//Constructeur par d�faut
    	Level l=new Level(lvl.EASY);
    	new Demineur("Mineur game",l);
    	
    	//new Demineur(10,5,"Mines_Game");//Constructeur surcharg�
    	
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


