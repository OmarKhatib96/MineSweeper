import java.util.*;
//import javax.swing.*;
//import java.awt.*;
public class Machine  implements Info {//a class can implement  as many interfaces as it wants

	private int id= 7;
	public void start () {
		
		System.out.println("Machine started");
		
		
	}
	
	public static void main( String[] args) {
		
		Machine mach1=new Machine();
		mach1.start();
		Person person1=new Person("Omar Khatib");
		Info info1 =new Machine();//this is possible
		info1.showInfo();
		
		Info info2=new Person("hh");
		info2.showInfo();
		outputInfo(mach1);
		outputInfo(person1);
		
		
		//java tutorial 1-Common String Methods
		
		String[] words= {"funk","chunck","furry","baconator"};
		//starts with
		for(String w: words) {
			
			if(w.startsWith("fu"))
				System.out.println(w+" starts with fu");
			
				
		}
		
		//ends with
		for(String w: words) {
			
			if(w.endsWith("unk"))
				System.out.println(w+" ends with unk");
			
		}
		
		String s="OmarKhatibOmarKhatibOmarKhatibOmarKhatibOmarKhatib";
		
		System.out.println(s.indexOf('k',5));//search for the first instant for k but ignore the first 5 lettes
		
		
		//Searching for a string:
		
		System.out.println(s.indexOf("rob",10));
		
		String a ="Bacon ";
		String b="monster";
		
		System.out.println(a+b);//Concatenate
		//OR 
		System.out.println(a.concat(b));
		
		System.out.println(a.replace('B','F'));
		
		System.out.println(b.toUpperCase());
		System.out.println(b.trim());//turn the string to no space at all
		
		//Intermediate Java tutorial-4-Introduction to Collection

			String[] things= {"eggs","lasers","hats","pie"};
			
				//To create a list you should tell what type of data you're gonna put in there
			List<String> list =new ArrayList<String>();
		
		for(String x:things)
			list.add(x);//Now our list has 4 items in it
		
		String[] morethings= {"lasers","hats"};
		
		List<String> list2 = new ArrayList<String>();
		
		for(String y:things)
			list2.add(y);
		
		
		//Intermediate Java tutorial-4-ArrayList Programm
		
		editlist(list,list2);
		System.out.println();
		
		

		for(int i=0;i<list.size();i++) {
			
			System.out.printf("%s ", list.get(i));
			
		}
		
		
	
		
	//Intermediate Java Tutorial-6-LinkedList
		String [] Things= {"apples", "noobs", "pwnge", "bacon","goAtS"};
		
		List<String> list1= new  LinkedList<String>();
		
		for(String x :Things)
			list1.add(x);
		
		String[] Things2= {"sausage","bacon","goats","harrypotter"};
		
		List<String> List2=new LinkedList<String>();
		
		for(String y: Things2)
			List2.add(y);
		list1.addAll(List2);
		//Now we don't need List2
		list2=null;//To free some memory
		
		printMe(list1);
		removeStuff(list1,2,5);
		printMe(list1);
		reverseMe(list1);
	
		
		//Intermediate Java Tutorial-8-Converting Lists to Arrays
		
		String[] stuff = {"babies","watermelong","melons","fudge"};
		
		LinkedList<String> thelist=new LinkedList<String>(Arrays.asList(stuff));//Turn the array to list
	
		thelist.add("pumpikinf");
		
		thelist.addFirst("firstthing");
		
		stuff=thelist.toArray(new String[thelist.size()]);//will convet the list to an array
		
		for(String x:stuff) {
			System.out.printf("%s ",x);			
		}
		
		//Intermediate Java Tutorial-9-Collections method sort
		
		String[] crap= {"apples","lemons","geese","bacon","beef","youtuber"};
		List<String> l1=Arrays.asList(crap);
		Collections.sort(l1);//it actually sorts the list itself
		System.out.printf("%s\n", l1);
		
		Collections.sort(l1, Collections.reverseOrder());
		System.out.printf("%s\n",l1);
		
		//Intermediate Java Tutorial-9-Collections method sort
		
		
		//create an array and convert to list
		Character[] ray= {'p','w','n'};
		
		List<Character> l=Arrays.asList(ray);
		
		System.out.println("List is :" );
		
		output(l);
		
		//reverse and print out the list
		
		Collections.reverse(l);//take any collection and reverse it
		
		System.out.println("After reverse : ");
		
		//create a new array and an new list 
		Character[] newRay= new Character[3];
		List<Character> listCopy=Arrays.asList(newRay);
		//Copy contents of list into listcopy
		Collections.copy(listCopy, listCopy);
		
		output(listCopy);
		
		Collections.fill(l,'X');
		
		output(l);

	
		//Intermediate Java Tutorial-12-addAll
		
		//convert stuff array to alist
		
		String[] stuff2= {"apples","beef","corn","ham"};
		List<String> list3=Arrays.asList(stuff2);
		ArrayList<String> list4= new ArrayList<String>();
		
		list4.add("youtube");
		list4.add("google");
		list4.add("Digg");
		
		Collections.addAll(list4, stuff2);//it takes all the elements frim stuff and adds them to stuff
		
		
		//Intermediate Java Tutorial-13-frequency,disjoint
		
		/*System.out.println(Collections.frequency(list2, "digg2"));
		//Disjoint returns true if 2 collections have no shit common at all
		
		boolean tof=Collections.disjoint(list1,list2);
		System.out.println(tof);
		if(tof)
			System.out.println("These lists do not have anything in common");
		else
			System.out.println("These lists do  have something in common");

		//Intermediate Java Tutorial-14-Stack,push,pop
		
		Stack<String> stack=new Stack<String>();
		stack.push("bottom");
		printStack(stack);
		
		stack.push("Second");
		printStack(stack);
		stack.push("third");
		printStack(stack);
		
		stack.pop();
		printStack(stack);
		stack.pop();
		printStack(stack);
		stack.pop();
		printStack(stack);
		*/
		//Intermediate Java tutorial-15-Queue
		PriorityQueue<String> q= new PriorityQueue<String>();
		//The first thing we're going to pass in , it's gonna be the first priority
		
		q.offer("first");
		q.offer("second");
		q.offer("third");
		System.out.printf("%s",q);
		System.out.printf("%s ", q.peek());//this method is going to look into the element with the highest priority
		q.poll();
		
		System.out.printf("%s ", q);
		
		//Intermediate Java tutorial-16-HashSet
		//A hashset is a collection without any duplicated item
		String[]  things4= {"apple","bob","ham","bob","bacon"};
		List<String> list5=Arrays.asList(things4);
		
		System.out.printf("%s ", list);
		System.out.println();
		Set<String> set=new HashSet<String>(list);
		System.out.printf("%s ", set);
		
		//intermediate Java Tutoroal-17- Generic Methods
		Integer[] array= {1,2,3,4};
		Character[] cray= {'o','m','a','r'};
		
		//intermediate Java Tutoroal-17- Generic Methods
		
		printMe(cray);
		printMe(array);
		//intermediate Java Tutoroal-18- Generic Methods

			System.out.println(max(23,42,1));
			System.out.println(max("apples","tots","chicken"));
			
	  	//intermediate Java Tutoroal-18- Introduction to Applets
			

	    
		
		
		
	}

	
	public static <T extends Comparable<T>> T max (T a , T b, T c)//only objects that inherits from the class class Comparable can be used in this method
	{//<T extends Comparable<T>> can return generic data
		T m= a;
		
		if(b.compareTo(a)>0)
			m=b;
		if(c.compareTo(m)>0)
			m=c;
		
		return m;
			
		
		
	}
	public static <T> void printMe (T[] x) {
		
		for(T b :x)
			System.out.printf("%s ", b);
		System.out.println();
		
	}
	
	public static void printMe(Integer[] i) {
		for(Integer x:i)
			System.out.printf("%s", x);
		
		System.out.println();
	}
	
	public static void printMe(Character[] i) {
		
		for(Character x:i)
			System.out.printf("%s ",x);
		
		System.out.println();
		
	}
	
	
	
	
	private static void printStack(Stack<String> s) {
		
		if(s.isEmpty())
			System.out.println("You have nothing in our stack");
		
		else
			System.out.printf("%s TOP\n",s);
		

			
	}
	
	private static void output(List<Character> thelist) {
		
		for(Character thing: thelist)
			System.out.printf("%s ", thing);
		
			
		
	}
	
	//printMe method
	
	private static void printMe(List<String> l) {
		for(String b:l)
			System.out.printf("%s", b);
		System.out.println();
		
		
	}
	
	//RemoveStuff from LinkedList method
	
	private static void removeStuff(List<String>l , int from, int to) {
		l.subList(from, to).clear();//It's gonna return a portion from the list and delete it
		
	}
	
	
	
	
	
	 public static void editlist(List<String> l1, List<String> l2)
	{
		Iterator<String> it = l1.iterator();
		while(it.hasNext()) {
			if(l2.contains(it.next()))
				it.remove();
								
		}
	}
	
	
	
	//Intermediate Java tutorial-3-Recursion 
			public static long fact(long n) {
				if(n<=1)
					return 1;
				else
					return n*fact(n-1);
				
			}
	;
	public void showInfo() {
		
		System.out.println("Machine interface ");
		
	}

	private static void outputInfo(Info in)
	{
		
		in.showInfo();
	}
	
	//reverseMe
	private static  void reverseMe(List<String> l) {
		ListIterator<String> boby= l.listIterator(l.size());
		while(boby.hasPrevious())
			
			
			System.out.printf("%s", boby.previous());
			
		
		
	}
	
}
