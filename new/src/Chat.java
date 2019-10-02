import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class Chat extends JFrame implements Runnable{
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread process;
	private Demineur client;
	//out=new DataOutputStream(sock.getOutputStream());

	Chat(Demineur client) {
		this.client=client;
		process=new Thread(this);
		process.start();
		this.socket=client.getSocket();
		//sock=new Socket(HostField,PortField);
		try {
			in= new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out=new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		process=new Thread(this);
		process.start();
		
	}
	
	public void quit() {
		
		try {
			this.socket.close();
			this.process=null;//Stop the thread
			this.in.close();//Close in stream
			this.out.close();//Close out stream
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sendMsf(String txt) {
		
		try {
			out.writeUTF(txt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public void run() {
		
		while(this.process!=null)//Boucle infinie
		{
			
			try {
				in.readUTF(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	

}
