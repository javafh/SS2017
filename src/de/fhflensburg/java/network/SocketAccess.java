package de.fhflensburg.java.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SocketAccess
{
	static public void main(String[] args)
	{
		SocketFactory rSocketFactory = SSLSocketFactory.getDefault();

		try (Socket aSocket = rSocketFactory.createSocket("www.example.com",
				443))
		{
			OutputStream rOutput = aSocket.getOutputStream();
			InputStream rInput = aSocket.getInputStream();
			String sRequest = "GET /index.html HTTP/1.1\r\nHost: www.example.com\r\n\r\n";

			int nMaxLength = Short.MAX_VALUE;
			int nContentLength = 0;
			boolean bReadingHeader = true;
			String sLine;

			rOutput.write(sRequest.getBytes());
			rOutput.flush();
			LineNumberReader aResponseReader = new LineNumberReader(
					new InputStreamReader(rInput));

			while (nMaxLength > 0
					&& (sLine = aResponseReader.readLine()) != null)
			{
				nMaxLength -= sLine.length() + 1;

				System.out.println(sLine);

				if (bReadingHeader)
				{
					if (sLine.startsWith("Content-Length:"))
					{
						String sLength = sLine
								.substring(sLine.indexOf(':') + 1).trim();

						nContentLength = Integer.parseInt(sLength);
					}
					else if (sLine.length() == 0)
					{
						nMaxLength = nContentLength;
						bReadingHeader = false;
					}
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
