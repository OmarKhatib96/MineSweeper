package emse.ismin.demingui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

public class Main extends JFrame {
	Main(){
		JPanel panel = new JPanel();
		
		JLabel lab = new JLabel("coucou");
		panel.add(lab);
		
		JButton butQuit= new JButton ("quit");
		butQuit.setForeground(Color.blue);
		butQuit.setBackground(Color.orange);
		lab.setFont(new Font("Papyrus",Font.ITALIC, 24));
		panel.add(butQuit);
		
		setContentPane(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}