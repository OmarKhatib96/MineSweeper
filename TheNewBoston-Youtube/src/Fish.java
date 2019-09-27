import java.awt.*;
import javax.swing.*;

public class Fish extends JApplet{

	public static void main(String[] args) {
		
		DogList DLO= new DogList();
		Dog d= new Dog();
		DLO.add(d);
		
		
		
		
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawString("wow this actually worked?",25,25);
		
	}
}
