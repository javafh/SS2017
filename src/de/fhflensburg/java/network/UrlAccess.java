package de.fhflensburg.java.network;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

public class UrlAccess
{

	public static void main(String[] args)
	{
		try
		{
			URL aUrl = new URL("http://www.example.com");

			try (InputStream rInput = aUrl.openStream())
			{
				LineNumberReader aInputReader = new LineNumberReader(
						new InputStreamReader(rInput));
				String sLine;

				while ((sLine = aInputReader.readLine()) != null)
				{
					System.out.println(sLine);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("ERR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
