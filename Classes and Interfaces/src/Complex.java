
public final class Complex {//Exemple of immutale complex number class
	
	private final double re;
	private final  double im;
	private static final Complex ZERO=new Complex(0,0);
	private static final Complex ONE=new Complex(1,0);
	private static final Complex I =new Complex(0,1);
	
	//To guarantee immutability the class should not permit itself to be subclassed. This can be done by making the class final,
		//but there is another, more flexible alternative. Instead of making an immutable class final, you can make
		//all of its constructors private or package private and add public static factories in place ofthe public constructors
		
	
	private  Complex(double re, double im) {
		this.re=re;
		this.im=im;
		
	}
	
	
	public static Complex valueOf(double re, double im) {
		
		return new Complex(re,im);
	}
	
	public double realPart() { return re;}
	public double imaginaryPart() {	return im;}
	
	
	
	public Complex plus(Complex c)
	{
		
		return new Complex(re+c.re, im+c.im); 
	}
	
	public Complex minus(Complex c) {
		return new Complex(re-c.re,im-c.im);
		
		
	}
	
	public Complex times(Complex c) {
		
		return new Complex(re*c.re-im*c.im,re*c.im+im*c.re);
	}
	
	public Complex dividedBy(Complex c) {
		
		double tmp=c.re*c.re+c.im*c.im;
		return new Complex((re*c.re+im*c.im)/tmp,(im*c.re-re*c.im)/tmp);
	}
	
	public boolean equals(Object o) {
		if(o==this)
			return true;
		if(!(o instanceof Complex))
			return false;
		Complex c=(Complex) o;
		return Double.compare(c.re, re)==0 && Double.compare(c.im, im)==0;
		
	}
	
	public int hashCode() {
		
		return 31*Double.hashCode(re)+Double.hashCode(im);
	}
	public String toString() {
		return "("+re+"+"+im+"i)";
	}
	
}

public interface Songwriter
{
	
	
}
}
