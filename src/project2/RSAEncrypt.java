package project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAEncrypt {
	public static void main(String[] args) throws IOException {
		//for 00-26 scheme 26 for 'space'
		char alph[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};
		

		String file = "test.txt";
		
		//breader to read in passage from file
		BufferedReader breader = new BufferedReader(new FileReader(file));
		StringBuilder sbuilder = new StringBuilder();
		String currLine = breader.readLine();
		
		while (currLine != null) {
			sbuilder.append(currLine);
			sbuilder.append("\n");
			currLine = breader.readLine();
		}
		breader.close();
		
		//turn passage into string
		String wholePassage = sbuilder.toString();
		System.out.println("raw text");
		System.out.println(wholePassage + "\n");

		
		//"clean" passage so we can break it up into bits of 3
		// removes ".", ",", and "\n"
		String cleanPassage = wholePassage.replace(".", "").replace(",", "").replace("\n", "").toLowerCase();
		StringBuilder cleanBuilder = new StringBuilder(cleanPassage);
		
		//add spaces to make it divisible by 3 if not already
		while((cleanBuilder.length() % 3) != 0) {
			cleanBuilder.append(" ");
		}
		
		//print clean final version of text
		String finalPassage = cleanBuilder.toString();
		System.out.println("clean final version of text:");
		System.out.println(finalPassage + "\n");

		//hold blocks of converted bits ie "this is..." block one will be 190708
		List<String> blocks = new ArrayList<String>();

		//convert blocks of three letters to numbers a=00 " " = 26
		for (int i = 0; i < finalPassage.length(); i = i + 3) {
			StringBuilder sb = new StringBuilder();
			for(int j = 0; j < alph.length; j++) {
				if(finalPassage.charAt(i) == (alph[j])) {
					if(String.valueOf(j).length() != 2) {
						sb.append(0);
						sb.append(j);
					} else {
						sb.append(j);
					}
				}
			}
			
			for(int j = 0; j < alph.length; j++) {
				if(finalPassage.charAt(i+1) == (alph[j])) {
					if(String.valueOf(j).length() !=2) {
						sb.append(0);
						sb.append(j);
					} else {
						sb.append(j);
					}
				}
			}

			for(int j = 0; j < alph.length; j++) {
				if(finalPassage.charAt(i+2) == (alph[j])) {
					if(String.valueOf(j).length() !=2) {
						sb.append(0);
						sb.append(j);
					} else {
						sb.append(j);
					}
				}
			}
			
			blocks.add(sb.toString());
		}
		
		//print blocks in number format ready to be encrypted
		System.out.println("blocks ready to be encrypted");
		System.out.println(blocks.toString() + "\n");

		//holds the lines of public key file to extract e and n
		List<String> elements = new ArrayList<String>();
		
		String pub_file = "pub_key.txt";
		
		BufferedReader pub_reader = new BufferedReader(new FileReader(pub_file));
		StringBuilder keyBuilder = new StringBuilder();
		String pubcurrentLine = pub_reader.readLine();
		
		//add line of pub_key.txt to elements list
		while(pubcurrentLine != null) {
			keyBuilder.append(pubcurrentLine + "\n");
			elements.add(pubcurrentLine);
			pubcurrentLine = pub_reader.readLine();
		}
		
		pub_reader.close();
		
		System.out.println("elements of pub_key.txt");
		
		//turn e from file to usable variable
		Long eFromFile = Long.parseLong(elements.get(0).substring(4));
		BigInteger e = BigInteger.valueOf(eFromFile);
		System.out.println("e: " + e.toString());

		//get n from file like e above
		Long nFromFile = Long.parseLong(elements.get(1).substring(4));
		BigInteger n = BigInteger.valueOf(nFromFile);
		System.out.println("n: " + n.toString());

		BufferedWriter priWriter = new BufferedWriter(new FileWriter("test.enc"));	

		BigInteger plain;
		BigInteger cipher;

		//actually write to the encrypted file
		for(int i = 0; i < blocks.size(); i++) {
			plain = new BigInteger(blocks.get(i));
			cipher = plain.modPow(e, n);
			
			priWriter.write(cipher.toString() + "\n");
		}
		
		priWriter.close();
		
		System.out.println("\n");
		System.out.println("encrypted text written to test.enc");
	}
}

