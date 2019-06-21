package com.dingram;

import java.math.BigInteger;

public class LinearEquations
{
	//Euclid euclid = new Euclid();
	
	public static void main(String[] args){
		LinearEquations l = new LinearEquations();
		
		BigInteger a = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768434237897634345765879087764242354365767869780876543424");
		BigInteger b = new BigInteger("45292384209127917243621242398573220935835723464332452353464376432246757234546765745246457656354765878442547568543334677652352657235");
		BigInteger n = new BigInteger("643808006803554439230129854961492699151386107534013432918073439524138264842370630061369715394739134090922937332590384720397133335969549256322620979036686633213903952966175107096769180017646161851573147596390153");	
		BigInteger ans = l.solve(a, b, n);
		
		System.out.println(ans);
	}
	
	public BigInteger solve(BigInteger a, BigInteger b, BigInteger n){
		
		BigInteger inv = inverse(a, n);
		try{
			return ((BigInteger.ZERO).subtract(b)).multiply(inv).mod(n);
		}catch(NullPointerException e){
			return null;
		}
	}
	
	private BigInteger inverse(BigInteger x, BigInteger n){
		
		Euclid e = new Euclid();
		BigInteger[] ext = e.gcd(x, n);
		
		if(ext[0].equals(BigInteger.ONE)){
			return ext[1].modInverse(n);
		}
		else{
			return null;
		}
	}
}
