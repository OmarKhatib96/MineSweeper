import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Chat extends JFrame implements Runnable, ActionListener{
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread process;
	private Demineur client;
	private JTextPane msgArea;
	private JTextField inputTextField=new JTextField();
	private Color color;
	private JButton sendButton=new JButton("Send");
	private boolean connected=false;



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
		pack();
		setVisible(true) ;	
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
			
			
		}
		
	}
	

	public void addMsg(String str,Color c) {
		//msgArea.replaceSelection(str);	
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
			aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
			aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
			int len = msgArea.getDocument().getLength();
			msgArea.setCaretPosition(len);
			msgArea.setCharacterAttributes(aset, false);
			msgArea.replaceSelection(str);
	}
	
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendButton ) 

		{
			String x = inputTextField.getText();
			if(!x.isEmpty()) {
				
			    try {
			    
			    	DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
					out.writeInt(0);//Pr�venir le serveur qu'on va envoyer un message
					out.writeInt(client.getColorInt());//Envoi de la couleur aussi
				    out.writeUTF(client.getPseudo()+": "+x);
				    inputTextField.setText("");//Reset the field
				     
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
				
			}
		}	 
	 
		

}
}
