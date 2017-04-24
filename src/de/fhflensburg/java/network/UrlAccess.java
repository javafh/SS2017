package de.fhflensburg.java.network;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlAccess
{

	public static void main(String[] args)
	{
		try
		{
			URL aUrl = new URL("https://www.example.com");

			URLConnection rConnection = aUrl.openConnection();

			try (InputStream rInput = rConnection.getInputStream())
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
