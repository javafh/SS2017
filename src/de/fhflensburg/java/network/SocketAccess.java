package de.fhflensburg.java.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketAccess
{
	static public void main(String[] args)
	{

		try (Socket aSocket = new Socket("www.example.com", 80))
		{
			OutputStream rOutput = aSocket.getOutputStream();
			InputStream rInput = aSocket.getInputStream();
			String sRequest = "GET /index.html HTTP/1.0\r\n\r\n";
			byte[] aBuffer = new byte[2048];
			int nReadCount;

			ByteArrayOutputStream aResponse = new ByteArrayOutputStream();

			rOutput.write(sRequest.getBytes());
			rOutput.flush();

			while ((nReadCount = rInput.read(aBuffer)) != -1)
			{
				aResponse.write(aBuffer, 0, nReadCount);
			}

			String sResponse = new String(aResponse.toByteArray());

			System.out.println("Response: " + sResponse);
		}
		catch (IOException e)
		{
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
