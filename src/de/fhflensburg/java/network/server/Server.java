package de.fhflensburg.java.network.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server
{

	private int nListeningPort;

	public Server(int nListeningPort)
	{
		this.nListeningPort = nListeningPort;
	}

	public static void main(String[] rArgs)
	{
		Server aServer = new Server(8000);

		try
		{
			aServer.run();
		}
		catch (Exception e)
		{
			Logger.getGlobal().severe(e.getMessage());
		}
	}

	public void run() throws Exception
	{
		try (ServerSocket aServerSocket = new ServerSocket(nListeningPort))
		{
			Logger.getGlobal()
					.info("Server started, listening on " + nListeningPort);
			while (true)
			{
				Socket rClientSocket = aServerSocket.accept();

				handleClientRequest(rClientSocket);
			}

		}
	}

	private void handleClientRequest(Socket rClientSocket)
	{
		try
		{
			InputStream rInput = rClientSocket.getInputStream();
			OutputStream rOutput = rClientSocket.getOutputStream();
			ByteArrayOutputStream aRequest = new ByteArrayOutputStream();
			int nByte;

			while (aRequest.size() < 4 && (nByte = rInput.read()) > 0)
			{
				aRequest.write(nByte);
			}

			String sRequest = new String(aRequest.toByteArray());

			if ("PING".equals(sRequest))
			{
				rOutput.write("PONG".getBytes());
			}
			else
			{
				rOutput.write(("Unknown request: " + sRequest).getBytes());
			}
		}
		catch (IOException e)
		{
			Logger.getGlobal().warning(e.getMessage());
		}
		finally
		{
			try
			{
				rClientSocket.close();
			}
			catch (IOException e)
			{
				Logger.getGlobal().warning(
						"Could not close client socket: " + e.getMessage());
			}
		}
	}

}
