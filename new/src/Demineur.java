import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.UnknownHostException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

public class Demineur extends JFrame implements Runnable {

	// private static final String FILENAME="score.dat";
	private static Level lev;
	public static final int PORT = 1000;
	public static final String HOSTNAME = "localhost";
	/*
	 * Pseudo par défaut du client
	 */
	public static String PSEUDO = "Omar";
	/*
	 * Commande pour envoyer un message
	 */
	public static final int MSG = 0;
	/*
	 * Commande pour recevoir une position
	 */
	public static final int POS = 1;
	/*
	 * Commande pour envoyer le signal start
	 */
	public static final int START = 2;
	public static final int LOST = 10;

	public static final int END = 3;
	public static final int IDLE = 4;
	public static final int GETPSEUDO = 5;

	/*
	 * 
	 */
	private boolean champRecu = false;
	/*
	 * Couleur associée au client
	 */
	private Color color;
	private int numeroClient = 0;
	private int cmd;
	private boolean connected = false;
	public static int increment = 0;
	/*
	 * Champ de message pour chaque client
	 */
	private JTextField inputTextField;// For each client
	private static Champ champ;
	private boolean solo = false;
	private static int nbr_cases_decouvertes = 0;
	/*
	 * mis à true si la partie a commencé
	 */
	private boolean started = false;
	private static IHMHello gui;
	private boolean lost = false;
	private Thread process;
	private DataInputStream in;
	private DataOutputStream out;
	/*
	 * Hashmap pour stocker les scores du mode en ligne
	 */
	private HashMap<lvl, Integer> hmScoresOnline = new HashMap<lvl, Integer>();//Hashmap pour stocker les scores du mode en ligne
	/*
	 * Pour stocker les scores en mode  hors ligne
	 */
	private HashMap<lvl, Integer> hmScoresOffline = new HashMap<lvl, Integer>();////Hashmap pour stocker les scores du mode hors ligne

	Socket sock;

	/*
	 * Retourne le nombre de cases découvertes par le joueur
	 */
	public int Get_nbr_cases_decouvertes() {
		return nbr_cases_decouvertes;

	}

	public boolean getChampRecu() {

		return champRecu;
	}

	private void Set_nbr_cases_decouvertes() {

		nbr_cases_decouvertes = 0;
	}

	/**
	 * Permet de retourrner le champ de saisi du message pour chaque client
	 */
	public JTextField getTextField() {

		return inputTextField;
	}

	/**
	 * Permet d'obtenir le socket du client
	 */
	public Socket getSocket() {

		return sock;
	}

	/**
	 * Récupère le pseudo du client
	 */
	public String getPseudo() {

		return PSEUDO;
	}

	public void setPseudo(String nickname) {

		PSEUDO = nickname;
	}

/**
 * Permet d'obtenir le statut de connexion du client(s'il est connecté ou pas)
 */
	public boolean getConnected() {

		return connected;
	}

	public void run() {

		/**
		 * Tant que le thread n'est pas mort 
		 */
		while (process != null && connected) {// boucle "infini"
			try {
				
				cmd = in.readInt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (cmd == Demineur.MSG) {// Envoie d'un message par le serveur

				String msg;
				try {
					int couleurClient = in.readInt();
					msg = in.readUTF();

					gui.addMsg(msg, new Color(couleurClient));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // veut dire qu'on va recevoir un message

				System.out.println("Message sent successfully");// v
			}

			if (cmd == Demineur.POS) {

				try {
					int x = in.readInt();
					int y = in.readInt();
					String pseudoPlayer = in.readUTF();
					int color = in.readInt();
					gui.SetJPanel(x, y, new Color(color));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (cmd == Demineur.END) {
					System.out.println("Partie terminée!");

					int color;
				try {
					color = in.readInt();
					gui.addMsg("Partie terminée!",new Color(color));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			if (cmd == Demineur.START) {//Si on reçoit le signal de start de la part du serveur
				try {

					gui.repaint();
					gui.getCompteur().stopCpt();
					this.setStarted(false);
					this.setLost(false);
					champ.resetTabMines();
					Set_nbr_cases_decouvertes();
					int tailleX = in.readInt();
					int tailleY = in.readInt();
					String niveauServeur=in.readUTF();
					System.out.println("From demineur lvl= "+niveauServeur);
					champ.InitialisationChamp(tailleX, tailleY, 0);

					if(niveauServeur.equals("EASY"));
						champ.setNiveau(lvl.EASY);
					if(niveauServeur.equals("MEDIUM"))	{		
						champ.setNiveau((lvl.MEDIUM));
						System.out.println("Level set to Medium");
					}

					if(niveauServeur.equals("HARD"))
						champ.setNiveau(lvl.HARD);


					System.out.println("taille x=" + tailleX);
					System.out.println("taille y=" + tailleY);

					System.out.println("got inside the demineur.start");
					for (int i = 0; i < tailleX; i++) {
						for (int j = 0; j < tailleY; j++) {
							champ.setTabMines(in.readBoolean(), i, j);
						}
					}

					gui.resetPanelMines();//Réinitialise le panel mines
					gui.placeCases();
					champ.affText();//Affiche le champ
					champRecu = true;
					System.out.println(champ.getNiveau().name());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gui.addMsg("\n"+getPseudo() + " " + "Go!\n", this.color);

			}

		}

	}

	/**
	 * Récupère le DataOutPutStream du client
	 */
	public DataOutputStream getDos() {

		return out;
	}

	/**
	 * Récupère le DataInPutStream du client
	 */
	public DataInputStream getDis() {

		return in;
	}

	public void setDis(DataInputStream input) {

		this.in = input;
	}

	public void setNumeroClient(int num) {
		numeroClient = num;

	}

	
	public int getColorInt() {

		return color.getRGB();
	}

	public int getNumeroClient() {
		return numeroClient;
	}


	/**
	 * Permet la déconnexion du client
	 */
	public void disconnect() {

		try {
			connected = false;
			process = null;
			sock.close();

		} catch (IOException ex) {

			System.out.println("Cannot close the socket!");
		}

	}
	/**
	 * Permet la connexion du client au serveur
	 */
	
	public void Connect2Server(String HostField, int PortField, String PseuField) {
		System.out.println("Try to connect to:" + HostField + ":" + PortField);
		try {

			sock = new Socket(HostField, PortField);
			in = new DataInputStream(sock.getInputStream());
			out = new DataOutputStream(sock.getOutputStream());
			gui.addMsg("Connexion réussie avec : " + HostField + ":" + PortField + "\n", this.color);
			cmd = IDLE;
			process = new Thread(this);
			process.start();
			connected = true;
			out.writeUTF(PseuField);
		} catch (UnknownHostException e) {
			gui.addMsg("Connexion impossible avec : " + HostField + ":" + PortField, this.color);
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newPartie() {

		setStarted(false);

		setLost(false);
		nbr_cases_decouvertes = 0;
	}

	/**
	 * Ajoute le score du client à la HashMap en mode en ligne
	 */
	public void addScorehmOnline(lvl l, int score) {
		hmScoresOnline.put(l, score);

	}

	/**
	 * Ajoute le score du client à la HashMap en mode hors ligne
	 */
	public void addScorehmOffline(lvl l, int score) {
		hmScoresOffline.put(l, score);

	}
/**
 * Permet de dire si le client a gagné ou pas
 */
	
	public boolean isWin() {
		boolean win = nbr_cases_decouvertes + champ.getNbMines() == champ.getDimensionX() * champ.getDimensionY();
		if (win)
			saveScore();
		return win;
	}

/**
 * Permet de sauvegarder les score en mode en ligne et en mode hors ligne
 */

	public void saveScore() {
		if (connected) {// Si on est en mode multijoueur
			for (lvl niveau : lvl.values()) { //Pour créer les fichiers s'ils n'existent pas
		
		
				String FILENAME ="ScoreOnline" + niveau.name() + ".txt";
				Path path = Paths.get(FILENAME);
				if (!Files.exists(path)) {// si le fichier n'existe pas
					try {
						FileOutputStream fos = new FileOutputStream(FILENAME);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

					}

				}

						hmScoresOnline.forEach((key,value) -> {//Pour chaque joueur on va stocker les scores

							try 
							{
								BufferedWriter writer = new BufferedWriter(new FileWriter("ScoreOnline"+ key.name()+".txt", true));
								writer.write(PSEUDO+" "+ " " + Integer.toString(value)+"\n");
								writer.close();

							} catch (IOException e) {
								e.printStackTrace();
							}
						});


			
				}
				
			

	

			else//Pour le mode hors-ligne
			 {for (lvl niveau : lvl.values()) { 
		
				
						String FILENAME ="ScoreOffline" + niveau.name() + ".txt";
						Path path = Paths.get(FILENAME);
						if (!Files.exists(path)) {// si le fichier n'existe pas
							try {

								FileOutputStream fos = new FileOutputStream(FILENAME);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
		
							}
		
						}
		
						
								hmScoresOffline.forEach((key,value) -> {
		
									try 
									{
										BufferedWriter writer = new BufferedWriter(new FileWriter("ScoreOffline"+ key.name()+".txt", true));
										System.out.println(PSEUDO+" "+" " + Integer.toString(value));

										writer.write(PSEUDO+" "+key.name() + " " + Integer.toString(value)+"\n");
										writer.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								});
		
		
						
						}
	}
			 


	
	public void set_nbr_cases_decouvertes(int nbr_cases) {
		nbr_cases_decouvertes=nbr_cases;
		
	}
	
	public boolean getLost() {
		return lost;
	}
	
	public IHMHello getGui(){

		return gui;
	}
	public void setLost(boolean lost) {
		this.lost=lost;
	}

	public void setGui(IHMHello gui) {
		this.gui = gui;
	}

	public void startCompteur() {
		
		
	}


	public Demineur()//Constructeur par d�faut du Demineur
	{
		
		
    	Champ champ=new Champ();    	 	
    	champ.affText();       	   	
    	System.out.println(champ);//Affichage de C(apr�s surcharge)
				
	}
	
public boolean isStarted() {return started;}
	
	public void setStarted(boolean started) {
		
		this.started=started;
	}
	/**
	 * Constructeur de la classe démineur
	 */
	public  Demineur(String name,Level lv)//Constructeur surcharg�
	{
		
		super("Démineur ISMIN Game");
		lev=lv;
		setRandomColor();

        if(solo==false) 	 {
           
            champ=new Champ(name,lv); 
        }   
        else
            champ=new Champ(name,lv); 

	    gui= new IHMHello(this) ;//Renommer IHMHello � GUi
		setContentPane(gui) ;//mettre un panel au milieu	
		ImageIcon img=new ImageIcon("new/img/logo.jpg");
		setIconImage(img.getImage());

		pack();
		setVisible(true) ;
		process=new Thread(this);
		process.start();
		
	}
		
	public Champ getChamp()
	{
		
		return champ;
	}
	
    public static void main(String args [])
    {
    	Level l=new Level(lvl.EASY);
    	new Demineur("Mineur game",l);//Constructeur par défaut
    
    	
	
    }
    
    /**
     * Permet de génèrer une couleur aléatoire
     */
  void setRandomColor() {
	  
	  Random rand=new Random();
	  int r=rand.nextInt(256);
	 int g=rand.nextInt(256);
	  int b=rand.nextInt(256);
	  Color randomColor=new Color(r,g,b);
	  this.color=randomColor;

	  
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



