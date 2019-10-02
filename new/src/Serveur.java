

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
	ServerSocket serversocket ;
	private GuiServeur gui;
	List<Socket> listSocket = new ArrayList<Socket>();
	//HashMap<String ,Socket > listClients=new HashMap<String ,Socket >();
	ArrayList<User> listClients=new ArrayList<User>();
	
	Serveur(){
		System.out.println("Démarrage du Serveur");
		 gui=new GuiServeur(this);
		setContentPane(gui);
		pack();//Redimentionner la frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		startServer();
		
	}
	public static void main(String[] args) {
		
		new Serveur();
	}

		
public void AddClient(User client) {
	
	listClients.add(client);
}
	
public ArrayList<User> listClients(){
	
	
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
	
		// serversocket=new  ServerSocket(Demineur.PORT);
		//Lancement d'un thread pour attendre le client
		
			     //String nickname = (new Scanner ( client.getInputStream() )).nextLine();

		//addClient()
		//listSocket.add(socket);
	//(t.isAlive()) {//Boucle infinie
			//Ecouter messages venant des clients
	
			try {
			Socket socket =serversocket.accept();	
			Thread t =new Thread(this);
			t.start();//Dans un thread à part 
			this.listSocket.add(socket);//add the new incoming client

			gui.addMsg(" Nouveau client\n");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			LocalDateTime now = LocalDateTime.now(); 
		    Date date = new Date();  
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			String welcomeMessage=dis.readUTF()+" has just joined the game";//From the IHM
			gui.addMsg(dtf.format(now)+" "+welcomeMessage+"\n");
			while(t!=null) {
			String message=dis.readUTF();//receive messages from the IHM
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now1 = LocalDateTime.now(); 
		    Date date1 = new Date();  
			gui.addMsg(dtf1.format(now1)+" "+message+"\n");
			
            System.out.println(message);
            for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
    			DataOutputStream dos = new DataOutputStream(listSocket.get(client).getOutputStream());
    			if(!listSocket.get(client).equals(socket))
    				dos.writeUTF(message);//Envoyer à tous les autres sauf à nous-même
    			
            }
    			
            	
            
            
			}
           //gui.addMsg(Demin.getPseudo()+": "+message);
		    


            //isAlreadyOpened = true;                     
          //  System.out.println(dis.readUTF());
		//	DataInputStream dis = new DataInputStream(socket.getInputStream()); 
         // DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
			//String pseudo = (new Scanner ( socket.getInputStream() )).nextLine();
		    //User client= new User(socket,dis,dos);
		    //listClients.add(client);


		  /*  
			*/
			}catch (IOException e) {
				e.printStackTrace();
			}
		    		    
		
		
	}

	

	
	
}



/*	
	try {
		
		ServerSocket gestSock=new  ServerSocket(10000);
		Socket socket=gestSock.accept();
		//Ouverture des stream
		DataInputStream entree=new DataInputStream(socket.getInputStream());
		DataOutputStream sortie=new DataOutputStream(socket.getOutputStream());
		//Lecture d'une donnée
		String nomJoueur=entree.readUTF();
		System.out.println(nomJoueur+"connected"); 
		
		//lecture d'une donnée:0 par exemple
		sortie.writeInt(0);
		// un peu de ménage
		sortie.close() ;
		 entree.close() ;
		socket.close();
		gestSock.close() ;
		} catch (IOException e) {e.printStackTrace();} 
		
*/

