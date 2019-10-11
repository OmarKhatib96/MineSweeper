
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
	
	private boolean [][] caseDiscovered;

    private List<DataInputStream> listIn = new ArrayList<DataInputStream>();
    private  List<DataOutputStream> listOut = new ArrayList<DataOutputStream>();
	
	ServerSocket serversocket ;
	private GuiServeur gui;
	List<Socket> listSocket = new ArrayList<Socket>();
	//HashMap<String ,Socket > listClients=new HashMap<String ,Socket >();
	ArrayList<User> listClients=new ArrayList<User>();
	public    Champ champ=new Champ("Mineur game", new Level(lvl.MEDIUM));
    
	private int nombreClients=0;
	
	private List<String> listPlayers=new ArrayList<String>();

public  Champ getChamp(){

    return champ;
}


	Serveur(){
		System.out.println("Démarrage serveur");
		 gui=new GuiServeur(this);
		setContentPane(gui);
		pack();//Redimentionner la frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
        startServer();
		caseDiscovered=new boolean[champ.getDimensionX()][champ.getDimensionY()];
		champ.affText();
		resetCaseDiscovered();

		
	}

	public void resetCaseDiscovered(){
		for(int i=0;i<champ.getDimensionX();i++)
		for(int j=0;j<champ.getDimensionY();j++)
			caseDiscovered[i][j]=false;

	}
	public static void main(String[] args) {
		
		new Serveur();
	}

		
public void AddClient(User client) {
	
	listClients.add(client);
}
	
public List<DataOutputStream>  getListOut(){
	
	return  listOut;
	
}


public List<DataInputStream>  getListIn(){

return  listIn;

}




public  ArrayList<User> listClients(){
	
	
	return listClients;
}

public void startServer() {
	
	try {
	gui.addMsg("Attente des clients");
	 serversocket=new  ServerSocket(Demineur.PORT);

	//List<Socket> listSocket = new ArrayList<Socket>();
	new Thread(this).start();

	
}catch(IOException e) {
	
	e.printStackTrace();
}


}


public void run(){
	
	
	
	try {
	Socket socket =serversocket.accept();	
	Thread t =new Thread(this);
	t.start();//Dans un thread à part 
	this.listSocket.add(socket);//add the new incoming client
	gui.addMsg(" Nouveau client\n");
	DataInputStream dis = new DataInputStream(socket.getInputStream());
	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	//Add to the collection
	//listIn.add(dis);
	//listOut.add(dos);
	LocalDateTime now = LocalDateTime.now(); 
    Date date = new Date();  
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	String welcomeMessage=dis.readUTF()+" has just joined the game";//From the IHM
	//Add to the GUI Server that the player x has joined the game
	gui.addMsg(dtf.format(now)+" "+welcomeMessage+"\n");
	String pseudoClient =dis.readUTF();
	listIn.add(nombreClients,dis);
	listOut.add(nombreClients,dos);

	//dos.writeInt(nombreClients);//Lui envoyer son numéro client
	listPlayers.add(pseudoClient);
	nombreClients++;
	//dos.writeInt(nombreClients);
	//dos.writeInt(nombreClients);


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
			//int numeroJoueur=dis.readInt();
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
							//dos1.writeInt(numeroJoueur);
							//dos1.writeInt(colorClient);
							
						}
					
					
			
			
			}
			
			
		}
		
		if(cmd==2) {
			
			//TODO end serveur et end game
		}
		
		if(cmd==10)
		{
			System.out.println("Got inside the cmd==10");
			 boolean lostPlayer=dis.readBoolean();
			 String playerWhoLost=dis.readUTF();
			 int time=dis.readInt();
			 int score=dis.readInt();
			 int colorClient=dis.readInt();
			 int numeroClient=dis.readInt();
			 String message;
			 // TODO: Ajouter son score dans le fichier
			 if(lostPlayer)//Si le joueur a perdu
			{
				
				 message="The player "+playerWhoLost+" has lost the game!\n";
			}

			else {//Si le jouer s'est déconnecté 
				
				 message="The player "+playerWhoLost+" has quit the game!\n";
				 listIn.remove(numeroClient);
				 listOut.remove(numeroClient);
				 this.listSocket.remove(numeroClient);
		}
				nombreClients--;

				if(nombreClients==0){

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

