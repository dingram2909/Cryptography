package com.dingram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Vigenere
{
	/**
	 * @author Daniel Ingram
	 */
	
	final int ASCII = 97;

	public static void main(String[] args) throws IOException
	{
		/*
		 * A method used for testing purposes only.
		 */
		
		Vigenere v = new Vigenere();
						
		String text = v.encrypt("ncl", "newcastleuniversity");
		
		System.out.println(text);
		
		text = v.decrypt("ncl", text);
		
		System.out.println(text);
		
		String key = v.analysis("ciphertext.txt");
		
		System.out.println(key);
		
		text = v.decrypt(key, v.readFile("ciphertext.txt"));
		
		System.out.println(text);
	}

	
	public String encrypt(String key, String text)
	{
		/*
		 * Encrypts a text with a given key.
		 */
		text = remove(text);
		
		int kl = key.length();
		String substr;
		String temp = "";
		for (int i = 0; i < text.length(); i = i + kl)
		{			
			if((i + kl) > text.length())
			{
				substr = text.substring(i, text.length());
			}
			else
			{
				substr = text.substring(i, i + kl);
			}
			
			temp = temp + swap(substr, key, true);
		}
		
		return temp;
	}
	
	public String decrypt(String key, String text)
	{
		/*
		 * Decrypts a text with the given key.
		 */
		text = remove(text);
		
		int kl = key.length();
		String substr;
		String temp = "";
		for (int i = 0; i < text.length(); i = i + kl)
		{
			
			if((i + kl) > text.length())
			{
				substr = text.substring(i, text.length());
			}
			else
			{
				substr = text.substring(i, i + kl);
			}
			
			temp = temp + swap(substr, key, false);
		}
		
		return temp;
	}
	
	private String swap (String text, String key, boolean encrypt)
	{
		/*
		 * Shifts the individual letters by the number dictated by the key.
		 * Works for both Encrypt and Decrypt.
		 */
		
		/**
		 * WARNING - METHOD NAME CLASHES WITH ANOTHER METHOD!
		 */
		
		char[] txtArray = text.toCharArray();
		char[] keyArray = key.toCharArray();
		
		if (encrypt)
		{
			for (int i = 0; i < txtArray.length; i++)
			{
				int shift = (keyArray[i] - ASCII);
				if (txtArray[i] + shift > 'z')
				{
					txtArray[i] = (char) (txtArray[i] - (26 - shift));
				}
				else
				{
					txtArray[i] = (char) (txtArray[i] + shift);
				}
			}
		}
		else
		{			
			for (int i = 0; i < txtArray.length; i++)
			{
				if (Character.isLetter(txtArray[i]))
				{
					int shift = (26 - (keyArray[i] - ASCII));
					if (txtArray[i] + shift > 'z')
					{
						txtArray[i] = (char) (txtArray[i] - (26 - shift));
					}
					else
					{
						txtArray[i] = (char) (txtArray[i] + shift);
					}
				}
			}
		}
	
		String returnable = new String(txtArray);
	
		return returnable;
	}
	

	
	private int gcd (int x, int y)
	{
		/*
		 * Returns the Greatest Common Denominator for the values entered.
		 */
		if ( y == 0 )
		{
			return x;
		}
		else if (x >= y && y > 0)
		{
			return gcd( y, x % y );
		}
		else
		{
			return gcd(y, x);
		}
	}
	
	private String analysis (String file) throws IOException
	{
		/*
		 * Analyses the file and returns the key.
		 */
		char[] text = charArray(readFile(file));
		
		ArrayList<Integer> location = coinc(text);
		
		int kl = keyLength(location);
		
		String[] broken = brDwn(text, kl);
				
		return getKey(broken);
	}
	
	private String getKey(String[] array) throws IOException
	{
		/*
		 * Creates the key from individual letters.
		 */
		String key = "";
		for (int i = 0; i < array.length; i++)
		{
			key += getLetter(array[i], i);
		}
		
		return key;
	}
	
	private char getLetter(String text, int i) throws IOException
	{
		/*
		 * Returns the individual letters of the key for the location i.
		 */
		char[] array = text.toCharArray();
		
		int letter = shift(array);
		
		return (char) (letter + ASCII);
	}
	
	private String[] brDwn(char[] text, int key)
	{
		/*
		 * Breaks the text down into substrings of key-size.
		 * This then creates arrays where the array id is the location
		 * in the substring.
		 * 
		 * THIS WAS HARD :'(
		 */
		String[] array = new String[key];
		int loc;
		
		for (int i = 0; i < array.length; i++)
		{
			array[i] = "";
		}
		
		for (int i = 0; i < text.length; i++)
		{
			loc = assign(i, key);
			array[loc] = array[loc] + text[i];
		}
		
		return array;
	}

	private int assign(int loc, int key)
	{
		/*
		 * Allows for String arrays to be constructed with the letters in the right places.
		 */
		int temp = ((loc + 1) % key);
		
		if (temp == 0)
		{
			return key - 1;
		}
		else
		{
			return temp - 1;
		}
	}
	
	private int keyLength (ArrayList<Integer> locations)
	{
		/*
		 * Finds the length of the key through GCD.
		 */
		int temp = gcd(locations.get(0),locations.get(1));
		for (int i = 1; i < (locations.size() - 1); i++)
		{
			temp = gcd(temp, locations.get(i));
		}
		
		return temp;
	}
	
	private ArrayList<Integer> coinc(char[] text)
	{
		/*
		 * Starter method to find the coincidental frequency of patterns. 
		 */
		char one;
		char two;
		char three;
		int i = 0;
		ArrayList<Integer> location = new ArrayList<Integer>();
		do
		{
			one = text[i];
			two = text[i+1];
			three = text[i+2];
			
			location = runThru(text, one, two, three, i);
			
			i++;
		}while(location.size() < 3);
		
		return location;
	}
	
	private ArrayList<Integer> runThru(char[] text, char one, char two, char three, int first)
	{
		/*
		 * Runs through the array to check for pattern frequencies.
		 */
		ArrayList<Integer> location = new ArrayList<Integer>();
		for (int i = 0; i < (text.length - 2); i++)
		{
			if (text[i] == one)
			{
				if (text[i + 1] == two)
				{
					if (text[i + 2] == three)
					{
						location.add((i) - first);
					}
				}
			}
		}
		
		return location;
	}
	
	/*
	 * METHODS COPIED MODIFIED FROM EXERCISE 1;
	 */
	
	public int shift(char[] array) throws IOException
	{
		/*
		 * Returns the value of the letter in the key.
		 */
		
		TreeMap<Double, Character> text = new TreeMap<Double, Character>();
		TreeMap<Double, Character> baseline = new TreeMap<Double, Character>();
		Map<Character, Character> swapper = new HashMap<Character, Character>();
		
		baseline = sort(fileFreq("textToUse.txt"));
		text = sort(getFreq(group(array)));
		
		swapper = swap(text, baseline);
		
		int key = (swapper.get('e') - 'e');
		
		return key;
	}

	/*
	 * METHODS COPIED DIRECTLY FROM EXCERCISE 1
	 */
	private String remove(String name)
	{
		// Converts string to all lower case letters.
		name = name.toLowerCase();
		
		// Removes any characters that aren't alphabetical.
		name = name.replaceAll("[^a-z]", "");

		// Turns string into a character array.
		return name;
	}
	
	private Map<Character, Character> swap(TreeMap<Double, Character> text, TreeMap<Double, Character> baseline)
	{
		
		/*
		 * Takes two maps, one which is a base text, one ciphertext and
		 * extracts the letters, placing the most common in one against the most common
		 * in the other and the least common against the least common etc.
		 */
		
		Map<Character, Character> ref = new HashMap<Character, Character>();
		
		while(baseline.size() > 0)
		{
			if(text.size() != 0)
			{
				ref.put(baseline.get(baseline.lastKey()), text.get(text.lastKey()));
			
				text.remove(text.lastKey());
			}
			else
			{
							
			}
			baseline.remove(baseline.lastKey());
		}
				
		return ref;
	}
	
	private TreeMap<Double, Character> sort(Map<Character, Double> map)
	{
		/*
		 * Converts a character, double map into the inverse, and sorts by
		 * frequency.
		 * 
		 */
		
		TreeMap<Double, Character> sorted = new TreeMap<Double, Character>();
		
		for(Entry<Character, Double> entry : map.entrySet())
		{
			sorted.put(entry.getValue(), entry.getKey());
		}
		
		return sorted;
		
	}
	
	private Map<Character, Double> fileFreq(String file) throws IOException
	{		
		/*
		 * Runs various methods for returning a frequency analysed map from a file.
		 * 
		 */
		
		String text = readFile(file);
		
		char[] chars = charArray(text);
		
		return getFreq(group(chars));
		
	}
	
	private Map<Character, Integer> group(char[] letters)
	{
		/*
		 * This method runs through a passed character array and counts
		 * the number of letters and places them in a map.
		 *  
		 */
		Map<Character, Integer> map = new HashMap<Character, Integer>();

		for (int i = 0; i < letters.length; i++)
		{

			if (map.containsKey(letters[i]))
			{
				map.put(letters[i], map.get(letters[i]) + 1);
			}
			else
			{
				map.put(letters[i], 1);
			}
		}

		return map;

	}
	
	private Map<Character, Double> getFreq (Map<Character, Integer> grouped)
	{
		/*
		 * This method goes through a grouped map and calculates the frequency
		 * of each character.
		 */
		
		Map<Character, Double> map = new HashMap<Character, Double>();
		
		int total = 0;
		Double temp;
		
		for(int i : grouped.values())
		{
			total += i;
		}
		
		for(Entry<Character, Integer> entry : grouped.entrySet())
		{
			temp = (double) entry.getValue()/total;
			map.put(entry.getKey(), temp);
		}
		
		return map;
		
	}
	
	private char[] charArray(String name)
	{
		// Converts string to all lower case letters.
		name = name.toLowerCase();
		
		// Removes any characters that aren't alphabetical.
		name = name.replaceAll("[^a-z]", "");

		// Turns string into a character array.
		return name.toCharArray();
	}
	
	private String readFile(String fileName) throws IOException
	{
		/*
		 * readFile class adapted from StackOverflow on 09/10/2014 @ 12:30
		 * Mildly edited.
		 * http://stackoverflow.com/questions/16027229/reading-from
		 * -a-text-file-and-storing-in-a-string
		 */

		BufferedReader br = new BufferedReader(new FileReader(fileName));

		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				line = br.readLine();
			}

			return sb.toString();
		}
		catch (IOException io)
		{
			throw io;
		}
		finally
		{
			br.close();
		}

	}
}
