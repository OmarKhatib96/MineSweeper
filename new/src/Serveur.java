

import javax.swing.JPanel;
import java.net.*;
import java.io.*;
import java.util.*;
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


public class Serveur extends JFrame implements Runnable{
	ServerSocket serversocket ;
	private GuiServeur gui;
	List<Socket> listSocket = new ArrayList<Socket>();

	
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

		

public void startServer() {
	
	try {
	gui.addMsg("Attente des clients");
	 serversocket=new  ServerSocket(Demineur.PORT);

	//List<Socket> listSocket = new ArrayList<Socket>();
	new Thread(this).start();

/*try {
		
		ServerSocket serversocket=new  ServerSocket(Demineur.PORT);
		//Lancement d'un thread pour attendre le client
		Socket socket =serversocket.accept();
		gui.addMsg(" Nouveau client");
//		listSocket.add(socket);
		
*/
	
}catch(IOException e) {
	
	e.printStackTrace();
}


}



public void run(){
	try {
		// serversocket=new  ServerSocket(Demineur.PORT);
		//Lancement d'un thread pour attendre le client
		Socket socket =serversocket.accept();
		new Thread(this).start();
		gui.addMsg(" Nouveau client\n");
		listSocket.add(socket);
	
	//ouverture in and out
	
	//STOCKAGE DANS UNE COLLECTION
	
	//boucle infinie d'attente
	
	
	//redispatch aux autres si nécessaire
	
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

