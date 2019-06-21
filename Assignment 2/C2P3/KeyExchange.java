package com.dingram;

import java.math.BigInteger;
import java.util.Random;

public class KeyExchange
{
	BigInteger modulus;
	BigInteger gen;
	BigInteger pow;
	public KeyExchange(BigInteger secret){
		pow = secret;
		Random rnd = new Random();
		
		//Generates a new modulus of 1024 bits, with a certainty of it being prime.
		modulus = new BigInteger(1024, 1, rnd);
		
		gen = BigInteger.valueOf(5);
	}

	/*public KeyExchange(BigInteger mod, BigInteger generator, BigInteger power){
		modulus = mod;
		gen = generator;
		pow = power;
	}*/
	
	public static void main(String[] args){
		
		KeyExchange alice = new KeyExchange(BigInteger.valueOf(25));
		
		KeyExchange bob = new KeyExchange(BigInteger.valueOf(13));
		
		// Generate first message (Alice -> Bob)
		BigInteger[] stateA = alice.ComputeMessageAtoB();
		
		// Use first message to calculate return message (Bob -> Alice)
		BigInteger stateB = bob.ComputeMessageBtoA(stateA);
		
		// Compute Alice's key.
		BigInteger keyA = alice.ComputeKeyA(stateB);
		
		// Compute Bob's key.
		BigInteger keyB = bob.ComputeKeyB(stateA);
		
		System.out.println(stateA[0] + ", " + stateA[1] + ", " + stateA[2]);
		System.out.println(stateB);
		
		System.out.println(keyA + ", " + keyB);
		
		/*
		 *  From this point we pretend that Eve has intercepted the message from Alice to Bob
		 *  and go from there.
		 *  
		 *  When each character is created in this program they generate their own random modulus.
		 *  
		 *  As this is an attack I will only show what the attacker generates (Thus, not Alice or Bob's
		 *  keys)
		 */
		
		KeyExchange eve = new KeyExchange(BigInteger.valueOf(29));
		
		// Eve generates her own message back to Alice.
		BigInteger stateE2A = eve.ComputeMessageBtoA(stateA);
		
		// Generates her own key for communication with Alice.
		BigInteger keyE2A = eve.ComputeKeyB(stateA);
		
		// Eve sends her key exchange request to Bob. 
		BigInteger[] stateE2B = eve.ComputeMessageAtoB();
		
		// Eve receives Bob's reply
		BigInteger msgB2E = bob.ComputeMessageBtoA(stateE2B);
		
		// Eve calculates her key for communication with Bob.
		BigInteger keyE2B = eve.ComputeKeyA(msgB2E);
		
		System.out.println("Alice's message: \n" + stateA[0] + ",\n" + stateA[1] + ",\n" + stateA[2]);
		System.out.println("Eve's reply: " + stateE2A);
		System.out.println("Eve's Alice Key: " + keyE2A);
		System.out.println("Eve's message to Bob: \n" + stateE2B[0] + ",\n" + stateE2B[1] + ",\n" + stateE2B[2]);
		System.out.println("Bob's reply: " + msgB2E);
		System.out.println("Eve's Bob Key: " + keyE2B);
		
	}
	
	public BigInteger[] ComputeMessageAtoB(){
		
		BigInteger[] message = {modulus, gen, raiseTo(gen, pow).mod(modulus)};
		
		return message;
	}
	
	public BigInteger ComputeMessageBtoA(BigInteger[] message){
				
		return raiseTo(message[1], pow).mod(message[0]);
		
	}
	
	public BigInteger ComputeKeyA(BigInteger message){
		
		return raiseTo(message, pow).mod(modulus);
		
	}
	
	public BigInteger ComputeKeyB(BigInteger[] message){
		
		return raiseTo(message[2], pow).mod(message[0]);
		
	}
	
	private BigInteger raiseTo(BigInteger base, BigInteger power){
		
		if(power.equals(BigInteger.ZERO)){
			return BigInteger.ONE;
		}
		else{
			return base.multiply(raiseTo(base, power.subtract(BigInteger.ONE)));
		}
		
	}
	
}