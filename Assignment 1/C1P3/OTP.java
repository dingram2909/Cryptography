package com.dingram;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class OTP
{
	final int KILOBYTE = 1024;
	final int MEGABYTE = KILOBYTE * KILOBYTE;

	public static void main(String[] args)
	{

		OTP o = new OTP();
		String temp;
		String key = "6dc72fc595e35dcd38c05dca2a0d2dbd8e2df20b129b2cfa29ad17972922a2";

		temp = o.encrypt("Every cloud has a silver lining", key);

		System.out.println(temp);

		temp = o.decrypt(temp, key);

		System.out.println(temp);

		key = o.keyGen();

		o.toFile(key, "key");

		temp = o.encrypt(temp, key);

		System.out.println(temp);

		temp = o.decrypt(temp, key);

		System.out.println(temp);
		
		String[] ciphertext =
		{
				"dcb68a9df8f716409ba0fb55ee3fc8b91f090177976e0961",
				"d4e2c992e9a11e53c2f2f653ef27c8ba1e5d403e882717699d852c",
				"d1ff8299acb11a558ae5e51aed3d83bd4b5a0f39",
				"d0f78785acb65b4d8bf4e356e47485b9004c13779d270a6f8b9c3314",
				"c9fe8cdcf8a50e558aa0e053ed38c8b71e5d",
				"c9f9c999fab2095896e8fe54e6749cb00e5b057795744767c8853a1069c6be",
				"c4f99c88e4f71252c2f7f649f5318cf8044740239462477f87823116",
				"c4f99cdce4b60d44c2e4f854e47491b71e5b402093750c" };
		
		//temp = o.attack(ciphertext);
		
		//System.out.println(temp);
	}

	public String keyGen()
	{
		/*
		 * Generates a random One Time Pad keystring.
		 */
		SecureRandom ran = new SecureRandom();
		byte[] temp = new byte[MEGABYTE];

		ran.nextBytes(temp);

		return bytesToHex(temp);
	}

	public String encrypt(String text, String key)
	{
		// Encrypt by converting each char to Hex.
		text = toHex(text);
		// XOR hex value by Pad char hex.
		return hexOr(text, key);
	}

	public String decrypt(String text, String key)
	{
		// Decrypt by XORing the ciphertext by the OTP.

		text = hexOr(text, key);

		// Convert result into a character.

		return fromHex(text);
	}

	/*public String theory(String [] ciphers)
	{
		String[]  msg = new String [ciphers.length];
		
		for (int i = 0; i < ciphers.length; i++)
		{
			ArrayList<String> similar = new ArrayList<String>();
			String temp = "";
			for (int x = 0; x < ciphers.length; x++)
			{
				if (x != i)
				{
					similar.add(hexOr(ciphers[i], ciphers[x]));
				}
			}
			int max = 0;
			
			for (int x = 0; x < similar.size() - 1 ; x++)
			{
				max = Math.max(similar.get(x).length(), similar.get(x+1).length());
			}
			
			for (int x = 0; x < max; x += 2)
			{
				for (int y = 0; y < similar.size(); y++)
				{
					if(x + 2 > similar.get(y).length() - 1)
					{
						
					}
					else
					{
						String substr = similar.get(y).substring(x, x+2);
						
						if(substr.equals("00"))
						{
							temp += ciphers[i].substring(x, x+2);
							break;
						}
					}
				}
				
				//System.out.println("Length of temp: " + temp.length() + ". Should be: " + (x+2));
				
				if (temp.length() != (x+2))
				{
					temp += "00";
					//System.out.println("Added 00.");
				}
			}
			msg[i] = temp;
		}
		for (int i = 0; i < msg.length; i++)
		{
			System.out.println(msg[i]);
		}
		
		return "0";
	}
	*/
	
	/*public String attack(String[] ciphers)
	{
		Scanner input = new Scanner(System.in);
		String cipher1 = ciphers[4];
		String cipher2 = ciphers[ciphers.length-1];
		String cipher3 = (hexOr(cipher1, cipher2));
		String text;
		String temp;
		String substr;
		String conf;
		
		while(true)
		{
			System.out.println("Enter text");
			text = toHex(input.nextLine());
		
			for (int i = 0; i < (cipher3.length() - text.length()); i+=2)
			{
				substr = cipher3.substring(i, i + text.length());
				temp = hexOr(substr, text);
				
				System.out.println("Is this (near) English?");
				System.out.println(fromHex(temp));
				
				conf = input.nextLine();
				
				if (conf.equals("y"))
				{
					return text;
				}
				
			}
		}
		
		
		//return "0";
	}*/

	private void toFile(String text, String filename)
	{
		/*
		 * Writes the give text to a text file String filename should be just
		 * the name of the file, the extension is added by the method.
		 */
		try
		{
			PrintWriter out = new PrintWriter(filename + ".txt");

			out.println(text);

			out.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String toHex(String text)
	{
		/*
		 * Converts String to Hex.
		 */
		char[] tArray = text.toCharArray();
		String temp = "";

		for (int i = 0; i < tArray.length; i++)
		{
			temp += Integer.toHexString((int) tArray[i]);
		}

		return temp;
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
			temp += ((char) Integer.parseInt(subst, 16));
		}

		return temp;
	}

	private char toHex(int c)
	{
		/*
		 * Converts char to hex.
		 * (Crude and rudimentary)
		 */
		String hexSet = "0123456789abcdef";

		return hexSet.charAt(c);
	}

	private int fromHex(char c)
	{
		/*
		 * Converts char from hex.
		 * (Again, crude.)
		 */
		if (c >= '0' && c <= '9')
		{
			return c - '0';
		}
		else if (c >= 'A' && c <= 'F')
		{
			return c - 'A' + 10;
		}
		else
		{
			return c - 'a' + 10;
		}
	}

	private String hexOr(String text, String key)
	{
		/*
		 * This method XORs the given string with the given key. String text
		 * must be in HEXADECIMAL form.
		 */
		String temp = "";

		for (int i = 0; i < text.length() && i < key.length(); i++)
		{
			temp += (toHex(fromHex(text.charAt(i)) ^ fromHex(key.charAt(i))));
		}

		return temp;

	}

	// TAKEN FROM EXAMPLE ATTACK CLASS

	private String bytesToHex(byte[] bytes)
	{
		final char[] hexArray =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++)
		{
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

}
