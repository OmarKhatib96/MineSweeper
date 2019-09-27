
import java.awt.GridLayout;
import java.awt.event.* ;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.UnknownHostException;

import javax.swing.* ; 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.GridBagLayout;


public class Demineur extends JFrame implements Runnable {
	
	private static final String FILENAME="score.dat";
	private static Level lev;
	public static final int PORT=1000;
	public static final String HOSTNAME="localhost";
	public static final String PSEUDO="Omar";
    public static final  int  MSG=0;
    public static final  int  POS=1;
    public static final  int  START=2;
    public static final  int  END=3;



	private static Champ champ=	new Champ("Mineur game", new Level(lvl.EASY));
	private static int nbr_cases_decouvertes=0;
	private boolean started=false;
	private IHMHello gui;
	private boolean lost=false;
	JTextArea msgArea=new  JTextArea(5,20);
	private Thread process;
	private DataInputStream in;
	private DataOutputStream out;
	public int  Get_nbr_cases_decouvertes() {
		return nbr_cases_decouvertes;
			
	}
		
	//Boucle d'attente des évts du serveur
	public void run() {
		
		while(process!=null) {
			int cmd=in.readInt();
			if(cmd==Demineur.MSG) {//Envoie d'un message par le serveur
				
				String msg =in.readUTF();
				gui.addMsg(msg);
				
			}
			
		}
		//boucle infinie
		
		//lecture dans in
		
		//Lecture de la commande
		
		//lecture du joueur qui a cliqué en  x,y

		
		//En fct de ce que je lis: j'affiche les mines/numeros/fin de partie
		
		
		
	}
	
	
	public void Connect2Server(String HostField,int PortField,String PseuField) 
	{
	System.out.println("Try to connect to:"+HostField+":"+PortField);
	try {
		
		Socket sock=new Socket(HostField,PortField);
		gui.addMsg(" Connexion réussie avec : "+HostField+":"+PortField);
		in= new DataInputStream(sock.getInputStream());
		out=new DataOutputStream(sock.getOutputStream());
		process=new Thread(this);
		DataOutputStream out =new  DataOutputStream(sock.getOutputStream());
		DataInputStream in = new DataInputStream(sock.getInputStream()); 
		
		//String pseudoJoueur=in.
		//System.out.println("Joueur n°:"+pseudoJoueur); 

		
	}catch(UnknownHostException e) {
		gui.addMsg("Connexion impossible avec : "+HostField+":"+PortField);
		e.printStackTrace();
	
		
	}catch(IOException e) {
		e.printStackTrace();
	}}
	
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
				
				//if(Champ.getLevel())
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



