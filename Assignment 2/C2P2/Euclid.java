package com.dingram;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Euclid
{
	
	public static void main(String[] args){
		Euclid test = new Euclid();
		
		BigInteger x = new BigInteger("1572855870797393");
		BigInteger y = new BigInteger("630065648824575");
		
		BigInteger[] result = test.gcd(x, y);
	
		System.out.println(result[0] + ", " + result[1] + ", " + result[2]);
		
		test.toFile(x, y, "answer");
	}
		
	public void toFile(BigInteger x, BigInteger y, String fname){
		/**
		 * This method calculates the Euclidian Algorithm and stores the results
		 * d, a and b to a text-file with the passed file name.
		 * @param x
		 * @param y 
		 * @param fname - A string which contains the name of the file you want to write to.
		 * 						WARNING: (fname).txt WILL be overwritten.
		 */
		BigInteger[] array = gcd(x, y);
		
		String text = toString(array);
		
		try
		{
			PrintWriter out = new PrintWriter(fname + ".txt");

			out.println(text);

			out.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public BigInteger[] gcd(BigInteger x, BigInteger y){
		/**
		 * A method to calculate the GCD and compute the Extended Euclidian Algorithm.
		 * @param x
		 * @param y
		 * @return BigIntegers d, a and b as a Big Integer array output
		 * (index 0 is d, 1 is a and 2 is b)
		 */
		if (y.equals(BigInteger.ZERO)){
			// In case of divide by zero.
			return new BigInteger[] {x, BigInteger.ONE, BigInteger.ZERO};
		}
		
		BigInteger[] returnable = gcd(y, x.mod(y));
		BigInteger d = returnable[0];
		BigInteger a = returnable[2];
		BigInteger b = returnable[1].subtract((x.divide(y)).multiply(returnable[2]));
		
		return new BigInteger[] {d, a, b};
		
	}
	
	private String toString(BigInteger[] array){
		
		return(array[0].toString() + "\n" + array[1].toString() + "\n" + array[2].toString());
		
	}
	
	
	/**
	 * This was the algorithm which was listed in Cryptography: Theory and Practice (Third Edition)
	 * by Douglas R. Stinson (p166). For some reason it would calculate r and s correctly, but t
	 * was incorrect.
	 * @param x
	 * @param y
	 * @return BigIntegers r, s and t as an output
	 */
	
	/*public void extended(BigInteger x, BigInteger y){
		BigInteger a = x;
		BigInteger b = y;		
		BigInteger q = (a.divide(b)).multiply(b);
		BigInteger r = x.subtract(q);
		BigInteger t0 = BigInteger.ZERO;
		BigInteger t = BigInteger.ONE;
		BigInteger s0 = BigInteger.ONE;
		BigInteger s = BigInteger.ZERO;
		BigInteger temp;
		
		
		while( r.compareTo(BigInteger.ZERO) > 0 ){
			temp = (t0.subtract(q.multiply(t)));
			t0 = t;
			t = temp;
			temp = s0.subtract(q.multiply(s));
			s0 = s;
			s = temp;
			a = b;
			b = r;
			q = a.divide(b);
			r = a.subtract(q.multiply(b));
		}
		r = b;
		
		System.out.println(r + ", " + s + ", " + t);
		System.out.println(gcd(x, y));
	}*/
	
	//private BigInteger gcd(BigInteger x, BigInteger y){
		/**
		 * Returns the Greatest Common Divisor of BigIntegers x and y.
		 * This is a recursive method.
		 * 
		 * @param x a BigInteger
		 * @param y a BigInteger
		 * @return BigInteger of Greatest Common Divisor of x and y
		 */
/*		if ( y.equals(BigInteger.ZERO) )
		{
			return x;
		}
		else if (x.compareTo(y) >= 0 && y.compareTo(BigInteger.ZERO) > 0)
		{
			return gcd( y, x.mod(y) );
		}
		else
		{
			return gcd(y, x);
		}
		
	}*/
}
