package project2;

import java.math.BigInteger;
//import java.util.Random;
import java.io.*;
import java.util.*;
//import java.sql.*;

public class RSAGenKey {

	int sizeOfPrime;
	BigInteger p, q, n, r, e, d;
	
	//constructor to take the size of key
	public RSAGenKey(int sizeOfPrime) {
		this.sizeOfPrime = sizeOfPrime;
		makePrimes();
		makeKeys();
	}
	
	//constructor to take in p, q, and e as input
	public RSAGenKey(BigInteger p, BigInteger q, BigInteger e) {
		this. p = p; 
		this. q = q;
		this. e = e;

		n = p.multiply(q); // r = p * q
		r = p.subtract(BigInteger.valueOf(1)); //r = (p-1)
		r = r.multiply(q.subtract(BigInteger.valueOf(1))); //(p-1)(q-1)
		
		d = e.modInverse(r);
	}
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Key Size");
		int keySize = input.nextInt();
		input.close();
		
		//run rsaGen key program using constructor that takes key size
		RSAGenKey gk = new RSAGenKey(keySize);
		String pubKey = gk.getE().toString();
		String privKey = gk.getD().toString();
		String n = gk.getN().toString();
		String p = gk.getP().toString();
		String q = gk.getQ().toString();
		
		FileWriter fwpub = new FileWriter("pub_key.txt");
		FileWriter fwpri = new FileWriter("pri_key.txt");
		System.out.println("(e, n): " + pubKey + ", " +  n);
		System.out.println("(d, n): " + privKey + ", " +  n);
		System.out.println("p: " + p + ", q: " +  q);
		
		fwpub.write("e = " + pubKey + "\n" + "n = " + n +  "\n");
		fwpri.write("d = " + privKey + "\n" + "n = " + n +  "\n");
		
		fwpub.close();
		fwpri.close();
	}

	//makes p and q based off sizeOfPrime input 
	public void makePrimes() {

		p = new BigInteger(sizeOfPrime, 10, new Random());

		do {
			q = new BigInteger(sizeOfPrime, 10, new Random());
		} while (q.compareTo(p) == 0);
	}
	
	//makes the public and private keys based off p and q
	public void makeKeys() {
		//r = p * q
		n = p.multiply(q); // r = p * q
		
		//r = (p-1)(q-1)
		r = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
		
		
//		r = r.multiply(q.subtract(BigInteger.valueOf(1))); //(p-1)(q-1)
		
		do {
			e = new BigInteger(2 * sizeOfPrime, new Random());
		} while ((e.compareTo(r) != -1) || (e.gcd(r).compareTo(BigInteger.valueOf(1)) != 0));
		
		d = e.modInverse(r);
	}

	//getters for elements 
	public BigInteger getP() {
		return (p);
	}

	public BigInteger getQ() {
		return (q);
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getE() {
		return (e);
	}

	public BigInteger getD() {
		return (d);
	}
}


