import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {

private Object[] elements;
private int size=0;
private static final int DEFAULT_INITIAL_CAPACITY=16;


public Stack() {
	elements=new Object[DEFAULT_INITIAL_CAPACITY];//creation of an array of size 16
	
}

public void push(Object e)
{
	ensureCapacity();
	elements[size]=null;//Eliminate obsolete reference
	elements[size++]=e;
}
public Object pop() {
	
	if(size==0)
		throw new EmptyStackException();
	//Memory leak fixed:
	return elements[--size];
}

/** Ensure space for at least one more element, roughly doubling the capacity each time the array needs to grow
 * 
 * 
 */
	private void ensureCapacity()
	{
		if(elements.length==size)
			elements=Arrays.copyOf(elements,2*size+1);
	}
}
