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
	public static String PSEUDO = "Omar";
	public static final int MSG = 0;
	public static final int POS = 1;
	public static final int START = 2;
	public static final int LOST = 10;

	public static final int END = 3;
	public static final int IDLE = 4;
	public static final int GETPSEUDO = 5;

	private boolean champRecu = false;
	private Color color;
	private int numeroClient = 0;
	private User client;
	private int cmd;
	private boolean connected = false;
	public static int increment = 0;
	private static String text;
	private JTextField inputTextField;// For each client
	private Chat chat;
	private static Champ champ;
	private boolean solo = false;
	private static int nbr_cases_decouvertes = 0;
	private boolean started = false;
	private static IHMHello gui;
	private boolean lost = false;
	private Thread process;
	private DataInputStream in;
	private DataOutputStream out;
	private HashMap<lvl, Integer> hmScoresOnline = new HashMap<lvl, Integer>();
	private HashMap<lvl, Integer> hmScoresOffline = new HashMap<lvl, Integer>();

	Socket sock;

	public int Get_nbr_cases_decouvertes() {
		return nbr_cases_decouvertes;

	}

	public boolean getChampRecu() {

		return champRecu;
	}

	private void Set_nbr_cases_decouvertes() {

		nbr_cases_decouvertes = 0;
	}

	public JTextField getTextField() {

		return inputTextField;
	}

	public Socket getSocket() {

		return sock;
	}

	public String getPseudo() {

		return PSEUDO;
	}

	public void setPseudo(String nickname) {

		this.PSEUDO = nickname;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public boolean getConnected() {

		return connected;
	}

	public void run() {

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
					System.out.println("Got inside the lost function");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // veut dire qu'on va recevoir un message

				System.out.println("Message sent successfully");// veut dire
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
					//System.out.println("After the save score function!");

					int color;
				try {
					color = in.readInt();
					gui.addMsg("Partie terminée!",new Color(color));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			if (cmd == Demineur.START) {
				try {
					// Level l=new Level(lvl.EASY);

					this.gui.repaint();
					this.gui.getCompteur().stopCpt();
					this.setStarted(false);
					this.setLost(false);
					champ.resetTabMines();
					Set_nbr_cases_decouvertes();
					int tailleX = in.readInt();
					int tailleY = in.readInt();
					String niveauServeur=in.readUTF();
					System.out.println("From demineur lvl= "+niveauServeur);
					champ.InitialisationChamp(tailleX, tailleY, 0);

					if(niveauServeur.equals("EASY"))
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

					gui.resetPanelMines();
					gui.placeCases();
					champ.affText();
					champRecu = true;
					System.out.println(champ.getNiveau().name());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gui.addMsg(getPseudo() + " " + "Go!\n", this.color);

			}

		}

	}

	public DataOutputStream getDos() {

		return out;
	}

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

	public void readMessage() {

		Thread readMessage = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						// read the message sent to this client
						String msg = in.readUTF();
						System.out.println(msg);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		});
		readMessage.start();

	}

	public void disconnect() {

		try {
			connected = false;
			process = null;
			sock.close();

		} catch (IOException ex) {

			System.out.println("Cannot close the socket!");
		}

	}

	public void Connect2Server(String HostField, int PortField, String PseuField) {
		System.out.println("Try to connect to:" + HostField + ":" + PortField);
		try {

			sock = new Socket(HostField, PortField);
			in = new DataInputStream(sock.getInputStream());
			out = new DataOutputStream(sock.getOutputStream());
			gui.addMsg(" Connexion réussie avec : " + HostField + ":" + PortField + "\n", this.color);
			cmd = IDLE;
			process = new Thread(this);
			process.start();
			connected = true;
			out.writeUTF(PseuField);
			gui.addMsg("Mon numéro client est: " + numeroClient, color);

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

	public void addScorehmOnline(lvl l, int score) {
		hmScoresOnline.put(l, score);

	}

	public void addScorehmOffline(lvl l, int score) {
		hmScoresOffline.put(l, score);

	}

	public boolean isWin() {
		boolean win = nbr_cases_decouvertes + champ.getNbMines() == champ.getDimensionX() * champ.getDimensionY();
		if (win)
			saveScore();
		return win;
	}



	public void saveScore() {
		if (connected) {// Si on n'est en mode multijoueur
			for (lvl niveau : lvl.values()) { //Pour créer les fichiers s'ils n'existent pas
		
		//	for (int i = 0; i < lvl.values().length; i++) {
				// Créer un fichier pour chaque niveau de difficulté?
				String FILENAME ="ScoreOnline" + niveau.name() + ".txt";
				Path path = Paths.get(FILENAME);
				if (!Files.exists(path)) {// si le fichier n'existe pas
					try {
						FileOutputStream fos = new FileOutputStream(FILENAME);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					}

				}

				
						hmScoresOnline.forEach((key,value) -> {

							try 
							{
								BufferedWriter writer = new BufferedWriter(new FileWriter("ScoreOnline"+ key.name()+".txt", true));
								writer.write(PSEUDO+" "+ " " + Integer.toString(value)+"\n");
								writer.close();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});


			
				}
				
			

			//TODO: remplir ave les scores en ligne
	

			else
			 {for (lvl niveau : lvl.values()) { 
		
				//	for (int i = 0; i < lvl.values().length; i++) {
						// Créer un fichier pour chaque niveau de difficulté?
						String FILENAME ="ScoreOffline" + niveau.name() + ".txt";
						Path path = Paths.get(FILENAME);
						if (!Files.exists(path)) {// si le fichier n'existe pas
							try {

								FileOutputStream fos = new FileOutputStream(FILENAME);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
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
		nbr_cases_decouvertes=nbr_cases;//reset when new game
		
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



