package com.dingram;

import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LetterAnalysis
{
	/**
	 * @author Daniel Ingram
	 */

	public static void main(String[] args) throws IOException
	{
		/*
		 * A method used for testing purposes only.
		 * 
		 */
		
		LetterAnalysis a = new LetterAnalysis();
						
		String text = a.decrypt("ciphertext.txt", "textToUse.txt");
		
		System.out.println(text);
		
	}

	public String decrypt(String filename, String base) throws IOException
	{
		/*
		 * The only publically accessible method. Will analyse any file passed
		 * through it. Needs a base text to analyse.
		 */
		
		TreeMap<Double, Character> text = new TreeMap<Double, Character>();
		TreeMap<Double, Character> baseline = new TreeMap<Double, Character>();
		Map<Character, Character> swapper = new HashMap<Character, Character>();
		char[] textArray;
		
		baseline = sort(fileFreq(base));
		text = sort(fileFreq(filename));
				
		swapper = swap(text, baseline);
				
		int key = 'e' - swapper.get('e') ;
	
		//System.out.println(key);
		
		textArray = getTextArray(filename);
		
		return done(textArray, key);
		
	}
	
	private String done(char[] text, int shift)
	{
		/*
		 * The final method which will return a string of text which has been
		 * deciphered.
		 */
		
		for(int i = 0; i < text.length; i++)
		{
			if(!Character.isLetter(text[i]))
			{
				
			}
			else if(text[i] - shift < 'a')
			{
				text[i] -= (shift - 26); 
			}
			else
			{
				text[i] -= shift;				
			}
		}
		
		String returnable = new String(text);
		
		return returnable;
		
	}
	
	private char[] getTextArray(String fileName) throws IOException
	{
		/*
		 * Gets a text array from a file.
		 */
		
		/**
		 *  WARNING - METHOD NAME SIMILAR TO ANOTHER, ADVISE REFACTORING
		 */
		
		String text = readFile(fileName);
		
		return textArray(text);
		
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
				ref.put(text.get(text.lastKey()), baseline.get(baseline.lastKey()));
			
				text.remove(text.lastKey());
			}
			else
			{
							
			}
			baseline.remove(baseline.lastKey());
		}
				
		return ref;
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

	private char[] charArray(String name)
	{
		// Converts string to all lower case letters.
		name = name.toLowerCase();
		
		// Removes any characters that aren't alphabetical.
		name = name.replaceAll("[^a-z]", "");

		// Turns string into a character array.
		return name.toCharArray();
	}
	
	private char[] textArray(String name)
	{
		// Converts string to all lower case letters.
		name = name.toLowerCase();

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
