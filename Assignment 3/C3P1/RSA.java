package com.dingram;

import java.math.BigInteger;

public class RSA
{
    final BigInteger TWO = new BigInteger("2");
    final BigInteger THREE = new BigInteger("3");
	
    public static void main(String[] args){
    	
    	BigInteger n = new BigInteger("23095675100376460353980581297675223373026833410647478222648288977449481620360427");
    	BigInteger enc = new BigInteger("674472526620593903800497637242400187916753185909");
    	
    	RSA blah = new RSA();
    	
    	System.out.println(blah.attack(enc, n));
    	
    }
    
    public String attack(BigInteger encrypt, BigInteger n){
    	
    	BigInteger temp = encrypt.mod(n);
    	
    	temp = cuberoot(temp);
    	
    	return fromHex(temp.toString());
    }
    
	public BigInteger cuberoot(BigInteger number){
		/*
		 * CubeRoot function taken (and slightly modified) from
		 * http://codereview.stackexchange.com/questions/39197/
		 * performance-of-biginteger-square-root-and-cube-root-functions-in-java 
		 * on 11/12/2014 at 18:27.
		 */
		
		BigInteger guess = number.divide(BigInteger.valueOf((long) number.bitLength() / 3));
        boolean go = true;
        int c = 0;
        BigInteger test = guess;
        while (go) {
            BigInteger numOne = number.divide(guess.multiply(guess));
            BigInteger numTwo = guess.multiply(TWO);
            guess = numOne.add(numTwo).divide(THREE);
            if (numOne.equals(numTwo)) {
                go = false;
            }
            if (guess.mod(TWO).equals(BigInteger.ONE)) {
                guess = guess.add(BigInteger.ONE);
            }
            // System.out.println(guess.toString());
            c++;
            c %= 5;
            if (c == 4 && (test.equals(guess))) {
                return guess.subtract(BigInteger.ONE);
            }
            if (c == 2) {
                test = guess;
            }
        }

        if ((guess.multiply(guess)).equals(number)) {
            return guess;
        }
        return guess.add(BigInteger.ONE);
	}
	
	private String fromHex(String text)
	{
		/*
		 * Converts String from Hex.
		 */
		String temp = "";

		for (int i = 0; i < text.length() - 1; i += 2)
		{
			String subst = text.substring(i, i + 2);
			
			temp += ((char) Integer.parseInt(subst));
		}

		return temp;
	}
}
