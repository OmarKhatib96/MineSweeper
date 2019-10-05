
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
import java.util.Scanner;

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
	public static  String PSEUDO="Omar";
    public static final  int  MSG=0;
    public static final  int  POS=1;
    public static final  int  START=2;
    public static final  int  END=3;
    public static final int IDLE=4;
    public static final int GETPSEUDO=5;

    private User client;
    private int cmd;
    private Scanner scn = new Scanner(System.in); 
    private boolean connected=false;
    public static int increment=0;
    private static String text;
    //private boolean ButtonSent=false;
    private JTextField inputTextField;//For each client

	private static Champ champ=	new Champ("Mineur game", new Level(lvl.EASY));
	private static int nbr_cases_decouvertes=0;
	private boolean started=false;
	private static IHMHello gui;
	private boolean lost=false;
	JTextArea msgArea=new  JTextArea(5,20);
	private  Thread process;
	
	private  DataInputStream in;
	private DataOutputStream out;
	Socket sock;
	public int  Get_nbr_cases_decouvertes() {
		return nbr_cases_decouvertes;
			
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
		
		this.PSEUDO=nickname;
	}
	
	
	public void setCmd(int cmd) {
		this.cmd=cmd;
	}
	//Boucle d'attente des �vts du serveur
	public void run() {
		
		
		
		while(process!=null && connected) {//boucle "infini"
				try {
					cmd=in.readInt();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(cmd==Demineur.MSG) {//Envoie d'un message par le serveur
				System.out.println("Button pressed ok");

				String msg;
				try {
					msg = in.readUTF();
					gui.addMsg(msg+'\n');
					System.out.println("from client"+msg);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//veut dire qu'on va recevoir un message

				//sendMessage(msg);
				//gui.addMsg(msg);
				System.out.println("Message sent successfully");//veut dire
				//cmd=IDLE;
			}
			
			if(cmd==Demineur.POS) {
				
				try {
					int x=in.readInt();
					int y=in.readInt();
					String pseudoPlayer=in.readUTF();
					System.out.println(pseudoPlayer+" "+x+" "+y);
					gui.addMsg(pseudoPlayer+" a cliqué sur la case "+"("+x+","+y+")+\n");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}
			
			if(cmd==Demineur.END) {
				
				String msg;
				started=false;
				quit();
				closeSocket();
				process=null;
				//out.writeUTF(msg);
				
			}
			if(cmd==Demineur.START) {
				gui.addMsg(getPseudo()+" "+ "Go!\n");
				started=true;
				
				
			}
			
			
		}
		
		}
		
		//boucle infinie
		
		//lecture dans in
		
		//Lecture de la commande
		
		//lecture du joueur qui a cliqu� en  x,y

		
		//En fct de ce que je lis: j'affiche les mines/numeros/fin de partie
		
		
		
	
	
	
	public DataOutputStream getDos(){
		
		return out;
	}
	
public DataInputStream getDis(){
		
		return in;
	}



public void setDis( DataInputStream input) {
	
	this.in=input;
}



	public void sendMessage(String message) {
		 /*Thread sendMessage = new Thread(new Runnable()  
	        { 
	            @Override
	            public void run() { 
	  
	                while (true) { 
	                	String msg=scn.nextLine();
	                */
	                    try { 
	                    	out.writeUTF(message);
	                    } catch (IOException e) { 
	  
	                        e.printStackTrace(); 
	                    } 
	                } 
	            //} 
	       // });
		 
		 
	// sendMessage.start();
	//} 
		 
		 
	public void readMessage() {

		 Thread readMessage = new Thread(new Runnable()  
	        { 
	            
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
	
	
	
	public void closeSocket() {
		
		try {
			
			sock.close();
			connected=false;
			
		}catch(IOException ex) {
			
			System.out.println("Cannot close the socket!");
		}
		
	}
	
	
	public void Connect2Server(String HostField,int PortField,String PseuField) 
	{
	System.out.println("Try to connect to:"+HostField+":"+PortField);
	try {
		
		sock=new Socket(HostField,PortField);
		in=new DataInputStream (sock.getInputStream());
		out= new DataOutputStream (sock.getOutputStream());
		gui.addMsg(" Connexion r�ussie avec : "+HostField+":"+PortField+"\n");
		cmd=IDLE;
		process=new Thread(this);
		process.start();
		connected=true;
	
		//gui.addMsg(" Saisissez votre pseudo s'il vous plait : ""\n");
/*
		
		in= new DataInputStream(sock.getInputStream());
		out=new DataOutputStream(sock.getOutputStream());
		
		
		//client=new User(sock,PseuField,in,out);
		
		process=new Thread(this);
		//if (args.length > 0) // envoi du nom
			// out.writeUTF(args[0]);
			 
			 out.writeUTF("Gros Bill");
			 int numJoueur = in.readInt(); // reception d�un nombre
			 System.out.println("Joueur n�:"+numJoueur);
			 in.close(); // fermeture Stream
			 out.close();
			 sock.close() ; // fermeture Socket 
		//String pseudoJoueur=in.
		//System.out.println("Joueur n�:"+pseudoJoueur); 
*/
		
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
		
		super("D�mineur ISMIN");
		lev=lv;
		Champ champ2= new Champ(name,lv); 
		champ=champ2;
    	champ2.affText();    	    	
	     gui= new IHMHello(this) ;//Renommer IHMHello � GUi
		setContentPane(gui) ;//mettre un panel au milieu		
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
    	new Demineur("Mineur game",l);
    	Thread main =new Thread();
    	main.start();
    	
    //	while(main!=null) {
		//	System.out.println(increment);
		//}
    	
    	
	
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



