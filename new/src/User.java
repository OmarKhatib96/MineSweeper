import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	
	
	private String pseudo;
	
	private Socket socket;
	private String colorUsed;
	private DataInputStream in;
	private DataOutputStream out;
	
	
	
	public  DataInputStream getInputStream() {
		return in;
	}

	
	
	
	public  DataOutputStream getOutputStream() {
		return out;
	}
	
	public String getPseudo() {
		
		return this.pseudo;
		
	}
	
	public String getColor() {
		
		return this.colorUsed;
	}
	
	  public User(Socket client,DataInputStream in,DataOutputStream out) throws IOException {//,DataInputStream in,DataOutputStream out
		    this.out = out;
		    this.in = in;
		    this.socket = client;
		    this.pseudo = pseudo;
		    
		    
		  }
	  
	  
	  public void setPseudo(String pseudo) {
		  
		  this.pseudo=pseudo;
	  }

}



