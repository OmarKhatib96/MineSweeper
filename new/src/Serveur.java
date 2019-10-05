
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
	private static Champ champ=	new Champ("Mineur game", new Level(lvl.EASY));
	
	Serveur(){
		System.out.println("Démarrage serveur");
		 gui=new GuiServeur(this);
		setContentPane(gui);
		pack();//Redimentionner la frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		startServer();
		caseDiscovered=new boolean[champ.getDimensionX()][champ.getDimensionY()];
		
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
	listIn.add(dis);
	listOut.add(dos);
	//Get local time
	LocalDateTime now = LocalDateTime.now(); 
    Date date = new Date();  
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	String welcomeMessage=dis.readUTF()+" has just joined the game";//From the IHM
	//Add to the GUI Server that the player x has joined the game
	gui.addMsg(dtf.format(now)+" "+welcomeMessage+"\n");

	while(t!=null) {//While the server thread is not  over
		
		int cmd=dis.readInt();

		
		if(cmd==0) {
		String message=dis.readUTF();//receive messages from the IHM
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now1 = LocalDateTime.now(); 
		Date date1 = new Date();  
		gui.addMsg(dtf1.format(now1)+" "+message+"\n");
	
		System.out.println(message);
		
	for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
			DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
				dos1.writeInt(0);
			//if(!listSocket.get(client).equals(socket))
				dos1.writeUTF(message);//Envoyer à tous les autres sauf à nous-même
		
		}
	
		}
		
		if(cmd==1) {
			
			int x=dis.readInt();
			int y=dis.readInt();
			String NomJoueur=dis.readUTF();
			System.out.println(x+" "+y+" "+ NomJoueur);
			//Passer aussi les identifiants
			for(int client=0;client<this.listSocket.size();client++) {//Diffuser aux autre clients
				
					if(!caseDiscovered[x][y]) {
						caseDiscovered[x][y]=true;
						DataOutputStream dos1 = new DataOutputStream(listSocket.get(client).getOutputStream());
						dos.writeInt(1);//prévenir les clients qu'on va envoyer un coordonnées
						dos1.writeInt(x);
						dos1.writeInt(y);
						dos1.writeUTF(NomJoueur);
						
					}
					
					
				//if(!listSocket.get(client).equals(socket))
					//dos1.writeUTF(message);//Envoyer à tous les autres sauf à nous-même
			
			}
			
			
		}
		
		if(cmd==2) {
			
			//TODO end serveur et end game
		}
		
    	
    
    
	}
   //gui.addMsg(Demin.getPseudo()+": "+message);
    



	}catch (IOException e) {
		e.printStackTrace();
	}
    		    


}

}

