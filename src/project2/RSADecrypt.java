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

public class RSADecrypt {

	public static void main(String[] args) throws IOException {
		String file = "test.enc";
		
		//breader to read in passage from file
		BufferedReader breader = new BufferedReader(new FileReader(file));
		StringBuilder sbuilder = new StringBuilder();
		String currLine = breader.readLine();
		
		//holds blocks of text (lines) from test.enc file
		List<String> blocks = new ArrayList<String>();

		//read in lines from test.enc file
		//store them in blocks list
		while (currLine != null) {
			sbuilder.append(currLine + "\n");
			blocks.add(currLine);
			currLine = breader.readLine();
		}
		breader.close();
		
		//holds lines of private key file to get e and n 
		List<String> elements = new ArrayList<String>();
		
		String pri_file = "pri_key.txt";
		
		BufferedReader pri_reader = new BufferedReader(new FileReader(pri_file));
		StringBuilder keyBuilder = new StringBuilder();
		String pubcurrentLine = pri_reader.readLine();
		
		//read in each line from pri_key.txt and add to elements list
		while(pubcurrentLine != null) {
			keyBuilder.append(pubcurrentLine);
			keyBuilder.append("\n");
			elements.add(pubcurrentLine);
			pubcurrentLine = pri_reader.readLine();
		}
		pri_reader.close();
		
		System.out.println("elements of pri_key.txt");
		
		BigInteger d = BigInteger.valueOf(Long.parseLong(elements.get(0).substring(4)));
		System.out.println("d: " + d.toString());

		BigInteger n = BigInteger.valueOf(Long.parseLong(elements.get(1).substring(4)));
		System.out.println("n: " + n.toString());
		System.out.println("\n");

		BufferedWriter priWriter = new BufferedWriter(new FileWriter("test.dec"));	

		BigInteger cipher;
		BigInteger plain;
		List<String> decryptedBlocks = new ArrayList<String>();

		//add decrypted blocks to decryptedBlocks list
		for(int i = 0; i < blocks.size(); i++) {
			cipher = new BigInteger(blocks.get(i));
			plain = cipher.modPow(d, n);
			decryptedBlocks.add(addZeros(plain.toString()));
			priWriter.write(decryptedBlocks.get(i) + "\n");
		}
		priWriter.close();
			
		System.out.println("encrypted blocks:");
		System.out.println(blocks);
		System.out.println("\n");

		System.out.println("decrypted blocks:");
		System.out.println(decryptedBlocks);
		System.out.println("\n");
		
		System.out.println("decrypted message:");
		System.out.println(decryptedEnglish(decryptedBlocks));
	}
	
	//adds "0" to beginning of the string if its required
	//ie if length is less than 6
	public static String addZeros(String input) {
		while(input.length() < 6) {
			input = "0" + input;
		}
		return input;
	}

	public static String decryptedEnglish(List<String> decBlocks) {
		char alph[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};
		String englishVersion = "";

		for (int i = 0; i < decBlocks.size(); i++) {
			int letter = Integer.parseInt(decBlocks.get(i).substring(0,2));
			englishVersion += alph[letter];
			
			letter = Integer.parseInt(decBlocks.get(i).substring(2,4));
			englishVersion += alph[letter];

			letter = Integer.parseInt(decBlocks.get(i).substring(4,6));
			englishVersion += alph[letter];
			
		}
		return englishVersion;
		
	}
}
