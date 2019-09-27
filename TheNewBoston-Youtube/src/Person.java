
public class Person implements Info {

	private String  name= "Omar Khatib";
	public void greet () {
		
		System.out.println("Hello there");
		
		
	}
	
	public Person(String name) {
		
		this.name=name;
	}
	
	public void showInfo() {
		
		System.out.println("Person interface ");

	}
	
	
	
}
