package de.fhflensburg.java.network.server;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Client
{

	public static void main(String[] args)
	{
		try (Socket aSocket = new Socket("172.16.69.88", 8000))
		{
			InputStream rInput = aSocket.getInputStream();
			OutputStream rOutput = aSocket.getOutputStream();
			ByteArrayOutputStream aResponse = new ByteArrayOutputStream();
			int nByte;

			rOutput.write("PING".getBytes());

			while (aResponse.size() < 4 && (nByte = rInput.read()) > 0)
			{
				aResponse.write(nByte);
			}

			String sResponse = new String(aResponse.toByteArray());
			System.out.println("Response from server: " + sResponse);
		}
		catch (Exception e)
		{
			Logger.getGlobal().severe(e.getMessage());
		}

	}
}