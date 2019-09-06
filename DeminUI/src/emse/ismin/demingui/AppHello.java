import java.awt.event.* ;
import javax.swing.* ;
public class AppHello extends JFrame {
/** ze main */
 public static void main (String [] args) {
new AppHello() ;
 }
/** Quit the application */
public void quit() {
System.out.println("Bye-Bye ");
System.exit(0) ;
 }
}