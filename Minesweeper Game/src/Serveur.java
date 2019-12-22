
import javax.swing.JPanel;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.GridLayout;
import java.awt.event.* ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.* ; 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.GridBagLayout;








public class Serveur extends JFrame implements Runnable{	
	/**
	 * Matrice de booléens qui précise si une case a déjà été découverte par un client ou pas
	 */
	private boolean [][] caseDiscovered;
	/**
	 * List contenant les datainputstream de tous les clients
	 */
    private List<DataInputStream> listIn = new ArrayList<DataInputStream>();
    /**
     * List contenant les dataoutputstream de tous les clients
     */
    private  List<DataOutputStream> listOut = new ArrayList<DataOutputStream>();
    /**
     * Hashmap contenant les map<Pseudo,DatainputStream> de tous les clients
     */
	private HashMap<String,DataInputStream> hmClientIn=new  HashMap<String,DataInputStream>();
	/**
     * Hashmap contenant les map<Pseudo,DataoutputStream> de tous les clients
     */
	private HashMap<String,DataOutputStream> hmClientOut=new  HashMap<String,DataOutputStream>();
	/**
     * Hashmap contenant les map<Pseudo,Socket> de tous les clients
     */
	private HashMap<String,Socket> hmClientSocket=new HashMap<String,Socket>();
	ServerSocket serversocket ;
	private GuiServeur gui;
	List<Socket> listSocket = new ArrayList<Socket>();
	public    Champ champ=new Champ("Mineur game", new Level(lvl.MEDIUM));//Champ par défaut  
	private int nombreClients=0;	
	private List<String> listPlayers=new ArrayList<String>();

public  Champ getChamp(){

    return champ;
}

public void setChamp(Champ newChamp){
	champ=newChamp;

}

	Serveur(){
		super("Démineur ISMIN Game server");
		System.out.println("Démarrage serveur");
		gui=new GuiServeur(this);
		ImageIcon img=new ImageIcon("new/img/serverLogo.jpg");
		setIconImage(img.getImage());
		setContentPane(gui);
		pack();//Redimentionner la frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
        startServer();
		caseDiscovered=new boolean[champ.getDimensionX()][champ.getDimensionY()];
		champ.affText();
		resetCaseDiscovered();

		
	}

	/**
	 * Permet de réinitialiser la matrice des cases découvertes
	 */
	public void resetCaseDiscovered(){//Réinitialise le tableau des booléens
		caseDiscovered=new boolean[champ.getDimensionX()][champ.getDimensionY()];//On en génère un nouveau
		for(int i=0;i<champ.getDimensionX();i++)
		for(int j=0;j<champ.getDimensionY();j++)
			caseDiscovered[i][j]=false;

	}
	
	public static void main(String[] args) {
		
		new Serveur();
	}

		

	
public List<DataOutputStream>  getListOut(){
	
	return  listOut;
	
}


public List<DataInputStream>  getListIn(){

return  listIn;

}






/**
 * Permet de lancer le serveur et d'attente les éventuels clients
 */
public void startServer() {
	
	try {
	gui.addMsg("Attente des clients...\n");
	serversocket=new  ServerSocket(Demineur.PORT);
	new Thread(this).start();

	
}catch(IOException e) {
	
	e.printStackTrace();
}


}

/**
 * Sert à acceuillir les clients entrants,à coordonner les différentes opérations(envoi des messages, des positions...)
 * 
 */
public void run(){
	
	
	
	try {
	Socket socket =serversocket.accept();	
	Thread t =new Thread(this);
	t.start();//Dans un thread à part 
	this.listSocket.add(socket);//accepte un nouveau client(socket)
	DataInputStream dis = new DataInputStream(socket.getInputStream());
	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	//Add to the collection
	listIn.add(dis);
	listOut.add(dos);
	LocalDateTime now = LocalDateTime.now(); 
    Date date = new Date();  
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	String welcomeMessage=dis.readUTF()+" has just joined the game";//From the IHM
	//Add to the GUI Server that the player x has joined the game
	gui.addMsg(dtf.format(now)+" "+welcomeMessage+"\n");
	String pseudoClient =dis.readUTF();
	hmClientIn.put(pseudoClient, dis);
	hmClientOut.put(pseudoClient, dos);
	hmClientSocket.put(pseudoClient, socket);
	listPlayers.add(pseudoClient);
	nombreClients++;
	System.out.println("Numéro" +nombreClients);

/**
 * Tant que le thread du serveur n'est pas mort
 */
	while(t!=null) {//While the server thread is not  over
		
		int cmd=dis.readInt();

		if(cmd==0) {//Pour envoyer un message
		int couleurClient=dis.readInt();
		String message=dis.readUTF();//receive messages from the IHM
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now1 = LocalDateTime.now(); 
		Date date1 = new Date();  
		gui.addMsg(dtf1.format(now1)+" "+message+"\n");	
		System.out.println(message);
	//To encapsulate	
	for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
			DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
				dos1.writeInt(0);
				dos1.writeInt(couleurClient);
				
				dos1.writeUTF(message+'\n');//Envoyer à tous les autres sauf à nous-même
		
		}
	
		}
		
		if(cmd==1) {//Pour envoyer la position de la case cliquée
			
			int x=dis.readInt();
			int y=dis.readInt();
			String NomJoueur=dis.readUTF();
			int color=dis.readInt();
			System.out.println(x+" "+y+" "+ NomJoueur);
			if(!caseDiscovered[x][y]) {
				caseDiscovered[x][y]=true;

				for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
					
						
							DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
							dos1.writeInt(1);//prévenir les clients qu'on va envoyer un coordonnées
							dos1.writeInt(x);
							dos1.writeInt(y);
							dos1.writeUTF(NomJoueur);
							dos1.writeInt(color);
							
							
						}
					
					
			
			
			}
			
			
		}
		
		
		if(cmd==10)
		{
			 boolean lostPlayer=dis.readBoolean();
			 String playerWhoLost=dis.readUTF();
			 int time=dis.readInt();
			 int score=dis.readInt();
			 int colorClient=dis.readInt();
			 String message;
			 // TODO: Ajouter son score dans le fichier
			 if(lostPlayer)//Si le joueur a perdu
			{
				
				 message="\nThe player "+playerWhoLost+" has lost the game!\n";
				 nombreClients--;

			}

			else {//Si le jouer s'est déconnecté 
				
				 message="\nThe player "+playerWhoLost+" has quit the game!\n";
				 DataInputStream inPlayerToRemove=hmClientIn.get(playerWhoLost);
				 DataOutputStream outPlayerToRemove=hmClientOut.get(playerWhoLost);
				 listIn.remove(inPlayerToRemove);
				 listOut.remove(outPlayerToRemove);
				 listSocket.remove(hmClientSocket.get(playerWhoLost));
				System.out.println("Removal done!\n");
		}

				if(nombreClients==0){
						
				for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
					
					
					DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
					dos1.writeInt(3);
					dos1.writeInt(colorClient);
					
					
				}
					//TODO Afficher le score
				}

				for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
					
					
					DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
					dos1.writeInt(0);
					dos1.writeInt(colorClient);
					dos1.writeUTF(message);
					
					
				}

			 

		}
    	
    
    
	}
    



	}catch (IOException e) {
		e.printStackTrace();
	}
    		    


}

}

